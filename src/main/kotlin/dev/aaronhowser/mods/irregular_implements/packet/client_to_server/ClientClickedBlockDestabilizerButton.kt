package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDestabilizerBlockEntity
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientBlockDestabilizer
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.ai.attributes.Attributes
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientClickedBlockDestabilizerButton(
    val blockPos: BlockPos,
    val buttonClicked: Button
) : IModPacket {

    enum class Button { TOGGLE_LAZY, SHOW_LAZY_SHAPE, RESET_LAZY_SHAPE }

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork
            val blockDestabilizerBlockEntity = player.level().getBlockEntity(blockPos) as? BlockDestabilizerBlockEntity
                ?: return@enqueueWork

            val playerReach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE)
            if (!player.canInteractWithBlock(blockPos, playerReach)) return@enqueueWork

            when (buttonClicked) {
                Button.TOGGLE_LAZY -> {
                    blockDestabilizerBlockEntity.toggleLazy()

                    ModPacketHandler.messagePlayer(
                        player,
                        UpdateClientBlockDestabilizer(blockDestabilizerBlockEntity.isLazy)
                    )
                }

                Button.SHOW_LAZY_SHAPE -> blockDestabilizerBlockEntity.showLazyShape()  //TODO: Close screen if returns true

                Button.RESET_LAZY_SHAPE -> blockDestabilizerBlockEntity.resetLazyShape()
            }
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
                BlockPos.STREAM_CODEC, ClientClickedBlockDestabilizerButton::blockPos,
                NeoForgeStreamCodecs.enumCodec(Button::class.java), ClientClickedBlockDestabilizerButton::buttonClicked,
                ::ClientClickedBlockDestabilizerButton
            )
    }

}