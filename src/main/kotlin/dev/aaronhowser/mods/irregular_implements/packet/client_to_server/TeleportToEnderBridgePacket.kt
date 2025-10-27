package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.item.PortableEnderBridgeItem
import dev.aaronhowser.mods.irregular_implements.packet.ModPacket
import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class TeleportToEnderBridgePacket(
	val pos: BlockPos
) : ModPacket() {

	override fun handleOnServer(context: IPayloadContext) {
		PortableEnderBridgeItem.handlePacket(this, context)
	}

	override fun type(): CustomPacketPayload.Type<out CustomPacketPayload?> {
		TODO("Not yet implemented")
	}

}