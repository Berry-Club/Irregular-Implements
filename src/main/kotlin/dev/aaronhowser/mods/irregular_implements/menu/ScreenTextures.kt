package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.gui.GuiGraphics

object ScreenTextures {

	sealed class Background(
		path: String,
		val width: Int,
		val height: Int,
		private val canvasSize: Int = 256
	) {
		private val texture = OtherUtil.modResource(path)

		fun render(guiGraphics: GuiGraphics, leftPos: Int, topPos: Int) {
			guiGraphics.blit(
				this.texture,
				leftPos,
				topPos,
				0f,
				0f,
				this.width,
				this.height,
				this.canvasSize,
				this.canvasSize
			)
		}

		data object AdvancedItemCollector : Background("textures/gui/advanced_item_collector.png", 176, 235)
		data object AdvancedRedstoneRepeater : Background("textures/gui/advanced_redstone_repeater.png", 91, 56)
		data object AnalogEmitter : Background("textures/gui/analog_emitter.png", 79, 50)
		data object AutoPlacer : Background("textures/gui/auto_placer.png", 175, 165)
		data object BlockDestabilizer : Background("textures/gui/block_destabilizer.png", 86, 35)
		data object BlockTeleporter : Background("textures/gui/block_teleporter.png", 176, 133)
		data object BlockDetector : Background("textures/gui/block_detector.png", 176, 133)
		data object ChatDetector : Background("textures/gui/chat_detector.png", 137, 54)
		data object ChunkAnalyzer : Background("textures/gui/chunk_analyzer.png", 190, 124)
		data object DropFilter : Background("textures/gui/drop_filter.png", 176, 133)
		data object DyeingMachine : Background("textures/gui/dyeing_machine.png", 176, 141)
		data object EnderEnergyDistributor : Background("textures/gui/ender_energy_distributor.png", 176, 130)
		data object EnderLetter : Background("textures/gui/ender_letter.png", 176, 133)
		data object EnderMailbox : Background("textures/gui/ender_mailbox.png", 176, 133)
		data object EntityDetector : Background("textures/gui/entity_detector.png", 176, 204)
		data object ExtractionPlate : Background("textures/gui/extraction_plate.png", 121, 42)
		data object FilteredRedirectorPlate : Background("textures/gui/filtered_redirector_plate.png", 176, 129)
		data object FilteredSuperLubricantPlatform : Background("textures/gui/filtered_super_lubricant_platform.png", 176, 129)
		data object GlobalChatDetector : Background("textures/gui/global_chat_detector.png", 176, 157)
		data object Igniter : Background("textures/gui/igniter.png", 79, 29)
		data object IronDropper : Background("textures/gui/iron_dropper.png", 176, 166)
		data object ImbuingStation : Background("textures/gui/imbuing_station.png", 176, 207)
		data object InventoryTester : Background("textures/gui/inventory_tester.png", 176, 136)
		data object ItemFilter : Background("textures/gui/item_filter.png", 176, 141)
		data object ItemProjector : Background("textures/gui/item_projector.png", 176, 166)
		data object MagneticForce : Background("textures/gui/magnetic_force.png", 123, 135)
		data object NotificationInterface : Background("textures/gui/notification_interface.png", 176, 146)
		data object OnlineDetector : Background("textures/gui/online_detector.png", 137, 52)
		data object PortableSoundDampener : Background("textures/gui/portable_sound_dampener.png", 176, 133)
		data object PotionVaporizer : Background("textures/gui/potion_vaporizer.png", 176, 166)
		data object ProcessingPlate : Background("textures/gui/processing_plate.png", 121, 77)
		data object RedstoneRemoteEdit : Background("textures/gui/redstone_remote/edit.png", 176, 150)
		data object RedstoneRemoteUse : Background("textures/gui/redstone_remote/use.png", 207, 41)
		data object SoundRecorder : Background("textures/gui/sound_recorder.png", 190, 187)
		data object VoidStone : Background("textures/gui/void_stone.png", 176, 133)

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

		object InventoryTester {
			data object Inverted : Sprite("buttons/inventory_tester/inverted", 4, 11)
			data object Uninverted : Sprite("buttons/inventory_tester/uninverted", 4, 11)
		}
	}

	//TODO: Check that all of these are actually used

}