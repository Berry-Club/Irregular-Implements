package dev.aaronhowser.mods.irregular_implements.entity

import com.mojang.math.Transformation
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Display.BlockDisplay
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import org.joml.Vector3f

class IndicatorDisplayEntity(
    entityType: EntityType<*>,
    level: Level
) : BlockDisplay(entityType, level) {

    companion object {
        private const val MAX_AGE_NBT = "max_age"

        val defaultTransformation = Transformation(
            null,
            null,
            Vector3f(0.5f, 0.5f, 0.5f),
            null
        )
    }

    constructor(
        level: Level,
        blockState: BlockState,
        glowColorOverride: Int,
        maximumAge: Int,
    ) : this(ModEntityTypes.INDICATOR_DISPLAY.get(), level) {
        this.maximumAge = maximumAge

        setGlowingTag(true)
        setGlowColorOverride(glowColorOverride)

        setBlockState(blockState)
        setTransformation(defaultTransformation)
    }

    private var maximumAge = 20

    override fun tick() {
        super.tick()

        if (this.tickCount >= maximumAge) {
            kill()
        }
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
        super.addAdditionalSaveData(compound)

        compound.putInt(MAX_AGE_NBT, maximumAge)
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        super.readAdditionalSaveData(compound)

        maximumAge = compound.getInt(MAX_AGE_NBT)
    }

    public override fun setBlockState(blockState: BlockState) {
        super.setBlockState(blockState)
    }

    public override fun setGlowColorOverride(glowColorOverride: Int) {
        super.setGlowColorOverride(glowColorOverride)
    }

    public override fun setTransformation(transformation: Transformation) {
        super.setTransformation(transformation)
    }

}