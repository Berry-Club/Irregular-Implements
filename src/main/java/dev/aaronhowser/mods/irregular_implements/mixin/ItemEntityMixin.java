package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyVariable(
            method = "tick",
            at = @At(
                    value = "STORE",
                    ordinal = 1
            )
    )
    private float replaceFriction(float original) {
        return (this.level()
                .getBlockState(getBlockPosBelowThatAffectsMyMovement())
                .is(ModBlockTagsProvider.Companion.getSUPER_LUBRICATED())
        ) ? 1f : original;
    }
}
