package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.defaultBlockState
import dev.aaronhowser.mods.irregular_implements.datagen.loot.ModChestLootSubprovider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.RandomizableContainer
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.WorldGenLevel
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.ChestType
import net.minecraft.world.level.levelgen.structure.BoundingBox
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces
import java.util.function.Supplier

class SpecialChestBlock(
	blockEntityType: Supplier<BlockEntityType<out ChestBlockEntity>>,
	private val blockEntityFactory: (BlockPos, BlockState) -> ChestBlockEntity,
) : ChestBlock(
	Properties.ofFullCopy(Blocks.CHEST),
	blockEntityType
) {

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return blockEntityFactory(pos, state)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return super.getStateForPlacement(context)?.setValue(TYPE, ChestType.SINGLE)
	}

	override fun updateShape(state: BlockState, facing: Direction, facingState: BlockState, level: LevelAccessor, currentPos: BlockPos, facingPos: BlockPos): BlockState {
		return super.updateShape(state, facing, facingState, level, currentPos, facingPos)
			.setValue(TYPE, ChestType.SINGLE)
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
				ModBlocks.WATER_CHEST.defaultBlockState(),
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
