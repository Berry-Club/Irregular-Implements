package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.config.ClientConfig
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue

object ModConfigLang {

	fun add(provider: ModLanguageProvider) {

		fun addConfigCategory(category: String, desc: String) {
			val categoryString = StringBuilder()
				.append(IrregularImplements.ID)
				.append(".configuration.")
				.append(category)
				.toString()

			provider.add(categoryString, desc)
		}

		fun addConfig(config: ConfigValue<*>, desc: String) {
			val configString = StringBuilder()
				.append(IrregularImplements.ID)
				.append(".configuration.")
				.append(config.path.last())
				.toString()

			provider.add(configString, desc)
		}

		addConfig(ClientConfig.COLLAPSE_INVERTS_MOUSE, "Collapse Inverts Mouse")
		addConfig(ClientConfig.HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON, "Hide Recipe Book Button In Custom Crafting Table")

		addConfig(ServerConfig.BLOCK_DESTABILIZER_LIMIT, "Block Destabilizer Limit")

		addConfig(ServerConfig.BLOCK_MOVER_TRY_VAPORIZE_FLUID, "Block Mover Vaporizes Fluid")
		addConfig(ServerConfig.PORTABLE_ENDER_BRIDGE_RANGE, "Portable Ender Bridge Range")
		addConfig(ServerConfig.SUMMONING_PENDULUM_CAPACITY, "Summoning Pendulum Capacity")
		addConfig(ServerConfig.BLOCK_REPLACER_UNIQUE_BLOCKS, "Block Replacer Unique Blocks")
		addConfig(ServerConfig.DIVINING_ROD_CHECK_RADIUS, "Divining Rod Check Radius")
		addConfig(ServerConfig.RAIN_SHIELD_CHUNK_RADIUS, "Rain Shield Chunk Radius")
		addConfig(ServerConfig.ESCAPE_ROPE_MAX_BLOCKS, "Escape Rope Max Blocks")
		addConfig(ServerConfig.ESCAPE_ROPE_BLOCKS_PER_TICK, "Escape Rope Blocks Per Tick")

		addConfig(ServerConfig.SPECTRE_IMBUE_CHANCE, "Spectre Imbue Chance")

		addConfig(ServerConfig.SPECTRE_BUFFER_CAPACITY, "Spectre Buffer Capacity")
		addConfig(ServerConfig.SPECTRE_BASIC_RATE, "Basic Coil Rate")
		addConfig(ServerConfig.SPECTRE_REDSTONE_RATE, "Redstone Coil Rate")
		addConfig(ServerConfig.SPECTRE_ENDER_RATE, "Ender Coil Rate")
		addConfig(ServerConfig.SPECTRE_NUMBER_RATE, "Number Coil Rate")
		addConfig(ServerConfig.SPECTRE_GENESIS_RATE, "Genesis Coil Rate")

		addConfig(ServerConfig.SPECTRE_CHARGER_BASIC, "Basic Charger Rate")
		addConfig(ServerConfig.SPECTRE_CHARGER_REDSTONE, "Redstone Charger Rate")
		addConfig(ServerConfig.SPECTRE_CHARGER_ENDER, "Ender Charger Rate")
		addConfig(ServerConfig.SPECTRE_CHARGER_GENESIS, "Genesis Charger Rate")

		addConfig(ServerConfig.BIOME_PAINTER_RADIUS, "Biome Painter Radius")
		addConfig(ServerConfig.BIOME_PAINTER_VIEW_HORIZONTAL_RADIUS, "Biome Painter View Horizontal Radius")
		addConfig(ServerConfig.BIOME_PAINTER_VIEW_VERTICAL_RADIUS, "Biome Painter View Vertical Radius")

		addConfigCategory(ServerConfig.SPECTRE_CATEGORY, "Spectre")
		addConfigCategory(ServerConfig.BIOME_PAINTER_CATEGORY, "Biome Painter")
	}

}