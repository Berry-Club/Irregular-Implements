package dev.aaronhowser.mods.irregular_implements.client.render.bewlr

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.aaron.misc.AaronDsls.withPose
import dev.aaronhowser.mods.irregular_implements.client.render.block_entity.CustomCraftingTableBER
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class CustomCraftingTableBEWLR : BlockEntityWithoutLevelRenderer(
	Minecraft.getInstance().blockEntityRenderDispatcher,
	Minecraft.getInstance().entityModels
) {

	val itemRenderer: ItemRenderer = Minecraft.getInstance().itemRenderer

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

		poseStack.withPose {
			itemRenderer
				.getModel(itemToRender, null, null, 0)
				.applyTransform(displayContext, poseStack, false)

			poseStack.withPose {
				poseStack.translate(0.5f, 0.5f, 0.5f)

				itemRenderer.renderStatic(
					itemToRender,
					ItemDisplayContext.NONE,
					packedLight,
					packedOverlay,
					poseStack,
					buffer,
					null,
					0,
				)
			}

			CustomCraftingTableBER.renderOverlay(
				poseStack,
				buffer,
				packedLight,
				packedOverlay,
			)
		}
	}

	object ClientItemExtensions : IClientItemExtensions {
		val BEWLR = CustomCraftingTableBEWLR()

		override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
			return BEWLR
		}
	}

}