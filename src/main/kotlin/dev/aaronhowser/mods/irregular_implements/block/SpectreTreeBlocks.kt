package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModConfiguredFeatures
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.grower.TreeGrower
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.MapColor
import net.neoforged.neoforge.common.ItemAbilities
import net.neoforged.neoforge.common.ItemAbility
import java.util.*

// Made this class because it requires a lot of anon classes and those are gross
// Who decided that flammability should work like this?
object SpectreTreeBlocks {

    val SPECTRE_WOOD = object : Block(
        Properties.ofFullCopy(Blocks.OAK_WOOD)
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
    ) {
        override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
            return getFlammability(Blocks.OAK_WOOD.defaultBlockState(), level, pos, direction) > 0
        }

        override fun getFlammability(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
            return getFlammability(Blocks.OAK_WOOD.defaultBlockState(), level, pos, direction)
        }

        override fun getFireSpreadSpeed(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
            return getFireSpreadSpeed(Blocks.OAK_WOOD.defaultBlockState(), level, pos, direction)
        }
    }

    val SPECTRE_LOG = object : FlammableRotatedPillarBlock(
        Blocks.OAK_LOG,
        Properties
            .ofFullCopy(Blocks.OAK_LOG)
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
    ) {
        override fun getToolModifiedState(state: BlockState, context: UseOnContext, itemAbility: ItemAbility, simulate: Boolean): BlockState? {
            if (itemAbility != ItemAbilities.AXE_STRIP) return super.getToolModifiedState(state, context, itemAbility, simulate)

            return ModBlocks.STRIPPED_SPECTRE_LOG.get()
                .defaultBlockState()
                .setValue(AXIS, state.getValue(AXIS))
        }
    }

    val STRIPPED_SPECTRE_LOG = object : FlammableRotatedPillarBlock(
        Blocks.STRIPPED_OAK_LOG,
        Properties
            .ofFullCopy(Blocks.STRIPPED_OAK_LOG)
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
    ) {}

    val SPECTRE_LEAVES = object : LeavesBlock(
        Properties
            .ofFullCopy(Blocks.OAK_LEAVES)
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
    ) {
        override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
            return getFlammability(Blocks.OAK_LEAVES.defaultBlockState(), level, pos, direction) > 0
        }

        override fun getFlammability(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
            return getFlammability(Blocks.OAK_LEAVES.defaultBlockState(), level, pos, direction)
        }

        override fun getFireSpreadSpeed(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
            return getFireSpreadSpeed(Blocks.OAK_LEAVES.defaultBlockState(), level, pos, direction)
        }
    }

    val SPECTRE_PLANKS = object : Block(
        Properties
            .ofFullCopy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
    ) {
        override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
            return getFlammability(Blocks.OAK_PLANKS.defaultBlockState(), level, pos, direction) > 0
        }

        override fun getFlammability(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
            return getFlammability(Blocks.OAK_PLANKS.defaultBlockState(), level, pos, direction)
        }

        override fun getFireSpreadSpeed(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
            return getFireSpreadSpeed(Blocks.OAK_PLANKS.defaultBlockState(), level, pos, direction)
        }
    }

    val SPECTRE_TREE_GROWER: TreeGrower = TreeGrower(
        OtherUtil.modResource("spectre").toString(),
        Optional.empty(),
        Optional.of(ModConfiguredFeatures.SPECTRE_TREE_KEY),
        Optional.empty()
    )

    val SPECTRE_SAPLING = object : SaplingBlock(
        SPECTRE_TREE_GROWER,
        Properties
            .ofFullCopy(Blocks.OAK_SAPLING)
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
    ) {}

}