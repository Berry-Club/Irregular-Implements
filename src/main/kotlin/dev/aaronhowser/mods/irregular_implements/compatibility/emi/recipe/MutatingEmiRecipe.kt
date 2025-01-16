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

class MutatingEmiRecipe(
    private val id: ResourceLocation,
    private val recipePattern: String,
    private val mutatingInput: List<ItemStack>,
    private val patternKeys: Map<Char, PatternValue>,
    private val mutatingOutput: List<ItemStack>,
    private val virtualInput: List<EmiIngredient>
) : EmiRecipe {

    init {
        require(recipePattern.length == 9) { "Recipe pattern must be 9 characters long" }
        require(mutatingInput.size == mutatingOutput.size) { "Input and output stacks must be the same size" }
    }

    sealed interface PatternValue {
        class IngredientValue(val ingredient: Ingredient) : PatternValue
        data object EmptyValue : PatternValue
        data object MutatingValue : PatternValue
    }

    class Builder {
        private var recipePattern: String = ""
        private val mutatingInput: MutableList<ItemStack> = mutableListOf()
        private val patternKeys: MutableMap<Char, PatternValue> = mutableMapOf()
        private val virtualInput: MutableList<EmiIngredient> = mutableListOf()
        private val mutatingOutput: MutableList<ItemStack> = mutableListOf()

        fun recipePattern(recipePattern: String): Builder {
            this.recipePattern = recipePattern.filterNot { it.isWhitespace() || it == ',' }
            return this
        }

        fun mutatingInput(vararg input: ItemStack): Builder {
            this.mutatingInput.addAll(input)
            return this
        }

        fun mutatingOutput(vararg output: ItemStack): Builder {
            this.mutatingOutput.addAll(output)
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

        fun build(id: ResourceLocation): MutatingEmiRecipe {
            return MutatingEmiRecipe(id, recipePattern, mutatingInput, patternKeys, mutatingOutput, virtualInput)
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

    private val uniqueId = Random().nextInt()

    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 60, 18)
        widgets.addTexture(EmiTexture.SHAPELESS, 97, 0)

        for (i in 0 until 9) {
            val x = i % 3 * 18
            val y = i / 3 * 18

            val patternChar = this.recipePattern.getOrNull(i) ?: continue
            val patternValue = this.patternKeys.getOrDefault(patternChar, null) ?: continue

            when (patternValue) {
                is PatternValue.EmptyValue -> widgets.addSlot(x, y)

                is PatternValue.IngredientValue -> widgets.addSlot(
                    EmiIngredient.of(patternValue.ingredient),
                    x,
                    y
                )

                is PatternValue.MutatingValue -> widgets.addGeneratedSlot(
                    { random -> getInputStack(random) },
                    uniqueId,
                    x,
                    y
                )
            }
        }

        widgets.addGeneratedSlot(
            { random -> getOutputStack(random) },
            uniqueId,
            92,
            14
        )
            .large(true)
            .recipeContext(this)
    }

    private fun getInputStack(random: Random): EmiStack {
        val randomIndex = random.nextInt(mutatingInput.size + 1)
        val stack = this.mutatingInput.getOrNull(randomIndex) ?: ItemStack.EMPTY

        return EmiStack.of(stack)
    }

    private fun getOutputStack(random: Random): EmiStack {
        val randomIndex = random.nextInt(mutatingOutput.size)
        val stack = this.mutatingOutput.getOrNull(randomIndex) ?: ItemStack.EMPTY

        return EmiStack.of(stack)
    }

}