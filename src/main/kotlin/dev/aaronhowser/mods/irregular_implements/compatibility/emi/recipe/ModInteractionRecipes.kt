package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Blocks

object ModInteractionRecipes {

    fun getInteractionRecipes(): List<EmiWorldInteractionRecipe> {
        return grassSeeds()
    }

    private fun grassSeeds(): List<EmiWorldInteractionRecipe> {
        val seeds = DyeColor.entries.map { GrassSeedItem.getFromColor(it) } + ModItems.GRASS_SEEDS

        return seeds.map { deferred ->
            val seedItem = deferred.get()

            val colorString = seedItem.dyeColor?.getName()

            val id = if (colorString == null) {
                OtherUtil.modResource("grass")
            } else {
                OtherUtil.modResource("grass/$colorString")
            }

            EmiWorldInteractionRecipe
                .builder()
                .leftInput(EmiIngredient.of(Ingredient.of(Blocks.DIRT)))
                .rightInput(EmiIngredient.of(Ingredient.of(seedItem)), true)
                .output(EmiStack.of(seedItem.resultBlock))
                .id(id)
                .build()
        }
    }

}