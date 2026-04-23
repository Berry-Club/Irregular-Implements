package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.hasEnchantment
import dev.aaronhowser.mods.aaron.misc.ItemCatcher
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent

@EventBusSubscriber(
	modid = IrregularImplements.MOD_ID
)
object MagneticHandler {

	@JvmStatic
	fun beforeDestroyBlock(player: ServerPlayer) {
		val usedItem = player.mainHandItem

		val hasMagnetic = hasMagnetic(usedItem, player.registryAccess())

		if (hasMagnetic) {
			ItemCatcher.startCatchingItems()
		}
	}

	@JvmStatic
	fun afterDestroyBlock(player: ServerPlayer) {
		if (!ItemCatcher.isCatchingItems()) return

		val caughtItems = ItemCatcher.getCaughtItemEntities()
		for (itemEntity in caughtItems) {
			teleportTo(itemEntity, player)
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	fun onLivingDropItems(event: LivingDropsEvent) {
		if (event.isCanceled || !event.source.isDirect) return

		val killer = event.source.entity as? LivingEntity ?: return
		val usedItem = killer.mainHandItem

		val hasMagnetic = hasMagnetic(usedItem, killer.registryAccess())
		if (!hasMagnetic) return

		for (itemEntity in event.drops) {
			teleportTo(itemEntity, killer)
		}
	}

	private fun hasMagnetic(stack: ItemStack, registryAccess: RegistryAccess): Boolean {
		val magnetEnchant = registryAccess
			.registryOrThrow(Registries.ENCHANTMENT)
			.getHolderOrThrow(ModEnchantments.MAGNETIC)

		return stack.hasEnchantment(magnetEnchant)
	}

	private fun teleportTo(itemEntity: ItemEntity, magneticEntity: LivingEntity) {
		itemEntity.target = magneticEntity.uuid
		itemEntity.teleportTo(magneticEntity.x, magneticEntity.y, magneticEntity.z)
		itemEntity.setNoPickUpDelay()

		if (magneticEntity is Player) itemEntity.playerTouch(magneticEntity)
	}

}