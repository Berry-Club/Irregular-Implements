package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreEnergyInjectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.savedata.SpectreCoilSavedData
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
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

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return SpectreEnergyInjectorBlockEntity(pos, state)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)

        if (placer == null) return
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is SpectreEnergyInjectorBlockEntity) {
            blockEntity.ownerUuid = placer.uuid
        }
    }

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        val blockEntity = level.getBlockEntity(pos)

        if (!level.isClientSide && blockEntity is SpectreEnergyInjectorBlockEntity) {
            val energyHandler = blockEntity.getEnergyHandler(null)
            val energyStored = energyHandler?.energyStored ?: 0
            val maxEnergy = energyHandler?.maxEnergyStored ?: SpectreCoilSavedData.MAX_ENERGY

            val storedFormatted = String.format("%,d", energyStored)
            val maxFormatted = String.format("%,d", maxEnergy)

            val component = Component.literal("$storedFormatted FE / $maxFormatted FE")

            player.sendSystemMessage(component)
        }

        return InteractionResult.SUCCESS
    }

}