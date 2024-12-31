package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreEnergyInjectorBlockEntity
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
        poseStack.pushPose()
        poseStack.translate(0.5, 0.6, 0.5)

        val buffer = bufferSource.getBuffer(RenderType.translucent())

        // Define vertices for a single triangle
        val size = 0.5f
        val colorRed = 255
        val colorGreen = 50
        val colorBlue = 200
        val alpha = 128 // Semi-transparent

        buffer.addVertex(poseStack.last().pose(), 0f, size, 0f)
            .setColor(colorRed, colorGreen, colorBlue, alpha)
            .setUv(0f, 0f)
            .setUv2(0xFFFFFF, 0xFFFFFF)
            .setNormal(0f, 1f, 0f)

        buffer.addVertex(poseStack.last().pose(), -size, -size, size)
            .setColor(colorRed, colorGreen, colorBlue, alpha)
            .setUv(0f, 0f)
            .setUv2(0xFFFFFF, 0xFFFFFF)
            .setNormal(0f, 1f, 0f)

        buffer.addVertex(poseStack.last().pose(), size, -size, size)
            .setColor(colorRed, colorGreen, colorBlue, alpha)
            .setUv(0f, 0f)
            .setUv2(0xFFFFFF, 0xFFFFFF)
            .setNormal(0f, 1f, 0f)

        // Second side of the triangle for double-sided rendering
        buffer.addVertex(poseStack.last().pose(), 0f, size, 0f)
            .setColor(colorRed, colorGreen, colorBlue, alpha)
            .setUv(0f, 0f)
            .setUv2(0xFFFFFF, 0xFFFFFF)
            .setNormal(0f, 1f, 0f)

        buffer.addVertex(poseStack.last().pose(), size, -size, size)
            .setColor(colorRed, colorGreen, colorBlue, alpha)
            .setUv(0f, 0f)
            .setUv2(0xFFFFFF, 0xFFFFFF)
            .setNormal(0f, 1f, 0f)

        buffer.addVertex(poseStack.last().pose(), -size, -size, size)
            .setColor(colorRed, colorGreen, colorBlue, alpha)
            .setUv(0f, 0f)
            .setUv2(0xFFFFFF, 0xFFFFFF)
            .setNormal(0f, 1f, 0f)

        poseStack.popPose()
    }

    override fun shouldRenderOffScreen(blockEntity: SpectreEnergyInjectorBlockEntity): Boolean {
        return true
    }

}