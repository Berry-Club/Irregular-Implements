package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CraftingMenu.class)
class CraftingMenuMixin {

    @Shadow
    @Final
    private ContainerLevelAccess access;

    @ModifyReturnValue(
            method = "stillValid",
            at = @At("RETURN")
    )
    private boolean stillValidCustomCraftingTable(boolean original, Player player) {
        return original || AbstractContainerMenu.stillValid(access, player, ModBlocks.CUSTOM_CRAFTING_TABLE.get());
    }
}
