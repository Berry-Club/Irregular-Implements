package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.item.crafting.RecipeManager

class ImbuingEmiRecipe(
	val recipe: RecipeHolder<ImbuingRecipe>
) : EmiRecipe {

	private val inputs = recipe.value.ingredients.map(EmiIngredient::of)
	private val outputs = listOf(EmiStack.of(recipe.value.output))

	override fun getCategory(): EmiRecipeCategory = ModEmiPlugin.IMBUING_CATEGORY

	override fun getId(): ResourceLocation = recipe.id

	override fun getInputs(): List<EmiIngredient> = inputs
	override fun getOutputs(): List<EmiStack> = outputs

	override fun getDisplayWidth(): Int = 75
	override fun getDisplayHeight(): Int = 61

	override fun addWidgets(widgets: WidgetHolder) {}

	companion object {
		fun getAllRecipes(recipeManager: RecipeManager): List<ImbuingEmiRecipe> {
			val actualRecipes = ImbuingRecipe.getAllRecipes(recipeManager)
			return actualRecipes.map(::ImbuingEmiRecipe)
		}
	}

}