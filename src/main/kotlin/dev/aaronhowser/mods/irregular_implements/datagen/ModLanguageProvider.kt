package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.config.ClientConfig
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModBlockLang
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModInfoLang
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModItemLang
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
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
		addEffects()
		addSubtitles()
		addMessages()
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

	object Messages {
		const val ENDER_BRIDGE_ITERATIONS = "message.irregular_implements.ender_bridge.iterations"
		const val ENDER_BRIDGE_HIT_BLOCK = "message.irregular_implements.ender_bridge.hit_block"
		const val REDSTONE_TOOL_BASE_SET = "message.irregular_implements.redstone_tool.base_block_set"
		const val REDSTONE_TOOL_INVALID_BASE_BLOCK = "message.irregular_implements.redstone_tool.no_base_block"
		const val REDSTONE_TOOL_WRONG_DIMENSION = "message.irregular_implements.redstone_tool.wrong_dimension"
		const val REDSTONE_TOOL_UNLOADED = "message.irregular_implements.redstone_tool.unloaded"
		const val REDSTONE_TOOL_BASE_NOT_LINKABLE = "message.irregular_implements.redstone_tool.base_not_linkable"
		const val REDSTONE_TOOL_LINKED = "message.irregular_implements.redstone_tool.linked"
		const val FLUID_FALL_DEATH_BOOT = "death.fell.accident.fluid_fall.boot"
		const val FLUID_FALL_DEATH_GENERIC = "death.fell.accident.fluid_fall.generic"
		const val ILLUMINATOR_ALREADY_PRESENT = "message.irregular_implements.spectre_illuminator.already_present"
		const val FE_RATIO = "message.irregular_implements.fe_ratio"
		const val ADVANCED_ITEM_COLLECTOR_X_RADIUS = "message.irregular_implements.advanced_item_collector.x_radius"
		const val ADVANCED_ITEM_COLLECTOR_Y_RADIUS = "message.irregular_implements.advanced_item_collector.y_radius"
		const val ADVANCED_ITEM_COLLECTOR_Z_RADIUS = "message.irregular_implements.advanced_item_collector.z_radius"
		const val ENDER_MAILBOX_NOT_OWNER = "message.irregular_implements.ender_mailbox.not_owner"
		const val ENDER_LETTER_EMPTY = "message.irregular_implements.ender_letter.empty"
		const val ENDER_LETTER_ALREADY_SENT = "message.irregular_implements.ender_letter.already_sent"
		const val ENDER_LETTER_NO_RECIPIENT = "message.irregular_implements.ender_letter.no_recipient"
		const val ENDER_LETTER_RECIPIENT_NOT_ONLINE = "message.irregular_implements.ender_letter.recipient_not_online"
		const val ENDER_LETTER_RECIPIENT_NO_ROOM = "message.irregular_implements.ender_letter.recipient_no_room"
		const val FIREPLACE_NO_NAME = "message.irregular_implements.floo_brick.no_name"
		const val FIREPLACE_NAME = "message.irregular_implements.floo_brick.name"
		const val FIREPLACE_BROKEN = "message.irregular_implements.floo_brick.broken"
		const val COMMAND_LEVEL_NOT_FOUND = "message.irregular_implements.command.level_not_found"
		const val FIREPLACE_NOT_FOUND = "message.irregular_implements.floo_brick.not_found"
		const val FIREPLACES_IN_DIMENSION = "message.irregular_implements.floo_brick.fireplaces_in_dimension"
		const val FIREPLACE_LIST_ENTRY = "message.irregular_implements.floo_brick.list_entry"
		const val FIREPLACE_ALREADY_AT = "message.irregular_implements.floo_brick.already_at"
		const val FIREPLACE_NO_LONGER_VALID = "message.irregular_implements.floo_brick.no_longer_valid"
		const val FIREPLACE_TELEPORTED = "message.irregular_implements.floo_brick.teleported"
	}

	private fun addMessages() {
		add(Messages.ENDER_BRIDGE_ITERATIONS, "Ender Bridge stopped searching after %d blocks.")
		add(Messages.ENDER_BRIDGE_HIT_BLOCK, "Ender Bridge stopped searching because it hit a %s at %d %d %d.")
		add(Messages.REDSTONE_TOOL_BASE_SET, "Redstone Tool linked to the %s at %d %d %d.")
		add(Messages.REDSTONE_TOOL_INVALID_BASE_BLOCK, "Cannot link as no base block is set.")
		add(Messages.REDSTONE_TOOL_WRONG_DIMENSION, "Cannot link as base %s is in a different dimension.")
		add(Messages.REDSTONE_TOOL_UNLOADED, "Cannot link as base %s is in an unloaded chunk.")
		add(Messages.REDSTONE_TOOL_BASE_NOT_LINKABLE, "Cannot link as base %s was replaced with a %s.")
		add(Messages.REDSTONE_TOOL_LINKED, "Linked the %s at %d %d %d to the %s at %d %d %d.")
		add(Messages.FLUID_FALL_DEATH_BOOT, "%s splattered against the surface of %s because they were wearing %s")
		add(Messages.FLUID_FALL_DEATH_GENERIC, "%s splattered against the surface of %s because they could walk on it")
		add(Messages.ILLUMINATOR_ALREADY_PRESENT, "This chunk already has a Spectre Illuminator!")
		add(Messages.FE_RATIO, "%s FE / %s FE")
		add(Messages.ADVANCED_ITEM_COLLECTOR_X_RADIUS, "X Radius: %d")
		add(Messages.ADVANCED_ITEM_COLLECTOR_Y_RADIUS, "Y Radius: %d")
		add(Messages.ADVANCED_ITEM_COLLECTOR_Z_RADIUS, "Z Radius: %d")
		add(Messages.ENDER_MAILBOX_NOT_OWNER, "You are not the owner of this Ender Mailbox.")
		add(Messages.ENDER_LETTER_EMPTY, "Your letter is empty!")
		add(Messages.ENDER_LETTER_ALREADY_SENT, "This letter has already been sent! You can't send it again.")
		add(Messages.ENDER_LETTER_NO_RECIPIENT, "This letter has no recipient")
		add(Messages.ENDER_LETTER_RECIPIENT_NOT_ONLINE, "%s of this letter is not online")
		add(Messages.ENDER_LETTER_RECIPIENT_NO_ROOM, "%s has no room for your letter")
		add(Messages.FIREPLACE_NO_NAME, "Nameless fireplace")
		add(Messages.FIREPLACE_NAME, "Fireplace: %s")
		add(Messages.FIREPLACE_BROKEN, "Bugged fireplace; please break and replace it.")
		add(Messages.COMMAND_LEVEL_NOT_FOUND, "Could not find level \"%s\"")
		add(Messages.FIREPLACE_NOT_FOUND, "Could not find fireplace named \"%s\"")
		add(Messages.FIREPLACES_IN_DIMENSION, "Fireplaces in %s: %d")
		add(Messages.FIREPLACE_LIST_ENTRY, "- \"%s\" at %d %d %d")
		add(Messages.FIREPLACE_ALREADY_AT, "You are already at %s.")
		add(Messages.FIREPLACE_NO_LONGER_VALID, "The fireplace at %d %d %d is no longer valid.")
		add(Messages.FIREPLACE_TELEPORTED, "Teleported to %s.")
	}

	object Subtitles {
		const val FART = "subtitle.irregular_implements.fart"
	}

	private fun addSubtitles() {
		add(Subtitles.FART, "Pbbbt")
	}

	private fun addEffects() {
		addEffect(ModEffects.FIRE_IMBUE, "Fire Imbue")
		addEffect(ModEffects.POISON_IMBUE, "Poison Imbue")
		addEffect(ModEffects.EXPERIENCE_IMBUE, "Experience Imbue")
		addEffect(ModEffects.WITHER_IMBUE, "Wither Imbue")
		addEffect(ModEffects.COLLAPSE_IMBUE, "Collapse Imbue")
		addEffect(ModEffects.SPECTRE_IMBUE, "Spectre Imbue")
		addEffect(ModEffects.COLLAPSE, "Collapse")
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