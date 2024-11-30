package dev.aaronhowser.mods.irregular_implements.datagen.loot

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block

class ModBlockLootTablesSubProvider(
    provider: HolderLookup.Provider
) : BlockLootSubProvider(setOf(), FeatureFlags.REGISTRY.allFlags(), provider) {

    override fun generate() {

        for (block in knownBlocks - nonDropSelfBlocks) {
            dropSelf(block)
        }

        for (block in noDropBlocks) {
            add(block, noDrop())
        }

        for (block in dropsDirtWithoutSilkTouch) {
            add(block) { createSingleItemTableWithSilkTouch(it, Items.DIRT) }
        }

    }

    private val dropsDirtWithoutSilkTouch = buildList {
        addAll(DyeColor.entries.map { ModBlocks.getColoredGrass(it).get() })

        add(ModBlocks.FERTILIZED_DIRT.get())
    }

    private val noDropBlocks = listOf(
        ModBlocks.BLAZE_FIRE
    ).map { it.get() }.toSet()

    private val nonDropSelfBlocks: Set<Block> = noDropBlocks + dropsDirtWithoutSilkTouch

    override fun getKnownBlocks(): List<Block> {
        return ModBlocks.BLOCK_REGISTRY.entries.map { it.get() }
    }

}