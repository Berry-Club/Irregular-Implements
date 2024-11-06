package dev.aaronhowser.mods.irregular_implements.block.renderer

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.MultiBufferSource
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
            poseStack.last().pose(),
            packedLight
        )

        poseStack.popPose()
    }

    private fun renderTop(
        matrix: Matrix4f,
        packedLight: Int,
    ) {
        val xMin = 0.0f
        val xMax = 1.0f
        val zMin = 0.0f
        val zMax = 1.0f
        val yTop = 1.2f

        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, TOP_TEXTURE)
        RenderSystem.enableBlend()

        val lightFloat = packedLight.toFloat()
        RenderSystem.setShaderColor(lightFloat, lightFloat, lightFloat, 1f)

        val bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)

        bufferBuilder.addVertex(matrix, xMin, yTop, zMax).setUv(0f, 1f)
        bufferBuilder.addVertex(matrix, xMax, yTop, zMax).setUv(0f, 1f)
        bufferBuilder.addVertex(matrix, xMax, yTop, zMin).setUv(0f, 1f)
        bufferBuilder.addVertex(matrix, xMin, yTop, zMin).setUv(0f, 1f)

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow())
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.disableBlend()
    }


}