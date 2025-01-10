package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.menu.IronDropperMenu
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientClickedIronDropperButton(
    private val buttonClicked: Int
) : IModPacket {

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork

            val playerMenu = player.containerMenu as? IronDropperMenu ?: return@enqueueWork
            if (!playerMenu.stillValid(player)) return@enqueueWork

            playerMenu.handleButtonPressed(buttonClicked)
        }
    }

    override fun type(): CustomPacketPayload.Type<ClientClickedIronDropperButton> {
        return TYPE
    }

    companion object {
        val TYPE: CustomPacketPayload.Type<ClientClickedIronDropperButton> =
            CustomPacketPayload.Type(OtherUtil.modResource("client_clicked_iron_dropper_button"))

        val STREAM_CODEC: StreamCodec<ByteBuf, ClientClickedIronDropperButton> =
            StreamCodec.composite(
                ByteBufCodecs.VAR_INT, ClientClickedIronDropperButton::buttonClicked,
                ::ClientClickedIronDropperButton
            )

    }

}