package dev.aaronhowser.mods.irregular_implements.client.render

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f


object RenderUtils {

    fun renderCube(
        poseStack: PoseStack,
        vertexConsumer: VertexConsumer,
        center: Vec3,
        width: Number,
        color: Int
    ) {
        renderCube(
            poseStack,
            vertexConsumer,
            center.x.toFloat() - width.toFloat() / 2,
            center.y.toFloat() - width.toFloat() / 2,
            center.z.toFloat() - width.toFloat() / 2,
            width.toFloat(),
            width.toFloat(),
            width.toFloat(),
            color
        )
    }

    fun renderCube(
        poseStack: PoseStack,
        vertexConsumer: VertexConsumer,
        posX: Float,
        posY: Float,
        posZ: Float,
        width: Float,
        length: Float,
        height: Float,
        color: Int
    ) {
        poseStack.pushPose()
        poseStack.translate(posX.toDouble(), posY.toDouble(), posZ.toDouble())

        val pose = poseStack.last()

        val vertices = arrayOf(
            Vector3f(0f, 0f, 0f),
            Vector3f(0f, height, 0f),
            Vector3f(width, height, 0f),
            Vector3f(width, 0f, 0f),
            Vector3f(width, height, length),
            Vector3f(width, 0f, length),
            Vector3f(0f, height, length),
            Vector3f(0f, 0f, length)
        )

        val faces = listOf(
            listOf(0, 1, 2, 3),  // Front
            listOf(3, 2, 4, 5),  // Right
            listOf(5, 4, 6, 7),  // Back
            listOf(7, 6, 1, 0),  // Left
            listOf(1, 6, 4, 2),  // Top
            listOf(7, 0, 3, 5)   // Bottom
        )

        for (face in faces) {
            for (vertexIndex in face) {
                val vertex = vertices[vertexIndex]
                vertexConsumer.addVertex(pose.pose(), vertex.x, vertex.y, vertex.z)
                    .setColor(color)
                    .setUv(0f, 0f)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setNormal(pose, 0f, 1f, 0f)
            }
        }

        poseStack.popPose()
    }

}