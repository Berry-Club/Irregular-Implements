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

class MutatingWithConstantEmiRecipe(
    private val id: ResourceLocation,
    private val mutatingInput: List<ItemStack>,
    private val constantStack: ItemStack,
    private val mutatingOutput: List<ItemStack>,
    private val virtualInput: List<EmiIngredient>
) : EmiRecipe {

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

        return (mutatingInput + constantStack).map { EmiIngredient.of(Ingredient.of(it)) }
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

            when (i) {
                0 -> widgets.addGeneratedSlot(
                    { random -> getMutatingInputStack(random) },
                    uniqueId,
                    x,
                    y
                )

                1 ->
                    widgets.addSlot(
                        EmiStack.of(this.constantStack),
                        x,
                        y
                    )

                else -> widgets.addSlot(x, y)
            }
        }

        widgets.addGeneratedSlot(
            { random -> getMutatingOutputStack(random) },
            uniqueId,
            92,
            14
        )
            .large(true)
            .recipeContext(this)
    }

    private fun getMutatingInputStack(random: Random): EmiStack {
        val randomIndex = random.nextInt(mutatingInput.size + 1)
        val stack = this.mutatingInput.getOrNull(randomIndex) ?: ItemStack.EMPTY

        return EmiStack.of(stack)
    }

    private fun getMutatingOutputStack(random: Random): EmiStack {
        val randomIndex = random.nextInt(mutatingOutput.size)
        val stack = this.mutatingOutput.getOrNull(randomIndex) ?: ItemStack.EMPTY

        return EmiStack.of(stack)
    }

}