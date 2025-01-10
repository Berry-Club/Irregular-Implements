package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.dispenser.BlockSource
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropperBlock
import net.minecraft.world.level.block.LevelEvent
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.HopperBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.VanillaInventoryCodeHooks

class IronDropperBlock : DropperBlock(
    Properties.ofFullCopy(Blocks.DROPPER)
) {

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity {
        return IronDropperBlockEntity(blockPos, blockState)
    }

    override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.IRON_DROPPER.get(), IronDropperBlockEntity::tick)
    }

    public override fun dispenseFrom(level: ServerLevel, state: BlockState, pos: BlockPos) {
        val blockEntity = level.getBlockEntity(pos) as? IronDropperBlockEntity ?: return

        val blockSource = BlockSource(level, pos, state, blockEntity)
        val slot = blockEntity.getRandomSlot(level.random)

        if (slot < 0) {
            level.levelEvent(LevelEvent.SOUND_DISPENSER_FAIL, pos, 0)
            return
        }

        val stack = blockEntity.getItem(slot)

        if (stack.isEmpty || !VanillaInventoryCodeHooks.dropperInsertHook(level, pos, blockEntity, slot, stack)) {
            return
        }

        val direction = state.getValue(FACING)

        val container = HopperBlockEntity.getContainerAt(level, pos.relative(direction))

        if (container == null) {
            val remainder = blockEntity.dispenseBehavior.dispense(blockSource, stack)
            blockEntity.setItem(slot, remainder)
            return
        }

        var stackToShoot = HopperBlockEntity.addItem(blockEntity, container, stack.copyWithCount(1), direction.opposite)
        if (stackToShoot.isEmpty) {
            stackToShoot = stack.copy()
            stackToShoot.shrink(1)
        } else {
            stackToShoot = stack.copy()
        }

        blockEntity.setItem(slot, stackToShoot)
    }

}