package dev.aaronhowser.mods.irregular_implements.recipe

import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import net.minecraft.core.HolderLookup
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level
import kotlin.jvm.optionals.getOrNull

class DiviningRodRecipe(
    craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

    companion object {
        private val oreSlots = setOf(Pair(0, 0), Pair(0, 2))
        private val stickSlots = setOf(Pair(0, 1), Pair(1, 0), Pair(1, 2), Pair(2, 1), Pair(2, 2))
        private val eyeSlots = setOf(Pair(1, 1))

        private fun getOreTag(input: CraftingInput): TagKey<Item>? {
            val topLeftStack = input.getItem(0, 0)
            val topRightStack = input.getItem(0, 2)

            return topLeftStack.tags
                .filter { tag ->
                    tag.location.toString().startsWith("c:ores/")
                            && topRightStack.`is`(tag)
                }
                .findFirst()
                .getOrNull()
        }
    }

    override fun matches(input: CraftingInput, level: Level): Boolean {
        val oreTag = getOreTag(input) ?: return false

        val oresMatch = oreSlots.all { (x, y) -> input.getItem(x, y).`is`(oreTag) }
        val sticksMatch = stickSlots.all { (x, y) -> input.getItem(x, y).`is`(Items.STICK) }
        val eyeMatch = eyeSlots.all { (x, y) -> input.getItem(x, y).`is`(Items.SPIDER_EYE) }

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
}