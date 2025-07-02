package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.EnergyDistributorBlock
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.item.component.LocationDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.energy.IEnergyStorage

class EnderEnergyDistributorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.ENDER_ENERGY_DISTRIBUTOR.get(), pos, blockState) {

	// Inventory stuff

	private val container: ImprovedSimpleContainer = ImprovedSimpleContainer(this, INVENTORY_SIZE)

	// Energy stuff

	private val energyStorage: IEnergyStorage = object : IEnergyStorage {

		fun extractableDestinations(): List<IEnergyStorage> = getCachedEnergyHandlers().filter(IEnergyStorage::canExtract)
		fun insertableDestinations(): List<IEnergyStorage> = getCachedEnergyHandlers().filter(IEnergyStorage::canReceive)

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

		override fun getEnergyStored(): Int = getCachedEnergyHandlers().sumOf(IEnergyStorage::getEnergyStored)
		override fun getMaxEnergyStored(): Int = getCachedEnergyHandlers().sumOf(IEnergyStorage::getMaxEnergyStored)
		override fun canExtract(): Boolean = getCachedEnergyHandlers().any(IEnergyStorage::canExtract)
		override fun canReceive(): Boolean = getCachedEnergyHandlers().any(IEnergyStorage::canReceive)
	}

	private val energyCache: MutableList<BlockEntity> = mutableListOf()
	private fun getCachedEnergyHandlers(): List<IEnergyStorage> {
		val level = this.level ?: return emptyList()
		val fromMyDirection = this.blockState.getValue(EnergyDistributorBlock.FACING).opposite

		return energyCache
			.asSequence()
			.filterNot(BlockEntity::isRemoved)
			.mapNotNull { level.getCapability(Capabilities.EnergyStorage.BLOCK, it.blockPos, fromMyDirection) }
			.toList()
	}

	fun tick() {
		val tick = this.level?.gameTime ?: return
		if (tick % 20L == 0L) {
			recalculateCache()
		}
	}

	private fun recalculateCache() {
		val level = this.level ?: return

		energyCache.clear()

		val blockEntities = container.items
			.asSequence()

			.mapNotNull { it.get(ModDataComponents.LOCATION) }
			.filter { it.dimension == level.dimension() }
			.map(LocationDataComponent::blockPos)

			.mapNotNull { level.getBlockEntity(it) }
			.filterNot(BlockEntity::isRemoved)
			.filter {
				EnergyDistributorBlockEntity.DIRECTIONS_OR_NULL
					.any { direction ->
						level.getCapability(Capabilities.EnergyStorage.BLOCK, it.blockPos, direction) != null
					}
			}
			.toList()

		energyCache.addAll(blockEntities)
	}

	fun getEnergyHandler(direction: Direction?): IEnergyStorage {
		return energyStorage
	}

	companion object {
		const val INVENTORY_SIZE = 8

		fun getCapability(energyDistributor: EnderEnergyDistributorBlockEntity, direction: Direction?): IEnergyStorage? {
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