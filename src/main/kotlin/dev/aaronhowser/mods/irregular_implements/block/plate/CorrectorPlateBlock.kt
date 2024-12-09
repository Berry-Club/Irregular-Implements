package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import kotlin.math.abs

class CorrectorPlateBlock : BasePlateBlock() {

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        val motion = entity.deltaMovement

        if (abs(motion.x) < abs(motion.z)) {
            if (entity.position().x != pos.x + 0.5) {
                entity.setPos(pos.x + 0.5, entity.position().y, entity.position().z)
            }

            if (motion.x != 0.0) {
                entity.setDeltaMovement(0.0, motion.y, motion.z)
            }
        } else if (abs(motion.x) > abs(motion.z)) {
            if (entity.position().z != pos.z + 0.5) {
                entity.setPos(entity.position().x, entity.position().y, pos.z + 0.5)
            }

            if (motion.z != 0.0) {
                entity.setDeltaMovement(motion.x, motion.y, 0.0)
            }
        }
    }

}