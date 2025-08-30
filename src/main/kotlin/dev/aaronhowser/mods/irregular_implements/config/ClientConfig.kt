package dev.aaronhowser.mods.irregular_implements.config

import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class ClientConfig(
	private val builder: ModConfigSpec.Builder
) {

	init {
		generalClientConfigs()
		biomePainter()
		builder.build()
	}

	private fun generalClientConfigs() {
		COLLAPSE_INVERTS_MOUSE = builder
			.comment("Should the Collapse Imbue invert the player's mouse sensitivity?")
			.define("collapseInvertsMouse", true)

		HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON = builder
			.comment("Should the recipe book button be hidden in the Custom Crafting Table screen?")
			.define("hideCustomCraftingTableRecipeBookButton", false)
	}

	private fun biomePainter() {
		builder.push(ServerConfig.BIOME_PAINTER_CATEGORY)

		BIOME_PAINTER_CORRECT_BIOME_CUBE_SIZE = builder
			.comment("The size of the cube shown on correct biome positions.")
			.defineInRange("biomePainterCorrectBiomeCubeSize", 0.05, 0.01, 1.0)

		BIOME_PAINTER_INCORRECT_BIOME_CUBE_SIZE = builder
			.comment("The size of the cube shown on incorrect biome positions.")
			.defineInRange("biomePainterIncorrectBiomeCubeSize", 0.35, 0.01, 1.0)

		BIOME_PAINTER_CORRECT_BIOME_CUBE_COLOR = builder
			.comment("The color of the cube shown on correct biome positions.")
			.defineInRange("biomePainterCorrectBiomeCubeColor", 0x6622AA00, 0, Int.MAX_VALUE)

		BIOME_PAINTER_INCORRECT_BIOME_CUBE_COLOR = builder
			.comment("The color of the cube shown on incorrect biome positions.")
			.defineInRange("biomePainterIncorrectBiomeCubeColor", 0x66AA2200, 0, Int.MAX_VALUE)

		BIOME_PAINTER_SELECTED_INCORRECT_BIOME_CUBE_COLOR = builder
			.comment("The color of the cube shown on the selected incorrect biome position.")
			.defineInRange("biomePainterSelectedIncorrectBiomeCubeColor", 0x662222AA, 0, Int.MAX_VALUE)

		builder.pop()
	}

	companion object {
		private val configPair: Pair<ClientConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::ClientConfig)

		val CONFIG: ClientConfig = configPair.left
		val CONFIG_SPEC: ModConfigSpec = configPair.right

		lateinit var COLLAPSE_INVERTS_MOUSE: ModConfigSpec.BooleanValue
		lateinit var HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON: ModConfigSpec.BooleanValue

		lateinit var BIOME_PAINTER_CORRECT_BIOME_CUBE_SIZE: ModConfigSpec.DoubleValue
		lateinit var BIOME_PAINTER_INCORRECT_BIOME_CUBE_SIZE: ModConfigSpec.DoubleValue

		lateinit var BIOME_PAINTER_CORRECT_BIOME_CUBE_COLOR: ModConfigSpec.IntValue
		lateinit var BIOME_PAINTER_INCORRECT_BIOME_CUBE_COLOR: ModConfigSpec.IntValue
		lateinit var BIOME_PAINTER_SELECTED_INCORRECT_BIOME_CUBE_COLOR: ModConfigSpec.IntValue
	}

}