package dev.aaronhowser.mods.irregular_implements.client.render.block_entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.BiomeRadarBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.item.ItemDisplayContext

class BiomeRadarBlockEntityRenderer(
	val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<BiomeRadarBlockEntity> {

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

		context.itemRenderer.renderStatic(
			stack,
			ItemDisplayContext.FIXED,
			packedLight,
			packedOverlay,
			poseStack,
			bufferSource,
			blockEntity.level,
			0
		)

		poseStack.popPose()
	}

}