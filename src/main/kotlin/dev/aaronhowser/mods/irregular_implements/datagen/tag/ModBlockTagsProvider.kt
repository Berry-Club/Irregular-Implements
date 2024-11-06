package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.BlockTagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModBlockTagsProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper?
) : BlockTagsProvider(output, lookupProvider, IrregularImplements.ID, existingFileHelper) {

    companion object {
        private fun create(id: String): TagKey<Block> {
            return BlockTags.create(OtherUtil.modResource(id))
        }

        val SUPER_LUBRICATED = create("super_lubricated")
    }

    override fun addTags(provider: HolderLookup.Provider) {


        this.tag(SUPER_LUBRICATED)
            .add(
                ModBlocks.SUPER_LUBRICANT_ICE.get(),
                ModBlocks.SUPER_LUBRICANT_STONE.get(),
                ModBlocks.SUPER_LUBRICANT_PLATFORM.get(),
                ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM.get()
            )

        val coloredGrassBlocks = DyeColor.entries.map { ModBlocks.getColoredGrass(it).get() }.toTypedArray()

        this.tag(BlockTags.DIRT)
            .add(*coloredGrassBlocks)

        this.tag(BlockTags.VALID_SPAWN)
            .add(*coloredGrassBlocks)

        this.tag(BlockTags.ANIMALS_SPAWNABLE_ON)
            .add(*coloredGrassBlocks)

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .add(*coloredGrassBlocks)

        val craftingTables = listOf(
            ModBlocks.OAK_CRAFTING_TABLE,
            ModBlocks.SPRUCE_CRAFTING_TABLE,
            ModBlocks.BIRCH_CRAFTING_TABLE,
            ModBlocks.JUNGLE_CRAFTING_TABLE,
            ModBlocks.ACACIA_CRAFTING_TABLE,
            ModBlocks.DARK_OAK_CRAFTING_TABLE,
            ModBlocks.CRIMSON_CRAFTING_TABLE,
            ModBlocks.WARPED_CRAFTING_TABLE,
            ModBlocks.MANGROVE_CRAFTING_TABLE,
            ModBlocks.BAMBOO_CRAFTING_TABLE,
            ModBlocks.CHERRY_CRAFTING_TABLE,
        ).map { it.get() }.toTypedArray()

        this.tag(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES)
            .add(*craftingTables)

        this.tag(BlockTags.MINEABLE_WITH_AXE)
            .add(*craftingTables)


    }

}