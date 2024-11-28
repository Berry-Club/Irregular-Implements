package dev.aaronhowser.mods.irregular_implements;

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider;
import dev.aaronhowser.mods.irregular_implements.registries.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;

public class ReloadableStatics {

    public static boolean test(LivingEntity livingEntity, FluidState fluidState) {

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
