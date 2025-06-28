package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.CustomCraftingTableBlockItem
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.Tags

//TODO: EMI
class CustomCraftingTableRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	override fun matches(input: CraftingInput, level: Level): Boolean {
		if (input.width() < 3 || input.height() < 3) return false
		if (!input.getItem(CRAFTING_TABLE_SLOT).`is`(Tags.Items.PLAYER_WORKSTATIONS_CRAFTING_TABLES)) return false

		val plankStack = input.getItem(plankSlots.first())

		val plankStackValid = plankStack.`is`(ModItemTagsProvider.CUSTOM_CRAFTING_TABLE_ITEMS)
				&& plankStack.item is BlockItem

		if (!plankStackValid) return false

		return plankSlots.all { input.getItem(it).`is`(plankStack.item) }
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		val plankStack = input.getItem(plankSlots.first())

		val block = (plankStack.item as? BlockItem)?.block ?: return ItemStack.EMPTY

		return CustomCraftingTableBlockItem.ofBlock(block)
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean {
		return width >= 3 && height >= 3
	}

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.CUSTOM_CRAFTING_TABLE.get()
	}

	companion object {
		private const val CRAFTING_TABLE_SLOT = 4
		private val plankSlots = (0..0).toSet() - CRAFTING_TABLE_SLOT
	}
}