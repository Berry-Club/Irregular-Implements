package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class UpdateClientChatDetector(
    val stopsMessage: Boolean,
    val regexString: String
) : IModPacket {

    override fun receiveMessage(context: IPayloadContext) {
        context.enqueueWork {
            Companion.regexString = this.regexString
            Companion.stopsMessage = this.stopsMessage
        }
    }

    override fun type(): CustomPacketPayload.Type<UpdateClientChatDetector> {
        return TYPE
    }

    companion object {

        val TYPE: CustomPacketPayload.Type<UpdateClientChatDetector> =
            CustomPacketPayload.Type(OtherUtil.modResource("update_client_chat_detector"))

        val STREAM_CODEC: StreamCodec<ByteBuf, UpdateClientChatDetector> =
            StreamCodec.composite(
                ByteBufCodecs.BOOL, UpdateClientChatDetector::stopsMessage,
                ByteBufCodecs.STRING_UTF8, UpdateClientChatDetector::regexString,
                ::UpdateClientChatDetector
            )

        var stopsMessage: Boolean = false
            private set
        var regexString: String = ""
            private set

        fun unset() {
            regexString = ""
            stopsMessage = false
        }
    }

}