package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.recipe.crafting.ApplySpectreAnchorRecipe
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.stack.EmiIngredient
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.Unit
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient

object MutatingRecipes {

    fun getRecipes(): List<EmiRecipe> {
        return lubricateRecipes() + spectreAnchor()
    }

    private fun lubricateRecipes(): List<MutatingEmiRecipe> {
        val boots = BuiltInRegistries.ITEM
            .filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }
            .map { it.defaultInstance }

        val bootMap: MutableMap<ItemStack, ItemStack> = mutableMapOf()

        for (boot in boots) {
            val lubricatedBoot = boot.copy()
            lubricatedBoot.set(ModDataComponents.LUBRICATED, Unit.INSTANCE)
            bootMap[boot] = lubricatedBoot
        }

        val lubricantStack = ModItems.SUPER_LUBRICANT_TINCTURE.toStack()
        val waterStack = OtherUtil.getPotionStack(Potions.WATER)

        val lubricateBuilder = MutatingEmiRecipe.Builder()
            .virtualInput(lubricantStack)
        val cleanBuilder = MutatingEmiRecipe.Builder()
            .virtualInput(waterStack)

        for ((cleanBoot, lubedBoot) in bootMap) {
            lubricateBuilder.addStage(
                inputItems = listOf(cleanBoot, lubricantStack),
                outputItem = lubedBoot
            )

            cleanBuilder.addStage(
                inputItems = listOf(lubedBoot, waterStack),
                outputItem = cleanBoot
            )
        }

        val lubricateRecipe = lubricateBuilder.build(OtherUtil.modResource("/lubricate_boot"))
        val cleanRecipe = cleanBuilder.build(OtherUtil.modResource("/clean_boot"))

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
            virtualInput = listOf(EmiIngredient.of(Ingredient.of(anchorStack)))
        )

        return recipe
    }

}