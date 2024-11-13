package dev.aaronhowser.mods.irregular_implements.client.renderer.block_entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Blocks

class DiaphanousBER(
    val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<DiaphanousBlockEntity> {

    override fun render(
        blockEntity: DiaphanousBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val blockToRender = (blockEntity.blockToRender.item as? BlockItem)?.block ?: Blocks.STONE
        val alpha = blockEntity.alpha

        //FIXME: Is there no way to render a block with a custom alpha?
        if (alpha > 0f) {
            context.blockRenderDispatcher
                .renderSingleBlock(
                    blockToRender.defaultBlockState(),
                    poseStack,
                    bufferSource,
                    packedLight,
                    packedOverlay
                )
        }
    }
}