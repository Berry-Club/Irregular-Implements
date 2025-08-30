package dev.aaronhowser.mods.irregular_implements.config

import dev.aaronhowser.mods.irregular_implements.datagen.language.ModConfigLang
import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class ClientConfig(
	private val builder: ModConfigSpec.Builder
) {

	init {
		clientConfigs()
		builder.build()
	}

	private fun clientConfigs() {
		COLLAPSE_INVERTS_MOUSE = builder
			.comment("Should the Collapse Imbue invert the player's mouse sensitivity?")
			.translation(ModConfigLang.ClientLang.COLLAPSE_INVERTS_MOUSE)
			.define("collapseInvertsMouse", true)

		HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON = builder
			.comment("Should the recipe book button be hidden in the Custom Crafting Table screen?")
			.translation(ModConfigLang.ClientLang.HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON)
			.define("hideCustomCraftingTableRecipeBookButton", false)
	}

	companion object {
		private val configPair: Pair<ClientConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::ClientConfig)

		val CONFIG: ClientConfig = configPair.left
		val CONFIG_SPEC: ModConfigSpec = configPair.right

		lateinit var COLLAPSE_INVERTS_MOUSE: ModConfigSpec.BooleanValue
		lateinit var HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON: ModConfigSpec.BooleanValue
	}

}