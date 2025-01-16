package dev.aaronhowser.mods.irregular_implements.recipe.machine

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeSerializers
import dev.aaronhowser.mods.irregular_implements.registry.ModRecipeTypes
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class ImbuingRecipe(
    private val outerIngredients: List<Ingredient>,
    private val centerIngredient: Ingredient,
    private val output: ItemStack
) : Recipe<ImbuingInput> {

    override fun matches(input: ImbuingInput, level: Level): Boolean {
        val outerItems = input.getOuterItems()
        val innerItem = input.getCenterItem()

        if (outerItems.size != this.outerIngredients.size) return false
        if (!this.centerIngredient.test(innerItem)) return false

        // outer item order shouldn't matter

        val missingOuterItems = outerItems.toMutableList()

        for (outerIngredient in this.outerIngredients) {

            val iterator = missingOuterItems.iterator()
            var found = false

            while (iterator.hasNext()) {
                val outerItem = iterator.next()
                if (outerIngredient.test(outerItem)) {
                    iterator.remove()
                    found = true
                    break
                }
            }

            if (!found) return false
        }

        return outerItems.isEmpty()
    }

    override fun assemble(input: ImbuingInput, registries: HolderLookup.Provider): ItemStack {
        return this.output.copy()
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return true
    }

    override fun getResultItem(registries: HolderLookup.Provider): ItemStack {
        return this.output.copy()
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipeSerializers.IMBUING.get()
    }

    override fun getType(): RecipeType<*> {
        return ModRecipeTypes.IMBUING.get()
    }

    class Serializer : RecipeSerializer<ImbuingRecipe> {

        override fun codec(): MapCodec<ImbuingRecipe> {
            return CODEC
        }

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, ImbuingRecipe> {
            return STREAM_CODEC
        }

        companion object {
            val CODEC: MapCodec<ImbuingRecipe> =
                RecordCodecBuilder.mapCodec { instance ->
                    instance.group(
                        Ingredient.CODEC_NONEMPTY.listOf()
                            .fieldOf("outer_ingredients")
                            .forGetter(ImbuingRecipe::outerIngredients),
                        Ingredient.CODEC_NONEMPTY
                            .fieldOf("center_ingredient")
                            .forGetter(ImbuingRecipe::centerIngredient),
                        ItemStack.CODEC
                            .fieldOf("output")
                            .forGetter(ImbuingRecipe::output)
                    ).apply(instance, ::ImbuingRecipe)
                }

            val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ImbuingRecipe> =
                StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), ImbuingRecipe::outerIngredients,
                    Ingredient.CONTENTS_STREAM_CODEC, ImbuingRecipe::centerIngredient,
                    ItemStack.STREAM_CODEC, ImbuingRecipe::output,
                    ::ImbuingRecipe
                )
        }
    }

    companion object {
        fun getRecipe(level: Level, input: ImbuingInput): ImbuingRecipe? {
            return level.recipeManager.getAllRecipesFor(ModRecipeTypes.IMBUING.get()).find { recipeHolder ->
                recipeHolder.value.matches(input, level)
            }?.value
        }

        fun hasRecipe(level: Level, input: ImbuingInput): Boolean {
            return getRecipe(level, input) != null
        }
    }

}