package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3

class AcceleratorPlateBlock : BasePlateBlock() {

    //FIXME: Not working?
    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (level.isClientSide || entity.isShiftKeyDown) return

        var entityMotion = entity.deltaMovement
        val y = entityMotion.y
        if (entityMotion.horizontalDistanceSqr() < 0.5 * 0.5) {
            entityMotion = entityMotion.normalize().scale(0.5)
        }

        entityMotion = entityMotion.scale(1.5)

        entity.deltaMovement = Vec3(entityMotion.x, y, entityMotion.z)
    }

}