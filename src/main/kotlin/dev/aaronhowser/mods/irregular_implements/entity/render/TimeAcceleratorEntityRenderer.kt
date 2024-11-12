package dev.aaronhowser.mods.irregular_implements.entity.render

import dev.aaronhowser.mods.irregular_implements.entity.TimeAcceleratorEntity
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class TimeAcceleratorEntityRenderer(
    context: EntityRendererProvider.Context
) : EntityRenderer<TimeAcceleratorEntity>(context) {

    override fun getTextureLocation(entity: TimeAcceleratorEntity): ResourceLocation {
        return ResourceLocation.withDefaultNamespace("cow")
    }

    override fun shouldRender(livingEntity: TimeAcceleratorEntity, camera: Frustum, camX: Double, camY: Double, camZ: Double): Boolean {
        return false
    }

}