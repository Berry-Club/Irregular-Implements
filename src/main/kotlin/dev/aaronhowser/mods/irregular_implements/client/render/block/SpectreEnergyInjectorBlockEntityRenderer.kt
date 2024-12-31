package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreEnergyInjectorBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.util.FastColor
import net.minecraft.util.RandomSource
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.sqrt

class SpectreEnergyInjectorBlockEntityRenderer(
    val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<SpectreEnergyInjectorBlockEntity> {


    companion object {
        private val HALF_SQRT_3: Float = (sqrt(3.0) / 2.0).toFloat()
    }

    override fun render(
        blockEntity: SpectreEnergyInjectorBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val time = ((blockEntity.level?.gameTime ?: 0) + partialTick) / 200f

        poseStack.pushPose()
        poseStack.translate(0.5f, 0.6f, 0.5f)

        renderRays(poseStack, time, bufferSource.getBuffer(RenderType.dragonRays()))
        renderRays(poseStack, time, bufferSource.getBuffer(RenderType.dragonRaysDepth()))

        poseStack.popPose()
    }

    fun renderRays(poseStack: PoseStack, time: Float, vertexConsumer: VertexConsumer) {
        poseStack.pushPose()

        // 1 is fully transparent, 0 is fully opaque
        val transparency = 0.2f

        val packedColor = FastColor.ARGB32.colorFromFloat(1.0f - transparency, 0.5f, 0.5f, 0.5f)

        val randomSource = RandomSource.create(432L)
        val vector3f = Vector3f()
        val vector3f1 = Vector3f()
        val vector3f2 = Vector3f()
        val vector3f3 = Vector3f()
        val quaternionf = Quaternionf()

        val amountRays = 15

        for (rayIndex in 0 until amountRays) {
            quaternionf
                .rotateXYZ(
                    randomSource.nextFloat() * (Math.PI * 2).toFloat(),
                    randomSource.nextFloat() * (Math.PI * 2).toFloat(),
                    randomSource.nextFloat() * (Math.PI * 2).toFloat()
                )
                .rotateXYZ(
                    randomSource.nextFloat() * (Math.PI * 2).toFloat(),
                    randomSource.nextFloat() * (Math.PI * 2).toFloat(),
                    randomSource.nextFloat() * (Math.PI * 2).toFloat() + time * (Math.PI / 2).toFloat()
                )

            poseStack.mulPose(quaternionf)

            val rayLength = 0.325f
            val rayWidth = 0.15f

            vector3f1.set(-HALF_SQRT_3 * rayWidth, rayLength, -0.5F * rayWidth)
            vector3f2.set(HALF_SQRT_3 * rayWidth, rayLength, -0.5F * rayWidth)
            vector3f3.set(0.0F, rayLength, rayWidth)

            val pose = poseStack.last()
            val color = 0x002244

            vertexConsumer.addVertex(pose, vector3f).setColor(packedColor)
            vertexConsumer.addVertex(pose, vector3f1).setColor(color)
            vertexConsumer.addVertex(pose, vector3f2).setColor(color)

            vertexConsumer.addVertex(pose, vector3f).setColor(packedColor)
            vertexConsumer.addVertex(pose, vector3f2).setColor(color)
            vertexConsumer.addVertex(pose, vector3f3).setColor(color)

            vertexConsumer.addVertex(pose, vector3f).setColor(packedColor)
            vertexConsumer.addVertex(pose, vector3f3).setColor(color)
            vertexConsumer.addVertex(pose, vector3f1).setColor(color)
        }

        poseStack.popPose()
    }

    override fun shouldRenderOffScreen(blockEntity: SpectreEnergyInjectorBlockEntity): Boolean {
        return true
    }

}