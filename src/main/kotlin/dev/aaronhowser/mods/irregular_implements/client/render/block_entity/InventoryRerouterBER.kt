package dev.aaronhowser.mods.irregular_implements.client.render.block_entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.getDirectionName
import dev.aaronhowser.mods.irregular_implements.block.InventoryRerouterBlock
import dev.aaronhowser.mods.irregular_implements.block_entity.InventoryRerouterBlockEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f

class InventoryRerouterBER(
	context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<InventoryRerouterBlockEntity> {

	override fun render(
		blockEntity: InventoryRerouterBlockEntity,
		partialTick: Float,
		poseStack: PoseStack,
		bufferSource: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		val front = blockEntity.blockState
			.getValue(InventoryRerouterBlock.FACING)

		val pose = poseStack.last().pose()

		for (exposedSide in Direction.entries) {
			if (exposedSide == front) continue

			val configuredSide = blockEntity.getConfiguredSide(exposedSide)
			val texture = OVERLAYS.getValue(configuredSide)
			val consumer = bufferSource.getBuffer(RenderType.entityCutout(texture))

			renderFace(pose, consumer, exposedSide, packedLight, packedOverlay)
		}
	}

	private fun renderFace(
		pose: Matrix4f,
		consumer: VertexConsumer,
		side: Direction,
		packedLight: Int,
		packedOverlay: Int
	) {
		val vertices = when (side) {
			Direction.DOWN -> DOWN_VERTICES
			Direction.UP -> UP_VERTICES
			Direction.NORTH -> NORTH_VERTICES
			Direction.SOUTH -> SOUTH_VERTICES
			Direction.WEST -> WEST_VERTICES
			Direction.EAST -> EAST_VERTICES
		}

		for (vertex in vertices) {
			consumer.addVertex(pose, vertex.x, vertex.y, vertex.z)
				.setColor(255, 255, 255, 255)
				.setUv(vertex.u, vertex.v)
				.setOverlay(packedOverlay)
				.setLight(packedLight)
				.setNormal(side.stepX.toFloat(), side.stepY.toFloat(), side.stepZ.toFloat())
		}
	}

	companion object {
		private const val MIN = -0.001f
		private const val MAX = 1.001f

		private val DOWN_VERTICES = arrayOf(
			Vertex(0f, MIN, 1f, 0f, 1f), Vertex(0f, MIN, 0f, 0f, 0f),
			Vertex(1f, MIN, 0f, 1f, 0f), Vertex(1f, MIN, 1f, 1f, 1f)
		)

		private val UP_VERTICES = arrayOf(
			Vertex(0f, MAX, 0f, 1f, 1f), Vertex(0f, MAX, 1f, 1f, 0f),
			Vertex(1f, MAX, 1f, 0f, 0f), Vertex(1f, MAX, 0f, 0f, 1f)
		)

		private val NORTH_VERTICES = arrayOf(
			Vertex(0f, 0f, MIN, 1f, 1f), Vertex(0f, 1f, MIN, 1f, 0f),
			Vertex(1f, 1f, MIN, 0f, 0f), Vertex(1f, 0f, MIN, 0f, 1f)
		)

		private val SOUTH_VERTICES = arrayOf(
			Vertex(1f, 0f, MAX, 1f, 1f), Vertex(1f, 1f, MAX, 1f, 0f),
			Vertex(0f, 1f, MAX, 0f, 0f), Vertex(0f, 0f, MAX, 0f, 1f)
		)

		private val WEST_VERTICES = arrayOf(
			Vertex(MIN, 0f, 1f, 1f, 1f), Vertex(MIN, 1f, 1f, 1f, 0f),
			Vertex(MIN, 1f, 0f, 0f, 0f), Vertex(MIN, 0f, 0f, 0f, 1f)
		)

		private val EAST_VERTICES = arrayOf(
			Vertex(MAX, 0f, 0f, 1f, 1f), Vertex(MAX, 1f, 0f, 1f, 0f),
			Vertex(MAX, 1f, 1f, 0f, 0f), Vertex(MAX, 0f, 1f, 0f, 1f)
		)

		val OVERLAYS: Map<Direction, ResourceLocation> =
			Direction.entries.associateWith { direction ->
				OtherUtil.modResource("textures/block/inventory_rerouter/overlay/${direction.getDirectionName()}.png")
			}
	}

	private data class Vertex(
		val x: Float,
		val y: Float,
		val z: Float,
		val u: Float,
		val v: Float
	)

}
