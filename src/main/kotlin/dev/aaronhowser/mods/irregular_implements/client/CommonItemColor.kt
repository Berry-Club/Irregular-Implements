package dev.aaronhowser.mods.irregular_implements.client

import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack

class CommonItemColor private constructor(
    private val dyeColor: DyeColor
) : ItemColor {

    override fun getColor(stack: ItemStack, tintIndex: Int): Int = dyeColor.id

    companion object {

        val WHITE = CommonItemColor(DyeColor.WHITE)
        val ORANGE = CommonItemColor(DyeColor.ORANGE)
        val MAGENTA = CommonItemColor(DyeColor.MAGENTA)
        val LIGHT_BLUE = CommonItemColor(DyeColor.LIGHT_BLUE)
        val YELLOW = CommonItemColor(DyeColor.YELLOW)
        val LIME = CommonItemColor(DyeColor.LIME)
        val PINK = CommonItemColor(DyeColor.PINK)
        val GRAY = CommonItemColor(DyeColor.GRAY)
        val LIGHT_GRAY = CommonItemColor(DyeColor.LIGHT_GRAY)
        val CYAN = CommonItemColor(DyeColor.CYAN)
        val PURPLE = CommonItemColor(DyeColor.PURPLE)
        val BLUE = CommonItemColor(DyeColor.BLUE)
        val BROWN = CommonItemColor(DyeColor.BROWN)
        val GREEN = CommonItemColor(DyeColor.GREEN)
        val RED = CommonItemColor(DyeColor.RED)
        val BLACK = CommonItemColor(DyeColor.BLACK)

        fun getFromColor(dyeColor: DyeColor): CommonItemColor {
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