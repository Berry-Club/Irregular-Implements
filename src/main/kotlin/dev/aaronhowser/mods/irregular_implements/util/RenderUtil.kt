package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.util.RandomSource
import net.minecraft.world.phys.Vec3
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.sqrt

object RenderUtil {

	private val HALF_SQRT_3: Float = (sqrt(3.0) / 2.0).toFloat()

	fun renderRaysDoubleLayer(
		poseStack: PoseStack,
		time: Float,
		bufferSource: MultiBufferSource,
		centerColor: Int = 0xFF000000.toInt(),
		outerColor: Int = 0x002C6A70,
		amountRays: Int = 15,
		rayLength: Float = 0.325f,
		rayWidth: Float = 0.15f
	) {
		renderDragonRays(
			poseStack = poseStack,
			time = time,
			bufferSource = bufferSource,
			centerColor = centerColor,
			outerColor = outerColor,
			amountRays = amountRays,
			rayLength = rayLength,
			rayWidth = rayWidth
		)

		renderDragonRaysDepth(
			poseStack = poseStack,
			time = time,
			bufferSource = bufferSource,
			centerColor = centerColor,
			outerColor = outerColor,
			amountRays = amountRays,
			rayLength = rayLength,
			rayWidth = rayWidth
		)
	}

	fun renderDragonRays(
		poseStack: PoseStack,
		time: Float,
		bufferSource: MultiBufferSource,
		centerColor: Int = 0xFF000000.toInt(),
		outerColor: Int = 0x002C6A70,
		amountRays: Int = 15,
		rayLength: Float = 0.325f,
		rayWidth: Float = 0.15f
	) {
		renderRays(
			poseStack = poseStack,
			time = time,
			vertexConsumer = bufferSource.getBuffer(RenderType.dragonRays()),
			centerColor = centerColor,
			outerColor = outerColor,
			amountRays = amountRays,
			rayLength = rayLength,
			rayWidth = rayWidth
		)
	}

	fun renderDragonRaysDepth(
		poseStack: PoseStack,
		time: Float,
		bufferSource: MultiBufferSource,
		centerColor: Int = 0xFF000000.toInt(),
		outerColor: Int = 0x002C6A70,
		amountRays: Int = 15,
		rayLength: Float = 0.325f,
		rayWidth: Float = 0.15f
	) {
		renderRays(
			poseStack = poseStack,
			time = time,
			vertexConsumer = bufferSource.getBuffer(RenderType.dragonRaysDepth()),
			centerColor = centerColor,
			outerColor = outerColor,
			amountRays = amountRays,
			rayLength = rayLength,
			rayWidth = rayWidth
		)
	}

	fun renderRays(
		poseStack: PoseStack,
		time: Float,
		vertexConsumer: VertexConsumer,
		centerColor: Int = 0xFF000000.toInt(),
		outerColor: Int = 0x002C6A70,
		amountRays: Int = 15,
		rayLength: Float = 0.325f,
		rayWidth: Float = 0.15f
	) {
		poseStack.pushPose()

		val randomSource = RandomSource.create(432L)
		val vec0 = Vector3f()
		val vec1 = Vector3f()
		val vec2 = Vector3f()
		val vec3 = Vector3f()
		val quaternionf = Quaternionf()

		for (rayIndex in 0 until amountRays) {
			quaternionf
				.rotateXYZ(
					randomSource.nextFloat() * (Math.PI * 2).toFloat(),
					randomSource.nextFloat() * (Math.PI * 2).toFloat(),
					randomSource.nextFloat() * (Math.PI * 2).toFloat()
				)
				.rotateXYZ(
					randomSource.nextFloat() * (Math.PI * 2).toFloat(),
					randomSource.nextFloat() * (Math.PI * 2).toFloat(),
					randomSource.nextFloat() * (Math.PI * 2).toFloat() + time * (Math.PI / 2).toFloat()
				)

			poseStack.mulPose(quaternionf)

			vec1.set(-HALF_SQRT_3 * rayWidth, rayLength, -0.5F * rayWidth)
			vec2.set(HALF_SQRT_3 * rayWidth, rayLength, -0.5F * rayWidth)
			vec3.set(0.0F, rayLength, rayWidth)

			val pose = poseStack.last()

			vertexConsumer.addVertex(pose, vec0).setColor(centerColor)
			vertexConsumer.addVertex(pose, vec1).setColor(outerColor)
			vertexConsumer.addVertex(pose, vec2).setColor(outerColor)

			vertexConsumer.addVertex(pose, vec0).setColor(centerColor)
			vertexConsumer.addVertex(pose, vec2).setColor(outerColor)
			vertexConsumer.addVertex(pose, vec3).setColor(outerColor)

			vertexConsumer.addVertex(pose, vec0).setColor(centerColor)
			vertexConsumer.addVertex(pose, vec3).setColor(outerColor)
			vertexConsumer.addVertex(pose, vec1).setColor(outerColor)
		}

		poseStack.popPose()
	}

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
			width,
			width,
			width,
			color
		)
	}

	@Suppress("NAME_SHADOWING")
	fun renderCube(
		poseStack: PoseStack,
		vertexConsumer: VertexConsumer,
		posX: Number,
		posY: Number,
		posZ: Number,
		width: Number,
		length: Number,
		height: Number,
		color: Int
	) {

		val posX = posX.toFloat()
		val posY = posY.toFloat()
		val posZ = posZ.toFloat()
		val width = width.toFloat()
		val length = length.toFloat()
		val height = height.toFloat()

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