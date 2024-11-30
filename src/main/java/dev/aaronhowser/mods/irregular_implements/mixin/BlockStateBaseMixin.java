package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneObserverBlockEntity;
import dev.aaronhowser.mods.irregular_implements.item.ModArmorItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

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

    @Inject(
            method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void irregular_implements$getCollisionShape(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        @Nullable VoxelShape shape = ModArmorItems.checkCollisionShape(level, pos, context, cir.getReturnValue());

        if (shape != null) {
            cir.setReturnValue(shape);
        }
    }

}
