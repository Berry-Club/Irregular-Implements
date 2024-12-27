package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import dev.aaronhowser.mods.irregular_implements.entity.ArtificialEndPortalEntity
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f
import org.joml.Vector3f
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

        val tesselator = Tesselator.getInstance()
        val bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR)
        val offset = 1f

        for (pass in 0 until getPasses(portalEntity.distanceToSqr(player))) {
            val vertexConsumer = bufferSource.getBuffer(RenderType.endPortal())

            val modelViewMatrix = Matrix4f()
            modelViewMatrix.identity()

            modelViewMatrix.translate(Vector3f(0.5f, 0.5f, 0f))
            modelViewMatrix.scale(0.5f, 0.5f, 1f)

            val f1 = pass + 1f
            modelViewMatrix.translate(
                17f / f1,
                (2f + f1 * 1.5f) * (System.currentTimeMillis() % 8000) / 8000f,
                0f
            )

            val sizeHalf = size / 2
            val finalMatrix = Matrix4f(project)

        }


    }

    fun getPasses(distance: Double): Int {
        return when {
            distance > 36864.0 -> 1
            distance > 25600.0 -> 3
            distance > 16384.0 -> 5
            distance > 9216.0 -> 7
            distance > 4096.0 -> 9
            distance > 1024.0 -> 11
            distance > 576.0 -> 13
            distance > 256.0 -> 14
            else -> 15
        }
    }

    override fun getTextureLocation(entity: ArtificialEndPortalEntity): ResourceLocation? {
        return null
    }
}