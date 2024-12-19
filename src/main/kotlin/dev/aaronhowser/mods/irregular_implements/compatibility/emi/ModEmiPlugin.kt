package dev.aaronhowser.mods.irregular_implements.compatibility.emi

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.DiviningRodRecipes
import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.LubricateBootRecipes
import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.ModInformationRecipes
import dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe.ModInteractionRecipes
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry

@EmiEntrypoint
class ModEmiPlugin : EmiPlugin {

    override fun register(registry: EmiRegistry) {
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

}