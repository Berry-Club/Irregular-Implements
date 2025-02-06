package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.client.render.RenderUtils
import dev.aaronhowser.mods.irregular_implements.entity.WeatherCloudEntity
import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class WeatherCloudRenderer(
    context: EntityRendererProvider.Context
) : EntityRenderer<WeatherCloudEntity>(context) {

    companion object {
        val TEXTURE = OtherUtil.modResource("naw")
    }

    override fun getTextureLocation(entity: WeatherCloudEntity): ResourceLocation {
        return TEXTURE
    }

    override fun render(
        spectreIlluminatorEntity: WeatherCloudEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int
    ) {
        if (spectreIlluminatorEntity.weather != WeatherEggItem.Weather.SUNNY) return

        val time = (spectreIlluminatorEntity.tickCount + partialTick) / 200.0f

        val centerColor = 0xFFFFFF00.toInt()
        val outerColor = 0x00FF0000

        val rayLength = 1f
        val rayWidth = 0.33f

        poseStack.pushPose()

        RenderUtils.renderDragonRays(
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

}