package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiCraftingRecipe
import dev.emi.emi.api.recipe.EmiRecipe
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.Unit
import net.minecraft.world.item.ArmorItem

object LubricateBootRecipes {

    fun getRecipes(): List<EmiRecipe> {
        return lubricateRecipes() + washRecipes()
    }

    private fun lubricateRecipes(): List<MutatingEmiRecipe> {

        val boots = BuiltInRegistries.ITEM
            .filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }
            .map { it.defaultInstance }

        val builder = MutatingEmiRecipe.Builder()

        builder.otherItem(ModItems.SUPER_LUBRICANT_TINCTURE.toStack())

        for (boot in boots) {

            val lubricatedBoot = boot.copy()
            lubricatedBoot.set(ModDataComponents.LUBRICATED, Unit.INSTANCE)

            builder.addStage(
                boot,
                lubricatedBoot
            )
        }

        return listOf(builder.build(OtherUtil.modResource("/lubricate_boot")))

//        val lubeIngredient = ModItems.SUPER_LUBRICANT_TINCTURE.emiIngredient
//
//        return boots.map { bootStack ->
//            val name = BuiltInRegistries.ITEM.getKey(bootStack.item).toString().replace(':', '/')
//
//            val bootIngredient = EmiIngredient.of(Ingredient.of(bootStack))
//            val output = bootStack.copy().apply { set(ModDataComponents.LUBRICATED, Unit.INSTANCE) }
//
//            EmiCraftingRecipe(
//                listOf(bootIngredient, lubeIngredient),
//                EmiStack.of(output),
//                OtherUtil.modResource("/lubricate_boot/$name"),
//                true
//            )
//        }
    }

    private fun washRecipes(): List<EmiCraftingRecipe> {
//        val boots = BuiltInRegistries.ITEM
//            .filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }
//            .map {
//                it.defaultInstance
//                    .apply { set(ModDataComponents.LUBRICATED, Unit.INSTANCE) }
//            }
//
//        val waterIngredient = EmiIngredient.of(Ingredient.of(OtherUtil.getPotionStack(Potions.WATER)))
//
//        return boots.map { bootStack ->
//            val name = BuiltInRegistries.ITEM.getKey(bootStack.item).toString().replace(':', '/')
//
//            val bootIngredient = EmiIngredient.of(Ingredient.of(bootStack))
//            val output = bootStack.copy().apply { remove(ModDataComponents.LUBRICATED) }
//
//            EmiCraftingRecipe(
//                listOf(bootIngredient, waterIngredient),
//                EmiStack.of(output),
//                OtherUtil.modResource("/wash_boot/$name"),
//                true
//            )
//        }

        return emptyList()
    }

}