package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceAdvancedBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : RedstoneInterfaceBlockEntity(ModBlockEntities.ADVANCED_REDSTONE_INTERFACE.get(), pPos, pBlockState) {

	val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)

	fun getLinkedPositions(): List<BlockPos> {
		val positions = mutableListOf<BlockPos>()

		for (stack in container.items) {
			val pos = stack.get(ModDataComponents.GLOBAL_POS) ?: continue
			if (pos.dimension == this.level?.dimension()) {
				positions.add(pos.pos)
			}
		}

		return positions
	}

	override fun updateTargets() {
		val positions = getLinkedPositions()
		for (pos in positions) {
			updatePos(pos)
		}
	}

	companion object {
		const val CONTAINER_SIZE = 9
	}

}