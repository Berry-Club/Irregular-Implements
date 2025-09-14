package dev.aaronhowser.mods.irregular_implements.client.render.block_entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.aaronhowser.mods.irregular_implements.block.block_entity.BiomeRadarBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.item.ItemDisplayContext

class BiomeRadarBlockEntityRenderer(
	val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<BiomeRadarBlockEntity> {

	//FIXME: packedLight is being received as 0 for some reason?
	override fun render(
		blockEntity: BiomeRadarBlockEntity,
		partialTick: Float,
		poseStack: PoseStack,
		bufferSource: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		val stack = blockEntity.getBiomeStack()
		if (stack.isEmpty) return

		poseStack.pushPose()

		poseStack.translate(0.5, 0.5, 0.5)
		poseStack.scale(0.5f, 0.5f, 0.5f)

		val time = (blockEntity.level?.gameTime ?: 0) % 3600 + partialTick

		poseStack.mulPose(
			Axis.YP.rotationDegrees(time)
		)

		context.itemRenderer.renderStatic(
			stack,
			ItemDisplayContext.FIXED,
			15728880,
			packedOverlay,
			poseStack,
			bufferSource,
			blockEntity.level,
			0
		)

		poseStack.popPose()
	}

}