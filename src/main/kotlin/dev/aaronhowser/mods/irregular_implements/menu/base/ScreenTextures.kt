package dev.aaronhowser.mods.irregular_implements.menu.base

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.gui.GuiGraphics

object ScreenTextures {

    fun renderBackground(guiGraphics: GuiGraphics, background: Backgrounds.Background, leftPos: Int, topPos: Int) {
        guiGraphics.blit(
            background.texture,
            leftPos,
            topPos,
            0f,
            0f,
            background.width,
            background.height,
            background.canvasSize,
            background.canvasSize
        )
    }

    object Backgrounds {

        abstract class Background(
            path: String,
            val width: Int,
            val height: Int,
            val canvasSize: Int = 256
        ) {
            val texture = OtherUtil.modResource(path)

            fun render(guiGraphics: GuiGraphics, leftPos: Int, topPos: Int) {
                renderBackground(guiGraphics, this, leftPos, topPos)
            }
        }

        object ChatDetector : Background("textures/gui/chat_detector.png", 137, 54)
        object GlobalChatDetector : Background("textures/gui/global_chat_detector.png", 176, 157)
        object BlockDestabilizer : Background("textures/gui/block_destabilizer.png", 86, 35)
        object IronDropper : Background("textures/gui/iron_dropper.png", 176, 166)
        object Igniter : Background("textures/gui/igniter.png", 79, 29)
        object VoidStone : Background("textures/gui/void_stone.png", 176, 133)
        object OnlineDetector : Background("textures/gui/online_detector.png", 137, 52)
        object NotificationInterface : Background("textures/gui/notification_interface.png", 176, 146)
        object SoundRecorder : Background("textures/gui/sound_recorder.png", 190, 187)
        object PortableSoundDampener : Background("textures/gui/portable_sound_dampener.png", 176, 133)
        object ProcessingPlate : Background("textures/gui/processing_plate.png", 121, 77)
        object PotionVaporizer : Background("textures/gui/potion_vaporizer.png", 176, 166)
        object ItemProjector : Background("textures/gui/item_projector.png", 176, 166)
        object ItemFilter : Background("textures/gui/item_filter.png", 219, 133)
        object InventoryTester : Background("textures/gui/inventory_tester.png", 176, 136)
        object ImbuingStation : Background("textures/gui/imbuing_station.png", 176, 207)
        object FilteredSuperLubricantPlatform : Background("textures/gui/filtered_super_lubricant_platform.png", 176, 129)
        object FilteredRedirectorPlate : Background("textures/gui/filtered_redirector_plate.png", 176, 129)
        object ExtractionPlate : Background("textures/gui/extraction_plate.png", 121, 42)
        object EntityDetector : Background("textures/gui/entity_detector.png", 176, 204)
        object EnderMailbox : Background("textures/gui/ender_mailbox.png", 176, 133)
        object EnderLetter : Background("textures/gui/ender_letter.png", 176, 133)
        object DyeingMachine : Background("textures/gui/dyeing_machine.png", 176, 141)
        object ChunkAnalyzer : Background("textures/gui/chunk_analyzer.png", 190, 124)
        object AnalogEmitter : Background("textures/gui/analog_emitter.png", 79, 50)
        object AdvancedRedstoneRepeater : Background("textures/gui/advanced_redstone_repeater.png", 91, 56)
        object AdvancedItemCollector : Background("textures/gui/advanced_item_collector.png", 176, 235)
    }

    object Sprites {

        abstract class Sprite(
            path: String,
            val width: Int,
            val height: Int
        ) {
            val texture = OtherUtil.modResource(path)
        }

        object ChatDetector {
            object MessageContinue : Sprite("buttons/chat_detector/message_continue", 13, 12)
            object MessageStop : Sprite("buttons/chat_detector/message_stop", 13, 12)
        }

        object BlockDestabilizer {
            object Lazy : Sprite("buttons/block_destabilizer/lazy", 13, 13)
            object NotLazy : Sprite("buttons/block_destabilizer/lazy_not", 13, 13)
            object ShowLazyShape : Sprite("buttons/block_destabilizer/show_lazy_shape", 16, 16)            //TODO: Replace with an original texture
            object ResetLazyShape : Sprite("buttons/block_destabilizer/reset_lazy_shape", 15, 12)
        }

        object IronDropper {
            object DirectionForward : Sprite("buttons/iron_dropper/direction_forward", 4, 4)
            object DirectionRandom : Sprite("buttons/iron_dropper/direction_random", 8, 8)

            object EffectParticle : Sprite("buttons/iron_dropper/effect_particle", 14, 13)
            object EffectSound : Sprite("buttons/iron_dropper/effect_sound", 5, 8)
            object EffectBoth : Sprite("buttons/iron_dropper/effect_both", 14, 13)

            object PickupFive : Sprite("buttons/iron_dropper/pickup_five", 10, 12)
            object PickupTwenty : Sprite("buttons/iron_dropper/pickup_twenty", 13, 9)
            object PickupZero : Sprite("buttons/iron_dropper/pickup_zero", 8, 12)

            object RedstonePulse : Sprite("buttons/iron_dropper/redstone_pulse", 8, 8)
            object RedstoneContinuous : Sprite("buttons/iron_dropper/redstone_continuous", 12, 14)
            object RedstoneContinuousPowered : Sprite("buttons/iron_dropper/redstone_continuous_powered", 12, 14)
        }

    }

    //TODO: Check that all of these are actually used

}