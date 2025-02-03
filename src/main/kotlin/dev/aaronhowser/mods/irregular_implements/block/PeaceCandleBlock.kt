package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

// Right now these are being added to the structures by recreating the structures entirely, and
// adding the structures to the pool. This is currently being done by ENTIRELY REPLACING the vanilla
// datapack files, which SUCKS and IS BAD. //TODO: DON'T DO THAT
class PeaceCandleBlock : Block(Properties.ofFullCopy(Blocks.OBSIDIAN)) {

    companion object {
        val SHAPE: VoxelShape = box(5.0, 0.0, 5.0, 11.0, 2.0, 11.0)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return canSupportCenter(level, pos.below(), Direction.UP)
    }

}