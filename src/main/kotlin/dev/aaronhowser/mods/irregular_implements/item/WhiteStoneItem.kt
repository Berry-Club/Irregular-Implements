package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModSounds
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent
import java.awt.Color
import java.util.function.Supplier

class WhiteStoneItem(properties: Properties) : Item(properties) {

	override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
		tryCharge(level, stack, entity.blockPosition().above())
	}

	override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {
		tryCharge(entity.level(), stack, entity.blockPosition())
		return false
	}

	override fun isFoil(stack: ItemStack): Boolean {
		return isChargedWhiteStone(stack)
	}

	override fun getBarColor(stack: ItemStack): Int {
		return Color.WHITE.rgb
	}

	override fun isBarVisible(stack: ItemStack): Boolean {
		val charge = stack.get(ModDataComponents.CHARGE.get())
		return charge != null && charge > 0 && charge < MAX_CHARGE
	}

	override fun getBarWidth(stack: ItemStack): Int {
		val charge = stack.get(ModDataComponents.CHARGE.get()) ?: return 0

		return (charge / MAX_CHARGE.toFloat() * 13).toInt()
	}

	override fun shouldCauseReequipAnimation(oldStack: ItemStack, newStack: ItemStack, slotChanged: Boolean): Boolean {
		return slotChanged && super.shouldCauseReequipAnimation(oldStack, newStack, true)
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val charge = stack.get(ModDataComponents.CHARGE.get()) ?: 0
		val percentCharge = charge / MAX_CHARGE.toFloat() * 100
		val formattedCharge = String.format("%.2f", percentCharge)

		tooltipComponents.add(ModTooltipLang.CHARGE.toGrayComponent(formattedCharge))

		if (charge < MAX_CHARGE) {
			tooltipComponents.add(ModTooltipLang.WHITE_STONE_FULL_MOON.toGrayComponent())
		}
	}

	companion object {
		val DEFAULT_PROPERTIES: Supplier<Properties> =
			Supplier {
				Properties()
					.rarity(Rarity.UNCOMMON)
					.stacksTo(1)
					.component(ModDataComponents.CHARGE, 0)
			}

		const val MAX_CHARGE = 20 * 100

		fun tryCharge(level: Level, stack: ItemStack, blockPos: BlockPos) {
			if (level !is ServerLevel
				|| level.moonPhase != 0
				|| level.dayTime !in 14000..23000
				|| isChargedWhiteStone(stack)
				|| !level.canSeeSky(blockPos)
			) return

			val currentCharge = stack.getOrDefault(ModDataComponents.CHARGE.get(), 0)
			val newCharge = currentCharge + 1

			stack.set(ModDataComponents.CHARGE, newCharge)

			level.sendParticles(
				ParticleTypes.ENCHANT,
				blockPos.center.x,
				blockPos.center.y,
				blockPos.center.z,
				1,
				0.0,
				0.0,
				0.0,
				Mth.lerp(newCharge / MAX_CHARGE.toDouble(), 3.0, 2.25)
			)

			if (newCharge == MAX_CHARGE) {
				level.playSound(
					null,
					blockPos,
					SoundEvents.ZOMBIE_VILLAGER_CONVERTED,
					SoundSource.PLAYERS,
				)
			}
		}

		fun isChargedWhiteStone(itemStack: ItemStack): Boolean {
			return itemStack.`is`(ModItems.WHITE_STONE.get()) && itemStack.get(ModDataComponents.CHARGE.get()) == MAX_CHARGE
		}

		fun tryPreventDeath(event: LivingDeathEvent) {
			if (event.isCanceled) return

			val entity = event.entity

			val whiteStone = if (entity is Player) {
				entity.inventory.items.find { isChargedWhiteStone(it) } ?: return
			} else {
				entity.handSlots.find { isChargedWhiteStone(it) } ?: return
			}

			event.isCanceled = true
			whiteStone.set(ModDataComponents.CHARGE, 0)

			entity.level().playSound(
				null,
				entity.blockPosition(),
				ModSounds.WHITE_STONE_ACTIVATE.get(),
				entity.soundSource,
			)

			entity.addEffect(MobEffectInstance(MobEffects.REGENERATION, 10 * 20))
			entity.addEffect(MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10 * 20))
			entity.addEffect(MobEffectInstance(MobEffects.FIRE_RESISTANCE, 10 * 20, 1))

			entity.health = entity.maxHealth
		}
	}


}