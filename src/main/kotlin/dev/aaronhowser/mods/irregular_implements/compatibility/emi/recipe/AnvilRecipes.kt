package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin.Companion.asEmiIngredient
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.stack.EmiIngredient
import net.minecraft.world.item.Items

object AnvilRecipes {

	fun getRecipes(): List<EmiAnvilRecipe> {
		val obsidianSkullRing = EmiAnvilRecipe(
			ModItems.OBSIDIAN_SKULL.asEmiIngredient(),
			Items.FIRE_CHARGE.asEmiIngredient(),
			ModItems.OBSIDIAN_SKULL_RING.asItem(),
			OtherUtil.modResource("/anvil/obsidian_skull_ring")
		)

		val obsidianWaterWalkingSkullOne = EmiAnvilRecipe(
			ModItems.WATER_WALKING_BOOTS.asEmiIngredient(),
			EmiIngredient.of(
				listOf(
					ModItems.OBSIDIAN_SKULL.asEmiIngredient(),
					ModItems.OBSIDIAN_SKULL_RING.asEmiIngredient()
				)
			),
			ModItems.OBSIDIAN_WATER_WALKING_BOOTS.asItem(),
			OtherUtil.modResource("/anvil/obsidian_water_walking_skull")
		)

		val lavaWaders = EmiAnvilRecipe(
			ModItems.OBSIDIAN_WATER_WALKING_BOOTS.asEmiIngredient(),
			ModItems.LAVA_CHARM.asEmiIngredient(),
			ModItems.LAVA_WADERS.asItem(),
			OtherUtil.modResource("/anvil/lava_waders")
		)

		return listOf(
			obsidianSkullRing,
			obsidianWaterWalkingSkullOne,
			lavaWaders
		)
	}

}