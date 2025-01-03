package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.aaronhowser.mods.irregular_implements.entity.ArtificialEndPortalEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f

class ArtificialEndPortalRenderer(
    context: EntityRendererProvider.Context
) : EntityRenderer<ArtificialEndPortalEntity>(context) {

    override fun render(
        portalEntity: ArtificialEndPortalEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int
    ) {
        val size = (3f / 115 * (portalEntity.actionTimer + partialTick - 85))
            .coerceIn(0f, 3f)

        val radius = size / 2

        val min = -radius
        val max = radius

        val pose = poseStack.last().pose()
        val consumer = bufferSource.getBuffer(RenderType.endPortal())

        renderFace(pose, consumer, min, max, 0.375f, 0.375f, min, min, max, max)
        renderFace(pose, consumer, min, max, 0.75f, 0.75f, max, max, min, min)
    }

    private fun renderFace(
        pose: Matrix4f,
        vertexConsumer: VertexConsumer,
        x0: Float,
        x1: Float,
        y0: Float,
        y1: Float,
        z0: Float,
        z1: Float,
        z2: Float,
        z3: Float,
    ) {
        vertexConsumer.addVertex(pose, x0, y0, z0)
        vertexConsumer.addVertex(pose, x1, y0, z1)
        vertexConsumer.addVertex(pose, x1, y1, z2)
        vertexConsumer.addVertex(pose, x0, y1, z3)
    }


    @Suppress("WRONG_NULLABILITY_FOR_JAVA_OVERRIDE")
    override fun getTextureLocation(entity: ArtificialEndPortalEntity): ResourceLocation? {
        return null
    }
}