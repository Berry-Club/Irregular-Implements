package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.menu.BlockDestabilizerMenu
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientClickedBlockDestabilizerButton(
    val buttonClicked: Int
) : IModPacket {

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork
            val playerMenu = player.containerMenu as? BlockDestabilizerMenu ?: return@enqueueWork

            playerMenu.handleButtonPressed(buttonClicked)
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return TYPE
    }

    companion object {
        val TYPE: CustomPacketPayload.Type<ClientClickedBlockDestabilizerButton> =
            CustomPacketPayload.Type(OtherUtil.modResource("client_clicked_block_destabilizer_button"))

        val STREAM_CODEC: StreamCodec<FriendlyByteBuf, ClientClickedBlockDestabilizerButton> =
            StreamCodec.composite(
                ByteBufCodecs.VAR_INT, ClientClickedBlockDestabilizerButton::buttonClicked,
                ::ClientClickedBlockDestabilizerButton
            )
    }

}