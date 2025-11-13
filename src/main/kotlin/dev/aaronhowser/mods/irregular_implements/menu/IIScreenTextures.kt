package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.aaron.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil

object IIScreenTextures {

	object B {
		private fun background(path: String, width: Int, height: Int): ScreenTextures.Background =
			ScreenTextures.Background(OtherUtil.modResource(path), width, height)

		val advancedItemCollector = background("textures/gui/advanced_item_collector.png", 176, 235)
		val advancedRedstoneInterface = background("textures/gui/redstone_interface/advanced.png", 176, 133)
		val AdvancedRedstoneRepeater = background("textures/gui/advanced_redstone_repeater.png", 91, 56)
		val AdvancedRedstoneTorch = background("textures/gui/advanced_redstone_torch.png", 91, 56)
		val AnalogEmitter = background("textures/gui/analog_emitter.png", 79, 50)
		val AutoPlacer = background("textures/gui/auto_placer.png", 175, 165)
		val BlockDestabilizer = background("textures/gui/block_destabilizer.png", 86, 35)
		val BlockTeleporter = background("textures/gui/block_teleporter.png", 176, 133)
		val BlockDetector = background("textures/gui/block_detector.png", 176, 133)
		val ChatDetector = background("textures/gui/chat_detector.png", 137, 54)
		val ChunkAnalyzer = background("textures/gui/chunk_analyzer.png", 190, 124)
		val DropFilter = background("textures/gui/drop_filter.png", 176, 133)
		val DyeingMachine = background("textures/gui/dyeing_machine.png", 176, 141)
		val EnderEnergyDistributor = background("textures/gui/ender_energy_distributor.png", 176, 130)
		val EnderLetter = background("textures/gui/ender_letter.png", 176, 133)
		val EnderMailbox = background("textures/gui/ender_mailbox.png", 176, 133)
		val EntityDetector = background("textures/gui/entity_detector.png", 176, 235)
		val ExtractionPlate = background("textures/gui/extraction_plate.png", 121, 42)
		val FilteredRedirectorPlate = background("textures/gui/filtered_redirector_plate.png", 176, 129)
		val FilteredSuperLubricantPlatform = background("textures/gui/filtered_super_lubricant_platform.png", 176, 129)
		val GlobalChatDetector = background("textures/gui/global_chat_detector.png", 176, 157)
		val Igniter = background("textures/gui/igniter.png", 79, 29)
		val IronDropper = background("textures/gui/iron_dropper.png", 176, 166)
		val ImbuingStation = background("textures/gui/imbuing_station.png", 176, 207)
		val InventoryTester = background("textures/gui/inventory_tester.png", 176, 136)
		val ItemFilter = background("textures/gui/item_filter.png", 176, 141)
		val ItemProjector = background("textures/gui/item_projector.png", 176, 166)
		val MagneticForce = background("textures/gui/magnetic_force.png", 123, 135)
		val NotificationInterface = background("textures/gui/notification_interface.png", 176, 146)
		val OnlineDetector = background("textures/gui/online_detector.png", 137, 52)
		val PortableSoundDampener = background("textures/gui/portable_sound_dampener.png", 176, 133)
		val PotionVaporizer = background("textures/gui/potion_vaporizer.png", 176, 166)
		val ProcessingPlate = background("textures/gui/processing_plate.png", 121, 77)
		val RedstoneRemoteEdit = background("textures/gui/redstone_remote/edit.png", 176, 150)
		val RedstoneRemoteUse = background("textures/gui/redstone_remote/use.png", 207, 41)
		val SoundRecorder = background("textures/gui/sound_recorder.png", 190, 187)
		val VoidStone = background("textures/gui/void_stone.png", 176, 133)

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