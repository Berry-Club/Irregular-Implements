package dev.aaronhowser.mods.irregular_implements.packet

import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

interface IModPacket : CustomPacketPayload {
    fun receiveOnClient(context: IPayloadContext) {
        throw UnsupportedOperationException("Packet $this cannot be received on the client!")
    }

    fun receiveOnServer(context: IPayloadContext) {
        throw UnsupportedOperationException("Packet $this cannot be received on the server!")
    }
}