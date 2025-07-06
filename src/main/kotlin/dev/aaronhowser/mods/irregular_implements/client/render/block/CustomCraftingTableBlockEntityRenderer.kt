package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class CustomCraftingTableBlockEntityRenderer(
	val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<CustomCraftingTableBlockEntity> {

	override fun render(
		blockEntity: CustomCraftingTableBlockEntity,
		partialTick: Float,
		poseStack: PoseStack,
		bufferSource: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		poseStack.pushPose()

		poseStack.scale(0.999f, 0.999f, 0.999f)
		poseStack.translate(0.0005f, 0.0005f, 0.0005f)

		@Suppress("DEPRECATION")
		context.blockRenderDispatcher.renderSingleBlock(
			blockEntity.renderedBlockState,
			poseStack,
			bufferSource,
			packedLight,
			packedOverlay,
		)

		poseStack.popPose()
	}
}