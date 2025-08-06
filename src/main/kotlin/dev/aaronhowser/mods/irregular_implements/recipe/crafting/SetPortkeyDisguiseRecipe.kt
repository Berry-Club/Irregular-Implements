package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.irregular_implements.datagen.ModRecipeProvider.Companion.asIngredient
import dev.aaronhowser.mods.irregular_implements.item.component.PortkeyDisguiseDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class SetPortkeyDisguiseRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	override fun matches(input: CraftingInput, level: Level): Boolean {
		val amountPortkeys = input.items().count { PORTKEY_INGREDIENT.test(it) }
		val amountNonPortkeys = input.items().count(::isApplicable)

		return amountPortkeys == 1 && amountNonPortkeys == 1
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		val portkey = input.items().first { PORTKEY_INGREDIENT.test(it) }
		val nonPortkey = input.items().first(::isApplicable)

		val result = portkey.copyWithCount(1)
		result.set(ModDataComponents.PORTKEY_DISGUISE, PortkeyDisguiseDataComponent(nonPortkey.copy()))

		return result
	}

	override fun getRemainingItems(input: CraftingInput): NonNullList<ItemStack> {
		val items = input.items().toMutableList()
		for (i in items.indices) {
			val stack = items[i]
			if (PORTKEY_INGREDIENT.test(stack)) {
				items[i] = ItemStack.EMPTY
			} else {
				items[i] = stack.copyWithCount(1)
			}
		}

		return NonNullList.copyOf(items)
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height >= 2

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.SET_PORTKEY_DISGUISE.get()
	}

	companion object {
		val PORTKEY_INGREDIENT: Ingredient = ModItems.PORTKEY.asIngredient()

		fun isApplicable(itemStack: ItemStack): Boolean {
			return !itemStack.isEmpty && !itemStack.`is`(ModItems.PORTKEY)
		}

	}

}