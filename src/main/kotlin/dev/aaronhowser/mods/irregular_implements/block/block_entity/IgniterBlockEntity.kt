package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.DispenserBlockEntity
import net.minecraft.world.level.block.state.BlockState

class IgniterBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : DispenserBlockEntity(ModBlockEntities.IGNITER.get(), pPos, pBlockState) {

    enum class Mode {
        TOGGLE,         // Make fire when powered, extinguish when unpowered
        IGNITE,         // Make fire when powered, do nothing when unpowered
        KEEP_IGNITED    // Make fire when powered, make another fire if it goes out while powered
    }

    companion object {
        const val MODE_NBT = "Mode"
    }

    var mode: Mode = Mode.TOGGLE
        private set(value) {
            field = value
            setChanged()
        }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putInt(MODE_NBT, this.mode.ordinal)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        this.mode = Mode.entries[tag.getInt(MODE_NBT)]
    }

}