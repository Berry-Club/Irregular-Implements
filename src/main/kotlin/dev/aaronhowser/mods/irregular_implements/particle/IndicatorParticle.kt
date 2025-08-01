package dev.aaronhowser.mods.irregular_implements.particle

import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.registry.ModParticleTypes
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import org.joml.Vector3f

class IndicatorParticle(
	level: ClientLevel,
	x: Double,
	y: Double,
	z: Double
) : Particle(level, x, y, z) {

	override fun render(buffer: VertexConsumer, camera: Camera, partialTicks: Float) {
		for (face in FACES) {
			for (vertexIndex in face) {
				val vertex = VERTICES[vertexIndex]
				buffer.addVertex(vertex.x, vertex.y, vertex.z)
					.setColor(0xFFFFFFFF.toInt())
					.setUv(0f, 0f)
					.setNormal(0f, 1f, 0f)
			}
		}
	}

	override fun getRenderType(): ParticleRenderType = ParticleRenderType.CUSTOM

	class IndicatorParticleType(overrideLimitter: Boolean) : ParticleType<IndicatorParticleOptions>(overrideLimitter) {
		override fun codec(): MapCodec<IndicatorParticleOptions> {
			TODO("Not yet implemented")
		}

		override fun streamCodec(): StreamCodec<in RegistryFriendlyByteBuf, IndicatorParticleOptions> {
			TODO("Not yet implemented")
		}
	}

	class IndicatorParticleOptions : ParticleOptions {
		override fun getType(): ParticleType<*> = ModParticleTypes.INDICATOR.get()
	}

	class IndicatorParticleProvider : ParticleProvider<IndicatorParticleOptions> {
		override fun createParticle(
			type: IndicatorParticleOptions,
			level: ClientLevel,
			x: Double,
			y: Double,
			z: Double,
			xSpeed: Double,
			ySpeed: Double,
			zSpeed: Double
		): Particle {
			return IndicatorParticle(level, x, y, z)
		}
	}

	companion object {
		const val HEIGHT = 1f
		const val WIDTH = 1f
		const val LENGTH = 1f

		private val VERTICES = arrayOf(
			Vector3f(0f, 0f, 0f),
			Vector3f(0f, HEIGHT, 0f),
			Vector3f(WIDTH, HEIGHT, 0f),
			Vector3f(WIDTH, 0f, 0f),
			Vector3f(WIDTH, HEIGHT, LENGTH),
			Vector3f(WIDTH, 0f, LENGTH),
			Vector3f(0f, HEIGHT, LENGTH),
			Vector3f(0f, 0f, LENGTH)
		)

		val FACES = listOf(
			listOf(0, 1, 2, 3),  // Front
			listOf(3, 2, 4, 5),  // Right
			listOf(5, 4, 6, 7),  // Back
			listOf(7, 6, 1, 0),  // Left
			listOf(1, 6, 4, 2),  // Top
			listOf(7, 0, 3, 5)   // Bottom
		)
	}

}