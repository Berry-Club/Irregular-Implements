package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CustomCraftingTableBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.CUSTOM_CRAFTING_TABLE.get(), pPos, pBlockState) {

    companion object {
        const val BLOCK_NBT = "Block"
    }

    var renderedBlock: Block = Blocks.OAK_PLANKS

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putString(BLOCK_NBT, BuiltInRegistries.BLOCK.getKey(this.renderedBlock).toString())
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        val blockString = tag.getString(BLOCK_NBT)
        val blockRl = ResourceLocation.tryParse(blockString)

        if (blockRl != null) {
            val block = BuiltInRegistries.BLOCK.getOptional(blockRl)

            if (block.isPresent) {
                this.renderedBlock = block.get()
            } else {
                IrregularImplements.LOGGER.error("A Custom Crafting Table at ${blockPos.x} ${blockPos.y} ${blockPos.z} tried to load a block that doesn't exist: $blockString")
            }
        }
    }

}