package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModRecipeTypes {

	val RECIPE_TYPES_REGISTRY: DeferredRegister<RecipeType<*>> =
		DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, IrregularImplements.ID)

	val IMBUING: DeferredHolder<RecipeType<*>, RecipeType<ImbuingRecipe>> =
		registerRecipeType("imbuing")

	private fun <T : Recipe<*>> registerRecipeType(
		name: String
	): DeferredHolder<RecipeType<*>, RecipeType<T>> {
		return RECIPE_TYPES_REGISTRY.register(name, Supplier { object : RecipeType<T> {} })
	}

}