package dev.aaronhowser.mods.irregular_implements.client.render.entity

import dev.aaronhowser.mods.irregular_implements.entity.GoldenChickenEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.model.ChickenModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth

class GoldenChickenRenderer(context: EntityRendererProvider.Context) : MobRenderer<GoldenChickenEntity, ChickenModel<GoldenChickenEntity>>(
	context,
	ChickenModel(context.bakeLayer(ModelLayers.CHICKEN)),
	0.3f
) {

	companion object {
		val TEXTURE = OtherUtil.modResource("textures/entity/golden_chicken.png")
	}

	override fun getTextureLocation(entity: GoldenChickenEntity): ResourceLocation {
		return TEXTURE
	}

	override fun getBob(livingBase: GoldenChickenEntity, partialTick: Float): Float {
		val f = Mth.lerp(partialTick, livingBase.oFlap, livingBase.flap)
		val f1 = Mth.lerp(partialTick, livingBase.oFlapSpeed, livingBase.flapSpeed)
		return (Mth.sin(f) + 1.0f) * f1
	}

}