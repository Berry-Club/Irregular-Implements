package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.Direction
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
		center: Vec3,
		width: Float,
		color: Int
	) {
		renderCube(
			poseStack,
			center.x - width / 2,
			center.y - width / 2,
			center.z - width / 2,
			width,
			width,
			width,
			color
		)
	}

	fun renderCube(
		poseStack: PoseStack,
		posX: Number,
		posY: Number,
		posZ: Number,
		width: Float,
		length: Float,
		height: Float,
		color: Int
	) {
		val vertexConsumer = Minecraft.getInstance()
			.renderBuffers()
			.bufferSource()
			.getBuffer(RenderType.debugQuads())

		poseStack.pushPose()
		poseStack.translate(posX.toDouble(), posY.toDouble(), posZ.toDouble())

		renderFace(
			poseStack,
			vertexConsumer,
			0f, 0f, 0f,
			width, length,
			color,
			Direction.DOWN
		)

		renderFace(
			poseStack,
			vertexConsumer,
			0f, height, 0f,
			width, length,
			color,
			Direction.UP
		)

		renderFace(
			poseStack,
			vertexConsumer,
			0f, 0f, 0f,
			width, height,
			color,
			Direction.NORTH
		)

		renderFace(
			poseStack,
			vertexConsumer,
			0f, 0f, length,
			width, height,
			color,
			Direction.SOUTH
		)

		renderFace(
			poseStack,
			vertexConsumer,
			0f, 0f, 0f,
			length, height,
			color,
			Direction.WEST
		)

		renderFace(
			poseStack,
			vertexConsumer,
			width, 0f, 0f,
			length, height,
			color,
			Direction.EAST
		)

		poseStack.popPose()
	}

	/** @param length is used as height for UP and DOWN faces */
	fun renderFace(
		poseStack: PoseStack,
		vertexConsumer: VertexConsumer,
		posX: Number,
		posY: Number,
		posZ: Number,
		width: Float,
		length: Float,
		color: Int,
		direction: Direction
	) {
		poseStack.pushPose()
		poseStack.translate(posX.toDouble(), posY.toDouble(), posZ.toDouble())

		val pose = poseStack.last()

		val vertices = when (direction) {
			Direction.UP -> listOf(
				Vector3f(0f, 0f, length),
				Vector3f(width, 0f, length),
				Vector3f(width, 0f, 0f),
				Vector3f(0f, 0f, 0f)
			)

			Direction.DOWN -> listOf(
				Vector3f(width, 0f, 0f),
				Vector3f(width, 0f, length),
				Vector3f(0f, 0f, length),
				Vector3f(0f, 0f, 0f)
			)

			Direction.NORTH -> listOf(
				Vector3f(0f, length, 0f),
				Vector3f(width, length, 0f),
				Vector3f(width, 0f, 0f),
				Vector3f(0f, 0f, 0f)
			)

			Direction.SOUTH -> listOf(
				Vector3f(width, 0f, 0f),
				Vector3f(width, length, 0f),
				Vector3f(0f, length, 0f),
				Vector3f(0f, 0f, 0f)
			)

			Direction.EAST -> listOf(
				Vector3f(0f, 0f, 0f),
				Vector3f(0f, length, 0f),
				Vector3f(0f, length, width),
				Vector3f(0f, 0f, width)
			)

			Direction.WEST -> listOf(
				Vector3f(0f, 0f, 0f),
				Vector3f(0f, 0f, width),
				Vector3f(0f, length, width),
				Vector3f(0f, length, 0f)
			)
		}

		for (vertex in vertices) {
			addVertex(
				pose,
				vertexConsumer,
				color,
				vertex.x, vertex.y, vertex.z,
				0f, 0f
			)
		}

		poseStack.popPose()
	}

	fun addVertex(
		pose: PoseStack.Pose,
		consumer: VertexConsumer,
		color: Int,
		x: Float, y: Float, z: Float,
		u: Float, v: Float,
		normalX: Float = 0f,
		normalY: Float = 1f,
		normalZ: Float = 0f,
		light: Int = 15728880,
		overlay: Int = OverlayTexture.NO_OVERLAY,
	) {
		consumer.addVertex(pose.pose(), x, y, z)
			.setColor(color)
			.setUv(u, v)
			.setOverlay(overlay)
			.setLight(light)
			.setNormal(pose, normalX, normalY, normalZ)
	}

	fun addVertex(
		pose: PoseStack.Pose,
		consumer: VertexConsumer,
		color: Int,
		x: Float, y: Float, z: Float,
		u: Float, v: Float,
		u1: Int, v1: Int,
		u2: Int, v2: Int,
		normalX: Float = 0f,
		normalY: Float = 1f,
		normalZ: Float = 0f,
	) {
		consumer.addVertex(pose.pose(), x, y, z)
			.setColor(color)
			.setUv(u, v)
			.setUv1(u1, v1)
			.setUv2(u2, v2)
			.setNormal(pose, normalX, normalY, normalZ)
	}

	fun getColorFromFluid(fluidStack: FluidStack): Int {
		val clientExt = IClientFluidTypeExtensions.of(fluidStack.fluid)
		val tintColor = clientExt.getTintColor(fluidStack)

		if (tintColor != -1) return tintColor

		val sprite = Minecraft.getInstance()
			.getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
			.apply(clientExt.getStillTexture(fluidStack))

		return getSpriteAverageColor(sprite)
	}

	private val SPRITE_AVERAGE_COLOR_CACHE: MutableMap<TextureAtlasSprite, Int> = mutableMapOf()

	fun getSpriteAverageColor(sprite: TextureAtlasSprite): Int {
		val cachedColor = SPRITE_AVERAGE_COLOR_CACHE[sprite]
		if (cachedColor != null) return cachedColor

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