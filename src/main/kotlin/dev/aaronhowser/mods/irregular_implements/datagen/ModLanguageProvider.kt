package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.config.ClientConfig
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.language.*
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.ChatFormatting
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue
import net.neoforged.neoforge.common.data.LanguageProvider

class ModLanguageProvider(
	output: PackOutput
) : LanguageProvider(output, IrregularImplements.ID, "en_us") {

	override fun addTranslations() {
		ModTooltipLang.add(this)
		ModItemLang.add(this)
		ModBlockLang.add(this)
		ModInfoLang.add(this)
		ModEffectLang.add(this)
		ModSubtitleLang.add(this)
		ModMessageLang.add(this)
		addMisc()
		addConfigs()
		addEntityTypes()
	}

	private fun addEntityTypes() {
		addEntityType(ModEntityTypes.SPECTRE_ILLUMINATOR, "Spectre Illuminator")
		addEntityType(ModEntityTypes.INDICATOR_DISPLAY, "Indicator Display")
		addEntityType(ModEntityTypes.ARTIFICIAL_END_PORTAL, "Artificial End Portal")
		addEntityType(ModEntityTypes.GOLDEN_EGG, "Golden Egg")
		addEntityType(ModEntityTypes.GOLDEN_CHICKEN, "Golden Chicken")
	}

	private fun addConfig(config: ConfigValue<*>, desc: String) {
		val configString = StringBuilder()
			.append(IrregularImplements.ID)
			.append(".configuration.")
			.append(config.path.last())
			.toString()

		add(configString, desc)
	}

	private fun addConfigCategory(category: String, desc: String) {
		val categoryString = StringBuilder()
			.append(IrregularImplements.ID)
			.append(".configuration.")
			.append(category)
			.toString()

		add(categoryString, desc)
	}

	private fun addConfigs() {
		addConfig(ClientConfig.COLLAPSE_INVERTS_MOUSE, "Collapse Inverts Mouse")
		addConfig(ClientConfig.HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON, "Hide Recipe Book Button In Custom Crafting Table")

		addConfig(ServerConfig.BLOCK_DESTABILIZER_LIMIT, "Block Destabilizer Limit")
		addConfig(ServerConfig.BIOME_PAINTER_HORIZONTAL_RADIUS, "Biome Painter Horizontal Radius")
		addConfig(ServerConfig.BIOME_PAINTER_BLOCKS_ABOVE, "Biome Painter Blocks Above")
		addConfig(ServerConfig.BIOME_PAINTER_BLOCKS_BELOW, "Biome Painter Blocks Below")

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

		addConfigCategory(ServerConfig.SPECTRE_CATEGORY, "Spectre")
	}

	object Misc {
		const val MAGNETIC_NAME = "enchantment.irregular_implements.magnetic"
		const val MAGNETIC_DESC = "enchantment.irregular_implements.magnetic.desc"

		const val MAGNETIC_TAG = "tag.item.irregular_implements.enchantable.magnetic"
		const val GRASS_SEEDS_TAG = "tag.item.irregular_implements.grass_seeds"
		const val HIDE_NAME_HELMET_TAG = "tag.item.irregular_implements.hide_name_helmet"
		const val HIDE_POTION_HELMET_TAG = "tag.item.irregular_implements.hide_potion_helmet"
		const val SUPER_LUBRICATED_TAG = "tag.item.irregular_implements.super_lubricated"
		const val CUSTOM_CRAFTING_TABLE_ITEMS_TAG = "tag.item.irregular_implements.custom_crafting_table_items"
		const val SPECTRE_ANCHOR_BLACKLIST_TAG = "tag.item.irregular_implements.spectre_anchor_blacklist"

		const val C_BEAN_TAG = "tag.item.c.crops.bean"
		const val C_OBSIDIAN_RODS_TAG = "tag.item.c.rods.obsidian"

		const val ALLOWS_LAVA_WALKING = "tag.fluid.irregular_implements.allows_lava_walking"
		const val ALLOWS_WATER_WALKING = "tag.fluid.irregular_implements.allows_water_walking"
	}

	private fun addMisc() {
		add(Misc.MAGNETIC_NAME, "Magnetic")
		add(Misc.MAGNETIC_DESC, "Teleports fresh item drops towards you")

		add(Misc.MAGNETIC_TAG, "Magnetic Enchantable")
		add(Misc.GRASS_SEEDS_TAG, "Grass Seeds")
		add(Misc.HIDE_NAME_HELMET_TAG, "Hides Name Helmet")
		add(Misc.HIDE_POTION_HELMET_TAG, "Hides Potion Helmet")
		add(Misc.SUPER_LUBRICATED_TAG, "Super Lubricated Boots")
		add(Misc.CUSTOM_CRAFTING_TABLE_ITEMS_TAG, "Custom Crafting Table Items")
		add(Misc.SPECTRE_ANCHOR_BLACKLIST_TAG, "Spectre Anchor Blacklist")

		add(Misc.C_BEAN_TAG, "Beans")
		add(Misc.C_OBSIDIAN_RODS_TAG, "Obsidian Rods")

		add(Misc.ALLOWS_WATER_WALKING, "Allows Water Walking")
		add(Misc.ALLOWS_LAVA_WALKING, "Allows Lava Walking")
	}

	companion object {
		fun String.toComponent(vararg args: Any?): MutableComponent = Component.translatable(this, *args)
		fun String.toGrayComponent(vararg args: Any?): MutableComponent = Component.translatable(this, *args).withStyle(ChatFormatting.GRAY)

		fun getInfoString(itemLike: ItemLike): String {
			val location = BuiltInRegistries.ITEM.getKey(itemLike.asItem())

			return StringBuilder()
				.append("info.")
				.append(location.namespace)
				.append(".")
				.append(location.path)
				.toString()
		}
	}

}