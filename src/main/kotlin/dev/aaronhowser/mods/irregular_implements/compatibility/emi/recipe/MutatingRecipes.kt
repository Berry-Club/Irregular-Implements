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
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.Tags

object MutatingRecipes {

    fun getRecipes(): List<EmiRecipe> {
        return lubricateRecipes() + spectreAnchor() + customCraftingTable()
    }

    private fun lubricateRecipes(): List<MutatingWithConstantEmiRecipe> {
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

        val lubricateRecipe = MutatingWithConstantEmiRecipe(
            id = OtherUtil.modResource("/lubricate_boot"),
            mutatingInput = cleanBoots,
            constantStack = lubricantStack,
            mutatingOutput = lubedBoots,
            virtualInput = listOf(lubricantStack)
        )

        val cleanRecipe = MutatingWithConstantEmiRecipe(
            id = OtherUtil.modResource("/clean_boot"),
            mutatingInput = lubedBoots,
            constantStack = waterStack,
            mutatingOutput = cleanBoots,
            virtualInput = listOf(waterStack)
        )

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

    fun customCraftingTable(): MutatingEmiRecipe {

        val validOuterItems = BuiltInRegistries.ITEM
            .getTag(ModItemTagsProvider.CUSTOM_CRAFTING_TABLE_ITEMS)
            .get().toList().map { it.value().defaultInstance }

        val innerIngredient = Ingredient.of(Tags.Items.PLAYER_WORKSTATIONS_CRAFTING_TABLES)

        val recipe = MutatingEmiRecipe.Builder()
            .recipePattern("PPP,PCP,PPP")
            .mutatingInput(*validOuterItems.toTypedArray())
            .patternKey('P', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('C', MutatingEmiRecipe.PatternValue.IngredientValue(innerIngredient))
            .virtualInput(innerIngredient)
            .build(OtherUtil.modResource("/custom_crafting_table"))

        return recipe
    }

    //TODO
    // CustomCraftingTableRecipe
    // SetDiaph
    // InvertDiaph

    //TODO: Maybe consolidate both types into a new one? Could have a map of char to either an ingredient or a mutating stack, somehow? And then smartly check which one it is

}