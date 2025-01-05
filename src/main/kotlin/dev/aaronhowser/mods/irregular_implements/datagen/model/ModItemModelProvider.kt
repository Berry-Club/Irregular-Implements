package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.EmeraldCompassItem
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.item.RedstoneActivatorItem
import dev.aaronhowser.mods.irregular_implements.item.SpectreChargerItem
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
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
        emeraldCompass()
        redstoneActivator()
        diviningRod()
        buckets()
        blockEntityWithoutLevelRenderers()
        spectreChargers()

        basicItems()
    }

    private fun spectreChargers() {

        val glowTexture = modLoc("item/spectre_charger/glow")

        val items = mapOf(
            ModItems.SPECTRE_CHARGER_BASIC.get() to "basic",
            ModItems.SPECTRE_CHARGER_REDSTONE.get() to "redstone",
            ModItems.SPECTRE_CHARGER_ENDER.get() to "ender",
            ModItems.SPECTRE_CHARGER_GENESIS.get() to "genesis"
        )

        for ((item, type) in items) {

            val name = getName(item)
            val baseTexture = modLoc("item/spectre_charger/$type")

            val glowModel = getBuilder("${name}_glow")
                .parent(ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", baseTexture)
                .texture("layer1", glowTexture)

            getBuilder(name.toString())
                .parent(ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", baseTexture)

                .override()
                .predicate(SpectreChargerItem.IS_ENABLED, 1f)
                .model(glowModel)
                .end()

            handledItems.add(item)
        }
    }

    private fun blockEntityWithoutLevelRenderers() {
        val blockEntityWithoutLevelRenderers = listOf(
            ModItems.DIAPHANOUS_BLOCK,
            ModItems.SPECTRE_ILLUMINATOR
        ).map { it.get() }

        for (item in blockEntityWithoutLevelRenderers) {
            val name = getName(item)

            getBuilder(name.toString())
                .parent(ModelFile.UncheckedModelFile("builtin/entity"))

            handledItems.add(item)
        }
    }

    private fun buckets() {
        val enderBucket = ModItems.ENDER_BUCKET.get()

        getBuilder(getName(enderBucket).toString())
            .parent(ModelFile.UncheckedModelFile("item/generated"))
            .texture("layer0", "item/ender_bucket/base")
            .texture("layer1", "item/ender_bucket/fluid")

        val reinforcedEnderBucket = ModItems.REINFORCED_ENDER_BUCKET.get()

        getBuilder(getName(reinforcedEnderBucket).toString())
            .parent(ModelFile.UncheckedModelFile("item/generated"))
            .texture("layer0", "item/reinforced_ender_bucket/base")
            .texture("layer1", "item/reinforced_ender_bucket/fluid")

        handledItems.add(enderBucket)
        handledItems.add(reinforcedEnderBucket)
    }

    private fun diviningRod() {
        val item = ModItems.DIVINING_ROD.get()

        getBuilder(getName(item).toString())
            .parent(ModelFile.UncheckedModelFile("item/handheld"))
            .texture("layer0", "item/divining_rod")
            .texture("layer1", "item/divining_rod_overlay")

        handledItems.add(item)
    }

    private fun redstoneActivator() {
        val item = ModItems.REDSTONE_ACTIVATOR.get()

        val middleModel = getBuilder("${getName(item)}_middle")
            .parent(ModelFile.UncheckedModelFile("item/handheld"))
            .texture("layer0", "item/redstone_activator/middle")

        val rightModel = getBuilder("${getName(item)}_right")
            .parent(ModelFile.UncheckedModelFile("item/handheld"))
            .texture("layer0", "item/redstone_activator/right")

        getBuilder(getName(item).toString())
            .parent(ModelFile.UncheckedModelFile("item/handheld"))
            .texture("layer0", "item/redstone_activator/left")

            .override()
            .predicate(RedstoneActivatorItem.DURATION, RedstoneActivatorItem.MEDIUM.toFloat())
            .model(middleModel)
            .end()

            .override()
            .predicate(RedstoneActivatorItem.DURATION, RedstoneActivatorItem.LONG.toFloat())
            .model(rightModel)
            .end()

        handledItems.add(item)
    }

    private fun emeraldCompass() {
        val item = ModItems.EMERALD_COMPASS.get()

        val baseModel = getBuilder(getName(item).toString())
            .parent(ModelFile.UncheckedModelFile("item/handheld"))
            .texture("layer0", "item/emerald_compass/emerald_compass_00")

        for (i in 0 until 31) {
            val number = i.toString().padStart(2, '0')
            val model = getBuilder("${getName(item)}_$number")
                .parent(baseModel)
                .texture("layer0", "item/emerald_compass/emerald_compass_$number")

            baseModel
                .override()
                .predicate(EmeraldCompassItem.ANGLE, i.toFloat() / 31)
                .model(model)
                .end()
        }

        handledItems.add(item)
    }

    private fun basicItems() {
        val complexModels = listOf(
            ModItems.EMERALD_COMPASS,
            ModItems.ENDER_BUCKET,
            ModItems.REINFORCED_ENDER_BUCKET,
            ModItems.REDSTONE_ACTIVATOR,
            ModItems.SOUND_PATTERN,
            ModItems.SOUND_RECORDER,
            ModItems.SPECTRE_CHARGER_BASIC,
            ModItems.ADVANCED_REDSTONE_TORCH,
            ModItems.DIVINING_ROD
        )

        val blockItemsToModel = listOf(
            ModItems.LOTUS_SEEDS,
            ModItems.BEAN,
            ModItems.LESSER_MAGIC_BEAN,
            ModItems.MAGIC_BEAN
        ).map { it.get() }

        for (deferred in ModItems.ITEM_REGISTRY.entries - complexModels.toSet()) {
            val item = deferred.get()
            if (item in handledItems) continue

            if (item !is BlockItem || item in blockItemsToModel) {
                basicItem(item)
            }
        }
    }

    private fun handheldItems() {
        val handHeldItems = listOf(
            ModItems.REDSTONE_TOOL,
            ModItems.SPECTRE_PICKAXE,
            ModItems.SPECTRE_AXE,
            ModItems.SPECTRE_SWORD,
            ModItems.SPECTRE_SHOVEL
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

            getBuilder(getName(grassSeeds).toString())
                .parent(ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", "item/grass_seeds")
                .element()
                .allFaces { t, u -> u.tintindex(color.id) }
                .end()

            handledItems.add(grassSeeds)
        }
    }

    private fun getName(item: Item): ResourceLocation {
        return BuiltInRegistries.ITEM.getKey(item)
    }

}