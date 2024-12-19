package dev.aaronhowser.mods.irregular_implements.recipe

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.util.Unit
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class ApplySpectreAnchorRecipe(
    craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

    companion object {
        val anchorIngredient: Ingredient = Ingredient.of(ModItems.SPECTRE_ANCHOR)

        private fun isApplicable(itemStack: ItemStack): Boolean {
            return !itemStack.isEmpty && !itemStack.has(ModDataComponents.ANCHORED) && !anchorIngredient.test(itemStack)
        }
    }

    override fun matches(input: CraftingInput, level: Level): Boolean {
        val amountAnchors = input.items().count { anchorIngredient.test(it) }
        val amountNonAnchors = input.items().count { isApplicable(it) }

        return amountAnchors == 1 && amountNonAnchors == 1
    }

    override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
        val nonAnchor = input.items().first { isApplicable(it) }

        val result = nonAnchor.copyWithCount(1) //TODO: Figure out how to let you do entire stacks without duping
        result.set(ModDataComponents.ANCHORED, Unit.INSTANCE)

        return result
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return width * height >= 2
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipeSerializers.APPLY_SPECTRE_ANCHOR.get()
    }
}