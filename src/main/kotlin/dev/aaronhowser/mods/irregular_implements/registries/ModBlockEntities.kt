package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModBlockEntities {

    val BLOCK_ENTITY_REGISTRY: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IrregularImplements.ID)

    val RAIN_SHIELD: DeferredHolder<BlockEntityType<*>, BlockEntityType<RainShieldBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("rain_shield", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> RainShieldBlockEntity(pos, state) },
                ModBlocks.RAIN_SHIELD.get()
            ).build(null)
        })

    val CUSTOM_CRAFTING_TABLE: DeferredHolder<BlockEntityType<*>, BlockEntityType<CustomCraftingTableBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("custom_crafting_table", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> CustomCraftingTableBlockEntity(pos, state) },
                ModBlocks.CUSTOM_CRAFTING_TABLE.get()
            ).build(null)
        })

    val DIAPHANOUS_BLOCK: DeferredHolder<BlockEntityType<*>, BlockEntityType<DiaphanousBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("diaphanous_block", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> DiaphanousBlockEntity(pos, state) },
                ModBlocks.DIAPHANOUS_BLOCK.get()
            ).build(null)
        })

}