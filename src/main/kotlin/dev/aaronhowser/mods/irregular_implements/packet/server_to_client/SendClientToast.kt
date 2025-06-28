package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.client.NotificationToast
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.Minecraft
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.network.handling.IPayloadContext

class SendClientToast(
	val title: String,
	val description: String,
	val icon: ItemStack?
) : IModPacket {

	override fun receiveOnClient(context: IPayloadContext) {
		context.enqueueWork {
			val toast = NotificationToast(title, description, icon)
			Minecraft.getInstance().toasts.addToast(toast)
		}
	}

	override fun type(): CustomPacketPayload.Type<SendClientToast> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<SendClientToast> =
			CustomPacketPayload.Type(OtherUtil.modResource("send_client_toast"))

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, SendClientToast> =
			StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8, SendClientToast::title,
				ByteBufCodecs.STRING_UTF8, SendClientToast::description,
				ItemStack.OPTIONAL_STREAM_CODEC, SendClientToast::icon,
				::SendClientToast
			)
	}

}