package dev.aaronhowser.mods.irregular_implements.block.renderer

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import org.joml.Matrix4f

class CustomCraftingTableBER(
    val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<CustomCraftingTableBlockEntity> {

    companion object {
        val TOP_TEXTURE = OtherUtil.modResource("textures/block/crafting_table/top.png")
        val SIDE_TEXTURE = OtherUtil.modResource("textures/block/crafting_table/side.png")
        val FRONT_TEXTURE = OtherUtil.modResource("textures/block/crafting_table/front.png")
    }

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

        context.blockRenderDispatcher
            .renderSingleBlock(
                baseBlock.defaultBlockState(),
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay
            )

        renderTop(
            bufferSource.getBuffer(RenderType.entityTranslucent(TOP_TEXTURE)),
            poseStack.last().pose(),
            LightTexture.FULL_BRIGHT,
            packedOverlay
        )

        poseStack.popPose()
    }

    private fun renderTop(
        vertexConsumer: VertexConsumer,
        matrix: Matrix4f,
        packedLight: Int,
        packedOverlay: Int,
    ) {
        val xMin = 0.0f
        val xMax = 1.0f
        val zMin = 0.0f
        val zMax = 1.0f
        val yTop = 1.01f

        vertexConsumer
            .addVertex(matrix, xMin, yTop, zMin)
            .setColor(1.0f, 1.0f, 1.0f, 1.0f)
            .setUv(1f, 0f)
            .setOverlay(packedOverlay)
            .setLight(packedLight)
            .setNormal(0.0f, 1.0f, 0.0f)

        vertexConsumer
            .addVertex(matrix, xMin, yTop, zMax)
            .setColor(1.0f, 1.0f, 1.0f, 1.0f)
            .setUv(1f, 0f)
            .setOverlay(packedOverlay)
            .setLight(packedLight)
            .setNormal(0.0f, 1.0f, 0.0f)

        vertexConsumer
            .addVertex(matrix, xMax, yTop, zMax)
            .setColor(1.0f, 1.0f, 1.0f, 1.0f)
            .setUv(1f, 0f)
            .setOverlay(packedOverlay)
            .setLight(packedLight)
            .setNormal(0.0f, 1.0f, 0.0f)

        vertexConsumer
            .addVertex(matrix, xMax, yTop, zMin)
            .setColor(1.0f, 1.0f, 1.0f, 1.0f)
            .setUv(1f, 0f)
            .setOverlay(packedOverlay)
            .setLight(packedLight)
            .setNormal(0.0f, 1.0f, 0.0f)

    }


}