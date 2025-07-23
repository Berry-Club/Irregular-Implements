package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.entity.SpiritEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.model.SlimeModel
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation

class SpiritEntityRenderer(
	context: EntityRendererProvider.Context
) : MobRenderer<SpiritEntity, SlimeModel<SpiritEntity>>(
	context,
	SlimeModel(context.bakeLayer(ModelLayers.SLIME)),
	0.25f
) {

	init {
		addLayer(SpiritGelLayer(this, context.modelSet))
	}

	override fun render(entity: SpiritEntity, entityYaw: Float, partialTicks: Float, poseStack: PoseStack, buffer: MultiBufferSource, packedLight: Int) {
		poseStack.pushPose()

		val scale = 0.5f
		poseStack.scale(scale, scale, scale)

		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight)

		poseStack.popPose()
	}

	override fun getBlockLightLevel(entity: SpiritEntity, pos: BlockPos): Int = 15
	override fun getSkyLightLevel(entity: SpiritEntity, pos: BlockPos): Int = 15

	override fun getTextureLocation(entity: SpiritEntity): ResourceLocation {
		return TEXTURE
	}

	companion object {
		val TEXTURE = OtherUtil.modResource("textures/entity/spirit.png")
	}

	class SpiritGelLayer(
		renderer: RenderLayerParent<SpiritEntity, SlimeModel<SpiritEntity>>,
		modelSet: EntityModelSet
	) : RenderLayer<SpiritEntity, SlimeModel<SpiritEntity>>(renderer) {

		val model = SlimeModel<SpiritEntity>(modelSet.bakeLayer(ModelLayers.SLIME_OUTER))

		override fun render(
			poseStack: PoseStack,
			bufferSource: MultiBufferSource,
			packedLight: Int,
			livingEntity: SpiritEntity,
			limbSwing: Float,
			limbSwingAmount: Float,
			partialTick: Float,
			ageInTicks: Float,
			netHeadYaw: Float,
			headPitch: Float
		) {
			if (livingEntity.isInvisible) return
			val consumer = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation(livingEntity)))

			parentModel.copyPropertiesTo(model)

			model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTick)
			model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
			model.renderToBuffer(poseStack, consumer, packedLight, getOverlayCoords(livingEntity, 0.0f))

		}

	}

}