package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

//FIXME: Update texture
class SemiPermeableGlassBlock(
	isSolidForMobsOnly: Boolean,
	properties: Properties
) : TransparentBlock(properties) {

	val mobsPassThrough: Boolean = !isSolidForMobsOnly
	val playersPassThrough: Boolean = isSolidForMobsOnly

	override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		if (context !is EntityCollisionContext) return Shapes.block()
		val entity = context.entity ?: return Shapes.block()

		return when (entity) {
			is Player if playersPassThrough -> Shapes.empty()
			!is Player if mobsPassThrough -> Shapes.empty()
			else -> Shapes.block()
		}
	}

	override fun isPathfindable(state: BlockState, pathComputationType: PathComputationType): Boolean {
		return when (pathComputationType) {
			PathComputationType.LAND -> mobsPassThrough
			PathComputationType.WATER -> false
			PathComputationType.AIR -> mobsPassThrough
		}
	}

}