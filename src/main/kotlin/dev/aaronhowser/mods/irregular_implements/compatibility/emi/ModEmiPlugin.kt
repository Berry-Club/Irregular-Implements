package dev.aaronhowser.mods.irregular_implements.compatibility.emi

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.DiviningRodRecipes
import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.LubricateBootRecipes
import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.ModInformationRecipes
import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.ModInteractionRecipes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.stack.Comparison
import dev.emi.emi.api.stack.EmiStack

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

        for (bootRecipe in LubricateBootRecipes.getRecipes()) {
            registry.addRecipe(bootRecipe)
        }

        for (diviningRodRecipe in DiviningRodRecipes.getRecipes()) {
            registry.addRecipe(diviningRodRecipe)
        }

    }

    private fun setComparisons(registry: EmiRegistry) {

        registry.setDefaultComparison(EmiStack.of(ModItems.DIVINING_ROD), Comparison.compareComponents())
    }

}