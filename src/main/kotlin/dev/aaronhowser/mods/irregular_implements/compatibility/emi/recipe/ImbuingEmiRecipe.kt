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
		widgets.addTexture(
			BACKGROUND,
			0, 0,
			64, 64,
			0, 0,
			64, 64,
			64, 64
		)

		val topY = 3
		val middleY = 23
		val bottomY = 43

		val leftX = 3
		val middleX = 23
		val rightX = 43

		widgets.addSlot(outerSlots[0], middleX, topY).drawBack(false)
		widgets.addSlot(outerSlots[1], leftX, middleY).drawBack(false)
		widgets.addSlot(outerSlots[2], middleX, bottomY).drawBack(false)

		widgets.addSlot(centerSlot, middleX, middleY).drawBack(false)

		widgets.addSlot(outputs.first(), rightX, middleY).drawBack(false).recipeContext(this)
	}

	companion object {
		val BACKGROUND: ResourceLocation = OtherUtil.modResource("textures/gui/imbuing_station_emi.png")

		fun getAllRecipes(recipeManager: RecipeManager): List<ImbuingEmiRecipe> {
			val actualRecipes = ImbuingRecipe.getAllRecipes(recipeManager)
			return actualRecipes.map(::ImbuingEmiRecipe)
		}
	}

}