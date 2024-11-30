package dev.aaronhowser.mods.irregular_implements;

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider;
import dev.aaronhowser.mods.irregular_implements.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
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

        var fluidState = level.getFluidState(pos);
        var fluidAmount = fluidState.getAmount();
        if (fluidAmount <= 0) return null;

        var entity = collisionContext.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) return null;

        if (entity.isCrouching() || entity.isUnderWater()) return null;

        var footArmor = livingEntity.getItemBySlot(EquipmentSlot.FEET);

        TagKey<Fluid> tagKey;

        if (footArmor.is(ModItems.INSTANCE.getWATER_WALKING_BOOTS())) {
            tagKey = ModFluidTagsProvider.getALLOWS_WATER_WALKING();
        } else if (footArmor.is(ModItems.INSTANCE.getLAVA_WADERS())) {
            tagKey = ModFluidTagsProvider.getALLOWS_LAVA_WALKING();
        } else {
            return null;
        }

        if (!fluidState.is(tagKey)) return null;

        var shape = getOrCalculateShape(fluidAmount);
        var shapeBelow = getOrCalculateShape(fluidAmount - 1);

        return (context.isAbove(shapeBelow, pos, true))
                ? (original != null) ? Shapes.or(original, shape) : shape
                : null;
    }

    Map<Integer, VoxelShape> SHAPES = new HashMap<>();

    private VoxelShape getOrCalculateShape(int height) {
        if (SHAPES.containsKey(height)) {
            return SHAPES.get(height);
        }

        var shape = Block.box(0, 0, 0, 16, height, 16);
        SHAPES.put(height, shape);

        return shape;
    }

}
