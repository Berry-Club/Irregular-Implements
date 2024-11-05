package dev.aaronhowser.mods.irregular_implements.client

import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack

class DyedItemColor private constructor(
    private val dyeColor: DyeColor
) : ItemColor {

    override fun getColor(stack: ItemStack, tintIndex: Int): Int = dyeColor.textureDiffuseColor

    companion object {

        val WHITE = DyedItemColor(DyeColor.WHITE)
        val ORANGE = DyedItemColor(DyeColor.ORANGE)
        val MAGENTA = DyedItemColor(DyeColor.MAGENTA)
        val LIGHT_BLUE = DyedItemColor(DyeColor.LIGHT_BLUE)
        val YELLOW = DyedItemColor(DyeColor.YELLOW)
        val LIME = DyedItemColor(DyeColor.LIME)
        val PINK = DyedItemColor(DyeColor.PINK)
        val GRAY = DyedItemColor(DyeColor.GRAY)
        val LIGHT_GRAY = DyedItemColor(DyeColor.LIGHT_GRAY)
        val CYAN = DyedItemColor(DyeColor.CYAN)
        val PURPLE = DyedItemColor(DyeColor.PURPLE)
        val BLUE = DyedItemColor(DyeColor.BLUE)
        val BROWN = DyedItemColor(DyeColor.BROWN)
        val GREEN = DyedItemColor(DyeColor.GREEN)
        val RED = DyedItemColor(DyeColor.RED)
        val BLACK = DyedItemColor(DyeColor.BLACK)

        fun getFromColor(dyeColor: DyeColor): DyedItemColor {
            return when (dyeColor) {
                DyeColor.WHITE -> WHITE
                DyeColor.ORANGE -> ORANGE
                DyeColor.MAGENTA -> MAGENTA
                DyeColor.LIGHT_BLUE -> LIGHT_BLUE
                DyeColor.YELLOW -> YELLOW
                DyeColor.LIME -> LIME
                DyeColor.PINK -> PINK
                DyeColor.GRAY -> GRAY
                DyeColor.LIGHT_GRAY -> LIGHT_GRAY
                DyeColor.CYAN -> CYAN
                DyeColor.PURPLE -> PURPLE
                DyeColor.BLUE -> BLUE
                DyeColor.BROWN -> BROWN
                DyeColor.GREEN -> GREEN
                DyeColor.RED -> RED
                DyeColor.BLACK -> BLACK
            }
        }

    }

}