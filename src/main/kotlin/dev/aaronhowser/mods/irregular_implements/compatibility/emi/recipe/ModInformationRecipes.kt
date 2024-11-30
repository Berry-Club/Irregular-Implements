package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiInfoRecipe
import dev.emi.emi.api.stack.EmiIngredient
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike

object ModInformationRecipes {

    fun getInformationRecipes(): List<EmiInfoRecipe> {
        return getBasicInformationRecipes() + complexRecipes()
    }

    private fun getBasicInformationRecipes(): List<EmiInfoRecipe> {

        val itemsWithBasicInfo = listOf(
            ModBlocks.FERTILIZED_DIRT,
            ModBlocks.PLAYER_INTERFACE,
            ModBlocks.LAPIS_GLASS,
            ModBlocks.LAPIS_LAMP,
            ModBlocks.ONLINE_DETECTOR,
            ModBlocks.CHAT_DETECTOR,
            ModBlocks.ENDER_BRIDGE,
            ModBlocks.PRISMARINE_ENDER_BRIDGE,
            ModBlocks.ENDER_ANCHOR,
            ModBlocks.BEAN_POD,
            ModBlocks.SPECTRE_BLOCK,
            ModBlocks.ANALOG_EMITTER,
            ModBlocks.FLUID_DISPLAY,
            ModBlocks.ENDER_MAILBOX,
            ModBlocks.PITCHER_PLANT,
            ModBlocks.ENTITY_DETECTOR,
            ModBlocks.QUARTZ_LAMP,
            ModBlocks.QUARTZ_GLASS,
            ModBlocks.POTION_VAPORIZER,
            ModBlocks.CONTACT_LEVER,
            ModBlocks.CONTACT_BUTTON,
            ModBlocks.RAIN_SHIELD,
            ModBlocks.BLOCK_BREAKER,
            ModBlocks.SUPER_LUBRICANT_ICE,
            ModBlocks.SUPER_LUBRICANT_STONE,
            ModBlocks.COMPRESSED_SLIME_BLOCK,
            ModBlocks.REDSTONE_OBSERVER,
            ModBlocks.BIOME_RADAR,
            ModBlocks.IRON_DROPPER,
            ModBlocks.IGNITER,
            ModBlocks.BLOCK_OF_STICKS,
            ModBlocks.RETURNING_BLOCK_OF_STICKS,
            ModBlocks.INVENTORY_REROUTER,
            ModBlocks.SLIME_CUBE,
            ModBlocks.PEACE_CANDLE,
            ModBlocks.NOTIFICATION_INTERFACE,
            ModBlocks.INVENTORY_TESTER,
            ModBlocks.GLOBAL_CHAT_DETECTOR,
            ModBlocks.TRIGGER_GLASS,
            ModBlocks.BLOCK_DESTABILIZER,
            ModBlocks.SOUND_BOX,
            ModBlocks.SOUND_DAMPENER,
            ModBlocks.SIDED_BLOCK_OF_REDSTONE,
            ModBlocks.SPECTRE_LENS,
            ModBlocks.SPECTRE_ENERGY_INJECTOR,
            ModBlocks.SPECTRE_COIL,
            ModBlocks.SPECTRE_COIL_REDSTONE,
            ModBlocks.SPECTRE_COIL_ENDER,
            ModBlocks.SPECTRE_COIL_NUMBER,
            ModBlocks.SPECTRE_COIL_GENESIS,
            ModItems.ADVANCED_REDSTONE_REPEATER,
            ModItems.ADVANCED_REDSTONE_TORCH,
            ModBlocks.SPECTRE_SAPLING,
            ModBlocks.ITEM_COLLECTOR,
            ModBlocks.ADVANCED_ITEM_COLLECTOR,
            ModBlocks.BIOME_GLASS,
            ModBlocks.BIOME_STONE_BRICKS,
            ModBlocks.BIOME_STONE_BRICKS_CHISELED,
            ModBlocks.BIOME_STONE_BRICKS_CRACKED,
            ModBlocks.BIOME_STONE,
            ModBlocks.BIOME_COBBLESTONE,
            ModBlocks.RAINBOW_LAMP,
            ModBlocks.BASIC_REDSTONE_INTERFACE,
            ModBlocks.ADVANCED_REDSTONE_INTERFACE,
            ModBlocks.REDIRECTOR_PLATE,
            ModBlocks.FILTERED_REDIRECTOR_PLATE,
            ModBlocks.REDSTONE_PLATE,
            ModBlocks.CORRECTOR_PLATE,
            ModBlocks.ITEM_SEALER_PLATE,
            ModBlocks.ITEM_REJUVINATOR_PLATE,
            ModBlocks.ACCELERATOR_PLATE,
            ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE,
            ModBlocks.BOUNCY_PLATE,
            ModBlocks.COLLECTION_PLATE,
            ModBlocks.EXTRACTION_PLATE,
            ModItems.STABLE_ENDER_PEARL,
            ModItems.BIOME_CRYSTAL,
            ModItems.LOCATION_FILTER,
            ModItems.LESSER_MAGIC_BEAN,
            ModItems.MAGIC_BEAN,
            ModItems.REDSTONE_TOOL,
            ModItems.WATER_WALKING_BOOTS,
            ModItems.LAVA_CHARM,
            ModItems.OBSIDIAN_SKULL,
            ModItems.OBSIDIAN_SKULL_RING,
            ModItems.MAGIC_HOOD,
            ModItems.FIRE_IMBUE,
            ModItems.POISON_IMBUE,
            ModItems.EXPERIENCE_IMBUE,
            ModItems.WITHER_IMBUE,
            ModItems.BOTTLE_OF_AIR,
            ModItems.ENDER_LETTER,
            ModItems.ENTITY_FILTER,
            ModItems.SAKANADE_SPORES,
            ModItems.ECTOPLASM,
            ModItems.LUMINOUS_POWDER,
            ModItems.LOTUS_BLOSSOM,
            ModItems.GOLDEN_EGG,
            ModItems.BLACKOUT_POWDER,
            ModItems.ITEM_FILTER,
            ModItems.REDSTONE_ACTIVATOR,
            ModItems.REDSTONE_REMOTE,
            ModItems.BLAZE_AND_STEEL,
            ModItems.RUNE_PATTERN,
            ModItems.FLOO_SIGN,
            ModItems.ID_CARD,
            ModItems.EMERALD_COMPASS,
            ModItems.SOUND_PATTERN,
            ModItems.SOUND_RECORDER,
            ModItems.PORTABLE_SOUND_DAMPENER,
        )

        return buildList {

            for (deferredItemLike in itemsWithBasicInfo) {

                val textComponent = ModLanguageProvider.getInfoString(deferredItemLike).toComponent()
                val id = deferredItemLike.key!!.location().toString().replace(':', '_')

                val recipe = EmiInfoRecipe(
                    listOf(
                        EmiIngredient.of(Ingredient.of(deferredItemLike))
                    ),
                    listOf(textComponent),
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

        return listOf(platforms, biomeBlocks)
    }

}