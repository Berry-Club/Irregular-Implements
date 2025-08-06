package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

	@WrapOperation(
			method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderGlint(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;)V"
			)
	)
	public void a(
			HumanoidArmorLayer instance,
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
		original.call(instance, rapPose, rapMultiBufferSource, packedLight, model);
	}

}
