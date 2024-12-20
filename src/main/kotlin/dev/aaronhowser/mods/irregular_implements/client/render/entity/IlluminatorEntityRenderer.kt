package dev.aaronhowser.mods.irregular_implements.client.render.entity

import dev.aaronhowser.mods.irregular_implements.entity.IlluminatorEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class IlluminatorEntityRenderer(
    context: EntityRendererProvider.Context
) : EntityRenderer<IlluminatorEntity>(context) {

    companion object {
        val TEXTURE = OtherUtil.modResource("no")
    }

    override fun getTextureLocation(entity: IlluminatorEntity): ResourceLocation {
        return TEXTURE
    }
}