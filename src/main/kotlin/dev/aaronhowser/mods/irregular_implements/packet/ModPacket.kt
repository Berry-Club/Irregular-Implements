package dev.aaronhowser.mods.irregular_implements.packet

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.network.handling.IPayloadContext

abstract class ModPacket : CustomPacketPayload {

	protected open fun handleOnClient(context: IPayloadContext) {
		throw kotlin.UnsupportedOperationException("Packet $this cannot be received on the client!")
	}

	protected open fun handleOnServer(context: IPayloadContext) {
		throw kotlin.UnsupportedOperationException("Packet $this cannot be received on the server!")
	}

	fun receiveOnClient(context: IPayloadContext) {
		context.enqueueWork {
			handleOnClient(context)
		}
	}

	fun receiveOnServer(context: IPayloadContext) {
		context.enqueueWork {
			handleOnServer(context)
		}
	}

	fun messagePlayer(player: ServerPlayer) = ModPacketHandler.messagePlayer(player, this)
	fun messageAllPlayers() = ModPacketHandler.messageAllPlayers(this)
	fun messageServer() = ModPacketHandler.messageServer(this)
	fun messageNearbyPlayers(serverLevel: ServerLevel, origin: Vec3, radius: Double) = ModPacketHandler.messageNearbyPlayers(this, serverLevel, origin, radius)
	fun messageNearbyPlayers(serverLevel: ServerLevel, pos: BlockPos, radius: Double) = ModPacketHandler.messageNearbyPlayers(this, serverLevel, pos, radius)

}