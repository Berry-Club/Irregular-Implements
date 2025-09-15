package dev.aaronhowser.mods.irregular_implements.config

import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class ClientConfig(
	private val builder: ModConfigSpec.Builder
) {

	lateinit var collapseInvertsMouse: ModConfigSpec.BooleanValue
	lateinit var hideCustomCraftingTableRecipeButton: ModConfigSpec.BooleanValue

	lateinit var biomePainterCorrectBiomeCubeSize: ModConfigSpec.DoubleValue
	lateinit var biomePainterIncorrectBiomeCubeSize: ModConfigSpec.DoubleValue

	lateinit var biomePainterCorrectBiomeCubeColor: ModConfigSpec.IntValue
	lateinit var biomePainterIncorrectBiomeCubeColor: ModConfigSpec.IntValue
	lateinit var biomePainterSelectedIncorrectBiomeCubeColor: ModConfigSpec.IntValue

	init {
		generalClientConfigs()
		biomePainter()
		builder.build()
	}

	private fun generalClientConfigs() {
		collapseInvertsMouse = builder
			.comment("Should the Collapse Imbue invert the player's mouse sensitivity?")
			.define("collapseInvertsMouse", true)

		hideCustomCraftingTableRecipeButton = builder
			.comment("Should the recipe book button be hidden in the Custom Crafting Table screen?")
			.define("hideCustomCraftingTableRecipeBookButton", false)
	}

	private fun biomePainter() {
		builder.push(ServerConfig.BIOME_PAINTER_CATEGORY)

		biomePainterCorrectBiomeCubeSize = builder
			.comment("The size of the cube shown on correct biome positions.")
			.defineInRange("biomePainterCorrectBiomeCubeSize", 0.05, 0.01, 1.0)

		biomePainterIncorrectBiomeCubeSize = builder
			.comment("The size of the cube shown on incorrect biome positions.")
			.defineInRange("biomePainterIncorrectBiomeCubeSize", 0.35, 0.01, 1.0)

		biomePainterCorrectBiomeCubeColor = builder
			.comment("The color of the cube shown on correct biome positions.")
			.defineInRange("biomePainterCorrectBiomeCubeColor", 0x6622AA00, 0, Int.MAX_VALUE)

		biomePainterIncorrectBiomeCubeColor = builder
			.comment("The color of the cube shown on incorrect biome positions.")
			.defineInRange("biomePainterIncorrectBiomeCubeColor", 0x66AA2200, 0, Int.MAX_VALUE)

		biomePainterSelectedIncorrectBiomeCubeColor = builder
			.comment("The color of the cube shown on the selected incorrect biome position.")
			.defineInRange("biomePainterSelectedIncorrectBiomeCubeColor", 0x662222AA, 0, Int.MAX_VALUE)

		builder.pop()
	}

	companion object {
		private val configPair: Pair<ClientConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::ClientConfig)

		@JvmField
		val CONFIG: ClientConfig = configPair.left
		val CONFIG_SPEC: ModConfigSpec = configPair.right
	}

}