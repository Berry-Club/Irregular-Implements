package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

	@WrapOperation(
			method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;ILnet/minecraft/resources/ResourceLocation;)V"
			)
	)
	public void irregular_implements$modelFullBright(
			HumanoidArmorLayer<T, M, A> instance,
			PoseStack poseStack,
			MultiBufferSource multiBufferSource,
			int originalPackedLight,
			Model model,
			int layerTintColor,
			ResourceLocation texture,
			Operation<Void> original,
			PoseStack rapPose,
			MultiBufferSource rapMultiBufferSource,
			LivingEntity livingEntity,
			EquipmentSlot slot
	) {
		ItemStack itemstack = livingEntity.getItemBySlot(slot);

		int packedLight = (itemstack.has(ModDataComponents.HAS_LUMINOUS_POWDER)) ? 0xf000f0 : originalPackedLight;
		original.call(instance, poseStack, multiBufferSource, packedLight, model, layerTintColor, texture);
	}

	@WrapOperation(
			method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderTrim(Lnet/minecraft/core/Holder;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/armortrim/ArmorTrim;Lnet/minecraft/client/model/Model;Z)V"
			)
	)
	public void irregular_implements$trimFullbright(
			HumanoidArmorLayer<T, M, A> instance,
			Holder<ArmorMaterial> armorMaterialHolder,
			PoseStack poseStack,
			MultiBufferSource multiBufferSource,
			int originalPackedLight,
			ArmorTrim trim,
			Model model,
			boolean flag,
			Operation<Void> original,
			PoseStack rapPose,
			MultiBufferSource rapMultiBufferSource,
			LivingEntity livingEntity,
			EquipmentSlot slot
	) {
		ItemStack itemstack = livingEntity.getItemBySlot(slot);

		int packedLight = (itemstack.has(ModDataComponents.HAS_LUMINOUS_POWDER)) ? 0xf000f0 : originalPackedLight;
		original.call(instance, armorMaterialHolder, poseStack, multiBufferSource, packedLight, trim, model, flag);
	}

	@WrapOperation(
			method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderGlint(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;)V"
			)
	)
	public void irregular_implements$glintFullbright(
			HumanoidArmorLayer<T, M, A> instance,
			PoseStack poseStack,
			MultiBufferSource multiBufferSource,
			int originalPackedLight,
			Model model,
			Operation<Void> original,
			PoseStack rapPose,
			MultiBufferSource rapMultiBufferSource,
			LivingEntity livingEntity,
			EquipmentSlot slot
	) {
		ItemStack itemstack = livingEntity.getItemBySlot(slot);

		int packedLight = (itemstack.has(ModDataComponents.HAS_LUMINOUS_POWDER)) ? 0xf000f0 : originalPackedLight;
		original.call(instance, poseStack, multiBufferSource, packedLight, model);
	}

}
