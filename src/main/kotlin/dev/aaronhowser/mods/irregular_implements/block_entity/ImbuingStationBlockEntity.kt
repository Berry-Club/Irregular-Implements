package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.container.ContainerContainer
import dev.aaronhowser.mods.aaron.container.ImprovedSimpleContainer
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.loadItems
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.saveItems
import dev.aaronhowser.mods.irregular_implements.menu.imbuing_station.ImbuingStationMenu
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingInput
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.wrapper.InvWrapper
import net.neoforged.neoforge.items.wrapper.RangedWrapper

class ImbuingStationBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntityTypes.IMBUING_STATION.get(), pPos, pBlockState), MenuProvider, ContainerContainer {

	// Machine stuff

	private val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)
	private val invWrapper = InvWrapper(container)

	override fun getContainers(): List<Container> {
		return listOf(container)
	}

	fun getItemHandler(direction: Direction?): IItemHandler? {
		return when (direction) {
			Direction.UP -> RangedWrapper(invWrapper, CENTER_SLOT_INDEX, CENTER_SLOT_INDEX + 1)
			Direction.DOWN, null -> RangedWrapper(invWrapper, OUTPUT_SLOT_INDEX, OUTPUT_SLOT_INDEX + 1)

			else -> RangedWrapper(invWrapper, MIN_OUTER_INDEX, MAX_OUTER_INDEX + 1)
		}
	}

	private var progress: Int = 0
		set(value) {
			if (field == value) return
			field = value
			setChanged()
		}

	private fun tick() {
		if (!hasRecipe()) {
			progress = 0
			return
		}

		progress++

		if (progress >= MAX_PROGRESS) {
			progress = 0
			craftItem()
		}
	}

	private fun craftItem() {
		val topStack = container.getItem(TOP_SLOT_INDEX)
		val leftStack = container.getItem(LEFT_SLOT_INDEX)
		val bottomStack = container.getItem(BOTTOM_SLOT_INDEX)

		val centerStack = container.getItem(CENTER_SLOT_INDEX)
		val outerStacks = listOf(topStack, leftStack, bottomStack)

		val imbuingInput = ImbuingInput(outerStacks, centerStack)

		val imbuingRecipe = ImbuingRecipe.getRecipe(level!!, imbuingInput) ?: return

		val outputStack = imbuingRecipe.assemble(imbuingInput, level!!.registryAccess())
		val stackInOutput = container.getItem(OUTPUT_SLOT_INDEX)

		if (stackInOutput.isEmpty) {
			container.setItem(OUTPUT_SLOT_INDEX, outputStack)
		} else {
			stackInOutput.grow(outputStack.count)
		}

		container.removeItem(TOP_SLOT_INDEX, 1)
		container.removeItem(LEFT_SLOT_INDEX, 1)
		container.removeItem(BOTTOM_SLOT_INDEX, 1)
		container.removeItem(CENTER_SLOT_INDEX, 1)

		progress = 0
	}

	private fun hasRecipe(): Boolean {
		val topStack = container.getItem(TOP_SLOT_INDEX)
		val leftStack = container.getItem(LEFT_SLOT_INDEX)
		val bottomStack = container.getItem(BOTTOM_SLOT_INDEX)

		val centerStack = container.getItem(CENTER_SLOT_INDEX)
		val outerStacks = listOf(topStack, leftStack, bottomStack)

		val imbuingInput = ImbuingInput(outerStacks, centerStack)

		val recipe = ImbuingRecipe.getRecipe(level!!, imbuingInput) ?: return false

		val stackInOutput = container.getItem(OUTPUT_SLOT_INDEX)

		if (stackInOutput.isEmpty) return true

		val recipeOutput = recipe.getResultItem(level!!.registryAccess())

		val outputCanFit = ItemStack.isSameItemSameComponents(recipeOutput, stackInOutput)
				&& stackInOutput.count + recipeOutput.count <= stackInOutput.maxStackSize

		return outputCanFit
	}

	// Menu stuff

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return ImbuingStationMenu(containerId, playerInventory, container)
	}

	override fun getDisplayName(): Component {
		return blockState.block.name
	}

	// Saving

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putInt(CRAFT_PROGRESS_NBT, progress)
		tag.saveItems(container, registries)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		progress = tag.getInt(CRAFT_PROGRESS_NBT)
		tag.loadItems(container, registries)
	}

	companion object {
		const val CONTAINER_SIZE = 5

		const val TOP_SLOT_INDEX = 0
		const val LEFT_SLOT_INDEX = 1
		const val BOTTOM_SLOT_INDEX = 2
		const val CENTER_SLOT_INDEX = 3
		const val OUTPUT_SLOT_INDEX = 4

		val MIN_OUTER_INDEX = minOf(TOP_SLOT_INDEX, LEFT_SLOT_INDEX, BOTTOM_SLOT_INDEX)
		val MAX_OUTER_INDEX = maxOf(TOP_SLOT_INDEX, LEFT_SLOT_INDEX, BOTTOM_SLOT_INDEX)

		const val CRAFT_PROGRESS_NBT = "CraftProgress"
		const val MAX_PROGRESS = 20 * 10

		fun tick(
			level: Level,
			pos: BlockPos,
			state: BlockState,
			blockEntity: ImbuingStationBlockEntity
		) {
			blockEntity.tick()
		}

		fun getItemCapability(imbuingStation: ImbuingStationBlockEntity, direction: Direction?): IItemHandler? {
			return imbuingStation.getItemHandler(direction)
		}

	}

}