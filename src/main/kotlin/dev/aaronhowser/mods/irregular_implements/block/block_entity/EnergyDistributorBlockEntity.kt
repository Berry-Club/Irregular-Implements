package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.EnergyDistributorBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.energy.IEnergyStorage

class EnergyDistributorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.ENERGY_DISTRIBUTOR.get(), pos, blockState) {

	@Suppress("UsePropertyAccessSyntax")
	private val energyStorage: IEnergyStorage = object : IEnergyStorage {

		fun extractableDestinations(): List<IEnergyStorage> = energyCache.filter(IEnergyStorage::canExtract)
		fun insertableDestinations(): List<IEnergyStorage> = energyCache.filter(IEnergyStorage::canReceive)

		override fun receiveEnergy(toReceive: Int, simulate: Boolean): Int {
			val destinations = insertableDestinations()

			var amountReceived = 0

			for (destination in destinations) {
				if (amountReceived >= toReceive) break

				val received = destination.receiveEnergy(toReceive - amountReceived, simulate)
				amountReceived += received
			}

			return amountReceived
		}

		override fun extractEnergy(toExtract: Int, simulate: Boolean): Int {
			val destinations = extractableDestinations()

			var amountExtracted = 0

			for (destination in destinations) {
				if (amountExtracted >= toExtract) break

				val extracted = destination.extractEnergy(toExtract - amountExtracted, simulate)
				amountExtracted += extracted
			}

			return amountExtracted
		}

		override fun getEnergyStored(): Int = getDestinations().sumOf(IEnergyStorage::getEnergyStored)
		override fun getMaxEnergyStored(): Int = getDestinations().sumOf(IEnergyStorage::getMaxEnergyStored)
		override fun canExtract(): Boolean = getDestinations().any(IEnergyStorage::canExtract)
		override fun canReceive(): Boolean = getDestinations().any(IEnergyStorage::canReceive)
	}

	private val energyCache: MutableList<IEnergyStorage> = mutableListOf()

	fun tick() {
		val tick = this.level?.gameTime ?: return
		if (tick % 20L == 0L) {
			recalculateCache()
		}
	}

	private fun recalculateCache() {
		energyCache.clear()
		energyCache.addAll(getDestinations())
	}

	private fun getDestinations(): List<IEnergyStorage> {
		val level = this.level ?: return emptyList()

		val direction = blockState.getValue(EnergyDistributorBlock.FACING)
		val list = mutableListOf<IEnergyStorage>()

		var checkedPos = this.worldPosition.relative(direction)

		while (level.isLoaded(checkedPos) && list.size < 100) {
			val energyStorageThere = level.getCapability(Capabilities.EnergyStorage.BLOCK, checkedPos, direction.opposite)
			if (energyStorageThere != null) {
				list.add(energyStorageThere)
				checkedPos = checkedPos.relative(direction)
			} else {
				break
			}
		}

		return list
	}

	fun getEnergyHandler(direction: Direction?): IEnergyStorage {
		return energyStorage
	}

	companion object {
		fun getCapability(energyDistributor: EnergyDistributorBlockEntity, direction: Direction?): IEnergyStorage? {
			return energyDistributor.getEnergyHandler(direction)
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: EnergyDistributorBlockEntity
		) {
			blockEntity.tick()
		}
	}
}