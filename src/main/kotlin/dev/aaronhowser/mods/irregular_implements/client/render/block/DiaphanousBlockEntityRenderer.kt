package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.neoforged.neoforge.client.model.data.ModelData

class DiaphanousBlockEntityRenderer(
    context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<DiaphanousBlockEntity> {

    override fun render(
        blockEntity: DiaphanousBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        poseStack.pushPose()

        val state = blockEntity.renderedBlock.defaultBlockState()
        val model = Minecraft.getInstance().blockRenderer.getBlockModel(state)
        val vertexConsumer = bufferSource.getBuffer(RenderType.translucent())

        val quads = model.getQuads(state, Direction.UP, blockEntity.level!!.random, ModelData.EMPTY, null)
        for (quad in quads) {
            vertexConsumer.putBulkData(
                poseStack.last(),
                quad,
                1f, 1f, 1f, 0.75f,
                packedLight, packedOverlay,
                true
            )
        }

        poseStack.popPose()
    }
}