package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class PermeableGlassBlock private constructor(
    isSolidForMobsOnly: Boolean,
    properties: Properties
) : TransparentBlock(properties) {

    val mobsPassThrough = !isSolidForMobsOnly
    val playersPassThrough = isSolidForMobsOnly

    companion object {

        val LAPIS = PermeableGlassBlock(
            isSolidForMobsOnly = true,
            Properties.ofFullCopy(Blocks.BLUE_STAINED_GLASS)
        )

        val QUARTZ = PermeableGlassBlock(
            isSolidForMobsOnly = false,
            Properties.ofFullCopy(Blocks.LIGHT_GRAY_STAINED_GLASS)
        )

    }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        if (context !is EntityCollisionContext) return Shapes.block()
        val entity = context.entity ?: return Shapes.block()

        return when {
            entity is Player && playersPassThrough -> Shapes.empty()
            entity !is Player && mobsPassThrough -> Shapes.empty()
            else -> Shapes.block()
        }
    }

    //FIXME: Figure out why mobs wont pathfind through Lapis glass
    override fun isPathfindable(state: BlockState, pathComputationType: PathComputationType): Boolean {
        return when (pathComputationType) {
            PathComputationType.LAND -> !mobsPassThrough
            PathComputationType.WATER -> false
            PathComputationType.AIR -> mobsPassThrough
        }
    }

}