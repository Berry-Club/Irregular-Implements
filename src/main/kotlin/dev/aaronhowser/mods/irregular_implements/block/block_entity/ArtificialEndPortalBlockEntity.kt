package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndRodBlock
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.common.Tags

class ArtificialEndPortalBlockEntity(
    pos: BlockPos,
    blockState: BlockState
) : TheEndPortalBlockEntity(ModBlockEntities.ARTIFICIAL_END_PORTAL.get(), pos, blockState) {

    companion object {

        fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: ArtificialEndPortalBlockEntity
        ) {
            if (level.isClientSide || level.gameTime % 100 != 0L) return

            if (!blockState.canSurvive(level, blockPos)) {
                level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState())
                return
            }
        }

        fun canSurvive(level: LevelReader, pos: BlockPos): Boolean {
            for (direction in Direction.Plane.HORIZONTAL) {
                val posThere = pos.relative(direction)
                val stateThere = level.getBlockState(posThere)

                if (!stateThere.`is`(ModBlocks.ARTIFICIAL_END_PORTAL.get()) && !stateThere.`is`(Tags.Blocks.OBSIDIANS)) return false
            }

            val blockEntity = level.getBlockEntity(pos) as? ArtificialEndPortalBlockEntity ?: return false

            val endRodBlockPos = blockEntity.endRodBlockPos
            val endRodBlockState = level.getBlockState(endRodBlockPos)

            return endRodBlockState.`is`(Blocks.END_ROD)
                    && endRodBlockState.getValue(EndRodBlock.FACING) == Direction.DOWN
                    && level.getBlockState(endRodBlockPos.above()).`is`(Tags.Blocks.END_STONES)
        }

        const val END_ROD_BLOCK_POS_KEY = "end_rod_block_pos"
    }

    var endRodBlockPos: BlockPos = BlockPos.ZERO

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putLong(END_ROD_BLOCK_POS_KEY, endRodBlockPos.asLong())
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        endRodBlockPos = BlockPos.of(tag.getLong(END_ROD_BLOCK_POS_KEY))
    }

}