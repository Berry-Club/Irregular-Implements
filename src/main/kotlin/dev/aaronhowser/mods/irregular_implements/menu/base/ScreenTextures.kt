package dev.aaronhowser.mods.irregular_implements.menu.base

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil

object ScreenTextures {

    object Background {

        object ChatDetector {
            val BACKGROUND = OtherUtil.modResource("textures/gui/chat_detector.png")

            const val WIDTH = 137
            const val HEIGHT = 54
            const val CANVAS_SIZE = 256
        }

        object BlockDestabilizer {
            val BACKGROUND = OtherUtil.modResource("textures/gui/block_destabilizer.png")

            const val WIDTH = 86
            const val HEIGHT = 35
            const val CANVAS_SIZE = 256
        }

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
        val GLOBAL_CHAT_DETECTOR = OtherUtil.modResource("textures/gui/global_chat_detector.png")
        val IGNITER = OtherUtil.modResource("textures/gui/igniter.png")
        val IMBUING_STATION = OtherUtil.modResource("textures/gui/imbuing_station.png")
        val INVENTORY_TESTER = OtherUtil.modResource("textures/gui/inventory_tester.png")
        val IRON_DROPPER = OtherUtil.modResource("textures/gui/iron_dropper.png")
        val ITEM_FILTER = OtherUtil.modResource("textures/gui/item_filter.png")
        val ITEM_PROJECTOR = OtherUtil.modResource("textures/gui/item_projector.png")
        val JEI_ANVIL = OtherUtil.modResource("textures/gui/jei_anvil.png")
        val NOTIFICATION_INTERFACE = OtherUtil.modResource("textures/gui/notification_interface.png")
        val ONLINE_DETECTOR = OtherUtil.modResource("textures/gui/online_detector.png")
        val PORTABLE_SOUND_DAMPENER = OtherUtil.modResource("textures/gui/portable_sound_dampener.png")
        val POTION_VAPORIZER = OtherUtil.modResource("textures/gui/potion_vaporizer.png")
        val PROCESSING_PLATE = OtherUtil.modResource("textures/gui/processing_plate.png")
        val SOUND_RECORDER = OtherUtil.modResource("textures/gui/sound_recorder.png")
        val VOID_STONE = OtherUtil.modResource("textures/gui/void_stone.png")
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

    }

    //TODO: Check that all of these are actually used

}