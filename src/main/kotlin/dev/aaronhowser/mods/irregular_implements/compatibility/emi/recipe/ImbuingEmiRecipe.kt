package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
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

	private val centerSlot = inputs.last()
	private val outerSlots = inputs.subList(0, inputs.size - 1)

	override fun getCategory(): EmiRecipeCategory = ModEmiPlugin.IMBUING_CATEGORY

	override fun getId(): ResourceLocation = recipe.id

	override fun getInputs(): List<EmiIngredient> = inputs
	override fun getOutputs(): List<EmiStack> = outputs

	override fun getDisplayWidth(): Int = 64
	override fun getDisplayHeight(): Int = 64

	override fun addWidgets(widgets: WidgetHolder) {
		widgets.addTexture(BACKGROUND, 0, 0, 64, 64, 0, 0)

		widgets.addSlot(centerSlot, 16, 16).drawBack(false)
		widgets.addSlot(outerSlots[0], 16, 0).drawBack(false)
		widgets.addSlot(outerSlots[1], 0, 16).drawBack(false)
		widgets.addSlot(outerSlots[2], 16, 32).drawBack(false)

		widgets.addSlot(outputs.first(), 32, 16).drawBack(false).recipeContext(this)
	}

	companion object {
		val BACKGROUND: ResourceLocation = OtherUtil.modResource("textures/gui/imbuing_station_emi.png")

		fun getAllRecipes(recipeManager: RecipeManager): List<ImbuingEmiRecipe> {
			val actualRecipes = ImbuingRecipe.getAllRecipes(recipeManager)
			return actualRecipes.map(::ImbuingEmiRecipe)
		}
	}

}