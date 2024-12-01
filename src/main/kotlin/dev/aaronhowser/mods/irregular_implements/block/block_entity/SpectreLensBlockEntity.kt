package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class SpectreLensBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.SPECTRE_LENS.get(), pPos, pBlockState) {

    companion object {
        const val OWNER_UUID_NBT = "owner_uuid"
    }

    var owner: UUID? = null

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        if (owner != null) {
            tag.putUUID(OWNER_UUID_NBT, owner!!)
        }
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        if (tag.contains(OWNER_UUID_NBT)) {
            owner = tag.getUUID(OWNER_UUID_NBT)
        }
    }

}