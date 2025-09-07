package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.BlockDetectorBlock
import dev.aaronhowser.mods.irregular_implements.block.BlockDetectorBlock.Companion.TRIGGERED
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.menu.block_detector.BlockDetectorMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlockDetectorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.BLOCK_DETECTOR.get(), pos, blockState), MenuProvider {

	val container = object : ImprovedSimpleContainer(this, CONTAINER_SIZE) {
		override fun setChanged() {
			super.setChanged()
			checkAndUpdate()
		}
	}

	fun isBlockDetected(): Boolean {
		val targetPos = blockPos.relative(blockState.getValue(BlockDetectorBlock.FACING))
		val blockThere = level?.getBlockState(targetPos)?.block
		val containedBlock = (container.items.firstOrNull()?.item as? BlockItem)?.block

		return blockThere != null && blockThere == containedBlock
	}

	fun checkAndUpdate() {
		val level = level ?: return

		val isDetectingBlock = isBlockDetected()
		val wasDetectingBlock = blockState.getValue(TRIGGERED)

		if (isDetectingBlock && !wasDetectingBlock) {
			level.setBlockAndUpdate(blockPos, blockState.setValue(TRIGGERED, true))
		} else if (!isDetectingBlock && wasDetectingBlock) {
			level.setBlockAndUpdate(blockPos, blockState.setValue(TRIGGERED, false))
		}
	}

	override fun getDisplayName(): Component = blockState.block.name

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return BlockDetectorMenu(containerId, playerInventory, container)
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