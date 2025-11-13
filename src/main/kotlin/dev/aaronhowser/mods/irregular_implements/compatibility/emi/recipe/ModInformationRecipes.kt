package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin.Companion.asEmiIngredient
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModInfoLang
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.aaron.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiInfoRecipe
import dev.emi.emi.api.stack.EmiIngredient
import net.minecraft.client.resources.language.I18n
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.EnchantedBookItem
import net.minecraft.world.item.enchantment.EnchantmentInstance
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.crafting.DataComponentIngredient

object ModInformationRecipes {

	fun getInformationRecipes(): List<EmiInfoRecipe> {
		return getBasicInformationRecipes() + complexRecipes()
	}

	private fun getBasicInformationRecipes(): List<EmiInfoRecipe> {

		val items = ModItems.ITEM_REGISTRY.entries.map { it.get() }

		return buildList {

			for (itemLike: ItemLike in items) {
				val item = itemLike.asItem()

				val infoString = ModInfoLang.getInfoString(item)

				if (!I18n.exists(infoString)) {
					IrregularImplements.LOGGER.debug("No info string for item: $infoString")
					continue
				}

				val id = BuiltInRegistries.ITEM.getKey(item).toString().replace(':', '_')

				val recipe = EmiInfoRecipe(
					listOf(
						item.asEmiIngredient()
					),
					listOf(infoString.toComponent()),
					OtherUtil.modResource("/info/$id")
				)

				add(recipe)
			}

		}
	}

	private fun toEmiIngredients(vararg itemLikes: ItemLike): List<EmiIngredient> {
		return itemLikes.map { it.asEmiIngredient() }
	}

	private fun complexRecipes(): List<EmiInfoRecipe> {

		val recipes = mutableListOf<EmiInfoRecipe>()

		recipes.add(
			EmiInfoRecipe(
				toEmiIngredients(
					ModBlocks.OAK_PLATFORM,
					ModBlocks.SPRUCE_PLATFORM,
					ModBlocks.BIRCH_PLATFORM,
					ModBlocks.JUNGLE_PLATFORM,
					ModBlocks.ACACIA_PLATFORM,
					ModBlocks.DARK_OAK_PLATFORM,
					ModBlocks.CRIMSON_PLATFORM,
					ModBlocks.WARPED_PLATFORM,
					ModBlocks.MANGROVE_PLATFORM,
					ModBlocks.BAMBOO_PLATFORM,
					ModBlocks.CHERRY_PLATFORM,
					ModBlocks.SUPER_LUBRICANT_PLATFORM,
					ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM
				),
				listOf(ModInfoLang.PLATFORM.toComponent()),
				OtherUtil.modResource("/info/platform")
			)
		)

		recipes.add(
			EmiInfoRecipe(
				toEmiIngredients(
					ModBlocks.BIOME_GLASS,
					ModBlocks.BIOME_STONE_BRICKS,
					ModBlocks.BIOME_STONE_BRICKS_CHISELED,
					ModBlocks.BIOME_STONE_BRICKS_CRACKED,
					ModBlocks.BIOME_STONE,
					ModBlocks.BIOME_COBBLESTONE
				),
				listOf(ModInfoLang.BIOME_BLOCKS.toComponent()),
				OtherUtil.modResource("/info/biome_blocks")
			)
		)

		recipes.add(
			EmiInfoRecipe(
				listOf(ModItemTagsProvider.SUPER_LUBRICATED_BLOCKS.asEmiIngredient()),
				listOf(ModInfoLang.LUBRICANT.toComponent()),
				OtherUtil.modResource("/info/lubricated_blocks")
			)
		)

		recipes.add(
			EmiInfoRecipe(
				toEmiIngredients(
					ModItems.SPECTRE_HELMET,
					ModItems.SPECTRE_CHESTPLATE,
					ModItems.SPECTRE_LEGGINGS,
					ModItems.SPECTRE_BOOTS
				),
				listOf(ModInfoLang.SPECTRE_ARMOR.toComponent()),
				OtherUtil.modResource("/info/spectre_armor")
			)
		)

		recipes.add(
			EmiInfoRecipe(
				toEmiIngredients(
					ModItems.SPECTRE_CHARGER_BASIC,
					ModItems.SPECTRE_CHARGER_REDSTONE,
					ModItems.SPECTRE_CHARGER_ENDER,
				),
				listOf(ModInfoLang.SPECTRE_CHARGERS.toComponent()),
				OtherUtil.modResource("/info/spectre_chargers")
			)
		)

		val registryAccess = ClientUtil.localPlayer?.registryAccess()
		if (registryAccess != null) {
			recipes.add(
				EmiInfoRecipe(
					listOf(
						EmiIngredient.of(
							DataComponentIngredient.of(
								true,
								EnchantedBookItem.createForEnchantment(
									EnchantmentInstance(
										ModEnchantments.getHolder(ModEnchantments.MAGNETIC, registryAccess),
										1
									)
								)
							),
						)
					),
					listOf(ModInfoLang.MAGNETIC_ENCHANT.toComponent()),
					OtherUtil.modResource("/info/magnetic")
				)
			)
		}

		return recipes
	}

}