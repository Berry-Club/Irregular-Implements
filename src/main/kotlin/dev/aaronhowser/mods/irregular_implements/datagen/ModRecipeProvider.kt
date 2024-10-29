package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import net.minecraft.advancements.Criterion
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.Tags
import java.util.concurrent.CompletableFuture

class ModRecipeProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>
) : RecipeProvider(output, lookupProvider) {

    override fun buildRecipes(recipeOutput: RecipeOutput) {
        for (shapedRecipe in shapedRecipes) {
            shapedRecipe.save(recipeOutput)
        }
    }

    //TODO:
    // Potions of Collapse
    // Crafting Tables
    // Platforms
    // Luminous Blocks
    // Translucent Luminous Blocks

    private sealed class IngredientType {
        data class TagKeyIng(val tagKey: TagKey<Item>) : IngredientType()
        data class ItemLikeIng(val item: ItemLike) : IngredientType()
    }

    private fun <T : IngredientType> makeShapedRecipe(
        output: ItemLike,
        count: Int,
        patterns: String,
        definitions: Map<Char, T>,
        unlockedByName: String = "has_log",
        unlockedByCriterion: Criterion<*> = has(ItemTags.LOGS)
    ): ShapedRecipeBuilder {
        var temp = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)

        for (pattern in patterns.split(",")) {
            temp = temp.pattern(pattern)
        }

        for (definition in definitions) {
            temp = when (val ing = definition.value) {
                is IngredientType.TagKeyIng -> temp.define(definition.key, ing.tagKey)
                is IngredientType.ItemLikeIng -> temp.define(definition.key, ing.item)
                else -> error("Unknown ingredient type: $ing")
            }
        }

        return temp.unlockedBy(unlockedByName, unlockedByCriterion)
    }

    private fun <T : IngredientType> makeShapedRecipe(
        output: ItemLike,
        patterns: String,
        definitions: Map<Char, T>,
        unlockedByName: String = "has_log",
        unlockedByCriterion: Criterion<*> = has(ItemTags.LOGS)
    ): ShapedRecipeBuilder {
        return makeShapedRecipe(output, 1, patterns, definitions, unlockedByName, unlockedByCriterion)
    }

    private val shapedRecipes: List<ShapedRecipeBuilder> = listOf(
        makeShapedRecipe(
            ModBlocks.FERTILIZED_DIRT,
            2,
            "FBF,BDB,FBF",
            mapOf(
                'F' to IngredientType.ItemLikeIng(Items.ROTTEN_FLESH),
                'B' to IngredientType.ItemLikeIng(Items.BONE_MEAL),
                'D' to IngredientType.ItemLikeIng(Items.DIRT)
            )
        ),
        makeShapedRecipe(
            ModBlocks.PLAYER_INTERFACE,
            "OEO,OSO,OPO",
            mapOf(
                'O' to IngredientType.TagKeyIng(Tags.Items.OBSIDIANS),
                'E' to IngredientType.ItemLikeIng(Items.ENDER_CHEST),
                'S' to IngredientType.ItemLikeIng(Items.NETHER_STAR),
                'P' to IngredientType.ItemLikeIng(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        makeShapedRecipe(
            ModBlocks.LAPIS_GLASS,
            "GGG,GLG,GGG",
            mapOf(
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'L' to IngredientType.TagKeyIng(Tags.Items.STORAGE_BLOCKS_LAPIS)
            )
        ),
        makeShapedRecipe(
            ModBlocks.LAPIS_LAMP,
            " L ,LRL, L ",
            mapOf(
                'L' to IngredientType.TagKeyIng(Tags.Items.GEMS_LAPIS),
                'R' to IngredientType.ItemLikeIng(Items.REDSTONE_LAMP)
            )
        ),
        makeShapedRecipe(
            ModBlocks.DYEING_MACHINE,
            " G ,RCB, W ",
            mapOf(
                'G' to IngredientType.TagKeyIng(Tags.Items.DYES_RED),
                'R' to IngredientType.TagKeyIng(Tags.Items.DYES_RED),
                'C' to IngredientType.ItemLikeIng(Items.CRAFTING_TABLE),
                'B' to IngredientType.TagKeyIng(Tags.Items.DYES_BLUE),
                'W' to IngredientType.ItemLikeIng(Items.BLACK_WOOL)
            )
        ),
        makeShapedRecipe(
            ModBlocks.ONLINE_DETECTOR,
            "SRS,RLR,SRS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'R' to IngredientType.ItemLikeIng(Items.REPEATER),
                'L' to IngredientType.TagKeyIng(Tags.Items.GEMS_LAPIS)
            )
        ),
        makeShapedRecipe(
            ModBlocks.CHAT_DETECTOR,
            "SRS,RDR,SRS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'R' to IngredientType.ItemLikeIng(Items.REPEATER),
                'D' to IngredientType.TagKeyIng(Tags.Items.DYES_RED)
            )
        ),
        makeShapedRecipe(
            ModBlocks.ENDER_BRIDGE,
            "EEE,ERP,EEE",
            mapOf(
                'E' to IngredientType.TagKeyIng(Tags.Items.END_STONES),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'P' to IngredientType.ItemLikeIng(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        makeShapedRecipe(
            ModBlocks.PRISMARINE_ENDER_BRIDGE,
            "SCS,CEC,SCS",
            mapOf(
                'S' to IngredientType.ItemLikeIng(Items.PRISMARINE_SHARD),
                'C' to IngredientType.ItemLikeIng(Items.PRISMARINE_CRYSTALS),
                'E' to IngredientType.ItemLikeIng(ModBlocks.ENDER_BRIDGE)
            )
        ),
        makeShapedRecipe(
            ModBlocks.ENDER_ANCHOR,
            "OOO,OEO,OOO",
            mapOf(
                'O' to IngredientType.TagKeyIng(Tags.Items.OBSIDIANS),
                'E' to IngredientType.ItemLikeIng(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        makeShapedRecipe(
            ModBlocks.LIGHT_REDIRECTOR,
            "PGP,G G,PGP",
            mapOf(
                'P' to IngredientType.TagKeyIng(ItemTags.PLANKS),
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS)
            )
        ),
        makeShapedRecipe(
            ModBlocks.IMBUING_STATION,
            " W ,VTV,LEL",
            mapOf(
                'W' to IngredientType.ItemLikeIng(Items.WATER_BUCKET),
                'V' to IngredientType.ItemLikeIng(Items.VINE),
                'T' to IngredientType.TagKeyIng(ItemTags.TERRACOTTA),
                'L' to IngredientType.ItemLikeIng(Items.LILY_PAD),
                'E' to IngredientType.TagKeyIng(Tags.Items.GEMS_EMERALD)
            )
        ),
        makeShapedRecipe(
            ModBlocks.ANALOG_EMITTER,
            "TIR,III,RIT",
            mapOf(
                'T' to IngredientType.ItemLikeIng(Items.REDSTONE_TORCH),
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        makeShapedRecipe(
            ModBlocks.FLUID_DISPLAY,
            "GGG,GBG,GGG",
            mapOf(
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'B' to IngredientType.ItemLikeIng(Items.GLASS_BOTTLE)
            )
        ),
        makeShapedRecipe(
            ModBlocks.ENDER_MAILBOX,
            "EHE,III, F ",
            mapOf(
                'E' to IngredientType.TagKeyIng(Tags.Items.ENDER_PEARLS),
                'H' to IngredientType.ItemLikeIng(Items.HOPPER),
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'F' to IngredientType.TagKeyIng(Tags.Items.FENCES_WOODEN)
            )
        ),
        makeShapedRecipe(
            ModBlocks.ENTITY_DETECTOR,
            "STS,EPE,STS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'T' to IngredientType.ItemLikeIng(Items.REDSTONE_TORCH),
                'E' to IngredientType.TagKeyIng(Tags.Items.ENDER_PEARLS),
                'P' to IngredientType.ItemLikeIng(Items.STONE_PRESSURE_PLATE)
            )
        ),
        makeShapedRecipe(
            ModBlocks.QUARTZ_LAMP,
            " Q ,QLQ, Q ",
            mapOf(
                'Q' to IngredientType.TagKeyIng(Tags.Items.GEMS_QUARTZ),
                'L' to IngredientType.ItemLikeIng(Items.REDSTONE_LAMP)
            )
        ),
        makeShapedRecipe(
            ModBlocks.QUARTZ_GLASS,
            "GGG,GQG,GGG",
            mapOf(
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'Q' to IngredientType.ItemLikeIng(Items.QUARTZ_BLOCK)
            )
        ),
        makeShapedRecipe(
            ModBlocks.POTION_VAPORIZER,
            "STS,ICI,SFS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.COBBLESTONES_NORMAL),
                'T' to IngredientType.ItemLikeIng(Items.IRON_TRAPDOOR),
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'C' to IngredientType.ItemLikeIng(Items.CAULDRON),
                'F' to IngredientType.ItemLikeIng(Items.FURNACE)
            )
        ),
        makeShapedRecipe(
            ModBlocks.VOXEL_PROJECTOR,
            "RGB,WLW,WWW",
            mapOf(
                'R' to IngredientType.ItemLikeIng(Items.RED_STAINED_GLASS),
                'G' to IngredientType.ItemLikeIng(Items.GREEN_STAINED_GLASS),
                'B' to IngredientType.ItemLikeIng(Items.BLUE_STAINED_GLASS),
                'W' to IngredientType.ItemLikeIng(Items.BLACK_WOOL),
                'L' to IngredientType.ItemLikeIng(Items.REDSTONE_LAMP)
            )
        ),
        makeShapedRecipe(
            ModBlocks.CONTACT_BUTTON,
            "SIS,SBS,SSS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'I' to IngredientType.ItemLikeIng(Items.IRON_BARS),
                'B' to IngredientType.ItemLikeIng(Items.STONE_BUTTON)
            )
        ),
        makeShapedRecipe(
            ModBlocks.CONTACT_LEVER,
            "SIS,SLS,SSS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'I' to IngredientType.ItemLikeIng(Items.IRON_BARS),
                'L' to IngredientType.ItemLikeIng(Items.LEVER)
            )
        ),
        makeShapedRecipe(
            ModBlocks.RAIN_SHIELD,
            " F , B ,NNN",
            mapOf(
                'F' to IngredientType.ItemLikeIng(Items.FLINT),
                'B' to IngredientType.TagKeyIng(Tags.Items.RODS_BLAZE),
                'N' to IngredientType.TagKeyIng(Tags.Items.NETHERRACKS)
            )
        ),
        makeShapedRecipe(
            ModBlocks.BLOCK_BREAKER,
            "CPC,CTC,CCC",
            mapOf(
                'C' to IngredientType.TagKeyIng(Tags.Items.COBBLESTONES_NORMAL),
                'P' to IngredientType.ItemLikeIng(Items.IRON_PICKAXE),
                'T' to IngredientType.ItemLikeIng(Items.REDSTONE_TORCH)
            )
        ),
        makeShapedRecipe(
            ModBlocks.SUPER_LUBRICANT_ICE,
            "S,I,B",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.SLIMEBALLS),
                'I' to IngredientType.ItemLikeIng(Items.ICE),
                'B' to IngredientType.ItemLikeIng(Items.WATER_BUCKET)
            )
        ),
        makeShapedRecipe(
            ModBlocks.REDSTONE_OBSERVER,
            "RQR,QEQ,RQR",
            mapOf(
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'Q' to IngredientType.TagKeyIng(Tags.Items.GEMS_QUARTZ),
                'E' to IngredientType.ItemLikeIng(Items.ENDER_EYE)
            )
        ),
        makeShapedRecipe(
            ModBlocks.BIOME_RADAR,
            "III,GBG,III",
            mapOf(
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'B' to IngredientType.ItemLikeIng(ModItems.BIOME_SENSOR)
            )
        ),
        makeShapedRecipe(
            ModBlocks.IRON_DROPPER,
            "III,I I,IDI",
            mapOf(
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'D' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        makeShapedRecipe(
            ModBlocks.BLOCK_OF_STICKS,
            16,
            "SSS,S S,SSS",
            mapOf(
                'S' to IngredientType.ItemLikeIng(Items.STICK)
            )
        ),
        makeShapedRecipe(
            ModBlocks.RETURNING_BLOCK_OF_STICKS,
            8,
            "SSS,SES,SSS",
            mapOf(
                'S' to IngredientType.ItemLikeIng(ModBlocks.BLOCK_OF_STICKS),
                'E' to IngredientType.ItemLikeIng(Items.ENDER_PEARL)
            )
        )
    )

}