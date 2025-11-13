package dev.aaronhowser.mods.irregular_implements.block.block_entity

import com.mojang.authlib.GameProfile
import dev.aaronhowser.mods.aaron.isTrue
import dev.aaronhowser.mods.irregular_implements.block.AutoPlacerBlock
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.menu.auto_placer.AutoPlacerMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.ContainerHelper
import net.minecraft.world.InteractionHand
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.neoforged.neoforge.common.util.FakePlayer
import net.neoforged.neoforge.common.util.FakePlayerFactory
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.wrapper.InvWrapper
import java.lang.ref.WeakReference
import java.util.*

class AutoPlacerBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntityTypes.AUTO_PLACER.get(), pPos, pBlockState), MenuProvider {

	val container: ImprovedSimpleContainer = object : ImprovedSimpleContainer(this, CONTAINER_SIZE) {
		override fun canAddItem(stack: ItemStack): Boolean {
			return super.canAddItem(stack) && stack.item is BlockItem
		}
	}
	private val invWrapper: InvWrapper = InvWrapper(container)

	private var uuid: UUID? = null
	private var fakePlayer: WeakReference<FakePlayer>? = null

	enum class Mode { ON_PULSE, WHILE_POWERED }

	private var mode: Mode = Mode.WHILE_POWERED
		set(value) {
			field = value
			setChanged()
		}

	fun tick() {
		if (fakePlayer?.get() == null) {
			initFakePlayer()
		}

		if (mode == Mode.WHILE_POWERED && level?.hasNeighborSignal(worldPosition).isTrue) {
			placeBlock()
		}
	}

	private fun initFakePlayer() {
		val level = level as? ServerLevel ?: return

		if (this.uuid == null) {
			this.uuid = UUID.randomUUID()
			setChanged()
		}

		val gameProfile = GameProfile(this.uuid, FAKE_PLAYER_NAME)
		val fakePlayer = FakePlayerFactory.get(level, gameProfile)

		fakePlayer.isSilent = true
		fakePlayer.setOnGround(true)

		this.fakePlayer = WeakReference(fakePlayer)
		setChanged()
	}

	fun placeBlock() {
		val stackToPlace = container.getItem(0)
		if (stackToPlace.isEmpty) return

		val level = this.level ?: return

		val direction = this.blockState.getValue(AutoPlacerBlock.FACING)
		val targetPos = worldPosition.relative(direction)

		val blockThere = level.getBlockState(targetPos)
		if (!blockThere.canBeReplaced()) return

		val fakePlayer = this.fakePlayer?.get() ?: return
		fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, stackToPlace)

		if (!fakePlayer.mayUseItemAt(targetPos, direction.opposite, stackToPlace)) return

		val blockHitResult = BlockHitResult(targetPos.center, direction.opposite, targetPos, true)
		val context = BlockPlaceContext(fakePlayer, InteractionHand.MAIN_HAND, stackToPlace, blockHitResult)

		stackToPlace.useOn(context)
	}

	fun getItemHandler(): IItemHandler = invWrapper

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return AutoPlacerMenu(containerId, playerInventory, container)
	}

	override fun getDisplayName(): Component {
		return this.blockState.block.name
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		ContainerHelper.saveAllItems(tag, this.container.items, registries)

		tag.putInt(MODE_NBT, if (mode == Mode.ON_PULSE) 0 else 1)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		ContainerHelper.loadAllItems(tag, this.container.items, registries)

		if (tag.contains(MODE_NBT)) {
			val modeInt = tag.getInt(MODE_NBT)
			mode = if (modeInt == 0) Mode.ON_PULSE else Mode.WHILE_POWERED
		}
	}

	companion object {
		const val CONTAINER_SIZE = 1
		const val MODE_NBT = "Mode"

		private const val FAKE_PLAYER_NAME = "IrregularImplementsAutoPlacer"

		fun getItemCapability(autoPlacer: AutoPlacerBlockEntity, direction: Direction?): IItemHandler {
			return autoPlacer.getItemHandler()
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: AutoPlacerBlockEntity
		) {
			if (level.isClientSide) return

			blockEntity.tick()
		}
	}

}