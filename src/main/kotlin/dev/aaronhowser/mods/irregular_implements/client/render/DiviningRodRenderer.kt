package dev.aaronhowser.mods.irregular_implements.client.render

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.core.BlockPos
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent
import org.lwjgl.opengl.GL11
import java.awt.Color

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    value = [Dist.CLIENT]
)
object DiviningRodRenderer {

    private val positionsToCheck: LinkedHashSet<BlockPos> = linkedSetOf()
    private val indicators: MutableList<Indicator> = mutableListOf()

    private class Indicator(val target: BlockPos, var duration: Int, val color: Color)

    //TODO: Probably laggy, maybe make it only check once a second?
    @SubscribeEvent
    fun afterClientTick(event: ClientTickEvent.Post) {

        val iterator = indicators.iterator()
        while (iterator.hasNext()) {
            val indicator = iterator.next()
            indicator.duration--

            if (indicator.duration <= 0) {
                iterator.remove()
            }
        }

        val player = ClientUtil.localPlayer ?: return
        val playerPos = player.blockPosition()
        val level = player.level()

        val offHandTag = player.offhandItem.get(ModDataComponents.BLOCK_TAG)
        val mainHandTag = player.mainHandItem.get(ModDataComponents.BLOCK_TAG)

        if (offHandTag == null && mainHandTag == null) return

        for (dX in -5..5) for (dY in -5..5) for (dZ in -5..5) {
            val checkedPos = playerPos.offset(dX, dY, dZ)
            if (!level.isLoaded(checkedPos)) continue

            val checkedState = level.getBlockState(checkedPos)

            val matchesOffHand = offHandTag != null && checkedState.`is`(offHandTag)
            val matchesMainHand = mainHandTag != null && checkedState.`is`(mainHandTag)

            if (!matchesOffHand && !matchesMainHand) continue
            if (indicators.any { it.target == checkedPos }) continue

            val indicator = Indicator(
                checkedPos,
                20,
                DiviningRodItem.getColor(checkedState)
            )

            indicators.add(indicator)
        }
    }

    private var vertexBuffer: VertexBuffer? = null

    @SubscribeEvent
    fun onRenderLevel(event: RenderLevelStageEvent) {
        if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return

        if (indicators.isEmpty()) return

        refresh(event.poseStack)
        render(event)
    }

    private fun render(event: RenderLevelStageEvent) {
        RenderSystem.depthMask(false)
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()

        val cameraPos = Minecraft.getInstance().entityRenderDispatcher.camera.position

        val poseStack = event.poseStack
        val vertexBuffer = this.vertexBuffer ?: return

        poseStack.pushPose()

        RenderSystem.setShader(GameRenderer::getPositionColorShader)
        RenderSystem.applyModelViewMatrix()
        RenderSystem.depthFunc(GL11.GL_ALWAYS)

        poseStack.mulPose(event.modelViewMatrix)
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

        vertexBuffer.bind()
        vertexBuffer.drawWithShader(
            poseStack.last().pose(),
            event.projectionMatrix,
            RenderSystem.getShader()!!
        )

        VertexBuffer.unbind()
        RenderSystem.depthFunc(GL11.GL_LEQUAL)

        poseStack.popPose()
        RenderSystem.applyModelViewMatrix()

        RenderSystem.depthMask(true)
    }

    private fun refresh(poseStack: PoseStack) {
        vertexBuffer = VertexBuffer(VertexBuffer.Usage.STATIC)
        val vertexBuffer = vertexBuffer ?: return

        val tesselator = Tesselator.getInstance()
        val buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

        for (indicator in indicators) {
            RenderUtils.renderCube(
                poseStack.last(),
                buffer,
                indicator.target.center.toVector3f(),
                indicator.color.rgb
            )
        }

        val build = buffer.build()
        if (build == null) {
            this.vertexBuffer = null
        } else {
            vertexBuffer.bind()
            vertexBuffer.upload(build)
            VertexBuffer.unbind()
        }
    }

}