package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.recipe.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModRecipeSerializers {

    val RECIPE_SERIALIZERS_REGISTRY: DeferredRegister<RecipeSerializer<*>> =
        DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, IrregularImplements.ID)

    val LUBRICATE_BOOT: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
        registerRecipeSerializer("lubricate_boot") { SimpleCraftingRecipeSerializer(::LubricateBootRecipe) }

    val WASH_BOOT: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
        registerRecipeSerializer("wash_boot") { SimpleCraftingRecipeSerializer(::WashBootRecipe) }

    val DIVINING_ROD: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
        registerRecipeSerializer("divining_rod") { SimpleCraftingRecipeSerializer(::DiviningRodRecipe) }

    val APPLY_SPECTRE_ANCHOR: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
        registerRecipeSerializer("apply_spectre_anchor") { SimpleCraftingRecipeSerializer(::ApplySpectreAnchorRecipe) }

    val SET_DIAPHANOUS_BLOCK: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
        registerRecipeSerializer("set_diaphanous_block") { SimpleCraftingRecipeSerializer(::SetDiaphanousBlockRecipe) }

    val INVERT_DIAPHANOUS_BLOCK: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
        registerRecipeSerializer("invert_diaphanous_block") { SimpleCraftingRecipeSerializer(::InvertDiaphanousBlockRecipe) }

    private fun registerRecipeSerializer(
        name: String,
        factory: () -> RecipeSerializer<*>
    ): DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> {
        return RECIPE_SERIALIZERS_REGISTRY.register(name, factory)
    }

}