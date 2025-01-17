package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.block.DiaphanousBlock
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.ApplySpectreAnchorRecipe
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiRecipe
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.Unit
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.Tags

object MutatingRecipes {

    fun getRecipes(): List<EmiRecipe> {
        return lubricateRecipes() + spectreAnchor() + customCraftingTable() + diaphanousRecipes()
    }

    private fun lubricateRecipes(): List<EmiRecipe> {
        val cleanBoots = BuiltInRegistries.ITEM
            .filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }
            .map { it.defaultInstance }

        val lubricating = cleanBoots.associateWith {
            val lubedBoot = it.copy()
            lubedBoot.set(ModDataComponents.LUBRICATED, Unit.INSTANCE)
            lubedBoot
        }

        val cleaning = lubricating.entries.associate { (k, v) -> v to k }

        val lubricantStack = ModItems.SUPER_LUBRICANT_TINCTURE.toStack()

        val lubricateRecipe = MutatingEmiRecipe.Builder()
            .recipePattern("BL")
            .patternKey('B', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('L', MutatingEmiRecipe.PatternValue.IngredientValue(lubricantStack))
            .associations(lubricating)
            .virtualInput(lubricantStack)
            .build(OtherUtil.modResource("/lubricate_boot"))

        val waterStack = OtherUtil.getPotionStack(Potions.WATER)

        val cleanRecipe = MutatingEmiRecipe.Builder()
            .recipePattern("BW")
            .patternKey('B', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('W', MutatingEmiRecipe.PatternValue.IngredientValue(waterStack))
            .associations(cleaning)
            .virtualInput(waterStack)
            .build(OtherUtil.modResource("/clean_boot"))

        return listOf(lubricateRecipe, cleanRecipe)
    }

    private fun spectreAnchor(): EmiRecipe {
        val allStacks = (BuiltInRegistries.ITEM).mapNotNull {
            val stack = it.defaultInstance
            if (ApplySpectreAnchorRecipe.isApplicable(stack)) stack else null
        }

        val associations = allStacks.associateWith {
            val anchoredStack = it.copy()
            anchoredStack.set(ModDataComponents.IS_ANCHORED, Unit.INSTANCE)
            anchoredStack
        }

        val anchorStack = ModItems.SPECTRE_ANCHOR.toStack()

        val recipe = MutatingEmiRecipe.Builder()
            .recipePattern("IA")
            .patternKey('I', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('A', MutatingEmiRecipe.PatternValue.IngredientValue(anchorStack))
            .associations(associations)
            .virtualInput(anchorStack)
            .build(OtherUtil.modResource("/apply_spectre_anchor"))

        return recipe
    }

    private fun customCraftingTable(): MutatingEmiRecipe {
        val validOuterItems = BuiltInRegistries.ITEM
            .getTag(ModItemTagsProvider.CUSTOM_CRAFTING_TABLE_ITEMS)
            .get().toList().map { it.value().defaultInstance }

        val associations = validOuterItems.associateWith {
            val block = (it.item as BlockItem).block

            val craftingTable = ModItems.CUSTOM_CRAFTING_TABLE.toStack()
            craftingTable.set(ModDataComponents.BLOCK, block)

            craftingTable
        }

        val craftingTableIngredient = Ingredient.of(Tags.Items.PLAYER_WORKSTATIONS_CRAFTING_TABLES)

        val recipe = MutatingEmiRecipe.Builder()
            .recipePattern("PPP,PCP,PPP")
            .patternKey('P', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('C', MutatingEmiRecipe.PatternValue.IngredientValue(craftingTableIngredient))
            .associations(associations)
            .virtualInput(craftingTableIngredient)
            .virtualOutputs(ModItems.CUSTOM_CRAFTING_TABLE.toStack())
            .build(OtherUtil.modResource("/custom_crafting_table"))

        return recipe
    }

    private fun diaphanousRecipes(): List<MutatingEmiRecipe> {
        val level = ClientUtil.localPlayer?.level() ?: return emptyList()

        val validBlockStacks = BuiltInRegistries.BLOCK.mapNotNull {
            if (!DiaphanousBlock.isValidBlock(it, level)) return@mapNotNull null

            val stack = it.asItem().defaultInstance
            if (stack.isEmpty) null else stack
        }

        val setAssociations = validBlockStacks.associateWith {
            val block = (it.item as? BlockItem)?.block
            val diaphanousBlockItem = ModItems.DIAPHANOUS_BLOCK.toStack()
            diaphanousBlockItem.set(ModDataComponents.BLOCK, block)
            diaphanousBlockItem
        }

        val defaultDiaphanousBlock = ModItems.DIAPHANOUS_BLOCK.toStack()

        // R = Regular Block
        // D = Diaphanous Block
        val setRecipe = MutatingEmiRecipe.Builder()
            .recipePattern("RD")
            .patternKey('R', MutatingEmiRecipe.PatternValue.MutatingValue)
            .patternKey('D', MutatingEmiRecipe.PatternValue.IngredientValue(defaultDiaphanousBlock))
            .associations(setAssociations)
            .virtualInput(defaultDiaphanousBlock)
            .virtualOutputs(defaultDiaphanousBlock)
            .build(OtherUtil.modResource("/set_diaphanous_block"))

        val invertAssociations = setAssociations.values.associateWith {
            val invertedStack = it.copy()
            invertedStack.set(ModDataComponents.IS_INVERTED, Unit.INSTANCE)
            invertedStack
        }

        val invertRecipe = MutatingEmiRecipe.Builder()
            .recipePattern("D")
            .patternKey('D', MutatingEmiRecipe.PatternValue.MutatingValue)
            .associations(invertAssociations)
            .virtualInput(defaultDiaphanousBlock)
            .virtualOutputs(defaultDiaphanousBlock)
            .build(OtherUtil.modResource("/invert_diaphanous_block"))

        val unInvertAssociations = invertAssociations.values.associateWith {
            val unInvertedStack = it.copy()
            unInvertedStack.remove(ModDataComponents.IS_INVERTED)
            unInvertedStack
        }

        val unInvertRecipe = MutatingEmiRecipe.Builder()
            .recipePattern("D")
            .patternKey('D', MutatingEmiRecipe.PatternValue.MutatingValue)
            .associations(unInvertAssociations)
            .virtualInput(defaultDiaphanousBlock)
            .virtualOutputs(defaultDiaphanousBlock)
            .build(OtherUtil.modResource("/uninvert_diaphanous_block"))

        return listOf(setRecipe, invertRecipe, unInvertRecipe)
    }

}