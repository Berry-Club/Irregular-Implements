package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.client.render.block.SpectreEnergyInjectorBlockEntityRenderer
import dev.aaronhowser.mods.irregular_implements.entity.IlluminatorEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class IlluminatorEntityRenderer(
    context: EntityRendererProvider.Context
) : EntityRenderer<IlluminatorEntity>(context) {

    companion object {
        val TEXTURE = OtherUtil.modResource("no")

        private const val TIME_TO_MAX_SIZE = 20 * 30

        private const val LENGTH_START = 0.25f
        private const val LENGTH_END = 1.5f

        private const val WIDTH_FACTOR = 0.35f
    }

    override fun render(
        illuminatorEntity: IlluminatorEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int
    ) {
        val time = (illuminatorEntity.tickCount + partialTick) / 400.0f

        val centerColor = 0xFF111111.toInt()
        val outerColor = 0x002C6A70

        val rayLength = LENGTH_START + (LENGTH_END - LENGTH_START) * (illuminatorEntity.tickCount % TIME_TO_MAX_SIZE) / TIME_TO_MAX_SIZE
        val rayWidth = rayLength * WIDTH_FACTOR

        SpectreEnergyInjectorBlockEntityRenderer.renderRays(
            poseStack = poseStack,
            time = time,
            vertexConsumer = bufferSource.getBuffer(RenderType.dragonRays()),
            centerColor = centerColor,
            outerColor = outerColor,
            rayLength = rayLength,
            rayWidth = rayWidth
        )

        SpectreEnergyInjectorBlockEntityRenderer.renderRays(
            poseStack = poseStack,
            time = time,
            vertexConsumer = bufferSource.getBuffer(RenderType.dragonRaysDepth()),
            centerColor = centerColor,
            outerColor = outerColor,
            rayLength = rayLength,
            rayWidth = rayWidth
        )

        super.render(illuminatorEntity, entityYaw, partialTick, poseStack, bufferSource, packedLight)
    }

    override fun getTextureLocation(entity: IlluminatorEntity): ResourceLocation {
        return TEXTURE
    }
}