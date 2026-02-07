package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.asIngredient
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.Unit
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class LubricateBootRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	val bootIngredient: Ingredient = Ingredient.of(*ALL_BOOTS.toTypedArray())
	val lubeIngredient: Ingredient = ModItems.SUPER_LUBRICANT_TINCTURE.asIngredient()

	override fun matches(input: CraftingInput, level: Level): Boolean {
		var bootStack: ItemStack? = null
		var lubeStack: ItemStack? = null

		for (stack in input.items()) {
			if (bootIngredient.test(stack)) {
				if (bootStack != null) return false
				bootStack = stack
			}

			if (lubeIngredient.test(stack)) {
				if (lubeStack != null) return false
				lubeStack = stack
			}

			if (bootStack != null && lubeStack != null) break
		}

		return bootStack != null && lubeStack != null && !bootStack.has(ModDataComponents.LUBRICATED)
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		var bootStack: ItemStack? = null
		var lubeStack: ItemStack? = null

		for (stack in input.items()) {
			if (bootIngredient.test(stack)) {
				if (bootStack != null) return ItemStack.EMPTY
				bootStack = stack
			}

			if (lubeIngredient.test(stack)) {
				if (lubeStack != null) return ItemStack.EMPTY
				lubeStack = stack
			}

			if (bootStack != null && lubeStack != null) break
		}

		if (bootStack == null || lubeStack == null) return ItemStack.EMPTY

		val newBootStack = bootStack.copy()
		newBootStack.set(ModDataComponents.LUBRICATED, Unit.INSTANCE)

		return newBootStack
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean {
		return width * height >= 2
	}

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.LUBRICATE_BOOT.get()
	}

	companion object {
		val ALL_BOOTS = BuiltInRegistries.ITEM
			.filter { it is ArmorItem && it.type == ArmorItem.Type.BOOTS }
			.map { it.defaultInstance }
	}
}