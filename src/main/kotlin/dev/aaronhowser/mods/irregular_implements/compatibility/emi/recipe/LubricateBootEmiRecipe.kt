package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.recipe.LubricateBootRecipe
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.SlotWidget
import net.minecraft.world.item.crafting.Ingredient

class LubricateBootEmiRecipe : EmiPatternCraftingRecipe(
    EmiIngredient.of(Ingredient.of(*LubricateBootRecipe.boots.toTypedArray())),
    EmiStack.of
) {

    override fun getInputWidget(slot: Int, x: Int, y: Int): SlotWidget {
        TODO("Not yet implemented")
    }

    override fun getOutputWidget(x: Int, y: Int): SlotWidget {
        TODO("Not yet implemented")
    }
}