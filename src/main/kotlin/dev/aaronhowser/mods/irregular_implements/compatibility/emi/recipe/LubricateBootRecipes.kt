package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiCraftingRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.Unit
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.crafting.Ingredient

object LubricateBootRecipes {

    fun lubricateRecipe(registry: EmiRegistry) {
        val boots = BuiltInRegistries.ITEM.filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }.map { it.defaultInstance }

        val lubeIngredient = EmiIngredient.of(Ingredient.of(ModItems.SUPER_LUBRICANT_TINCTURE))

        for ((i, bootItem) in boots.withIndex()) {
            val bootIngredient = EmiIngredient.of(Ingredient.of(bootItem))

            val lubricated = bootItem.copy()
            lubricated.set(ModDataComponents.LUBRICATED, Unit.INSTANCE)

            registry.addRecipe(
                EmiCraftingRecipe(
                    listOf(bootIngredient, lubeIngredient),
                    EmiStack.of(lubricated),
                    OtherUtil.modResource("/lubricate_boot/$i"),
                    true
                )
            )

        }
    }

}