package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB

class AdvancedItemCollectorBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : ItemCollectorBlockEntity(ModBlockEntities.ADVANCED_ITEM_COLLECTOR.get(), pPos, pBlockState) {

    companion object {
        const val X_RADIUS_NBT = "XRadius"
        const val Y_RADIUS_NBT = "YRadius"
        const val Z_RADIUS_NBT = "ZRadius"
    }

    var xRadius: Double = 5.0
        set(value) {
            field = value.coerceIn(0.0, 10.0)
            setChanged()
        }

    var yRadius: Double = 5.0
        set(value) {
            field = value.coerceIn(0.0, 10.0)
            setChanged()
        }

    var zRadius: Double = 5.0
        set(value) {
            field = value.coerceIn(0.0, 10.0)
            setChanged()
        }

    val container = ImprovedSimpleContainer(this, 1)

    override fun getFilter(): ItemFilterDataComponent? {
        return this.container.getItem(0).get(ModDataComponents.ITEM_FILTER_ENTRIES)
    }

    override fun getCollectionArea(): AABB {
        val pos = this.blockPos
        return AABB.ofSize(
            pos.center,
            2 * this.xRadius,
            2 * this.yRadius,
            2 * this.zRadius
        )
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putDouble(X_RADIUS_NBT, this.xRadius)
        tag.putDouble(Y_RADIUS_NBT, this.yRadius)
        tag.putDouble(Z_RADIUS_NBT, this.zRadius)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        this.xRadius = tag.getDouble(X_RADIUS_NBT)
        this.yRadius = tag.getDouble(Y_RADIUS_NBT)
        this.zRadius = tag.getDouble(Z_RADIUS_NBT)
    }

}