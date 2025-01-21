package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.InventoryTesterBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.ItemHandlerHelper

class InventoryTesterBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.INVENTORY_TESTER.get(), pPos, pBlockState) {

    companion object {
        const val ITEMSTACK_NBT = "ItemStack"
        const val INVERT_SIGNAL_NBT = "InvertSignal"
        const val IS_EMITTING_REDSTONE_NBT = "IsEmittingRedstone"
    }

    var itemStack: ItemStack = ItemStack.EMPTY
        set(value) {
            field = value
            setChanged()
        }

    var invertSignal: Boolean = false
        set(value) {
            field = value
            setChanged()
        }

    var isEmittingRedstone: Boolean = false
        private set(value) {
            field = value
            setChanged()
        }

    private var counter: Int = 0
    fun tick() {
        val level = level ?: return
        val item = this.itemStack

        if (level.isClientSide || ++counter != 2 || item.isEmpty) return

        val facing = this.blockState.getValue(InventoryTesterBlock.FACING)
        val onBlock = this.blockPos.relative(facing)
        val itemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, onBlock, facing.opposite) ?: return

        val canInsert = ItemHandlerHelper.insertItemStacked(itemHandler, item, true).isEmpty

        val redstone = if (this.invertSignal) !canInsert else canInsert

        if (redstone != this.isEmittingRedstone) {
            this.isEmittingRedstone = redstone
            level.updateNeighborsAt(this.blockPos, this.blockState.block)
        }

    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        if (!this.itemStack.isEmpty) {
            tag.put(ITEMSTACK_NBT, this.itemStack.save(registries))
        }

        tag.putBoolean(INVERT_SIGNAL_NBT, this.invertSignal)
        tag.putBoolean(IS_EMITTING_REDSTONE_NBT, this.isEmittingRedstone)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        this.itemStack = ItemStack.parseOptional(registries, tag.getCompound(ITEMSTACK_NBT))
        this.invertSignal = tag.getBoolean(INVERT_SIGNAL_NBT)
        this.isEmittingRedstone = tag.getBoolean(IS_EMITTING_REDSTONE_NBT)
    }

}