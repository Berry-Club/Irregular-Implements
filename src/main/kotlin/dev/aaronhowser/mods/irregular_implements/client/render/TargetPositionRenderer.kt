package dev.aaronhowser.mods.irregular_implements.client.render

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
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

object TargetPositionRenderer {

	private val positions: MutableList<BlockPos> = mutableListOf()

	fun afterClientTick(event: ClientTickEvent.Post) {
		positions.clear()

		val player = ClientUtil.localPlayer ?: return

		val mainHandItemLocation = player.mainHandItem.get(ModDataComponents.LOCATION)
		val offHandItemLocation = player.offhandItem.get(ModDataComponents.LOCATION)

		val level = player.level()

		if (mainHandItemLocation != null && mainHandItemLocation.dimension == level.dimension()) {
			positions.add(mainHandItemLocation.blockPos)
		}

		if (offHandItemLocation != null && offHandItemLocation.dimension == level.dimension()) {
			positions.add(offHandItemLocation.blockPos)
		}

	}

	private var vertexBuffer: VertexBuffer? = null

	fun onRenderLevel(event: RenderLevelStageEvent) {
		if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return

		refresh(event.poseStack)
		render(event)
	}

	private fun refresh(poseStack: PoseStack) {
		vertexBuffer = VertexBuffer(VertexBuffer.Usage.STATIC)
		val vertexBuffer = vertexBuffer ?: return

		val tesselator = Tesselator.getInstance()
		val buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

		for (position in positions) {
			RenderUtils.renderCube(
				poseStack,
				buffer,
				position.x,
				position.y,
				position.z,
				1,
				1,
				1,
				0x3200FF00  //TODO: Change color?
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

	private fun render(event: RenderLevelStageEvent) {
		val cameraPos = Minecraft.getInstance().entityRenderDispatcher.camera.position
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

}