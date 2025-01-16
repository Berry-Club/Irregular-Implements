package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class MutatingWithConstantEmiRecipe(
    private val id: ResourceLocation,
    private val mutatingInput: List<ItemStack>,
    private val constantStack: ItemStack,
    private val mutatingOutput: List<ItemStack>,
    private val virtualInput: List<EmiIngredient>
) : EmiRecipe {

    override fun getCategory(): EmiRecipeCategory {
        return VanillaEmiRecipeCategories.CRAFTING
    }

    override fun getId(): ResourceLocation {
        return id
    }

    override fun getInputs(): List<EmiIngredient> {
        if (this.virtualInput.isNotEmpty()) {
            return virtualInput
        }

        return (mutatingInput + constantStack).map { EmiIngredient.of(Ingredient.of(it)) }
    }

    override fun getOutputs(): List<EmiStack> {
        return emptyList()
    }

    override fun getDisplayWidth(): Int {
        return 118
    }

    override fun getDisplayHeight(): Int {
        return 54
    }

    override fun supportsRecipeTree(): Boolean {
        return false
    }

    override fun addWidgets(widgets: WidgetHolder) {
        TODO("Not yet implemented")
    }

}