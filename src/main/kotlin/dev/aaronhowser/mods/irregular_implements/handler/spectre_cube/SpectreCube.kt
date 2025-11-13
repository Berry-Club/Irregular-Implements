package dev.aaronhowser.mods.irregular_implements.handler.spectre_cube

import dev.aaronhowser.mods.aaron.AaronExtensions.getUuidOrNull
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModDimensions
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class SpectreCube(
	val handler: SpectreCubeSavedData
) {

	var owner: UUID? = null
	val guests: MutableList<UUID> = mutableListOf()

	var interiorHeight = 2
	var cubeIndex = 0

	private var spawnPos: BlockPos = BlockPos(8, 0, 8)
	fun getSpawnPos(): BlockPos = spawnPos

	constructor(handler: SpectreCubeSavedData, owner: UUID, cubeIndex: Int) : this(handler) {
		this.owner = owner
		this.cubeIndex = cubeIndex
		this.spawnPos = getOriginPos().offset(8, 0, 8)
	}

	fun toTag(): CompoundTag {
		val tag = CompoundTag()

		val owner = this@SpectreCube.owner
		if (owner != null) tag.putUUID(OWNER_NBT, owner)

		tag.putInt(INTERIOR_HEIGHT_NBT, interiorHeight)
		tag.putInt(INDEX_NBT, cubeIndex)

		val guestList = tag.getList(GUESTS_NBT, Tag.TAG_COMPOUND.toInt())
		for (guest in guests) {
			val guestTag = CompoundTag()
			guestTag.putUUID(UUID_NBT, guest)
			guestList.add(guestTag)
		}
		tag.put(GUESTS_NBT, guestList)

		tag.putLong(SPAWN_BLOCK_NBT, spawnPos.asLong())

		return tag
	}

	fun generate(level: Level) {
		if (level !is ServerLevel) return

		val cornerOne = getOriginPos()
		val cornerTwo = cornerOne.offset(15, interiorHeight + 1, 15)

		generateCubeShell(level, cornerOne, cornerTwo, ModBlocks.SPECTRE_BLOCK.get().defaultBlockState())
		generateCubeShell(
			level,
			cornerOne.offset(7, 0, 7),
			cornerOne.offset(8, 0, 8),
			ModBlocks.SPECTRE_CORE.get().defaultBlockState()
		)
	}

	fun increaseHeight(amountToAdd: Int, spectreLevel: ServerLevel): Int {
		if (spectreLevel.dimension() != ModDimensions.SPECTRE_LEVEL_KEY) {
			IrregularImplements.LOGGER.error("Tried to increase height of Spectre Cube in wrong dimension: ${spectreLevel.dimension().location()}")
			return 0
		}

		val maxToAdd = spectreLevel.maxBuildHeight - (interiorHeight + 2)
		val newHeight = (interiorHeight + amountToAdd).coerceAtMost(interiorHeight + maxToAdd)

		if (newHeight == interiorHeight) return 0

		val delta = newHeight - interiorHeight
		changeHeight(spectreLevel, newHeight)
		return delta
	}

	private fun changeHeight(level: Level, newHeight: Int) {
		val cornerOne = getOriginPos()
		val cornerTwo = cornerOne.offset(15, interiorHeight + 1, 15)

		generateCubeShell(level, cornerOne, cornerTwo, Blocks.AIR.defaultBlockState())
		interiorHeight = newHeight
		generate(level)

		handler.setDirty()
	}

	fun getOriginPos(): BlockPos = getCubeOriginPos(cubeIndex)

	companion object {

		fun getCubeOriginPos(cubeIndex: Int): BlockPos {
			val x = cubeIndex * 16 * 16
			return BlockPos(x, 0, 0)
		}

		const val OWNER_NBT = "owner"
		const val GUESTS_NBT = "guests"
		const val UUID_NBT = "uuid"
		const val INTERIOR_HEIGHT_NBT = "interior_height"
		const val INDEX_NBT = "index"
		const val SPAWN_BLOCK_NBT = "spawn_block"

		private fun generateCubeShell(
			level: Level,
			cornerOne: BlockPos,
			cornerTwo: BlockPos,
			state: BlockState
		) {
			val minX = minOf(cornerOne.x, cornerTwo.x)
			val minY = minOf(cornerOne.y, cornerTwo.y)
			val minZ = minOf(cornerOne.z, cornerTwo.z)
			val maxX = maxOf(cornerOne.x, cornerTwo.x)
			val maxY = maxOf(cornerOne.y, cornerTwo.y)
			val maxZ = maxOf(cornerOne.z, cornerTwo.z)

			for (x in minX..maxX) for (y in minY..maxY) for (z in minZ..maxZ) {
				if (x in setOf(minX, maxX) || y in setOf(minY, maxY) || z in setOf(minZ, maxZ)) {
					level.setBlockAndUpdate(
						BlockPos(x, y, z),
						state
					)
				}
			}
		}

		fun fromTag(handler: SpectreCubeSavedData, tag: CompoundTag): SpectreCube {
			val cube = SpectreCube(handler)
			cube.owner = tag.getUuidOrNull(OWNER_NBT)
			cube.interiorHeight = tag.getInt(INTERIOR_HEIGHT_NBT)
			cube.cubeIndex = tag.getInt(INDEX_NBT)
			cube.spawnPos = BlockPos.of(tag.getLong(SPAWN_BLOCK_NBT))

			val guestList = tag.getList(GUESTS_NBT, Tag.TAG_COMPOUND.toInt())
			for (i in guestList.indices) {
				val guestTag = guestList.getCompound(i)
				val guestUuid = guestTag.getUuidOrNull(UUID_NBT)
				if (guestUuid != null) {
					cube.guests.add(guestUuid)
				}
			}

			return cube
		}

	}

}