package dev.aaronhowser.mods.irregular_implements.client.render.block

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.aaronhowser.mods.irregular_implements.block.block_entity.PlayerInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.model.SkullModelBase
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer
import net.minecraft.core.component.DataComponents
import net.minecraft.util.Mth
import net.minecraft.world.level.block.SkullBlock
import kotlin.math.atan2

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

		val localPlayer = ClientUtil.localPlayer
		if (localPlayer != null) {
			val deltaPos = blockEntity.blockPos
				.above()
				.center
				.vectorTo(localPlayer.eyePosition)
				.normalize()

			poseStack.translate(0.5, 0.5, 0.5)

			val yaw = atan2(deltaPos.x, deltaPos.z).toFloat() - 180 * Mth.DEG_TO_RAD
			val pitch = atan2(deltaPos.y, deltaPos.horizontalDistance()).toFloat()

			poseStack.mulPose(Axis.YP.rotation(yaw))
			poseStack.mulPose(Axis.XP.rotation(pitch))

			poseStack.translate(-0.5, -0.5, -0.5)
		}

		poseStack.translate(0f, 1.1f, 0f)

		val model = skullModels[SkullBlock.Types.PLAYER] ?: return

		SkullBlockRenderer.renderSkull(
			null,
			0f,
			0f,
			poseStack,
			bufferSource,
			0xFFFFFF,
			model,
			SkullBlockRenderer.getRenderType(SkullBlock.Types.PLAYER, profile)
		)

		poseStack.popPose()
	}

}