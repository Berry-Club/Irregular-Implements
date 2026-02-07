package dev.aaronhowser.mods.irregular_implements.recipe.crafting

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.asIngredient
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.ChatFormatting
import net.minecraft.core.HolderLookup
import net.minecraft.util.Unit
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent

class ApplyLuminousPowderRecipe(
	craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

	override fun matches(input: CraftingInput, level: Level): Boolean {
		val amountLuminousPowder = input.items().count { LUMINOUS_POWDER_INGREDIENT.test(it) }
		val amountNonLuminousPowders = input.items().count(::isApplicable)

		return amountLuminousPowder == 1 && amountNonLuminousPowders == 1
	}

	override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
		val nonAnchor = input.items().first(::isApplicable)

		val result = nonAnchor.copyWithCount(1)
		result.set(ModDataComponents.HAS_LUMINOUS_POWDER, Unit.INSTANCE)

		return result
	}

	override fun canCraftInDimensions(width: Int, height: Int): Boolean {
		return width * height >= 2
	}

	override fun getSerializer(): RecipeSerializer<*> {
		return ModRecipeSerializers.APPLY_LUMINOUS_POWDER.get()
	}

	companion object {
		val LUMINOUS_POWDER_INGREDIENT: Ingredient = ModItems.LUMINOUS_POWDER.asIngredient()

		fun isApplicable(itemStack: ItemStack): Boolean {
			return !itemStack.isEmpty && !itemStack.has(ModDataComponents.HAS_LUMINOUS_POWDER)
		}

		fun tooltip(event: ItemTooltipEvent) {
			val stack = event.itemStack
			if (!stack.has(ModDataComponents.HAS_LUMINOUS_POWDER)) return

			event.toolTip.add(
				ModTooltipLang.HAS_LUMINOUS_POWDER
					.toComponent()
					.withStyle(ChatFormatting.YELLOW)
			)
		}
	}
}