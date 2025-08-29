package dev.aaronhowser.mods.irregular_implements.particle

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.core.particles.SimpleParticleType

class CubeParticle(
	level: ClientLevel,
	x: Double,
	y: Double,
	z: Double,
	xSpeed: Double,
	ySpeed: Double,
	zSpeed: Double
) : Particle(level, x, y, z, xSpeed, ySpeed, zSpeed) {

	override fun render(buffer: VertexConsumer, camera: Camera, partialTicks: Float) {
		RenderUtil.renderCube(
			PoseStack(),
			x - 0.5,
			y - 0.5,
			z - 0.5,
			1f,
			1f,
			1f,
			0xFFFFFFFF.toInt()
		)
	}

	override fun getRenderType(): ParticleRenderType = ParticleRenderType.CUSTOM

	object Provider : ParticleProvider<SimpleParticleType> {
		override fun createParticle(
			type: SimpleParticleType,
			level: ClientLevel,
			x: Double,
			y: Double,
			z: Double,
			xSpeed: Double,
			ySpeed: Double,
			zSpeed: Double
		): Particle {
			return CubeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed)
		}
	}

}