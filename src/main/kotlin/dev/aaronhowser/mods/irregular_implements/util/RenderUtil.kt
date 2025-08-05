package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.RandomSource
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions
import net.neoforged.neoforge.fluids.FluidStack
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
		width: Float,
		color: Int
	) {
		renderCube(
			poseStack,
			vertexConsumer,
			center.x.toFloat() - width / 2,
			center.y.toFloat() - width / 2,
			center.z.toFloat() - width / 2,
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
		posX: Float,
		posY: Float,
		posZ: Float,
		width: Float,
		length: Float,
		height: Float,
		color: Int
	) {

		val posX = posX
		val posY = posY
		val posZ = posZ
		val width = width
		val length = length
		val height = height

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

	fun getColorFromFluid(fluidStack: FluidStack): Int {
		val clientExt = IClientFluidTypeExtensions.of(fluidStack.fluid)
		val tintColor = clientExt.getTintColor(fluidStack)

		if (tintColor != -1) return tintColor

		val sprite = Minecraft.getInstance()
			.getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
			.apply(clientExt.getStillTexture(fluidStack))

		return RenderUtil.getSpriteAverageColor(sprite)
	}

	private val SPRITE_AVERAGE_COLOR_CACHE: MutableMap<TextureAtlasSprite, Int> = mutableMapOf()

	fun getSpriteAverageColor(sprite: TextureAtlasSprite): Int {
		val cachedColor = SPRITE_AVERAGE_COLOR_CACHE[sprite]
//		if (cachedColor != null) return cachedColor

		val nativeImage = sprite.contents().originalImage
		val width = nativeImage.width
		val height = nativeImage.height

		var totalRed = 0
		var totalGreen = 0
		var totalBlue = 0
		var totalPixels = 0

		// There's some bullfuckery going on here, and I blame Mojang
		// Putting `lava_still.png` into https://matkl.github.io/average-color/ returns `rgb(212, 90, 18)`
		// However, putting that image through `getPixelRGBA` and then averaging it all out returns `rgb(255, 18, 90)`
		// I have no god damn idea why it's doing that, but the simplest fix for me is to just accept that r is super wrong and then flip b and g
		// So instead of an rgba like it should be giving, i'm treating it as an rbga

		for (x in 0 until width) for (y in 0 until height) {
			val color = nativeImage.getPixelRGBA(x, y)

			val a = color and 0xFF
			if (a <= 0) continue

			val r = (color shr 24) and 0xFF
			val b = (color shr 16) and 0xFF
			val g = (color shr 8) and 0xFF

			totalRed += r
			totalGreen += g
			totalBlue += b
			totalPixels++
		}

		if (totalPixels == 0) return 0xFFFFFFFF.toInt()

		val averageRed = totalRed / totalPixels
		val averageGreen = totalGreen / totalPixels
		val averageBlue = totalBlue / totalPixels

		val averageColor = (0xFF shl 24) or (averageRed shl 16) or (averageGreen shl 8) or averageBlue
		SPRITE_AVERAGE_COLOR_CACHE[sprite] = averageColor
		return averageColor
	}

}