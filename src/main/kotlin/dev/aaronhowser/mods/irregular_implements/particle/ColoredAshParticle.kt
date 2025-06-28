package dev.aaronhowser.mods.irregular_implements.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.BaseAshSmokeParticle
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.core.particles.SimpleParticleType

class ColoredAshParticle : BaseAshSmokeParticle {

	// Because I hate how long it was lmao
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(
		level: ClientLevel,
		x: Double, y: Double, z: Double,
		xSpeed: Double, ySpeed: Double, zSpeed: Double,
		red: Float, green: Float, blue: Float,
		quadSizeMultiplier: Float,
		sprites: SpriteSet,
		lifetime: Int,
	) : super(
		level,
		x, y, z,
		0.1f, -0.1f, 0.1f,
		xSpeed, ySpeed, zSpeed,
		quadSizeMultiplier,
		sprites,
		0f,
		lifetime,
		0.0125f,
		false
	) {
		this.rCol = red
		this.gCol = green
		this.bCol = blue
	}

	constructor(
		level: ClientLevel,
		x: Double, y: Double, z: Double,
		xSpeed: Double, ySpeed: Double, zSpeed: Double,
		color: Int,
		quadSizeMultiplier: Float,
		sprites: SpriteSet,
		lifetime: Int,
	) : this(
		level,
		x, y, z,
		xSpeed, ySpeed, zSpeed,
		((color shr 16) and 0xFF) / 255f,
		((color shr 8) and 0xFF) / 255f,
		(color and 0xFF) / 255f,
		quadSizeMultiplier,
		sprites,
		lifetime
	)

	class Provider(private val sprites: SpriteSet) : ParticleProvider<SimpleParticleType> {

		override fun createParticle(
			type: SimpleParticleType,
			level: ClientLevel,
			x: Double,
			y: Double,
			z: Double,
			xSpeed: Double,
			ySpeed: Double,
			zSpeed: Double
		): Particle? {
			TODO("Not yet implemented")
		}

	}

}