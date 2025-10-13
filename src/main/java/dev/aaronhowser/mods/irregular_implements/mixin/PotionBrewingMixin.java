package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Surely there's a better way to do this?
@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {

	@Shadow
	public abstract boolean hasMix(ItemStack reagent, ItemStack potionItem);

	@Inject(
			method = "isIngredient",
			at = @At("HEAD"),
			cancellable = true
	)
	private void irregular_implements$allowGlowingMushroomForGlowstoneInIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (cir != null && stack.is(ModItemTagsProvider.BREWABLE_AS_GLOWSTONE)) {
			cir.setReturnValue(true);
		}
	}

	@ModifyReturnValue(
			method = "hasMix",
			at = @At("RETURN")
	)
	private boolean irregular_implements$allowGlowingMushroomForGlowstoneInHasMix(
			boolean original,
			ItemStack potionItem,
			ItemStack reagent
	) {
		if (!original) {
			if (reagent.is(ModItemTagsProvider.BREWABLE_AS_GLOWSTONE)) {
				return hasMix(Items.GLOWSTONE_DUST.getDefaultInstance(), potionItem);
			}
		}

		return original;
	}

}
