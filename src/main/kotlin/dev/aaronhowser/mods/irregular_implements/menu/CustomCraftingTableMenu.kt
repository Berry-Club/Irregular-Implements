package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.RecipeBookMenu
import net.minecraft.world.inventory.RecipeBookType
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.RecipeHolder

class CustomCraftingTableMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val access: ContainerLevelAccess
) : RecipeBookMenu<CraftingInput, CraftingRecipe>(ModMenuTypes.CUSTOM_CRAFTING_TABLE.get(), containerId) {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                ContainerLevelAccess.NULL
            )

    private fun handleCraftingSlot(player: Player, stackInSlot: ItemStack, itemStack: ItemStack, slot: Slot): Boolean {
        access.execute { level, pos -> stackInSlot.item.onCraftedBy(stackInSlot, level, player) }

        if (!this.moveItemStackTo(
                stackInSlot,
                10,
                46,
                true
            )
        ) return false

        slot.onQuickCraft(stackInSlot, itemStack)

        return true
    }

    private fun handleInventorySlot(stackInSlot: ItemStack, index: Int): Boolean {
        if (!this.moveItemStackTo(stackInSlot, 1, 10, false)) {
            return if (index < 37) {
                !this.moveItemStackTo(stackInSlot, 37, 46, false)
            } else {
                !this.moveItemStackTo(stackInSlot, 10, 37, false)
            }
        }

        return true
    }

    private fun finalizeSlotState(
        stackInSlot: ItemStack,
        itemStack: ItemStack,
        slot: Slot,
        player: Player,
        index: Int
    ): ItemStack {

        if (stackInSlot.isEmpty) {
            slot.set(ItemStack.EMPTY)
        } else {
            slot.setChanged()
        }

        if (stackInSlot.count == itemStack.count) return ItemStack.EMPTY

        slot.onTake(player, stackInSlot)
        if (index == 0) {
            player.drop(stackInSlot, false)
        }

        return itemStack
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var itemstack = ItemStack.EMPTY

        val slot = slots.getOrNull(index)

        if (slot == null || !slot.hasItem()) return itemstack

        val stackInSlot = slot.item
        itemstack = stackInSlot.copy()

        when (index) {
            0 -> if (!handleCraftingSlot(player, stackInSlot, itemstack, slot)) return ItemStack.EMPTY
            in 10..45 -> if (!handleInventorySlot(stackInSlot, index)) return ItemStack.EMPTY
            else -> if (!this.moveItemStackTo(stackInSlot, 10, 46, false)) return ItemStack.EMPTY
        }

        return finalizeSlotState(stackInSlot, itemstack, slot, player, index)
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(this.access, player, ModBlocks.CUSTOM_CRAFTING_TABLE.get())
    }

    override fun fillCraftSlotsStackedContents(itemHelper: StackedContents) {
        TODO("Not yet implemented")
    }

    override fun clearCraftingContent() {
        TODO("Not yet implemented")
    }

    override fun getResultSlotIndex(): Int {
        TODO("Not yet implemented")
    }

    override fun getGridWidth(): Int {
        TODO("Not yet implemented")
    }

    override fun getGridHeight(): Int {
        TODO("Not yet implemented")
    }

    override fun getSize(): Int {
        TODO("Not yet implemented")
    }

    override fun getRecipeBookType(): RecipeBookType {
        TODO("Not yet implemented")
    }

    override fun shouldMoveToInventory(slotIndex: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun recipeMatches(recipe: RecipeHolder<CraftingRecipe>): Boolean {
        TODO("Not yet implemented")
    }

}