package dev.aaronhowser.mods.irregular_implements.packet

import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.PacketDistributor
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

	fun messagePlayer(player: ServerPlayer) = PacketDistributor.sendToPlayer(player, this)
	fun messageAllPlayers() = ModPacketHandler.messageAllPlayers(this)
	fun messageServer() = ModPacketHandler.messageServer(this)

}