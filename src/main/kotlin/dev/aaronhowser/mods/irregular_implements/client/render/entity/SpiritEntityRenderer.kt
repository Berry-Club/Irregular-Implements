package dev.aaronhowser.mods.irregular_implements.client.render.entity

import dev.aaronhowser.mods.irregular_implements.entity.SpiritEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.model.SlimeModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.resources.ResourceLocation

class SpiritEntityRenderer(
	context: EntityRendererProvider.Context
) : MobRenderer<SpiritEntity, SlimeModel<SpiritEntity>>(
	context,
	SlimeModel(context.bakeLayer(ModelLayers.SLIME)),
	0.25f
) {

	override fun getTextureLocation(entity: SpiritEntity): ResourceLocation {
		return TEXTURE
	}

	companion object {
		val TEXTURE = OtherUtil.modResource("textures/entity/spirit.png")
	}

}