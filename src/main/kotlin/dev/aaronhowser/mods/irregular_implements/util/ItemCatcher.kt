package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.world.entity.item.ItemEntity
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID
)
object ItemCatcher {

    private var catchingDrops: Boolean = false
    private val caughtItemEntities: MutableList<ItemEntity> = mutableListOf()

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
        if (!catchingDrops || event.entity !is ItemEntity) return

        caughtItemEntities.add(event.entity as ItemEntity)
    }

    @JvmStatic
    fun startCatching() {
        catchingDrops = true
    }

    @JvmStatic
    fun stopCatchingAndReturnList(): List<ItemEntity> {
        catchingDrops = false
        val list = caughtItemEntities.toList()
        caughtItemEntities.clear()
        return list
    }

    @JvmStatic
    fun isCatching(): Boolean {
        return catchingDrops
    }

}