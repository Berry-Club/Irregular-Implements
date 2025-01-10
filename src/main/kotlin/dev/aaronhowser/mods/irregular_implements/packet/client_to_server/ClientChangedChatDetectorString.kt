package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.menu.ChatDetectorMenu
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientChangedChatDetectorString(
    val regexString: String
) : IModPacket {

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork
            val playerMenu = player.containerMenu as? ChatDetectorMenu ?: return@enqueueWork

            playerMenu.setRegex(regexString)
        }
    }

    override fun type(): CustomPacketPayload.Type<ClientChangedChatDetectorString> {
        return TYPE
    }

    companion object {
        val TYPE: CustomPacketPayload.Type<ClientChangedChatDetectorString> =
            CustomPacketPayload.Type(OtherUtil.modResource("client_changed_chat_detector"))

        val STREAM_CODEC: StreamCodec<ByteBuf, ClientChangedChatDetectorString> =
            StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, ClientChangedChatDetectorString::regexString,
                ::ClientChangedChatDetectorString
            )

    }

}