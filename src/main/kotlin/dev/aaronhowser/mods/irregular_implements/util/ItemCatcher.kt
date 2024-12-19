package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments.MAGNETIC
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments.getHolder
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID
)
object ItemCatcher {

    private var catchingDrops: Boolean = false
    private val caughtItemEntities: MutableList<ItemEntity> = mutableListOf()

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
        if (!catchingDrops || event.isCanceled || event.entity !is ItemEntity) return

        caughtItemEntities.add(event.entity as ItemEntity)
    }

    @JvmStatic
    fun beforeDestroyBlock(player: ServerPlayer) {
        val usedItem = player.mainHandItem
        if (usedItem.getEnchantmentLevel(getHolder(MAGNETIC, player.registryAccess())) < 1) return

        catchingDrops = true
    }

    @JvmStatic
    fun afterDestroyBlock(player: ServerPlayer) {
        if (!catchingDrops) return

        for (itemEntity in caughtItemEntities) {
            itemEntity.setNoPickUpDelay()
            itemEntity.playerTouch(player)
            itemEntity.target = player.uuid
            itemEntity.teleportTo(player.x, player.y, player.z)
        }
    }

    @JvmStatic
    fun isCatching(): Boolean {
        return catchingDrops
    }


}