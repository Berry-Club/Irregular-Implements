package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin.Companion.emiIngredient
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiCraftingRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.Unit
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient

//TODO: Copy the implementation of EmiBookCloningRecipe
object LubricateBootRecipes {

    fun getRecipes(): List<EmiCraftingRecipe> {
        return lubricateRecipes() + washRecipes()
    }

    private fun lubricateRecipes(): List<EmiCraftingRecipe> {
        val boots = BuiltInRegistries.ITEM
            .filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }
            .map { it.defaultInstance }

        val lubeIngredient = ModItems.SUPER_LUBRICANT_TINCTURE.emiIngredient

        return boots.map { bootStack ->
            val name = BuiltInRegistries.ITEM.getKey(bootStack.item).toString().replace(':', '/')

            val bootIngredient = EmiIngredient.of(Ingredient.of(bootStack))
            val output = bootStack.copy().apply { set(ModDataComponents.LUBRICATED, Unit.INSTANCE) }

            EmiCraftingRecipe(
                listOf(bootIngredient, lubeIngredient),
                EmiStack.of(output),
                OtherUtil.modResource("/lubricate_boot/$name"),
                true
            )
        }
    }

    private fun washRecipes(): List<EmiCraftingRecipe> {
        val boots = BuiltInRegistries.ITEM
            .filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }
            .map {
                it.defaultInstance
                    .apply { set(ModDataComponents.LUBRICATED, Unit.INSTANCE) }
            }

        val waterIngredient = EmiIngredient.of(Ingredient.of(OtherUtil.getPotionStack(Potions.WATER)))

        return boots.map { bootStack ->
            val name = BuiltInRegistries.ITEM.getKey(bootStack.item).toString().replace(':', '/')

            val bootIngredient = EmiIngredient.of(Ingredient.of(bootStack))
            val output = bootStack.copy().apply { remove(ModDataComponents.LUBRICATED) }

            EmiCraftingRecipe(
                listOf(bootIngredient, waterIngredient),
                EmiStack.of(output),
                OtherUtil.modResource("/wash_boot/$name"),
                true
            )
        }
    }

}