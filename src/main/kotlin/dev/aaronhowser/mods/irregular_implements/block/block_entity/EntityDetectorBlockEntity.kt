package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isServerSide
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
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB

class EntityDetectorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.ENTITY_DETECTOR.get(), pos, blockState), MenuProvider {

	var filter: Filter = Filter.ALL
		private set

	var xRadius: Int = 1
		private set
	var yRadius: Int = 1
		private set
	var zRadius: Int = 1
		private set

	var inverted: Boolean = false
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

		val shouldBePowered = if (inverted) !filterFoundEntities else filterFoundEntities
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
		tag.putBoolean(INVERTED_NBT, inverted)
		tag.putBoolean(ACTIVE_NBT, isActive)

		ContainerHelper.saveAllItems(tag, container.items, registries)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		xRadius = tag.getInt(X_RADIUS_NBT)
		yRadius = tag.getInt(Y_RADIUS_NBT)
		zRadius = tag.getInt(Z_RADIUS_NBT)
		filter = Filter.entries[tag.getInt(FILTER_NBT).coerceIn(0, Filter.entries.size - 1)]
		inverted = tag.getBoolean(INVERTED_NBT)
		isActive = tag.getBoolean(ACTIVE_NBT)

		ContainerHelper.loadAllItems(tag, container.items, registries)
	}

	override fun getDisplayName(): Component = this.blockState.block.name

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		TODO("Not yet implemented")
	}

	companion object {
		const val CONTAINER_SIZE = 1
		private const val X_RADIUS_NBT = "XRadius"
		private const val Y_RADIUS_NBT = "YRadius"
		private const val Z_RADIUS_NBT = "ZRadius"
		private const val FILTER_NBT = "Filter"
		private const val INVERTED_NBT = "Inverted"
		private const val ACTIVE_NBT = "Active"

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
		ALL(Entity::class.java, "all"),
		LIVING(LivingEntity::class.java, "living"),
		ANIMAL(Animal::class.java, "animal"),
		MONSTER(Monster::class.java, "monster"),
		PLAYER(Player::class.java, "player"),
		ITEM(ItemEntity::class.java, "item"),
		CUSTOM(null, "custom")

		;

		fun predicate(filterStack: ItemStack): (Entity) -> Boolean {
			return { entity ->
				entityClass?.isAssignableFrom(entity.javaClass)
					?: (entity.type == filterStack.get(ModDataComponents.ENTITY_TYPE))
			}
		}
	}

}