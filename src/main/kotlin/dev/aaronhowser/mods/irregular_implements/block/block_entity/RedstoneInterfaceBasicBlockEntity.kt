package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.aaron.client.AaronClientUtil
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.LineIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceBasicBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : RedstoneInterfaceBlockEntity(
	ModBlockEntityTypes.BASIC_REDSTONE_INTERFACE.get(),
	pPos,
	pBlockState
), RedstoneToolLinkable {

	private var linkedPos: BlockPos? = null
	private var futureLinkedPos: BlockPos? = null
	override fun getLinkedPos(): BlockPos? = linkedPos

	/**
	 * Actually sets futureLinkedPos; the change will be applied on the next server tick.
	 * The reason for this is that when the BlockEntity is first being loaded, Level is null, which means
	 * we can't link/unlink blocks at that time.
	 */
	override fun setLinkedPos(pos: BlockPos?) {
		futureLinkedPos = pos
	}

	private fun updateLinkedPos() {
		if (linkedPos == futureLinkedPos) return
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

		val newPos = futureLinkedPos

		if (newPos != null) {
			linkBlock(
				level = level,
				interfacePos = this.blockPos,
				targetPos = newPos
			)

			updatePos(newPos)
		} else {
			removeInterface(level, this.blockPos)
		}

		linkedPos = newPos
		setChanged()
	}

	override fun updateTargets() {
		val pos = this.linkedPos ?: return
		updatePos(pos)
	}

	override fun serverTick() {
		val level = this.level ?: return
		if (level.isClientSide) return

		updateLinkedPos()
	}

	override fun clientTick() {
		val level = this.level ?: return
		if (!level.isClientSide) return

		updateLinkedPos()

		val player = AaronClientUtil.localPlayer ?: return
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