package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.irregular_implements.block.DiaphanousBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

//TODO: Emi
class SetDiaphanousBlockRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	override fun matches(input: CraftingInput, level: Level): Boolean {
		var diaphanousBlockStack: ItemStack? = null
		var blockStack: ItemStack? = null

		for (item in input.items()) {
			if (item.isEmpty) continue
			val blockItem = item.item as? BlockItem ?: continue

			if (blockItem == ModBlocks.DIAPHANOUS_BLOCK.asItem()) {
				if (diaphanousBlockStack != null) return false
				diaphanousBlockStack = item
			} else if (DiaphanousBlock.isValidBlock(blockItem.block, level)) {
				if (blockStack != null) return false
				blockStack = item
			}
		}

		return diaphanousBlockStack != null && blockStack != null
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		val diaphanousBlockStack = input.items().first { it.item == ModBlocks.DIAPHANOUS_BLOCK.asItem() }
		val blockStack = input.items().first { !it.isEmpty && !it.`is`(ModBlocks.DIAPHANOUS_BLOCK.asItem()) && it.item is BlockItem }

		val output = diaphanousBlockStack.copyWithCount(1)
		output.set(ModDataComponents.BLOCK, (blockStack.item as BlockItem).block)

		return output
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean {
		return width * height >= 2
	}

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.SET_DIAPHANOUS_BLOCK.get()
	}
}