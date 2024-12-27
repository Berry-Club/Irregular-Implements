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

    //TODO: Maybe instead of pulsating transparency, add an icon to the corner?
    override fun renderByItem(
        stack: ItemStack,
        displayContext: ItemDisplayContext,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val block = stack.get(ModDataComponents.BLOCK) ?: Blocks.STONE
        val itemRenderer = Minecraft.getInstance().itemRenderer

        poseStack.pushPose()
        poseStack.translate(0.5, 0.5, 0.5)

        itemRenderer.renderStatic(
            block.asItem().defaultInstance,
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