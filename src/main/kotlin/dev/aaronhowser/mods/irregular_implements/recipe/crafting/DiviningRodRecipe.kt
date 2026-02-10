package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.asIngredient
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isItem
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.Tags
import kotlin.jvm.optionals.getOrNull

class DiviningRodRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	override fun matches(input: CraftingInput, level: Level): Boolean {
		val oreTag = getOreTag(input) ?: return false
		if (input.width() < 3 || input.height() < 3) return false

		val oresMatch = ORE_SLOTS.all { input.getItem(it).isItem(oreTag) }
		val sticksMatch = STICK_SLOTS.all { STICKS_INGREDIENT.test(input.getItem(it)) }
		val eyeMatch = EYE_SLOTS.all { EYE_INGREDIENT.test(input.getItem(it)) }

		return oresMatch && sticksMatch && eyeMatch
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		val oreTag = getOreTag(input) ?: return ItemStack.EMPTY

		return DiviningRodItem.getRodForItemTag(oreTag)
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean {
		return width * height >= 9
	}

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.DIVINING_ROD.get()
	}

	companion object {
		private val ORE_SLOTS: Set<Int> = setOf(0, 2)
		private val STICK_SLOTS: Set<Int> = setOf(1, 3, 5, 6, 8)
		private val EYE_SLOTS: Set<Int> = setOf(4)

		val STICKS_INGREDIENT: Ingredient = Tags.Items.RODS_WOODEN.asIngredient()
		val EYE_INGREDIENT: Ingredient = Items.SPIDER_EYE.asIngredient()

		private fun getOreTag(input: CraftingInput): TagKey<Item>? {
			if (input.width() < 3 || input.height() < 3) return null

			val topLeftStack = input.getItem(0)
			val topRightStack = input.getItem(2)

			return topLeftStack.tags
				.filter { tag ->
					tag.location.toString().startsWith("c:ores/")
							&& topRightStack.isItem(tag)
				}
				.findFirst()
				.getOrNull()
		}
	}
}