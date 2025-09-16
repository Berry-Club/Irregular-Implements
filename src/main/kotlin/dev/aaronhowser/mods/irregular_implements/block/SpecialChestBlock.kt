package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpecialChestBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.loot.ModChestLootSubprovider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.RandomizableContainer
import net.minecraft.world.level.WorldGenLevel
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.structure.BoundingBox
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces
import java.util.function.Supplier

//TODO Item Models
class SpecialChestBlock private constructor(
	private val chestType: Type,
) : ChestBlock(
	Properties.ofFullCopy(Blocks.CHEST),
	chestType.blockEntityType
) {

	enum class Type(val blockEntityType: Supplier<BlockEntityType<out ChestBlockEntity>>) {
		NATURE({ ModBlockEntities.NATURE_CHEST.get() }),
		WATER({ ModBlockEntities.WATER_CHEST.get() })

		;

		val block = SpecialChestBlock(this)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return when (this.chestType) {
			Type.NATURE -> SpecialChestBlockEntity.NatureChestBlockEntity(pos, state)
			Type.WATER -> SpecialChestBlockEntity.WaterChestBlockEntity(pos, state)
		}
	}

	companion object {

		@JvmStatic
		fun addToOceanMonument(
			level: WorldGenLevel,
			random: RandomSource,
			boundingBox: BoundingBox,
			coreRoom: OceanMonumentPieces.OceanMonumentCoreRoom
		) {
			val x = 6
			val y = 1
			val z = 6

			coreRoom.placeBlock(
				level,
				ModBlocks.WATER_CHEST.get().defaultBlockState(),
				x, y, z,
				boundingBox
			)

			val chestPosition = coreRoom.getWorldPos(x, y, z)

			RandomizableContainer.setBlockEntityLootTable(
				level,
				random,
				chestPosition,
				ModChestLootSubprovider.OCEAN_MONUMENT_CHEST
			)
		}
	}
}
