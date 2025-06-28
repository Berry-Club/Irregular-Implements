package dev.aaronhowser.mods.irregular_implements.client.render.item

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.client.render.RenderUtils
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class SpectreIlluminatorBEWLR : BlockEntityWithoutLevelRenderer(
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
		poseStack.pushPose()
		poseStack.translate(0.5, 0.5, 0.5)

		val time = (ClientUtil.localPlayer?.tickCount ?: 0) / 200f

		RenderUtils.renderDragonRays(
			poseStack = poseStack,
			time = time,
			bufferSource = buffer
		)

		poseStack.popPose()
	}

	companion object {
		val clientItemExtensions = object : IClientItemExtensions {
			val BEWLR = SpectreIlluminatorBEWLR()

			override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
				return BEWLR
			}
		}
	}

}