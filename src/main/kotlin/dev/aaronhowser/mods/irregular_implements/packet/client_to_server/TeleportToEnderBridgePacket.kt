package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.item.PortableEnderBridgeItem
import dev.aaronhowser.mods.irregular_implements.packet.ModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class TeleportToEnderBridgePacket(
	val pos: BlockPos
) : ModPacket() {

	override fun handleOnServer(context: IPayloadContext) {
		PortableEnderBridgeItem.handlePacket(this, context)
	}

	override fun type(): CustomPacketPayload.Type<out CustomPacketPayload?> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<TeleportToEnderBridgePacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("teleport_to_ender_bridge"))

		val STREAM_CODEC: StreamCodec<ByteBuf, TeleportToEnderBridgePacket> =
			BlockPos.STREAM_CODEC.map(
				::TeleportToEnderBridgePacket,
				TeleportToEnderBridgePacket::pos
			)
	}

}