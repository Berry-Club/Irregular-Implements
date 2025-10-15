package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.LineIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceAdvancedBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : RedstoneInterfaceBlockEntity(ModBlockEntityTypes.ADVANCED_REDSTONE_INTERFACE.get(), pPos, pBlockState) {

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

	override fun clientTick() {
		val level = this.level ?: return
		if (!level.isClientSide) return

		val player = ClientUtil.localPlayer ?: return
		if (!player.isHolding(ModItems.REDSTONE_TOOL.get())) return

		val links = getLinkedPositions()

		if (links.isEmpty()) return

		CubeIndicatorRenderer.addIndicator(
			this.blockPos,
			2,
			0x32FF0000,
			size = 0.5f
		)

		for (link in links) {
			LineIndicatorRenderer.addIndicator(
				start = this.blockPos.center,
				end = link.center,
				duration = 2,
				color = 0xFFFF0000.toInt()
			)

			CubeIndicatorRenderer.addIndicator(
				target = link,
				duration = 2,
				color = 0x320000FF,
				size = 0.5f
			)
		}
	}

	companion object {
		const val CONTAINER_SIZE = 9
	}

}