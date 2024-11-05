package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import net.minecraft.data.PackOutput
import net.minecraft.world.item.BlockItem
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class ModItemModelProvider(
    output: PackOutput,
    existingFileHelper: ExistingFileHelper
) : ItemModelProvider(output, IrregularImplements.ID, existingFileHelper) {

    override fun registerModels() {

        val complexModels = listOf(
            ModItems.ECLIPSED_CLOCK,
            ModItems.EMERALD_COMPASS,
            ModItems.ENDER_BUCKET,
            ModItems.REINFORCED_ENDER_BUCKET,
            ModItems.WEATHER_EGG,
            ModItems.REDSTONE_ACTIVATOR,
            ModItems.SOUND_PATTERN,
            ModItems.SOUND_RECORDER,
            ModItems.SPECTRE_CHARGER,
            ModItems.ADVANCED_REDSTONE_TORCH,
            ModItems.GRASS_SEEDS_WHITE,
            ModItems.GRASS_SEEDS_ORANGE,
            ModItems.GRASS_SEEDS_MAGENTA,
            ModItems.GRASS_SEEDS_LIGHT_BLUE,
            ModItems.GRASS_SEEDS_YELLOW,
            ModItems.GRASS_SEEDS_LIME,
            ModItems.GRASS_SEEDS_PINK,
            ModItems.GRASS_SEEDS_GRAY,
            ModItems.GRASS_SEEDS_LIGHT_GRAY,
            ModItems.GRASS_SEEDS_CYAN,
            ModItems.GRASS_SEEDS_PURPLE,
            ModItems.GRASS_SEEDS_BLUE,
            ModItems.GRASS_SEEDS_BROWN,
            ModItems.GRASS_SEEDS_GREEN,
            ModItems.GRASS_SEEDS_RED,
            ModItems.GRASS_SEEDS_BLACK,
            ModItems.RUNE_DUST_WHITE,
            ModItems.RUNE_DUST_ORANGE,
            ModItems.RUNE_DUST_MAGENTA,
            ModItems.RUNE_DUST_LIGHT_BLUE,
            ModItems.RUNE_DUST_YELLOW,
            ModItems.RUNE_DUST_LIME,
            ModItems.RUNE_DUST_PINK,
            ModItems.RUNE_DUST_GRAY,
            ModItems.RUNE_DUST_LIGHT_GRAY,
            ModItems.RUNE_DUST_CYAN,
            ModItems.RUNE_DUST_PURPLE,
            ModItems.RUNE_DUST_BLUE,
            ModItems.RUNE_DUST_BROWN,
            ModItems.RUNE_DUST_GREEN,
            ModItems.RUNE_DUST_RED,
            ModItems.RUNE_DUST_BLACK,
        )

        for (item in ModItems.ITEM_REGISTRY.entries - complexModels.toSet()) {
            if (item.get() !is BlockItem) {
                basicItem(item.get())
            }
        }

    }

}