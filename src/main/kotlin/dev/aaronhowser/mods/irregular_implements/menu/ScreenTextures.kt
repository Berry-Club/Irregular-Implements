package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.menu.textures.ScreenSprite
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil

object ScreenTextures {

	object Backgrounds {
		private fun background(path: String, width: Int, height: Int): ScreenBackground =
			ScreenBackground(OtherUtil.modResource(path), width, height)

		val ADVANCED_ITEM_COLLECTOR: ScreenBackground = background("textures/gui/advanced_item_collector.png", 176, 235)
		val ADVANCED_REDSTONE_INTERFACE: ScreenBackground = background("textures/gui/redstone_interface/advanced.png", 176, 133)
		val ADVANCED_REDSTONE_REPEATER: ScreenBackground = background("textures/gui/advanced_redstone_repeater.png", 91, 56)
		val ADVANCED_REDSTONE_TORCH: ScreenBackground = background("textures/gui/advanced_redstone_torch.png", 91, 56)
		val ANALOG_EMITTER: ScreenBackground = background("textures/gui/analog_emitter.png", 79, 50)
		val AUTO_PLACER: ScreenBackground = background("textures/gui/auto_placer.png", 175, 165)
		val BLOCK_DESTABILIZER: ScreenBackground = background("textures/gui/block_destabilizer.png", 86, 35)
		val BLOCK_TELEPORTER: ScreenBackground = background("textures/gui/block_teleporter.png", 176, 133)
		val BLOCK_DETECTOR: ScreenBackground = background("textures/gui/block_detector.png", 176, 133)
		val CHAT_DETECTOR: ScreenBackground = background("textures/gui/chat_detector.png", 137, 54)
		val CHUNK_ANALYZER: ScreenBackground = background("textures/gui/chunk_analyzer.png", 190, 124)
		val DROP_FILTER: ScreenBackground = background("textures/gui/drop_filter.png", 176, 133)
		val DYEING_MACHINE: ScreenBackground = background("textures/gui/dyeing_machine.png", 176, 141)
		val ENDER_ENERGY_DISTRIBUTOR: ScreenBackground = background("textures/gui/ender_energy_distributor.png", 176, 130)
		val ENDER_LETTER: ScreenBackground = background("textures/gui/ender_letter.png", 176, 133)
		val ENDER_MAILBOX: ScreenBackground = background("textures/gui/ender_mailbox.png", 176, 133)
		val ENTITY_DETECTOR: ScreenBackground = background("textures/gui/entity_detector.png", 176, 235)
		val EXTRACTION_PLATE: ScreenBackground = background("textures/gui/extraction_plate.png", 121, 42)
		val FILTERED_REDIRECTOR_PLATE: ScreenBackground = background("textures/gui/filtered_redirector_plate.png", 176, 129)
		val FILTERED_SUPER_LUBRICANT_PLATFORM: ScreenBackground = background("textures/gui/filtered_super_lubricant_platform.png", 176, 129)
		val GLOBAL_CHAT_DETECTOR: ScreenBackground = background("textures/gui/global_chat_detector.png", 176, 157)
		val IGNITER: ScreenBackground = background("textures/gui/igniter.png", 79, 29)
		val IRON_DROPPER: ScreenBackground = background("textures/gui/iron_dropper.png", 176, 166)
		val IMBUING_STATION: ScreenBackground = background("textures/gui/imbuing_station.png", 176, 207)
		val INVENTORY_TESTER: ScreenBackground = background("textures/gui/inventory_tester.png", 176, 136)
		val ITEM_FILTER: ScreenBackground = background("textures/gui/item_filter.png", 176, 141)
		val ITEM_PROJECTOR: ScreenBackground = background("textures/gui/item_projector.png", 176, 166)
		val MAGNETIC_FORCE: ScreenBackground = background("textures/gui/magnetic_force.png", 123, 135)
		val NOTIFICATION_INTERFACE: ScreenBackground = background("textures/gui/notification_interface.png", 176, 146)
		val ONLINE_DETECTOR: ScreenBackground = background("textures/gui/online_detector.png", 137, 52)
		val PORTABLE_SOUND_DAMPENER: ScreenBackground = background("textures/gui/portable_sound_dampener.png", 176, 133)
		val POTION_VAPORIZER: ScreenBackground = background("textures/gui/potion_vaporizer.png", 176, 166)
		val PROCESSING_PLATE: ScreenBackground = background("textures/gui/processing_plate.png", 121, 77)
		val REDSTONE_REMOTE_EDIT: ScreenBackground = background("textures/gui/redstone_remote/edit.png", 176, 150)
		val REDSTONE_REMOTE_USE: ScreenBackground = background("textures/gui/redstone_remote/use.png", 207, 41)
		val SOUND_RECORDER: ScreenBackground = background("textures/gui/sound_recorder.png", 190, 187)
		val VOID_STONE: ScreenBackground = background("textures/gui/void_stone.png", 176, 133)
	}

	object Sprites {
		fun sprite(path: String, width: Int, height: Int): ScreenSprite =
			ScreenSprite(OtherUtil.modResource(path), width, height)

		object BlockDestabilizer {
			val Lazy = sprite("buttons/block_destabilizer/lazy", 13, 13)
			val NotLazy = sprite("buttons/block_destabilizer/lazy_not", 13, 13)
			val ShowLazyShape = sprite("buttons/block_destabilizer/show_lazy_shape", 16, 16)            //TODO: Replace with an original texture
			val ResetLazyShape = sprite("buttons/block_destabilizer/reset_lazy_shape", 15, 12)
		}

		object ChatDetector {
			val MessageContinue = sprite("buttons/chat_detector/message_continue", 13, 12)
			val MessageStop = sprite("buttons/chat_detector/message_stop", 13, 12)
		}

		object IronDropper {
			val DirectionForward = sprite("buttons/iron_dropper/direction_forward", 4, 4)
			val DirectionRandom = sprite("buttons/iron_dropper/direction_random", 8, 8)

			val EffectParticle = sprite("buttons/iron_dropper/effect_particle", 14, 13)
			val EffectSound = sprite("buttons/iron_dropper/effect_sound", 5, 8)
			val EffectBoth = sprite("buttons/iron_dropper/effect_both", 14, 13)

			val PickupFive = sprite("buttons/iron_dropper/pickup_five", 10, 12)
			val PickupTwenty = sprite("buttons/iron_dropper/pickup_twenty", 13, 9)
			val PickupZero = sprite("buttons/iron_dropper/pickup_zero", 8, 12)

			val RedstonePulse = sprite("buttons/iron_dropper/redstone_pulse", 8, 8)
			val RedstoneContinuous = sprite("buttons/iron_dropper/redstone_continuous", 12, 14)
			val RedstoneContinuousPowered = sprite("buttons/iron_dropper/redstone_continuous_powered", 12, 14)
		}

		object ItemFilter {
			val Whitelist = sprite("buttons/item_filter/whitelist", 15, 12)
			val Blacklist = sprite("buttons/item_filter/blacklist", 15, 12)
		}

		val Inverted = sprite("buttons/inventory_tester/inverted", 4, 11)
		val Uninverted = sprite("buttons/inventory_tester/uninverted", 4, 11)
	}

	//TODO: Check that all of these are actually used

}