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

class ClientClickedChatDetectorButton(
    private val buttonId: Int
) : IModPacket {

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork
            val playerMenu = player.containerMenu as? ChatDetectorMenu ?: return@enqueueWork

            if (!playerMenu.stillValid(player)) return@enqueueWork

            playerMenu.handleButtonPressed(buttonId)
        }
    }

    override fun type(): CustomPacketPayload.Type<ClientClickedChatDetectorButton> {
        return TYPE
    }

    companion object {
        val TYPE: CustomPacketPayload.Type<ClientClickedChatDetectorButton> =
            CustomPacketPayload.Type(OtherUtil.modResource("client_clicked_chat_detector_button"))

        val STREAM_CODEC: StreamCodec<ByteBuf, ClientClickedChatDetectorButton> =
            StreamCodec.composite(
                ByteBufCodecs.VAR_INT, ClientClickedChatDetectorButton::buttonId,
                ::ClientClickedChatDetectorButton
            )
    }
}