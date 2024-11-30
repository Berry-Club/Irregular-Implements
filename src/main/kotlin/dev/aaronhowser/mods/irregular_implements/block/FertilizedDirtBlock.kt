package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FarmBlock
import net.minecraft.world.level.block.state.BlockState

class FertilizedDirtBlock : FarmBlock(Properties.ofFullCopy(Blocks.FARMLAND)) {

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return true
    }

    override fun fallOn(level: Level, state: BlockState, pos: BlockPos, entity: Entity, fallDistance: Float) {
        // Do nothing
    }

}