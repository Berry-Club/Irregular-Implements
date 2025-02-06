package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.client.render.RenderUtils
import dev.aaronhowser.mods.irregular_implements.entity.SpectreIlluminatorEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class SpectreIlluminatorEntityRenderer(
    context: EntityRendererProvider.Context
) : EntityRenderer<SpectreIlluminatorEntity>(context) {

    companion object {
        val TEXTURE = OtherUtil.modResource("no")

        private const val LENGTH_START = 0.1f
        private const val LENGTH_END = 2.25f

        private const val WIDTH_FACTOR = 0.35f
    }

    //TODO: Render the wibbly item texture in the center (also do this for the BEWLR)
    override fun render(
        spectreIlluminatorEntity: SpectreIlluminatorEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int
    ) {
        val time = (spectreIlluminatorEntity.tickCount + partialTick) / 400.0f

        val centerColor = 0xFF111111.toInt()
        val outerColor = 0x002C6A70

        val percentGrown = (spectreIlluminatorEntity.actionTimer + partialTick) / SpectreIlluminatorEntity.TICKS_TO_MAX_SIZE

        val rayLength = LENGTH_START + (LENGTH_END - LENGTH_START) * percentGrown
        val rayWidth = rayLength * WIDTH_FACTOR

        poseStack.pushPose()
        poseStack.translate(0.0, 0.5, 0.0)

        RenderUtils.renderRaysDoubleLayer(
            poseStack = poseStack,
            time = time,
            bufferSource = bufferSource,
            centerColor = centerColor,
            outerColor = outerColor,
            rayLength = rayLength,
            rayWidth = rayWidth
        )

        poseStack.popPose()

        super.render(spectreIlluminatorEntity, entityYaw, partialTick, poseStack, bufferSource, packedLight)
    }

    override fun getTextureLocation(entity: SpectreIlluminatorEntity): ResourceLocation {
        return TEXTURE
    }
}