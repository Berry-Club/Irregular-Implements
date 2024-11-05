package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Blocks

object ModInteractionRecipes {

    fun getInteractionRecipes(): List<EmiWorldInteractionRecipe> {
        return grassSeeds()
    }

    private fun grassSeeds(): List<EmiWorldInteractionRecipe> {
        return listOf(
            ModItems.GRASS_SEEDS,
            ModItems.GRASS_SEEDS_WHITE,
            ModItems.GRASS_SEEDS_ORANGE,
            ModItems.GRASS_SEEDS_MAGENTA,
            ModItems.GRASS_SEEDS_LIGHT_BLUE,
            ModItems.GRASS_SEEDS_YELLOW,
            ModItems.GRASS_SEEDS_LIME,
            ModItems.GRASS_SEEDS_PINK,
            ModItems.GRASS_SEEDS_GRAY,
            ModItems.GRASS_SEEDS_LIGHT_GRAY,
            ModItems.GRASS_SEEDS_CYAN,
            ModItems.GRASS_SEEDS_PURPLE,
            ModItems.GRASS_SEEDS_BLUE,
            ModItems.GRASS_SEEDS_BROWN,
            ModItems.GRASS_SEEDS_GREEN,
            ModItems.GRASS_SEEDS_RED,
            ModItems.GRASS_SEEDS_BLACK,
        ).map { deferred ->
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