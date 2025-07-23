package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import kotlin.jvm.optionals.getOrNull

class NatureCoreBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.NATURE_CORE.get(), pos, blockState) {

	fun tick() {
		val random = level?.random ?: return

		if (random.nextInt(40) == 0) replaceSand()
		if (random.nextInt(400) == 0) spawnAnimals()
		if (random.nextInt(100) == 0) boneMealCrops()
		if (random.nextInt(600) == 0) growTrees()
		if (random.nextInt(600) == 0) rebuild()
	}

	private fun replaceSand() {
		val level = level ?: return
		val random = level.random

		val x = blockPos.x + random.nextInt(11) - 5
		val y = blockPos.y + random.nextInt(4) - 3
		val z = blockPos.z + random.nextInt(11) - 5

		val pos = BlockPos(x, y, z)

		val stateThere = level.getBlockState(pos)
		if (stateThere.`is`(BlockTags.SAND)) {
			val belowAir = level.isEmptyBlock(pos.above())
			val place = if (belowAir) Blocks.GRASS_BLOCK else Blocks.DIRT

			level.setBlockAndUpdate(pos, place.defaultBlockState())
		}
	}

	private fun spawnAnimals() {
		val level = level as? ServerLevel ?: return
		val animalsNearby = level.getEntitiesOfClass(
			Animal::class.java,
			AABB(blockPos).inflate(5.0, 5.0, 5.0)
		)

		if (animalsNearby.size > 2) return

		var pos: BlockPos
		do {
			pos = BlockPos(
				blockPos.x + level.random.nextInt(11) - 5,
				blockPos.y + level.random.nextInt(5) - 2,
				blockPos.z + level.random.nextInt(11) - 5
			)
		} while (!level.isEmptyBlock(pos))

		val entitiesThatCanSpawnHere = level.getBiome(pos)
			.value()
			.mobSettings.getMobs(MobCategory.CREATURE)

		val randomEntityType = entitiesThatCanSpawnHere
			.getRandom(level.random)
			.getOrNull()
			?.type
			?: return

		randomEntityType.spawn(level, pos, MobSpawnType.SPAWNER)
	}

	private fun boneMealCrops() {

	}

	private fun growTrees() {

	}

	private fun rebuild() {

	}

}