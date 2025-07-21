package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.BlockTeleporterBlock
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.item.component.LocationDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.block_teleporter.BlockTeleporterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlockTeleporterBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.BLOCK_TELEPORTER.get(), pos, blockState), MenuProvider {

	val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)

	fun swapBlocks(): Boolean {
		val stateToSend = getMyTargetBlockState() ?: return false
		val stateToReceive = getLinkedBlockTeleporter()?.getMyTargetBlockState() ?: return false

		if (!placeBlockState(stateToSend)) return false
		if (!getLinkedBlockTeleporter()?.placeBlockState(stateToReceive).isTrue) {
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

		if (!level.getBlockState(pos).canBeReplaced()
			|| !stateToPlace.canSurvive(level, pos)
		) return false

		return level.setBlockAndUpdate(pos, stateToPlace)
	}

	fun getMyTargetBlockState(): BlockState? {
		val level = level ?: return null
		val direction = blockState.getValue(BlockTeleporterBlock.FACING)
		val targetPos = worldPosition.relative(direction)
		return level.getBlockState(targetPos)
	}

	fun getLinkedBlockTeleporter(): BlockTeleporterBlockEntity? {
		val locationData = getLinkedLocation() ?: return null
		val myLevel = level ?: return null
		val targetLevelDim = locationData.dimension

		val targetLevel = if (myLevel.dimension() == targetLevelDim) {
			myLevel
		} else {
			if (!ServerConfig.BLOCK_TELEPORTER_CROSS_DIMENSION.get()) {
				null
			} else {
				myLevel.server?.getLevel(targetLevelDim)
			}
		} ?: return null

		val targetPos = locationData.blockPos
		return targetLevel.getBlockEntity(targetPos) as? BlockTeleporterBlockEntity
	}

	fun getLinkedLocation(): LocationDataComponent? {
		return container.items
			.firstOrNull()
			?.get(ModDataComponents.LOCATION)
	}

	override fun getDisplayName(): Component = blockState.block.name

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu? {
		return BlockTeleporterMenu(containerId, playerInventory, container)
	}

	companion object {
		const val CONTAINER_SIZE = 1
	}

}