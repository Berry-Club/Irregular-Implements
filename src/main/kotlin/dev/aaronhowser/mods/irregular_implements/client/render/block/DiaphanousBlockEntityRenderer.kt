package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.world.level.levelgen.XoroshiroRandomSource
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
        val level = blockEntity.level ?: return

        val alpha = 1f

        poseStack.pushPose()

        val stateToRender = blockEntity.renderedBlock.defaultBlockState()
        val model = Minecraft.getInstance().blockRenderer.getBlockModel(stateToRender)
        val vertexConsumer = bufferSource.getBuffer(RenderType.translucent())

        val color = Minecraft.getInstance().blockColors.getColor(stateToRender, level, blockEntity.blockPos, 1)
        val red = ((color shr 16) and 0xFF) / 255f
        val green = ((color shr 8) and 0xFF) / 255f
        val blue = (color and 0xFF) / 255f

        for (direction in Direction.entries) {
            val shouldSkip = level
                .getBlockState(blockEntity.blockPos.relative(direction))
                .`is`(ModBlocks.DIAPHANOUS_BLOCK)

            if (shouldSkip) continue

            val quads = model.getQuads(
                stateToRender,
                direction,
                XoroshiroRandomSource(0, 0),
                ModelData.EMPTY,
                null
            )

            for (quad in quads) {
                vertexConsumer.putBulkData(
                    poseStack.last(),
                    quad,
                    red, green, blue, alpha,
                    packedLight, packedOverlay,
                    true
                )
            }
        }

        poseStack.popPose()
    }
}