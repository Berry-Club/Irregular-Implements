package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.EnderAnchorCarrier
import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderAnchorBlockEntity.Companion.getEnderAnchorPositionLongs
import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderAnchorBlockEntity.Companion.getEnderAnchorPositions
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import kotlin.math.acos

class PortableEnderBridgeItem(properties: Properties) : Item(properties) {

	//TODO: particles
	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)

		if (level !is EnderAnchorCarrier || level.isClientSide) {
			return InteractionResultHolder.pass(usedStack)
		}

		val enderBridgeLocations = level.getEnderAnchorPositions()

		if (enderBridgeLocations.isEmpty()) {
			return InteractionResultHolder.pass(usedStack)
		}

		val targetBridges = mutableSetOf<BlockPos>()

		for (anchorPos in enderBridgeLocations) {
			val deltaVec = player.eyePosition.vectorTo(anchorPos.center)
			val lookVec = player.lookAngle

			val dot = deltaVec.normalize().dot(lookVec.normalize())
			val radianAngle = acos(dot)
			val degreeAngle = Math.toDegrees(radianAngle)

			if (degreeAngle < 5.0) {
				targetBridges.add(anchorPos)
			}
		}

		if (targetBridges.isEmpty()) {
			return InteractionResultHolder.fail(usedStack)
		}

		val nearest = targetBridges.minBy { player.eyePosition.distanceToSqr(it.center) }

		val stateAbove = level.getBlockState(nearest.above())
		val stateTwoAbove = level.getBlockState(nearest.above(2))
		if (!stateAbove.isAir || !stateTwoAbove.isAir) {
			return InteractionResultHolder.fail(usedStack)
		}

		level.playSound(
			null,
			player.blockPosition(),
			SoundEvents.ENDERMAN_TELEPORT,
			SoundSource.BLOCKS
		)

		player.teleportTo(nearest.x + 0.5, nearest.y + 1.0, nearest.z + 0.5)

		level.playSound(
			null,
			nearest.above(),
			SoundEvents.ENDERMAN_TELEPORT,
			SoundSource.BLOCKS
		)

		return InteractionResultHolder.success(usedStack)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

}