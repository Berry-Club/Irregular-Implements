package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.*
import dev.aaronhowser.mods.irregular_implements.recipe.machine.ImbuingRecipe
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModRecipeSerializers {

	val RECIPE_SERIALIZERS_REGISTRY: DeferredRegister<RecipeSerializer<*>> =
		DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, IrregularImplements.MOD_ID)

	val LUBRICATE_BOOT: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("lubricate_boot", ::LubricateBootRecipe)

	val WASH_BOOT: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("wash_boot", ::WashBootRecipe)

	val DIVINING_ROD: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("divining_rod", ::DiviningRodRecipe)

	val APPLY_SPECTRE_ANCHOR: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("apply_spectre_anchor", ::ApplySpectreAnchorRecipe)

	val SET_DIAPHANOUS_BLOCK: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("set_diaphanous_block", ::SetDiaphanousBlockRecipe)

	val INVERT_DIAPHANOUS_BLOCK: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("invert_diaphanous_block", ::InvertDiaphanousBlockRecipe)

	val CUSTOM_CRAFTING_TABLE: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("custom_crafting_table", ::CustomCraftingTableRecipe)

	val IMBUING: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		registerRecipeSerializer("imbuing", ImbuingRecipe::Serializer)

	val SET_PORTKEY_DISGUISE: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("set_portkey_disguise", ::SetPortkeyDisguiseRecipe)

	val APPLY_LUMINOUS_POWDER: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("apply_luminous_powder", ::ApplyLuminousPowderRecipe)

	val SET_EMERALD_COMPASS_PLAYER: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("set_emerald_compass_player", ::SetEmeraldCompassPlayerRecipe)

	val SET_GOLDEN_COMPASS_POSITION: DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> =
		simple("set_golden_compass_position", ::SetGoldenCompassPositionRecipe)

	private fun simple(name: String, factory: SimpleCraftingRecipeSerializer.Factory<*>): DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> {
		return registerRecipeSerializer(name) { SimpleCraftingRecipeSerializer(factory) }
	}

	private fun registerRecipeSerializer(
		name: String,
		factory: () -> RecipeSerializer<*>
	): DeferredHolder<RecipeSerializer<*>, RecipeSerializer<*>> {
		return RECIPE_SERIALIZERS_REGISTRY.register(name, factory)
	}

}