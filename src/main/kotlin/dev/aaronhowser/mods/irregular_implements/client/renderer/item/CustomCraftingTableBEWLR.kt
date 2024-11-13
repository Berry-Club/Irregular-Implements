package dev.aaronhowser.mods.irregular_implements.client.renderer.item

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

class CustomCraftingTableBEWLR : BlockEntityWithoutLevelRenderer(
    Minecraft.getInstance().blockEntityRenderDispatcher,
    Minecraft.getInstance().entityModels
) {

    override fun renderByItem(
        stack: ItemStack,
        displayContext: ItemDisplayContext,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val baseBlock = Items.BIRCH_PLANKS as BlockItem

        poseStack.pushPose()

        poseStack.scale(0.999f, 0.999f, 0.999f)
        poseStack.translate(0.0005, 0.0005, 0.0005)

        Minecraft.getInstance().itemRenderer.render(
            baseBlock.defaultInstance,
            displayContext,
            false,
            poseStack,
            buffer,
            packedLight,
            packedOverlay,
            Minecraft.getInstance().itemRenderer.getModel(baseBlock.defaultInstance, null, null, 0)
        )

        poseStack.popPose()
    }

}