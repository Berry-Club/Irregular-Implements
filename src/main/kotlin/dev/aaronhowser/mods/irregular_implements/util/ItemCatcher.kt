package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
import net.minecraft.core.registries.Registries
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
	modid = IrregularImplements.MOD_ID
)
object ItemCatcher {

	private var isCatchingDrops: Boolean = false
	private val caughtItemEntities: MutableList<ItemEntity> = mutableListOf()

	@SubscribeEvent(priority = EventPriority.LOWEST)
	fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
		if (!this.isCatchingDrops
			|| event.isCanceled
			|| event.entity !is ItemEntity
		) return

		caughtItemEntities.add(event.entity as ItemEntity)
	}

	@JvmStatic
	fun beforeDestroyBlock(player: ServerPlayer) {
		val usedItem = player.mainHandItem

		val magnetEnchant = player.registryAccess()
			.registryOrThrow(Registries.ENCHANTMENT)
			.getHolderOrThrow(ModEnchantments.MAGNETIC)
		val hasMagnetic = usedItem.getEnchantmentLevel(magnetEnchant) > 0

		this.isCatchingDrops = hasMagnetic
	}

	@JvmStatic
	fun afterDestroyBlock(player: ServerPlayer) {
		if (!this.isCatchingDrops) return

		for (itemEntity in this.caughtItemEntities.toList()) {
			teleportTo(itemEntity, player)
		}

		this.caughtItemEntities.clear()
		this.isCatchingDrops = false
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	fun onLivingDropItems(event: LivingDropsEvent) {
		if (event.isCanceled || !event.source.isDirect) return

		val killer = event.source.entity as? LivingEntity ?: return
		val usedItem = killer.mainHandItem

		val magnetEnchant = killer.registryAccess()
			.registryOrThrow(Registries.ENCHANTMENT)
			.getHolderOrThrow(ModEnchantments.MAGNETIC)
		if (usedItem.getEnchantmentLevel(magnetEnchant) < 1) return

		for (itemEntity in event.drops) {
			teleportTo(itemEntity, killer)
		}
	}

	private fun teleportTo(itemEntity: ItemEntity?, magneticEntity: LivingEntity?) {
		if (itemEntity == null || magneticEntity == null) return

		itemEntity.setNoPickUpDelay()
		if (magneticEntity is Player) itemEntity.playerTouch(magneticEntity)

		if (!itemEntity.item.isEmpty) {
			itemEntity.target = magneticEntity.uuid
			itemEntity.teleportTo(magneticEntity.x, magneticEntity.y, magneticEntity.z)
		}
	}

}