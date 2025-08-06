package dev.aaronhowser.mods.irregular_implements.compatibility.emi

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.*
import dev.aaronhowser.mods.irregular_implements.datagen.ModRecipeProvider.Companion.asIngredient
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.stack.Comparison
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike

@EmiEntrypoint
class ModEmiPlugin : EmiPlugin {

	override fun register(registry: EmiRegistry) {
		setComparisons(registry)

		for (infoRecipe in ModInformationRecipes.getInformationRecipes()) {
			registry.addRecipe(infoRecipe)
		}

		for (interactionRecipe in ModInteractionRecipes.getInteractionRecipes()) {
			registry.addRecipe(interactionRecipe)
		}

		for (bootRecipe in MutatingRecipes.getRecipes()) {
			registry.addRecipe(bootRecipe)
		}

		for (diviningRodRecipe in DiviningRodRecipes.getRecipes()) {
			registry.addRecipe(diviningRodRecipe)
		}

		for (anvilRecipe in AnvilRecipes.getRecipes()) {
			registry.addRecipe(anvilRecipe)
		}

	}

	private fun setComparisons(registry: EmiRegistry) {
		registry.setDefaultComparison(EmiStack.of(ModItems.DIVINING_ROD), Comparison.compareComponents())
	}

	companion object {
		fun ItemLike.asEmiStack(): EmiStack = EmiStack.of(this.asItem())
		fun Ingredient.asEmiIngredient(): EmiIngredient = EmiIngredient.of(this)
		fun ItemLike.asEmiIngredient(): EmiIngredient = EmiIngredient.of(this.asIngredient())
		fun TagKey<Item>.asEmiIngredient(): EmiIngredient = EmiIngredient.of(this)
	}

}