package dev.aaronhowser.mods.irregular_implements.client.render.bewlr

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.client.render.block_entity.CustomCraftingTableBlockEntityRenderer.Companion.BOTTOM
import dev.aaronhowser.mods.irregular_implements.client.render.block_entity.CustomCraftingTableBlockEntityRenderer.Companion.SAW_AND_HAMMER
import dev.aaronhowser.mods.irregular_implements.client.render.block_entity.CustomCraftingTableBlockEntityRenderer.Companion.SCISSORS
import dev.aaronhowser.mods.irregular_implements.client.render.block_entity.CustomCraftingTableBlockEntityRenderer.Companion.TOP
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class CustomCraftingTableBEWLR : BlockEntityWithoutLevelRenderer(
	Minecraft.getInstance().blockEntityRenderDispatcher,
	Minecraft.getInstance().entityModels
) {

	override fun renderByItem(
		stack: ItemStack,
		displayContext: ItemDisplayContext,
		poseStack: PoseStack,
		buffer: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		val itemToRender = stack
			.getOrDefault(ModDataComponents.BLOCK, Blocks.OAK_PLANKS)
			.asItem()
			.defaultInstance

		renderCraftingTableOverlay(poseStack, displayContext, itemToRender, packedLight, packedOverlay)

		renderBaseItem(
			poseStack,
			buffer,
			displayContext,
			itemToRender,
			packedLight,
			packedOverlay,
		)
	}

	//TODO: It's translated incorrectly
	private fun renderCraftingTableOverlay(
		poseStack: PoseStack,
		displayContext: ItemDisplayContext,
		itemToRender: ItemStack,
		packedLight: Int,
		packedOverlay: Int
	) {
		val itemTransform = Minecraft.getInstance()
			.itemRenderer
			.getModel(itemToRender, null, null, 0)
			.transforms
			.getTransform(displayContext)

		poseStack.pushPose()

		itemTransform.apply(false, poseStack)

		RenderUtil.renderTexturedCube(
			poseStack,
			RenderType.cutout(),
			TOP, BOTTOM,
			SAW_AND_HAMMER, SCISSORS, SCISSORS, SAW_AND_HAMMER,
			packedLight, packedOverlay
		)

		poseStack.popPose()
	}

	private fun renderBaseItem(
		poseStack: PoseStack,
		buffer: MultiBufferSource,
		displayContext: ItemDisplayContext,
		itemToRender: ItemStack,
		packedLight: Int,
		packedOverlay: Int,
	) {
		poseStack.pushPose()

		poseStack.translate(-0.5, -0.5, -0.5)
		poseStack.scale(0.999f, 0.999f, 0.999f)
		poseStack.translate(0.5, 0.5, 0.5)

		poseStack.translate(0.0005f, 0.0005f, 0.0005f)
		poseStack.translate(0.5, 0.5, 0.5)

		Minecraft.getInstance().itemRenderer
			.renderStatic(
				itemToRender,
				displayContext,
				packedLight,
				packedOverlay,
				poseStack,
				buffer,
				null,
				0,
			)

		poseStack.popPose()
	}

	object ClientItemExtensions : IClientItemExtensions {
		val BEWLR = CustomCraftingTableBEWLR()

		override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
			return BEWLR
		}
	}

}