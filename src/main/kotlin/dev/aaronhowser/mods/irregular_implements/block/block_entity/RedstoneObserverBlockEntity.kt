package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.aaron.AaronExtensions.isTrue
import dev.aaronhowser.mods.aaron.ClientUtil
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.LineIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import org.antlr.v4.runtime.misc.MultiMap

class RedstoneObserverBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : RedstoneToolLinkable, BlockEntity(ModBlockEntityTypes.REDSTONE_OBSERVER.get(), pPos, pBlockState) {

	override fun setRemoved() {
		val level = this.level
		if (level != null) {
			removeObserver(level, this.blockPos)
		}

		super.setRemoved()
	}

	private var linkedPos: BlockPos? = null

	override fun getLinkedPos(): BlockPos? = linkedPos

	override fun setLinkedPos(pos: BlockPos?) {
		val oldPos = linkedPos
		if (oldPos != null) {
			unlinkBlock(
				level = this.level!!,
				observerPos = this.blockPos,
				targetPos = oldPos
			)
		}

		if (pos != null) {
			linkBlock(
				level = this.level!!,
				observerPos = this.blockPos,
				targetPos = pos
			)
		} else {
			removeObserver(this.level!!, this.blockPos)
		}

		linkedPos = pos
		setChanged()
	}

	fun clientTick() {
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

	override fun setChanged() {
		super.setChanged()

		level?.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL_IMMEDIATE)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)
		this.saveToTag(tag)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)
		this.loadFromTag(tag)
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	companion object {
		private data class LevelPos(val level: Level, val pos: BlockPos)

		/**
		 * A map of a linked position to the position of every Observer that's watching it
		 */
		private val linkedPositions: MultiMap<LevelPos, BlockPos> = MultiMap()

		@JvmStatic
		private fun linkBlock(level: Level, observerPos: BlockPos, targetPos: BlockPos) {
			linkedPositions
				.getOrPut(LevelPos(level, targetPos)) { mutableListOf() }
				.add(observerPos)
		}

		@JvmStatic
		private fun unlinkBlock(level: Level, observerPos: BlockPos, targetPos: BlockPos) {
			val levelPos = LevelPos(level, targetPos)

			linkedPositions[levelPos]?.remove(observerPos)
			if (linkedPositions[levelPos]?.isEmpty().isTrue()) {
				linkedPositions.remove(levelPos)
			}
		}

		@JvmStatic
		fun updateObservers(level: Level, targetPos: BlockPos) {
			val levelPos = LevelPos(level, targetPos)

			for (observerPos in linkedPositions[levelPos] ?: return) {
				level.updateNeighborsAt(observerPos, level.getBlockState(observerPos).block)
			}
		}

		@JvmStatic
		private fun removeObserver(level: Level, observerPos: BlockPos) {
			val iterator = linkedPositions.entries.iterator()

			while (iterator.hasNext()) {
				val (levelPos, interfaces) = iterator.next()

				if (levelPos.level == level && interfaces.contains(observerPos)) {
					interfaces.remove(observerPos)
					if (interfaces.isEmpty()) {
						iterator.remove()
					}
				}
			}
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: RedstoneObserverBlockEntity
		) {
			if (level.isClientSide) {
				blockEntity.clientTick()
			}
		}
	}

}