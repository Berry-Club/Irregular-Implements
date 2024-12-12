package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModItemTagsProvider(
    pOutput: PackOutput,
    pLookupProvider: CompletableFuture<HolderLookup.Provider>,
    pBlockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper
) : ItemTagsProvider(pOutput, pLookupProvider, pBlockTags, IrregularImplements.ID, existingFileHelper) {

    companion object {
        private fun create(id: String): TagKey<Item> = ItemTags.create(OtherUtil.modResource(id))
        private fun common(id: String): TagKey<Item> = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", id))

        val GRASS_SEEDS = create("grass_seeds")
        val RUNE_DUSTS = create("rune_dusts")
        val C_CROPS_BEAN = common("crops/bean")
        val C_RODS_OBSIDIAN = common("rods/obsidian")

        @JvmStatic
        val HIDE_POTION_HELMET = create("hide_potion_helmet")

        @JvmStatic
        val HIDE_NAME_HELMET = create("hide_name_helmet")
    }

    override fun addTags(provider: HolderLookup.Provider) {

        this.tag(C_RODS_OBSIDIAN)
            .add(
                ModItems.OBSIDIAN_ROD.get()
            )

        this.tag(HIDE_NAME_HELMET)
            .add(
                ModItems.MAGIC_HOOD.get()
            )

        this.tag(HIDE_POTION_HELMET)
            .add(
                ModItems.MAGIC_HOOD.get()
            )

        this.tag(C_CROPS_BEAN)
            .add(ModItems.BEAN.get())

        this.tag(Tags.Items.CROPS)
            .addTag(C_CROPS_BEAN)

        this.tag(Tags.Items.SEEDS)
            .addTag(GRASS_SEEDS)
            .add(
                ModItems.LOTUS_SEEDS.get(),
                ModItems.BEAN.get()
            )

        this.tag(Tags.Items.FOODS)
            .add(
                ModItems.LOTUS_BLOSSOM.get(),
                ModItems.BEAN_STEW.get()
            )

        this.tag(GRASS_SEEDS)
            .add(
                ModItems.GRASS_SEEDS.get(),
                ModItems.GRASS_SEEDS_WHITE.get(),
                ModItems.GRASS_SEEDS_ORANGE.get(),
                ModItems.GRASS_SEEDS_MAGENTA.get(),
                ModItems.GRASS_SEEDS_LIGHT_BLUE.get(),
                ModItems.GRASS_SEEDS_YELLOW.get(),
                ModItems.GRASS_SEEDS_LIME.get(),
                ModItems.GRASS_SEEDS_PINK.get(),
                ModItems.GRASS_SEEDS_GRAY.get(),
                ModItems.GRASS_SEEDS_LIGHT_GRAY.get(),
                ModItems.GRASS_SEEDS_CYAN.get(),
                ModItems.GRASS_SEEDS_PURPLE.get(),
                ModItems.GRASS_SEEDS_BLUE.get(),
                ModItems.GRASS_SEEDS_BROWN.get(),
                ModItems.GRASS_SEEDS_GREEN.get(),
                ModItems.GRASS_SEEDS_RED.get(),
                ModItems.GRASS_SEEDS_BLACK.get(),
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