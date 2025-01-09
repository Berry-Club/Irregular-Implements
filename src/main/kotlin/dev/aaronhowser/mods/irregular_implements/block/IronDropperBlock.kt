package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.dispenser.BlockSource
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropperBlock
import net.minecraft.world.level.block.LevelEvent
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.HopperBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.VanillaInventoryCodeHooks

class IronDropperBlock : DropperBlock(
    Properties.ofFullCopy(Blocks.DROPPER)
) {

    companion object {
        val DEFAULT_DISPENSE_BEHAVIOR = DefaultDispenseItemBehavior()
        val STRAIGHT_DISPENSE_BEHAVIOR = object : DefaultDispenseItemBehavior() {
            override fun execute(blockSource: BlockSource, item: ItemStack): ItemStack {
                val direction = blockSource.state().getValue(FACING)
                val position = getDispensePosition(blockSource)
                val itemstack = item.split(1)

                val x = position.x()
                val y = position.y() - if (direction.axis == Direction.Axis.Y) 0.125 else 0.15625
                val z = position.z()

                val itemEntity = ItemEntity(blockSource.level, x, y, z, itemstack)

                val speed = 6

                itemEntity.setDeltaMovement(
                    direction.stepX * speed * 0.1,
                    direction.stepY * speed * 0.1,
                    direction.stepZ * speed * 0.1
                )

                blockSource.level.addFreshEntity(itemEntity)

                return item
            }
        }
    }

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity {
        return IronDropperBlockEntity(blockPos, blockState)
    }

    override fun dispenseFrom(level: ServerLevel, state: BlockState, pos: BlockPos) {
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
            val remainder = STRAIGHT_DISPENSE_BEHAVIOR.dispense(blockSource, stack)
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