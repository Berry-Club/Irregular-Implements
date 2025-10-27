package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.EnderAnchorCarrier
import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderAnchorBlockEntity.Companion.getEnderAnchorPositions
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.TeleportToEnderBridgePacket
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.neoforged.neoforge.network.handling.IPayloadContext

class PortableEnderBridgeItem(properties: Properties) : Item(properties) {

	//TODO: Sounds, particles
	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)

		if (level !is EnderAnchorCarrier || !level.isClientSide) {
			return InteractionResultHolder.pass(usedStack)
		}

		val enderBridgeLocations = level.getEnderAnchorPositions().map(BlockPos::of)

		if (enderBridgeLocations.isEmpty()) {
			return InteractionResultHolder.pass(usedStack)
		}

		val targetBridges = mutableSetOf<BlockPos>()

		for (anchorPos in enderBridgeLocations) {
			val deltaVec = player.eyePosition.vectorTo(anchorPos.center)
			val lookVec = player.lookAngle

			val radianAngle = deltaVec.normalize().dot(lookVec.normalize())
			val degreeAngle = Math.toDegrees(radianAngle)

			if (degreeAngle < 20.0) {
				targetBridges.add(anchorPos)
			}
		}

		if (targetBridges.isEmpty()) {
			return InteractionResultHolder.fail(usedStack)
		}

		val nearest = targetBridges.minBy { player.eyePosition.distanceToSqr(it.center) }

		val packet = TeleportToEnderBridgePacket(nearest)
		packet.messageServer()

		return InteractionResultHolder.success(usedStack)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		fun handlePacket(
			packet: TeleportToEnderBridgePacket,
			context: IPayloadContext
		) {
			val player = context.player() as? ServerPlayer ?: return
			val level = player.serverLevel()

			val pos = packet.pos
			val state = level.getBlockState(pos)

			if (!state.`is`(ModBlocks.ENDER_ANCHOR)) return

			val stateAbove = level.getBlockState(pos.above())
			val stateTwoAbove = level.getBlockState(pos.above(2))
			if (!stateAbove.isAir || !stateTwoAbove.isAir) return

			player.teleportTo(pos.x + 0.5, pos.y + 1.0, pos.z + 0.5)
		}
	}

}