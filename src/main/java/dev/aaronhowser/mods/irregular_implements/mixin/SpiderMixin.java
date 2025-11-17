package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil;
import net.minecraft.world.entity.monster.Spider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Spider.class)
public abstract class SpiderMixin {

	@Inject(
			method = "setClimbing",
			at = @At("HEAD"),
			cancellable = true
	)
	private void irregular_implements$preventClimbing(boolean climbing, CallbackInfo ci) {
		if (climbing) {
			if (OtherUtil.shouldSpiderNotClimb((Spider) (Object) this)) {
				ci.cancel();
			}
		}
	}

}
