package dev.aaronhowser.mods.irregular_implements.compatibility.emi.recipe

import dev.aaronhowser.mods.irregular_implements.compatibility.emi.ModEmiPlugin.Companion.emiIngredient
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.emi.emi.api.stack.EmiIngredient
import net.minecraft.world.item.Items

object AnvilRecipes {

    fun getRecipes(): List<EmiAnvilRecipe> {
        val obsidianSkullRing = EmiAnvilRecipe(
            ModItems.OBSIDIAN_SKULL.emiIngredient,
            Items.FIRE_CHARGE.emiIngredient,
            ModItems.OBSIDIAN_SKULL_RING.asItem(),
            OtherUtil.modResource("obsidian_skull_ring")
        )

        val obsidianWaterWalkingSkullOne = EmiAnvilRecipe(
            ModItems.WATER_WALKING_BOOTS.emiIngredient,
            EmiIngredient.of(
                listOf(
                    ModItems.OBSIDIAN_SKULL.emiIngredient,
                    ModItems.OBSIDIAN_SKULL_RING.emiIngredient
                )
            ),
            ModItems.OBSIDIAN_WATER_WALKING_BOOTS.asItem(),
            OtherUtil.modResource("obsidian_water_walking_skull")
        )

        val lavaWaders = EmiAnvilRecipe(
            ModItems.OBSIDIAN_WATER_WALKING_BOOTS.emiIngredient,
            ModItems.LAVA_CHARM.emiIngredient,
            ModItems.LAVA_WADERS.asItem(),
            OtherUtil.modResource("lava_waders")
        )

        return listOf(
            obsidianSkullRing,
            obsidianWaterWalkingSkullOne,
            lavaWaders
        )
    }

}