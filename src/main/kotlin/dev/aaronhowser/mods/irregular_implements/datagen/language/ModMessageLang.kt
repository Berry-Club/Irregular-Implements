package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider

object ModMessageLang {

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
	const val ESCAPE_ROPE_HANDLER_PROGRESS = "message.irregular_implements.escape_rope_handler.progress"

	fun add(provider: ModLanguageProvider) {
		provider.apply {
			add(ENDER_BRIDGE_ITERATIONS, "Ender Bridge stopped searching after %d blocks.")
			add(ENDER_BRIDGE_HIT_BLOCK, "Ender Bridge stopped searching because it hit a %s at %d %d %d.")
			add(REDSTONE_TOOL_BASE_SET, "Redstone Tool linked to the %s at %d %d %d.")
			add(REDSTONE_TOOL_INVALID_BASE_BLOCK, "Cannot link as no base block is set.")
			add(REDSTONE_TOOL_WRONG_DIMENSION, "Cannot link as base %s is in a different dimension.")
			add(REDSTONE_TOOL_UNLOADED, "Cannot link as base %s is in an unloaded chunk.")
			add(REDSTONE_TOOL_BASE_NOT_LINKABLE, "Cannot link as base %s was replaced with a %s.")
			add(REDSTONE_TOOL_LINKED, "Linked the %s at %d %d %d to the %s at %d %d %d.")
			add(FLUID_FALL_DEATH_BOOT, "%s splattered against the surface of %s because they were wearing %s")
			add(FLUID_FALL_DEATH_GENERIC, "%s splattered against the surface of %s because they could walk on it")
			add(ILLUMINATOR_ALREADY_PRESENT, "This chunk already has a Spectre Illuminator!")
			add(FE_RATIO, "%s FE / %s FE")
			add(ADVANCED_ITEM_COLLECTOR_X_RADIUS, "X Radius: %d")
			add(ADVANCED_ITEM_COLLECTOR_Y_RADIUS, "Y Radius: %d")
			add(ADVANCED_ITEM_COLLECTOR_Z_RADIUS, "Z Radius: %d")
			add(ENDER_MAILBOX_NOT_OWNER, "You are not the owner of this Ender Mailbox.")
			add(ENDER_LETTER_EMPTY, "Your letter is empty!")
			add(ENDER_LETTER_ALREADY_SENT, "This letter has already been sent! You can't send it again.")
			add(ENDER_LETTER_NO_RECIPIENT, "This letter has no recipient")
			add(ENDER_LETTER_RECIPIENT_NOT_ONLINE, "%s of this letter is not online")
			add(ENDER_LETTER_RECIPIENT_NO_ROOM, "%s has no room for your letter")
			add(FIREPLACE_NO_NAME, "Nameless fireplace")
			add(FIREPLACE_NAME, "Fireplace: %s")
			add(FIREPLACE_BROKEN, "Bugged fireplace; please break and replace it.")
			add(COMMAND_LEVEL_NOT_FOUND, "Could not find level \"%s\"")
			add(FIREPLACE_NOT_FOUND, "Could not find fireplace named \"%s\"")
			add(FIREPLACES_IN_DIMENSION, "Fireplaces in %s: %d")
			add(FIREPLACE_LIST_ENTRY, "- \"%s\" at %d %d %d")
			add(FIREPLACE_ALREADY_AT, "You are already at %s.")
			add(FIREPLACE_NO_LONGER_VALID, "The fireplace at %d %d %d is no longer valid.")
			add(FIREPLACE_TELEPORTED, "Teleported to %s.")
			add(ESCAPE_ROPE_HANDLER_PROGRESS, "Checked %d blocks")
		}
	}

}