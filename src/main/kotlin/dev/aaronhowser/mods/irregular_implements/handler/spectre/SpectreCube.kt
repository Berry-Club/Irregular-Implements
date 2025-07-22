package dev.aaronhowser.mods.irregular_implements.handler.spectre

import net.minecraft.core.BlockPos
import net.minecraft.world.Container
import net.minecraft.world.ContainerListener
import net.minecraft.world.SimpleContainer
import net.minecraft.world.level.ChunkPos
import java.util.*

class SpectreCube(
	val handler: SpectreHandler
) : ContainerListener {

	var owner: UUID = UUID(0L, 0L)
	var cubeIndex: Int = Int.MIN_VALUE
	var height: Int = 2
	var spawnPos: BlockPos = BlockPos.ZERO

	val inventory: SimpleContainer = SimpleContainer(11)

	init {
		inventory.addListener(this)
	}

	constructor(handler: SpectreHandler, owner: UUID, cubeIndex: Int) : this(handler) {
		this.owner = owner
		this.cubeIndex = cubeIndex

		this.spawnPos = getPosFromCubeIndex(cubeIndex)
	}

	override fun containerChanged(container: Container) {

	}

	companion object {
		private val CACHED_SPIRAL_POINTS: MutableList<ChunkPos> = mutableListOf()

		fun getPosFromCubeIndex(cubeIndex: Int): BlockPos {
			return BlockPos(cubeIndex * 16 + 8, 0, 8)
		}
	}

}