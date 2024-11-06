package dev.aaronhowser.mods.irregular_implements.block.renderer

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class CustomCraftingTableBlockEntityRenderer(
    val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<CustomCraftingTableBlockEntity> {
    override fun render(
        blockEntity: CustomCraftingTableBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val baseBlock = blockEntity.baseBlock

        poseStack.pushPose()

        //FIXME: This is obviously a horrible way of doing it. I'm just trying to get it to work for now.
        poseStack.scale(0.999f, 0.999f, 0.999f)
        poseStack.translate(0.0005, 0.0005, 0.0005)

        context.blockRenderDispatcher
            .renderSingleBlock(
                baseBlock.defaultBlockState(),
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay
            )

        poseStack.popPose()
    }
}