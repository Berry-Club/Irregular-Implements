package dev.aaronhowser.mods.irregular_implements.particle

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.nextRange
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.*

class ColoredFlameParticle(
	level: ClientLevel,
	x: Double,
	y: Double,
	z: Double,
	xSpeed: Double,
	ySpeed: Double,
	zSpeed: Double,
	options: ColoredFlameParticleOptions
) : RisingParticle(
	level,
	x, y, z,
	xSpeed, ySpeed, zSpeed
) {

	init {
		val f = random.nextRange(0.6f, 1f)

		this.rCol = options.color.x * f
		this.gCol = options.color.y * f
		this.bCol = options.color.z * f
	}

	override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_OPAQUE

	class Provider(
		val spriteSet: SpriteSet
	) : ParticleProvider<ColoredFlameParticleOptions> {
		override fun createParticle(
			options: ColoredFlameParticleOptions,
			level: ClientLevel,
			x: Double,
			y: Double,
			z: Double,
			xSpeed: Double,
			ySpeed: Double,
			zSpeed: Double
		): Particle {
			val particle = ColoredFlameParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options)
			particle.pickSprite(spriteSet)
			return particle
		}
	}

}