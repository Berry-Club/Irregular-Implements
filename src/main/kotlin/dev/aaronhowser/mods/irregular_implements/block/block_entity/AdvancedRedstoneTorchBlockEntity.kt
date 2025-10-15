package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class AdvancedRedstoneTorchBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.ADVANCED_REDSTONE_TORCH.get(), pos, blockState) {

	private var strengthGreen: Int = 15
	private var strengthRed: Int = 4

	fun getStrength(isLit: Boolean): Int = if (isLit) strengthGreen else strengthRed

	private val containerData = object : ContainerData {
		override fun get(index: Int): Int {
			return when (index) {
				STRENGTH_LIT_INDEX -> strengthGreen
				STRENGTH_UNLIT_INDEX -> strengthRed
				else -> 0
			}
		}

		override fun set(index: Int, value: Int) {
			when (index) {
				STRENGTH_LIT_INDEX -> strengthGreen = value.coerceIn(0, 15)
				STRENGTH_UNLIT_INDEX -> strengthRed = value.coerceIn(0, 15)
			}

			setChanged()
		}

		override fun getCount(): Int = CONTAINER_DATA_SIZE
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putInt(STRENGTH_GREEN_NBT, strengthGreen)
		tag.putInt(STRENGTH_RED_NBT, strengthRed)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		strengthGreen = tag.getInt(STRENGTH_GREEN_NBT).coerceIn(0, 15)
		strengthRed = tag.getInt(STRENGTH_RED_NBT).coerceIn(0, 15)
	}

	companion object {
		const val STRENGTH_LIT_INDEX = 0
		const val STRENGTH_UNLIT_INDEX = 1
		const val CONTAINER_DATA_SIZE = 2

		const val STRENGTH_GREEN_NBT = "StrengthGreen"
		const val STRENGTH_RED_NBT = "StrengthRed"
	}

}