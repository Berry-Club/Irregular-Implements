package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.recipe.LubricateBootRecipe
import dev.aaronhowser.mods.irregular_implements.recipe.WashBootRecipe
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.advancements.Criterion
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.crafting.DataComponentIngredient
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

        for (platform in platforms()) {
            platform.save(recipeOutput)
        }

        for (coloredThing in coloredThings()) {
            coloredThing.save(recipeOutput)
        }

        populatedNamedRecipes()

        for ((recipe, name) in recipesWithNames) {
            recipe.save(recipeOutput, OtherUtil.modResource(name))
        }

        lubricateBoot.save(recipeOutput, OtherUtil.modResource("lubricate_boot"))
        washBoot.save(recipeOutput, OtherUtil.modResource("wash_boot"))

    }

    private val lubricateBoot = SpecialRecipeBuilder.special(::LubricateBootRecipe)
    private val washBoot = SpecialRecipeBuilder.special(::WashBootRecipe)

    //TODO:
    // Potions of Collapse
    // Crafting Tables
    // Imbues
    // Weather Eggs
    // Spectre Charger tiers
    // Divining Rods

    private sealed class IngredientType {
        data class TagKeyIng(val tagKey: TagKey<Item>) : IngredientType()
        data class ItemLikeIng(val item: ItemLike) : IngredientType()
        data class ItemStackIng(val itemStack: ItemStack) : IngredientType()

        fun getIngredient(): Ingredient {
            return when (this) {
                is TagKeyIng -> Ingredient.of(tagKey)
                is ItemLikeIng -> Ingredient.of(item)
                is ItemStackIng -> if (itemStack.isComponentsPatchEmpty) {
                    Ingredient.of(itemStack)
                } else {
                    DataComponentIngredient.of(false, itemStack)
                }

            }
        }

    }

    private fun ing(tagKey: TagKey<Item>) = IngredientType.TagKeyIng(tagKey)
    private fun ing(item: ItemLike) = IngredientType.ItemLikeIng(item)
    private fun ing(itemStack: ItemStack) = IngredientType.ItemStackIng(itemStack)

    fun <T : IngredientType> shapedRecipe(
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
                is IngredientType.TagKeyIng -> temp.define(definition.key, ing.getIngredient())
                is IngredientType.ItemLikeIng -> temp.define(definition.key, ing.getIngredient())
                is IngredientType.ItemStackIng -> temp.define(definition.key, ing.getIngredient())

                else -> error("Unknown ingredient type")
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
                'F' to ing(Items.ROTTEN_FLESH),
                'B' to ing(Items.BONE_MEAL),
                'D' to ing(Items.DIRT)
            )
        ),
        shapedRecipe(
            ModBlocks.PLAYER_INTERFACE,
            "OEO,OSO,OPO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'E' to ing(Items.ENDER_CHEST),
                'S' to ing(Items.NETHER_STAR),
                'P' to ing(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.LAPIS_GLASS,
            "GGG,GLG,GGG",
            mapOf(
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'L' to ing(Tags.Items.STORAGE_BLOCKS_LAPIS)
            )
        ),
        shapedRecipe(
            ModBlocks.ONLINE_DETECTOR,
            "SRS,RLR,SRS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'R' to ing(Items.REPEATER),
                'L' to ing(Tags.Items.GEMS_LAPIS)
            )
        ),
        shapedRecipe(
            ModBlocks.CHAT_DETECTOR,
            "SRS,RDR,SRS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'R' to ing(Items.REPEATER),
                'D' to ing(Tags.Items.DYES_RED)
            )
        ),
        shapedRecipe(
            ModBlocks.ENDER_BRIDGE,
            "EEE,ERP,EEE",
            mapOf(
                'E' to ing(Tags.Items.END_STONES),
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'P' to ing(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.PRISMARINE_ENDER_BRIDGE,
            "SCS,CEC,SCS",
            mapOf(
                'S' to ing(Items.PRISMARINE_SHARD),
                'C' to ing(Items.PRISMARINE_CRYSTALS),
                'E' to ing(ModBlocks.ENDER_BRIDGE)
            )
        ),
        shapedRecipe(
            ModBlocks.ENDER_ANCHOR,
            "OOO,OEO,OOO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'E' to ing(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.IMBUING_STATION,
            " W ,VTV,LEL",
            mapOf(
                'W' to ing(Items.WATER_BUCKET),
                'V' to ing(Items.VINE),
                'T' to ing(ItemTags.TERRACOTTA),
                'L' to ing(Items.LILY_PAD),
                'E' to ing(Tags.Items.GEMS_EMERALD)
            )
        ),
        shapedRecipe(
            ModBlocks.ANALOG_EMITTER,
            "TIR,III,RIT",
            mapOf(
                'T' to ing(Items.REDSTONE_TORCH),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'R' to ing(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.FLUID_DISPLAY,
            "GGG,GBG,GGG",
            mapOf(
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'B' to ing(Items.GLASS_BOTTLE)
            )
        ),
        shapedRecipe(
            ModBlocks.ENDER_MAILBOX,
            "EHE,III, F ",
            mapOf(
                'E' to ing(Tags.Items.ENDER_PEARLS),
                'H' to ing(Items.HOPPER),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'F' to ing(Tags.Items.FENCES_WOODEN)
            )
        ),
        shapedRecipe(
            ModBlocks.ENTITY_DETECTOR,
            "STS,EPE,STS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'T' to ing(Items.REDSTONE_TORCH),
                'E' to ing(Tags.Items.ENDER_PEARLS),
                'P' to ing(Items.STONE_PRESSURE_PLATE)
            )
        ),
        shapedRecipe(
            ModBlocks.QUARTZ_GLASS,
            "GGG,GQG,GGG",
            mapOf(
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'Q' to ing(Items.QUARTZ_BLOCK)
            )
        ),
        shapedRecipe(
            ModBlocks.POTION_VAPORIZER,
            "STS,ICI,SFS",
            mapOf(
                'S' to ing(Tags.Items.COBBLESTONES_NORMAL),
                'T' to ing(Items.IRON_TRAPDOOR),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'C' to ing(Items.CAULDRON),
                'F' to ing(Items.FURNACE)
            )
        ),
        shapedRecipe(
            ModBlocks.CONTACT_BUTTON,
            "SIS,SBS,SSS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'I' to ing(Items.IRON_BARS),
                'B' to ing(Items.STONE_BUTTON)
            )
        ),
        shapedRecipe(
            ModBlocks.CONTACT_LEVER,
            "SIS,SLS,SSS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'I' to ing(Items.IRON_BARS),
                'L' to ing(Items.LEVER)
            )
        ),
        shapedRecipe(
            ModBlocks.RAIN_SHIELD,
            " F , B ,NNN",
            mapOf(
                'F' to ing(Items.FLINT),
                'B' to ing(Tags.Items.RODS_BLAZE),
                'N' to ing(Tags.Items.NETHERRACKS)
            )
        ),
        shapedRecipe(
            ModBlocks.BLOCK_BREAKER,
            "CPC,CTC,CCC",
            mapOf(
                'C' to ing(Tags.Items.COBBLESTONES_NORMAL),
                'P' to ing(Items.IRON_PICKAXE),
                'T' to ing(Items.REDSTONE_TORCH)
            )
        ),
        shapedRecipe(
            ModBlocks.SUPER_LUBRICANT_ICE,
            "S,I,B",
            mapOf(
                'S' to ing(Tags.Items.SLIMEBALLS),
                'I' to ing(Items.ICE),
                'B' to ing(Items.WATER_BUCKET)
            )
        ),
        shapedRecipe(
            ModBlocks.REDSTONE_OBSERVER,
            "RQR,QEQ,RQR",
            mapOf(
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'Q' to ing(Tags.Items.GEMS_QUARTZ),
                'E' to ing(Items.ENDER_EYE)
            )
        ),
        shapedRecipe(
            ModBlocks.BIOME_RADAR,
            "III,GBG,III",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'B' to ing(ModItems.BIOME_SENSOR)
            )
        ),
        shapedRecipe(
            ModBlocks.IRON_DROPPER,
            "III,I I,IDI",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'D' to ing(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.BLOCK_OF_STICKS,
            16,
            "SSS,S S,SSS",
            mapOf(
                'S' to ing(Items.STICK)
            )
        ),
        shapedRecipe(
            ModBlocks.RETURNING_BLOCK_OF_STICKS,
            8,
            "SSS,SES,SSS",
            mapOf(
                'S' to ing(ModBlocks.BLOCK_OF_STICKS),
                'E' to ing(Items.ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.INVENTORY_REROUTER,
            "SBS,BHB,SBS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'B' to ing(Items.IRON_BARS),
                'H' to ing(Items.HOPPER)
            )
        ),
        shapedRecipe(
            ModBlocks.SLIME_CUBE,
            " S ,SWS, S ",
            mapOf(
                'S' to ing(Tags.Items.SLIMEBALLS),
                'W' to ing(Items.NETHER_STAR)
            )
        ),
        shapedRecipe(
            ModBlocks.NOTIFICATION_INTERFACE,
            "SPS,PQP,SPS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'P' to ing(Items.PAPER),
                'Q' to ing(Tags.Items.GEMS_QUARTZ)
            )
        ),
        shapedRecipe(
            ModBlocks.INVENTORY_TESTER,
            2,
            " S ,SRS, C ",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'R' to ing(Items.COMPARATOR),
                'C' to ing(Tags.Items.CHESTS_WOODEN)
            )
        ),
        shapedRecipe(
            ModBlocks.SUPER_LUBRICANT_STONE,
            8,
            "SSS,SLS,SSS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'L' to ing(ModItems.SUPER_LUBRICANT_TINCTURE)
            )
        ),
        shapedRecipe(
            ModBlocks.GLOBAL_CHAT_DETECTOR,
            " T ,RCR, R ",
            mapOf(
                'T' to ing(Items.REDSTONE_TORCH),
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'C' to ing(ModBlocks.CHAT_DETECTOR)
            )
        ),
        shapedRecipe(
            ModBlocks.TRIGGER_GLASS,
            4,
            " L ,GRG, L ",
            mapOf(
                'L' to ing(ModBlocks.LAPIS_GLASS),
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'R' to ing(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.BLOCK_DESTABILIZER,
            "ORO,SDS,ORO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'S' to ing(Tags.Items.SANDS),
                'D' to ing(Tags.Items.GEMS_DIAMOND)
            )
        ),
        shapedRecipe(
            ModBlocks.SOUND_BOX,
            "PPP,PLP,PPP",
            mapOf(
                'P' to ing(ItemTags.PLANKS),
                'L' to ing(Tags.Items.GEMS_LAPIS)
            )
        ),
        shapedRecipe(
            ModBlocks.SOUND_DAMPENER,
            "PWP,WSW,PWP",
            mapOf(
                'P' to ing(ItemTags.PLANKS),
                'W' to ing(ItemTags.WOOL),
                'S' to ing(ModItems.PORTABLE_SOUND_DAMPENER)
            )
        ),
        shapedRecipe(
            ModBlocks.SIDED_BLOCK_OF_REDSTONE,
            "GGR,GGR,GGR",
            mapOf(
                'G' to ing(Tags.Items.GUNPOWDERS),
                'R' to ing(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_LENS,
            "SES,DGD,SES",
            mapOf(
                'S' to ing(ModItems.SPECTRE_INGOT),
                'E' to ing(Tags.Items.GEMS_EMERALD),
                'D' to ing(Tags.Items.GEMS_DIAMOND),
                'G' to ing(Tags.Items.GLASS_BLOCKS)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_ENERGY_INJECTOR,
            "OLO,SBS,OBO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'L' to ing(ModBlocks.SPECTRE_LENS),
                'S' to ing(ModItems.SPECTRE_STRING),
                'B' to ing(Items.BEACON)
            )
        ),
        shapedRecipe(
            ModBlocks.PROCESSING_PLATE,
            "B B,E C,B B",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'E' to ing(ModBlocks.EXTRACTION_PLATE),
                'C' to ing(ModBlocks.COLLECTION_PLATE)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_COIL,
            "OSO,OIG,OSO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'S' to ing(ModItems.SPECTRE_STRING),
                'I' to ing(ModItems.SPECTRE_INGOT),
                'G' to ing(Tags.Items.GLASS_BLOCKS)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_COIL_REDSTONE,
            "BSR,SCS,RSB",
            mapOf(
                'B' to ing(Tags.Items.STORAGE_BLOCKS_REDSTONE),
                'S' to ing(ModItems.SPECTRE_STRING),
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'C' to ing(ModBlocks.SPECTRE_COIL)
            )
        ),
        shapedRecipe(
            ModBlocks.SPECTRE_COIL_ENDER,
            "PSE,SCS,ESP",
            mapOf(
                'P' to ing(ModItems.STABLE_ENDER_PEARL),
                'S' to ing(ModItems.SPECTRE_STRING),
                'E' to ing(Tags.Items.ENDER_PEARLS),
                'C' to ing(ModBlocks.SPECTRE_COIL_REDSTONE)
            )
        ),
        shapedRecipe(
            ModItems.ADVANCED_REDSTONE_REPEATER,
            "TRT,ISI",
            mapOf(
                'T' to ing(Items.REDSTONE_TORCH),
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'S' to ing(Tags.Items.STONES)
            )
        ),
        shapedRecipe(
            ModItems.ADVANCED_REDSTONE_TORCH,
            " R ,RIR, S ",
            mapOf(
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'S' to ing(Items.STICK)
            )
        ),
        shapedRecipe(
            ModBlocks.ITEM_COLLECTOR,
            " P , H ,OOO",
            mapOf(
                'P' to ing(Tags.Items.ENDER_PEARLS),
                'H' to ing(Items.HOPPER),
                'O' to ing(Tags.Items.OBSIDIANS)
            )
        ),
        shapedRecipe(
            ModBlocks.ADVANCED_ITEM_COLLECTOR,
            " T ,GCG",
            mapOf(
                'T' to ing(Items.REDSTONE_TORCH),
                'G' to ing(Tags.Items.DUSTS_GLOWSTONE),
                'C' to ing(ModBlocks.ITEM_COLLECTOR)
            )
        ),
        shapedRecipe(
            ModBlocks.BIOME_COBBLESTONE,
            16,
            "CCC,CBC,CCC",
            mapOf(
                'C' to ing(Tags.Items.COBBLESTONES_NORMAL),
                'B' to ing(ModItems.BIOME_CRYSTAL)
            )
        ),
        shapedRecipe(
            ModBlocks.BIOME_STONE,
            16,
            "SSS,SBS,SSS",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'B' to ing(ModItems.BIOME_CRYSTAL)
            )
        ),
        shapedRecipe(
            ModBlocks.BIOME_STONE_BRICKS,
            16,
            "SSS,SBS,SSS",
            mapOf(
                'S' to ing(Items.STONE_BRICKS),
                'B' to ing(ModItems.BIOME_CRYSTAL)
            )
        ),
        shapedRecipe(
            ModBlocks.BIOME_STONE_BRICKS_CRACKED,
            16,
            "SSS,SBS,SSS",
            mapOf(
                'S' to ing(Items.CRACKED_STONE_BRICKS),
                'B' to ing(ModItems.BIOME_CRYSTAL)
            )
        ),
        shapedRecipe(
            ModBlocks.BIOME_STONE_BRICKS_CHISELED,
            16,
            "SSS,SBS,SSS",
            mapOf(
                'S' to ing(Items.CHISELED_STONE_BRICKS),
                'B' to ing(ModItems.BIOME_CRYSTAL)
            )
        ),
        shapedRecipe(
            ModBlocks.BIOME_GLASS,
            16,
            "GGG,GBG,GGG",
            mapOf(
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'B' to ing(ModItems.BIOME_CRYSTAL)
            )
        ),
        shapedRecipe(
            ModBlocks.RAINBOW_LAMP,
            " G ,RLB",
            mapOf(
                'G' to ing(Tags.Items.DYES_GREEN),
                'R' to ing(Tags.Items.DYES_RED),
                'L' to ing(Items.REDSTONE_LAMP),
                'B' to ing(Tags.Items.DYES_BLUE)
            )
        ),
        shapedRecipe(
            ModBlocks.BASIC_REDSTONE_INTERFACE,
            "IRI,RPR,IRI",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'P' to ing(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModBlocks.ADVANCED_REDSTONE_INTERFACE,
            "ROR,OIO,ROR",
            mapOf(
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'O' to ing(Tags.Items.OBSIDIANS),
                'I' to ing(ModBlocks.BASIC_REDSTONE_INTERFACE)
            )
        ),
        shapedRecipe(
            ModBlocks.REDIRECTOR_PLATE,
            2,
            "BGB,G G,BGB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'G' to ing(Tags.Items.DYES_GREEN)
            )
        ),
        shapedRecipe(
            ModBlocks.FILTERED_REDIRECTOR_PLATE,
            2,
            "BGB,Y L,BGB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'G' to ing(Tags.Items.DYES_GREEN),
                'Y' to ing(Tags.Items.DYES_YELLOW),
                'L' to ing(Tags.Items.DYES_BLUE)
            )
        ),
        shapedRecipe(
            ModBlocks.REDSTONE_PLATE,
            2,
            "BGB,R R,BGB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'G' to ing(Tags.Items.DYES_GREEN),
                'R' to ing(Tags.Items.DUSTS_REDSTONE)
            )
        ),
        shapedRecipe(
            ModBlocks.CORRECTOR_PLATE,
            2,
            "BIB,I I,BIB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'I' to ing(Items.IRON_BARS)
            )
        ),
        shapedRecipe(
            ModBlocks.ITEM_SEALER_PLATE,
            2,
            "BLB,L L,BLB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'L' to ing(Tags.Items.GEMS_LAPIS)
            )
        ),
        shapedRecipe(
            ModBlocks.ITEM_REJUVENATOR_PLATE,
            2,
            "BMB,M M,BMB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'M' to ing(Tags.Items.DYES_MAGENTA)
            )
        ),
        shapedRecipe(
            ModBlocks.ACCELERATOR_PLATE,
            2,
            "BRB,R R,BRB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'R' to ing(Tags.Items.DYES_RED)
            )
        ),
        shapedRecipe(
            ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE,
            2,
            "BRB,   ,BRB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'R' to ing(Tags.Items.DYES_RED)
            )
        ),
        shapedRecipe(
            ModBlocks.BOUNCY_PLATE,
            2,
            "B B, S ,B B",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'S' to ing(Tags.Items.SLIMEBALLS)
            )
        ),
        shapedRecipe(
            ModBlocks.COLLECTION_PLATE,
            2,
            "B B, H ,B B",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'H' to ing(Items.HOPPER)
            )
        ),
        shapedRecipe(
            ModBlocks.EXTRACTION_PLATE,
            2,
            "BIB,IHI,BIB",
            mapOf(
                'B' to ing(ModItems.PLATE_BASE),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'H' to ing(Items.HOPPER)
            )
        ),
        shapedRecipe(
            ModItems.STABLE_ENDER_PEARL,
            "OLO,LPL,OLO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'L' to ing(Tags.Items.GEMS_LAPIS),
                'P' to ing(Tags.Items.ENDER_PEARLS)
            )
        ),
        shapedRecipe(
            ModItems.LOCATION_FILTER,
            " M ,MPM, M ",
            mapOf(
                'M' to ing(Tags.Items.DYES_MAGENTA),
                'P' to ing(Items.PAPER)
            )
        ),
        shapedRecipe(
            ModItems.LESSER_MAGIC_BEAN,
            "NNN,NBN,NNN",
            mapOf(
                'N' to ing(Tags.Items.NUGGETS_GOLD),
                'B' to ing(ModItems.BEAN)
            )
        ),
        shapedRecipe(
            ModItems.REDSTONE_TOOL,
            "R,S,S",
            mapOf(
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'S' to ing(Items.STICK)
            )
        ),
        shapedRecipe(
            ModItems.OBSIDIAN_SKULL,
            "OBO,NWN,OBO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'B' to ing(Tags.Items.RODS_BLAZE),
                'N' to ing(Tags.Items.NUGGETS_GOLD),
                'W' to ing(Items.WITHER_SKELETON_SKULL)
            )
        ),
        shapedRecipe(
            ModItems.ENDER_LETTER,
            "PEP, P ",
            mapOf(
                'P' to ing(Items.PAPER),
                'E' to ing(Tags.Items.ENDER_PEARLS)
            )
        ),
        shapedRecipe(
            ModItems.ENTITY_FILTER,
            " L ,LPL, L ",
            mapOf(
                'L' to ing(Tags.Items.GEMS_LAPIS),
                'P' to ing(Items.PAPER)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_INGOT,
            9,
            "ELE,EGE,EEE",
            mapOf(
                'E' to ing(ModItems.ECTOPLASM),
                'L' to ing(Tags.Items.STORAGE_BLOCKS_LAPIS),
                'G' to ing(Tags.Items.STORAGE_BLOCKS_GOLD)
            )
        ),
        shapedRecipe(
            ModItems.BIOME_SENSOR,
            "III,RBI,IRI",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'B' to ing(ModItems.BIOME_CRYSTAL)
            )
        ),
        shapedRecipe(
            ModItems.PLATE_BASE,
            8,
            "I I, B ,I I",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'B' to ing(Items.IRON_BARS)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_STRING,
            4,
            "ESE,SDS,ESE",
            mapOf(
                'E' to ing(ModItems.ECTOPLASM),
                'S' to ing(Tags.Items.STRINGS),
                'D' to ing(Tags.Items.GEMS_DIAMOND)
            )
        ),
        shapedRecipe(
            ModItems.ITEM_FILTER,
            " Y ,YPY, Y ",
            mapOf(
                'Y' to ing(Tags.Items.DYES_YELLOW),
                'P' to ing(Items.PAPER)
            )
        ),
        shapedRecipe(
            ModItems.REDSTONE_ACTIVATOR,
            "IRI,ITI,III",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'R' to ing(Tags.Items.DUSTS_REDSTONE),
                'T' to ing(Items.REDSTONE_TORCH)
            )
        ),
        shapedRecipe(
            ModItems.REDSTONE_REMOTE,
            "RRR,OPO,OOO",
            mapOf(
                'R' to ing(ModItems.REDSTONE_ACTIVATOR),
                'O' to ing(Tags.Items.OBSIDIANS),
                'P' to ing(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModItems.FLOO_SIGN,
            "PPP,PSP,PPP",
            mapOf(
                'P' to ing(ModItems.FLOO_POWDER),
                'S' to ing(ItemTags.SIGNS)
            )
        ),
        shapedRecipe(
            ModItems.EMERALD_COMPASS,
            " E ,ECE, E ",
            mapOf(
                'E' to ing(Tags.Items.GEMS_EMERALD),
                'C' to ing(Items.COMPASS)
            )
        ),
        shapedRecipe(
            ModItems.FLOO_TOKEN,
            "FFF,FPF,FFF",
            mapOf(
                'F' to ing(ModItems.FLOO_POWDER),
                'P' to ing(Items.PAPER)
            )
        ),
        shapedRecipe(
            ModItems.PORTKEY,
            "GEG, D ",
            mapOf(
                'G' to ing(Tags.Items.GUNPOWDERS),
                'E' to ing(ModItems.STABLE_ENDER_PEARL),
                'D' to ing(Tags.Items.GEMS_DIAMOND)
            )
        ),
        shapedRecipe(
            ModItems.SOUND_PATTERN,
            "S,P,L",
            mapOf(
                'S' to ing(Tags.Items.STRINGS),
                'P' to ing(Items.PAPER),
                'L' to ing(Tags.Items.GEMS_LAPIS)
            )
        ),
        shapedRecipe(
            ModItems.SOUND_RECORDER,
            "BGG,ILI,III",
            mapOf(
                'B' to ing(Items.IRON_BARS),
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'L' to ing(Tags.Items.GEMS_LAPIS)
            )
        ),
        shapedRecipe(
            ModItems.PORTABLE_SOUND_DAMPENER,
            "ISI,SLS,ISI",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'S' to ing(Tags.Items.STRINGS),
                'L' to ing(Tags.Items.GEMS_LAPIS)
            )
        ),
        shapedRecipe(
            ModItems.ESCAPE_ROPE,
            "SGP,GSG,PGS",
            mapOf(
                'S' to ing(Tags.Items.STRINGS),
                'G' to ing(Tags.Items.INGOTS_GOLD),
                'P' to ing(Tags.Items.ENDER_PEARLS)
            )
        ),
        shapedRecipe(
            ModItems.ENDER_BUCKET,
            "I I, P ",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'P' to ing(Tags.Items.ENDER_PEARLS)
            )
        ),
        shapedRecipe(
            ModItems.REINFORCED_ENDER_BUCKET,
            "OBO, S ",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'B' to ing(ModItems.ENDER_BUCKET),
                'S' to ing(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModItems.CHUNK_ANALYZER,
            "B B,IGI,SID",
            mapOf(
                'B' to ing(Items.IRON_BARS),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'S' to ing(Tags.Items.STONES),
                'D' to ing(ItemTags.DIRT)
            )
        ),
        shapedRecipe(
            ModItems.FLOO_POUCH,
            " L ,LFL,LLL",
            mapOf(
                'L' to ing(Tags.Items.LEATHERS),
                'F' to ing(ModItems.FLOO_POWDER)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_ILLUMINATOR,
            "EGE,GIG,EGE",
            mapOf(
                'E' to ing(ModItems.ECTOPLASM),
                'G' to ing(Tags.Items.DUSTS_GLOWSTONE),
                'I' to ing(ModItems.LUMINOUS_POWDER)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_KEY,
            "S  ,SP ,  S",
            mapOf(
                'S' to ing(ModItems.SPECTRE_INGOT),
                'P' to ing(ModItems.STABLE_ENDER_PEARL)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_ANCHOR,
            " I ,IEI,III",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'E' to ing(ModItems.ECTOPLASM)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_SWORD,
            "E,E,O",
            mapOf(
                'E' to ing(ModItems.SPECTRE_INGOT),
                'O' to ing(Tags.Items.OBSIDIANS)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_PICKAXE,
            "EEE, O , O ",
            mapOf(
                'E' to ing(ModItems.SPECTRE_INGOT),
                'O' to ing(Tags.Items.OBSIDIANS)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_AXE,
            "EE ,EO , O ",
            mapOf(
                'E' to ing(ModItems.SPECTRE_INGOT),
                'O' to ing(Tags.Items.OBSIDIANS)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_SHOVEL,
            "E,O,O",
            mapOf(
                'E' to ing(ModItems.SPECTRE_INGOT),
                'O' to ing(Tags.Items.OBSIDIANS)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_CHARGER,
            "BOS,OIO,SOB",
            mapOf(
                'B' to ing(Items.IRON_BARS),
                'O' to ing(Tags.Items.OBSIDIANS),
                'S' to ing(ModItems.SPECTRE_STRING),
                'I' to ing(ModItems.SPECTRE_INGOT)
            )
        ),
        shapedRecipe(
            ModItems.WEATHER_EGG_SUNNY,
            2,
            "OFO,SCS,OFO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'F' to ing(Items.FEATHER),
                'S' to ing(Items.SUNFLOWER),
                'C' to ing(Items.FIRE_CHARGE),
            )
        ),
        shapedRecipe(
            ModItems.WEATHER_EGG_RAINY,
            2,
            "OWO,LCL,OWO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'W' to ing(OtherUtil.getPotionStack(Potions.WATER)),
                'L' to ing(Tags.Items.GEMS_LAPIS),
                'C' to ing(Items.FIRE_CHARGE),
            )
        ),
        shapedRecipe(
            ModItems.WEATHER_EGG_STORMY,
            2,
            "OSO,LCL,OSO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'S' to ing(Items.SUGAR),
                'L' to ing(Tags.Items.GEMS_LAPIS),
                'C' to ing(Items.FIRE_CHARGE),
            )
        ),
        shapedRecipe(
            ModBlocks.SHOCK_ABSORBER,
            "WWW,WAW,DDD",
            mapOf(
                'W' to ing(ItemTags.WOOL),
                'A' to ing(Items.WATER_BUCKET),
                'D' to ing(ItemTags.DIRT)
            )
        ),
        shapedRecipe(
            ModItems.BIOME_CAPSULE,
            "DTE,GQG,OOO",
            mapOf(
                'D' to ing(Tags.Items.STORAGE_BLOCKS_DIAMOND),
                'E' to ing(Tags.Items.GEMS_EMERALD),
                'T' to ing(ModItems.TRANSFORMATION_CORE),
                'Q' to ing(Tags.Items.GEMS_QUARTZ),
                'G' to ing(Tags.Items.GLASS_BLOCKS),
                'O' to ing(Tags.Items.OBSIDIANS),
            )
        ),
        shapedRecipe(
            ModItems.BIOME_PAINTER,
            "T,W,O",
            mapOf(
                'T' to ing(ModItems.TRANSFORMATION_CORE),
                'W' to ing(ItemTags.WOOL),
                'O' to ing(ModItemTagsProvider.C_RODS_OBSIDIAN),
            )
        ),
        shapedRecipe(
            ModItems.DROP_FILTER,
            "LSL,SFS,LSL",
            mapOf(
                'L' to ing(Tags.Items.LEATHERS),
                'S' to ing(Tags.Items.STRINGS),
                'F' to ing(Items.FLINT),
            )
        ),
        shapedRecipe(
            ModItems.VOID_STONE,
            " O ,OEO, O ",
            mapOf(
                'O' to ing(Tags.Items.STONES),
                'E' to ing(Tags.Items.ENDER_PEARLS),
            )
        ),
        shapedRecipe(
            ModItems.OBSIDIAN_ROD,
            3,
            "O,O",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
            )
        ),
        shapedRecipe(
            ModItems.MAGNETIC_FORCE,
            "E,M,P",
            mapOf(
                'E' to ing(Tags.Items.ENDER_PEARLS),
                'M' to ing(Tags.Items.GEMS_EMERALD),
                'P' to ing(Items.PAPER),
            )
        ),
        shapedRecipe(
            ModItems.PORTABLE_ENDER_PORTER,
            "P,S,S",
            mapOf(
                'P' to ing(ModBlocks.PRISMARINE_ENDER_BRIDGE),
                'S' to ing(ModItemTagsProvider.C_RODS_OBSIDIAN)
            )
        ),
        shapedRecipe(
            ModItems.BLOCK_MOVER,
            "S S,O O, O ",
            mapOf(
                'S' to ing(ModItems.STABLE_ENDER_PEARL),
                'O' to ing(Tags.Items.OBSIDIANS),
            )
        ),
        shapedRecipe(
            ModItems.DIAMOND_BREAKER,
            " I ,IDI, I ",
            mapOf(
                'I' to ing(Tags.Items.INGOTS_IRON),
                'D' to ing(Items.DIAMOND_PICKAXE),
            )
        ),
        shapedRecipe(
            ModBlocks.AUTO_PLACER,
            "S S,IPI,S S",
            mapOf(
                'S' to ing(Tags.Items.STONES),
                'I' to ing(Tags.Items.INGOTS_IRON),
                'P' to ing(Items.PISTON),
            )
        ),
        shapedRecipe(
            ModBlocks.BLOCK_TELEPORTER,
            "OCO,OEO,OOO",
            mapOf(
                'O' to ing(Tags.Items.OBSIDIANS),
                'C' to ing(Items.COMPARATOR),
                'E' to ing(Tags.Items.ENDER_PEARLS),
            )
        ),
        shapedRecipe(
            ModItems.BLOCK_REPLACER,
            " GF, RL,R  ",
            mapOf(
                'G' to ing(Tags.Items.NUGGETS_GOLD),
                'F' to ing(Items.FLINT),
                'R' to ing(ModItemTagsProvider.C_RODS_OBSIDIAN),
                'L' to ing(Tags.Items.GEMS_LAPIS),
            )
        ),
        shapedRecipe(
            ModBlocks.MOON_PHASE_DETECTOR,
            "GGG,LQL,SSS",
            mapOf(
                'G' to ing(Tags.Items.GLASS_BLOCKS_COLORLESS),
                'L' to ing(Tags.Items.GEMS_LAPIS),
                'Q' to ing(Tags.Items.GEMS_QUARTZ),
                'S' to ing(ItemTags.WOODEN_SLABS)
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_HELMET,
            "SSS,S S",
            mapOf(
                'S' to ing(ModItems.SPECTRE_INGOT),
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_CHESTPLATE,
            "S S,SSS,SSS",
            mapOf(
                'S' to ing(ModItems.SPECTRE_INGOT),
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_LEGGINGS,
            "SSS,S S,S S",
            mapOf(
                'S' to ing(ModItems.SPECTRE_INGOT),
            )
        ),
        shapedRecipe(
            ModItems.SPECTRE_BOOTS,
            "S S,S S",
            mapOf(
                'S' to ing(ModItems.SPECTRE_INGOT),
            )
        ),
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
            temp = temp.requires(requirement.getIngredient())
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
                ing(Tags.Items.SEEDS),
                ing(OtherUtil.getPotionStack(Potions.WATER)),
                ing(ModItems.BEAN)
            )
        ),
        shapelessRecipe(
            ModBlocks.SPECTRE_PLANKS,
            4,
            listOf(ing(ModBlocks.SPECTRE_WOOD))
        ),
        shapelessRecipe(
            ModItems.BEAN_STEW,
            listOf(
                ing(ModItems.BEAN),
                ing(ModItems.BEAN),
                ing(ModItems.BEAN),
                ing(Tags.Items.CROPS_WHEAT),
                ing(Items.BOWL)
            )
        ),
        shapelessRecipe(
            ModItems.LUMINOUS_POWDER,
            4,
            listOf(
                ing(Tags.Items.DUSTS_GLOWSTONE),
                ing(Tags.Items.GLASS_BLOCKS),
                ing(ModBlocks.GLOWING_MUSHROOM)
            )
        ),
        shapelessRecipe(
            ModItems.FLOO_POWDER,
            16,
            listOf(
                ing(Tags.Items.ENDER_PEARLS),
                ing(Tags.Items.DUSTS_REDSTONE),
                ing(Tags.Items.GUNPOWDERS),
                ing(ModItems.BEAN)
            )
        ),
        shapelessRecipe(
            ModItems.BLACKOUT_POWDER,
            4,
            listOf(
                ing(Tags.Items.DYES_BLACK),
                ing(Tags.Items.OBSIDIANS),
                ing(Tags.Items.DYES_BLUE),
            )
        ),
        shapelessRecipe(
            ModItems.BLAZE_AND_STEEL,
            listOf(
                ing(Items.BLAZE_POWDER),
                ing(Tags.Items.INGOTS_IRON)
            )
        ),
        shapelessRecipe(
            ModItems.ID_CARD,
            listOf(
                ing(Items.INK_SAC),
                ing(Items.PAPER)
            )
        ),
        shapelessRecipe(
            ModItems.GRASS_SEEDS,
            listOf(
                ing(Items.GRASS_BLOCK)
            )
        ),
        shapelessRecipe(
            ModItems.VOIDING_DROP_FILTER,
            listOf(
                ing(ModItems.DROP_FILTER),
                ing(ModItems.VOID_STONE)
            )
        ),
        shapelessRecipe(
            ModItems.TRANSFORMATION_CORE,
            listOf(
                ing(Tags.Items.DYES_RED),
                ing(Tags.Items.DYES_ORANGE),
                ing(Tags.Items.DYES_YELLOW),
                ing(Tags.Items.DYES_GREEN),
                ing(Tags.Items.DYES_CYAN),
                ing(Tags.Items.DYES_PURPLE),
                ing(Tags.Items.DYES_GRAY),
                ing(Tags.Items.DYES_LIME),
                ing(Tags.Items.DYES_MAGENTA),
            )
        )
    )

    private fun platforms(): List<ShapedRecipeBuilder> {
        val platformIngredientMap = mapOf(
            ModBlocks.OAK_PLATFORM to Items.OAK_PLANKS,
            ModBlocks.SPRUCE_PLATFORM to Items.SPRUCE_PLANKS,
            ModBlocks.BIRCH_PLATFORM to Items.BIRCH_PLANKS,
            ModBlocks.JUNGLE_PLATFORM to Items.JUNGLE_PLANKS,
            ModBlocks.ACACIA_PLATFORM to Items.ACACIA_PLANKS,
            ModBlocks.DARK_OAK_PLATFORM to Items.DARK_OAK_PLANKS,
            ModBlocks.CRIMSON_PLATFORM to Items.CRIMSON_PLANKS,
            ModBlocks.WARPED_PLATFORM to Items.WARPED_PLANKS,
            ModBlocks.MANGROVE_PLATFORM to Items.MANGROVE_PLANKS,
            ModBlocks.BAMBOO_PLATFORM to Items.BAMBOO_PLANKS,
            ModBlocks.CHERRY_PLATFORM to Items.CHERRY_PLANKS,
            ModBlocks.SUPER_LUBRICANT_PLATFORM to ModBlocks.SUPER_LUBRICANT_ICE
        )

        return platformIngredientMap.map { (platform, ingredient) ->
            shapedRecipe(
                platform.get(),
                6,
                "PPP, E ",
                mapOf(
                    'P' to ing(ingredient),
                    'E' to ing(Tags.Items.ENDER_PEARLS)
                )
            )
        } + shapedRecipe(
            ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM.get(),
            "P,L,S",
            mapOf(
                'P' to ing(Items.PAPER),
                'L' to ing(ModBlocks.SUPER_LUBRICANT_PLATFORM.get()),
                'S' to ing(Tags.Items.STRINGS)
            )
        )
    }

    private val recipesWithNames: MutableMap<RecipeBuilder, String> = mutableMapOf()

    private val dyeTags: Map<DyeColor, TagKey<Item>> = mapOf(
        DyeColor.WHITE to Tags.Items.DYES_WHITE,
        DyeColor.ORANGE to Tags.Items.DYES_ORANGE,
        DyeColor.MAGENTA to Tags.Items.DYES_MAGENTA,
        DyeColor.LIGHT_BLUE to Tags.Items.DYES_LIGHT_BLUE,
        DyeColor.YELLOW to Tags.Items.DYES_YELLOW,
        DyeColor.LIME to Tags.Items.DYES_LIME,
        DyeColor.PINK to Tags.Items.DYES_PINK,
        DyeColor.GRAY to Tags.Items.DYES_GRAY,
        DyeColor.LIGHT_GRAY to Tags.Items.DYES_LIGHT_GRAY,
        DyeColor.CYAN to Tags.Items.DYES_CYAN,
        DyeColor.PURPLE to Tags.Items.DYES_PURPLE,
        DyeColor.BLUE to Tags.Items.DYES_BLUE,
        DyeColor.BROWN to Tags.Items.DYES_BROWN,
        DyeColor.GREEN to Tags.Items.DYES_GREEN,
        DyeColor.RED to Tags.Items.DYES_RED,
        DyeColor.BLACK to Tags.Items.DYES_BLACK
    )

    private fun coloredThings(): List<RecipeBuilder> {
        return buildList {
            for (color in DyeColor.entries) {
                val colorString = color.getName()

                val dyeTag = dyeTags[color]!!

                val luminous = ModBlocks.getLuminousBlock(color).get()
                val transLuminous = ModBlocks.getLuminousBlockTranslucent(color).get()

                val stainedBrick = ModBlocks.getStainedBrick(color).get()
                val transBrick = ModBlocks.getStainedBrickLuminous(color).get()

                val grassSeeds = GrassSeedItem.getFromColor(color)

                add(
                    shapedRecipe(
                        luminous,
                        "LD,LL",
                        mapOf(
                            'L' to ing(ModItems.LUMINOUS_POWDER),
                            'D' to ing(dyeTag)
                        )
                    )
                )

                add(
                    shapedRecipe(
                        transLuminous,
                        " P ,PLP, P ",
                        mapOf(
                            'P' to ing(Tags.Items.GLASS_PANES_COLORLESS),
                            'L' to ing(luminous)
                        )
                    )
                )

                add(
                    shapedRecipe(
                        stainedBrick,
                        8,
                        "BBB,BDB,BBB",
                        mapOf(
                            'B' to ing(Items.BRICKS),
                            'D' to ing(dyeTag)
                        )
                    )
                )

                add(
                    shapelessRecipe(
                        transBrick,
                        listOf(
                            ing(ModItems.LUMINOUS_POWDER),
                            ing(stainedBrick)
                        )
                    )
                )

                add(
                    shapelessRecipe(
                        grassSeeds,
                        listOf(
                            ing(ModItemTagsProvider.GRASS_SEEDS),
                            ing(dyeTag)
                        )
                    )
                )

            }
        }
    }

    private fun populatedNamedRecipes() {

        val spectreIngotRecipe = shapedRecipe(
            ModItems.SPECTRE_INGOT,
            "L,G,E",
            mapOf(
                'L' to ing(Tags.Items.GEMS_LAPIS),
                'G' to ing(Tags.Items.INGOTS_GOLD),
                'E' to ing(ModItems.ECTOPLASM)
            )
        )

        recipesWithNames[spectreIngotRecipe] = "spectre_ingot_single"

    }

}