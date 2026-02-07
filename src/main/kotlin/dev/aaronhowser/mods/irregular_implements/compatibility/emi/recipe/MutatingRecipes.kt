package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.getAsStack
import dev.aaronhowser.mods.aaron.client.AaronClientUtil
import dev.aaronhowser.mods.irregular_implements.block.DiaphanousBlock
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.ApplyLuminousPowderRecipe
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.ApplySpectreAnchorRecipe
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
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
		return lubricateRecipes() + spectreAnchor() + customCraftingTable() + diaphanousRecipes() + luminousPowder()
	}

	private fun lubricateRecipes(): List<EmiRecipe> {
		val cleanBoots = BuiltInRegistries.ITEM.mapNotNull { item ->
			item.takeIf { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }?.defaultInstance
		}

		val lubricating = cleanBoots.associateWith { stack ->
			stack.copy().apply {
				set(ModDataComponents.LUBRICATED, Unit.INSTANCE)
			}
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

		val waterStack = Potions.WATER.getAsStack()

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
		val allStacks = (BuiltInRegistries.ITEM).mapNotNull { item ->
			item.defaultInstance.takeIf { stack -> ApplySpectreAnchorRecipe.isApplicable(stack) }
		}

		val associations = allStacks.associateWith { stack ->
			stack.copy().apply {
				set(ModDataComponents.IS_ANCHORED, Unit.INSTANCE)
			}
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

	private fun luminousPowder(): EmiRecipe {
		val allStacks = BuiltInRegistries.ITEM.mapNotNull { item ->
			item.defaultInstance.takeIf { stack -> ApplyLuminousPowderRecipe.isApplicable(stack) }
		}

		val associations = allStacks.associateWith { stack ->
			stack.copy().apply {
				set(ModDataComponents.HAS_LUMINOUS_POWDER, Unit.INSTANCE)
			}
		}

		val luminousPowderStack = ModItems.LUMINOUS_POWDER.toStack()

		val recipe = MutatingEmiRecipe.Builder()
			.recipePattern("IL")
			.patternKey('I', MutatingEmiRecipe.PatternValue.MutatingValue)
			.patternKey('L', MutatingEmiRecipe.PatternValue.IngredientValue(luminousPowderStack))
			.associations(associations)
			.virtualInput(luminousPowderStack)
			.build(OtherUtil.modResource("/apply_luminous_powder"))

		return recipe
	}

	private fun customCraftingTable(): MutatingEmiRecipe {
		val validOuterItems = BuiltInRegistries.ITEM
			.getTag(ModItemTagsProvider.CUSTOM_CRAFTING_TABLE_ITEMS)
			.get()
			.map { it.value().defaultInstance }

		val associations = validOuterItems.associateWith { stack ->
			val block = (stack.item as BlockItem).block

			ModItems.CUSTOM_CRAFTING_TABLE.toStack().apply {
				set(ModDataComponents.BLOCK, block)
			}
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
		val level = AaronClientUtil.localLevel ?: return emptyList()

		val validBlockStacks = BuiltInRegistries.BLOCK.mapNotNull { block ->
			if (!DiaphanousBlock.isValidBlock(block, level)) return@mapNotNull null

			block.asItem().defaultInstance.takeUnless { it.isEmpty }
		}

		val setAssociations = validBlockStacks.associateWith { stack ->
			val block = (stack.item as? BlockItem)?.block

			ModItems.DIAPHANOUS_BLOCK.toStack().apply {
				set(ModDataComponents.BLOCK, block)
			}
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

		val invertAssociations = setAssociations.values.associateWith { stack ->
			stack.copy().apply {
				set(ModDataComponents.IS_INVERTED, Unit.INSTANCE)
			}
		}

		val invertRecipe = MutatingEmiRecipe.Builder()
			.recipePattern("D")
			.patternKey('D', MutatingEmiRecipe.PatternValue.MutatingValue)
			.associations(invertAssociations)
			.virtualInput(defaultDiaphanousBlock)
			.virtualOutputs(defaultDiaphanousBlock)
			.build(OtherUtil.modResource("/invert_diaphanous_block"))

		val unInvertAssociations = invertAssociations.entries.associate { (k, v) -> v to k }

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