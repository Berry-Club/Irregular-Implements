package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.asIngredient
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

//TODO: Emi
class SetGoldenCompassPositionRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	override fun matches(input: CraftingInput, level: Level): Boolean {
		val amountEmeraldCompasses = input.items().count { GOLDEN_COMPASS_INGREDIENT.test(it) }
		val amountPlayerFilters = input.items().count { LOCATION_FILTER_INGREDIENT.test(it) }
		val amountNonEmpty = input.items().count { !it.isEmpty }

		if (!(amountEmeraldCompasses == 1 && amountPlayerFilters == 1 && amountNonEmpty == 2)) return false

		val playerFilter = input.items().first { LOCATION_FILTER_INGREDIENT.test(it) }
		return playerFilter.has(ModDataComponents.GLOBAL_POS)
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		val emeraldCompass = input.items().first { GOLDEN_COMPASS_INGREDIENT.test(it) }
		val playerFilter = input.items().first { LOCATION_FILTER_INGREDIENT.test(it) }

		val result = emeraldCompass.copyWithCount(1)
		val globalPos = playerFilter.get(ModDataComponents.GLOBAL_POS) ?: return ItemStack.EMPTY
		result.set(ModDataComponents.GLOBAL_POS, globalPos)

		return result
	}

	override fun getRemainingItems(input: CraftingInput): NonNullList<ItemStack?> {
		val items = input.items().toMutableList()
		for (i in items.indices) {
			val stack = items[i]
			if (LOCATION_FILTER_INGREDIENT.test(stack)) {
				items[i] = stack.copy()
			} else {
				items[i] = ItemStack.EMPTY
			}
		}

		return NonNullList.copyOf(items)
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean {
		return width * height >= 2
	}

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.SET_GOLDEN_COMPASS_POSITION.get()
	}

	companion object {
		val GOLDEN_COMPASS_INGREDIENT = ModItems.GOLDEN_COMPASS.asIngredient()
		val LOCATION_FILTER_INGREDIENT = ModItems.LOCATION_FILTER.asIngredient()
	}

}