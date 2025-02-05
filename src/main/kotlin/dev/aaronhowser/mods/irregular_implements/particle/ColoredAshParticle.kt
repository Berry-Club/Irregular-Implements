package dev.aaronhowser.mods.irregular_implements.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.BaseAshSmokeParticle
import net.minecraft.client.particle.SpriteSet

class ColoredAshParticle : BaseAshSmokeParticle {

    // Because I hate how long it was lmao
    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(
        level: ClientLevel,
        x: Double, y: Double, z: Double,
        xSpeed: Double, ySpeed: Double, zSpeed: Double,
        quadSizeMultiplier: Float,
        sprites: SpriteSet,
        rColMultiplier: Float,
        lifetime: Int,
    ) : super(
        level,
        x, y, z,
        0.1f, -0.1f, 0.1f,
        xSpeed, ySpeed, zSpeed,
        quadSizeMultiplier, sprites,
        rColMultiplier,
        lifetime,
        0.0125f,
        false
    )

}