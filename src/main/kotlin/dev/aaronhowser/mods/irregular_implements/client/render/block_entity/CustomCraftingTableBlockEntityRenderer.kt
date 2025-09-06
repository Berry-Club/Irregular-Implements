package dev.aaronhowser.mods.irregular_implements.client.render.block_entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
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

		poseStack.translate(0f, 0.1f, 0f)

		RenderUtil.renderTexturedCube(
			poseStack,
			RenderType.translucent(),
			TOP, BOTTOM,
			FRONT, SIDE, SIDE, SIDE,
		)

		poseStack.popPose()

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

	companion object {
		val BOTTOM = OtherUtil.modResource("block/custom_crafting_table/bottom")
		val TOP = OtherUtil.modResource("block/custom_crafting_table/top")
		val FRONT = OtherUtil.modResource("block/custom_crafting_table/front")
		val SIDE = OtherUtil.modResource("block/custom_crafting_table/side")
	}

}