package dev.aaronhowser.mods.irregular_implements.client

import net.minecraft.client.renderer.DimensionSpecialEffects
import net.minecraft.world.phys.Vec3

class SpectreSpecialEffects : DimensionSpecialEffects(
	Float.NaN,
	false,
	SkyType.NONE,
	true,
	true
) {
	override fun getBrightnessDependentFogColor(fogColor: Vec3, brightness: Float): Vec3 = fogColor.scale(0.15)
	override fun isFoggyAt(x: Int, y: Int): Boolean = true
}