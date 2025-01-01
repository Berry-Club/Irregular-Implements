package dev.aaronhowser.mods.irregular_implements.client.render.item

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.client.render.block.SpectreEnergyInjectorBlockEntityRenderer
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class SpectreIlluminatorBEWLR : BlockEntityWithoutLevelRenderer(
    Minecraft.getInstance().blockEntityRenderDispatcher,
    Minecraft.getInstance().entityModels
) {

    companion object {
        val clientItemExtensions = object : IClientItemExtensions {
            val BEWLR = SpectreIlluminatorBEWLR()

            override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
                return BEWLR
            }
        }
    }

    override fun renderByItem(
        stack: ItemStack,
        displayContext: ItemDisplayContext,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        poseStack.pushPose()
        poseStack.translate(0.5, 0.5, 0.5)

        val time = (ClientUtil.localPlayer?.tickCount ?: 0) / 200f

        SpectreEnergyInjectorBlockEntityRenderer.renderRays(
            poseStack = poseStack,
            time = time,
            vertexConsumer = buffer.getBuffer(RenderType.dragonRays()),
        )

        poseStack.popPose()
    }

}