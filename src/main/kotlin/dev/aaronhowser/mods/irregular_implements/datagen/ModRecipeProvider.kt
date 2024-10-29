package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import net.minecraft.advancements.Criterion
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
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

        for (shapelessRecipe in shapelessRecipes) {
            shapelessRecipe.save(recipeOutput)
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

    private fun <T : IngredientType> shapedRecipe(
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

    private fun <T : IngredientType> shapedRecipe(
        output: ItemLike,
        patterns: String,
        definitions: Map<Char, T>,
        unlockedByName: String = "has_log",
        unlockedByCriterion: Criterion<*> = has(ItemTags.LOGS)
    ): ShapedRecipeBuilder {
        return shapedRecipe(output, 1, patterns, definitions, unlockedByName, unlockedByCriterion)
    }

    private val shapedRecipes: List<ShapedRecipeBuilder> = listOf(
        shapedRecipe(
            ModBlocks.FERTILIZED_DIRT,
            2,
            "FBF,BDB,FBF",
            mapOf(
                'F' to IngredientType.ItemLikeIng(Items.ROTTEN_FLESH),
                'B' to IngredientType.ItemLikeIng(Items.BONE_MEAL),
                'D' to IngredientType.ItemLikeIng(Items.DIRT)
            )
        ),
        shapedRecipe(
            ModBlocks.PLAYER_INTERFACE,
            "OEO,OSO,OPO",
            mapOf(
                'O' to IngredientType.TagKeyIng(Tags.Items.OBSIDIANS),
                'E' to IngredientType.ItemLikeIng(Items.ENDER_CHEST),
                'S' to IngredientType.ItemLikeIng(Items.NETHER_STAR),
                'P' to IngredientType.ItemLikeIng(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.LAPIS_GLASS,
            "GGG,GLG,GGG",
            mapOf(
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'L' to IngredientType.TagKeyIng(Tags.Items.STORAGE_BLOCKS_LAPIS)
            )
        ),
        shapedRecipe(
            ModBlocks.LAPIS_LAMP,
            " L ,LRL, L ",
            mapOf(
                'L' to IngredientType.TagKeyIng(Tags.Items.GEMS_LAPIS),
                'R' to IngredientType.ItemLikeIng(Items.REDSTONE_LAMP)
            )
        ),
        shapedRecipe(
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
        shapedRecipe(
            ModBlocks.ONLINE_DETECTOR,
            "SRS,RLR,SRS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'R' to IngredientType.ItemLikeIng(Items.REPEATER),
                'L' to IngredientType.TagKeyIng(Tags.Items.GEMS_LAPIS)
            )
        ),
        shapedRecipe(
            ModBlocks.CHAT_DETECTOR,
            "SRS,RDR,SRS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'R' to IngredientType.ItemLikeIng(Items.REPEATER),
                'D' to IngredientType.TagKeyIng(Tags.Items.DYES_RED)
            )
        ),
        shapedRecipe(
            ModBlocks.ENDER_BRIDGE,
            "EEE,ERP,EEE",
            mapOf(
                'E' to IngredientType.TagKeyIng(Tags.Items.END_STONES),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'P' to IngredientType.ItemLikeIng(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.PRISMARINE_ENDER_BRIDGE,
            "SCS,CEC,SCS",
            mapOf(
                'S' to IngredientType.ItemLikeIng(Items.PRISMARINE_SHARD),
                'C' to IngredientType.ItemLikeIng(Items.PRISMARINE_CRYSTALS),
                'E' to IngredientType.ItemLikeIng(ModBlocks.ENDER_BRIDGE)
            )
        ),
        shapedRecipe(
            ModBlocks.ENDER_ANCHOR,
            "OOO,OEO,OOO",
            mapOf(
                'O' to IngredientType.TagKeyIng(Tags.Items.OBSIDIANS),
                'E' to IngredientType.ItemLikeIng(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.LIGHT_REDIRECTOR,
            "PGP,G G,PGP",
            mapOf(
                'P' to IngredientType.TagKeyIng(ItemTags.PLANKS),
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS)
            )
        ),
        shapedRecipe(
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
        shapedRecipe(
            ModBlocks.ANALOG_EMITTER,
            "TIR,III,RIT",
            mapOf(
                'T' to IngredientType.ItemLikeIng(Items.REDSTONE_TORCH),
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.FLUID_DISPLAY,
            "GGG,GBG,GGG",
            mapOf(
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'B' to IngredientType.ItemLikeIng(Items.GLASS_BOTTLE)
            )
        ),
        shapedRecipe(
            ModBlocks.ENDER_MAILBOX,
            "EHE,III, F ",
            mapOf(
                'E' to IngredientType.TagKeyIng(Tags.Items.ENDER_PEARLS),
                'H' to IngredientType.ItemLikeIng(Items.HOPPER),
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'F' to IngredientType.TagKeyIng(Tags.Items.FENCES_WOODEN)
            )
        ),
        shapedRecipe(
            ModBlocks.ENTITY_DETECTOR,
            "STS,EPE,STS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'T' to IngredientType.ItemLikeIng(Items.REDSTONE_TORCH),
                'E' to IngredientType.TagKeyIng(Tags.Items.ENDER_PEARLS),
                'P' to IngredientType.ItemLikeIng(Items.STONE_PRESSURE_PLATE)
            )
        ),
        shapedRecipe(
            ModBlocks.QUARTZ_LAMP,
            " Q ,QLQ, Q ",
            mapOf(
                'Q' to IngredientType.TagKeyIng(Tags.Items.GEMS_QUARTZ),
                'L' to IngredientType.ItemLikeIng(Items.REDSTONE_LAMP)
            )
        ),
        shapedRecipe(
            ModBlocks.QUARTZ_GLASS,
            "GGG,GQG,GGG",
            mapOf(
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'Q' to IngredientType.ItemLikeIng(Items.QUARTZ_BLOCK)
            )
        ),
        shapedRecipe(
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
        shapedRecipe(
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
        shapedRecipe(
            ModBlocks.CONTACT_BUTTON,
            "SIS,SBS,SSS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'I' to IngredientType.ItemLikeIng(Items.IRON_BARS),
                'B' to IngredientType.ItemLikeIng(Items.STONE_BUTTON)
            )
        ),
        shapedRecipe(
            ModBlocks.CONTACT_LEVER,
            "SIS,SLS,SSS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'I' to IngredientType.ItemLikeIng(Items.IRON_BARS),
                'L' to IngredientType.ItemLikeIng(Items.LEVER)
            )
        ),
        shapedRecipe(
            ModBlocks.RAIN_SHIELD,
            " F , B ,NNN",
            mapOf(
                'F' to IngredientType.ItemLikeIng(Items.FLINT),
                'B' to IngredientType.TagKeyIng(Tags.Items.RODS_BLAZE),
                'N' to IngredientType.TagKeyIng(Tags.Items.NETHERRACKS)
            )
        ),
        shapedRecipe(
            ModBlocks.BLOCK_BREAKER,
            "CPC,CTC,CCC",
            mapOf(
                'C' to IngredientType.TagKeyIng(Tags.Items.COBBLESTONES_NORMAL),
                'P' to IngredientType.ItemLikeIng(Items.IRON_PICKAXE),
                'T' to IngredientType.ItemLikeIng(Items.REDSTONE_TORCH)
            )
        ),
        shapedRecipe(
            ModBlocks.SUPER_LUBRICANT_ICE,
            "S,I,B",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.SLIMEBALLS),
                'I' to IngredientType.ItemLikeIng(Items.ICE),
                'B' to IngredientType.ItemLikeIng(Items.WATER_BUCKET)
            )
        ),
        shapedRecipe(
            ModBlocks.REDSTONE_OBSERVER,
            "RQR,QEQ,RQR",
            mapOf(
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'Q' to IngredientType.TagKeyIng(Tags.Items.GEMS_QUARTZ),
                'E' to IngredientType.ItemLikeIng(Items.ENDER_EYE)
            )
        ),
        shapedRecipe(
            ModBlocks.BIOME_RADAR,
            "III,GBG,III",
            mapOf(
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'B' to IngredientType.ItemLikeIng(ModItems.BIOME_SENSOR)
            )
        ),
        shapedRecipe(
            ModBlocks.IRON_DROPPER,
            "III,I I,IDI",
            mapOf(
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'D' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.BLOCK_OF_STICKS,
            16,
            "SSS,S S,SSS",
            mapOf(
                'S' to IngredientType.ItemLikeIng(Items.STICK)
            )
        ),
        shapedRecipe(
            ModBlocks.RETURNING_BLOCK_OF_STICKS,
            8,
            "SSS,SES,SSS",
            mapOf(
                'S' to IngredientType.ItemLikeIng(ModBlocks.BLOCK_OF_STICKS),
                'E' to IngredientType.ItemLikeIng(Items.ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.INVENTORY_REROUTER,
            "SBS,BHB,SBS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'B' to IngredientType.ItemLikeIng(Items.IRON_BARS),
                'H' to IngredientType.ItemLikeIng(Items.HOPPER)
            )
        ),
        shapedRecipe(
            ModBlocks.SLIME_CUBE,
            " S ,SWS, S ",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.SLIMEBALLS),
                'W' to IngredientType.ItemLikeIng(Items.NETHER_STAR)
            )
        ),
        shapedRecipe(
            ModBlocks.NOTIFICATION_INTERFACE,
            "SPS,PQP,SPS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'P' to IngredientType.ItemLikeIng(Items.PAPER),
                'Q' to IngredientType.TagKeyIng(Tags.Items.GEMS_QUARTZ)
            )
        ),
        shapedRecipe(
            ModBlocks.INVENTORY_TESTER,
            2,
            " S ,SRS, C ",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'R' to IngredientType.ItemLikeIng(Items.COMPARATOR),
                'C' to IngredientType.TagKeyIng(Tags.Items.CHESTS_WOODEN)
            )
        ),
        shapedRecipe(
            ModBlocks.SUPER_LUBRICANT_STONE,
            8,
            "SSS,SLS,SSS",
            mapOf(
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES),
                'L' to IngredientType.ItemLikeIng(ModItems.SUPER_LUBRICANT_TINCTURE)
            )
        ),
        shapedRecipe(
            ModBlocks.GLOBAL_CHAT_DETECTOR,
            " T ,RCR, R ",
            mapOf(
                'T' to IngredientType.ItemLikeIng(Items.REDSTONE_TORCH),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'C' to IngredientType.ItemLikeIng(ModBlocks.CHAT_DETECTOR)
            )
        ),
        shapedRecipe(
            ModBlocks.TRIGGER_GLASS,
            4,
            " L ,GRG, L ",
            mapOf(
                'L' to IngredientType.ItemLikeIng(ModBlocks.LAPIS_GLASS),
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.BLOCK_DESTABILIZER,
            "ORO,SDS,ORO",
            mapOf(
                'O' to IngredientType.TagKeyIng(Tags.Items.OBSIDIANS),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'S' to IngredientType.TagKeyIng(Tags.Items.SANDS),
                'D' to IngredientType.TagKeyIng(Tags.Items.GEMS_DIAMOND)
            )
        ),
        shapedRecipe(
            ModBlocks.SOUND_BOX,
            "PPP,PLP,PPP",
            mapOf(
                'P' to IngredientType.TagKeyIng(ItemTags.PLANKS),
                'L' to IngredientType.TagKeyIng(Tags.Items.GEMS_LAPIS)
            )
        ),
        shapedRecipe(
            ModBlocks.SOUND_DAMPENER,
            "PWP,WSW,PWP",
            mapOf(
                'P' to IngredientType.TagKeyIng(ItemTags.PLANKS),
                'W' to IngredientType.TagKeyIng(ItemTags.WOOL),
                'S' to IngredientType.ItemLikeIng(ModItems.PORTABLE_SOUND_DAMPENER)
            )
        ),
        shapedRecipe(
            ModBlocks.DIAPHANOUS_BLOCK,
            4,
            " G ,RBL, Y ",
            mapOf(
                'G' to IngredientType.ItemLikeIng(Items.GREEN_STAINED_GLASS),
                'R' to IngredientType.ItemLikeIng(Items.RED_STAINED_GLASS),
                'L' to IngredientType.ItemLikeIng(Items.BLUE_STAINED_GLASS),
                'B' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS_COLORLESS),
                'Y' to IngredientType.ItemLikeIng(Items.YELLOW_STAINED_GLASS)
            )
        ),
        shapedRecipe(
            ModBlocks.SIDED_BLOCK_OF_REDSTONE,
            "GGR,GGR,GGR",
            mapOf(
                'G' to IngredientType.TagKeyIng(Tags.Items.GUNPOWDERS),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_LENS,
            "SES,DGD,SES",
            mapOf(
                'S' to IngredientType.ItemLikeIng(ModItems.SPECTRE_INGOT),
                'E' to IngredientType.TagKeyIng(Tags.Items.GEMS_EMERALD),
                'D' to IngredientType.TagKeyIng(Tags.Items.GEMS_DIAMOND),
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_ENERGY_INJECTOR,
            "OLO,SBS,OBO",
            mapOf(
                'O' to IngredientType.TagKeyIng(Tags.Items.OBSIDIANS),
                'L' to IngredientType.ItemLikeIng(ModBlocks.SPECTRE_LENS),
                'S' to IngredientType.ItemLikeIng(ModItems.SPECTRE_STRING),
                'B' to IngredientType.ItemLikeIng(Items.BEACON)
            )
        ),
        shapedRecipe(
            ModBlocks.PROCESSING_PLATE,
            "B B,E C,B B",
            mapOf(
                'B' to IngredientType.ItemLikeIng(ModItems.PLATE_BASE),
                'E' to IngredientType.ItemLikeIng(ModBlocks.EXTRACTION_PLATE),
                'C' to IngredientType.ItemLikeIng(ModBlocks.COLLECTION_PLATE)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_COIL,
            "OSO,OIG,OSO",
            mapOf(
                'O' to IngredientType.TagKeyIng(Tags.Items.OBSIDIANS),
                'S' to IngredientType.ItemLikeIng(ModItems.SPECTRE_STRING),
                'I' to IngredientType.ItemLikeIng(ModItems.SPECTRE_INGOT),
                'G' to IngredientType.TagKeyIng(Tags.Items.GLASS_BLOCKS)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_COIL_REDSTONE,
            "BSR,SCS,RSB",
            mapOf(
                'B' to IngredientType.TagKeyIng(Tags.Items.STORAGE_BLOCKS_REDSTONE),
                'S' to IngredientType.ItemLikeIng(ModItems.SPECTRE_STRING),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'C' to IngredientType.ItemLikeIng(ModBlocks.SPECTRE_COIL)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_COIL_ENDER,
            "PSE,SCS,ESP",
            mapOf(
                'P' to IngredientType.ItemLikeIng(ModItems.STABLE_ENDER_PEARL),
                'S' to IngredientType.ItemLikeIng(ModItems.SPECTRE_STRING),
                'E' to IngredientType.TagKeyIng(Tags.Items.ENDER_PEARLS),
                'C' to IngredientType.ItemLikeIng(ModBlocks.SPECTRE_COIL_REDSTONE)
            )
        ),
        shapedRecipe(
            ModItems.ADVANCED_REDSTONE_REPEATER,
            "TRT,ISI",
            mapOf(
                'T' to IngredientType.ItemLikeIng(Items.REDSTONE_TORCH),
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'S' to IngredientType.TagKeyIng(Tags.Items.STONES)
            )
        ),
        shapedRecipe(
            ModItems.ADVANCED_REDSTONE_TORCH,
            " R ,RIR, S ",
            mapOf(
                'R' to IngredientType.TagKeyIng(Tags.Items.DUSTS_REDSTONE),
                'I' to IngredientType.TagKeyIng(Tags.Items.INGOTS_IRON),
                'S' to IngredientType.ItemLikeIng(Items.STICK)
            )
        )
    )

    private fun shapelessRecipe(
        output: ItemLike,
        count: Int,
        requirements: List<IngredientType>,
        unlockedByName: String = "has_log",
        unlockedByCriterion: Criterion<*> = has(ItemTags.LOGS)
    ): ShapelessRecipeBuilder {
        var temp = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count)

        for (requirement in requirements) {
            temp = when (requirement) {
                is IngredientType.TagKeyIng -> temp.requires(requirement.tagKey)
                is IngredientType.ItemLikeIng -> temp.requires(requirement.item)
            }
        }

        return temp.unlockedBy(unlockedByName, unlockedByCriterion)
    }

    private fun shapelessRecipe(
        output: ItemLike,
        requirements: List<IngredientType>,
        unlockedByName: String = "has_log",
        unlockedByCriterion: Criterion<*> = has(ItemTags.LOGS)
    ) = shapelessRecipe(output, 1, requirements, unlockedByName, unlockedByCriterion)

    private val shapelessRecipes = listOf(
        shapelessRecipe(
            ModItems.SUPER_LUBRICANT_TINCTURE,
            listOf(
                IngredientType.TagKeyIng(Tags.Items.SEEDS),
                IngredientType.ItemLikeIng(Items.POTION),        //TODO: Water bottle
                IngredientType.ItemLikeIng(ModItems.BEAN)
            )
        )
    )

}