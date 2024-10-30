package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

class BlockOfSticks(
    val returning: Boolean
) : Block(
    Properties.of()
        .mapColor(MapColor.SAND)
        .sound(SoundType.SCAFFOLDING)
        .strength(0.3f)
        .pushReaction(PushReaction.DESTROY)
        .isRedstoneConductor(Blocks::never)
        .isValidSpawn(Blocks::never)
        .isSuffocating(Blocks::never)
        .isViewBlocking(Blocks::never)
) {

    override fun onPlace(
        pState: BlockState,
        pLevel: Level,
        pPos: BlockPos,
        pOldState: BlockState,
        pMovedByPiston: Boolean
    ) {
        if (!pLevel.isClientSide) pLevel.scheduleTick(pPos, this, 20 * 7)
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston)
    }

    override fun tick(pState: BlockState, pLevel: ServerLevel, pPos: BlockPos, pRandom: RandomSource) {
        pLevel.destroyBlock(pPos, true)
        super.tick(pState, pLevel, pPos, pRandom)
    }

}