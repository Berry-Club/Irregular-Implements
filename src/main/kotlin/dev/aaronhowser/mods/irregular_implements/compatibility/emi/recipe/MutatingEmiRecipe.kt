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
    private val stages: List<Stage>,
    private val virtualInput: List<EmiIngredient>
) : EmiRecipe {

    class Stage(
        val inputItemGrid: List<ItemStack>,
        val outputStack: ItemStack
    )

    class Builder {
        private val stages: MutableList<Stage> = mutableListOf()

        private val actualInputs: MutableList<EmiIngredient> = mutableListOf()

        fun addStage(inputItems: List<ItemStack>, outputItem: ItemStack): Builder {
            this.stages.add(Stage(inputItems, outputItem))
            return this
        }

        fun virtualInput(vararg input: ItemStack): Builder {
            this.actualInputs.add(EmiIngredient.of(Ingredient.of(*input)))
            return this
        }

        fun build(id: ResourceLocation): MutatingEmiRecipe {
            return MutatingEmiRecipe(id, stages, actualInputs)
        }
    }

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

        return stages.flatMap { stage ->
            stage.inputItemGrid.map { EmiIngredient.of(Ingredient.of(it)) }
        }
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

            widgets.addGeneratedSlot(
                { random -> getInputStack(random, i) },
                uniqueId,
                x,
                y
            )
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

    private fun getInputStack(random: Random, index: Int): EmiStack {
        val stage = stages[random.nextInt(stages.size)]
        val stack = stage.inputItemGrid.getOrElse(index) { ItemStack.EMPTY }

        return EmiStack.of(stack)
    }

    private fun getOutputStack(random: Random): EmiStack {
        val stage = stages[random.nextInt(stages.size)]

        return EmiStack.of(stage.outputStack)
    }

}