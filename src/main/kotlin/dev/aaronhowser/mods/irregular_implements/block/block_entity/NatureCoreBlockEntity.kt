package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModEntityTypeTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.nextRange
import dev.aaronhowser.mods.irregular_implements.util.StructureSchematics
import dev.aaronhowser.mods.irregular_implements.world.feature.NatureCoreFeature
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.util.Mth
import net.minecraft.util.random.WeightedRandomList
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.LevelEvent
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.AABB
import kotlin.jvm.optionals.getOrNull
import kotlin.math.cos

class NatureCoreBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.NATURE_CORE.get(), pos, blockState) {

	private fun tick() {
		val random = level?.random ?: return

		if (random.nextInt(40) == 0) replaceSand()
		if (random.nextInt(400) == 0) spawnAnimals()
		if (random.nextInt(100) == 0) boneMealCrops()
		if (random.nextInt(600) == 0) plantSaplings()
		if (random.nextInt(600) == 0) rebuild()
	}

	private fun replaceSand() {
		val level = level ?: return

		val radius = ServerConfig.CONFIG.natureCoreReplaceSandRadius.get()

		var attempts = 0
		val pos = this.blockPos.mutable()
		do {
			pos.set(
				this.blockPos.offset(
					level.random.nextRange(-radius, radius + 1),
					level.random.nextRange(-radius, radius + 1),
					level.random.nextRange(-radius, radius + 1)
				)
			)

			val blockStateThere = level.getBlockState(pos)
			attempts++

			val foundSand = blockStateThere.`is`(BlockTags.SAND)
					&& !blockStateThere.`is`(ModBlockTagsProvider.NATURE_CORE_IMMUNE)
		} while (foundSand && attempts < 50)

		val stateThere = level.getBlockState(pos)
		if (stateThere.`is`(BlockTags.SAND) && !stateThere.`is`(ModBlockTagsProvider.NATURE_CORE_IMMUNE)) {
			val belowAir = level.isEmptyBlock(pos.above())
			val place = if (belowAir) Blocks.GRASS_BLOCK else Blocks.DIRT

			level.setBlockAndUpdate(pos, place.defaultBlockState())
		}
	}

	private fun spawnAnimals() {
		val level = level as? ServerLevel ?: return

		val radius = ServerConfig.CONFIG.natureCoreSpawnAnimalRadius.get()

		val animalsNearby = level.getEntitiesOfClass(
			Animal::class.java,
			AABB(blockPos).inflate(radius * 2, radius * 2, radius * 2)
		)

		if (animalsNearby.size > 2) return

		val radCeil = Mth.ceil(radius)

		var attempts = 0
		var pos: BlockPos
		do {
			pos = this.blockPos.offset(
				level.random.nextRange(-radCeil, radCeil + 1),
				level.random.nextRange(-radCeil, radCeil + 1),
				level.random.nextRange(-radCeil, radCeil + 1)
			)

			val fluidStateThere = level.getFluidState(pos)
			val blockStateThere = level.getBlockState(pos)
			attempts++

			val foundValidSpot = !blockStateThere.getCollisionShape(level, pos).isEmpty
					|| !fluidStateThere.isEmpty && !fluidStateThere.`is`(Fluids.WATER)
		} while (foundValidSpot && attempts < 50)

		val isUnderWater = level.getFluidState(pos).`is`(Fluids.WATER)
		val mobCategory = if (isUnderWater) {
			if (level.random.nextBoolean()) MobCategory.WATER_AMBIENT else MobCategory.WATER_CREATURE
		} else {
			MobCategory.CREATURE
		}

		val entitiesThatCanSpawnHere = level.getBiome(pos)
			.value()
			.mobSettings.getMobs(mobCategory)

		val filtered = entitiesThatCanSpawnHere
			.unwrap()
			.filterNot {
				it.type.`is`(ModEntityTypeTagsProvider.NATURE_CORE_IMMUNE)
			}

		if (filtered.isEmpty()) return

		val newWeightedList = WeightedRandomList.create(filtered)

		val randomEntityType = newWeightedList
			.getRandom(level.random)
			.getOrNull()
			?.type
			?: return

		randomEntityType.spawn(level, pos, MobSpawnType.SPAWNER)
	}

	private fun boneMealCrops() {
		val level = level as? ServerLevel ?: return

		val radius = ServerConfig.CONFIG.natureCoreBoneMealCropRadius.get()

		var attempts = 0
		var pos: BlockPos
		do {
			pos = this.blockPos.offset(
				level.random.nextRange(-radius, radius + 1),
				level.random.nextRange(-radius, radius + 1),
				level.random.nextRange(-radius, radius + 1)
			)

			val blockStateThere = level.getBlockState(pos)
			attempts++

			val blockThere = blockStateThere.block
			val success = !blockStateThere.`is`(ModBlockTagsProvider.NATURE_CORE_IMMUNE)
					&& blockThere is BonemealableBlock
					&& blockThere.isValidBonemealTarget(level, pos, blockStateThere)
					&& blockThere.isBonemealSuccess(level, level.random, pos, blockStateThere)
		} while (success && attempts < 50)

		val state = level.getBlockState(pos)
		val block = state.block as? BonemealableBlock ?: return

		block.performBonemeal(level, level.random, pos, state)
	}

	private fun plantSaplings() {
		val level = level as? ServerLevel ?: return

		val maxRadius = ServerConfig.CONFIG.natureCorePlantSaplingRadius.get()

		val radius = Mth.lerp(level.random.nextDouble(), 10.0, maxRadius.toDouble()).toInt()
		val rads = level.random.nextDouble() * Mth.TWO_PI

		val x = Mth.floor(blockPos.x + radius * cos(rads))
		val z = Mth.floor(blockPos.z + radius * cos(rads))
		val y = blockPos.y + 10

		val pos = BlockPos(x, y, z).mutable()

		do {
			pos.move(Direction.DOWN)
		} while (level.isInWorldBounds(pos) && level.isEmptyBlock(pos))

		pos.move(Direction.UP)

		val saplings = BuiltInRegistries.BLOCK
			.filter {
				val state = it.defaultBlockState()

				state.`is`(ModBlockTagsProvider.NATURE_CORE_POSSIBLE_SAPLINGS)
						&& state.canSurvive(level, pos)
			}

		if (saplings.isEmpty()) return

		val index = level.random.nextInt(saplings.size)
		val randomSapling = saplings.getOrNull(index)?.defaultBlockState() ?: return

		level.levelEvent(
			null,
			LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH,
			pos,
			Block.getId(randomSapling)
		)

		level.setBlockAndUpdate(pos, randomSapling)
	}

	private fun rebuild() {
		val level = level as? ServerLevel ?: return
		val biome = level.getBiome(blockPos)

		val log = NatureCoreFeature.getLogFromBiome(biome)
		val leaves = NatureCoreFeature.getLeavesFromBiome(biome)

		val schematic = StructureSchematics.getNatureCore(log, leaves)
		if (schematic.entries.isEmpty()) return

		val index = level.random.nextInt(schematic.entries.size)
		val (offset, state) = schematic.entries.elementAtOrNull(index) ?: return

		if (offset == BlockPos.ZERO) return

		val pos = blockPos.offset(offset)
		if (level.isOutsideBuildHeight(pos) || !level.isEmptyBlock(pos)) return

		level.setBlockAndUpdate(pos, state)
	}

	companion object {
		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: NatureCoreBlockEntity
		) {
			if (level.isClientSide) return
			blockEntity.tick()
		}
	}

}