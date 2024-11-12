package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.registries.ModEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3

class TimeAcceleratorEntity(
    entityType: EntityType<TimeAcceleratorEntity>,
    level: Level
) : Entity(entityType, level) {

    constructor(level: Level, pos: BlockPos) : this(ModEntityTypes.TIME_ACCELERATOR.get(), level) {
        this.setPos(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
    }

    companion object {
        val TICK_RATE: EntityDataAccessor<Int> = SynchedEntityData.defineId(TimeAcceleratorEntity::class.java, EntityDataSerializers.INT)
        val TICKS_REMAINING: EntityDataAccessor<Int> = SynchedEntityData.defineId(TimeAcceleratorEntity::class.java, EntityDataSerializers.INT)

        const val TICK_RATE_NBT = "tick_rate"
        const val TICKS_REMAINING_NBT = "ticks_remaining"
    }

    override fun onAddedToLevel() {
        tickRate = 1
        ticksRemaining = ServerConfig.TIAB_TICKS_PER
    }

    override fun tick() {
        val level = level() as? ServerLevel ?: return

        val pos = blockPosition()
        val blockState = level.getBlockState(pos)

        if (blockState.`is`(ModBlockTagsProvider.CANNOT_ACCELERATE)) {
            this.discard()
            return
        }

        val blockEntity = level.getBlockEntity(pos)

        @Suppress("UNCHECKED_CAST")
        val blockEntityTicker =
            blockEntity?.blockState?.getTicker(level, blockEntity.type) as? BlockEntityTicker<BlockEntity>?

        var ticked = false
        for (i in 0 until tickRate) {
            // Why that number?
            if (blockState.isRandomlyTicking && level.random.nextInt(1385) == 0) {
                blockState.randomTick(level, pos, level.random)
                ticked = true
            }

            if (blockEntityTicker != null && blockEntity.type.isValid(blockState)) {
                blockEntityTicker.tick(level, pos, blockState, blockEntity)
                ticked = true
            }

            if (!ticked) {
                this.discard()
                return
            }
        }

        ticksRemaining--
        if (ticksRemaining <= 0) {
            this.discard()
        }

    }

    var tickRate: Int
        get() = entityData.get(TICK_RATE)
        set(value) = entityData.set(TICK_RATE, value)

    var ticksRemaining: Int
        get() = entityData.get(TICKS_REMAINING)
        set(value) = entityData.set(TICKS_REMAINING, value)

    override fun readAdditionalSaveData(compound: CompoundTag) {
        tickRate = compound.getInt(TICK_RATE_NBT)
        ticksRemaining = compound.getInt(TICKS_REMAINING_NBT)
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
        compound.putInt(TICK_RATE_NBT, tickRate)
        compound.putInt(TICKS_REMAINING_NBT, ticksRemaining)
    }

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        builder.define(TICK_RATE, 1)
        builder.define(TICKS_REMAINING, 0)
    }

    override fun isColliding(pos: BlockPos, state: BlockState): Boolean = false

    override fun limitPistonMovement(pos: Vec3): Vec3 = Vec3.ZERO

}