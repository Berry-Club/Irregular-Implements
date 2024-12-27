package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.entity.ArtificialEndPortalEntity
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import kotlin.math.min

class ArtificialEndPortalRenderer(
    context: EntityRendererProvider.Context
) : EntityRenderer<ArtificialEndPortalEntity>(context) {

    companion object;

    override fun render(
        portalEntity: ArtificialEndPortalEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int
    ) {
        if (portalEntity.actionTimer > 85) return
        val player = ClientUtil.localPlayer ?: return

        val size = min(3.0, 3.0 / 115 * (portalEntity.actionTimer + partialTick - 85))

        val level = portalEntity.level()


    }

    @Suppress("WRONG_NULLABILITY_FOR_JAVA_OVERRIDE")
    override fun getTextureLocation(entity: ArtificialEndPortalEntity): ResourceLocation? {
        return null
    }
}