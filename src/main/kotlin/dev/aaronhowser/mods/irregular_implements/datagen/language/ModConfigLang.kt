package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider

object ModConfigLang {

	private fun configCategoryKey(categoryName: String) = "${IrregularImplements.ID}.configuration.$categoryName"
	private fun configKey(configName: String) = "${IrregularImplements.ID}.configuration.$configName"

	object ClientLang {
		val COLLAPSE_INVERTS_MOUSE = configKey("collapse_inverts_mouse")
		val HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON = configKey("hide_custom_crafting_table_recipe_book_button")
	}

	object ServerLang {
		val BLOCK_DESTABILIZER_LIMIT = configKey("block_destabilizer_limit")
		val BIOME_PAINTER_HORIZONTAL_RADIUS = configKey("biome_painter_horizontal_radius")
		val BIOME_PAINTER_BLOCKS_ABOVE = configKey("biome_painter_blocks_above")
		val BIOME_PAINTER_BLOCKS_BELOW = configKey("biome_painter_blocks_below")
		val BLOCK_MOVER_TRY_VAPORIZE_FLUID = configKey("block_mover_try_vaporize_fluid")
		val PORTABLE_ENDER_BRIDGE_RANGE = configKey("portable_ender_bridge_range")
		val SUMMONING_PENDULUM_CAPACITY = configKey("summoning_pendulum_capacity")
		val BLOCK_REPLACER_UNIQUE_BLOCKS = configKey("block_replacer_unique_blocks")
		val DIVINING_ROD_CHECK_RADIUS = configKey("divining_rod_check_radius")
		val RAIN_SHIELD_CHUNK_RADIUS = configKey("rain_shield_chunk_radius")
		val ESCAPE_ROPE_MAX_BLOCKS = configKey("escape_rope_max_blocks")
		val ESCAPE_ROPE_BLOCKS_PER_TICK = configKey("escape_rope_blocks_per_tick")
		val SPECTRE_IMBUE_CHANCE = configKey("spectre_imbue_chance")
		val SPECTRE_BUFFER_CAPACITY = configKey("spectre_buffer_capacity")
		val SPECTRE_BASIC_RATE = configKey("spectre_basic_rate")
		val SPECTRE_REDSTONE_RATE = configKey("spectre_redstone_rate")
		val SPECTRE_ENDER_RATE = configKey("spectre_ender_rate")
		val SPECTRE_NUMBER_RATE = configKey("spectre_number_rate")
		val SPECTRE_GENESIS_RATE = configKey("spectre_genesis_rate")
		val SPECTRE_CHARGER_BASIC = configKey("spectre_charger_basic")
		val SPECTRE_CHARGER_REDSTONE = configKey("spectre_charger_redstone")
		val SPECTRE_CHARGER_ENDER = configKey("spectre_charger_ender")
		val SPECTRE_CHARGER_GENESIS = configKey("spectre_charger_genesis")

		val SPECTRE_CATEGORY = configCategoryKey("spectre_category")
	}

	fun add(provider: ModLanguageProvider) {

		provider.add(ClientLang.COLLAPSE_INVERTS_MOUSE, "Collapse Inverts Mouse")
		provider.add(ClientLang.HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON, "Hide Recipe Book Button In Custom Crafting Table")

		provider.add(ServerLang.BLOCK_DESTABILIZER_LIMIT, "Block Destabilizer Limit")
		provider.add(ServerLang.BIOME_PAINTER_HORIZONTAL_RADIUS, "Biome Painter Horizontal Radius")
		provider.add(ServerLang.BIOME_PAINTER_BLOCKS_ABOVE, "Biome Painter Blocks Above")
		provider.add(ServerLang.BIOME_PAINTER_BLOCKS_BELOW, "Biome Painter Blocks Below")
		provider.add(ServerLang.BLOCK_MOVER_TRY_VAPORIZE_FLUID, "Block Mover Vaporizes Fluid")
		provider.add(ServerLang.PORTABLE_ENDER_BRIDGE_RANGE, "Portable Ender Bridge Range")
		provider.add(ServerLang.SUMMONING_PENDULUM_CAPACITY, "Summoning Pendulum Capacity")
		provider.add(ServerLang.BLOCK_REPLACER_UNIQUE_BLOCKS, "Block Replacer Unique Blocks")
		provider.add(ServerLang.DIVINING_ROD_CHECK_RADIUS, "Divining Rod Check Radius")
		provider.add(ServerLang.RAIN_SHIELD_CHUNK_RADIUS, "Rain Shield Chunk Radius")
		provider.add(ServerLang.ESCAPE_ROPE_MAX_BLOCKS, "Escape Rope Max Blocks")
		provider.add(ServerLang.ESCAPE_ROPE_BLOCKS_PER_TICK, "Escape Rope Blocks Per Tick")
		provider.add(ServerLang.SPECTRE_IMBUE_CHANCE, "Spectre Imbue Chance")
		provider.add(ServerLang.SPECTRE_BUFFER_CAPACITY, "Spectre Buffer Capacity")
		provider.add(ServerLang.SPECTRE_BASIC_RATE, "Basic Coil Rate")
		provider.add(ServerLang.SPECTRE_REDSTONE_RATE, "Redstone Coil Rate")
		provider.add(ServerLang.SPECTRE_ENDER_RATE, "Ender Coil Rate")
		provider.add(ServerLang.SPECTRE_NUMBER_RATE, "Number Coil Rate")
		provider.add(ServerLang.SPECTRE_GENESIS_RATE, "Genesis Coil Rate")
		provider.add(ServerLang.SPECTRE_CHARGER_BASIC, "Basic Charger Rate")
		provider.add(ServerLang.SPECTRE_CHARGER_REDSTONE, "Redstone Charger Rate")
		provider.add(ServerLang.SPECTRE_CHARGER_ENDER, "Ender Charger Rate")
		provider.add(ServerLang.SPECTRE_CHARGER_GENESIS, "Genesis Charger Rate")

		provider.add(ServerLang.SPECTRE_CATEGORY, "Spectre")
	}

}