package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3

class AcceleratorPlateBlock : BasePlateBlock() {

	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		if (entity.isShiftKeyDown) return

		val originalMotion = entity.deltaMovement

		var newMotion = originalMotion.scale(1.2)
		if (newMotion.horizontalDistanceSqr() > 0.5 * 0.5) {
			newMotion = newMotion.normalize().scale(0.5)
		}

		entity.deltaMovement = Vec3(newMotion.x, originalMotion.y, newMotion.z)
	}

}