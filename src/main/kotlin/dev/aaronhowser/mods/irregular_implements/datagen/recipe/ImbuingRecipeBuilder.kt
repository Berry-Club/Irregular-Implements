package dev.aaronhowser.mods.irregular_implements.datagen.recipe

import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class ImbuingRecipeBuilder(
	val outerIngredients: List<Ingredient>,
	val centerIngredient: Ingredient,
	val outputStack: ItemStack,
) : RecipeBuilder {

	private val criteria: MutableMap<String, Criterion<*>> = mutableMapOf()

	override fun unlockedBy(name: String, criterion: Criterion<*>): RecipeBuilder {
		criteria[name] = criterion
		return this
	}

	override fun group(p0: String?): RecipeBuilder {
		error("Unsupported")
	}

	override fun getResult(): Item {
		return outputStack.item
	}

	override fun save(recipeOutput: RecipeOutput, defaultId: ResourceLocation) {
		val idString = StringBuilder()

		idString
			.append("imbuing/")
			.append(defaultId.path)

		val id = OtherUtil.modResource(idString.toString())

		val advancement = recipeOutput.advancement()
			.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
			.rewards(AdvancementRewards.Builder.recipe(id))
			.requirements(AdvancementRequirements.Strategy.OR)

		for (criterion in criteria) {
			advancement.addCriterion(criterion.key, criterion.value)
		}

		val recipe = ImbuingRecipe(
			outerIngredients,
			centerIngredient,
			outputStack
		)

		recipeOutput.accept(id, recipe, advancement.build(id.withPrefix("recipes/")))
	}
}