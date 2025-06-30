package dev.aaronhowser.mods.irregular_implements.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.FlameParticle

class FlooFlameParticle(
	level: ClientLevel,
	x: Double, y: Double, z: Double,
	xSpeed: Double, ySpeed: Double, zSpeed: Double
) : FlameParticle(level, x, y, z, xSpeed, ySpeed, zSpeed) {


}