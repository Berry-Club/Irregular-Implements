package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.platform.GlStateManager.DestFactor
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreEnergyInjectorBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import kotlin.math.sin
import kotlin.random.Random

class SpectreEnergyInjectorBlockEntityRenderer(
    val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<SpectreEnergyInjectorBlockEntity> {

    override fun render(
        blockEntity: SpectreEnergyInjectorBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val level = blockEntity.level ?: return
        val f = (level.gameTime + partialTick) / 200f

        val tesselator = Tesselator.getInstance()
        val bufferBuilder = tesselator.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR)

        poseStack.pushPose()
        poseStack.translate(0.5, 0.6, 0.5)

        val f1 = 0f

        val random = Random(432L)

//        GlStateManager.disableTexture2D();
//        GlStateManager.shadeModel(7425);
        RenderSystem.enableBlend()
        RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA)
//        GlStateManager.disableAlpha();
        RenderSystem.disableCull()
        RenderSystem.depthMask(false)

        for (i in 0 until 25) {

//            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
//            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
//            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
//            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
//            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
//            GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);

            val f2 = random.nextFloat() * 0.2f + 0.1f + f1 * 10.0f
            val f3 = random.nextFloat() * 0.1f + f1 * 2.0f

            val red = ((sin(f.toDouble()) / 2 + 0.5) * 80).toInt() + 40

            bufferBuilder.addVertex(0f, 0f, 0f).setColor(0, 0, 0, (255f * (1f - f1)).toInt())
            bufferBuilder.addVertex(-0.866f * f3, f2, (-0.5f * f3)).setColor(red, 230, 226, 0)
            bufferBuilder.addVertex(0.866f * f3, f2, (-0.5f * f3)).setColor(red, 230, 226, 0)
            bufferBuilder.addVertex(0f, f2, f3).setColor(red, 230, 226, 0)
            bufferBuilder.addVertex(-0.866f * f3, f2, (-0.5f * f3)).setColor(red, 230, 226, 0)

//            tessellator.draw();
        }

        poseStack.popPose()
        RenderSystem.depthMask(true)
        RenderSystem.disableCull()
        RenderSystem.disableBlend()
//        GlStateManager.shadeModel(7424);
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.enableTexture2D();
//        GlStateManager.enableAlpha();
//        RenderHelper.enableStandardItemLighting();

    }

    override fun shouldRenderOffScreen(blockEntity: SpectreEnergyInjectorBlockEntity): Boolean {
        return true
    }

}