package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.block.SakanadeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.HugeBrownMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HugeBrownMushroomFeature.class)
public abstract class HugeBrownMushroomFeatureMixin {

    @Inject(
            method = "makeCap",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/feature/HugeBrownMushroomFeature;setBlock(Lnet/minecraft/world/level/LevelWriter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void a(
            LevelAccessor level,
            RandomSource random,
            BlockPos pos,
            int treeHeight,
            BlockPos.MutableBlockPos mutablePos,
            HugeMushroomFeatureConfiguration config,
            CallbackInfo ci
    ) {
        SakanadeBlock.addToMushroom(level, pos, config, mutablePos);
    }

}
