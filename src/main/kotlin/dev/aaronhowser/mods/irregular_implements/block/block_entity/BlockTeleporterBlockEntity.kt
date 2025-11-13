package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.aaronlib.isTrue
import dev.aaronhowser.mods.irregular_implements.block.BlockTeleporterBlock
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.menu.block_teleporter.BlockTeleporterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.GlobalPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlockTeleporterBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.BLOCK_TELEPORTER.get(), pos, blockState), MenuProvider {

	val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)

	//TODO: Sound on either succeed or fail
	fun swapBlocks(): Boolean {
		if (level?.isClientSide.isTrue) return false

		val stateToSend = getMyTargetBlockState() ?: return false
		val stateToReceive = getLinkedBlockTeleporter()?.getMyTargetBlockState() ?: return false

		if (!placeBlockState(stateToReceive)) return false
		if (!getLinkedBlockTeleporter()?.placeBlockState(stateToSend).isTrue) {
			// If we can't place the target block state, revert our own placement
			placeBlockState(stateToSend)
			return false
		}

		return true
	}

	private fun placeBlockState(stateToPlace: BlockState): Boolean {
		val level = level ?: return false
		val direction = blockState.getValue(BlockTeleporterBlock.FACING)
		val pos = worldPosition.relative(direction)

		if (!stateToPlace.canSurvive(level, pos)) return false

		return level.setBlockAndUpdate(pos, stateToPlace)
	}

	fun getMyTargetBlockState(): BlockState? {
		val level = level ?: return null
		val direction = blockState.getValue(BlockTeleporterBlock.FACING)
		val targetPos = worldPosition.relative(direction)

		// Disallow block entities from being teleported
		if (level.getBlockEntity(targetPos) != null) return null

		val targetState = level.getBlockState(targetPos)
		if (targetState.`is`(ModBlockTagsProvider.EXCLUDED_FROM_BLOCK_TELEPORTER)) return null

		return targetState
	}

	fun getLinkedBlockTeleporter(): BlockTeleporterBlockEntity? {
		val locationData = getLinkedLocation() ?: return null
		val myLevel = level ?: return null
		val targetLevelDim = locationData.dimension

		val targetLevel = if (myLevel.dimension() == targetLevelDim) {
			myLevel
		} else {
			if (!ServerConfig.CONFIG.blockTeleporterCrossDimension.get()) {
				null
			} else {
				myLevel.server?.getLevel(targetLevelDim)
			}
		} ?: return null

		val targetPos = locationData.pos
		return targetLevel.getBlockEntity(targetPos) as? BlockTeleporterBlockEntity
	}

	fun getLinkedLocation(): GlobalPos? {
		return container.items
			.firstOrNull()
			?.get(ModDataComponents.GLOBAL_POS)
	}

	override fun getDisplayName(): Component = blockState.block.name

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return BlockTeleporterMenu(containerId, playerInventory, container)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)
		ContainerHelper.saveAllItems(tag, container.items, registries)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)
		ContainerHelper.loadAllItems(tag, container.items, registries)
	}

	companion object {
		const val CONTAINER_SIZE = 1
	}

}