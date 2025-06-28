package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin.Companion.emiStack
import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories
import dev.emi.emi.api.render.EmiTexture
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import kotlin.random.Random

class EmiAnvilRecipe(
	private val baseItem: EmiIngredient,
	private val resource: EmiIngredient,
	private val result: Item,
	private val id: ResourceLocation
) : EmiRecipe {

	override fun getCategory(): EmiRecipeCategory {
		return VanillaEmiRecipeCategories.ANVIL_REPAIRING
	}

	override fun getId(): ResourceLocation {
		return id
	}

	override fun getInputs(): List<EmiIngredient> {
		return listOf(baseItem, resource)
	}

	override fun getOutputs(): List<EmiStack> {
		return listOf(result.emiStack)
	}

	override fun supportsRecipeTree(): Boolean {
		return true
	}

	override fun getDisplayWidth(): Int {
		return 125
	}

	override fun getDisplayHeight(): Int {
		return 18
	}

	override fun addWidgets(widgets: WidgetHolder) {
		widgets.addTexture(EmiTexture.PLUS, 27, 3)
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 75, 1)

		val randomInt = Random.nextInt()

		//FIXME: fix only the first one showing (implement the random somewhere?)
		widgets.addGeneratedSlot({ baseItem.emiStacks.first() }, randomInt, 0, 0)
		widgets.addGeneratedSlot({ resource.emiStacks.first() }, randomInt, 49, 0)
		widgets.addGeneratedSlot({ result.emiStack }, randomInt, 107, 0).recipeContext(this)

	}

}
