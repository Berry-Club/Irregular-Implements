package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories
import dev.emi.emi.api.render.EmiTexture
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.*

class MutatingEmiRecipe private constructor(
	private val id: ResourceLocation,
	private val recipePattern: String,
	private val mutations: Map<ItemStack, ItemStack>,
	private val patternKeys: Map<Char, PatternValue>,
	private val virtualInput: List<EmiIngredient>,
	private val virtualOutputs: List<ItemStack>
) : EmiRecipe {

	init {
		require(recipePattern.length <= 9) { "Recipe pattern must be 9 characters long or less" }
	}

	sealed interface PatternValue {
		class IngredientValue(val ingredient: Ingredient) : PatternValue {
			constructor(stack: ItemStack) : this(Ingredient.of(stack))
		}

		data object EmptyValue : PatternValue
		data object MutatingValue : PatternValue
	}

	class Builder {
		private var recipePattern: String = ""
		private val patternKeys: MutableMap<Char, PatternValue> = mutableMapOf()
		private val virtualInput: MutableList<EmiIngredient> = mutableListOf()
		private val mutations: MutableMap<ItemStack, ItemStack> = mutableMapOf()
		private val virtualOutputs: MutableList<ItemStack> = mutableListOf()

		fun recipePattern(recipePattern: String): Builder {
			this.recipePattern = recipePattern.filterNot { it == ',' }
			return this
		}

		fun associations(mutations: Map<ItemStack, ItemStack>): Builder {
			this.mutations.putAll(mutations)
			return this
		}

		fun patternKey(key: Char, patternValue: PatternValue): Builder {
			this.patternKeys[key] = patternValue
			return this
		}

		fun virtualInput(input: Ingredient): Builder {
			this.virtualInput.add(EmiIngredient.of(input))
			return this
		}

		fun virtualInput(itemStack: ItemStack): Builder {
			return virtualInput(Ingredient.of(itemStack))
		}

		fun virtualOutputs(vararg output: ItemStack): Builder {
			this.virtualOutputs.addAll(output)
			return this
		}

		fun build(id: ResourceLocation): MutatingEmiRecipe {
			return MutatingEmiRecipe(id, recipePattern, mutations, patternKeys, virtualInput, virtualOutputs)
		}
	}

	override fun getCategory(): EmiRecipeCategory {
		return VanillaEmiRecipeCategories.CRAFTING
	}

	override fun getId(): ResourceLocation {
		return id
	}

	override fun getInputs(): List<EmiIngredient> {
		return virtualInput
	}

	override fun getOutputs(): List<EmiStack> {
		return virtualOutputs.map { EmiStack.of(it) }
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

	private val uniqueId = Random().nextInt()

	@Suppress("MoveVariableDeclarationIntoWhen")
	override fun addWidgets(widgets: WidgetHolder) {
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 60, 18)
		widgets.addTexture(EmiTexture.SHAPELESS, 97, 0)

		for (i in 0 until 9) {
			val x = i % 3 * 18
			val y = i / 3 * 18

			val patternChar = this.recipePattern.getOrNull(i)
			val patternValue = this.patternKeys.getOrDefault(patternChar, PatternValue.EmptyValue)

			when (patternValue) {
				is PatternValue.EmptyValue -> widgets.addSlot(x, y)

				is PatternValue.IngredientValue -> widgets.addSlot(
					EmiIngredient.of(patternValue.ingredient),
					x,
					y
				)

				is PatternValue.MutatingValue -> widgets.addGeneratedSlot(
					{ random -> getMutatingStack(random, isInput = true) },
					uniqueId,
					x,
					y
				)
			}
		}

		widgets.addGeneratedSlot(
			{ random -> getMutatingStack(random, isInput = false) },
			uniqueId,
			92,
			14
		)
			.large(true)
			.recipeContext(this)
	}

	private fun getMutatingStack(random: Random, isInput: Boolean): EmiStack {
		val randomIndex = random.nextInt(mutations.size)
		val pair = mutations.entries.elementAt(randomIndex)

		return EmiStack.of(if (isInput) pair.key else pair.value)
	}

}