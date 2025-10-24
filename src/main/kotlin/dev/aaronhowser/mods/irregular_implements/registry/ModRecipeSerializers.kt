package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.*
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import net.neoforged.neoforge.registries.DeferredHolder

object ModRecipeSerializers {

	val RECIPE_SERIALIZERS_REGISTRY: DeferredRegister<RecipeSerializer<*>> =
		DeferredRegister.create(Registries.RECIPE_SERIALIZER, IrregularImplements.ID)

	val LUBRICATE_BOOT: RegistryObject<RecipeSerializer<*>> =
		simple("lubricate_boot", ::LubricateBootRecipe)

	val WASH_BOOT: RegistryObject<RecipeSerializer<*>> =
		simple("wash_boot", ::WashBootRecipe)

	val DIVINING_ROD: RegistryObject<RecipeSerializer<*>> =
		simple("divining_rod", ::DiviningRodRecipe)

	val APPLY_SPECTRE_ANCHOR: RegistryObject<RecipeSerializer<*>> =
		simple("apply_spectre_anchor", ::ApplySpectreAnchorRecipe)

	val SET_DIAPHANOUS_BLOCK: RegistryObject<RecipeSerializer<*>> =
		simple("set_diaphanous_block", ::SetDiaphanousBlockRecipe)

	val INVERT_DIAPHANOUS_BLOCK: RegistryObject<RecipeSerializer<*>> =
		simple("invert_diaphanous_block", ::InvertDiaphanousBlockRecipe)

	val CUSTOM_CRAFTING_TABLE: RegistryObject<RecipeSerializer<*>> =
		simple("custom_crafting_table", ::CustomCraftingTableRecipe)

	val IMBUING: RegistryObject<RecipeSerializer<*>> =
		registerRecipeSerializer("imbuing", ImbuingRecipe::Serializer)

	val SET_PORTKEY_DISGUISE: RegistryObject<RecipeSerializer<*>> =
		simple("set_portkey_disguise", ::SetPortkeyDisguiseRecipe)

	val APPLY_LUMINOUS_POWDER: RegistryObject<RecipeSerializer<*>> =
		simple("apply_luminous_powder", ::ApplyLuminousPowderRecipe)

	val SET_EMERALD_COMPASS_PLAYER: RegistryObject<RecipeSerializer<*>> =
		simple("set_emerald_compass_player", ::SetEmeraldCompassPlayerRecipe)

	val SET_GOLDEN_COMPASS_POSITION: RegistryObject<RecipeSerializer<*>> =
		simple("set_golden_compass_position", ::SetGoldenCompassPositionRecipe)

	private fun simple(name: String, factory: SimpleCraftingRecipeSerializer.Factory<*>): RegistryObject<RecipeSerializer<*>> {
		return registerRecipeSerializer(name) { SimpleCraftingRecipeSerializer(factory) }
	}

	private fun registerRecipeSerializer(
		name: String,
		factory: () -> RecipeSerializer<*>
	): RegistryObject<RecipeSerializer<*>> {
		return RECIPE_SERIALIZERS_REGISTRY.register(name, factory)
	}

}