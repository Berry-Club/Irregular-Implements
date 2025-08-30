package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.irregular_implements.datagen.ModRecipeProvider.Companion.asIngredient
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

//TODO: Emi
class SetEmeraldCompassPlayerRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	override fun matches(input: CraftingInput, level: Level): Boolean {
		val amountEmeraldCompasses = input.items().count { EMERALD_COMPASS_INGREDIENT.test(it) }
		val amountPlayerFilters = input.items().count { PLAYER_FILTER_INGREDIENT.test(it) }
		val amountNonEmpty = input.items().count { !it.isEmpty }

		if (!(amountEmeraldCompasses == 1 && amountPlayerFilters == 1 && amountNonEmpty == 2)) return false

		val playerFilter = input.items().first { PLAYER_FILTER_INGREDIENT.test(it) }
		return playerFilter.has(ModDataComponents.PLAYER)
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		val emeraldCompass = input.items().first { EMERALD_COMPASS_INGREDIENT.test(it) }
		val playerFilter = input.items().first { PLAYER_FILTER_INGREDIENT.test(it) }

		val result = emeraldCompass.copyWithCount(1)
		val entityIdentifier = playerFilter.get(ModDataComponents.PLAYER) ?: return ItemStack.EMPTY
		result.set(ModDataComponents.ENTITY_IDENTIFIER, entityIdentifier)

		return result
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean {
		return width * height >= 2
	}

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.SET_EMERALD_COMPASS_PLAYER.get()
	}

	companion object {
		val EMERALD_COMPASS_INGREDIENT = ModItems.EMERALD_COMPASS.asIngredient()
		val PLAYER_FILTER_INGREDIENT = ModItems.PLAYER_FILTER.asIngredient()
	}

}