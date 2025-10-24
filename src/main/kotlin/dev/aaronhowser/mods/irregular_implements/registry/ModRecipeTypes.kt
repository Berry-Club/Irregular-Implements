package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object ModRecipeTypes {

	val RECIPE_TYPES_REGISTRY: DeferredRegister<RecipeType<*>> =
		DeferredRegister.create(Registries.RECIPE_TYPE, IrregularImplements.ID)

	val IMBUING: RegistryObject<RecipeType<ImbuingRecipe>> =
		registerRecipeType("imbuing")

	private fun <T : Recipe<*>> registerRecipeType(
		name: String
	): RegistryObject<RecipeType<T>> {
		return RECIPE_TYPES_REGISTRY.register(name, Supplier { object : RecipeType<T> {} })
	}

}