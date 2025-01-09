package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.menu.IronDropperMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Position
import net.minecraft.core.dispenser.BlockSource
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.DropperBlock.FACING
import net.minecraft.world.level.block.DropperBlock.getDispensePosition
import net.minecraft.world.level.block.entity.DispenserBlockEntity
import net.minecraft.world.level.block.state.BlockState

class IronDropperBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : DispenserBlockEntity(ModBlockEntities.IRON_DROPPER.get(), pPos, pBlockState) {

    var shouldShootStraight = false
        private set(value) {
            field = value
            setChanged()
        }

    var shouldHaveEffects = true
        private set(value) {
            field = value
            setChanged()
        }

    var pickupDelay = 5
        private set(value) {
            field = value
            setChanged()
        }

    val dispenseBehavior = object : DefaultDispenseItemBehavior() {
        override fun playSound(blockSource: BlockSource) {
            if (this@IronDropperBlockEntity.shouldHaveEffects) super.playSound(blockSource)
        }

        override fun playAnimation(blockSource: BlockSource, direction: Direction) {
            if (this@IronDropperBlockEntity.shouldHaveEffects) super.playAnimation(blockSource, direction)
        }

        override fun execute(blockSource: BlockSource, item: ItemStack): ItemStack {
            val direction = blockSource.state().getValue(FACING)
            val position = getDispensePosition(blockSource)
            val stack = item.split(1)

            val speed = 6

            shoot(blockSource.level, stack, speed, direction, position, this@IronDropperBlockEntity.shouldShootStraight)

            return stack
        }

        private fun shoot(level: Level, stack: ItemStack, speed: Int, facing: Direction, position: Position, shootForward: Boolean) {
            val x = position.x()
            val y = position.y() - if (facing.axis == Direction.Axis.Y) 0.125 else 0.15625
            val z = position.z()

            val itemEntity = ItemEntity(level, x, y, z, stack)

            if (shootForward) {
                itemEntity.setDeltaMovement(
                    facing.stepX * speed * 0.1,
                    facing.stepY * speed * 0.1,
                    facing.stepZ * speed * 0.1
                )
            } else {
                val offset = level.random.nextDouble() * 0.1 + 0.2

                itemEntity.setDeltaMovement(
                    level.random.triangle(facing.stepX.toDouble() * offset, 0.0172275 * speed.toDouble()),
                    level.random.triangle(0.2, 0.0172275 * speed.toDouble()),
                    level.random.triangle(facing.stepZ.toDouble() * offset, 0.0172275 * speed.toDouble())
                )
            }

            itemEntity.setPickUpDelay(this@IronDropperBlockEntity.pickupDelay)

            level.addFreshEntity(itemEntity)
        }
    }

    override fun getDefaultName(): Component {
        return ModBlocks.IRON_DROPPER.get().name
    }

    override fun createMenu(id: Int, playerInventory: Inventory): AbstractContainerMenu {
        return IronDropperMenu(id, playerInventory, this)
    }

}