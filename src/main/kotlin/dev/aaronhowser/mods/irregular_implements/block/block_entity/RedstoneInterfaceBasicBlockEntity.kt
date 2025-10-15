package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.LineIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceBasicBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : RedstoneInterfaceBlockEntity(
	ModBlockEntities.BASIC_REDSTONE_INTERFACE.get(),
	pPos,
	pBlockState
), RedstoneToolLinkable {

	private var linkedPos: BlockPos? = null

	override fun getLinkedPos(): BlockPos? = linkedPos

	override fun setLinkedPos(pos: BlockPos?) {
		val level = this.level ?: return

		val oldPos = linkedPos
		if (oldPos != null) {
			unlinkBlock(
				level = level,
				interfacePos = this.blockPos,
				targetPos = oldPos
			)

			updatePos(oldPos)
		}

		if (pos != null) {
			linkBlock(
				level = level,
				interfacePos = this.blockPos,
				targetPos = pos
			)

			updatePos(pos)
		} else {
			removeInterface(level, this.blockPos)
		}

		linkedPos = pos
		setChanged()
	}

	override fun updateTargets() {
		val pos = this.linkedPos ?: return
		updatePos(pos)
	}

	override fun clientTick() {
		val level = this.level ?: return
		if (!level.isClientSide) return

		val player = ClientUtil.localPlayer ?: return
		if (!player.isHolding(ModItems.REDSTONE_TOOL.get())) return

		CubeIndicatorRenderer.addIndicator(
			this.blockPos,
			2,
			0x32FF0000,
			size = 0.5f
		)

		val targetPos = this.linkedPos
		if (targetPos != null) {
			LineIndicatorRenderer.addIndicator(
				start = this.blockPos.center,
				end = targetPos.center,
				duration = 2,
				color = 0xFFFF0000.toInt()
			)

			CubeIndicatorRenderer.addIndicator(
				target = targetPos,
				duration = 2,
				color = 0x320000FF,
				size = 0.5f
			)
		}
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)
		this.saveToTag(tag)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)
		this.loadFromTag(tag)
	}

}