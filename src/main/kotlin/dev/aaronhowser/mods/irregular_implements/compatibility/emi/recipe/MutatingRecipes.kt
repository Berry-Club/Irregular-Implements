package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.ApplySpectreAnchorRecipe
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiRecipe
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.Unit
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.Tags

object MutatingRecipes {

    fun getRecipes(): List<EmiRecipe> {
        return lubricateRecipes() + spectreAnchor() + customCraftingTable()
    }

    private fun lubricateRecipes(): List<EmiRecipe> {
        val cleanBoots = BuiltInRegistries.ITEM
            .filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }
            .map { it.defaultInstance }

        val lubricantStack = ModItems.SUPER_LUBRICANT_TINCTURE.toStack()
        val waterStack = OtherUtil.getPotionStack(Potions.WATER)

        val lubedBoots: MutableList<ItemStack> = mutableListOf()

        for (boot in cleanBoots) {
            val lubedBoot = boot.copy()
            lubedBoot.set(ModDataComponents.LUBRICATED, Unit.INSTANCE)
            lubedBoots.add(lubedBoot)
        }

        val lubricateRecipe = MutatingEmiRecipe.Builder()
            .recipePattern("BL")
            .mutatingInput(*cleanBoots.toTypedArray())
            .patternKey('B', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('L', MutatingEmiRecipe.PatternValue.IngredientValue(Ingredient.of(lubricantStack)))
            .mutatingOutput(*lubedBoots.toTypedArray())
            .virtualInput(Ingredient.of(lubricantStack))
            .build(OtherUtil.modResource("/lubricate_boot"))

        val cleanRecipe = MutatingEmiRecipe.Builder()
            .recipePattern("BW")
            .mutatingInput(*lubedBoots.toTypedArray())
            .patternKey('B', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('W', MutatingEmiRecipe.PatternValue.IngredientValue(Ingredient.of(waterStack)))
            .mutatingOutput(*cleanBoots.toTypedArray())
            .virtualInput(Ingredient.of(waterStack))
            .build(OtherUtil.modResource("/clean_boot"))

        return listOf(lubricateRecipe, cleanRecipe)
    }

    private fun spectreAnchor(): MutatingWithConstantEmiRecipe {
        val allItems = (BuiltInRegistries.ITEM).mapNotNull {
            val stack = it.defaultInstance
            if (ApplySpectreAnchorRecipe.isApplicable(stack)) stack else null
        }

        val anchoredItems: MutableList<ItemStack> = mutableListOf()

        for (item in allItems) {
            val anchoredItem = item.copy()
            anchoredItem.set(ModDataComponents.ANCHORED, Unit.INSTANCE)
            anchoredItems.add(anchoredItem)
        }

        val anchorStack = ModItems.SPECTRE_ANCHOR.toStack()

        val recipe = MutatingWithConstantEmiRecipe(
            id = OtherUtil.modResource("/apply_spectre_anchor"),
            mutatingInput = allItems,
            constantStack = anchorStack,
            mutatingOutput = anchoredItems,
            virtualInput = listOf(anchorStack)
        )

        return recipe
    }

    private fun customCraftingTable(): MutatingEmiRecipe {
        val validOuterItems = BuiltInRegistries.ITEM
            .getTag(ModItemTagsProvider.CUSTOM_CRAFTING_TABLE_ITEMS)
            .get().toList().map { it.value().defaultInstance }

        val innerIngredient = Ingredient.of(Tags.Items.PLAYER_WORKSTATIONS_CRAFTING_TABLES)

        val mutatingOutput: MutableList<ItemStack> = mutableListOf()

        //TODO: Figure out why these aren't lined up
        for (item in validOuterItems) {
            val block = (item.item as BlockItem).block

            val craftingTable = ModItems.CUSTOM_CRAFTING_TABLE.toStack()
            craftingTable.set(ModDataComponents.BLOCK, block)

            mutatingOutput.add(craftingTable)
        }

        for (i in validOuterItems.indices) {

            println(
                """
                
                ${validOuterItems[i].item}
                ${mutatingOutput[i].get(ModDataComponents.BLOCK)!!.name}
                
            """.trimIndent()
            )

        }

        val recipe = MutatingEmiRecipe.Builder()
            .recipePattern("PPP,PCP,PPP")
            .mutatingInput(*validOuterItems.toTypedArray())
            .patternKey('P', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('C', MutatingEmiRecipe.PatternValue.IngredientValue(innerIngredient))
            .mutatingOutput(*mutatingOutput.toTypedArray())
            .virtualInput(innerIngredient)
            .build(OtherUtil.modResource("/custom_crafting_table"))

        return recipe
    }

    //TODO
    // SetDiaph
    // InvertDiaph

    //TODO: Maybe consolidate both types into a new one? Could have a map of char to either an ingredient or a mutating stack, somehow? And then smartly check which one it is

}