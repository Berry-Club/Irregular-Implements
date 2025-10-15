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
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.LongArrayTag
import net.minecraft.world.ContainerHelper
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.wrapper.InvWrapper

class RedstoneInterfaceAdvancedBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : RedstoneInterfaceBlockEntity(ModBlockEntityTypes.ADVANCED_REDSTONE_INTERFACE.get(), pPos, pBlockState) {

	private val linkedPositions: MutableList<BlockPos> = mutableListOf()

	val container = object : ImprovedSimpleContainer(this, CONTAINER_SIZE) {
		override fun setChanged() {
			super.setChanged()
			updateLinkedPositions(this)
		}
	}

	private val invWrapper = InvWrapper(container)

	private fun updateLinkedPositions(container: ImprovedSimpleContainer) {
		val level = this.level ?: return

		val newList = mutableListOf<BlockPos>()

		for (stack in container.items) {
			val pos = stack.get(ModDataComponents.GLOBAL_POS) ?: continue
			if (pos.dimension == this@RedstoneInterfaceAdvancedBlockEntity.level?.dimension()) {
				newList.add(pos.pos)
			}
		}

		val added = newList.filterNot { linkedPositions.contains(it) }
		val removed = linkedPositions.filterNot { newList.contains(it) }

		linkedPositions.clear()
		linkedPositions.addAll(newList)

		for (pos in added) {
			linkBlock(
				level = level,
				interfacePos = this.blockPos,
				targetPos = pos
			)

			updatePos(pos)
		}

		for (pos in removed) {
			unlinkBlock(
				level = level,
				interfacePos = this.blockPos,
				targetPos = pos
			)

			updatePos(pos)
		}

		level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL_IMMEDIATE)
	}

	override fun updateTargets() {
		val positions = linkedPositions
		for (pos in positions) {
			updatePos(pos)
		}
	}

	override fun clientTick() {
		val level = this.level ?: return
		if (!level.isClientSide) return

		val player = ClientUtil.localPlayer ?: return
		if (!player.isHolding(ModItems.REDSTONE_TOOL.get())) return

		val links = linkedPositions

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

	fun getItemHandler(direction: Direction?): IItemHandler = invWrapper

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		if (linkedPositions.isNotEmpty()) {
			val longArrayTag = LongArrayTag(linkedPositions.map(BlockPos::asLong))
			tag.put(LINKS_NBT, longArrayTag)
		}

		ContainerHelper.saveAllItems(tag, container.items, registries)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		ContainerHelper.loadAllItems(tag, container.items, registries)

		linkedPositions.clear()
		if (tag.contains(LINKS_NBT)) {
			val longArrayTag = tag.getLongArray(LINKS_NBT)
			for (long in longArrayTag) {
				linkedPositions.add(BlockPos.of(long))
			}
		}
	}

	companion object {
		const val LINKS_NBT = "Links"

		const val CONTAINER_SIZE = 9

		fun getItemCapability(
			advancedRedstoneInterface: RedstoneInterfaceAdvancedBlockEntity,
			direction: Direction?
		): IItemHandler {
			return advancedRedstoneInterface.getItemHandler(direction)
		}
	}

}