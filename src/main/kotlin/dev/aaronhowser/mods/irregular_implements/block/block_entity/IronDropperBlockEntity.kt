package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.menu.IronDropperMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.core.Position
import net.minecraft.core.dispenser.BlockSource
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.SimpleContainerData
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

    companion object {
        const val CONTAINER_DATA_SIZE = 4

        const val SHOOT_STRAIGHT_INDEX = 0
        const val SHOULD_HAVE_EFFECTS_INDEX = 1
        const val PICKUP_DELAY_INDEX = 2
        const val REDSTONE_MODE_INDEX = 3

        const val SHOOT_STRAIGHT_NBT = "ShootStraight"
        const val SHOULD_HAVE_EFFECTS_NBT = "ShouldHaveEffects"
        const val PICKUP_DELAY_NBT = "PickupDelay"
        const val REDSTONE_MODE_NBT = "RedstoneMode"
    }

    var shouldShootStraight: Boolean = false
        private set(value) {
            field = value
            setChanged()
        }

    var shouldHaveEffects: Boolean = true
        private set(value) {
            field = value
            setChanged()
        }

    var pickupDelay: Int = 0
        private set(value) {
            field = value
            setChanged()
        }

    enum class RedstoneMode { PULSE, REPEAT, REPEAT_POWERED }

    var redstoneMode: RedstoneMode = RedstoneMode.PULSE
        private set(value) {
            field = value
            setChanged()
        }


    private val containerData = object : SimpleContainerData(CONTAINER_DATA_SIZE) {

        override fun get(index: Int): Int {
            return when (index) {
                SHOOT_STRAIGHT_INDEX -> if (this@IronDropperBlockEntity.shouldShootStraight) 1 else 0
                SHOULD_HAVE_EFFECTS_INDEX -> if (this@IronDropperBlockEntity.shouldHaveEffects) 1 else 0
                PICKUP_DELAY_INDEX -> this@IronDropperBlockEntity.pickupDelay
                REDSTONE_MODE_INDEX -> RedstoneMode.entries.indexOf(this@IronDropperBlockEntity.redstoneMode)
                else -> 0
            }
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                SHOOT_STRAIGHT_INDEX -> this@IronDropperBlockEntity.shouldShootStraight = value != 0
                SHOULD_HAVE_EFFECTS_INDEX -> this@IronDropperBlockEntity.shouldHaveEffects = value != 0
                PICKUP_DELAY_INDEX -> this@IronDropperBlockEntity.pickupDelay = value
                REDSTONE_MODE_INDEX -> this@IronDropperBlockEntity.redstoneMode = RedstoneMode.entries[value]
            }
        }
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

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
        return IronDropperMenu(containerId, playerInventory, this, containerData)
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putBoolean(SHOOT_STRAIGHT_NBT, this.shouldShootStraight)
        tag.putBoolean(SHOULD_HAVE_EFFECTS_NBT, this.shouldHaveEffects)
        tag.putInt(PICKUP_DELAY_NBT, this.pickupDelay)
        tag.putInt(REDSTONE_MODE_NBT, this.redstoneMode.ordinal)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        this.shouldShootStraight = tag.getBoolean(SHOOT_STRAIGHT_NBT)
        this.shouldHaveEffects = tag.getBoolean(SHOULD_HAVE_EFFECTS_NBT)
        this.pickupDelay = tag.getInt(PICKUP_DELAY_NBT)
        this.redstoneMode = RedstoneMode.entries[tag.getInt(REDSTONE_MODE_NBT)]
    }

    // Syncs with client
    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)


}