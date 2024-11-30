package dev.aaronhowser.mods.irregular_implements;

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider;
import dev.aaronhowser.mods.irregular_implements.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public interface BlockStateBaseFunctions {

    @Nullable
    default VoxelShape checkCollisionShape(BlockGetter level, BlockPos pos, CollisionContext context, VoxelShape original) {
        if (!(context instanceof EntityCollisionContext collisionContext)) return null;

        FluidState fluidState = level.getFluidState(pos);
        float fluidHeight = fluidState.getHeight(level, pos);
        if (fluidHeight <= 0) return null;

        Entity entity = collisionContext.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) return null;

        if (livingEntity.isCrouching() || livingEntity.isUnderWater()) return null;

        ItemStack footArmor = livingEntity.getItemBySlot(EquipmentSlot.FEET);

        TagKey<Fluid> tagKey;

        if (footArmor.is(ModItems.INSTANCE.getWATER_WALKING_BOOTS())) {
            tagKey = ModFluidTagsProvider.getALLOWS_WATER_WALKING();
        } else if (footArmor.is(ModItems.INSTANCE.getLAVA_WADERS())) {
            tagKey = ModFluidTagsProvider.getALLOWS_LAVA_WALKING();
        } else {
            return null;
        }

        if (!fluidState.is(tagKey)) return null;

        VoxelShape shape = SHAPES.computeIfAbsent(
                fluidHeight,
                h -> Block.box(0, 0, 0, 16, h * 16, 16)
        );

        if (!context.isAbove(shape, pos, true)) return null;

        return (original == null)
                ? shape
                : Shapes.or(original, shape);
    }

    Map<Float, VoxelShape> SHAPES = new HashMap<>();
}
