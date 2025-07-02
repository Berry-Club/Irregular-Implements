package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.energy.IEnergyStorage

class EnderEnergyDistributorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.ENDER_ENERGY_DISTRIBUTOR.get(), pos, blockState) {

	fun tick() {

	}

	companion object {
		fun getCapability(energyDistributor: EnergyDistributorBlockEntity, direction: Direction?): IEnergyStorage? {
			return energyDistributor.getEnergyHandler(direction)
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: EnderEnergyDistributorBlockEntity
		) {
			blockEntity.tick()
		}
	}
}