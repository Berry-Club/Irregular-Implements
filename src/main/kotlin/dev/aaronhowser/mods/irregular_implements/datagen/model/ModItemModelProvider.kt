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
            ModItems.FIRE_IMBUE,
            ModItems.POISON_IMBUE,
            ModItems.WITHER_IMBUE,
            ModItems.EXPERIENCE_IMBUE,
            ModItems.WEATHER_EGG,
            ModItems.REDSTONE_ACTIVATOR,
            ModItems.SOUND_PATTERN,
            ModItems.SOUND_RECORDER,
            ModItems.SPECTRE_CHARGER,
            ModItems.ADVANCED_REDSTONE_TORCH
        )

        for (item in ModItems.ITEM_REGISTRY.entries - complexModels.toSet()) {
            if (item.get() !is BlockItem) {
                basicItem(item.get())
            }
        }

    }

}