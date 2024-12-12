package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiInfoRecipe
import dev.emi.emi.api.stack.EmiIngredient
import net.minecraft.client.resources.language.I18n
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike

object ModInformationRecipes {

    fun getInformationRecipes(): List<EmiInfoRecipe> {
        return getBasicInformationRecipes() + complexRecipes()
    }

    private fun getBasicInformationRecipes(): List<EmiInfoRecipe> {

        val items = ModItems.ITEM_REGISTRY.entries.map { it.get() }

        return buildList {

            for (itemLike: ItemLike in items) {
                val item = itemLike.asItem()

                val infoString = ModLanguageProvider.getInfoString(item)

                if (!I18n.exists(infoString)) {
                    IrregularImplements.LOGGER.debug("No info string for item: $infoString")
                    continue
                }

                val id = BuiltInRegistries.ITEM.getKey(item).toString().replace(':', '_')

                val recipe = EmiInfoRecipe(
                    listOf(
                        EmiIngredient.of(Ingredient.of(item))
                    ),
                    listOf(infoString.toComponent()),
                    OtherUtil.modResource("/info/$id")
                )

                add(recipe)
            }

        }
    }

    private fun toEmiIngredients(vararg itemLikes: ItemLike): List<EmiIngredient> {
        return itemLikes.map { EmiIngredient.of(Ingredient.of(it)) }
    }

    private fun complexRecipes(): List<EmiInfoRecipe> {
        val platforms = EmiInfoRecipe(
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
            listOf(ModLanguageProvider.Info.PLATFORM.toComponent()),
            OtherUtil.modResource("/info/platform")
        )

        val biomeBlocks = EmiInfoRecipe(
            toEmiIngredients(
                ModBlocks.BIOME_GLASS,
                ModBlocks.BIOME_STONE_BRICKS,
                ModBlocks.BIOME_STONE_BRICKS_CHISELED,
                ModBlocks.BIOME_STONE_BRICKS_CRACKED,
                ModBlocks.BIOME_STONE,
                ModBlocks.BIOME_COBBLESTONE
            ),
            listOf(ModLanguageProvider.Info.BIOME_BLOCKS.toComponent()),
            OtherUtil.modResource("/info/biome_blocks")
        )

        val lubricatedBlocks = EmiInfoRecipe(
            listOf(EmiIngredient.of(Ingredient.of(ModItemTagsProvider.SUPER_LUBRICATED_BLOCKS))),
            listOf(ModLanguageProvider.Info.LUBRICANT.toComponent()),
            OtherUtil.modResource("/info/lubricated_blocks")
        )

        return listOf(platforms, biomeBlocks, lubricatedBlocks)
    }

}