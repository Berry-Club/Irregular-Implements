package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreEnergyInjectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.client.render.RenderUtils
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class SpectreEnergyInjectorBlockEntityRenderer(
    val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<SpectreEnergyInjectorBlockEntity> {

    override fun render(
        blockEntity: SpectreEnergyInjectorBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val time = ((blockEntity.level?.gameTime ?: 0) + partialTick) / 200f

        poseStack.pushPose()
        poseStack.translate(0.5f, 0.6f, 0.5f)

        RenderUtils.renderRays(
            poseStack = poseStack,
            time = time,
            vertexConsumer = bufferSource.getBuffer(RenderType.dragonRays())
        )

        RenderUtils.renderRays(
            poseStack = poseStack,
            time = time,
            vertexConsumer = bufferSource.getBuffer(RenderType.dragonRaysDepth())
        )

        poseStack.popPose()
    }

    override fun shouldRenderOffScreen(blockEntity: SpectreEnergyInjectorBlockEntity): Boolean {
        return true
    }

}