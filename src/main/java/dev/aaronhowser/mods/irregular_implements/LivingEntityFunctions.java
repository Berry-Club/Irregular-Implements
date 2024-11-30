package dev.aaronhowser.mods.irregular_implements;

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider;
import dev.aaronhowser.mods.irregular_implements.registries.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public interface LivingEntityFunctions {

    default boolean checkShouldDiscardFriction() {
        LivingEntity livingEntity = (LivingEntity) this;

        if (livingEntity.getDeltaMovement().lengthSqr() > 1f) return false;

        if (livingEntity.getItemBySlot(EquipmentSlot.FEET)
                .is(ModItems.INSTANCE.getSUPER_LUBRICANT_BOOTS())
        ) return true;

        return livingEntity.level()
                .getBlockState(livingEntity.getBlockPosBelowThatAffectsMyMovement())
                .is(ModBlockTagsProvider.getSUPER_LUBRICATED());
    }

    //FIXME: Breaks if the player's footpos is under the surface of the water
    default boolean checkCanStandOnFluid(FluidState fluidState) {
        LivingEntity livingEntity = (LivingEntity) this;

        if (livingEntity instanceof Player player) {
            if (player.isShiftKeyDown()) return false;
        }

        if (livingEntity.isUnderWater()) return false;

        var wornBoots = livingEntity.getItemBySlot(EquipmentSlot.FEET);

        if (fluidState.is(ModFluidTagsProvider.getALLOWS_WATER_WALKING())) {
            return wornBoots.is(ModItems.INSTANCE.getWATER_WALKING_BOOTS());
        } else if (fluidState.is(ModFluidTagsProvider.getALLOWS_LAVA_WALKING())) {
            return wornBoots.is(ModItems.INSTANCE.getLAVA_WADERS());
        }

        return false;
    }

}
