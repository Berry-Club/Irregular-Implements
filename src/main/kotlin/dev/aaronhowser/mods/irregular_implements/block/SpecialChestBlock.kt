package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.datagen.loot.ModChestLootSubprovider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
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

//TODO Item Models
class SpecialChestBlock private constructor(
	private val type: Type
) : ChestBlock(
	Properties.ofFullCopy(Blocks.CHEST),
	{ if (type == Type.NATURE) ModBlockEntities.NATURE_CHEST.get() else ModBlockEntities.WATER_CHEST.get() }
) {

	private enum class Type { NATURE, WATER }

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return if (type == Type.NATURE) NatureChestBlockEntity(pos, state) else WaterChestBlockEntity(pos, state)
	}

	//TODO: Move to other package
	abstract class SpecialChestBlockEntity(type: BlockEntityType<*>, pos: BlockPos, blockState: BlockState) : ChestBlockEntity(type, pos, blockState) {
		override fun getDefaultName(): Component {
			return this.blockState.block.name
		}
	}

	class NatureChestBlockEntity(pos: BlockPos, blockState: BlockState) : SpecialChestBlockEntity(ModBlockEntities.NATURE_CHEST.get(), pos, blockState)
	class WaterChestBlockEntity(pos: BlockPos, blockState: BlockState) : SpecialChestBlockEntity(ModBlockEntities.WATER_CHEST.get(), pos, blockState)

	companion object {
		val NATURE = SpecialChestBlock(Type.NATURE)
		val WATER = SpecialChestBlock(Type.WATER)

		@JvmStatic
		fun addToOceanMonument(
			level: WorldGenLevel,
			randomSource: RandomSource,
			box: BoundingBox,
			coreRoom: OceanMonumentPieces.OceanMonumentCoreRoom
		) {
			val x = 6
			val y = 1
			val z = 6

			coreRoom.placeBlock(
				level,
				ModBlocks.WATER_CHEST.get().defaultBlockState(),
				x, y, z,
				box
			)

			val chestPos = coreRoom.getWorldPos(x, y, z)

			RandomizableContainer.setBlockEntityLootTable(
				level,
				randomSource,
				chestPos,
				ModChestLootSubprovider.OCEAN_MONUMENT_CHEST
			)

		}

	}

}