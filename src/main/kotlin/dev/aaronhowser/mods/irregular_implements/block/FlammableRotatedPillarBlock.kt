package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState

open class FlammableRotatedPillarBlock(private val baseBlock: Block, properties: Properties) : RotatedPillarBlock(properties) {

	override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
		return isFlammable(baseBlock.defaultBlockState(), level, pos, direction)
	}

	override fun getFlammability(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return getFlammability(baseBlock.defaultBlockState(), level, pos, direction)
	}

	override fun getFireSpreadSpeed(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return getFireSpreadSpeed(baseBlock.defaultBlockState(), level, pos, direction)
	}

}