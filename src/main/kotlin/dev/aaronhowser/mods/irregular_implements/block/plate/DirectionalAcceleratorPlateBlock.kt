package dev.aaronhowser.mods.irregular_implements.block.plate

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.toVec3
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.Vec3

class DirectionalAcceleratorPlateBlock : BasePlateBlock() {

	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(FACING, Direction.NORTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.horizontalDirection)
	}

	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		val direction = state.getValue(FACING)

		val accelerationVector = direction.normal.toVec3().scale(0.1)

		entity.push(accelerationVector)
	}

	companion object {
		val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
	}

}