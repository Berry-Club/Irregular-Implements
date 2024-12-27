package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import kotlin.math.sin

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
        val level = blockEntity.level ?: return

        val time = level.gameTime
        val alpha = (0.5 + 0.5 * sin(time.toDouble() / 10)).toFloat()

        poseStack.pushPose()

        poseStack.translate(0.5, 0.5, 0.5)

        val state = blockEntity.block.defaultBlockState()

        val model = Minecraft.getInstance().blockRenderer.getBlockModel(state)

        val vertexConsumer = bufferSource.getBuffer(RenderType.translucent())

        val quads = model.getQuads(state, null, level.random)       //FIXME: Why is this empty?
        for (quad in quads) {
            vertexConsumer.putBulkData(
                poseStack.last(),
                quad,
                1f, 1f, 1f, alpha,
                packedLight, packedOverlay,
                true
            )
        }

        poseStack.popPose()
    }
}