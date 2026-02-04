package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.aaron.AaronExtensions.getAsStack
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentMap
import net.minecraft.util.Unit
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.crafting.DataComponentIngredient

class WashBootRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	val bootIngredient: Ingredient = DataComponentIngredient.of(
		false,
		DataComponentMap.builder().set(ModDataComponents.LUBRICATED, Unit.INSTANCE).build(),
		*LubricateBootRecipe.ALL_BOOTS.map(ItemStack::getItem).toTypedArray()
	)

	val waterIngredient: Ingredient = DataComponentIngredient.of(false, Potions.WATER.getAsStack())

	override fun matches(input: CraftingInput, level: Level): Boolean {
		var bootStack: ItemStack? = null
		var waterStack: ItemStack? = null

		for (stack in input.items()) {
			if (bootIngredient.test(stack)) {
				if (bootStack != null) return false
				bootStack = stack
			}

			if (waterIngredient.test(stack)) {
				if (waterStack != null) return false
				waterStack = stack
			}

			if (bootStack != null && waterStack != null) break
		}

		return bootStack != null && waterStack != null && bootStack.has(ModDataComponents.LUBRICATED)
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		var bootStack: ItemStack? = null
		var waterStack: ItemStack? = null

		for (stack in input.items()) {
			if (bootIngredient.test(stack)) {
				if (bootStack != null) return ItemStack.EMPTY
				bootStack = stack
			}

			if (waterIngredient.test(stack)) {
				if (waterStack != null) return ItemStack.EMPTY
				waterStack = stack
			}

			if (bootStack != null && waterStack != null) break
		}

		if (bootStack == null || waterStack == null) return ItemStack.EMPTY

		val newBootStack = bootStack.copy()
		newBootStack.remove(ModDataComponents.LUBRICATED)

		return newBootStack
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean {
		return width * height >= 2
	}

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.WASH_BOOT.get()
	}
}