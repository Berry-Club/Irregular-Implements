package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.IronDropperScreen
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientClickedIronDropperButton(
    val blockPos: BlockPos,
    val buttonClicked: Int
) : IModPacket {

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork

            val blockEntity = player.level().getBlockEntity(blockPos) as? IronDropperBlockEntity
                ?: return@enqueueWork

            when (buttonClicked) {
                IronDropperScreen.SHOOT_MODE_BUTTON_ID -> blockEntity.toggleShootStraight()
                IronDropperScreen.TOGGLE_EFFECT_BUTTON_ID -> blockEntity.toggleShouldHaveEffects()
                IronDropperScreen.DELAY_BUTTON_ID -> blockEntity.cyclePickupDelay()
                IronDropperScreen.REDSTONE_MODE_BUTTON_ID -> blockEntity.cycleRedstoneMode()
                else -> IrregularImplements.LOGGER.warn("Unknown button clicked in ClientClickedIronDropperButton packet: $buttonClicked")
            }
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
                BlockPos.STREAM_CODEC, ClientClickedIronDropperButton::blockPos,
                ByteBufCodecs.INT, ClientClickedIronDropperButton::buttonClicked,
                ::ClientClickedIronDropperButton
            )

    }

}