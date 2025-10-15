package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class AdvancedRedstoneTorchBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.ADVANCED_REDSTONE_TORCH.get(), pos, blockState) {

	private var strengthOn: Int = 15
	private var strengthOff: Int = 4

	fun getStrength(on: Boolean): Int = if (on) strengthOn else strengthOff

	private val containerData = object : ContainerData {
		override fun get(index: Int): Int {
			return when (index) {
				0 -> strengthOn
				1 -> strengthOff
				else -> 0
			}
		}

		override fun set(index: Int, value: Int) {
			when (index) {
				0 -> strengthOn = value.coerceIn(0, 15)
				1 -> strengthOff = value.coerceIn(0, 15)
			}

			setChanged()
		}

		override fun getCount(): Int = CONTAINER_DATA_SIZE

	}

	companion object {
		const val CONTAINER_DATA_SIZE = 2
	}

}