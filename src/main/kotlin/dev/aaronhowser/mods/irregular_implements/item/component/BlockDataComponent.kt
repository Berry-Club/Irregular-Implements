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
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BucketPickup
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.level.BlockEvent
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

	fun tryPlace(level: Level, posToPlaceIn: BlockPos, player: Player?): Boolean {

		// Update the shape so double chests become single, etc
		val adjustedState = Block.updateFromNeighbourShapes(this.blockState, level, posToPlaceIn)
		val stateAlreadyThere = level.getBlockState(posToPlaceIn)

		if (adjustedState.isAir
			|| !stateAlreadyThere.canBeReplaced()
			|| !level.isUnobstructed(adjustedState, posToPlaceIn, if (player == null) CollisionContext.empty() else CollisionContext.of(player))
			|| !adjustedState.canSurvive(level, posToPlaceIn)
		) {
			return false
		}

		level.captureBlockSnapshots = true
		level.setBlockAndUpdate(posToPlaceIn, adjustedState)
		level.captureBlockSnapshots = false

		val snapshots = level.capturedBlockSnapshots.toList()
		level.capturedBlockSnapshots.clear()

		val snapshot = snapshots.firstOrNull() ?: return false

		if (NeoForge.EVENT_BUS.post(BlockEvent.EntityPlaceEvent(snapshot, stateAlreadyThere, player)).isCanceled) {
			level.restoringBlockSnapshots = true
			snapshot.restore(snapshot.flags or Block.UPDATE_CLIENTS)
			level.restoringBlockSnapshots = false
			return false
		}

		val soundType = adjustedState.block.getSoundType(adjustedState, level, posToPlaceIn, player)
		level.playSound(
			null,
			posToPlaceIn,
			soundType.placeSound,
			SoundSource.BLOCKS,
			(soundType.volume + 1.0f) / 2.0f,
			soundType.pitch * 0.8f
		)

		if (this.blockEntityNbt != null) {

			this.blockEntityNbt.putInt("x", posToPlaceIn.x)
			this.blockEntityNbt.putInt("y", posToPlaceIn.y)
			this.blockEntityNbt.putInt("z", posToPlaceIn.z)

			level.getBlockEntity(posToPlaceIn)
				?.loadWithComponents(this.blockEntityNbt, level.registryAccess())
		}

		val fluidState = level.getFluidState(posToPlaceIn)
		val fluidType = fluidState.fluidType
		val fluidStack = FluidStack(fluidState.type, FluidType.BUCKET_VOLUME)

		//TODO: Test this
		var fluidPickup: BucketPickup? = null
		if (ServerConfig.CONFIG.blockMoverTryVaporizeFluid.get() && fluidType.isVaporizedOnPlacement(level, posToPlaceIn, fluidStack)) {
			val adjustedStateBlock = adjustedState.block

			if (adjustedStateBlock is BucketPickup) {
				fluidPickup = adjustedStateBlock
			} else {
				return false
			}
		}

		if (fluidPickup != null) {
			if (!fluidPickup.pickupBlock(player, level, posToPlaceIn, adjustedState).isEmpty) {
				fluidType.onVaporize(null, level, posToPlaceIn, fluidStack)
			}
		}

		return true
	}


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

}