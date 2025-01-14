package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.CustomCraftingTableMenu
import net.minecraft.core.BlockPos
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class CustomCraftingTableBlock : Block(Properties.ofFullCopy(Blocks.CRAFTING_TABLE)), EntityBlock {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return CustomCraftingTableBlockEntity(pos, state)
    }

    override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
        return Shapes.empty()
    }

    override fun getSoundType(state: BlockState, level: LevelReader, pos: BlockPos, entity: Entity?): SoundType {
        val blockEntity = level.getBlockEntity(pos) as? CustomCraftingTableBlockEntity
            ?: return super.getSoundType(state, level, pos, entity)

        return blockEntity.renderedBlock
            .defaultBlockState()
            .getSoundType(level, pos, entity)
    }

    override fun spawnDestroyParticles(level: Level, player: Player, pos: BlockPos, state: BlockState) {
        val blockEntity = level.getBlockEntity(pos) as? CustomCraftingTableBlockEntity
            ?: return super.spawnDestroyParticles(level, player, pos, state)

        level.levelEvent(
            player,
            LevelEvent.PARTICLES_DESTROY_BLOCK,
            pos,
            getId(blockEntity.renderedBlock.defaultBlockState())
        )
    }

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS
        } else {
            player.openMenu(state.getMenuProvider(level, pos))
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE)
            return InteractionResult.CONSUME
        }
    }

    override fun getMenuProvider(state: BlockState, level: Level, pos: BlockPos): MenuProvider {
        return SimpleMenuProvider(
            { containerId, playerInventory, player ->
                CustomCraftingTableMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos))
            },
            this.name
        )
    }

}