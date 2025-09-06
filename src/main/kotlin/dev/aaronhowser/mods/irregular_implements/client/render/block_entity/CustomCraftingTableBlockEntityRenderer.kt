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

	//FIXME: Either allow it to be translucent or make the top texture not have translucency
	override fun render(
		blockEntity: CustomCraftingTableBlockEntity,
		partialTick: Float,
		poseStack: PoseStack,
		bufferSource: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		RenderUtil.renderTexturedCube(
			poseStack,
			RenderType.cutout(),
			TOP, BOTTOM,
			SAW_AND_HAMMER, SCISSORS, SCISSORS, SAW_AND_HAMMER,
		)

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
		val SAW_AND_HAMMER = OtherUtil.modResource("block/custom_crafting_table/saw_and_hammer")
		val SCISSORS = OtherUtil.modResource("block/custom_crafting_table/scissors")
	}

}