package dev.aaronhowser.mods.irregular_implements.datagen.loot

import dev.aaronhowser.mods.irregular_implements.block.LotusBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.advancements.critereon.StatePropertiesPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition

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

        add(ModBlocks.COMPRESSED_SLIME_BLOCK.get()) { createSingleItemTable(Blocks.SLIME_BLOCK) }

        add(ModBlocks.LOTUS.get()) {
            super.applyExplosionDecay(
                it,
                LootTable.lootTable()
                    .withPool(
                        LootPool.lootPool()
                            .`when`(
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(it)
                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LotusBlock.AGE, LotusBlock.MAXIMUM_AGE))
                            )
                            .add(LootItem.lootTableItem(ModItems.LOTUS_BLOSSOM.get()))
                    )
                    .withPool(
                        LootPool.lootPool()
                            .add(LootItem.lootTableItem(ModItems.LOTUS_SEEDS.get()))
                    )
            )
        }
    }

    private val dropsDirtWithoutSilkTouch = buildList {
        addAll(DyeColor.entries.map { ModBlocks.getColoredGrass(it).get() })

        add(ModBlocks.FERTILIZED_DIRT.get())
    }

    private val noDropBlocks = listOf(
        ModBlocks.BLAZE_FIRE,
        ModBlocks.LOTUS
    ).map { it.get() }.toSet()

    private val nonDropSelfBlocks: Set<Block> = noDropBlocks + dropsDirtWithoutSilkTouch + setOf(
        ModBlocks.COMPRESSED_SLIME_BLOCK.get()
    )

    override fun getKnownBlocks(): List<Block> {
        return ModBlocks.BLOCK_REGISTRY.entries.map { it.get() }
    }

}