package dev.aaronhowser.mods.irregular_implements.block.block_entity

import com.google.common.base.Predicate
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BiomeRadarBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.BIOME_RADAR.get(), pos, blockState) {

	private var antennaValid: Boolean = false
	private var biomePos: BlockPos? = null

	private var biomeStack: ItemStack = ItemStack.EMPTY

	private fun updateAntenna() {
		val level = level ?: return

		antennaValid = ANTENNA_RELATIVE_POSITIONS.all { relPos ->
			val checkPos = blockPos.offset(relPos)
			val blockState = level.getBlockState(checkPos)

			return@all blockState.`is`(Blocks.IRON_BARS)
		}
	}

	private fun checkAntenna() {
		val wasValid = antennaValid
		updateAntenna()
		val isValid = antennaValid

		if (isValid == wasValid) return

		// Do something?
	}

	fun serverTick() {
		val level = level ?: return

		if (level.gameTime % 20 == 0L) {
			checkAntenna()
		}
	}

	fun clientTick() {
		val level = level ?: return

		if (level.gameTime % 3 != 0L) return

		// spawn particles
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		antennaValid = tag.getBoolean(ANTENNA_VALID_NBT)

		if (tag.contains(BIOME_POS_NBT)) {
			biomePos = BlockPos.of(tag.getLong(BIOME_POS_NBT))
		}

		if (tag.contains(BIOME_STACK_NBT)) {
			biomeStack = ItemStack.parseOptional(registries, tag.getCompound(BIOME_STACK_NBT))
		}
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putBoolean(ANTENNA_VALID_NBT, antennaValid)

		val bp = biomePos
		if (bp != null) {
			tag.putLong(BIOME_POS_NBT, bp.asLong())
		}

		if (!biomeStack.isEmpty) {
			tag.put(BIOME_STACK_NBT, biomeStack.save(registries))
		}
	}

	companion object {
		private const val ANTENNA_VALID_NBT = "AntennaValid"
		private const val BIOME_POS_NBT = "BiomePos"
		private const val BIOME_STACK_NBT = "BiomeStack"

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

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: BiomeRadarBlockEntity
		) {
			if (level.isClientSide) {
				blockEntity.clientTick()
			} else {
				blockEntity.serverTick()
			}
		}

	}

}