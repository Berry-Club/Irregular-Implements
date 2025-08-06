package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider

object ModTooltipLang {

	const val SHIFT_FOR_MORE = "tooltip.irregular_implements.shift_for_more"
	const val ENTITY_FILTER_ENTITY = "tooltip.irregular_implements.entity_filter_entity"
	const val PLAYER_FILTER_PLAYER = "tooltip.irregular_implements.player_filter_player"
	const val PLAYER_FILTER_UUID = "tooltip.irregular_implements.player_filter_uuid"
	const val COMPRESSED_SLIME_AMOUNT = "tooltip.irregular_implements.compressed_slime_AMOUNT"
	const val LUBRICATED = "tooltip.irregular_implements.lubricated"
	const val WITH_BLOCK_ENTITY = "tooltip.irregular_implements.with_block_entity"
	const val CHARGE = "tooltip.irregular_implements.charge"
	const val WHITE_STONE_FULL_MOON = "tooltip.irregular_implements.white_stone_full_moon"
	const val SUMMONING_PENDULUM_FRACTION = "tooltip.irregular_implements.summoning_pendulum_fraction"
	const val LIST_POINT = "tooltip.irregular_implements.summoning_pendulum_list_each"
	const val BLOCK_REPLACER_LOADING = "tooltip.irregular_implements.block_replacer_loading"
	const val BLOCK_REPLACER_UNLOADING = "tooltip.irregular_implements.block_replacer_unloading"
	const val BLOCK_REPLACER_ALT_FOR_LIST = "tooltip.irregular_implements.block_replacer_alt_for_list"
	const val ITEM_COUNT = "tooltip.irregular_implements.item_count"
	const val VOID_STONE_INSERT = "tooltip.irregular_implements.void_stone_insert"
	const val VOID_STONE_HOLDING = "tooltip.irregular_implements.void_stone_holding"
	const val VOID_STONE_REMOVE = "tooltip.irregular_implements.void_stone_remove"
	const val ANCHORED = "tooltip.irregular_implements.anchored"
	const val HAS_LUMINOUS_POWDER = "tooltip.irregular_implements.has_luminous_powder"
	const val ALL_ORES = "tooltip.irregular_implements.all_ores"
	const val LAZY = "tooltip.irregular_implements.lazy"
	const val NOT_LAZY = "tooltip.irregular_implements.not_lazy"
	const val SHOW_LAZY_SHAPE = "tooltip.irregular_implements.show_lazy_shape"
	const val FORGET_LAZY_SHAPE = "tooltip.irregular_implements.forget_lazy_shape"
	const val STOPS_MESSAGE = "tooltip.irregular_implements.stops_message"
	const val DOESNT_STOP_MESSAGE = "tooltip.irregular_implements.doesnt_stop_message"
	const val MESSAGE_REGEX = "tooltip.irregular_implements.message_regex"
	const val COIL_TRANSFERS = "tooltip.irregular_implements.coil_transfers"
	const val COIL_GENERATES = "tooltip.irregular_implements.coil_generates"
	const val CHARGER_CHARGES = "tooltip.irregular_implements.charger_charges"
	const val LOCATION_COMPONENT = "tooltip.irregular_implements.location_component"
	const val IRON_DROPPER_CONTINUOUS_POWERED = "tooltip.irregular_implements.iron_dropper_continuous_powered"
	const val IRON_DROPPER_CONTINUOUS = "tooltip.irregular_implements.iron_dropper_continuous"
	const val IRON_DROPPER_PULSE = "tooltip.irregular_implements.iron_dropper_pulse"
	const val IRON_DROPPER_NO_DELAY = "tooltip.irregular_implements.iron_dropper_no_delay"
	const val IRON_DROPPER_FIVE_DELAY = "tooltip.irregular_implements.iron_dropper_five_delay"
	const val IRON_DROPPER_TWENTY_DELAY = "tooltip.irregular_implements.iron_dropper_twenty_delay"
	const val IRON_DROPPER_EXACT_VELOCITY = "tooltip.irregular_implements.iron_dropper_exact_velocity"
	const val IRON_DROPPER_RANDOM_VELOCITY = "tooltip.irregular_implements.iron_dropper_random_velocity"
	const val IRON_DROPPER_NO_EFFECTS = "tooltip.irregular_implements.iron_dropper_no_effects"
	const val IRON_DROPPER_ONLY_SOUND = "tooltip.irregular_implements.iron_dropper_only_sound"
	const val IRON_DROPPER_ONLY_PARTICLES = "tooltip.irregular_implements.iron_dropper_only_particles"
	const val IRON_DROPPER_BOTH_EFFECTS = "tooltip.irregular_implements.iron_dropper_both_effects"
	const val BLOCK = "tooltip.irregular_implements.block"
	const val DIAPHANOUS_INVERTED = "tooltip.irregular_implements.diaphanous_inverted"
	const val ITEM_TAG = "tooltip.irregular_implements.item_tag"
	const val BLACKLIST = "tooltip.irregular_implements.blacklist"
	const val WHITELIST = "tooltip.irregular_implements.whitelist"
	const val IGNITER_TOGGLE = "tooltip.irregular_implements.igniter_toggle"
	const val IGNITER_IGNITE = "tooltip.irregular_implements.igniter_ignite"
	const val IGNITER_KEEP_IGNITED = "tooltip.irregular_implements.igniter_keep_ignited"
	const val ITEM_FILTER_ITEM = "tooltip.irregular_implements.item_filter_item"
	const val ITEM_FILTER_TAG = "tooltip.irregular_implements.item_filter_tag"
	const val ITEM_FILTER_REQUIRE_COMPONENTS = "tooltip.irregular_implements.item_filter_require_components"
	const val ITEM_FILTER_IGNORE_COMPONENTS = "tooltip.irregular_implements.item_filter_ignore_components"
	const val ITEM_FILTER_REQUIRES_SAME_COMPONENTS = "tooltip.irregular_implements.item_filter_requires_same_components"
	const val FLOO_POUCH_AMOUNT = "tooltip.irregular_implements.floo_pouch.amount"
	const val TRANS_RIGHTS = "tooltip.irregular_implements.trans_rights"
	const val ENDER_LETTER_TO = "tooltip.irregular_implements.ender_letter_to"
	const val ENDER_LETTER_FROM = "tooltip.irregular_implements.ender_letter_from"
	const val INVENTORY_TESTER_INVERTED = "tooltip.irregular_implements.inventory_tester_inverted"
	const val INVENTORY_TESTER_UNINVERTED = "tooltip.irregular_implements.inventory_tester_uninverted"

	fun add(provider: ModLanguageProvider) {
		provider.apply {
			add(ITEM_FILTER_ITEM, "Item Filter")
			add(ITEM_FILTER_TAG, "Tag Filter")
			add(ITEM_FILTER_REQUIRE_COMPONENTS, "Require Components")
			add(ITEM_FILTER_IGNORE_COMPONENTS, "Ignore Components")
			add(ITEM_FILTER_REQUIRES_SAME_COMPONENTS, "Requires same components")
			add(SHIFT_FOR_MORE, "Hold SHIFT for more information")
			add(ENTITY_FILTER_ENTITY, "Entity Type: %s")
			add(PLAYER_FILTER_PLAYER, "Player: %s")
			add(PLAYER_FILTER_UUID, "Player UUID: %s")
			add(COMPRESSED_SLIME_AMOUNT, "Compression level %d")
			add(LUBRICATED, "Lubricated")
			add(WITH_BLOCK_ENTITY, "%s with block entity")
			add(CHARGE, "Charge: %d%%")
			add(WHITE_STONE_FULL_MOON, "Charge under the full moon")
			add(SUMMONING_PENDULUM_FRACTION, "Stored: %d/%d")
			add(LIST_POINT, "• %s")
			add(BLOCK_REPLACER_LOADING, "Store blocks by right-clicking them on this stack")
			add(BLOCK_REPLACER_UNLOADING, "Remove blocks by right-clicking this stack on empty slots")
			add(BLOCK_REPLACER_ALT_FOR_LIST, "Hold ALT to see stored blocks")
			add(ITEM_COUNT, "%s x%d")
			add(VOID_STONE_INSERT, "Store items by right-clicking them on this stack")
			add(VOID_STONE_HOLDING, "Currently holding %s, inserting another will void and replace it")
			add(VOID_STONE_REMOVE, "You can remove the %s by right-clicking the Stone into an empty slot")
			add(ANCHORED, "Anchored")
			add(HAS_LUMINOUS_POWDER, "Luminous Powder applied")
			add(ALL_ORES, "All Ores")
			add(LOCATION_COMPONENT, "%s, %dx %dy %dz")
			add(LAZY, "Lazy")
			add(NOT_LAZY, "Not Lazy")
			add(SHOW_LAZY_SHAPE, "Show Lazy Shape")
			add(FORGET_LAZY_SHAPE, "Forget Lazy Shape")
			add(STOPS_MESSAGE, "Stops message")
			add(DOESNT_STOP_MESSAGE, "Doesn't stop message")
			add(MESSAGE_REGEX, "Message regex")
			add(COIL_TRANSFERS, "Transfers %s RF/t")
			add(COIL_GENERATES, "Generates %s RF/t")
			add(CHARGER_CHARGES, "Charges %s RF/t")
			add(IRON_DROPPER_CONTINUOUS_POWERED, "Eject continuously while powered")
			add(IRON_DROPPER_CONTINUOUS, "Eject continuously")
			add(IRON_DROPPER_PULSE, "Eject when pulsed")
			add(IRON_DROPPER_NO_DELAY, "No pickup delay")
			add(IRON_DROPPER_FIVE_DELAY, "5 tick pickup delay")
			add(IRON_DROPPER_TWENTY_DELAY, "20 tick pickup delay")
			add(IRON_DROPPER_EXACT_VELOCITY, "Exact velocity")
			add(IRON_DROPPER_RANDOM_VELOCITY, "Random velocity")
			add(IRON_DROPPER_NO_EFFECTS, "No effects")
			add(IRON_DROPPER_ONLY_SOUND, "Sound effects")
			add(IRON_DROPPER_ONLY_PARTICLES, "Particle effects")
			add(IRON_DROPPER_BOTH_EFFECTS, "Sound & particle effects")
			add(BLOCK, "Block: %s")
			add(DIAPHANOUS_INVERTED, "Inverted")
			add(ITEM_TAG, "Item Tag: %s")
			add(BLACKLIST, "Blacklist")
			add(WHITELIST, "Whitelist")
			add(IGNITER_TOGGLE, "Toggle")
			add(IGNITER_IGNITE, "Ignite")
			add(IGNITER_KEEP_IGNITED, "Keep ignited")
			add(FLOO_POUCH_AMOUNT, "%d / %d Floo Powder")
			add(TRANS_RIGHTS, "Trans rights are human rights!")
			add(ENDER_LETTER_TO, "To: %s")
			add(ENDER_LETTER_FROM, "From: %s")
			add(INVENTORY_TESTER_INVERTED, "Inverted")
			add(INVENTORY_TESTER_UNINVERTED, "Uninverted")
		}
	}

}