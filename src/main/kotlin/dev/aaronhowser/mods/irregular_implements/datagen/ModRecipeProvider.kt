package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import net.minecraft.advancements.Criterion
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import java.util.concurrent.CompletableFuture

class ModRecipeProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>
) : RecipeProvider(output, lookupProvider) {

    override fun buildRecipes(recipeOutput: RecipeOutput) {
        for (shapedRecipe in shapedRecipes) {
            shapedRecipe.save(recipeOutput)
        }
    }

    //TODO: Potions of Collapse

    private sealed class IngredientType {
        data class TagKeyIng(val tagKey: TagKey<Item>) : IngredientType()
        data class ItemLikeIng(val item: ItemLike) : IngredientType()
    }

    private fun <T : IngredientType> makeShapedRecipe(
        output: ItemLike,
        count: Int = 1,
        patterns: List<String>,
        definitions: Map<Char, T>,
        unlockedByName: String = "has_log",
        unlockedByCriterion: Criterion<*> = has(ItemTags.LOGS)
    ): ShapedRecipeBuilder {
        var temp = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)

        for (pattern in patterns) {
            temp = temp.pattern(pattern)
        }

        for (definition in definitions) {
            temp = when (val ing = definition.value) {
                is IngredientType.TagKeyIng -> temp.define(definition.key, ing.tagKey)
                is IngredientType.ItemLikeIng -> temp.define(definition.key, ing.item)
                else -> error("Unknown ingredient type: $ing")
            }
        }

        return temp.unlockedBy(unlockedByName, unlockedByCriterion)
    }

    private val shapedRecipes: List<ShapedRecipeBuilder> = listOf(
        makeShapedRecipe(
            ModBlocks.FERTILIZED_DIRT,
            2,
            listOf("FBF", "BDB", "FBF"),
            mapOf(
                'F' to IngredientType.ItemLikeIng(Items.ROTTEN_FLESH),
                'B' to IngredientType.ItemLikeIng(Items.BONE_MEAL),
                'D' to IngredientType.ItemLikeIng(Items.DIRT)
            )
        )
    )

}