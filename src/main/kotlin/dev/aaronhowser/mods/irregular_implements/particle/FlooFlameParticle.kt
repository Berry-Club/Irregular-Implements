package dev.aaronhowser.mods.irregular_implements.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.*
import net.minecraft.core.particles.SimpleParticleType

class FlooFlameParticle(
	level: ClientLevel,
	x: Double,
	y: Double,
	z: Double,
	xSpeed: Double,
	ySpeed: Double,
	zSpeed: Double
) : FlameParticle(level, x, y, z, xSpeed, ySpeed, zSpeed) {

	override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_LIT

	class Provider(
		val spriteSet: SpriteSet
	) : ParticleProvider<SimpleParticleType> {
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
			val particle = FlooFlameParticle(level, x, y, z, xSpeed, ySpeed, zSpeed)
			particle.pickSprite(spriteSet)
			return particle
		}
	}

}