package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.entity.ThrownGoldenEggEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isServerSide
import net.minecraft.core.Direction
import net.minecraft.core.Position
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ProjectileItem
import net.minecraft.world.level.Level

class GoldenEggItem(properties: Properties) : Item(properties), ProjectileItem {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)
		level.playSound(
			null,
			player.x,
			player.y,
			player.z,
			SoundEvents.EGG_THROW,
			SoundSource.PLAYERS,
			0.5f,
			0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f)
		)

		if (level.isServerSide) {
			val thrownGoldenEgg = ThrownGoldenEggEntity(level, player)
			thrownGoldenEgg.item = usedStack
			thrownGoldenEgg.shootFromRotation(player, player.xRot, player.yRot, 0.0f, 1.5f, 1.0f)
			level.addFreshEntity(thrownGoldenEgg)
		}

		player.awardStat(Stats.ITEM_USED[this])
		usedStack.consume(1, player)
		return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide())

	}

	override fun asProjectile(level: Level, pos: Position, stack: ItemStack, direction: Direction): Projectile {
		val thrownGoldenEgg = ThrownGoldenEggEntity(level, pos.x(), pos.y(), pos.z())
		thrownGoldenEgg.item = stack

		return thrownGoldenEgg
	}

}