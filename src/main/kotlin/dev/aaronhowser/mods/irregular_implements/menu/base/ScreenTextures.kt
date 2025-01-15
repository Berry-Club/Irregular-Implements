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

    object Sprite {

        object ChatDetector {
            val MESSAGE_CONTINUE = OtherUtil.modResource("buttons/chat_detector/message_continue")
            val MESSAGE_STOP = OtherUtil.modResource("buttons/chat_detector/message_stop")

            const val WIDTH = 13
            const val HEIGHT = 12
        }

        object BlockDestabilizer {
            val LAZY = OtherUtil.modResource("buttons/block_destabilizer/lazy")
            const val LAZY_WIDTH = 13
            const val LAZY_HEIGHT = 13

            val NOT_LAZY = OtherUtil.modResource("buttons/block_destabilizer/lazy_not")
            const val NOT_LAZY_WIDTH = 13
            const val NOT_LAZY_HEIGHT = 13

            //TODO: Replace with an original texture
            val SHOW_LAZY_SHAPE = OtherUtil.modResource("buttons/block_destabilizer/show_lazy_shape")
            const val SHOW_LAZY_SHAPE_WIDTH = 16
            const val SHOW_LAZY_SHAPE_HEIGHT = 16

            val RESET_LAZY_SHAPE = OtherUtil.modResource("buttons/block_destabilizer/reset_lazy_shape")
            const val RESET_LAZY_SHAPE_WIDTH = 15
            const val RESET_LAZY_SHAPE_HEIGHT = 12
        }

        object IronDropper {

            val DIRECTION_FORWARD = OtherUtil.modResource("buttons/iron_dropper/direction_forward")
            const val DIRECTION_FORWARD_WIDTH = 4
            const val DIRECTION_FORWARD_HEIGHT = 4

            val DIRECTION_RANDOM = OtherUtil.modResource("buttons/iron_dropper/direction_random")
            const val DIRECTION_RANDOM_WIDTH = 8
            const val DIRECTION_RANDOM_HEIGHT = 8

            val EFFECT_PARTICLE = OtherUtil.modResource("buttons/iron_dropper/effect_particle")
            const val EFFECT_PARTICLE_WIDTH = 14
            const val EFFECT_PARTICLE_HEIGHT = 13

            val EFFECT_SOUND = OtherUtil.modResource("buttons/iron_dropper/effect_sound")
            const val EFFECT_SOUND_WIDTH = 5
            const val EFFECT_SOUND_HEIGHT = 8

            val EFFECT_BOTH = OtherUtil.modResource("buttons/iron_dropper/effect_both")
            const val EFFECT_BOTH_WIDTH = 14
            const val EFFECT_BOTH_HEIGHT = 13

            val PICKUP_FIVE = OtherUtil.modResource("buttons/iron_dropper/pickup_five")
            const val PICKUP_FIVE_WIDTH = 10
            const val PICKUP_FIVE_HEIGHT = 12

            val PICKUP_TWENTY = OtherUtil.modResource("buttons/iron_dropper/pickup_twenty")
            const val PICKUP_TWENTY_WIDTH = 13
            const val PICKUP_TWENTY_HEIGHT = 9

            val PICKUP_ZERO = OtherUtil.modResource("buttons/iron_dropper/pickup_zero")
            const val PICKUP_ZERO_WIDTH = 8
            const val PICKUP_ZERO_HEIGHT = 12

            val REDSTONE_PULSE = OtherUtil.modResource("buttons/iron_dropper/redstone_pulse")
            const val REDSTONE_PULSE_WIDTH = 8
            const val REDSTONE_PULSE_HEIGHT = 8

            val REDSTONE_CONTINUOUS = OtherUtil.modResource("buttons/iron_dropper/redstone_continuous")
            const val REDSTONE_CONTINUOUS_WIDTH = 12
            const val REDSTONE_CONTINUOUS_HEIGHT = 14

            val REDSTONE_CONTINUOUS_POWERED = OtherUtil.modResource("buttons/iron_dropper/redstone_continuous_powered")
            const val REDSTONE_CONTINUOUS_POWERED_WIDTH = 12
            const val REDSTONE_CONTINUOUS_POWERED_HEIGHT = 14
        }

    }

    //TODO: Check that all of these are actually used

}