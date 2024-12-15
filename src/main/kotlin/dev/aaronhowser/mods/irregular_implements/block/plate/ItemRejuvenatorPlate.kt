package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class ItemRejuvenatorPlate : BasePlateBlock() {

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (entity is ItemEntity) {
            entity.lifespan = 20 * 60 * 4
            entity.age = 0
        }
    }

}