package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.*
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
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
) : ItemModelProvider(output, IrregularImplements.MOD_ID, existingFileHelper) {

	private val handledItems: MutableSet<Item> = mutableSetOf()

	override fun registerModels() {
		coloredItems()
		handheldItems()
		emeraldCompass()
		goldenCompass()
		redstoneActivator()
		diviningRod()
		buckets()
		blockEntityWithoutLevelRenderers()
		spectreChargers()
		weatherEggs()
		specialChests()
		advancedRedstoneTorch()

		basicItems()
	}

	private fun advancedRedstoneTorch() {
		val item = ModItems.ADVANCED_REDSTONE_TORCH.get()

		val texture = modLoc("block/advanced_redstone_torch/red")

		getBuilder(getName(item).toString())
			.parent(ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", texture)

		handledItems.add(item)
	}

	private fun specialChests() {
		val blockItems = listOf(
			ModBlocks.WATER_CHEST.asItem(),
			ModBlocks.NATURE_CHEST.asItem()
		)

		for (item in blockItems) {
			getBuilder(getName(item).toString())
				.parent(ModelFile.UncheckedModelFile("item/chest"))

			handledItems.add(item)
		}
	}

	private fun weatherEggs() {
		val item = ModItems.WEATHER_EGG.get()

		val sunnyModel = getBuilder("${getName(item)}_sunny")
			.parent(ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", "item/weather_egg/sunny")

		val rainyModel = getBuilder("${getName(item)}_rainy")
			.parent(ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", "item/weather_egg/rainy")

		val stormyModel = getBuilder("${getName(item)}_stormy")
			.parent(ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", "item/weather_egg/stormy")

		getBuilder(getName(item).toString())

			.override()
			.predicate(WeatherEggItem.WEATHER, 0f)
			.model(sunnyModel)
			.end()

			.override()
			.predicate(WeatherEggItem.WEATHER, 1f)
			.model(rainyModel)
			.end()

			.override()
			.predicate(WeatherEggItem.WEATHER, 2f)
			.model(stormyModel)
			.end()

		handledItems.add(item)
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
			ModItems.DIAPHANOUS_BLOCK.get(),
			ModItems.SPECTRE_ILLUMINATOR.get(),
			ModItems.CUSTOM_CRAFTING_TABLE.get()
		)

		for (item in blockEntityWithoutLevelRenderers) {
			val name = getName(item)

			getBuilder(name.toString())
				.parent(ModelFile.UncheckedModelFile("builtin/entity"))

			handledItems.add(item)
		}
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
			val wrapped = (i + 16) % 32

			val number = wrapped.toString().padStart(2, '0')
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

	private fun goldenCompass() {
		val item = ModItems.GOLDEN_COMPASS.get()

		val baseModel = getBuilder(getName(item).toString())
			.parent(ModelFile.UncheckedModelFile("item/handheld"))
			.texture("layer0", "item/golden_compass/golden_compass_00")

		for (i in 0 until 31) {
			val wrapped = (i + 16) % 32

			val number = wrapped.toString().padStart(2, '0')
			val model = getBuilder("${getName(item)}_$number")
				.parent(baseModel)
				.texture("layer0", "item/golden_compass/golden_compass_$number")

			baseModel
				.override()
				.predicate(GoldenCompassItem.ANGLE, i.toFloat() / 31)
				.model(model)
				.end()
		}

		handledItems.add(item)
	}

	private fun buckets() {
		val enderBucket = ModItems.ENDER_BUCKET.get()
		val enderBucketName = getName(enderBucket).toString()

		val enderBaseModel = getBuilder(enderBucketName)
			.parent(ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", "item/ender_bucket/base")

		val enderFluidModel = getBuilder(enderBucketName + "_fluid")
			.parent(enderBaseModel)
			.texture("layer1", "item/ender_bucket/fluid")

		enderBaseModel
			.override()
			.predicate(EnderBucketItem.HAS_FLUID, 1f)
			.model(enderFluidModel)
			.end()

		val reinforcedEnderBucket = ModItems.REINFORCED_ENDER_BUCKET.get()
		val reinforcedEnderBucketName = getName(reinforcedEnderBucket).toString()

		val reinforcedBaseModel = getBuilder(reinforcedEnderBucketName)
			.parent(ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", "item/reinforced_ender_bucket/base")

		val reinforcedFluidModel = getBuilder(reinforcedEnderBucketName + "_fluid")
			.parent(reinforcedBaseModel)
			.texture("layer1", "item/reinforced_ender_bucket/fluid")

		reinforcedBaseModel
			.override()
			.predicate(EnderBucketItem.HAS_FLUID, 1f)
			.model(reinforcedFluidModel)
			.end()

		handledItems.add(enderBucket)
		handledItems.add(reinforcedEnderBucket)
	}

	private fun basicItems() {
		val complexModels = listOf(
			ModItems.EMERALD_COMPASS,
			ModItems.ENDER_BUCKET,
			ModItems.REINFORCED_ENDER_BUCKET,
			ModItems.REDSTONE_ACTIVATOR,
			ModItems.SPECTRE_CHARGER_BASIC,
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

			val grassSeeds = GrassSeedItem.getFromColor(color)?.get() ?: continue

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