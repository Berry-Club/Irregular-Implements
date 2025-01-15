package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.block.entity.DispenserBlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class IgniterBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : DispenserBlockEntity(ModBlockEntities.IGNITER.get(), pPos, pBlockState) {

    enum class Mode : StringRepresentable {
        TOGGLE,         // Make fire when powered, extinguish when unpowered
        IGNITE,         // Make fire when powered, do nothing when unpowered
        KEEP_IGNITED    // Make fire when powered, make another fire if it goes out while powered
        ;

        override fun getSerializedName(): String {
            return name.lowercase(Locale.getDefault())
        }
    }

}