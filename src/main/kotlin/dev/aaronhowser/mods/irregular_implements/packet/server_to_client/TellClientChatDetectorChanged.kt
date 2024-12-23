package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class TellClientChatDetectorChanged(
    val stopsMessage: Boolean,
    val message: String
) : IModPacket {

    override fun receiveMessage(context: IPayloadContext) {
        context.enqueueWork {
            Companion.message = this.message
            Companion.stopsMessage = this.stopsMessage
        }
    }

    override fun type(): CustomPacketPayload.Type<TellClientChatDetectorChanged> {
        return TYPE
    }

    companion object {

        val TYPE: CustomPacketPayload.Type<TellClientChatDetectorChanged> =
            CustomPacketPayload.Type(OtherUtil.modResource("tell_client_chat_detector_changed"))

        val STREAM_CODEC: StreamCodec<ByteBuf, TellClientChatDetectorChanged> =
            StreamCodec.composite(
                ByteBufCodecs.BOOL, TellClientChatDetectorChanged::stopsMessage,
                ByteBufCodecs.STRING_UTF8, TellClientChatDetectorChanged::message,
                ::TellClientChatDetectorChanged
            )

        var stopsMessage: Boolean = false
            private set
        var message: String = ""
            private set
    }

}