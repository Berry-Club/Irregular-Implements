package dev.aaronhowser.mods.irregular_implements.client.renderer.item

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

class DiaphanousBEWLR : BlockEntityWithoutLevelRenderer(
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
        val baseStack = stack.get(ModDataComponents.ITEMSTACK)?.itemStack ?: return

        Minecraft.getInstance().itemRenderer.render(
            baseStack,
            displayContext,
            false,
            poseStack,
            buffer,
            packedLight,
            packedOverlay,
            Minecraft.getInstance().itemRenderer.getModel(baseStack, null, null, 0)
        )

    }

}