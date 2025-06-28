package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModCurioProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurioItem

class LavaCharmItem(properties: Properties) : Item(properties), ICurioItem {

	override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
		charge(stack)
	}

	override fun curioTick(slotContext: SlotContext, stack: ItemStack) {
		charge(stack)
	}

	companion object {
		const val MAX_CHARGE = 200
		const val MAX_COOLDOWN = 40

		val DEFAULT_PROPERTIES: Properties =
			Properties()
				.stacksTo(1)
				.rarity(Rarity.RARE)
				.fireResistant()
				.component(ModDataComponents.COOLDOWN, 0)
				.component(ModDataComponents.CHARGE, MAX_CHARGE)

		fun charge(stack: ItemStack) {
			val charge = stack.get(ModDataComponents.CHARGE) ?: 0
			val cooldown = stack.get(ModDataComponents.COOLDOWN) ?: MAX_COOLDOWN

			if (cooldown >= 1) {
				stack.set(ModDataComponents.COOLDOWN, cooldown - 1)
				return
			}

			if (charge < MAX_CHARGE) {
				stack.set(ModDataComponents.CHARGE, charge + 1)
			}
		}

		fun tryBlockLavaDamage(event: LivingIncomingDamageEvent) {
			if (event.isCanceled || !event.source.`is`(DamageTypes.LAVA)) return

			val entity = event.entity

			val lavaProtectorStack = getFirstLavaProtector(entity) ?: return
			val charge = lavaProtectorStack.get(ModDataComponents.CHARGE) ?: return

			if (charge > 0) {
				lavaProtectorStack.set(ModDataComponents.CHARGE, charge - 1)
				lavaProtectorStack.set(ModDataComponents.COOLDOWN, MAX_COOLDOWN)
				event.isCanceled = true
			}
		}

		private fun isChargedLavaCharm(stack: ItemStack): Boolean {
			return stack.`is`(ModItems.LAVA_CHARM) && (stack.get(ModDataComponents.CHARGE) ?: 0) > 0
		}

		private fun isChargedLavaWader(stack: ItemStack): Boolean {
			return stack.`is`(ModItems.LAVA_WADERS) && (stack.get(ModDataComponents.CHARGE) ?: 0) > 0
		}

		fun getFirstLavaProtector(entity: LivingEntity): ItemStack? {
			val footArmor = entity.getItemBySlot(EquipmentSlot.FEET)
			if (isChargedLavaWader(footArmor)) {
				return footArmor
			}

			if (entity is Player) {
				var curioProtector: ItemStack? = null

				CuriosApi.getCuriosInventory(entity).ifPresent { inventory ->
					inventory.getStacksHandler(ModCurioProvider.RING_SLOT).ifPresent { ringSlotHandler ->
						for (i in 0 until ringSlotHandler.slots) {
							val stack = ringSlotHandler.stacks.getStackInSlot(i)
							if (isChargedLavaCharm(stack)) {
								curioProtector = stack
								break
							}
						}
					}
				}

				if (curioProtector != null) return curioProtector

				val inventoryCharm = entity.inventory.items.firstOrNull { isChargedLavaCharm(it) }
				if (inventoryCharm != null) return inventoryCharm
			}

			return entity.handSlots.firstOrNull { isChargedLavaCharm(it) }
		}

	}

}