package dev.aaronhowser.mods.irregular_implements.handler

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import org.antlr.v4.runtime.misc.MultiMap
import kotlin.collections.component1
import kotlin.collections.component2

object WirelessRedstoneHandler {

	private data class LevelPos(val level: Level, val pos: BlockPos)

	/**
	 * A map of a linked position to the position of every Interface that links to it
	 */
	private val linkedPositions: MultiMap<LevelPos, BlockPos> = MultiMap()

	@JvmStatic
	fun linkBlock(level: Level, interfacePos: BlockPos, targetPos: BlockPos) {
		linkedPositions
			.getOrPut(LevelPos(level, targetPos)) { mutableListOf() }
			.add(interfacePos)
	}

	@JvmStatic
	fun unlinkBlock(level: Level, interfacePos: BlockPos, targetPos: BlockPos) {
		val levelPos = LevelPos(level, targetPos)

		linkedPositions[levelPos]?.remove(interfacePos)
		if (linkedPositions[levelPos]?.isEmpty().isTrue) {
			linkedPositions.remove(levelPos)
		}
	}

	@JvmStatic
	fun getLinkedPower(level: Level, targetPos: BlockPos): Int {
		val levelPos = LevelPos(level, targetPos)
		val interfaces = linkedPositions[levelPos] ?: return -1
		if (interfaces.isEmpty()) return -1
		return interfaces.maxOf { level.getBestNeighborSignal(it) }
	}

	@JvmStatic
	fun removeInterface(level: Level, interfacePos: BlockPos) {
		val iterator = linkedPositions.entries.iterator()

		while (iterator.hasNext()) {
			val (levelPos, interfaces) = iterator.next()

			if (levelPos.level == level && interfaces.contains(interfacePos)) {
				interfaces.remove(interfacePos)
				if (interfaces.isEmpty()) {
					iterator.remove()
				}
			}
		}
	}

}