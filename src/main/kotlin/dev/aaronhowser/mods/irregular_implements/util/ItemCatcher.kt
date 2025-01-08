package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
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
        if (!this.catchingDrops
            || event.isCanceled
            || event.entity !is ItemEntity
        ) return

        caughtItemEntities.add(event.entity as ItemEntity)
    }

    @JvmStatic
    fun beforeDestroyBlock(player: ServerPlayer) {
        val usedItem = player.mainHandItem

        val hasMagnetic = usedItem.getEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.MAGNETIC, player.registryAccess())) > 0

        this.catchingDrops = hasMagnetic
    }

    @JvmStatic
    fun afterDestroyBlock(player: ServerPlayer) {
        if (!this.catchingDrops) return

        for (itemEntity in this.caughtItemEntities) {
            itemEntity.setNoPickUpDelay()
            itemEntity.playerTouch(player)
            itemEntity.target = player.uuid
            itemEntity.teleportTo(player.x, player.y, player.z)
        }

        this.caughtItemEntities.clear()
        this.catchingDrops = false
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onLivingDropItems(event: LivingDropsEvent) {
        if (event.isCanceled || !event.source.isDirect) return

        val killer = event.source.entity as? LivingEntity ?: return
        val usedItem = killer.mainHandItem

        if (usedItem.getEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.MAGNETIC, killer.registryAccess())) < 1) return

        for (itemEntity in event.drops) {
            itemEntity.setNoPickUpDelay()
            if (killer is Player) itemEntity.playerTouch(killer)
            itemEntity.target = killer.uuid
            itemEntity.teleportTo(killer.x, killer.y, killer.z)
        }

        this.caughtItemEntities.clear()
        this.catchingDrops = false
    }

}