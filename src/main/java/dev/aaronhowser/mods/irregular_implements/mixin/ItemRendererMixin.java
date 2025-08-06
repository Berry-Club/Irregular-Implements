package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

	@WrapOperation(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V"
			)
	)
	public void irregular_implements$fullbrightModelLists(
			ItemRenderer instance,
			BakedModel bakedModel,
			ItemStack stack,
			int combinedLight,
			int combinedOverlay,
			PoseStack poseStack,
			VertexConsumer vertexConsumer,
			Operation<Void> original
	) {
		int packedLight = (stack.has(ModDataComponents.HAS_LUMINOUS_POWDER)) ? 0xF000F0 : combinedLight;
		original.call(instance, bakedModel, stack, packedLight, combinedOverlay, poseStack, vertexConsumer);
	}

	@WrapOperation(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/BlockEntityWithoutLevelRenderer;renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"
			)
	)
	public void irregular_implements$fullbrightByItem(
			BlockEntityWithoutLevelRenderer instance,
			ItemStack skullmodelbase,
			ItemDisplayContext rendertype,
			PoseStack abstractskullblock,
			MultiBufferSource multiBufferSource,
			int combinedLight,
			int combinedOverlay,
			Operation<Void> original
	) {
		int packedLight = (skullmodelbase.has(ModDataComponents.HAS_LUMINOUS_POWDER)) ? 0xf000f0 : combinedLight;
		original.call(instance, skullmodelbase, rendertype, abstractskullblock, multiBufferSource, packedLight, combinedOverlay);
	}

}
