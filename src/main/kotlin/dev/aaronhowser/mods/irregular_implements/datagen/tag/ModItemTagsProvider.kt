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
        val SUPER_LUBRICATED_BLOCKS = create("super_lubricated")
        val C_CROPS_BEAN = common("crops/bean")
        val C_RODS_OBSIDIAN = common("rods/obsidian")
        val RING_CURIO: TagKey<Item> = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "ring"))
        val ENCHANTABLE_MAGNETIC = create("enchantable/magnetic")

        @JvmStatic
        val HIDE_POTION_HELMET = create("hide_potion_helmet")

        @JvmStatic
        val HIDE_NAME_HELMET = create("hide_name_helmet")
    }

    override fun addTags(provider: HolderLookup.Provider) {

        this.tag(ENCHANTABLE_MAGNETIC)
            .addTags(
                Tags.Items.MINING_TOOL_TOOLS,
                Tags.Items.MELEE_WEAPON_TOOLS
            )

        this.tag(RING_CURIO)
            .add(
                ModItems.OBSIDIAN_SKULL_RING.get(),
                ModItems.LAVA_CHARM.get()
            )

        this.copy(ModBlockTagsProvider.SUPER_LUBRICATED, SUPER_LUBRICATED_BLOCKS)

        this.tag(Tags.Items.MELEE_WEAPON_TOOLS)
            .add(
                ModItems.SPECTRE_SWORD.get()
            )

        this.tag(Tags.Items.MINING_TOOL_TOOLS)
            .add(
                ModItems.SPECTRE_PICKAXE.get(),
                ModItems.SPECTRE_SHOVEL.get(),
                ModItems.SPECTRE_AXE.get()
            )

        this.tag(ItemTags.AXES)
            .add(
                ModItems.SPECTRE_AXE.get()
            )

        this.tag(ItemTags.BEACON_PAYMENT_ITEMS)
            .add(
                ModItems.SPECTRE_INGOT.get()
            )

        this.tag(ItemTags.SWORDS)
            .add(
                ModItems.SPECTRE_SWORD.get()
            )

        this.tag(ItemTags.SHOVELS)
            .add(
                ModItems.SPECTRE_SHOVEL.get()
            )

        this.tag(ItemTags.PICKAXES)
            .add(
                ModItems.SPECTRE_PICKAXE.get()
            )

        this.tag(ItemTags.TRIMMABLE_ARMOR)
            .remove(
                ModItems.SPECTRE_HELMET.get(),
                ModItems.SPECTRE_CHESTPLATE.get(),
                ModItems.SPECTRE_LEGGINGS.get(),
                ModItems.SPECTRE_BOOTS.get(),
                ModItems.WATER_WALKING_BOOTS.get(),
                ModItems.OBSIDIAN_WATER_WALKING_BOOTS.get(),
                ModItems.LAVA_WADERS.get()
            )

        this.tag(ItemTags.HEAD_ARMOR)
            .add(
                ModItems.MAGIC_HOOD.get(),
                ModItems.SPECTRE_HELMET.get()
            )

        this.tag(ItemTags.CHEST_ARMOR)
            .add(
                ModItems.SPECTRE_CHESTPLATE.get()
            )

        this.tag(ItemTags.LEG_ARMOR)
            .add(
                ModItems.SPECTRE_LEGGINGS.get()
            )

        this.tag(ItemTags.FOOT_ARMOR)
            .add(
                ModItems.SPECTRE_BOOTS.get(),
                ModItems.WATER_WALKING_BOOTS.get(),
                ModItems.OBSIDIAN_WATER_WALKING_BOOTS.get(),
                ModItems.LAVA_WADERS.get()
            )

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

    }
}