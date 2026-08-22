package dev.aaronhowser.mods.irregular_implements.config

import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class StartupConfig(
	private val builder: ModConfigSpec.Builder
) {

	lateinit var peaceCandleVillageTemplatePools: ModConfigSpec.ConfigValue<List<String>>

	init {
		peaceCandleVillageGeneration()

		builder.build()
	}

	private fun peaceCandleVillageGeneration() {
		peaceCandleVillageTemplatePools = builder
			.comment("The Village Template Pools whose Brewing Stands may be replaced with Peace Candles. Leave empty to disable generation.")
			.defineListAllowEmpty(
				"peaceCandleVillageTemplatePools",
				listOf(
					"minecraft:village/plains/houses",
					"minecraft:village/desert/houses",
					"minecraft:village/savanna/houses",
					"minecraft:village/snowy/houses",
					"minecraft:village/taiga/houses"
				),
				{ "minecraft:village/" },
				::isResourceLocation
			)
	}

	companion object {
		private val configPair: Pair<StartupConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::StartupConfig)

		@JvmField
		val CONFIG: StartupConfig = configPair.left
		val CONFIG_SPEC: ModConfigSpec = configPair.right

		private fun isResourceLocation(value: Any): Boolean {
			return value is String && ResourceLocation.tryParse(value) != null
		}
	}

}