package dev.aaronhowser.mods.irregular_implements.datagen.recipe

import dev.aaronhowser.mods.aaron.datagen.AaronRecipeProvider
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.asIngredient
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.getAsStack
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.withComponent
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.*
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.SpecialRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.Tags
import vazkii.patchouli.api.PatchouliAPI
import java.util.concurrent.CompletableFuture

class ModRecipeProvider(
	output: PackOutput,
	lookupProvider: CompletableFuture<HolderLookup.Provider>
) : AaronRecipeProvider(output, lookupProvider) {

	override fun buildRecipes(recipeOutput: RecipeOutput) {
		buildShapedRecipes(recipeOutput)
		buildShapelessRecipes(recipeOutput)
		buildPlatforms(recipeOutput)
		buildColoredRecipes(recipeOutput)
		buildSpecialRecipes(recipeOutput)
		buildNamedRecipes(recipeOutput)
		buildImbuingRecipes(recipeOutput)
	}

	private fun buildShapedRecipes(recipeOutput: RecipeOutput) {
		shapedRecipe(
			ModBlocks.ENERGY_DISTRIBUTOR,
			"IRI,BWB,IRI",
			mapOf(
				'I' to Tags.Items.STORAGE_BLOCKS_IRON.asIngredient(),
				'R' to Items.REPEATER.asIngredient(),
				'W' to ModItems.ENERGIZED_WATER.asIngredient(),
				'B' to Tags.Items.STORAGE_BLOCKS_REDSTONE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ENDER_ENERGY_DISTRIBUTOR,
			" D ,DED, D ",
			mapOf(
				'D' to ModItems.ENDER_DIAMOND.asIngredient(),
				'E' to ModBlocks.ENERGY_DISTRIBUTOR.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.ENDER_DIAMOND,
			" P ,PDP, P ",
			mapOf(
				'P' to Tags.Items.ENDER_PEARLS.asIngredient(),
				'D' to Tags.Items.GEMS_DIAMOND.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.LAPIS_LAMP,
			" A ,ALA, A ",
			mapOf(
				'A' to Tags.Items.GEMS_LAPIS.asIngredient(),
				'L' to Items.REDSTONE_LAMP.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.QUARTZ_LAMP,
			" A ,ALA, A ",
			mapOf(
				'A' to Tags.Items.GEMS_QUARTZ.asIngredient(),
				'L' to Items.REDSTONE_LAMP.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.FERTILIZED_DIRT,
			2,
			"FBF,BDB,FBF",
			mapOf(
				'F' to Items.ROTTEN_FLESH.asIngredient(),
				'B' to Items.BONE_MEAL.asIngredient(),
				'D' to Items.DIRT.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.PLAYER_INTERFACE,
			"OEO,OSO,OPO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'E' to Items.ENDER_CHEST.asIngredient(),
				'S' to Items.NETHER_STAR.asIngredient(),
				'P' to ModItems.STABLE_ENDER_PEARL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.LAPIS_GLASS,
			"GGG,GLG,GGG",
			mapOf(
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient(),
				'L' to Tags.Items.STORAGE_BLOCKS_LAPIS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ONLINE_DETECTOR,
			"SRS,RLR,SRS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'R' to Items.REPEATER.asIngredient(),
				'L' to Tags.Items.GEMS_LAPIS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.CHAT_DETECTOR,
			"SRS,RDR,SRS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'R' to Items.REPEATER.asIngredient(),
				'D' to Tags.Items.DYES_RED.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ENDER_BRIDGE,
			"EEE,ERP,EEE",
			mapOf(
				'E' to Tags.Items.END_STONES.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'P' to ModItems.STABLE_ENDER_PEARL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.PRISMARINE_ENDER_BRIDGE,
			"SCS,CEC,SCS",
			mapOf(
				'S' to Items.PRISMARINE_SHARD.asIngredient(),
				'C' to Items.PRISMARINE_CRYSTALS.asIngredient(),
				'E' to ModBlocks.ENDER_BRIDGE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ENDER_ANCHOR,
			"OOO,OEO,OOO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'E' to ModItems.STABLE_ENDER_PEARL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.IMBUING_STATION,
			" W ,VTV,LEL",
			mapOf(
				'W' to Items.WATER_BUCKET.asIngredient(),
				'V' to Items.VINE.asIngredient(),
				'T' to ItemTags.TERRACOTTA.asIngredient(),
				'L' to Items.LILY_PAD.asIngredient(),
				'E' to Tags.Items.GEMS_EMERALD.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ANALOG_EMITTER,
			"TIR,III,RIT",
			mapOf(
				'T' to Items.REDSTONE_TORCH.asIngredient(),
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ENDER_MAILBOX,
			"EHE,III, F ",
			mapOf(
				'E' to Tags.Items.ENDER_PEARLS.asIngredient(),
				'H' to Items.HOPPER.asIngredient(),
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'F' to Tags.Items.FENCES_WOODEN.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ENTITY_DETECTOR,
			"STS,EPE,STS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'T' to Items.REDSTONE_TORCH.asIngredient(),
				'E' to Tags.Items.ENDER_PEARLS.asIngredient(),
				'P' to Items.STONE_PRESSURE_PLATE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.QUARTZ_GLASS,
			"GGG,GQG,GGG",
			mapOf(
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient(),
				'Q' to Items.QUARTZ_BLOCK.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.POTION_VAPORIZER,
			"STS,ICI,SFS",
			mapOf(
				'S' to Tags.Items.COBBLESTONES_NORMAL.asIngredient(),
				'T' to Items.IRON_TRAPDOOR.asIngredient(),
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'C' to Items.CAULDRON.asIngredient(),
				'F' to Items.FURNACE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.CONTACT_BUTTON,
			"SIS,SBS,SSS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'I' to Items.IRON_BARS.asIngredient(),
				'B' to Items.STONE_BUTTON.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.CONTACT_LEVER,
			"SIS,SLS,SSS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'I' to Items.IRON_BARS.asIngredient(),
				'L' to Items.LEVER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.RAIN_SHIELD,
			" F , B ,NNN",
			mapOf(
				'F' to Items.FLINT.asIngredient(),
				'B' to Tags.Items.RODS_BLAZE.asIngredient(),
				'N' to Tags.Items.NETHERRACKS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BLOCK_BREAKER,
			"CPC,CTC,CCC",
			mapOf(
				'C' to Tags.Items.COBBLESTONES_NORMAL.asIngredient(),
				'P' to Items.IRON_PICKAXE.asIngredient(),
				'T' to Items.REDSTONE_TORCH.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SUPER_LUBRICANT_ICE,
			"S,I,B",
			mapOf(
				'S' to Tags.Items.SLIME_BALLS.asIngredient(),
				'I' to Items.ICE.asIngredient(),
				'B' to Items.WATER_BUCKET.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.REDSTONE_OBSERVER,
			"RQR,QEQ,RQR",
			mapOf(
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'Q' to Tags.Items.GEMS_QUARTZ.asIngredient(),
				'E' to Items.ENDER_EYE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BIOME_RADAR,
			"III,GBG,III",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient(),
				'B' to ModItems.BIOME_SENSOR.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.IRON_DROPPER,
			"III,I I,IDI",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'D' to Tags.Items.DUSTS_REDSTONE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BLOCK_OF_STICKS,
			16,
			"SSS,S S,SSS",
			mapOf(
				'S' to Items.STICK.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.RETURNING_BLOCK_OF_STICKS,
			8,
			"SSS,SES,SSS",
			mapOf(
				'S' to ModBlocks.BLOCK_OF_STICKS.asIngredient(),
				'E' to Items.ENDER_PEARL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.INVENTORY_REROUTER,
			"SBS,BHB,SBS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'B' to Items.IRON_BARS.asIngredient(),
				'H' to Items.HOPPER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SLIME_CUBE,
			" S ,SWS, S ",
			mapOf(
				'S' to Tags.Items.SLIME_BALLS.asIngredient(),
				'W' to Items.NETHER_STAR.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.NOTIFICATION_INTERFACE,
			"SPS,PQP,SPS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'P' to Items.PAPER.asIngredient(),
				'Q' to Tags.Items.GEMS_QUARTZ.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.INVENTORY_TESTER,
			2,
			" S ,SRS, C ",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'R' to Items.COMPARATOR.asIngredient(),
				'C' to Tags.Items.CHESTS_WOODEN.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SUPER_LUBRICANT_STONE,
			8,
			"SSS,SLS,SSS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'L' to ModItems.SUPER_LUBRICANT_TINCTURE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.GLOBAL_CHAT_DETECTOR,
			" T ,RCR, R ",
			mapOf(
				'T' to Items.REDSTONE_TORCH.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'C' to ModBlocks.CHAT_DETECTOR.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.TRIGGER_GLASS,
			4,
			" L ,GRG, L ",
			mapOf(
				'L' to ModBlocks.LAPIS_GLASS.asIngredient(),
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BLOCK_DESTABILIZER,
			"ORO,SDS,ORO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'S' to Tags.Items.SANDS.asIngredient(),
				'D' to Tags.Items.GEMS_DIAMOND.asIngredient()
			)
		).save(recipeOutput)

//		shapedRecipe(
//			ModBlocks.SOUND_BOX,
//			"PPP,PLP,PPP",
//			mapOf(
//				'P' to ItemTags.PLANKS.asIngredient(),
//				'L' to Tags.Items.GEMS_LAPIS.asIngredient()
//			)
//		),
//		shapedRecipe(
//			ModBlocks.SOUND_DAMPENER,
//			"PWP,WSW,PWP",
//			mapOf(
//				'P' to ItemTags.PLANKS.asIngredient(),
//				'W' to ItemTags.WOOL.asIngredient(),
//				'S' to ModItems.PORTABLE_SOUND_DAMPENER.asIngredient()
//			)
//		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SIDED_BLOCK_OF_REDSTONE,
			"GGR,GGR,GGR",
			mapOf(
				'G' to Tags.Items.GUNPOWDERS.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SPECTRE_LENS,
			"SES,DGD,SES",
			mapOf(
				'S' to ModItems.SPECTRE_INGOT.asIngredient(),
				'E' to Tags.Items.GEMS_EMERALD.asIngredient(),
				'D' to Tags.Items.GEMS_DIAMOND.asIngredient(),
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SPECTRE_ENERGY_INJECTOR,
			"OLO,SBS,OSO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'L' to ModBlocks.SPECTRE_LENS.asIngredient(),
				'S' to ModItems.SPECTRE_STRING.asIngredient(),
				'B' to Items.BEACON.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.PROCESSING_PLATE,
			"B B,E C,B B",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'E' to ModBlocks.EXTRACTION_PLATE.asIngredient(),
				'C' to ModBlocks.COLLECTION_PLATE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SPECTRE_COIL_BASIC,
			"OSO,OIG,OSO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'S' to ModItems.SPECTRE_STRING.asIngredient(),
				'I' to ModItems.SPECTRE_INGOT.asIngredient(),
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SPECTRE_COIL_REDSTONE,
			"BSR,SCS,RSB",
			mapOf(
				'B' to Tags.Items.STORAGE_BLOCKS_REDSTONE.asIngredient(),
				'S' to ModItems.SPECTRE_STRING.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'C' to ModBlocks.SPECTRE_COIL_BASIC.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SPECTRE_COIL_ENDER,
			"PSE,SCS,ESP",
			mapOf(
				'P' to ModItems.STABLE_ENDER_PEARL.asIngredient(),
				'S' to ModItems.SPECTRE_STRING.asIngredient(),
				'E' to Tags.Items.ENDER_PEARLS.asIngredient(),
				'C' to ModBlocks.SPECTRE_COIL_REDSTONE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ADVANCED_REDSTONE_REPEATER,
			"TRT,ISI",
			mapOf(
				'T' to Items.REDSTONE_TORCH.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'S' to Tags.Items.STONES.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.ADVANCED_REDSTONE_TORCH,
			" R ,RIR, S ",
			mapOf(
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'S' to Items.STICK.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ITEM_COLLECTOR,
			" P , H ,OOO",
			mapOf(
				'P' to Tags.Items.ENDER_PEARLS.asIngredient(),
				'H' to Items.HOPPER.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ADVANCED_ITEM_COLLECTOR,
			" T ,GCG",
			mapOf(
				'T' to Items.REDSTONE_TORCH.asIngredient(),
				'G' to Tags.Items.DUSTS_GLOWSTONE.asIngredient(),
				'C' to ModBlocks.ITEM_COLLECTOR.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BIOME_COBBLESTONE,
			16,
			"CCC,CBC,CCC",
			mapOf(
				'C' to Tags.Items.COBBLESTONES_NORMAL.asIngredient(),
				'B' to ModItems.BIOME_CRYSTAL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BIOME_STONE,
			16,
			"SSS,SBS,SSS",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'B' to ModItems.BIOME_CRYSTAL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BIOME_STONE_BRICKS,
			16,
			"SSS,SBS,SSS",
			mapOf(
				'S' to Items.STONE_BRICKS.asIngredient(),
				'B' to ModItems.BIOME_CRYSTAL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BIOME_STONE_BRICKS_CRACKED,
			16,
			"SSS,SBS,SSS",
			mapOf(
				'S' to Items.CRACKED_STONE_BRICKS.asIngredient(),
				'B' to ModItems.BIOME_CRYSTAL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BIOME_STONE_BRICKS_CHISELED,
			16,
			"SSS,SBS,SSS",
			mapOf(
				'S' to Items.CHISELED_STONE_BRICKS.asIngredient(),
				'B' to ModItems.BIOME_CRYSTAL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BIOME_GLASS,
			16,
			"GGG,GBG,GGG",
			mapOf(
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient(),
				'B' to ModItems.BIOME_CRYSTAL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.RAINBOW_LAMP,
			" G ,RLB",
			mapOf(
				'G' to Tags.Items.DYES_GREEN.asIngredient(),
				'R' to Tags.Items.DYES_RED.asIngredient(),
				'L' to Items.REDSTONE_LAMP.asIngredient(),
				'B' to Tags.Items.DYES_BLUE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BASIC_REDSTONE_INTERFACE,
			"IRI,RPR,IRI",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'P' to ModItems.STABLE_ENDER_PEARL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ADVANCED_REDSTONE_INTERFACE,
			"ROR,OIO,ROR",
			mapOf(
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'I' to ModBlocks.BASIC_REDSTONE_INTERFACE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.REDIRECTOR_PLATE,
			2,
			"BGB,G G,BGB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'G' to Tags.Items.DYES_GREEN.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.FILTERED_REDIRECTOR_PLATE,
			2,
			"BGB,Y L,BGB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'G' to Tags.Items.DYES_GREEN.asIngredient(),
				'Y' to Tags.Items.DYES_YELLOW.asIngredient(),
				'L' to Tags.Items.DYES_BLUE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.REDSTONE_PLATE,
			2,
			"BGB,R R,BGB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'G' to Tags.Items.DYES_GREEN.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.CORRECTOR_PLATE,
			2,
			"BIB,I I,BIB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'I' to Items.IRON_BARS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ITEM_SEALER_PLATE,
			2,
			"BLB,L L,BLB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'L' to Tags.Items.GEMS_LAPIS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ITEM_REJUVENATOR_PLATE,
			2,
			"BMB,M M,BMB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'M' to Tags.Items.DYES_MAGENTA.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.ACCELERATOR_PLATE,
			2,
			"BRB,R R,BRB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'R' to Tags.Items.DYES_RED.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE,
			2,
			"BRB,   ,BRB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'R' to Tags.Items.DYES_RED.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BOUNCY_PLATE,
			2,
			"B B, S ,B B",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'S' to Tags.Items.SLIME_BALLS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.COLLECTION_PLATE,
			2,
			"B B, H ,B B",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'H' to Items.HOPPER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.EXTRACTION_PLATE,
			2,
			"BIB,IHI,BIB",
			mapOf(
				'B' to ModItems.PLATE_BASE.asIngredient(),
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'H' to Items.HOPPER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.STABLE_ENDER_PEARL,
			"OLO,LPL,OLO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'L' to Tags.Items.GEMS_LAPIS.asIngredient(),
				'P' to Tags.Items.ENDER_PEARLS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.LOCATION_FILTER,
			" M ,MPM, M ",
			mapOf(
				'M' to Tags.Items.DYES_MAGENTA.asIngredient(),
				'P' to Items.PAPER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.LESSER_MAGIC_BEAN,
			"NNN,NBN,NNN",
			mapOf(
				'N' to Tags.Items.NUGGETS_GOLD.asIngredient(),
				'B' to ModItems.BEAN.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.REDSTONE_TOOL,
			"R,S,S",
			mapOf(
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'S' to Items.STICK.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.OBSIDIAN_SKULL,
			"OBO,NWN,OBO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'B' to Tags.Items.RODS_BLAZE.asIngredient(),
				'N' to Tags.Items.NUGGETS_GOLD.asIngredient(),
				'W' to Items.WITHER_SKELETON_SKULL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.ENDER_LETTER,
			"PEP, P ",
			mapOf(
				'P' to Items.PAPER.asIngredient(),
				'E' to Tags.Items.ENDER_PEARLS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.ENTITY_FILTER,
			" L ,LPL, L ",
			mapOf(
				'L' to Tags.Items.GEMS_LAPIS.asIngredient(),
				'P' to Items.PAPER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_INGOT,
			9,
			"ELE,EGE,EEE",
			mapOf(
				'E' to ModItems.ECTOPLASM.asIngredient(),
				'L' to Tags.Items.STORAGE_BLOCKS_LAPIS.asIngredient(),
				'G' to Tags.Items.STORAGE_BLOCKS_GOLD.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.BIOME_SENSOR,
			"III,RBI,IRI",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'B' to ModItems.BIOME_CRYSTAL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.PLATE_BASE,
			8,
			"I I, B ,I I",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'B' to Items.IRON_BARS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_STRING,
			4,
			"ESE,SDS,ESE",
			mapOf(
				'E' to ModItems.ECTOPLASM.asIngredient(),
				'S' to Tags.Items.STRINGS.asIngredient(),
				'D' to Tags.Items.GEMS_DIAMOND.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.ITEM_FILTER,
			" Y ,YPY, Y ",
			mapOf(
				'Y' to Tags.Items.DYES_YELLOW.asIngredient(),
				'P' to Items.PAPER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.REDSTONE_ACTIVATOR,
			"IRI,ITI,III",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'R' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'T' to Items.REDSTONE_TORCH.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.REDSTONE_REMOTE,
			"RRR,OPO,OOO",
			mapOf(
				'R' to ModItems.REDSTONE_ACTIVATOR.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'P' to ModItems.STABLE_ENDER_PEARL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.FLOO_SIGN,
			"PPP,PSP,PPP",
			mapOf(
				'P' to ModItems.FLOO_POWDER.asIngredient(),
				'S' to ItemTags.SIGNS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.EMERALD_COMPASS,
			" E ,ECE, E ",
			mapOf(
				'E' to Tags.Items.GEMS_EMERALD.asIngredient(),
				'C' to Items.COMPASS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.GOLDEN_COMPASS,
			" G ,GCG, G ",
			mapOf(
				'G' to Tags.Items.INGOTS_GOLD.asIngredient(),
				'C' to Items.COMPASS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.FLOO_TOKEN,
			"FFF,FPF,FFF",
			mapOf(
				'F' to ModItems.FLOO_POWDER.asIngredient(),
				'P' to Items.PAPER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.PORTKEY,
			"GEG, D ",
			mapOf(
				'G' to Tags.Items.GUNPOWDERS.asIngredient(),
				'E' to ModItems.STABLE_ENDER_PEARL.asIngredient(),
				'D' to Tags.Items.GEMS_DIAMOND.asIngredient()
			)
		).save(recipeOutput)

//		shapedRecipe(
//			ModItems.SOUND_PATTERN,
//			"S,P,L",
//			mapOf(
//				'S' to Tags.Items.STRINGS.asIngredient(),
//				'P' to Items.PAPER.asIngredient(),
//				'L' to Tags.Items.GEMS_LAPIS.asIngredient()
//			)
//		),
//		shapedRecipe(
//			ModItems.SOUND_RECORDER,
//			"BGG,ILI,III",
//			mapOf(
//				'B' to Items.IRON_BARS.asIngredient(),
//				'G' to Tags.Items.GLASS_BLOCKS.asIngredient(),
//				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
//				'L' to Tags.Items.GEMS_LAPIS.asIngredient()
//			)
//		),
//		shapedRecipe(
//			ModItems.PORTABLE_SOUND_DAMPENER,
//			"ISI,SLS,ISI",
//			mapOf(
//				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
//				'S' to Tags.Items.STRINGS.asIngredient(),
//				'L' to Tags.Items.GEMS_LAPIS.asIngredient()
//			)
//		).save(recipeOutput)

		shapedRecipe(
			ModItems.ESCAPE_ROPE,
			"SGP,GSG,PGS",
			mapOf(
				'S' to Tags.Items.STRINGS.asIngredient(),
				'G' to Tags.Items.INGOTS_GOLD.asIngredient(),
				'P' to Tags.Items.ENDER_PEARLS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.ENDER_BUCKET,
			"I I, P ",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'P' to Tags.Items.ENDER_PEARLS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.REINFORCED_ENDER_BUCKET,
			"OBO, S ",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'B' to ModItems.ENDER_BUCKET.asIngredient(),
				'S' to ModItems.STABLE_ENDER_PEARL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.CHUNK_ANALYZER,
			"B B,IGI,SID",
			mapOf(
				'B' to Items.IRON_BARS.asIngredient(),
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient(),
				'S' to Tags.Items.STONES.asIngredient(),
				'D' to ItemTags.DIRT.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.FLOO_POUCH,
			" L ,LFL,LLL",
			mapOf(
				'L' to Tags.Items.LEATHERS.asIngredient(),
				'F' to ModItems.FLOO_POWDER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_ILLUMINATOR,
			"EGE,GIG,EGE",
			mapOf(
				'E' to ModItems.ECTOPLASM.asIngredient(),
				'G' to Tags.Items.DUSTS_GLOWSTONE.asIngredient(),
				'I' to ModItems.LUMINOUS_POWDER.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_KEY,
			"S  ,SP ,  S",
			mapOf(
				'S' to ModItems.SPECTRE_INGOT.asIngredient(),
				'P' to ModItems.STABLE_ENDER_PEARL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_ANCHOR,
			" I ,IEI,III",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'E' to ModItems.ECTOPLASM.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_SWORD,
			"E,E,O",
			mapOf(
				'E' to ModItems.SPECTRE_INGOT.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_PICKAXE,
			"EEE, O , O ",
			mapOf(
				'E' to ModItems.SPECTRE_INGOT.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_AXE,
			"EE ,EO , O ",
			mapOf(
				'E' to ModItems.SPECTRE_INGOT.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_SHOVEL,
			"E,O,O",
			mapOf(
				'E' to ModItems.SPECTRE_INGOT.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.SHOCK_ABSORBER,
			"WWW,WAW,DDD",
			mapOf(
				'W' to ItemTags.WOOL.asIngredient(),
				'A' to Items.WATER_BUCKET.asIngredient(),
				'D' to ItemTags.DIRT.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.BIOME_CAPSULE,
			"DTE,GQG,OOO",
			mapOf(
				'D' to Tags.Items.STORAGE_BLOCKS_DIAMOND.asIngredient(),
				'E' to Tags.Items.GEMS_EMERALD.asIngredient(),
				'T' to ModItems.TRANSFORMATION_CORE.asIngredient(),
				'Q' to Tags.Items.GEMS_QUARTZ.asIngredient(),
				'G' to Tags.Items.GLASS_BLOCKS.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.BIOME_PAINTER,
			"T,W,O",
			mapOf(
				'T' to ModItems.TRANSFORMATION_CORE.asIngredient(),
				'W' to ItemTags.WOOL.asIngredient(),
				'O' to ModItemTagsProvider.C_RODS_OBSIDIAN.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.DROP_FILTER,
			"LSL,SFS,LSL",
			mapOf(
				'L' to Tags.Items.LEATHERS.asIngredient(),
				'S' to Tags.Items.STRINGS.asIngredient(),
				'F' to Items.FLINT.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.VOID_STONE,
			" O ,OEO, O ",
			mapOf(
				'O' to Tags.Items.STONES.asIngredient(),
				'E' to Tags.Items.ENDER_PEARLS.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.OBSIDIAN_ROD,
			3,
			"O,O",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.PORTABLE_ENDER_BRIDGE,
			"P,S,S",
			mapOf(
				'P' to ModBlocks.PRISMARINE_ENDER_BRIDGE.asIngredient(),
				'S' to ModItemTagsProvider.C_RODS_OBSIDIAN.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.BLOCK_MOVER,
			"S S,O O, O ",
			mapOf(
				'S' to ModItems.STABLE_ENDER_PEARL.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.DIAMOND_BREAKER,
			" I ,IDI, I ",
			mapOf(
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'D' to Items.DIAMOND_PICKAXE.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.AUTO_PLACER,
			"S S,IPI,S S",
			mapOf(
				'S' to Tags.Items.STONES.asIngredient(),
				'I' to Tags.Items.INGOTS_IRON.asIngredient(),
				'P' to Items.PISTON.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.BLOCK_TELEPORTER,
			"OCO,OEO,OOO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'C' to Items.COMPARATOR.asIngredient(),
				'E' to Tags.Items.ENDER_PEARLS.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.BLOCK_REPLACER,
			" GF, RL,R  ",
			mapOf(
				'G' to Tags.Items.NUGGETS_GOLD.asIngredient(),
				'F' to Items.FLINT.asIngredient(),
				'R' to ModItemTagsProvider.C_RODS_OBSIDIAN.asIngredient(),
				'L' to Tags.Items.GEMS_LAPIS.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.MOON_PHASE_DETECTOR,
			"GGG,LQL,SSS",
			mapOf(
				'G' to Tags.Items.GLASS_BLOCKS_COLORLESS.asIngredient(),
				'L' to Tags.Items.GEMS_LAPIS.asIngredient(),
				'Q' to Tags.Items.GEMS_QUARTZ.asIngredient(),
				'S' to ItemTags.WOODEN_SLABS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_HELMET,
			"SSS,S S",
			mapOf(
				'S' to ModItems.SPECTRE_INGOT.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_CHESTPLATE,
			"S S,SSS,SSS",
			mapOf(
				'S' to ModItems.SPECTRE_INGOT.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_LEGGINGS,
			"SSS,S S,S S",
			mapOf(
				'S' to ModItems.SPECTRE_INGOT.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_BOOTS,
			"S S,S S",
			mapOf(
				'S' to ModItems.SPECTRE_INGOT.asIngredient(),
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.DIAPHANOUS_BLOCK.toStack(4).withComponent(ModDataComponents.BLOCK.get(), Blocks.STONE),
			" G ,RLB, Y ",
			mapOf(
				'G' to Items.GREEN_STAINED_GLASS.asIngredient(),
				'R' to Items.RED_STAINED_GLASS.asIngredient(),
				'L' to Tags.Items.GLASS_BLOCKS_COLORLESS.asIngredient(),
				'B' to Items.BLUE_STAINED_GLASS.asIngredient(),
				'Y' to Items.YELLOW_STAINED_GLASS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.EVIL_TEAR,
			"W,G,E",
			mapOf(
				'W' to Items.WITHER_SKELETON_SKULL.asIngredient(),
				'G' to Items.GHAST_TEAR.asIngredient(),
				'E' to Tags.Items.ENDER_PEARLS.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_CHARGER_BASIC,
			"IOS,ONO,SOI",
			mapOf(
				'I' to Items.IRON_BARS.asIngredient(),
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'S' to ModItems.SPECTRE_STRING.asIngredient(),
				'N' to ModItems.SPECTRE_INGOT.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_CHARGER_REDSTONE,
			"BSD,SCS,DSB",
			mapOf(
				'B' to Tags.Items.STORAGE_BLOCKS_REDSTONE.asIngredient(),
				'S' to ModItems.SPECTRE_STRING.asIngredient(),
				'D' to Tags.Items.DUSTS_REDSTONE.asIngredient(),
				'C' to ModItems.SPECTRE_CHARGER_BASIC.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModItems.SPECTRE_CHARGER_ENDER,
			"PSE,SCS,ESP",
			mapOf(
				'P' to ModItems.STABLE_ENDER_PEARL.asIngredient(),
				'S' to ModItems.SPECTRE_STRING.asIngredient(),
				'E' to Tags.Items.ENDER_PEARLS.asIngredient(),
				'C' to ModItems.SPECTRE_CHARGER_REDSTONE.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			ModBlocks.IGNITER,
			"NCC,NFC,NCC",
			mapOf(
				'N' to Tags.Items.NETHERRACKS.asIngredient(),
				'C' to Tags.Items.COBBLESTONES_NORMAL.asIngredient(),
				'F' to Items.FLINT_AND_STEEL.asIngredient()
			)
		).save(recipeOutput)

		shapedRecipe(
			PatchouliAPI.get().getBookStack(OtherUtil.modResource("guide")),
			"BBB, B ,BBB",
			mapOf(
				'B' to Items.BOOK.asIngredient()
			)
		).save(recipeOutput)
	}

	private fun buildShapelessRecipes(recipeOutput: RecipeOutput) {
		shapelessRecipe(
			ModItems.SUPER_LUBRICANT_TINCTURE,
			listOf(
				Tags.Items.SEEDS.asIngredient(),
				Potions.WATER.getAsStack().asIngredient(),
				ModItems.BEAN.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModBlocks.SPECTRE_PLANKS,
			4,
			listOf(ModBlocks.SPECTRE_LOG.asIngredient())
		).save(recipeOutput)

		shapelessRecipe(
			ModBlocks.SPECTRE_WOOD,
			4,
			listOf(
				ModBlocks.SPECTRE_LOG.asIngredient(),
				ModBlocks.SPECTRE_LOG.asIngredient(),
				ModBlocks.SPECTRE_LOG.asIngredient(),
				ModBlocks.SPECTRE_LOG.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.BEAN_STEW,
			listOf(
				ModItems.BEAN.asIngredient(),
				ModItems.BEAN.asIngredient(),
				ModItems.BEAN.asIngredient(),
				Tags.Items.CROPS_WHEAT.asIngredient(),
				Items.BOWL.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.LUMINOUS_POWDER,
			4,
			listOf(
				Tags.Items.DUSTS_GLOWSTONE.asIngredient(),
				Tags.Items.GLASS_BLOCKS.asIngredient(),
				ModBlocks.GLOWING_MUSHROOM.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.FLOO_POWDER,
			16,
			listOf(
				Tags.Items.ENDER_PEARLS.asIngredient(),
				Tags.Items.DUSTS_REDSTONE.asIngredient(),
				Tags.Items.GUNPOWDERS.asIngredient(),
				ModItems.BEAN.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.BLACKOUT_POWDER,
			4,
			listOf(
				Tags.Items.DYES_BLACK.asIngredient(),
				Tags.Items.OBSIDIANS.asIngredient(),
				Tags.Items.DYES_BLUE.asIngredient(),
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.BLAZE_AND_STEEL,
			listOf(
				Items.BLAZE_POWDER.asIngredient(),
				Tags.Items.INGOTS_IRON.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.PLAYER_FILTER,
			listOf(
				Items.INK_SAC.asIngredient(),
				Items.PAPER.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.GRASS_SEEDS,
			listOf(
				Items.GRASS_BLOCK.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.VOIDING_DROP_FILTER,
			listOf(
				ModItems.DROP_FILTER.asIngredient(),
				ModItems.VOID_STONE.asIngredient()
			)
		).save(recipeOutput)

		shapelessRecipe(
			ModItems.TRANSFORMATION_CORE,
			listOf(
				Tags.Items.DYES_RED.asIngredient(),
				Tags.Items.DYES_ORANGE.asIngredient(),
				Tags.Items.DYES_YELLOW.asIngredient(),
				Tags.Items.DYES_GREEN.asIngredient(),
				Tags.Items.DYES_CYAN.asIngredient(),
				Tags.Items.DYES_PURPLE.asIngredient(),
				Tags.Items.DYES_GRAY.asIngredient(),
				Tags.Items.DYES_LIME.asIngredient(),
				Tags.Items.DYES_MAGENTA.asIngredient(),
			)
		).save(recipeOutput)
	}

	private fun buildPlatforms(recipeOutput: RecipeOutput) {
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

		for ((platform, ingredient) in platformIngredientMap) {
			shapedRecipe(
				platform.get(),
				6,
				"PPP, E ",
				mapOf(
					'P' to ingredient.asIngredient(),
					'E' to Tags.Items.ENDER_PEARLS.asIngredient()
				)
			).save(recipeOutput)
		}

		shapedRecipe(
			ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM.get(),
			"P,L,S",
			mapOf(
				'P' to Items.PAPER.asIngredient(),
				'L' to ModBlocks.SUPER_LUBRICANT_PLATFORM.asIngredient(),
				'S' to Tags.Items.STRINGS.asIngredient()
			)
		).save(recipeOutput)
	}

	private fun buildColoredRecipes(recipeOutput: RecipeOutput) {
		val dyeTags: Map<DyeColor, TagKey<Item>> = mapOf(
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

		val recipes = buildList {
			for (color in DyeColor.entries) {
				val dyeTag = dyeTags[color]
				if (dyeTag == null) {
					IrregularImplements.LOGGER.warn("No dye tag for color $color")
					continue
				}

				val luminous = ModBlocks.getLuminousBlock(color)?.get()
				val transLuminous = ModBlocks.getLuminousBlockTranslucent(color)?.get()

				val stainedBrick = ModBlocks.getStainedBrick(color)?.get()
				val transBrick = ModBlocks.getStainedBrickLuminous(color)?.get()

				val grassSeeds = GrassSeedItem.getFromColor(color)

				if (luminous != null) {
					add(
						shapedRecipe(
							luminous,
							"LD,LL",
							mapOf(
								'L' to ModItems.LUMINOUS_POWDER.asIngredient(),
								'D' to dyeTag.asIngredient()
							)
						)
					)

					if (transLuminous != null) {
						add(
							shapedRecipe(
								transLuminous,
								" P ,PLP, P ",
								mapOf(
									'P' to Tags.Items.GLASS_PANES_COLORLESS.asIngredient(),
									'L' to luminous.asIngredient()
								)
							)
						)
					}
				}

				if (stainedBrick != null) {
					add(
						shapedRecipe(
							stainedBrick,
							8,
							"BBB,BDB,BBB",
							mapOf(
								'B' to Items.BRICKS.asIngredient(),
								'D' to dyeTag.asIngredient()
							)
						)
					)

					if (transBrick != null) {
						add(
							shapelessRecipe(
								transBrick,
								listOf(
									ModItems.LUMINOUS_POWDER.asIngredient(),
									stainedBrick.asIngredient()
								)
							)
						)
					}
				}

				if (grassSeeds != null) {
					add(
						shapelessRecipe(
							grassSeeds,
							listOf(
								ModItemTagsProvider.GRASS_SEEDS.asIngredient(),
								dyeTag.asIngredient()
							)
						)
					)
				}
			}
		}

		for (recipe in recipes) {
			recipe.save(recipeOutput)
		}
	}

	private fun buildNamedRecipes(recipeOutput: RecipeOutput) {
		shapedRecipe(
			ModItems.SPECTRE_INGOT,
			"L,G,E",
			mapOf(
				'L' to Tags.Items.GEMS_LAPIS.asIngredient(),
				'G' to Tags.Items.INGOTS_GOLD.asIngredient(),
				'E' to ModItems.ECTOPLASM.asIngredient()
			)
		).save(recipeOutput, OtherUtil.modResource("spectre_ingot_single"))

		shapedRecipe(
			DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES),
			"CSD,ILE,GPR",
			mapOf(
				'S' to Items.STICK.asIngredient(),
				'L' to Tags.Items.SLIME_BALLS.asIngredient(),
				'C' to DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_COAL).asIngredient(),
				'D' to DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_DIAMOND).asIngredient(),
				'I' to DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_IRON).asIngredient(),
				'E' to DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_EMERALD).asIngredient(),
				'G' to DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_GOLD).asIngredient(),
				'P' to DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_LAPIS).asIngredient(),
				'R' to DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_REDSTONE).asIngredient()
			)
		).save(recipeOutput, OtherUtil.modResource("universal_ore_divining_rod"))

		shapedRecipe(
			WeatherEggItem.Weather.SUNNY.getStack().copyWithCount(2),
			"OFO,SCS,OFO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'F' to Items.FEATHER.asIngredient(),
				'S' to Items.SUNFLOWER.asIngredient(),
				'C' to Items.FIRE_CHARGE.asIngredient(),
			)
		).save(recipeOutput, OtherUtil.modResource("weather_egg_sunny"))

		shapedRecipe(
			WeatherEggItem.Weather.RAINY.getStack().copyWithCount(2),
			"OWO,LCL,OWO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'W' to Potions.WATER.getAsStack().asIngredient(),
				'L' to Tags.Items.GEMS_LAPIS.asIngredient(),
				'C' to Items.FIRE_CHARGE.asIngredient(),
			)
		).save(recipeOutput, OtherUtil.modResource("weather_egg_rainy"))

		shapedRecipe(
			WeatherEggItem.Weather.STORMY.getStack().copyWithCount(2),
			"OSO,LCL,OSO",
			mapOf(
				'O' to Tags.Items.OBSIDIANS.asIngredient(),
				'S' to Items.SUGAR.asIngredient(),
				'L' to Tags.Items.GEMS_LAPIS.asIngredient(),
				'C' to Items.FIRE_CHARGE.asIngredient(),
			)
		).save(recipeOutput, OtherUtil.modResource("weather_egg_stormy"))

	}

	private fun buildImbuingRecipes(recipeOutput: RecipeOutput) {

		val mossyCobbleRecipe = ImbuingRecipeBuilder(
			outerIngredients = listOf(
				Potions.WATER.getAsStack().asIngredient(),
				Items.VINE.asIngredient(),
				Items.BONE_MEAL.asIngredient()
			),
			centerIngredient = Items.COBBLESTONE.asIngredient(),
			outputStack = Items.MOSSY_COBBLESTONE.defaultInstance
		)

		mossyCobbleRecipe.save(recipeOutput, "imbuing/mossy_cobblestone")

		val energizedWater = ImbuingRecipeBuilder(
			outerIngredients = listOf(
				Tags.Items.GUNPOWDERS.asIngredient(),
				Tags.Items.DUSTS_REDSTONE.asIngredient(),
				Tags.Items.DUSTS_GLOWSTONE.asIngredient()
			),
			centerIngredient = Potions.WATER.getAsStack().asIngredient(),
			outputStack = ModItems.ENERGIZED_WATER.toStack()
		)

		energizedWater.save(recipeOutput, "imbuing/energized_water")

		val fireImbue = ImbuingRecipeBuilder(
			outerIngredients = listOf(
				ItemTags.COALS.asIngredient(),
				Items.FLINT.asIngredient(),
				Items.BLAZE_POWDER.asIngredient()
			),
			centerIngredient = Potions.WATER.getAsStack().asIngredient(),
			outputStack = ModItems.FIRE_IMBUE.toStack()
		)

		fireImbue.save(recipeOutput, "imbuing/fire_imbue")

		val poisonImbue = ImbuingRecipeBuilder(
			outerIngredients = listOf(
				Items.SPIDER_EYE.asIngredient(),
				Items.ROTTEN_FLESH.asIngredient(),
				Items.RED_MUSHROOM.asIngredient()
			),
			centerIngredient = Potions.WATER.getAsStack().asIngredient(),
			outputStack = ModItems.POISON_IMBUE.toStack()
		)

		poisonImbue.save(recipeOutput, "imbuing/poison_imbue")

		val experienceImbue = ImbuingRecipeBuilder(
			outerIngredients = listOf(
				ModItems.LESSER_MAGIC_BEAN.asIngredient(),
				Tags.Items.GEMS_LAPIS.asIngredient(),
				Tags.Items.DUSTS_GLOWSTONE.asIngredient()
			),
			centerIngredient = Potions.WATER.getAsStack().asIngredient(),
			outputStack = ModItems.EXPERIENCE_IMBUE.toStack()
		)

		experienceImbue.save(recipeOutput, "imbuing/experience_imbue")

		val witherImbue = ImbuingRecipeBuilder(
			outerIngredients = listOf(
				Items.WITHER_SKELETON_SKULL.asIngredient(),
				Items.NETHER_BRICK.asIngredient(),
				Items.GHAST_TEAR.asIngredient()
			),
			centerIngredient = Potions.WATER.getAsStack().asIngredient(),
			outputStack = ModItems.WITHER_IMBUE.toStack()
		)

		witherImbue.save(recipeOutput, "imbuing/wither_imbue")

		val collapseImbue = ImbuingRecipeBuilder(
			outerIngredients = listOf(
				ModBlocks.SAKANADE_SPORES.asIngredient(),
				Items.VINE.asIngredient(),
				Tags.Items.SLIME_BALLS.asIngredient()
			),
			centerIngredient = Potions.WATER.getAsStack().asIngredient(),
			outputStack = ModItems.COLLAPSE_IMBUE.toStack()
		)

		collapseImbue.save(recipeOutput, "imbuing/collapse_imbue")

		val spectreImbue = ImbuingRecipeBuilder(
			outerIngredients = listOf(
				ModItems.ECTOPLASM.asIngredient(),
				Tags.Items.STORAGE_BLOCKS_LAPIS.asIngredient(),
				Items.OXEYE_DAISY.asIngredient()
			),
			centerIngredient = Potions.WATER.getAsStack().asIngredient(),
			outputStack = ModItems.SPECTRE_IMBUE.toStack()
		)

		spectreImbue.save(recipeOutput, "imbuing/spectre_imbue")
	}

	private fun buildSpecialRecipes(recipeOutput: RecipeOutput) {
		val recipes = mapOf(
			SpecialRecipeBuilder.special(::LubricateBootRecipe) to "lubricate_boot",
			SpecialRecipeBuilder.special(::WashBootRecipe) to "wash_boot",
			SpecialRecipeBuilder.special(::DiviningRodRecipe) to "divining_rod",
			SpecialRecipeBuilder.special(::ApplySpectreAnchorRecipe) to "apply_spectre_anchor",
			SpecialRecipeBuilder.special(::ApplyLuminousPowderRecipe) to "apply_luminous_powder",
			SpecialRecipeBuilder.special(::SetDiaphanousBlockRecipe) to "set_diaphanous_block",
			SpecialRecipeBuilder.special(::InvertDiaphanousBlockRecipe) to "invert_diaphanous_block",
			SpecialRecipeBuilder.special(::CustomCraftingTableRecipe) to "custom_crafting_table",
			SpecialRecipeBuilder.special(::SetPortkeyDisguiseRecipe) to "set_portkey_disguise",
			SpecialRecipeBuilder.special(::SetEmeraldCompassPlayerRecipe) to "set_emerald_compass_player",
			SpecialRecipeBuilder.special(::SetGoldenCompassPositionRecipe) to "set_golden_compass_position",
		)

		for ((recipe, name) in recipes) {
			recipe.save(recipeOutput, OtherUtil.modResource(name))
		}
	}

}