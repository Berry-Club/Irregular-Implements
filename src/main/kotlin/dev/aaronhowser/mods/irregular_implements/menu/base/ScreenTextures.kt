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
            val canvasSize: Int
        ) {
            val texture = OtherUtil.modResource(path)

            fun render(guiGraphics: GuiGraphics, leftPos: Int, topPos: Int) {
                renderBackground(guiGraphics, this, leftPos, topPos)
            }
        }

        object ChatDetector : Background("textures/gui/chat_detector.png", 137, 54, 256)
        object GlobalChatDetector : Background("textures/gui/global_chat_detector.png", 176, 157, 256)
        object BlockDestabilizer : Background("textures/gui/block_destabilizer.png", 86, 35, 256)
        object IronDropper : Background("textures/gui/iron_dropper.png", 176, 166, 256)
        object Igniter : Background("textures/gui/igniter.png", 79, 29, 256)
        object VoidStone : Background("textures/gui/void_stone.png", 176, 133, 256)
        object OnlineDetector : Background("textures/gui/online_detector.png", 137, 52, 256)

        val ADVANCED_ITEM_COLLECTOR = OtherUtil.modResource("textures/gui/advanced_item_collector.png")
        val ADVANCED_REDSTONE_REPEATER = OtherUtil.modResource("textures/gui/advanced_redstone_repeater.png")
        val ANALOG_EMITTER = OtherUtil.modResource("textures/gui/analog_emitter.png")
        val CHUNK_ANALYZER = OtherUtil.modResource("textures/gui/chunk_analyzer.png")
        val CRAFTING_RECIPE = OtherUtil.modResource("textures/gui/crafting_recipe.png")
        val DYEING_MACHINE = OtherUtil.modResource("textures/gui/dyeing_machine.png")
        val ENDER_LETTER = OtherUtil.modResource("textures/gui/ender_letter.png")
        val ENDER_MAILBOX = OtherUtil.modResource("textures/gui/ender_mailbox.png")
        val ENTITY_DETECTOR = OtherUtil.modResource("textures/gui/entity_detector.png")
        val EXTRACTION_PLATE = OtherUtil.modResource("textures/gui/extraction_plate.png")
        val FILTERED_REDIRECTOR_PLATE = OtherUtil.modResource("textures/gui/filtered_redirector_plate.png")
        val FILTERED_SUPER_LUBRICANT_PLATFORM = OtherUtil.modResource("textures/gui/filtered_super_lubricant_platform.png")
        val IMBUING_STATION = OtherUtil.modResource("textures/gui/imbuing_station.png")
        val INVENTORY_TESTER = OtherUtil.modResource("textures/gui/inventory_tester.png")
        val ITEM_FILTER = OtherUtil.modResource("textures/gui/item_filter.png")
        val ITEM_PROJECTOR = OtherUtil.modResource("textures/gui/item_projector.png")
        val NOTIFICATION_INTERFACE = OtherUtil.modResource("textures/gui/notification_interface.png")
        val PORTABLE_SOUND_DAMPENER = OtherUtil.modResource("textures/gui/portable_sound_dampener.png")
        val POTION_VAPORIZER = OtherUtil.modResource("textures/gui/potion_vaporizer.png")
        val PROCESSING_PLATE = OtherUtil.modResource("textures/gui/processing_plate.png")
        val SOUND_RECORDER = OtherUtil.modResource("textures/gui/sound_recorder.png")
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