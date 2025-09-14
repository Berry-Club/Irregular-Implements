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

		addConfig(ClientConfig.BIOME_PAINTER_CORRECT_BIOME_CUBE_SIZE, "Biome Painter Correct Biome Cube Size")
		addConfig(ClientConfig.BIOME_PAINTER_INCORRECT_BIOME_CUBE_SIZE, "Biome Painter Incorrect Biome Cube Size")
		addConfig(ClientConfig.BIOME_PAINTER_CORRECT_BIOME_CUBE_COLOR, "Biome Painter Correct Biome Cube Color")
		addConfig(ClientConfig.BIOME_PAINTER_INCORRECT_BIOME_CUBE_COLOR, "Biome Painter Incorrect Biome Cube Color")
		addConfig(ClientConfig.BIOME_PAINTER_SELECTED_INCORRECT_BIOME_CUBE_COLOR, "Biome Painter Selected Incorrect Biome Cube Color")

		addConfig(ServerConfig.CONFIG.blockDestabilizerLimit, "Block Destabilizer Limit")

		addConfig(ServerConfig.CONFIG.blockMoverTryVaporizeFluid, "Block Mover Vaporizes Fluid")
		addConfig(ServerConfig.CONFIG.portableEnderBridgeRange, "Portable Ender Bridge Range")
		addConfig(ServerConfig.CONFIG.summoningPendulumCapacity, "Summoning Pendulum Capacity")
		addConfig(ServerConfig.CONFIG.blockReplacerUniqueBlocks, "Block Replacer Unique Blocks")
		addConfig(ServerConfig.CONFIG.diviningRodCheckRadius, "Divining Rod Check Radius")
		addConfig(ServerConfig.CONFIG.rainShieldChunkRadius, "Rain Shield Chunk Radius")
		addConfig(ServerConfig.CONFIG.escapeRopeMaxBlocks, "Escape Rope Max Blocks")
		addConfig(ServerConfig.CONFIG.escapeRopeBlocksPerTick, "Escape Rope Blocks Per Tick")

		addConfig(ServerConfig.CONFIG.spectreImbueChance, "Spectre Imbue Chance")

		addConfig(ServerConfig.CONFIG.spectreBufferCapacity, "Spectre Buffer Capacity")
		addConfig(ServerConfig.CONFIG.spectreBasicRate, "Basic Coil Rate")
		addConfig(ServerConfig.CONFIG.spectreRedstoneRate, "Redstone Coil Rate")
		addConfig(ServerConfig.CONFIG.spectreEnderRate, "Ender Coil Rate")
		addConfig(ServerConfig.CONFIG.spectreNumberRate, "Number Coil Rate")
		addConfig(ServerConfig.CONFIG.spectreGenesisRate, "Genesis Coil Rate")

		addConfig(ServerConfig.CONFIG.spectreChargerBasic, "Basic Charger Rate")
		addConfig(ServerConfig.CONFIG.specterChargerRedstone, "Redstone Charger Rate")
		addConfig(ServerConfig.CONFIG.spectreChargerEnder, "Ender Charger Rate")
		addConfig(ServerConfig.CONFIG.spectreChargerGenesis, "Genesis Charger Rate")

		addConfig(ServerConfig.CONFIG.biomePainterRadius, "Biome Painter Radius")
		addConfig(ServerConfig.CONFIG.biomePainterViewHorizontalRadius, "Biome Painter View Horizontal Radius")
		addConfig(ServerConfig.CONFIG.biomePainterViewVerticalRadius, "Biome Painter View Vertical Radius")

		addConfigCategory(ServerConfig.SPECTRE_CATEGORY, "Spectre")
		addConfigCategory(ServerConfig.BIOME_PAINTER_CATEGORY, "Biome Painter")
	}

}