package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3

class BouncyPlateBlock : BasePlateBlock() {

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (entity.deltaMovement.y >= 1.0) return

        entity.deltaMovement = Vec3(
            entity.deltaMovement.x,
            1.0,
            entity.deltaMovement.z
        )

        entity.resetFallDistance()
        entity.setOnGround(false)
    }

}