package dev.aaronhowser.mods.irregular_implements.client.render.item

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class CustomCraftingTableBEWLR : BlockEntityWithoutLevelRenderer(
	Minecraft.getInstance().blockEntityRenderDispatcher,
	Minecraft.getInstance().entityModels
) {

	companion object {
		val clientItemExtensions = object : IClientItemExtensions {
			val BEWLR = CustomCraftingTableBEWLR()

			override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
				return BEWLR
			}
		}
	}

	//TODO: Render the crafting table part
	override fun renderByItem(
		stack: ItemStack,
		displayContext: ItemDisplayContext,
		poseStack: PoseStack,
		buffer: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		poseStack.pushPose()

		poseStack.scale(0.999f, 0.999f, 0.999f)
		poseStack.translate(0.0005f, 0.0005f, 0.0005f)
		poseStack.translate(0.5, 0.5, 0.5)

		val itemToRender = stack.get(ModDataComponents.BLOCK)?.asItem() ?: Items.OAK_PLANKS
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

}