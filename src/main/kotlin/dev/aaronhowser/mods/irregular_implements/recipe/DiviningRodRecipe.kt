package dev.aaronhowser.mods.irregular_implements.recipe

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

class DiviningRodRecipe(
    val oreTag: TagKey<Item>,
    val craftingCategory: CraftingBookCategory = CraftingBookCategory.MISC
) : CustomRecipe(craftingCategory) {

    companion object {
        private val oreSlots = setOf(Pair(0, 0), Pair(0, 2))
        private val stickSlots = setOf(Pair(0, 1), Pair(1, 0), Pair(1, 2), Pair(2, 1), Pair(2, 2))
        private val eyeSlots = setOf(Pair(1, 1))
    }

    override fun matches(input: CraftingInput, level: Level): Boolean {
        return oreSlots.all { input.getItem(it.first, it.second).`is`(oreTag) }
                && stickSlots.all { input.getItem(it.first, it.second).`is`(Items.STICK) }
                && eyeSlots.all { input.getItem(it.first, it.second).`is`(Items.SPIDER_EYE) }
    }

    override fun assemble(input: CraftingInput, registries: HolderLookup.Provider): ItemStack {
        return DiviningRodItem.getRodForItemTag(oreTag)
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return width * height >= 9
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipeSerializers.DIVINING_ROD.get()
    }

    class Serializer : RecipeSerializer<DiviningRodRecipe> {
        override fun codec(): MapCodec<DiviningRodRecipe> {
            return CODEC
        }

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, DiviningRodRecipe> {
            return STREAM_CODEC
        }

        companion object {
            val CODEC: MapCodec<DiviningRodRecipe> =
                RecordCodecBuilder.mapCodec { instance ->
                    instance.group(
                        TagKey.codec(Registries.ITEM)
                            .fieldOf("ore_tag")
                            .forGetter(DiviningRodRecipe::oreTag),
                        CraftingBookCategory.CODEC
                            .optionalFieldOf("crafting_category", CraftingBookCategory.MISC)
                            .forGetter(DiviningRodRecipe::craftingCategory)
                    ).apply(instance, ::DiviningRodRecipe)
                }

            val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, DiviningRodRecipe> =
                StreamCodec.composite(
                    OtherUtil.tagKeyStreamCodec(Registries.ITEM), DiviningRodRecipe::oreTag,
                    CraftingBookCategory.STREAM_CODEC, DiviningRodRecipe::craftingCategory,
                    ::DiviningRodRecipe
                )
        }
    }
}