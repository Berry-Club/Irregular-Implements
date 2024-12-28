package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreEnergyInjectorBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class SpectreEnergyInjectorBlock : Block(
    Properties
        .ofFullCopy(Blocks.IRON_BLOCK)
        .sound(SoundType.GLASS)
), EntityBlock {

    override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
        return Shapes.empty()
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.ENTITYBLOCK_ANIMATED
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return SpectreEnergyInjectorBlockEntity(pos, state)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)

        if (placer == null) return
        val blockEntity = level.getBlockEntity(pos) as? SpectreEnergyInjectorBlockEntity ?: return
        blockEntity.ownerUuid = placer.uuid
    }

}