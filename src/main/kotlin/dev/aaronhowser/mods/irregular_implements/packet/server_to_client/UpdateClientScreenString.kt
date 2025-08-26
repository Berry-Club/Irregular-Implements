package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.menu.ScreenWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.ModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class UpdateClientScreenString(
	val stringId: Int,
	val newString: String
) : ModPacket() {

	override fun handleOnClient(context: IPayloadContext) {
		val screen = Minecraft.getInstance().screen ?: return

		if (screen is ScreenWithStrings) {
			screen.receivedString(stringId, newString)
		}
	}

	override fun type(): CustomPacketPayload.Type<UpdateClientScreenString> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<UpdateClientScreenString> =
			CustomPacketPayload.Type(OtherUtil.modResource("update_client_screen_string"))

		val STREAM_CODEC: StreamCodec<ByteBuf, UpdateClientScreenString> =
			StreamCodec.composite(
				ByteBufCodecs.VAR_INT, UpdateClientScreenString::stringId,
				ByteBufCodecs.STRING_UTF8, UpdateClientScreenString::newString,
				::UpdateClientScreenString
			)
	}

}