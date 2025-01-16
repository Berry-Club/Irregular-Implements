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
    private val otherItem: ItemStack,
    private val stages: List<Stage>
) : EmiRecipe {

    class Stage(
        val inputItem: ItemStack,
        val outputItem: ItemStack
    )

    class Builder {
        private val stages: MutableList<Stage> = mutableListOf()
        private var otherItem: ItemStack = ItemStack.EMPTY

        fun otherItem(item: ItemStack): Builder {
            this.otherItem = item
            return this
        }

        fun addStage(inputItem: ItemStack, outputItem: ItemStack): Builder {
            this.stages.add(Stage(inputItem, outputItem))
            return this
        }

        fun build(id: ResourceLocation): MutatingEmiRecipe {
            return MutatingEmiRecipe(id, otherItem, stages)
        }
    }

    private val uniqueId = Random().nextInt()

    override fun getCategory(): EmiRecipeCategory {
        return VanillaEmiRecipeCategories.CRAFTING
    }

    override fun getId(): ResourceLocation {
        return id
    }

    private val changingInputs = stages.map { it.inputItem }
    private val changingOutput = stages.map { it.outputItem }

    override fun getInputs(): List<EmiIngredient> {
        return (changingInputs + otherItem).map { EmiIngredient.of(Ingredient.of(it)) }
    }

    override fun getOutputs(): List<EmiStack> {
        return changingOutput.map { EmiStack.of(it) }
    }

    override fun getDisplayWidth(): Int {
        return 118
    }

    override fun getDisplayHeight(): Int {
        return 54
    }

    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 60, 18)
        widgets.addTexture(EmiTexture.SHAPELESS, 97, 0)

        for (i in 0 until 9) {
            val x = i % 3 * 18
            val y = i / 3 * 18

            when (i) {
                0 -> widgets.addGeneratedSlot({ random -> getStartItem(random) }, uniqueId, x, y)

                1 -> widgets.addSlot(EmiStack.of(otherItem), x, y)

                else -> widgets.addSlot(EmiStack.of(ItemStack.EMPTY), x, y)
            }
        }

        widgets.addGeneratedSlot({ random -> getOutputItem(random) }, uniqueId, 92, 14)
            .large(true)
            .recipeContext(this)
    }

    private fun getStartItem(random: Random): EmiStack {
        val stage = stages[random.nextInt(stages.size)]

        return EmiStack.of(stage.inputItem)
    }

    private fun getOutputItem(random: Random): EmiStack {
        val stage = stages[random.nextInt(stages.size)]

        return EmiStack.of(stage.outputItem)
    }

}