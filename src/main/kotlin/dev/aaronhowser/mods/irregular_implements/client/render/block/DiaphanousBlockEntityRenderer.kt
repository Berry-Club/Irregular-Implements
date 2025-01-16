package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.util.Mth
import net.minecraft.world.level.levelgen.XoroshiroRandomSource
import net.neoforged.neoforge.client.model.data.ModelData
import kotlin.math.cos

class DiaphanousBlockEntityRenderer(
    val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<DiaphanousBlockEntity> {

    override fun getViewDistance(): Int {
        return 256
    }

    override fun render(
        blockEntity: DiaphanousBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val level = blockEntity.level ?: return

        val player = ClientUtil.localPlayer ?: return
        val distance = player.distanceToSqr(blockEntity.blockPos.center)

        val clampedDistance = (distance - 8).coerceIn(0.0, 25.0).toFloat()
        val cosValue = cos(Mth.PI * clampedDistance / 25)
        val baseAlpha = -0.5f * (cosValue - 1)

        val alpha = if (blockEntity.isInverted) 1 - baseAlpha else baseAlpha

        poseStack.pushPose()

        val stateToRender = blockEntity.renderedBlockState
        val model = context.blockRenderDispatcher.getBlockModel(stateToRender)
        val vertexConsumer = bufferSource.getBuffer(RenderType.translucent())

        val color = Minecraft.getInstance().blockColors.getColor(stateToRender, level, blockEntity.blockPos, 1)
        val red = ((color shr 16) and 0xFF) / 255f
        val green = ((color shr 8) and 0xFF) / 255f
        val blue = (color and 0xFF) / 255f

        for (direction in Direction.entries) {
            val blockEntityThere = level.getBlockEntity(blockEntity.blockPos.relative(direction)) as? DiaphanousBlockEntity
            val shouldSkip = blockEntityThere?.isInverted == blockEntity.isInverted
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