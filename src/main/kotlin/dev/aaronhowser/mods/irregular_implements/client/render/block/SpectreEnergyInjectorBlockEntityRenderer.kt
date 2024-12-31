package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreEnergyInjectorBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.util.FastColor
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.min
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
        val time = 10 + partialTick

        poseStack.pushPose()
        poseStack.translate(0.5f, 0.6f, 0.5f)

        renderRays(poseStack, time, bufferSource.getBuffer(RenderType.dragonRays()))
        renderRays(poseStack, time, bufferSource.getBuffer(RenderType.dragonRaysDepth()))

        poseStack.popPose()
    }

    fun renderRays(poseStack: PoseStack, time: Float, vertexConsumer: VertexConsumer) {
        poseStack.pushPose()

        val f = min(
            if (time > 0.8f) (time - 0.8f) / 2 else 0f,
            1f
        )

        val i = FastColor.ARGB32.colorFromFloat(1.0f - f, 1.0f, 1.0f, 1.0f)

        val randomSource = RandomSource.create(432L)
        val vector3f = Vector3f()
        val vector3f1 = Vector3f()
        val vector3f2 = Vector3f()
        val vector3f3 = Vector3f()
        val quaternionf = Quaternionf()

        val k = Mth.floor(
            (time + time * time) / 2f * 60f
        )

        for (l in 0 until k) {
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

            val f1 = randomSource.nextFloat() * 20.0f + 5.0f + f * 10.0f
            val f2 = randomSource.nextFloat() * 2.0f + 1.0f + f * 2.0f

            vector3f1.set(-HALF_SQRT_3 * f2, f1, -0.5F * f2)
            vector3f2.set(HALF_SQRT_3 * f2, f1, -0.5F * f2)
            vector3f3.set(0.0F, f1, f2)

            val pose = poseStack.last()

            vertexConsumer.addVertex(pose, vector3f).setColor(i)
            vertexConsumer.addVertex(pose, vector3f1).setColor(16711935)
            vertexConsumer.addVertex(pose, vector3f2).setColor(16711935)
            vertexConsumer.addVertex(pose, vector3f).setColor(i)
            vertexConsumer.addVertex(pose, vector3f2).setColor(16711935)
            vertexConsumer.addVertex(pose, vector3f3).setColor(16711935)
            vertexConsumer.addVertex(pose, vector3f).setColor(i)
            vertexConsumer.addVertex(pose, vector3f3).setColor(16711935)
            vertexConsumer.addVertex(pose, vector3f1).setColor(16711935)
        }

        poseStack.popPose()
    }

    override fun shouldRenderOffScreen(blockEntity: SpectreEnergyInjectorBlockEntity): Boolean {
        return true
    }

}