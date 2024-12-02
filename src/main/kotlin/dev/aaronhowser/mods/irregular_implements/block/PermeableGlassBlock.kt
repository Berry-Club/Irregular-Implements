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

class PermeableGlassBlock(
    val type: Type
) : TransparentBlock(
    Properties
        .ofFullCopy(if (type == Type.LAPIS) Blocks.BLUE_STAINED_GLASS else Blocks.LIGHT_GRAY_STAINED_GLASS)
) {

    enum class Type { QUARTZ, LAPIS }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        if (context !is EntityCollisionContext) return Shapes.block()
        val entity = context.entity ?: return Shapes.block()

        return if (entity is Player && type == Type.LAPIS) Shapes.block() else Shapes.empty()
    }

    //FIXME: Figure out why mobs wont pathfind through Lapis glass
    override fun isPathfindable(state: BlockState, pathComputationType: PathComputationType): Boolean {
        return when (pathComputationType) {
            PathComputationType.LAND -> type == Type.QUARTZ
            PathComputationType.WATER -> false
            PathComputationType.AIR -> type == Type.LAPIS
        }
    }

}