package dev.aaronhowser.mods.irregular_implements.block.plate

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.toVec3
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity

object PlateMovement {

	fun getEntryDirection(pos: BlockPos, entity: Entity): Direction? {
		val motion = entity.deltaMovement
		if (motion.horizontalDistanceSqr() < 0.001) return null

		val movingDirection = Direction.getNearest(motion.x, 0.0, motion.z)
		val center = pos.toVec3().add(0.5, 0.0, 0.5)
		val displacement = center.vectorTo(entity.position())
		val entryDirection = Direction.getNearest(displacement.x, 0.0, displacement.z)

		return entryDirection.takeIf { movingDirection == it.opposite }
	}

	fun redirect(pos: BlockPos, entity: Entity, entryDirection: Direction, outputDirection: Direction) {
		val center = pos.toVec3().add(0.5, 0.0, 0.5)
		val destination = outputDirection.normal.toVec3().scale(0.4).add(center)
		val rotation = entryDirection.opposite.toYRot() - outputDirection.toYRot()

		entity.teleportTo(destination.x, entity.y, destination.z)
		entity.deltaMovement = entity.deltaMovement.yRot(Mth.DEG_TO_RAD * rotation)
	}

}