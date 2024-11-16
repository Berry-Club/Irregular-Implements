package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.client.model.generators.ModelFile
import net.neoforged.neoforge.common.data.ExistingFileHelper

class ModItemModelProvider(
    output: PackOutput,
    existingFileHelper: ExistingFileHelper
) : ItemModelProvider(output, IrregularImplements.ID, existingFileHelper) {

    private val handledItems: MutableSet<Item> = mutableSetOf()

    override fun registerModels() {
        coloredItems()
        handheldItems()
        basicItems()
    }

    private fun basicItems() {
        val complexModels = listOf(
            ModItems.ECLIPSED_CLOCK,
            ModItems.EMERALD_COMPASS,
            ModItems.ENDER_BUCKET,
            ModItems.REINFORCED_ENDER_BUCKET,
            ModItems.REDSTONE_ACTIVATOR,
            ModItems.SOUND_PATTERN,
            ModItems.SOUND_RECORDER,
            ModItems.SPECTRE_CHARGER,
            ModItems.ADVANCED_REDSTONE_TORCH
        )

        for (item in ModItems.ITEM_REGISTRY.entries - complexModels.toSet()) {
            if (item.get() in handledItems) continue

            if (item.get() !is BlockItem) {
                basicItem(item.get())
            }
        }
    }

    private fun handheldItems() {
        val handHeldItems = listOf(
            ModItems.REDSTONE_TOOL
        ).map { it.get() }

        for (item in handHeldItems) {
            val name = getName(item)

            getBuilder(name.toString())
                .parent(ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", "item/${name.path}")

            handledItems.add(item)
        }
    }

    private fun coloredItems() {
        for (color in DyeColor.entries) {

            val grassSeeds = GrassSeedItem.getFromColor(color).get()
            val runeDust = ModItems.getRuneDust(color).get()

            getBuilder(getName(grassSeeds).toString())
                .parent(ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", "item/grass_seeds")
                .element()
                .allFaces { t, u -> u.tintindex(color.id) }
                .end()

            getBuilder(getName(runeDust).toString())
                .parent(ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", "item/rune_dust")
                .element()
                .allFaces { t, u -> u.tintindex(color.id) }
                .end()

            handledItems.add(grassSeeds)
            handledItems.add(runeDust)
        }
    }

    private fun getName(item: Item): ResourceLocation {
        return BuiltInRegistries.ITEM.getKey(item)
    }

}