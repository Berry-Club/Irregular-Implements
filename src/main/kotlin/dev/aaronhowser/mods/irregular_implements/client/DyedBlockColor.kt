package dev.aaronhowser.mods.irregular_implements.client

import net.minecraft.client.color.block.BlockColor
import net.minecraft.core.BlockPos
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState

class DyedBlockColor private constructor(
    private val dyeColor: DyeColor
) : BlockColor {

    override fun getColor(state: BlockState, level: BlockAndTintGetter?, pos: BlockPos?, tintIndex: Int): Int = dyeColor.textureDiffuseColor

    companion object {

        val WHITE = DyedBlockColor(DyeColor.WHITE)
        val ORANGE = DyedBlockColor(DyeColor.ORANGE)
        val MAGENTA = DyedBlockColor(DyeColor.MAGENTA)
        val LIGHT_BLUE = DyedBlockColor(DyeColor.LIGHT_BLUE)
        val YELLOW = DyedBlockColor(DyeColor.YELLOW)
        val LIME = DyedBlockColor(DyeColor.LIME)
        val PINK = DyedBlockColor(DyeColor.PINK)
        val GRAY = DyedBlockColor(DyeColor.GRAY)
        val LIGHT_GRAY = DyedBlockColor(DyeColor.LIGHT_GRAY)
        val CYAN = DyedBlockColor(DyeColor.CYAN)
        val PURPLE = DyedBlockColor(DyeColor.PURPLE)
        val BLUE = DyedBlockColor(DyeColor.BLUE)
        val BROWN = DyedBlockColor(DyeColor.BROWN)
        val GREEN = DyedBlockColor(DyeColor.GREEN)
        val RED = DyedBlockColor(DyeColor.RED)
        val BLACK = DyedBlockColor(DyeColor.BLACK)

        fun getFromColor(dyeColor: DyeColor): DyedBlockColor {
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