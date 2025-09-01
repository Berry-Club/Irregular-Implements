package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.gui.GuiGraphics

object ScreenTextures {

	enum class Background(
		path: String,
		val width: Int,
		val height: Int,
		private val canvasSize: Int = 256
	) {
		ADVANCED_ITEM_COLLECTOR("textures/gui/advanced_item_collector.png", 176, 235),
		ADVANCED_REDSTONE_REPEATER("textures/gui/advanced_redstone_repeater.png", 91, 56),
		ANALOG_EMITTER("textures/gui/analog_emitter.png", 79, 50),
		AUTO_PLACER("textures/gui/auto_placer.png", 175, 165),
		BLOCK_DESTABILIZER("textures/gui/block_destabilizer.png", 86, 35),
		BLOCK_TELEPORTER("textures/gui/block_teleporter.png", 176, 133),
		BLOCK_DETECTOR("textures/gui/block_detector.png", 176, 133),
		CHAT_DETECTOR("textures/gui/chat_detector.png", 137, 54),
		CHUNK_ANALYZER("textures/gui/chunk_analyzer.png", 190, 124),
		DROP_FILTER("textures/gui/drop_filter.png", 176, 133),
		DYEING_MACHINE("textures/gui/dyeing_machine.png", 176, 141),
		ENDER_ENERGY_DISTRIBUTOR("textures/gui/ender_energy_distributor.png", 176, 130),
		ENDER_LETTER("textures/gui/ender_letter.png", 176, 133),
		ENDER_MAILBOX("textures/gui/ender_mailbox.png", 176, 133),
		ENTITY_DETECTOR("textures/gui/entity_detector.png", 176, 204),
		EXTRACTION_PLATE("textures/gui/extraction_plate.png", 121, 42),
		FILTERED_REDIRECTOR_PLATE("textures/gui/filtered_redirector_plate.png", 176, 129),
		FILTERED_SUPER_LUBRICANT_PLATFORM("textures/gui/filtered_super_lubricant_platform.png", 176, 129),
		GLOBAL_CHAT_DETECTOR("textures/gui/global_chat_detector.png", 176, 157),
		IGNITER("textures/gui/igniter.png", 79, 29),
		IRON_DROPPER("textures/gui/iron_dropper.png", 176, 166),
		IMBUING_STATION("textures/gui/imbuing_station.png", 176, 207),
		INVENTORY_TESTER("textures/gui/inventory_tester.png", 176, 136),
		ITEM_FILTER("textures/gui/item_filter.png", 176, 141),
		ITEM_PROJECTOR("textures/gui/item_projector.png", 176, 166),
		MAGNETIC_FORCE("textures/gui/magnetic_force.png", 123, 135),
		NOTIFICATION_INTERFACE("textures/gui/notification_interface.png", 176, 146),
		ONLINE_DETECTOR("textures/gui/online_detector.png", 137, 52),
		PORTABLE_SOUND_DAMPENER("textures/gui/portable_sound_dampener.png", 176, 133),
		POTION_VAPORIZER("textures/gui/potion_vaporizer.png", 176, 166),
		PROCESSING_PLATE("textures/gui/processing_plate.png", 121, 77),
		REDSTONE_REMOTE_EDIT("textures/gui/redstone_remote/edit.png", 176, 150),
		REDSTONE_REMOTE_USE("textures/gui/redstone_remote/use.png", 207, 41),
		SOUND_RECORDER("textures/gui/sound_recorder.png", 190, 187),
		VOID_STONE("textures/gui/void_stone.png", 176, 133)

		;

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