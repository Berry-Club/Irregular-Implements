package dev.aaronhowser.mods.irregular_implements.client

import net.minecraft.client.color.block.BlockColor
import net.minecraft.core.BlockPos
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState

class DyeBlockColor private constructor(
    private val dyeColor: DyeColor
) : BlockColor {

    override fun getColor(state: BlockState, level: BlockAndTintGetter?, pos: BlockPos?, tintIndex: Int): Int = dyeColor.textureDiffuseColor

    companion object {

        val WHITE = DyeBlockColor(DyeColor.WHITE)
        val ORANGE = DyeBlockColor(DyeColor.ORANGE)
        val MAGENTA = DyeBlockColor(DyeColor.MAGENTA)
        val LIGHT_BLUE = DyeBlockColor(DyeColor.LIGHT_BLUE)
        val YELLOW = DyeBlockColor(DyeColor.YELLOW)
        val LIME = DyeBlockColor(DyeColor.LIME)
        val PINK = DyeBlockColor(DyeColor.PINK)
        val GRAY = DyeBlockColor(DyeColor.GRAY)
        val LIGHT_GRAY = DyeBlockColor(DyeColor.LIGHT_GRAY)
        val CYAN = DyeBlockColor(DyeColor.CYAN)
        val PURPLE = DyeBlockColor(DyeColor.PURPLE)
        val BLUE = DyeBlockColor(DyeColor.BLUE)
        val BROWN = DyeBlockColor(DyeColor.BROWN)
        val GREEN = DyeBlockColor(DyeColor.GREEN)
        val RED = DyeBlockColor(DyeColor.RED)
        val BLACK = DyeBlockColor(DyeColor.BLACK)

        fun getFromColor(dyeColor: DyeColor): DyeBlockColor {
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