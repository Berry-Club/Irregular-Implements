package dev.aaronhowser.mods.irregular_implements.client.render

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.renderer.texture.OverlayTexture
import org.joml.Vector3f

object RenderUtils {

    fun renderCube(
        pose: PoseStack.Pose,
        consumer: VertexConsumer,
        pos: Vector3f,
        color: Int
    ) {
        val cubeRadius = 0.3f

        val minX = pos.x - cubeRadius
        val minY = pos.y - cubeRadius
        val minZ = pos.z - cubeRadius

        val maxX = pos.x + cubeRadius
        val maxY = pos.y + cubeRadius
        val maxZ = pos.z + cubeRadius

        // South face
        renderQuad(pose, consumer, color, minX, minY, maxZ, maxX, maxY, maxZ)
        // North face
        renderQuad(pose, consumer, color, maxX, minY, minZ, minX, maxY, minZ)
        // West face
        renderQuad(pose, consumer, color, minX, minY, minZ, minX, maxY, maxZ)
        // East face
        renderQuad(pose, consumer, color, maxX, minY, maxZ, maxX, maxY, minZ)

        // Top face
        renderQuad(pose, consumer, color, minX, maxY, minZ, maxX, maxY, maxZ)
        // Bottom face
        renderQuad(pose, consumer, color, minX, minY, maxZ, maxX, minY, minZ)
    }

    fun renderQuad(
        pose: PoseStack.Pose,
        consumer: VertexConsumer,
        color: Int,
        minX: Float, minY: Float, minZ: Float,
        maxX: Float, maxY: Float, maxZ: Float,
    ) {
        addVertex(pose, consumer, color, maxY, minX, minZ)
        addVertex(pose, consumer, color, minY, minX, minZ)
        addVertex(pose, consumer, color, minY, maxX, maxZ)
        addVertex(pose, consumer, color, maxY, maxX, maxZ)
    }

    fun addVertex(
        pose: PoseStack.Pose, consumer: VertexConsumer, color: Int, y: Float, x: Float, z: Float
    ) {
        consumer.addVertex(pose, x, y, z)
            .setColor(color)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0f, 1f, 0f)
    }

}