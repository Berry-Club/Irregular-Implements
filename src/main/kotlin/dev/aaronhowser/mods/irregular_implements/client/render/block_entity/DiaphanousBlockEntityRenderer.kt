package dev.aaronhowser.mods.irregular_implements.client.render.block_entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.util.Mth
import net.minecraft.world.level.levelgen.XoroshiroRandomSource
import net.neoforged.neoforge.client.model.data.ModelData
import kotlin.math.cos

class DiaphanousBlockEntityRenderer(
	val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<DiaphanousBlockEntity> {

	override fun getViewDistance(): Int {
		return 256
	}

	override fun render(
		blockEntity: DiaphanousBlockEntity,
		partialTick: Float,
		poseStack: PoseStack,
		bufferSource: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		val level = blockEntity.level ?: return

		val player = ClientUtil.localPlayer ?: return
		val distance = player.getPosition(partialTick).distanceToSqr(blockEntity.blockPos.center)

		val clampedDistance = (distance - 8).coerceIn(0.0, 25.0).toFloat()
		val cosValue = cos(Mth.PI * clampedDistance / 25)
		val baseAlpha = -0.5f * (cosValue - 1)

		val diaphBlockAlpha = if (blockEntity.isInverted) 1 - baseAlpha else baseAlpha

		poseStack.pushPose()

		val stateToRender = blockEntity.renderedBlockState
		val model = context.blockRenderDispatcher.getBlockModel(stateToRender)
		val vertexConsumer = bufferSource.getBuffer(RenderType.translucent())

		val myState = blockEntity.blockState

		for (direction in Direction.entries) {
			val posThere = blockEntity.blockPos.relative(direction)

			val blockEntityThere = level.getBlockEntity(posThere) as? DiaphanousBlockEntity
			val blockStateThere = level.getBlockState(posThere)

			val shouldSkip = blockEntityThere?.isInverted == blockEntity.isInverted
					|| blockStateThere.hidesNeighborFace(level, posThere, myState, direction.opposite)

			if (shouldSkip) continue

			val quads = model.getQuads(
				stateToRender,
				direction,
				XoroshiroRandomSource(0, 0),
				ModelData.EMPTY,
				null
			)

			val blockColors = Minecraft.getInstance().blockColors

			for (quad in quads) {
				val tintIndex = quad.tintIndex

				val color = if (tintIndex == -1) {
					0xFFFFFFFF.toInt()
				} else {
					blockColors.getColor(
						stateToRender, level, blockEntity.blockPos, tintIndex
					)
				}

				val red = ((color shr 16) and 0xFF) / 255f
				val green = ((color shr 8) and 0xFF) / 255f
				val blue = (color and 0xFF) / 255f
				var colorAlpha = ((color shr 24) and 0xFF) / 255f
				if (colorAlpha == 0f) colorAlpha = 1f

				vertexConsumer.putBulkData(
					poseStack.last(),
					quad,
					red, green, blue, diaphBlockAlpha * colorAlpha,
					packedLight, packedOverlay,
					true
				)
			}
		}

		poseStack.popPose()
	}
}