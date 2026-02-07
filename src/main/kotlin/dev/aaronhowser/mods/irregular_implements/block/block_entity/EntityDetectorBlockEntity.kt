package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isServerSide
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.menu.entity_detector.EntityDetectorMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB

class EntityDetectorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.ENTITY_DETECTOR.get(), pos, blockState), MenuProvider {

	var filter: Filter = Filter.ALL
		private set

	var xRadius: Int = 1
		private set
	var yRadius: Int = 1
		private set
	var zRadius: Int = 1
		private set

	var isInverted: Boolean = false
		private set

	var isActive: Boolean = false
		private set

	private val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)

	fun getFilterStack(): ItemStack = container.getItem(0)
	fun getFilterArea(): AABB = AABB(blockPos).inflate(xRadius.toDouble(), yRadius.toDouble(), zRadius.toDouble())

	fun tick() {
		val level = level ?: return
		if (level.gameTime % 10 != 0L) return

		val filterFoundEntities = level
			.getEntities(null, getFilterArea(), filter.predicate(getFilterStack()))
			.isNotEmpty()

		val shouldBePowered = if (isInverted) !filterFoundEntities else filterFoundEntities
		if (isActive == shouldBePowered) return

		isActive = shouldBePowered
		setChanged()

		level.updateNeighborsAt(blockPos, blockState.block)
	}

	fun cycleFilter() {
		val nextOrdinal = (filter.ordinal + 1) % Filter.entries.size
		filter = Filter.entries[nextOrdinal]
		setChanged()
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putInt(X_RADIUS_NBT, xRadius)
		tag.putInt(Y_RADIUS_NBT, yRadius)
		tag.putInt(Z_RADIUS_NBT, zRadius)
		tag.putInt(FILTER_NBT, filter.ordinal)
		tag.putBoolean(INVERTED_NBT, isInverted)
		tag.putBoolean(ACTIVE_NBT, isActive)

		ContainerHelper.saveAllItems(tag, container.items, registries)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		xRadius = tag.getInt(X_RADIUS_NBT)
		yRadius = tag.getInt(Y_RADIUS_NBT)
		zRadius = tag.getInt(Z_RADIUS_NBT)
		filter = Filter.entries[tag.getInt(FILTER_NBT).coerceIn(0, Filter.entries.size - 1)]
		isInverted = tag.getBoolean(INVERTED_NBT)
		isActive = tag.getBoolean(ACTIVE_NBT)

		ContainerHelper.loadAllItems(tag, container.items, registries)
	}

	// Menu stuff

	private val containerData = object : ContainerData {
		override fun get(index: Int): Int {
			return when (index) {
				X_RADIUS_INDEX -> this@EntityDetectorBlockEntity.xRadius
				Y_RADIUS_INDEX -> this@EntityDetectorBlockEntity.yRadius
				Z_RADIUS_INDEX -> this@EntityDetectorBlockEntity.zRadius
				INVERTED_INDEX -> if (this@EntityDetectorBlockEntity.isInverted) 1 else 0
				FILTER_ORDINAL_INDEX -> this@EntityDetectorBlockEntity.filter.ordinal
				else -> 0
			}
		}

		override fun set(index: Int, value: Int) {
			when (index) {
				X_RADIUS_INDEX -> this@EntityDetectorBlockEntity.xRadius = value.coerceIn(0, 16)
				Y_RADIUS_INDEX -> this@EntityDetectorBlockEntity.yRadius = value.coerceIn(0, 16)
				Z_RADIUS_INDEX -> this@EntityDetectorBlockEntity.zRadius = value.coerceIn(0, 16)
				INVERTED_INDEX -> this@EntityDetectorBlockEntity.isInverted = (value != 0)
				FILTER_ORDINAL_INDEX -> {
					val ordinal = value.coerceIn(0, Filter.entries.size - 1)
					this@EntityDetectorBlockEntity.filter = Filter.entries[ordinal]
				}
			}

			setChanged()
		}

		override fun getCount(): Int = CONTAINER_DATA_SIZE
	}

	override fun getDisplayName(): Component = this.blockState.block.name

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return EntityDetectorMenu(containerId, playerInventory, container, containerData)
	}

	companion object {
		const val CONTAINER_SIZE = 1
		const val CONTAINER_DATA_SIZE = 5
		private const val X_RADIUS_NBT = "XRadius"
		private const val Y_RADIUS_NBT = "YRadius"
		private const val Z_RADIUS_NBT = "ZRadius"
		private const val FILTER_NBT = "Filter"
		private const val INVERTED_NBT = "Inverted"
		private const val ACTIVE_NBT = "Active"

		const val X_RADIUS_INDEX = 0
		const val Y_RADIUS_INDEX = 1
		const val Z_RADIUS_INDEX = 2
		const val INVERTED_INDEX = 3
		const val FILTER_ORDINAL_INDEX = 4

		fun tick(
			level: Level,
			pos: BlockPos,
			state: BlockState,
			blockEntity: EntityDetectorBlockEntity
		) {
			if (level.isServerSide) blockEntity.tick()
		}
	}

	enum class Filter(
		val entityClass: Class<out Entity>?,
		val unlocalizedName: String
	) {
		ALL(Entity::class.java, ModMessageLang.ENTITY_DETECTOR_ALL),
		LIVING(LivingEntity::class.java, ModMessageLang.ENTITY_DETECTOR_LIVING),
		ANIMAL(Animal::class.java, ModMessageLang.ENTITY_DETECTOR_ANIMAL),
		MONSTER(Monster::class.java, ModMessageLang.ENTITY_DETECTOR_MONSTER),
		PLAYER(Player::class.java, ModMessageLang.ENTITY_DETECTOR_PLAYER),
		ITEM(ItemEntity::class.java, ModMessageLang.ENTITY_DETECTOR_ITEM),
		CUSTOM(null, ModMessageLang.ENTITY_DETECTOR_CUSTOM)

		;

		val component = unlocalizedName.toComponent()

		fun predicate(filterStack: ItemStack): (Entity) -> Boolean {
			return { entity ->
				entityClass?.isAssignableFrom(entity.javaClass)
					?: (entity.type == filterStack.get(ModDataComponents.ENTITY_TYPE))
			}
		}
	}

}