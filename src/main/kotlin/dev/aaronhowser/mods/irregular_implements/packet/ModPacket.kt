package dev.aaronhowser.mods.irregular_implements.packet

import net.minecraft.network.protocol.common.custom.CustomPacketPayload
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

}