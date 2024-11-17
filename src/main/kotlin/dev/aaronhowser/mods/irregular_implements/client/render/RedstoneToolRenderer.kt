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
import net.minecraft.world.phys.Vec3
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent
import org.lwjgl.opengl.GL11

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    value = [Dist.CLIENT]
)
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
        this.mainBlockPos = toolBlockPos

        val toolBlockEntity = player.level().getBlockEntity(toolLocation.blockPos) as? RedstoneToolLinkable ?: return
        val linkedPos = toolBlockEntity.linkedPos ?: return

        this.linkedBlockPos = linkedPos
    }

    private var vertexBuffer: VertexBuffer? = null

    private lateinit var cameraPos: Vec3

    @SubscribeEvent
    fun onRenderLevel(event: RenderLevelStageEvent) {
        if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return
        if (ClientUtil.localPlayer == null) return
        if (this.mainBlockPos == null) return

        cameraPos = Minecraft.getInstance().entityRenderDispatcher.camera.position

        refresh()
        render(event)
    }

    private fun render(event: RenderLevelStageEvent) {
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
        val buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

        val alpha = 0.45f
        val red = 1f
        val green = 0f
        val blue = 0f

        if (mainBlockPos != null) {
            renderCube(buffer, mainBlockPos!!.center, alpha, red, green, blue)
        }
        if (linkedBlockPos != null) {
            renderCube(buffer, linkedBlockPos!!.center, alpha, red, green, blue)
        }

        if (mainBlockPos != null && linkedBlockPos != null) {
            renderLine(buffer, mainBlockPos!!.center, linkedBlockPos!!.center, alpha, red, green, blue)
        }

        val build = buffer.build()
        if (build == null) {
            vertexBuffer = null
        } else {
            vertexBuffer!!.bind()
            vertexBuffer!!.upload(build)
            VertexBuffer.unbind()
        }
    }

    private fun drawQuad(
        buffer: BufferBuilder,
        x1: Float, y1: Float, z1: Float,
        x2: Float, y2: Float, z2: Float,
        x3: Float, y3: Float, z3: Float,
        x4: Float, y4: Float, z4: Float,
        alpha: Float,
        red: Float,
        green: Float,
        blue: Float
    ) {
        // Calculate the center of the quad
        val centerX = (x1 + x2 + x3 + x4) / 4
        val centerY = (y1 + y2 + y3 + y4) / 4
        val centerZ = (z1 + z2 + z3 + z4) / 4

        // Calculate the normal of the quad
        val edge1X = x2 - x1
        val edge1Y = y2 - y1
        val edge1Z = z2 - z1

        val edge2X = x3 - x1
        val edge2Y = y3 - y1
        val edge2Z = z3 - z1

        val normalX = edge1Y * edge2Z - edge1Z * edge2Y
        val normalY = edge1Z * edge2X - edge1X * edge2Z
        val normalZ = edge1X * edge2Y - edge1Y * edge2X

        // Vector from the quad center to the camera
        val toCameraX = cameraPos.x.toFloat() - centerX
        val toCameraY = cameraPos.y.toFloat() - centerY
        val toCameraZ = cameraPos.z.toFloat() - centerZ

        // Dot product to determine if the normal faces the camera
        val dotProduct = normalX * toCameraX + normalY * toCameraY + normalZ * toCameraZ

        if (dotProduct > 0) {
            // Normal faces away from the camera, use the original order
            buffer.addVertex(x1, y1, z1).setColor(red, green, blue, alpha)
            buffer.addVertex(x2, y2, z2).setColor(red, green, blue, alpha)
            buffer.addVertex(x3, y3, z3).setColor(red, green, blue, alpha)
            buffer.addVertex(x4, y4, z4).setColor(red, green, blue, alpha)
        } else {
            // Normal faces the camera, reverse the order
            buffer.addVertex(x4, y4, z4).setColor(red, green, blue, alpha)
            buffer.addVertex(x3, y3, z3).setColor(red, green, blue, alpha)
            buffer.addVertex(x2, y2, z2).setColor(red, green, blue, alpha)
            buffer.addVertex(x1, y1, z1).setColor(red, green, blue, alpha)
        }
    }

    private fun renderLine(
        buffer: BufferBuilder,
        startPos: Vec3,
        endPos: Vec3,
        alpha: Float,
        red: Float,
        green: Float,
        blue: Float
    ) {

        val endPointRadius = 0.05f

        val x1 = startPos.x.toFloat()
        val y1 = startPos.y.toFloat()
        val z1 = startPos.z.toFloat()

        val x2 = startPos.x.toFloat()
        val y2 = startPos.y.toFloat() - endPointRadius
        val z2 = startPos.z.toFloat()

        val x3 = endPos.x.toFloat()
        val y3 = endPos.y.toFloat() - endPointRadius
        val z3 = endPos.z.toFloat()

        val x4 = endPos.x.toFloat()
        val y4 = endPos.y.toFloat()
        val z4 = endPos.z.toFloat()

        drawQuad(buffer, x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, alpha, red, green, blue)
    }

    private fun renderCube(
        buffer: BufferBuilder,
        center: Vec3,
        alpha: Float,
        red: Float,
        green: Float,
        blue: Float
    ) {
        val cubeRadius = 0.4f

        val x1 = center.x.toFloat() - cubeRadius
        val y1 = center.y.toFloat() - cubeRadius
        val z1 = center.z.toFloat() - cubeRadius

        val x2 = center.x.toFloat() + cubeRadius
        val y2 = center.y.toFloat() - cubeRadius
        val z2 = center.z.toFloat() - cubeRadius

        val x3 = center.x.toFloat() + cubeRadius
        val y3 = center.y.toFloat() + cubeRadius
        val z3 = center.z.toFloat() - cubeRadius

        val x4 = center.x.toFloat() - cubeRadius
        val y4 = center.y.toFloat() + cubeRadius
        val z4 = center.z.toFloat() - cubeRadius

        val x5 = center.x.toFloat() - cubeRadius
        val y5 = center.y.toFloat() - cubeRadius
        val z5 = center.z.toFloat() + cubeRadius

        val x6 = center.x.toFloat() + cubeRadius
        val y6 = center.y.toFloat() - cubeRadius
        val z6 = center.z.toFloat() + cubeRadius

        val x7 = center.x.toFloat() + cubeRadius
        val y7 = center.y.toFloat() + cubeRadius
        val z7 = center.z.toFloat() + cubeRadius

        val x8 = center.x.toFloat() - cubeRadius
        val y8 = center.y.toFloat() + cubeRadius
        val z8 = center.z.toFloat() + cubeRadius

        drawQuad(buffer, x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, alpha, red, green, blue)
        drawQuad(buffer, x5, y5, z5, x6, y6, z6, x7, y7, z7, x8, y8, z8, alpha, red, green, blue)
        drawQuad(buffer, x1, y1, z1, x2, y2, z2, x6, y6, z6, x5, y5, z5, alpha, red, green, blue)
        drawQuad(buffer, x2, y2, z2, x3, y3, z3, x7, y7, z7, x6, y6, z6, alpha, red, green, blue)
        drawQuad(buffer, x3, y3, z3, x4, y4, z4, x8, y8, z8, x7, y7, z7, alpha, red, green, blue)
        drawQuad(buffer, x4, y4, z4, x1, y1, z1, x5, y5, z5, x8, y8, z8, alpha, red, green, blue)
    }

}