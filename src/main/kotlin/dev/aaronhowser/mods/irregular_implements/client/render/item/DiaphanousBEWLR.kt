package dev.aaronhowser.mods.irregular_implements.client.render.item

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
import kotlin.math.sin

class DiaphanousBEWLR : BlockEntityWithoutLevelRenderer(
    Minecraft.getInstance().blockEntityRenderDispatcher,
    Minecraft.getInstance().entityModels
) {

    companion object {
        val clientItemExtensions = object : IClientItemExtensions {
            val BEWLR = DiaphanousBEWLR()

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

        if (displayContext == ItemDisplayContext.GUI) {
            val time = Minecraft.getInstance().level?.gameTime ?: 0
            val modelScale = 0.9625f + 0.0375f * sin(time.toFloat() / 2.5f)
            poseStack.scale(modelScale, modelScale, modelScale)
        }

        val blockToRender = stack.get(ModDataComponents.BLOCK) ?: Blocks.STONE
        val itemRenderer = Minecraft.getInstance().itemRenderer

        itemRenderer.renderStatic(
            blockToRender.asItem().defaultInstance,
            displayContext,
            packedLight,
            packedOverlay,
            poseStack,
            buffer,
            null,
            0,
        )

        poseStack.popPose()
    }

}