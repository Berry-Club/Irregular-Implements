package dev.aaronhowser.mods.irregular_implements.block.block_entity

import com.google.common.base.Predicate
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BiomeRadarBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.BIOME_RADAR.get(), pos, blockState) {

	private var antennaValid: Boolean = false

	fun checkAntenna() {
		val level = level ?: return

		antennaValid = ANTENNA_RELATIVE_POSITIONS.all { relPos ->
			val checkPos = blockPos.offset(relPos)
			val blockState = level.getBlockState(checkPos)

			return@all blockState.`is`(Blocks.IRON_BARS)
		}
	}

	companion object {
		val ANTENNA_RELATIVE_POSITIONS = listOf(
			BlockPos(0, 1, 0),
			BlockPos(0, 2, 0),

			BlockPos(1, 2, 0),
			BlockPos(-1, 2, 0),
			BlockPos(0, 2, 1),
			BlockPos(0, 2, -1),

			BlockPos(1, 3, 0),
			BlockPos(-1, 3, 0),
			BlockPos(0, 3, 1),
			BlockPos(0, 3, -1),
		)

		fun locateBiome(
			targetBiome: Holder<Biome>,
			searchFrom: BlockPos,
			level: ServerLevel
		): BlockPos? {
			return level.findClosestBiome3d(
				Predicate { it.`is`(targetBiome) },
				searchFrom,
				6400,
				32,
				64
			)?.first
		}
	}

}