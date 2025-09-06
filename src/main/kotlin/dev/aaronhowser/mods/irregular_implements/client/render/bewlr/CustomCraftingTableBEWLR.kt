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
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class CustomCraftingTableBEWLR : BlockEntityWithoutLevelRenderer(
	Minecraft.getInstance().blockEntityRenderDispatcher,
	Minecraft.getInstance().entityModels
) {

	//TODO: Render the crafting table part
	override fun renderByItem(
		stack: ItemStack,
		displayContext: ItemDisplayContext,
		poseStack: PoseStack,
		buffer: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		renderCraftingTable(poseStack)

		renderBaseItem(
			poseStack,
			buffer,
			displayContext,
			itemToRender = stack.get(ModDataComponents.BLOCK)?.asItem() ?: Items.OAK_PLANKS,
			packedLight,
			packedOverlay,
		)
	}

	private fun renderCraftingTable(
		poseStack: PoseStack
	) {
		poseStack.pushPose()

		RenderUtil.renderTexturedCube(
			poseStack,
			RenderType.cutout(),
			TOP, BOTTOM,
			SAW_AND_HAMMER, SCISSORS, SCISSORS, SAW_AND_HAMMER,
		)

		poseStack.popPose()
	}

	private fun renderBaseItem(
		poseStack: PoseStack,
		buffer: MultiBufferSource,
		displayContext: ItemDisplayContext,
		itemToRender: Item,
		packedLight: Int,
		packedOverlay: Int,
	) {
		poseStack.pushPose()

		poseStack.scale(0.999f, 0.999f, 0.999f)
		poseStack.translate(0.0005f, 0.0005f, 0.0005f)
		poseStack.translate(0.5, 0.5, 0.5)

		val itemRenderer = Minecraft.getInstance().itemRenderer

		itemRenderer.renderStatic(
			itemToRender.defaultInstance,
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