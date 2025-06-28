package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientChangedMenuString(
	val stringId: Int,
	val string: String
) : IModPacket {

	override fun receiveOnServer(context: IPayloadContext) {
		context.enqueueWork {
			val player = context.player() as? ServerPlayer ?: return@enqueueWork
			val playerMenu = player.containerMenu

			if (playerMenu is MenuWithStrings) {
				playerMenu.receiveString(stringId, string)
			}
		}
	}

	override fun type(): CustomPacketPayload.Type<ClientChangedMenuString> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<ClientChangedMenuString> =
			CustomPacketPayload.Type(OtherUtil.modResource("client_changed_menu_string"))

		val STREAM_CODEC: StreamCodec<ByteBuf, ClientChangedMenuString> =
			StreamCodec.composite(
				ByteBufCodecs.VAR_INT, ClientChangedMenuString::stringId,
				ByteBufCodecs.STRING_UTF8, ClientChangedMenuString::string,
				::ClientChangedMenuString
			)

	}

}