package dev.aaronhowser.mods.irregular_implements.client.render

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.core.BlockPos
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent
import org.lwjgl.opengl.GL11

@EventBusSubscriber(modid = IrregularImplements.ID)
object RedstoneToolRenderer {

    private var mainBlockPos: BlockPos? = null
    private var linkedBlockPos: BlockPos? = null

    @SubscribeEvent
    fun afterClientTick(event: ClientTickEvent.Post) {
        this.mainBlockPos = null
        this.linkedBlockPos = null

        val player = ClientUtil.localPlayer ?: return

        val itemInHand = player.mainHandItem
        if (!itemInHand.`is`(ModItems.REDSTONE_TOOL)) return

        val toolLocation = itemInHand.get(ModDataComponents.LOCATION) ?: return

        if (toolLocation.dimension != player.level().dimension()) return

        val toolBlockPos = toolLocation.blockPos

        val toolBlockEntity = player.level().getBlockEntity(toolLocation.blockPos) as? RedstoneToolLinkable ?: return
        val linkedPos = toolBlockEntity.linkedPos ?: return

        this.mainBlockPos = toolBlockPos
        this.linkedBlockPos = linkedPos
    }

    private var vertexBuffer: VertexBuffer? = null

    @SubscribeEvent
    fun onRenderLevel(event: RenderLevelStageEvent) {
        if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return
        if (ClientUtil.localPlayer == null) return
        if (this.mainBlockPos == null || this.linkedBlockPos == null) return

        if (vertexBuffer == null) refresh()

        render(event)
    }

    private fun render(event: RenderLevelStageEvent) {
        val cameraPos = Minecraft.getInstance().entityRenderDispatcher.camera.position

        RenderSystem.depthMask(false)
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()

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
    }

    private fun refresh() {
        vertexBuffer = VertexBuffer(VertexBuffer.Usage.STATIC)

        val tesselator = Tesselator.getInstance()
        val buffer = tesselator.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR)

        val alpha = 1f
        val red = 1f
        val green = 0f
        val blue = 0f

        if (mainBlockPos != null) renderCube(buffer, mainBlockPos!!, alpha, red, green, blue)
        if (linkedBlockPos != null) renderCube(buffer, linkedBlockPos!!, alpha, red, green, blue)

        if (mainBlockPos != null && linkedBlockPos != null) {
            renderLine(buffer, mainBlockPos!!, linkedBlockPos!!, alpha, red, green, blue)
        }
    }

    //FIXME: Lines are 1 pixel wide. Maybe move this to be quads?
    private fun renderLine(
        buffer: BufferBuilder,
        x1: Float, y1: Float, z1: Float,
        x2: Float, y2: Float, z2: Float,
        alpha: Float,
        red: Float,
        green: Float,
        blue: Float
    ) {
        buffer.addVertex(x1, y1, z1).setColor(red, green, blue, alpha)
        buffer.addVertex(x2, y2, z2).setColor(red, green, blue, alpha)
    }

    private fun renderLine(
        buffer: BufferBuilder,
        blockPos1: BlockPos,
        blockPos2: BlockPos,
        alpha: Float,
        red: Float,
        green: Float,
        blue: Float
    ) {
        renderLine(
            buffer,
            blockPos1.x.toFloat(),
            blockPos1.y.toFloat(),
            blockPos1.z.toFloat(),
            blockPos2.x.toFloat(),
            blockPos2.y.toFloat(),
            blockPos2.z.toFloat(),
            alpha,
            red,
            green,
            blue
        )
    }

    private fun renderCube(
        buffer: BufferBuilder,
        blockPos: BlockPos,
        alpha: Float,
        red: Float,
        green: Float,
        blue: Float
    ) {
        val cubeSize = 0.5f
        val x1 = blockPos.x - cubeSize / 2
        val y1 = blockPos.y - cubeSize / 2
        val z1 = blockPos.z - cubeSize / 2
        val x2 = blockPos.x + cubeSize / 2
        val y2 = blockPos.y + cubeSize / 2
        val z2 = blockPos.z + cubeSize / 2

        renderLine(buffer, x1, y1, z1, x2, y1, z1, alpha, red, green, blue)
        renderLine(buffer, x1, y1, z1, x1, y2, z1, alpha, red, green, blue)
        renderLine(buffer, x1, y1, z1, x1, y1, z2, alpha, red, green, blue)

        renderLine(buffer, x2, y1, z1, x2, y2, z1, alpha, red, green, blue)
        renderLine(buffer, x2, y1, z1, x2, y1, z2, alpha, red, green, blue)
        renderLine(buffer, x1, y2, z1, x2, y2, z1, alpha, red, green, blue)

        renderLine(buffer, x1, y2, z1, x1, y2, z2, alpha, red, green, blue)
        renderLine(buffer, x1, y1, z2, x2, y1, z2, alpha, red, green, blue)
        renderLine(buffer, x1, y1, z2, x1, y2, z2, alpha, red, green, blue)

        renderLine(buffer, x1, y2, z2, x2, y2, z2, alpha, red, green, blue)
        renderLine(buffer, x2, y1, z2, x2, y2, z2, alpha, red, green, blue)
        renderLine(buffer, x2, y2, z1, x2, y2, z2, alpha, red, green, blue)
    }

}