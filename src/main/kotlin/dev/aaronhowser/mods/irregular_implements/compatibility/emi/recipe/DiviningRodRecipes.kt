package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin.Companion.emiIngredient
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.recipe.EmiCraftingRecipe
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Items
import net.neoforged.neoforge.common.Tags

object DiviningRodRecipes {

	fun getRecipes(): MutableList<EmiCraftingRecipe> {

		val allOreBlockTags = DiviningRodItem.getAllOreTags() - Tags.Blocks.ORES
		val allOreItemTags = allOreBlockTags.map { TagKey.create(Registries.ITEM, it.location) }

		val stickIngredient = Items.STICK.emiIngredient
		val eyeIngredient = Items.SPIDER_EYE.emiIngredient
		val emptyIngredient = Items.AIR.emiIngredient

		val recipes = mutableListOf<EmiCraftingRecipe>()

		for (oreTag in allOreItemTags) {
			val oreIngredient = oreTag.emiIngredient

			val shapedRecipe = EmiCraftingRecipe(
				listOf(
					oreIngredient, stickIngredient, oreIngredient,
					stickIngredient, eyeIngredient, stickIngredient,
					stickIngredient, emptyIngredient, stickIngredient
				),
				EmiStack.of(DiviningRodItem.getRodForItemTag(oreTag)),
				OtherUtil.modResource("/divining_rod/${oreTag.location.toString().replace(':', '/')}")
			)


			recipes.add(shapedRecipe)
		}

		return recipes
	}

}