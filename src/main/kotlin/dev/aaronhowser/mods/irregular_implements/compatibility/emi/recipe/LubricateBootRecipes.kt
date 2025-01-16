package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiRecipe
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.Unit
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.Potions

object LubricateBootRecipes {

    fun getRecipes(): List<EmiRecipe> {
        return lubricateRecipes()
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
            .otherItem(ModItems.SUPER_LUBRICANT_TINCTURE.toStack())

        val cleanBuilder = MutatingEmiRecipe.Builder()
            .otherItem(OtherUtil.getPotionStack(Potions.WATER))

        for ((cleanBoot, lubedBoot) in bootMap) {
            lubricateBuilder.addStage(
                cleanBoot,
                lubedBoot
            )

            cleanBuilder.addStage(
                lubedBoot,
                cleanBoot
            )
        }

        val lubricateRecipe = lubricateBuilder.build(OtherUtil.modResource("/lubricate_boot"))
        val cleanRecipe = cleanBuilder.build(OtherUtil.modResource("/clean_boot"))

        return listOf(lubricateRecipe, cleanRecipe)
    }

}