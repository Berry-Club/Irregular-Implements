package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.block.block_entity.NatureChestBlockEntity;
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheets.class)
abstract class SheetsMixin {

    @Shadow
    @Final
    public static ResourceLocation CHEST_SHEET;
    //FIXME No worky
    @Unique
    private static Material NATURE_CHEST = new Material(CHEST_SHEET, OtherUtil.modResource("entity/nature_chest"));

    @Inject(
            method = "chooseMaterial(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/state/properties/ChestType;Z)Lnet/minecraft/client/resources/model/Material;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void irregular_implements$getChestTexture(BlockEntity blockEntity, ChestType chestType, boolean holiday, CallbackInfoReturnable<Material> cir) {
        if (blockEntity instanceof NatureChestBlockEntity) {
            cir.setReturnValue(NATURE_CHEST);
        }
    }


}
