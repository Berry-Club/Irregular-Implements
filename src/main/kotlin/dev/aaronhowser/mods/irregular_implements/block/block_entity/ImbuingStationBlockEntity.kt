package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.menu.ImbuingStationMenu
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingInput
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

//TODO: Does automation work?
class ImbuingStationBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.IMBUING_STATION.get(), pPos, pBlockState), MenuProvider {

    companion object {
        const val CONTAINER_SIZE = 5

        const val TOP_SLOT_INDEX = 0
        const val LEFT_SLOT_INDEX = 1
        const val BOTTOM_SLOT_INDEX = 2
        const val CENTER_SLOT_INDEX = 3
        const val OUTPUT_SLOT_INDEX = 4

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
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putInt(CRAFT_PROGRESS_NBT, this.progress)
        ContainerHelper.saveAllItems(tag, this.container.items, registries)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        this.progress = tag.getInt(CRAFT_PROGRESS_NBT)
        ContainerHelper.loadAllItems(tag, this.container.items, registries)
    }

    // Machine stuff

    private val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)

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

        this.progress++

        if (progress >= MAX_PROGRESS) {
            this.progress = 0
            craftItem()
        }
    }

    private fun craftItem() {
        val topStack = this.container.getItem(TOP_SLOT_INDEX)
        val leftStack = this.container.getItem(LEFT_SLOT_INDEX)
        val bottomStack = this.container.getItem(BOTTOM_SLOT_INDEX)

        val centerStack = this.container.getItem(CENTER_SLOT_INDEX)
        val outerStacks = listOf(topStack, leftStack, bottomStack)

        val imbuingInput = ImbuingInput(outerStacks, centerStack)

        val imbuingRecipe = ImbuingRecipe.getRecipe(this.level!!, imbuingInput) ?: return

        val outputStack = imbuingRecipe.assemble(imbuingInput, this.level!!.registryAccess())
        val stackInOutput = this.container.getItem(OUTPUT_SLOT_INDEX)

        if (stackInOutput.isEmpty) {
            this.container.setItem(OUTPUT_SLOT_INDEX, outputStack)
        } else {
            stackInOutput.grow(outputStack.count)
        }

        this.container.removeItem(TOP_SLOT_INDEX, 1)
        this.container.removeItem(LEFT_SLOT_INDEX, 1)
        this.container.removeItem(BOTTOM_SLOT_INDEX, 1)
        this.container.removeItem(CENTER_SLOT_INDEX, 1)

        this.progress = 0
    }

    private fun hasRecipe(): Boolean {
        val topStack = this.container.getItem(TOP_SLOT_INDEX)
        val leftStack = this.container.getItem(LEFT_SLOT_INDEX)
        val bottomStack = this.container.getItem(BOTTOM_SLOT_INDEX)

        val centerStack = this.container.getItem(CENTER_SLOT_INDEX)
        val outerStacks = listOf(topStack, leftStack, bottomStack)

        val imbuingInput = ImbuingInput(outerStacks, centerStack)

        val recipe = ImbuingRecipe.getRecipe(this.level!!, imbuingInput) ?: return false

        val stackInOutput = this.container.getItem(OUTPUT_SLOT_INDEX)

        if (stackInOutput.isEmpty) return true

        val recipeOutput = recipe.getResultItem(this.level!!.registryAccess())

        val outputCanFit = ItemStack.isSameItemSameComponents(recipeOutput, stackInOutput)
                && stackInOutput.count + recipeOutput.count <= stackInOutput.maxStackSize

        return outputCanFit
    }

    // Menu stuff

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
        return ImbuingStationMenu(containerId, playerInventory, this.container)
    }

    override fun getDisplayName(): Component {
        return this.blockState.block.name
    }
}