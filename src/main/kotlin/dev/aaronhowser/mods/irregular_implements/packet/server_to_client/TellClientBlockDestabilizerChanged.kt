package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class TellClientBlockDestabilizerChanged(
    val isLazy: Boolean
) : IModPacket {

    override fun receiveMessage(context: IPayloadContext) {
        context.enqueueWork {
            Companion.isLazy = this.isLazy
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return TYPE
    }

    companion object {
        val TYPE: CustomPacketPayload.Type<TellClientBlockDestabilizerChanged> =
            CustomPacketPayload.Type(OtherUtil.modResource("tell_client_block_destabilizer_changed"))

        val STREAM_CODEC: StreamCodec<ByteBuf, TellClientBlockDestabilizerChanged> =
            StreamCodec.composite(
                ByteBufCodecs.BOOL, TellClientBlockDestabilizerChanged::isLazy,
                ::TellClientBlockDestabilizerChanged
            )

        var isLazy: Boolean = false
            private set

        fun unset() {
            isLazy = false
        }

    }

}