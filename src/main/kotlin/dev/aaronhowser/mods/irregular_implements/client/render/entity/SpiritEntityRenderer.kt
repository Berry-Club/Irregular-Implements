package dev.aaronhowser.mods.irregular_implements.client.render.entity

import dev.aaronhowser.mods.irregular_implements.entity.SpiritEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class SpiritEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<SpiritEntity>(context) {

	override fun getTextureLocation(entity: SpiritEntity): ResourceLocation {
		return TEXTURE
	}

	companion object {
		val TEXTURE = OtherUtil.modResource("textures/entity/spirit.png")
	}

}