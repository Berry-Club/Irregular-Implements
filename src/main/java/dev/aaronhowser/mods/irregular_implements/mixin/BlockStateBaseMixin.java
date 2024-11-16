package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneObserverBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {

    @Inject(
            method = "handleNeighborChanged",
            at = @At("HEAD")
    )
    private void irregular_implements$handleNeighborChanged(
            Level level,
            BlockPos pos,
            Block block,
            BlockPos fromPos,
            boolean isMoving,
            CallbackInfo ci
    ) {
        RedstoneObserverBlockEntity.updateObservers(level, pos);
    }
}
