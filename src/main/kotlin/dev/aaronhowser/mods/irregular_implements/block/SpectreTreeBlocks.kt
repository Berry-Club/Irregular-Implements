package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModConfiguredFeatures
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.grower.TreeGrower
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.MapColor
import net.neoforged.neoforge.common.ItemAbilities
import net.neoforged.neoforge.common.ItemAbility
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import java.util.*

// Made this class because it requires a lot of anon classes and those are gross
// Who decided that flammability should work like this?
object SpectreTreeBlocks {

	val SPECTRE_WOOD: Block =
		object : Block(
			Properties.ofFullCopy(Blocks.OAK_WOOD)
				.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
				.noOcclusion()
		) {
			override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
				return super.getFlammability(Blocks.OAK_WOOD.defaultBlockState(), level, pos, direction) > 0
			}

			override fun getFlammability(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
				return super.getFlammability(Blocks.OAK_WOOD.defaultBlockState(), level, pos, direction)
			}

			override fun getFireSpreadSpeed(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
				return super.getFireSpreadSpeed(Blocks.OAK_WOOD.defaultBlockState(), level, pos, direction)
			}

			override fun skipRendering(state: BlockState, adjacentBlockState: BlockState, side: Direction): Boolean {
				return adjacentBlockState.`is`(this)
			}

		}

	val SPECTRE_LOG: FlammableRotatedPillarBlock =
		object : FlammableRotatedPillarBlock(
			Blocks.OAK_LOG,
			Properties
				.ofFullCopy(Blocks.OAK_LOG)
				.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
				.noOcclusion()
		) {
			override fun skipRendering(state: BlockState, adjacentBlockState: BlockState, side: Direction): Boolean {
				return adjacentBlockState.`is`(this)
			}
		}

	val SPECTRE_LEAVES: LeavesBlock =
		object : LeavesBlock(
			Properties
				.ofFullCopy(Blocks.OAK_LEAVES)
				.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
				.noOcclusion()
		) {
			override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
				return super.getFlammability(Blocks.OAK_LEAVES.defaultBlockState(), level, pos, direction) > 0
			}

			override fun getFlammability(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
				return super.getFlammability(Blocks.OAK_LEAVES.defaultBlockState(), level, pos, direction)
			}

			override fun getFireSpreadSpeed(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
				return super.getFireSpreadSpeed(Blocks.OAK_LEAVES.defaultBlockState(), level, pos, direction)
			}
		}

	val SPECTRE_PLANKS: Block =
		object : Block(
			Properties
				.ofFullCopy(Blocks.OAK_PLANKS)
				.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
				.noOcclusion()
		) {
			override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
				return super.getFlammability(Blocks.OAK_PLANKS.defaultBlockState(), level, pos, direction) > 0
			}

			override fun getFlammability(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
				return super.getFlammability(Blocks.OAK_PLANKS.defaultBlockState(), level, pos, direction)
			}

			override fun getFireSpreadSpeed(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
				return super.getFireSpreadSpeed(Blocks.OAK_PLANKS.defaultBlockState(), level, pos, direction)
			}

			override fun skipRendering(state: BlockState, adjacentBlockState: BlockState, side: Direction): Boolean {
				return adjacentBlockState.`is`(this)
			}
		}

	val SPECTRE_TREE_GROWER: TreeGrower = TreeGrower(
		OtherUtil.modResource("spectre").toString(),
		Optional.empty(),
		Optional.of(ModConfiguredFeatures.SPECTRE_TREE),
		Optional.empty()
	)

	val SPECTRE_SAPLING = SaplingBlock(
		SPECTRE_TREE_GROWER,
		BlockBehaviour.Properties
			.ofFullCopy(Blocks.OAK_SAPLING)
			.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
			.noOcclusion()
	)

	fun convertSaplings(event: PlayerInteractEvent.RightClickBlock) {
		val usedStack = event.itemStack
		if (!usedStack.`is`(ModItems.ECTOPLASM)) return

		val level = event.level as? ServerLevel ?: return
		val pos = event.pos

		val clickedState = level.getBlockState(pos)
		if (!clickedState.`is`(ModBlockTagsProvider.CONVERTS_TO_SPECTRE_SAPLING)) return

		level.setBlockAndUpdate(pos, ModBlocks.SPECTRE_SAPLING.get().defaultBlockState())
		usedStack.consume(1, event.entity)

		level.sendParticles(
			ParticleTypes.SCULK_SOUL,
			pos.x + 0.5,
			pos.y + 0.5,
			pos.z + 0.5,
			5,
			0.25,
			0.25,
			0.25,
			0.0
		)
	}

}