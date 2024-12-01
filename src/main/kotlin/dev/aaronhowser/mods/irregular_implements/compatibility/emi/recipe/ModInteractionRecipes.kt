package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.ItemAbilities

object ModInteractionRecipes {

    fun getInteractionRecipes(): List<EmiWorldInteractionRecipe> {
        return grassSeeds() + slime()
    }

    private fun grassSeeds(): List<EmiWorldInteractionRecipe> {
        val seeds = DyeColor.entries.map { GrassSeedItem.getFromColor(it) } + ModItems.GRASS_SEEDS

        return seeds.map { deferred ->
            val seedItem = deferred.get()

            val colorString = seedItem.dyeColor?.getName()

            val id = if (colorString == null) {
                OtherUtil.modResource("/interaction/grass")
            } else {
                OtherUtil.modResource("/interaction/grass/$colorString")
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

    private fun slime(): MutableList<EmiWorldInteractionRecipe> {
        val recipes: MutableList<EmiWorldInteractionRecipe> = mutableListOf()

        val shovel = BuiltInRegistries.ITEM.filter { it.defaultInstance.canPerformAction(ItemAbilities.SHOVEL_FLATTEN) }
        val slimeBlock = Items.SLIME_BLOCK.defaultInstance
        val compressedSlimeOne = ModBlocks.COMPRESSED_SLIME_BLOCK.asItem().defaultInstance
            .apply { set(DataComponents.LORE, ItemLore.EMPTY.withLineAdded(Component.literal("1"))) }
        val compressedSlimeTwo = ModBlocks.COMPRESSED_SLIME_BLOCK.asItem().defaultInstance
            .apply { set(DataComponents.LORE, ItemLore.EMPTY.withLineAdded(Component.literal("2"))) }
        val compressedSlimeThree = ModBlocks.COMPRESSED_SLIME_BLOCK.asItem().defaultInstance
            .apply { set(DataComponents.LORE, ItemLore.EMPTY.withLineAdded(Component.literal("3"))) }

        val recipeOne = EmiWorldInteractionRecipe
            .builder()
            .leftInput(EmiIngredient.of(Ingredient.of(slimeBlock)))
            .rightInput(EmiIngredient.of(Ingredient.of(*shovel.toTypedArray())), true)
            .output(EmiStack.of(compressedSlimeOne))
            .id(OtherUtil.modResource("/interaction/slime/slime_to_compressed_one"))
            .build()

        val recipeTwo = EmiWorldInteractionRecipe
            .builder()
            .leftInput(EmiIngredient.of(Ingredient.of(compressedSlimeOne)))
            .rightInput(EmiIngredient.of(Ingredient.of(*shovel.toTypedArray())), true)
            .output(EmiStack.of(compressedSlimeTwo))
            .id(OtherUtil.modResource("/interaction/slime/compressed_one_to_compressed_two"))
            .build()

        val recipeThree = EmiWorldInteractionRecipe
            .builder()
            .leftInput(EmiIngredient.of(Ingredient.of(compressedSlimeTwo)))
            .rightInput(EmiIngredient.of(Ingredient.of(*shovel.toTypedArray())), true)
            .output(EmiStack.of(compressedSlimeThree))
            .id(OtherUtil.modResource("/interaction/slime/compressed_two_to_compressed_three"))
            .build()

        val recipeFour = EmiWorldInteractionRecipe
            .builder()
            .leftInput(EmiIngredient.of(Ingredient.of(compressedSlimeThree)))
            .rightInput(EmiIngredient.of(Ingredient.of(*shovel.toTypedArray())), true)
            .output(EmiStack.of(slimeBlock))
            .id(OtherUtil.modResource("/interaction/slime/compressed_three_to_slime"))
            .build()

        recipes.add(recipeOne)
        recipes.add(recipeTwo)
        recipes.add(recipeThree)
        recipes.add(recipeFour)

        return recipes
    }

}