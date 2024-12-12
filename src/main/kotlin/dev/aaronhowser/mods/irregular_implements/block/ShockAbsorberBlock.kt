package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class ShockAbsorberBlock : Block(Properties.of()) {

    override fun fallOn(level: Level, state: BlockState, pos: BlockPos, entity: Entity, fallDistance: Float) {
        // Do nothing (including causing fall damage)
    }

}