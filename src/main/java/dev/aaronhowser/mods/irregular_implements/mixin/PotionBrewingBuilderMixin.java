package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionBrewing.Builder.class)
public abstract class PotionBrewingBuilderMixin {

	@Shadow
	public abstract void addMix(Holder<Potion> input, Item reagent, Holder<Potion> result);

	@Shadow
	public abstract void addStartMix(Item reagent, Holder<Potion> result);

	@Shadow
	public abstract void addContainerRecipe(Item input, Item reagent, Item result);

	@Inject(
			method = "addMix",
			at = @At("HEAD")
	)
	private void irregular_implements$glowingMushroomMix(
			Holder<Potion> input,
			Item reagent,
			Holder<Potion> result,
			CallbackInfo ci
	) {
		if (reagent == Items.GLOWSTONE_DUST || reagent.getDefaultInstance().is(Tags.Items.DUSTS_GLOWSTONE)) {
			addMix(input, ModBlocks.INSTANCE.getGLOWING_MUSHROOM().asItem(), result);
		}
	}

	@Inject(
			method = "addStartMix",
			at = @At("HEAD")
	)
	private void irregular_implements$glowingMushroomStartMix(
			Item reagent,
			Holder<Potion> result,
			CallbackInfo ci
	) {
		if (reagent == Items.GLOWSTONE_DUST || reagent.getDefaultInstance().is(Tags.Items.DUSTS_GLOWSTONE)) {
			addStartMix(reagent, result);
		}
	}

	@Inject(
			method = "addContainerRecipe",
			at = @At("HEAD")
	)
	private void irregular_implements$glowingMushroomContainerRecipe(
			Item input,
			Item reagent,
			Item result,
			CallbackInfo ci
	) {
		if (reagent == Items.GLOWSTONE_DUST || reagent.getDefaultInstance().is(Tags.Items.DUSTS_GLOWSTONE)) {
			addContainerRecipe(input, ModBlocks.INSTANCE.getGLOWING_MUSHROOM().asItem(), result);
		}
	}

}
