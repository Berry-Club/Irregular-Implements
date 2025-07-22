package dev.aaronhowser.mods.irregular_implements.handler.spectre_cube

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.ContainerListener
import net.minecraft.world.SimpleContainer
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import java.util.*

class SpectreCube(
	val handler: SpectreCubeSavedData
) : ContainerListener {

	var owner: UUID = UUID(0L, 0L)
	var cubeIndex: Int = Int.MIN_VALUE
	var height: Int = 2
	var spawnPos: BlockPos = BlockPos.ZERO

	val inventory: SimpleContainer = SimpleContainer(11)

	init {
		inventory.addListener(this)
	}

	constructor(handler: SpectreCubeSavedData, owner: UUID, cubeIndex: Int) : this(handler) {
		this.owner = owner
		this.cubeIndex = cubeIndex

		this.spawnPos = getPosFromCubeIndex(cubeIndex)
	}

	override fun containerChanged(container: Container) {

	}

	fun toTag(provider: HolderLookup.Provider): CompoundTag {
		val tag = CompoundTag()
		tag.putUUID(OWNER, owner)
		tag.putInt(CUBE_INDEX, cubeIndex)
		tag.putInt(HEIGHT, height)

		ContainerHelper.saveAllItems(tag, inventory.items, provider)

		return tag
	}

	companion object {
		const val OWNER = "owner"
		const val CUBE_INDEX = "cube_index"
		const val HEIGHT = "height"

		private val CACHED_SPIRAL_POINTS: MutableList<ChunkPos> = mutableListOf()

		fun getPosFromCubeIndex(cubeIndex: Int): BlockPos {
			return BlockPos(cubeIndex * 16 + 8, 0, 8)
		}

		fun fromTag(handler: SpectreCubeSavedData, tag: CompoundTag, provider: HolderLookup.Provider): SpectreCube {
			val uuid = tag.getUUID(OWNER)
			val index = tag.getInt(CUBE_INDEX)
			val height = tag.getInt(HEIGHT)

			val cube = SpectreCube(handler, uuid, index)
			cube.height = height
			cube.spawnPos = getPosFromCubeIndex(index)

			ContainerHelper.loadAllItems(tag, cube.inventory.items, provider)

			return cube
		}
	}

}