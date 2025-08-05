package dev.aaronhowser.mods.irregular_implements.client.render.item

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
import net.neoforged.neoforge.fluids.SimpleFluidContent

//TODO
class EnderBucketBEWLR : BlockEntityWithoutLevelRenderer(
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
		val fluidContent = stack.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY

		val model = itemRenderer.getModel(stack, null, null, 0)

		// Render the base bucket model
		val baseVertexConsumer = ItemRenderer.getFoilBufferDirect(
			buffer,
			RenderType.itemEntityTranslucentCull(TEXTURE_LOCATION),
			false,
			stack.hasFoil()
		)

		itemRenderer.renderModelLists(
			model,
			stack,
			packedLight,
			packedOverlay,
			poseStack,
			baseVertexConsumer
		)


		if (!fluidContent.isEmpty) {
			val fluidStack = fluidContent.copy()
			val ext = IClientFluidTypeExtensions.of(fluidContent.fluid.fluidType)

			val sprite = Minecraft.getInstance()
				.getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
				.apply(ext.getStillTexture(fluidStack))

			val fluidColor = ext.getTintColor(fluidStack)

			val r = (fluidColor shr 16 and 0xFF) / 255f
			val g = (fluidColor shr 8 and 0xFF) / 255f
			val b = (fluidColor and 0xFF) / 255f
			val a = (fluidColor shr 24 and 0xFF) / 255f

			val fluidConsumer = buffer.getBuffer(RenderType.itemEntityTranslucentCull(TEXTURE_LOCATION))

			poseStack.pushPose()

			// Slight Z offset to render above base layer
			val matrix = poseStack.last().pose()

			// Draw a flat quad with the fluid sprite on the front face of the item
			fluidConsumer.addVertex(matrix, -0.5f, -0.5f, 0.01f).setColor(r, g, b, a).setUv(sprite.u0, sprite.v1)
			fluidConsumer.addVertex(matrix, -0.5f,  0.5f, 0.01f).setColor(r, g, b, a).setUv(sprite.u0, sprite.v0)
			fluidConsumer.addVertex(matrix,  0.5f,  0.5f, 0.01f).setColor(r, g, b, a).setUv(sprite.u1, sprite.v0)
			fluidConsumer.addVertex(matrix,  0.5f, -0.5f, 0.01f).setColor(r, g, b, a).setUv(sprite.u1, sprite.v1)

			poseStack.popPose()
		}


	}

	companion object {
		val CLIENT_ITEM_EXTENSIONS = object : IClientItemExtensions {
			val BEWLR = EnderBucketBEWLR()

			override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
				return BEWLR
			}
		}

		val TEXTURE_LOCATION = OtherUtil.modResource("textures/item/ender_bucket.png")
	}

}