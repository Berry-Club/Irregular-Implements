package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.aaronhowser.mods.irregular_implements.entity.PortkeyItemEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.ItemEntityRenderer
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemDisplayContext
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class PortkeyItemEntityRenderer(
	context: EntityRendererProvider.Context
) : EntityRenderer<PortkeyItemEntity>(context) {

	private val itemRenderer: ItemRenderer = context.itemRenderer
	private val random: RandomSource = RandomSource.create()

	/** @see net.minecraft.client.renderer.entity.ItemEntityRenderer.render */
	override fun render(
		entity: PortkeyItemEntity,
		entityYaw: Float,
		partialTicks: Float,
		poseStack: PoseStack,
		buffer: MultiBufferSource,
		packedLight: Int
	) {
		poseStack.pushPose()

		val portkeyStack = entity.item.copy()
		val disguise = portkeyStack.get(ModDataComponents.PORTKEY_DISGUISE)

		val renderStack = disguise?.stack?.copy() ?: portkeyStack

		if (portkeyStack.has(ModDataComponents.LOCATION) && entity.age < PortkeyItemEntity.PORTKEY_PICKUP_DELAY) {
			renderStack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
		}

		random.setSeed(ItemEntityRenderer.getSeedForItemStack(renderStack).toLong())
		val bakedmodel = itemRenderer.getModel(renderStack, entity.level(), null, entity.id)
		val flag = bakedmodel.isGui3d

		val f = 0.25f
		val shouldBob = IClientItemExtensions.of(renderStack).shouldBobAsEntity(renderStack)

		val f1 = if (shouldBob) Mth.sin((entity.getAge().toFloat() + partialTicks) / 10.0f + entity.bobOffs) * 0.1f + 0.1f else 0f
		val f2 = bakedmodel.transforms.getTransform(ItemDisplayContext.GROUND).scale.y()
		poseStack.translate(0.0f, f1 + 0.25f * f2, 0.0f)

		val f3 = entity.getSpin(partialTicks)
		poseStack.mulPose(Axis.YP.rotation(f3))
		ItemEntityRenderer.renderMultipleFromCount(this.itemRenderer, poseStack, buffer, packedLight, renderStack, bakedmodel, flag, this.random)

		poseStack.popPose()
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight)
	}

	override fun getTextureLocation(entity: PortkeyItemEntity): ResourceLocation {
		return TextureAtlas.LOCATION_BLOCKS
	}

}