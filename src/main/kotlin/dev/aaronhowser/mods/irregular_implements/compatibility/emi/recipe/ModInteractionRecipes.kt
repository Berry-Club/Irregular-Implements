package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin.Companion.asEmiIngredient
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getDyeName
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.ItemAbilities

object ModInteractionRecipes {

	fun getInteractionRecipes(): List<EmiWorldInteractionRecipe> {
		return grassSeeds() + slime() + otherRecipes()
	}

	private fun grassSeeds(): List<EmiWorldInteractionRecipe> {
		val seeds = GrassSeedItem.getAllSeeds()

		return seeds.map { deferred ->
			val seedItem = deferred.get()

			val colorString = seedItem.dyeColor?.getDyeName()

			val id = if (colorString == null) {
				OtherUtil.modResource("/interaction/grass")
			} else {
				OtherUtil.modResource("/interaction/grass/$colorString")
			}

			EmiWorldInteractionRecipe
				.builder()
				.leftInput(ItemTags.DIRT.asEmiIngredient())
				.rightInput(seedItem.asEmiIngredient(), false)
				.output(EmiStack.of(seedItem.resultBlock))
				.id(id)
				.build()
		}
	}

	private fun slimeStackWithLore(compressionLevel: Int): ItemStack {
		val stack = ModBlocks.COMPRESSED_SLIME_BLOCK.asItem().defaultInstance

		val component = ModTooltipLang.COMPRESSED_SLIME_AMOUNT
			.toGrayComponent(compressionLevel)
			.withStyle {
				it.withUnderlined(true).withItalic(false)
			}

		stack.set(DataComponents.LORE, ItemLore.EMPTY.withLineAdded(component))

		return stack
	}

	private fun slime(): List<EmiWorldInteractionRecipe> {
		val recipes: MutableList<EmiWorldInteractionRecipe> = mutableListOf()

		val slimeBlock = Items.SLIME_BLOCK.defaultInstance
		val compressedSlimeOne = slimeStackWithLore(1)
		val compressedSlimeTwo = slimeStackWithLore(2)
		val compressedSlimeThree = slimeStackWithLore(3)

		val shovels = BuiltInRegistries.ITEM
			.filter { it.defaultInstance.canPerformAction(ItemAbilities.SHOVEL_FLATTEN) }

		val shovelsEmiIngredient = EmiIngredient.of(Ingredient.of(*shovels.toTypedArray()))

		for (emiStack in shovelsEmiIngredient.emiStacks) {
			val damagedStack = emiStack.itemStack.copy()
			damagedStack.damageValue = 1
			emiStack.setRemainder(EmiStack.of(damagedStack))
		}


		val recipeOne = EmiWorldInteractionRecipe
			.builder()
			.leftInput(EmiIngredient.of(Ingredient.of(slimeBlock)))
			.rightInput(shovelsEmiIngredient, true)
			.output(EmiStack.of(compressedSlimeOne))
			.id(OtherUtil.modResource("/interaction/slime/slime_to_compressed_one"))
			.build()

		val recipeTwo = EmiWorldInteractionRecipe
			.builder()
			.leftInput(EmiIngredient.of(Ingredient.of(compressedSlimeOne)))
			.rightInput(shovelsEmiIngredient, true)
			.output(EmiStack.of(compressedSlimeTwo))
			.id(OtherUtil.modResource("/interaction/slime/compressed_one_to_compressed_two"))
			.build()

		val recipeThree = EmiWorldInteractionRecipe
			.builder()
			.leftInput(EmiIngredient.of(Ingredient.of(compressedSlimeTwo)))
			.rightInput(shovelsEmiIngredient, true)
			.output(EmiStack.of(compressedSlimeThree))
			.id(OtherUtil.modResource("/interaction/slime/compressed_two_to_compressed_three"))
			.build()

		val recipeFour = EmiWorldInteractionRecipe
			.builder()
			.leftInput(EmiIngredient.of(Ingredient.of(compressedSlimeThree)))
			.rightInput(shovelsEmiIngredient, true)
			.output(EmiStack.of(slimeBlock))
			.id(OtherUtil.modResource("/interaction/slime/compressed_three_to_slime"))
			.build()

		recipes.add(recipeOne)
		recipes.add(recipeTwo)
		recipes.add(recipeThree)
		recipes.add(recipeFour)

		return recipes
	}

	private fun otherRecipes(): List<EmiWorldInteractionRecipe> {
		val recipes: MutableList<EmiWorldInteractionRecipe> = mutableListOf()

		val axes = BuiltInRegistries.ITEM
			.filter { it.defaultInstance.canPerformAction(ItemAbilities.AXE_STRIP) }

		val axesEmiIngredient = EmiIngredient.of(Ingredient.of(*axes.toTypedArray()))

		for (emiStack in axesEmiIngredient.emiStacks) {
			val damagedStack = emiStack.itemStack.copy()
			damagedStack.damageValue = 1
			emiStack.setRemainder(EmiStack.of(damagedStack))
		}

		val stripRecipe = EmiWorldInteractionRecipe
			.builder()
			.leftInput(ModBlocks.SPECTRE_LOG.asEmiIngredient())
			.rightInput(axesEmiIngredient, true)
			.output(EmiStack.of(ModBlocks.STRIPPED_SPECTRE_LOG.asItem()))
			.id(OtherUtil.modResource("/interaction/spectre_log_stripping"))
			.build()

		recipes.add(stripRecipe)

		val convertsToSpectreSapling = BuiltInRegistries.ITEM
			.filter { it is BlockItem && it.block.defaultBlockState().`is`(ModBlockTagsProvider.CONVERTS_TO_SPECTRE_SAPLING) }

		if (convertsToSpectreSapling.isNotEmpty()) {
			val saplingsEmiIngredient = EmiIngredient.of(Ingredient.of(*convertsToSpectreSapling.toTypedArray()))
			val ectoplasmIngredient = ModItems.ECTOPLASM.asEmiIngredient()

			val spectreSaplingRecipe = EmiWorldInteractionRecipe
				.builder()
				.leftInput(saplingsEmiIngredient)
				.rightInput(ectoplasmIngredient, false)
				.output(EmiStack.of(ModBlocks.SPECTRE_SAPLING.asItem()))
				.id(OtherUtil.modResource("/interaction/spectre_sapling_conversion"))
				.build()

			recipes.add(spectreSaplingRecipe)
		}

		return recipes
	}

}