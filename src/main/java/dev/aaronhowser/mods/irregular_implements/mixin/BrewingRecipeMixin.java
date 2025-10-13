package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider;
import dev.aaronhowser.mods.irregular_implements.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrewingRecipe.class)
public abstract class BrewingRecipeMixin {

	@Shadow
	public abstract Ingredient getIngredient();

	@ModifyReturnValue(
			method = "isIngredient",
			at = @At("RETURN")
	)
	boolean irregular_implements$allowGlowingMushroomForGlowstoneInIngredient(boolean original, ItemStack ingredient) {
		if (original) {
			return true;
		}

		return this.getIngredient().test(Items.GLOWSTONE.getDefaultInstance()) && ingredient.is(ModItemTagsProvider.BREWABLE_AS_GLOWSTONE);
	}

}
