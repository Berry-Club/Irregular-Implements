package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModItemTagsProvider(
    pOutput: PackOutput,
    pLookupProvider: CompletableFuture<HolderLookup.Provider>,
    pBlockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper?
) : ItemTagsProvider(pOutput, pLookupProvider, pBlockTags, IrregularImplements.ID, existingFileHelper) {

    companion object {
        private fun create(id: String): TagKey<Item> {
            return ItemTags.create(OtherUtil.modResource(id))
        }

        val GRASS_SEEDS = create("grass_seeds")
        val RUNE_DUSTS = create("rune_dusts")
    }

    override fun addTags(provider: HolderLookup.Provider) {

        this.tag(GRASS_SEEDS)
            .add(
                ModItems.RUNE_DUST_WHITE.get(),
                ModItems.RUNE_DUST_ORANGE.get(),
                ModItems.RUNE_DUST_MAGENTA.get(),
                ModItems.RUNE_DUST_LIGHT_BLUE.get(),
                ModItems.RUNE_DUST_YELLOW.get(),
                ModItems.RUNE_DUST_LIME.get(),
                ModItems.RUNE_DUST_PINK.get(),
                ModItems.RUNE_DUST_GRAY.get(),
                ModItems.RUNE_DUST_LIGHT_GRAY.get(),
                ModItems.RUNE_DUST_CYAN.get(),
                ModItems.RUNE_DUST_PURPLE.get(),
                ModItems.RUNE_DUST_BLUE.get(),
                ModItems.RUNE_DUST_BROWN.get(),
                ModItems.RUNE_DUST_GREEN.get(),
                ModItems.RUNE_DUST_RED.get(),
                ModItems.RUNE_DUST_BLACK.get(),
            )

        this.tag(RUNE_DUSTS)
            .add(
                ModItems.RUNE_DUST_WHITE.get(),
                ModItems.RUNE_DUST_ORANGE.get(),
                ModItems.RUNE_DUST_MAGENTA.get(),
                ModItems.RUNE_DUST_LIGHT_BLUE.get(),
                ModItems.RUNE_DUST_YELLOW.get(),
                ModItems.RUNE_DUST_LIME.get(),
                ModItems.RUNE_DUST_PINK.get(),
                ModItems.RUNE_DUST_GRAY.get(),
                ModItems.RUNE_DUST_LIGHT_GRAY.get(),
                ModItems.RUNE_DUST_CYAN.get(),
                ModItems.RUNE_DUST_PURPLE.get(),
                ModItems.RUNE_DUST_BLUE.get(),
                ModItems.RUNE_DUST_BROWN.get(),
                ModItems.RUNE_DUST_GREEN.get(),
                ModItems.RUNE_DUST_RED.get(),
                ModItems.RUNE_DUST_BLACK.get(),
            )

    }
}