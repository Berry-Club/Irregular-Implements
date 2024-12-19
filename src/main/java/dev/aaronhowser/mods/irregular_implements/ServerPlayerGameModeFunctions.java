package dev.aaronhowser.mods.irregular_implements;

import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments;
import dev.aaronhowser.mods.irregular_implements.util.ItemCatcher;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;

public interface ServerPlayerGameModeFunctions {

    default void beforeDestroy(ServerPlayer player) {
        var usedItem = player.getMainHandItem();
        if (usedItem.getEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.getMAGNETIC(), player.registryAccess())) < 1) return;

        ItemCatcher.startCatching();
    }

    default void afterDestroy(ServerPlayer player) {
        if (!ItemCatcher.isCatching()) return;

        for (ItemEntity itemEntity : ItemCatcher.stopCatchingAndReturnList()) {
            itemEntity.setNoPickUpDelay();
            itemEntity.playerTouch(player);
            itemEntity.setTarget(player.getUUID());
            itemEntity.teleportTo(player.getX(), player.getY(), player.getZ());
        }

    }

}
