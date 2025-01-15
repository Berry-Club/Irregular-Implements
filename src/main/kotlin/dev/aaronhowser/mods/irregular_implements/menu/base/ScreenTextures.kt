package dev.aaronhowser.mods.irregular_implements.menu.base

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.gui.GuiGraphics

object ScreenTextures {

    fun renderBackground(guiGraphics: GuiGraphics, background: Backgrounds.MenuBackground, leftPos: Int, topPos: Int) {
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

        abstract class MenuBackground(
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

        object ChatDetector : MenuBackground("textures/gui/chat_detector.png", 137, 54)
        object GlobalChatDetector : MenuBackground("textures/gui/global_chat_detector.png", 176, 157)
        object BlockDestabilizer : MenuBackground("textures/gui/block_destabilizer.png", 86, 35)
        object IronDropper : MenuBackground("textures/gui/iron_dropper.png", 176, 166)
        object Igniter : MenuBackground("textures/gui/igniter.png", 79, 29)
        object VoidStone : MenuBackground("textures/gui/void_stone.png", 176, 133)
        object OnlineDetector : MenuBackground("textures/gui/online_detector.png", 137, 52)
        object NotificationInterface : MenuBackground("textures/gui/notification_interface.png", 176, 146)
        object SoundRecorder : MenuBackground("textures/gui/sound_recorder.png", 190, 187)
        object PortableSoundDampener : MenuBackground("textures/gui/portable_sound_dampener.png", 176, 133)
        object ProcessingPlate : MenuBackground("textures/gui/processing_plate.png", 121, 77)
        object PotionVaporizer : MenuBackground("textures/gui/potion_vaporizer.png", 176, 166)
        object ItemProjector : MenuBackground("textures/gui/item_projector.png", 176, 166)
        object ItemFilter : MenuBackground("textures/gui/item_filter.png", 219, 133)
        object InventoryTester : MenuBackground("textures/gui/inventory_tester.png", 176, 136)
        object ImbuingStation : MenuBackground("textures/gui/imbuing_station.png", 176, 207)
        object FilteredSuperLubricantPlatform : MenuBackground("textures/gui/filtered_super_lubricant_platform.png", 176, 129)
        object FilteredRedirectorPlate : MenuBackground("textures/gui/filtered_redirector_plate.png", 176, 129)
        object ExtractionPlate : MenuBackground("textures/gui/extraction_plate.png", 121, 42)
        object EntityDetector : MenuBackground("textures/gui/entity_detector.png", 176, 204)
        object EnderMailbox : MenuBackground("textures/gui/ender_mailbox.png", 176, 133)
        object EnderLetter : MenuBackground("textures/gui/ender_letter.png", 176, 133)
        object DyeingMachine : MenuBackground("textures/gui/dyeing_machine.png", 176, 141)
        object ChunkAnalyzer : MenuBackground("textures/gui/chunk_analyzer.png", 190, 124)
        object AnalogEmitter : MenuBackground("textures/gui/analog_emitter.png", 79, 50)
        object AdvancedRedstoneRepeater : MenuBackground("textures/gui/advanced_redstone_repeater.png", 91, 56)
        object AdvancedItemCollector : MenuBackground("textures/gui/advanced_item_collector.png", 176, 235)
    }

    object Sprites {

        abstract class MenuSprite(
            path: String,
            val width: Int,
            val height: Int
        ) {
            val texture = OtherUtil.modResource(path)
        }

        object ChatDetector {
            object MessageContinue : MenuSprite("buttons/chat_detector/message_continue", 13, 12)
            object MessageStop : MenuSprite("buttons/chat_detector/message_stop", 13, 12)
        }

        object BlockDestabilizer {
            object Lazy : MenuSprite("buttons/block_destabilizer/lazy", 13, 13)
            object NotLazy : MenuSprite("buttons/block_destabilizer/lazy_not", 13, 13)
            object ShowLazyShape : MenuSprite("buttons/block_destabilizer/show_lazy_shape", 16, 16)            //TODO: Replace with an original texture
            object ResetLazyShape : MenuSprite("buttons/block_destabilizer/reset_lazy_shape", 15, 12)
        }

        object IronDropper {
            object DirectionForward : MenuSprite("buttons/iron_dropper/direction_forward", 4, 4)
            object DirectionRandom : MenuSprite("buttons/iron_dropper/direction_random", 8, 8)

            object EffectParticle : MenuSprite("buttons/iron_dropper/effect_particle", 14, 13)
            object EffectSound : MenuSprite("buttons/iron_dropper/effect_sound", 5, 8)
            object EffectBoth : MenuSprite("buttons/iron_dropper/effect_both", 14, 13)

            object PickupFive : MenuSprite("buttons/iron_dropper/pickup_five", 10, 12)
            object PickupTwenty : MenuSprite("buttons/iron_dropper/pickup_twenty", 13, 9)
            object PickupZero : MenuSprite("buttons/iron_dropper/pickup_zero", 8, 12)

            object RedstonePulse : MenuSprite("buttons/iron_dropper/redstone_pulse", 8, 8)
            object RedstoneContinuous : MenuSprite("buttons/iron_dropper/redstone_continuous", 12, 14)
            object RedstoneContinuousPowered : MenuSprite("buttons/iron_dropper/redstone_continuous_powered", 12, 14)
        }

    }

    //TODO: Check that all of these are actually used

}