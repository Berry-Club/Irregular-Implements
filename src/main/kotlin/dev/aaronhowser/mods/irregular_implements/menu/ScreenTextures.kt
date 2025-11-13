package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil

object ScreenTextures {

	object Backgrounds {
		private fun background(path: String, width: Int, height: Int): ScreenBackground =
			ScreenBackground(OtherUtil.modResource(path), width, height)

		val advancedItemCollector: ScreenBackground = background("textures/gui/advanced_item_collector.png", 176, 235)
		val advancedRedstoneInterface: ScreenBackground = background("textures/gui/redstone_interface/advanced.png", 176, 133)
		val advancedRedstoneRepeater: ScreenBackground = background("textures/gui/advanced_redstone_repeater.png", 91, 56)
		val advancedRedstoneTorch: ScreenBackground = background("textures/gui/advanced_redstone_torch.png", 91, 56)
		val analogEmitter: ScreenBackground = background("textures/gui/analog_emitter.png", 79, 50)
		val autoPlacer: ScreenBackground = background("textures/gui/auto_placer.png", 175, 165)
		val blockDestabilizer: ScreenBackground = background("textures/gui/block_destabilizer.png", 86, 35)
		val blockTeleporter: ScreenBackground = background("textures/gui/block_teleporter.png", 176, 133)
		val blockDetector: ScreenBackground = background("textures/gui/block_detector.png", 176, 133)
		val chatDetector: ScreenBackground = background("textures/gui/chat_detector.png", 137, 54)
		val chunkAnalyzer: ScreenBackground = background("textures/gui/chunk_analyzer.png", 190, 124)
		val dropFilter: ScreenBackground = background("textures/gui/drop_filter.png", 176, 133)
		val dyeingMachine: ScreenBackground = background("textures/gui/dyeing_machine.png", 176, 141)
		val enderEnergyDistributor: ScreenBackground = background("textures/gui/ender_energy_distributor.png", 176, 130)
		val enderLetter: ScreenBackground = background("textures/gui/ender_letter.png", 176, 133)
		val enderMailbox: ScreenBackground = background("textures/gui/ender_mailbox.png", 176, 133)
		val entityDetector: ScreenBackground = background("textures/gui/entity_detector.png", 176, 235)
		val extractionPlate: ScreenBackground = background("textures/gui/extraction_plate.png", 121, 42)
		val filteredRedirectorPlate: ScreenBackground = background("textures/gui/filtered_redirector_plate.png", 176, 129)
		val filteredSuperLubricantPlatform: ScreenBackground = background("textures/gui/filtered_super_lubricant_platform.png", 176, 129)
		val globalChatDetector: ScreenBackground = background("textures/gui/global_chat_detector.png", 176, 157)
		val igniter: ScreenBackground = background("textures/gui/igniter.png", 79, 29)
		val ironDropper: ScreenBackground = background("textures/gui/iron_dropper.png", 176, 166)
		val imbuingStation: ScreenBackground = background("textures/gui/imbuing_station.png", 176, 207)
		val inventoryTester: ScreenBackground = background("textures/gui/inventory_tester.png", 176, 136)
		val itemFilter: ScreenBackground = background("textures/gui/item_filter.png", 176, 141)
		val itemProjector: ScreenBackground = background("textures/gui/item_projector.png", 176, 166)
		val magneticForce: ScreenBackground = background("textures/gui/magnetic_force.png", 123, 135)
		val notificationInterface: ScreenBackground = background("textures/gui/notification_interface.png", 176, 146)
		val onlineDetector: ScreenBackground = background("textures/gui/online_detector.png", 137, 52)
		val portableSoundDampener: ScreenBackground = background("textures/gui/portable_sound_dampener.png", 176, 133)
		val potionVaporizer: ScreenBackground = background("textures/gui/potion_vaporizer.png", 176, 166)
		val processingPlate: ScreenBackground = background("textures/gui/processing_plate.png", 121, 77)
		val redstoneRemoteEdit: ScreenBackground = background("textures/gui/redstone_remote/edit.png", 176, 150)
		val redstoneRemoteUse: ScreenBackground = background("textures/gui/redstone_remote/use.png", 207, 41)
		val soundRecorder: ScreenBackground = background("textures/gui/sound_recorder.png", 190, 187)
		val voidStone: ScreenBackground = background("textures/gui/void_stone.png", 176, 133)

	}

	sealed class Sprite(
		path: String,
		val width: Int,
		val height: Int
	) {
		val texture = OtherUtil.modResource(path)

		object BlockDestabilizer {
			data object Lazy : Sprite("buttons/block_destabilizer/lazy", 13, 13)
			data object NotLazy : Sprite("buttons/block_destabilizer/lazy_not", 13, 13)
			data object ShowLazyShape : Sprite("buttons/block_destabilizer/show_lazy_shape", 16, 16)            //TODO: Replace with an original texture
			data object ResetLazyShape : Sprite("buttons/block_destabilizer/reset_lazy_shape", 15, 12)
		}

		object ChatDetector {
			data object MessageContinue : Sprite("buttons/chat_detector/message_continue", 13, 12)
			data object MessageStop : Sprite("buttons/chat_detector/message_stop", 13, 12)
		}

		object IronDropper {
			data object DirectionForward : Sprite("buttons/iron_dropper/direction_forward", 4, 4)
			data object DirectionRandom : Sprite("buttons/iron_dropper/direction_random", 8, 8)

			data object EffectParticle : Sprite("buttons/iron_dropper/effect_particle", 14, 13)
			data object EffectSound : Sprite("buttons/iron_dropper/effect_sound", 5, 8)
			data object EffectBoth : Sprite("buttons/iron_dropper/effect_both", 14, 13)

			data object PickupFive : Sprite("buttons/iron_dropper/pickup_five", 10, 12)
			data object PickupTwenty : Sprite("buttons/iron_dropper/pickup_twenty", 13, 9)
			data object PickupZero : Sprite("buttons/iron_dropper/pickup_zero", 8, 12)

			data object RedstonePulse : Sprite("buttons/iron_dropper/redstone_pulse", 8, 8)
			data object RedstoneContinuous : Sprite("buttons/iron_dropper/redstone_continuous", 12, 14)
			data object RedstoneContinuousPowered : Sprite("buttons/iron_dropper/redstone_continuous_powered", 12, 14)
		}

		object ItemFilter {
			data object Whitelist : Sprite("buttons/item_filter/whitelist", 15, 12)
			data object Blacklist : Sprite("buttons/item_filter/blacklist", 15, 12)
		}

		data object Inverted : Sprite("buttons/inventory_tester/inverted", 4, 11)
		data object Uninverted : Sprite("buttons/inventory_tester/uninverted", 4, 11)
	}

	//TODO: Check that all of these are actually used

}