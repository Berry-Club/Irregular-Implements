package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.PlayerInterfaceBlockEntity
import net.minecraft.client.model.SkullModelBase
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer
import net.minecraft.core.component.DataComponents
import net.minecraft.world.level.block.SkullBlock

class PlayerInterfaceBlockEntityRenderer(
	val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<PlayerInterfaceBlockEntity> {

	private val skullModels: MutableMap<SkullBlock.Type, SkullModelBase> = SkullBlockRenderer.createSkullRenderers(context.modelSet)

	override fun render(
		blockEntity: PlayerInterfaceBlockEntity,
		partialTick: Float,
		poseStack: PoseStack,
		bufferSource: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		val skull = blockEntity.ownerHead
		if (skull.isEmpty) return

		val profile = skull.get(DataComponents.PROFILE) ?: return

		poseStack.pushPose()
		poseStack.translate(0f, 1.5f, 0f)

		val model = skullModels[SkullBlock.Types.PLAYER] ?: return

		SkullBlockRenderer.renderSkull(
			null,
			0f,
			0f,
			poseStack,
			bufferSource,
			packedLight,
			model,
			SkullBlockRenderer.getRenderType(SkullBlock.Types.PLAYER, profile)
		)

		poseStack.popPose()
	}

}