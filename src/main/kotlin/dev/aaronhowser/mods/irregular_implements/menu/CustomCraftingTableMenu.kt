package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.RecipeHolder

// This class is pretty much a blind copy of CraftingMenu
class CustomCraftingTableMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val access: ContainerLevelAccess
) : RecipeBookMenu<CraftingInput, CraftingRecipe>(ModMenuTypes.CUSTOM_CRAFTING_TABLE.get(), containerId) {

    private val craftSlots: CraftingContainer = TransientCraftingContainer(this, 3, 3)
    private val resultSlots = ResultContainer()

    private val player = playerInventory.player

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                ContainerLevelAccess.NULL
            )

    init {
        this.addSlot(
            ResultSlot(
                playerInventory.player,
                this.craftSlots,
                this.resultSlots,
                0,
                124,
                35
            )
        )

        for (row in 0..2) {
            for (column in 0..2) {
                val index = column + row * 3
                val x = 30 + column * 18
                val y = 17 + row * 18

                this.addSlot(Slot(this.craftSlots, index, x, y))
            }
        }

        for (row in 0..2) {
            for (column in 0..8) {
                val index = column + row * 9 + 9
                val x = 8 + column * 18
                val y = 84 + row * 18

                this.addSlot(Slot(playerInventory, index, x, y))
            }
        }

        for (index in 0..8) {
            val x = 8 + index * 18
            val y = 142

            this.addSlot(Slot(playerInventory, index, x, y))
        }
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var itemstack = ItemStack.EMPTY
        val slot = slots.getOrNull(index)

        if (slot != null && slot.hasItem()) {
            val itemstack1 = slot.item
            itemstack = itemstack1.copy()
            if (index == 0) {
                access.execute { level, pos -> itemstack1.item.onCraftedBy(itemstack1, level, player) }

                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY
                }

                slot.onQuickCraft(itemstack1, itemstack)
            } else if (index in 10..45) {

                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY
                    }
                }

            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY
            }

            if (itemstack1.isEmpty) {
                slot.setByPlayer(ItemStack.EMPTY)
            } else {
                slot.setChanged()
            }

            if (itemstack1.count == itemstack.count) {
                return ItemStack.EMPTY
            }

            slot.onTake(player, itemstack1)
            if (index == 0) {
                player.drop(itemstack1, false)
            }
        }

        return itemstack
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(this.access, player, ModBlocks.CUSTOM_CRAFTING_TABLE.get())
    }

    override fun fillCraftSlotsStackedContents(itemHelper: StackedContents) {
        this.craftSlots.fillStackedContents(itemHelper)
    }

    override fun clearCraftingContent() {
        this.craftSlots.clearContent()
        this.resultSlots.clearContent()
    }

    override fun getResultSlotIndex(): Int {
        return 0
    }

    override fun getGridWidth(): Int {
        return this.craftSlots.width
    }

    override fun getGridHeight(): Int {
        return this.craftSlots.height
    }

    override fun getSize(): Int {
        return 10
    }

    override fun getRecipeBookType(): RecipeBookType {
        return RecipeBookType.CRAFTING
    }

    override fun shouldMoveToInventory(slotIndex: Int): Boolean {
        return slotIndex != this.resultSlotIndex
    }

    override fun recipeMatches(recipe: RecipeHolder<CraftingRecipe>): Boolean {
        return recipe.value().matches(craftSlots.asCraftInput(), this.player.level())
    }

}