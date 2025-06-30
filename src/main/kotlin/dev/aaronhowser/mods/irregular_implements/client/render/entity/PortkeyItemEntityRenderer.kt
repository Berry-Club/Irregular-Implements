package dev.aaronhowser.mods.irregular_implements.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.ItemEntityRenderer
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemDisplayContext
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class PortkeyItemEntityRenderer(context: EntityRendererProvider.Context) : ItemEntityRenderer(context) {

	private val itemRenderer: ItemRenderer = context.itemRenderer
	private val random: RandomSource = RandomSource.create()

	override fun render(
		entity: ItemEntity,
		entityYaw: Float,
		partialTicks: Float,
		poseStack: PoseStack,
		buffer: MultiBufferSource,
		packedLight: Int
	) {
		poseStack.pushPose()

		var itemstack = entity.item
		val disguise = itemstack.get(ModDataComponents.PORTKEY_DISGUISE)
		if (disguise != null) itemstack = disguise

		random.setSeed(getSeedForItemStack(itemstack).toLong())
		val bakedmodel = itemRenderer.getModel(itemstack, entity.level(), null, entity.id)
		val flag = bakedmodel.isGui3d

		val f = 0.25f
		val shouldBob = IClientItemExtensions.of(itemstack).shouldBobAsEntity(itemstack)

		val f1 = if (shouldBob) Mth.sin((entity.getAge().toFloat() + partialTicks) / 10.0f + entity.bobOffs) * 0.1f + 0.1f else 0f
		val f2 = bakedmodel.transforms.getTransform(ItemDisplayContext.GROUND).scale.y()
		poseStack.translate(0.0f, f1 + 0.25f * f2, 0.0f)

		val f3 = entity.getSpin(partialTicks)
		poseStack.mulPose(Axis.YP.rotation(f3))
		renderMultipleFromCount(this.itemRenderer, poseStack, buffer, packedLight, itemstack, bakedmodel, flag, this.random)

		poseStack.popPose()
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight)
	}

}