package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

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

        val lubricateBuilder = MutatingEmiRecipe.Builder()
        val cleanBuilder = MutatingEmiRecipe.Builder()

        val lubricantStack = ModItems.SUPER_LUBRICANT_TINCTURE.toStack()
        val waterStack = OtherUtil.getPotionStack(Potions.WATER)

        for ((cleanBoot, lubedBoot) in bootMap) {
            lubricateBuilder.addStage(
                listOf(cleanBoot, lubricantStack),
                lubedBoot
            )

            cleanBuilder.addStage(
                listOf(lubedBoot, waterStack),
                cleanBoot
            )
        }

        val lubricateRecipe = lubricateBuilder.build(OtherUtil.modResource("/lubricate_boot"))
        val cleanRecipe = cleanBuilder.build(OtherUtil.modResource("/clean_boot"))

        return listOf(lubricateRecipe, cleanRecipe)
    }

    private fun spectreAnchor(): MutatingEmiRecipe {
        val allItems = (BuiltInRegistries.ITEM).mapNotNull {
            val stack = it.defaultInstance
            if (ApplySpectreAnchorRecipe.isApplicable(stack)) stack else null
        }

        val anchorStack = ModItems.SPECTRE_ANCHOR.toStack()

        val builder = MutatingEmiRecipe.Builder()
            .actualInput(anchorStack)

        for (item in allItems) {
            val anchoredItem = item.copy()
            anchoredItem.set(ModDataComponents.ANCHORED, Unit.INSTANCE)

            builder.addStage(
                listOf(item, anchorStack),
                anchoredItem
            )
        }

        return builder.build(OtherUtil.modResource("/apply_spectre_anchor"))
    }

}