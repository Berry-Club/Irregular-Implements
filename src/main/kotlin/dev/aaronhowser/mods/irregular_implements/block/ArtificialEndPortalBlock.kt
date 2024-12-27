package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ArtificialEndPortalBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndPortalBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class ArtificialEndPortalBlock : EndPortalBlock(Properties.ofFullCopy(Blocks.END_PORTAL)) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return ArtificialEndPortalBlockEntity(pos, state)
    }

    override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        return BaseEntityBlock.createTickerHelper(
            blockEntityType,
            ModBlockEntities.ARTIFICIAL_END_PORTAL.get(),
            ArtificialEndPortalBlockEntity::tick
        )
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return ArtificialEndPortalBlockEntity.canSurvive(level, pos)
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        return if (canSurvive(state, level, pos)) {
            state
        } else {
            Blocks.AIR.defaultBlockState()
        }
    }

}