package dev.aaronhowser.mods.irregular_implements.client

import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack

class DyeItemColor private constructor(
    private val dyeColor: DyeColor
) : ItemColor {

    override fun getColor(stack: ItemStack, tintIndex: Int): Int = dyeColor.textureDiffuseColor

    companion object {

        val WHITE = DyeItemColor(DyeColor.WHITE)
        val ORANGE = DyeItemColor(DyeColor.ORANGE)
        val MAGENTA = DyeItemColor(DyeColor.MAGENTA)
        val LIGHT_BLUE = DyeItemColor(DyeColor.LIGHT_BLUE)
        val YELLOW = DyeItemColor(DyeColor.YELLOW)
        val LIME = DyeItemColor(DyeColor.LIME)
        val PINK = DyeItemColor(DyeColor.PINK)
        val GRAY = DyeItemColor(DyeColor.GRAY)
        val LIGHT_GRAY = DyeItemColor(DyeColor.LIGHT_GRAY)
        val CYAN = DyeItemColor(DyeColor.CYAN)
        val PURPLE = DyeItemColor(DyeColor.PURPLE)
        val BLUE = DyeItemColor(DyeColor.BLUE)
        val BROWN = DyeItemColor(DyeColor.BROWN)
        val GREEN = DyeItemColor(DyeColor.GREEN)
        val RED = DyeItemColor(DyeColor.RED)
        val BLACK = DyeItemColor(DyeColor.BLACK)

        fun getFromColor(dyeColor: DyeColor): DyeItemColor {
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