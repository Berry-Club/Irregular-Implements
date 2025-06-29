package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.aaronhowser.mods.irregular_implements.PoweredRedstoneInterfaces;
import dev.aaronhowser.mods.irregular_implements.handler.redstone_signal.RedstoneHandlerSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.SignalGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SignalGetter.class)
public interface SignalGetterMixin {

	@ModifyReturnValue(
			method = "getSignal",
			at = @At("RETURN")
	)
	default int irregular_implements$getSignal(int original, BlockPos pos, Direction direction) {
		return irregular_Implements$getModifiedSignal(original, pos, direction);
	}

	@ModifyReturnValue(
			method = "getDirectSignal",
			at = @At("RETURN")
	)
	default int irregular_implements$getDirectSignal(int original, BlockPos pos, Direction direction) {
		return irregular_Implements$getModifiedSignal(original, pos, direction);
	}

	@Unique
	private int irregular_Implements$getModifiedSignal(int original, BlockPos pos, Direction direction) {
		if (original >= 15) return original;

		int signal = original;

		if (this instanceof PoweredRedstoneInterfaces f) {
			signal = Math.max(signal, f.irregular_implements$getLinkedInterfacePower(pos, direction));
		}

		if (this instanceof ServerLevel sl) {
			signal = Math.max(signal, RedstoneHandlerSavedData.getRedstoneHandlerSavedData(sl).getStrongPower(sl, pos, direction));
		}

		return signal;
	}

}
