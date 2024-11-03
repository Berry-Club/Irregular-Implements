package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow
    public abstract BlockPos getBlockPosBelowThatAffectsMyMovement();

    @ModifyVariable(
            method = "tick",
            at = @At(
                    value = "STORE",
                    ordinal = 1
            )
    )
    private float replaceFriction(float original) {
        var groundPos = getBlockPosBelowThatAffectsMyMovement();
        var level = ((ItemEntity) (Object) this).level();

        if (!level.getBlockState(groundPos).is(ModBlockTagsProvider.Companion.getSUPER_LUBRICATED())) return original;
        return 1f;
    }
}
