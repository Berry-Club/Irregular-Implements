package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.world.level.levelgen.XoroshiroRandomSource
import net.neoforged.neoforge.client.model.data.ModelData
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
        val time = level.gameTime + partialTick

        val alpha = (0.65 + 0.25 * sin(time.toDouble() / 20.0)).toFloat()

        poseStack.pushPose()

        val state = blockEntity.renderedBlock.defaultBlockState()
        val model = Minecraft.getInstance().blockRenderer.getBlockModel(state)
        val vertexConsumer = bufferSource.getBuffer(RenderType.translucent())

        for (direction in Direction.entries) {
            val quads = model.getQuads(state, direction, XoroshiroRandomSource(0, 0), ModelData.EMPTY, null)
            for (quad in quads) {
                vertexConsumer.putBulkData(
                    poseStack.last(),
                    quad,
                    1f, 1f, 1f, alpha,
                    packedLight, packedOverlay,
                    true
                )
            }
        }

        poseStack.popPose()
    }
}