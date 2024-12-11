package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BucketPickup
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.FluidType
import java.util.*

// Copied pretty much verbatim from Mekanism's data component they use for Cardboard Boxes
data class BlockDataComponent(
    val blockState: BlockState,
    val blockEntityNbt: CompoundTag?
) {

    constructor(provider: HolderLookup.Provider, blockState: BlockState, blockEntity: BlockEntity?) :
            this(blockState, blockEntity?.saveWithFullMetadata(provider))

    companion object {
        val CODEC: Codec<BlockDataComponent> =
            RecordCodecBuilder.create { instance ->
                instance.group(
                    BlockState.CODEC.fieldOf("block_state")
                        .forGetter(BlockDataComponent::blockState),
                    CompoundTag.CODEC.optionalFieldOf("block_entity_nbt", null)
                        .forGetter(BlockDataComponent::blockEntityNbt)
                ).apply(instance, ::BlockDataComponent)
            }

        val STREAM_CODEC: StreamCodec<ByteBuf, BlockDataComponent> =
            StreamCodec.composite(
                ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), BlockDataComponent::blockState,
                ByteBufCodecs.optional(ByteBufCodecs.TRUSTED_COMPOUND_TAG), { Optional.ofNullable(it.blockEntityNbt) },
                { state, tag -> BlockDataComponent(state, tag.orElse(null)) }
            )
    }

    fun tryPlace(level: Level, posToPlaceIn: BlockPos, player: Player?): Boolean {

        // Update the shape so double chests become single, etc
        val adjustedState = Block.updateFromNeighbourShapes(blockState, level, posToPlaceIn)
        if (adjustedState.isAir) {
            // This means you can't place the block here
            return false
        }

        val fluidState = level.getFluidState(posToPlaceIn)
        val fluidType = fluidState.fluidType
        val fluidStack = FluidStack(fluidState.type, FluidType.BUCKET_VOLUME)

        var fluidPickup: BucketPickup? = null
        if (fluidType.isVaporizedOnPlacement(level, posToPlaceIn, fluidStack)) {
            val adjustedStateBlock = adjustedState.block

            if (ServerConfig.BLOCK_MOVER_TRY_VAPORIZE_FLUID.get() && adjustedStateBlock is BucketPickup) {
                fluidPickup = adjustedStateBlock
            } else {
                return false
            }
        }

        level.setBlockAndUpdate(posToPlaceIn, adjustedState)

        if (blockEntityNbt != null) {

            blockEntityNbt.putInt("x", posToPlaceIn.x)
            blockEntityNbt.putInt("y", posToPlaceIn.y)
            blockEntityNbt.putInt("z", posToPlaceIn.z)

            level.getBlockEntity(posToPlaceIn)
                ?.loadWithComponents(blockEntityNbt, level.registryAccess())
        }

        if (fluidPickup != null) {
            if (!fluidPickup.pickupBlock(player, level, posToPlaceIn, adjustedState).isEmpty) {
                fluidType.onVaporize(null, level, posToPlaceIn, fluidStack)
            }
        }

        return true
    }

}