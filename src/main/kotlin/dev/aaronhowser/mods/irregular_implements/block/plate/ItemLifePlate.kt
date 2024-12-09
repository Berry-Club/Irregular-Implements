package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class ItemLifePlate private constructor(
    private val duration: Int
) : BasePlateBlock() {

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (entity is ItemEntity) entity.lifespan = this.duration
    }

    companion object {
        val SEALER = ItemLifePlate(20 * 30)
        val REJUVENATOR = ItemLifePlate(20 * 60 * 4)
    }

}