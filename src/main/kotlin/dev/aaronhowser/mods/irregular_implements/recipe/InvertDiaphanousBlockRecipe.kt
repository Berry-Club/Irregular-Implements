package dev.aaronhowser.mods.irregular_implements.recipe

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.util.Unit
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

//TODO: EMI
class InvertDiaphanousBlockRecipe(
    craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

    override fun matches(input: CraftingInput, level: Level): Boolean {
        return input.items().count { it.`is`(ModBlocks.DIAPHANOUS_BLOCK.asItem()) } == 1
    }

    override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
        val inputStack = input.items().first { it.`is`(ModBlocks.DIAPHANOUS_BLOCK.asItem()) }
        val output = inputStack.copyWithCount(1)

        if (output.has(ModDataComponents.IS_INVERTED)) {
            output.remove(ModDataComponents.IS_INVERTED)
        } else {
            output.set(ModDataComponents.IS_INVERTED, Unit.INSTANCE)
        }

        return output
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return width * height >= 1
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipeSerializers.INVERT_DIAPHANOUS_BLOCK.get()
    }
}