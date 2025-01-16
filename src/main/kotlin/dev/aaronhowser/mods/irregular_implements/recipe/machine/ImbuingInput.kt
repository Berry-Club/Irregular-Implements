package dev.aaronhowser.mods.irregular_implements.recipe.machine

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput

class ImbuingInput(
    private val outerItems: List<ItemStack>,
    private val centerItem: ItemStack
) : RecipeInput {

    fun getOuterItems(): List<ItemStack> {
        return outerItems.map { it.copy() }
    }

    fun getCenterItem(): ItemStack {
        return centerItem.copy()
    }

    override fun getItem(index: Int): ItemStack {
        return when (index) {
            in outerItems.indices -> outerItems[index].copy()
            outerItems.size -> centerItem.copy()
            else -> error("Invalid index $index")
        }
    }

    override fun size(): Int {
        return outerItems.size + 1
    }
}