package dev.aaronhowser.mods.irregular_implements.item

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndRodBlock
import net.neoforged.neoforge.common.Tags

class EvilTearItem : Item(Properties()) {

    companion object {
        private fun tryPlacePortal(level: Level, clickedPos: BlockPos): Boolean {
            val blockState = level.getBlockState(clickedPos)

            if (
                !blockState.`is`(Blocks.END_ROD)
                || blockState.getValue(EndRodBlock.FACING) != Direction.DOWN
                || !level.getBlockState(clickedPos.above()).`is`(Tags.Blocks.END_STONES)
                || !level.getBlockState(clickedPos.below()).isAir
            ) return false

            var blocksBelow = 2
            while (blocksBelow <= 10) {
                val stateThere = level.getBlockState(clickedPos.below(blocksBelow))
                if (stateThere.isAir) {
                    blocksBelow++
                    continue
                }
                if (stateThere.`is`(Tags.Blocks.END_STONES)) break

                return false
            }

            val centerPos = clickedPos.below(blocksBelow - 1)

            for (dX in -1..1) for (dZ in -1..1) {
                val posThere = centerPos.offset(dX, 0, dZ)

                val isAir = level.getBlockState(posThere).isAir
                val isAboveEndStone = level.getBlockState(posThere.below()).`is`(Tags.Blocks.END_STONES)

                if (!isAir || !isAboveEndStone) return false

                if (dX != 0) {
                    val posDx = posThere.offset(dX, 0, 0)
                    val stateDx = level.getBlockState(posDx)
                    if (!stateDx.`is`(Tags.Blocks.OBSIDIANS)) return false
                }

                if (dZ != 0) {
                    val posDz = posThere.offset(0, 0, dZ)
                    val stateDz = level.getBlockState(posDz)
                    if (!stateDz.`is`(Tags.Blocks.OBSIDIANS)) return false
                }
            }

            for (dX in -1..1) for (dZ in -1..1) {
                val posThere = centerPos.offset(dX, 0, dZ)

                level.setBlockAndUpdate(
                    posThere,
                    Blocks.END_PORTAL.defaultBlockState()
                )
            }

            return true
        }
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val clickedPos = context.clickedPos
        val level = context.level

        return if (!level.isClientSide && tryPlacePortal(level, clickedPos)) {
            InteractionResult.SUCCESS
        } else {
            InteractionResult.FAIL
        }
    }

}