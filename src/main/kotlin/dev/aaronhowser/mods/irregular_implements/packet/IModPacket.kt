package dev.aaronhowser.mods.irregular_implements.packet

import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

interface IModPacket : CustomPacketPayload {
    fun receiveMessage(context: IPayloadContext)
}