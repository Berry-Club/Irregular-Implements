package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.aaron.AaronExtensions.isClientSide
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB

class StableEnderPearlItem(properties: Properties) : Item(properties) {

	override fun use(
		level: Level,
		player: Player,
		usedHand: InteractionHand
	): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)

		usedStack.set(
			ModDataComponents.UUID,
			player.uuid
		)

		return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide)
	}

	override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {
		if (entity.isClientSide) return false
		if (entity.age < 20 * 7) return false

		entity.playSound(SoundEvents.ENDER_EYE_DEATH)

		var teleportedPlayer = false

		val ownerUuid = stack.get(ModDataComponents.UUID)
		if (ownerUuid != null) {
			val owner = entity.level().getPlayerByUUID(ownerUuid)
			if (owner != null) {
				owner.teleportTo(entity.x, entity.y, entity.z)
				teleportedPlayer = true
			}
		}

		if (!teleportedPlayer) {
			entity.level()
				.getEntitiesOfClass(
					LivingEntity::class.java,
					AABB.ofSize(entity.position(), 20.0, 20.0, 20.0)
				)
				.randomOrNull()
				?.teleportTo(entity.x, entity.y, entity.z)
		}

		entity.discard()
		return true
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(16)
	}

}