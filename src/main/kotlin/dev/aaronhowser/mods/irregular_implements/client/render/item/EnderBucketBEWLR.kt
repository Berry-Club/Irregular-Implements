package dev.aaronhowser.mods.irregular_implements.client.render.item

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class EnderBucketBEWLR : BlockEntityWithoutLevelRenderer(
    Minecraft.getInstance().blockEntityRenderDispatcher,
    Minecraft.getInstance().entityModels
) {

    companion object {
        val clientItemExtensions = object : IClientItemExtensions {
            val BEWLR = EnderBucketBEWLR()

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

        val fluidContent = stack.get(ModDataComponents.SIMPLE_FLUID_CONTENT)

        if (fluidContent != null) {
            val fluid = fluidContent.fluid
            val sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
        }

    }

}