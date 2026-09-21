package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.container.ContainerContainer
import dev.aaronhowser.mods.aaron.container.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.menu.ender_energy_distributor.EnderEnergyDistributorMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.GlobalPos
import net.minecraft.network.chat.Component
import net.minecraft.world.Container
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.energy.IEnergyStorage

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")    // ???? complains about it being `Direction?` instead of `@Nullable Direction`
class EnderEnergyDistributorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.ENDER_ENERGY_DISTRIBUTOR.get(), pos, blockState), MenuProvider, ContainerContainer {

	// Inventory stuff

	private val container: ImprovedSimpleContainer = EnderEnergyDistributorContainer()

	override fun getContainers(): List<Container> {
		return listOf(container)
	}

	override fun getDisplayName(): Component = blockState.block.name

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return EnderEnergyDistributorMenu(containerId, playerInventory, container)
	}

	fun tick() {
		val tick = this.level?.gameTime ?: return
		if (tick % 20L == 0L) {
			recalculateCache()
		}
	}

	// Energy stuff

	private val energyStorage: IEnergyStorage = DistributedEnergyStorage()

	private val energyCache: MutableList<BlockEntity> = mutableListOf()
	private fun getCachedEnergyHandlers(): List<IEnergyStorage> {
		val level = this.level ?: return emptyList()

		return energyCache
			.asSequence()
			.filterNot(BlockEntity::isRemoved)
			.mapNotNull {
				EnergyDistributorBlockEntity.DIRECTIONS_OR_NULL.firstNotNullOfOrNull { dir ->
					level.getCapability(Capabilities.EnergyStorage.BLOCK, it.blockPos, dir)
				}
			}
			.toList()
	}

	private fun recalculateCache() {
		val level = this.level ?: return

		energyCache.clear()

		val blockEntities = container.items
			.asSequence()

			.mapNotNull { it.get(ModDataComponents.GLOBAL_POS) }
			.filter { it.dimension == level.dimension() }
			.map(GlobalPos::pos)

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

		fun getEnergyCapability(energyDistributor: EnderEnergyDistributorBlockEntity, direction: Direction?): IEnergyStorage {
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

	private inner class EnderEnergyDistributorContainer : ImprovedSimpleContainer(this, INVENTORY_SIZE) {

		override fun canPlaceItem(slot: Int, stack: ItemStack): Boolean {
			return stack.has(ModDataComponents.GLOBAL_POS)
		}

	}

	private inner class DistributedEnergyStorage : IEnergyStorage {

		private fun getExtractableDestinations(): List<IEnergyStorage> {
			return getCachedEnergyHandlers().filter(IEnergyStorage::canExtract)
		}

		private fun getInsertableDestinations(): List<IEnergyStorage> {
			return getCachedEnergyHandlers().filter(IEnergyStorage::canReceive)
		}

		override fun receiveEnergy(toReceive: Int, simulate: Boolean): Int {
			val destinations = getInsertableDestinations()
			var amountReceived = 0

			for (destination in destinations) {
				if (amountReceived >= toReceive) break

				val received = destination.receiveEnergy(toReceive - amountReceived, simulate)
				amountReceived += received
			}

			return amountReceived
		}

		override fun extractEnergy(toExtract: Int, simulate: Boolean): Int {
			val destinations = getExtractableDestinations()
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
}