package dev.aaronhowser.mods.irregular_implements.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.aaronhowser.mods.irregular_implements.block.block_entity.LightRedirectorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(BlockRenderDispatcher.class)
public class BlockRenderDispatcherMixins {

    @Inject(
            method = "renderSingleBlock(Lnet/minecraft/world/level/block/state/BlockState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V",
            at = @At("HEAD")
    )
    private void renderSingleBlock(
            BlockState state,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay,
            ModelData modelData,
            RenderType renderType,
            CallbackInfo ci
    ) {

        synchronized (LightRedirectorBlockEntity.Companion.getRedirectorSet()) {
            if (LightRedirectorBlockEntity.Companion.getRedirectorSet().isEmpty()) return;

            BlockPos changedPos = irregular_Implements$getSwitchedPosition(levelReader, pos);

        }
    }

    @Unique
    private static HashSet<BlockPos> irregular_Implements$posSet = new HashSet<>();

    @Unique
    private static BlockPos irregular_Implements$getSwitchedPosition(LevelReader levelReader, BlockPos pos) {

    }

}
