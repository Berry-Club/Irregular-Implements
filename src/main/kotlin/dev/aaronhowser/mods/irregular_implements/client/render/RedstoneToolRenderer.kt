package dev.aaronhowser.mods.irregular_implements.client.render

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneWireBlock
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

        refresh(event.poseStack)
        render(event)
    }

    private fun render(event: RenderLevelStageEvent) {
        val poseStack = event.poseStack
        val vertexBuffer = this.vertexBuffer ?: return

        RenderSystem.depthMask(false)
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()

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

    //TODO: Make sure this is working
    private fun refresh(poseStack: PoseStack) {
        vertexBuffer = VertexBuffer(VertexBuffer.Usage.STATIC)

        val tesselator = Tesselator.getInstance()
        val buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

        val startColor = 0x66FF0000
        val endColor = 0x660000FF

        if (mainBlockPos != null) {
            RenderUtils.renderCube(poseStack, buffer, mainBlockPos!!.center, 0.45, startColor)
        }
        if (linkedBlockPos != null) {
            RenderUtils.renderCube(poseStack, buffer, linkedBlockPos!!.center, 0.45, endColor)
        }

        if (mainBlockPos != null && linkedBlockPos != null) {
//            renderLine(poseStack.last(), buffer, mainBlockPos!!.center, linkedBlockPos!!.center, color)
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

    val LAYER_NAME = OtherUtil.modResource("wire_strength")

    fun tryRenderWireStrength(guiGraphics: GuiGraphics, deltaTracker: DeltaTracker) {
        val player = ClientUtil.localPlayer ?: return
        if (!player.isHolding(ModItems.REDSTONE_TOOL.get())) return

        val level = player.level()

        val clipResult = OtherUtil.getPovResult(level, player, player.blockInteractionRange())

        val pos = clipResult.blockPos
        val state = level.getBlockState(pos)

        if (!state.`is`(Blocks.REDSTONE_WIRE)) return

        val strength = state.getValue(RedStoneWireBlock.POWER)

        guiGraphics.drawString(
            Minecraft.getInstance().font,
            strength.toString(),
            guiGraphics.guiWidth() / 2 + 5,
            guiGraphics.guiHeight() / 2 + 5,
            0xFF0000
        )
    }

}