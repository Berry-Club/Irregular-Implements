package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.BlockTagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModBlockTagsProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper?
) : BlockTagsProvider(output, lookupProvider, IrregularImplements.ID, existingFileHelper) {

    companion object {
        private fun create(id: String): TagKey<Block> {
            return BlockTags.create(OtherUtil.modResource(id))
        }

        val SUPER_LUBRICATED = create("super_lubricated")
        val CANNOT_ACCELERATE = create("cannot_accelerate")
    }

    override fun addTags(provider: HolderLookup.Provider) {

        this.tag(SUPER_LUBRICATED)
            .add(
                ModBlocks.SUPER_LUBRICANT_ICE.get(),
                ModBlocks.SUPER_LUBRICANT_STONE.get(),
                ModBlocks.SUPER_LUBRICANT_PLATFORM.get(),
                ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM.get()
            )

        this.tag(CANNOT_ACCELERATE)
            .addTag(Tags.Blocks.CHESTS)

        val coloredGrassBlocks = DyeColor.entries.map { ModBlocks.getColoredGrass(it).get() }.toTypedArray()

        this.tag(BlockTags.DIRT)
            .add(
                *coloredGrassBlocks,
                ModBlocks.FERTILIZED_DIRT.get()
            )

        this.tag(BlockTags.VALID_SPAWN)
            .add(*coloredGrassBlocks)

        this.tag(BlockTags.ANIMALS_SPAWNABLE_ON)
            .add(*coloredGrassBlocks)

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .add(
                *coloredGrassBlocks,
                ModBlocks.FERTILIZED_DIRT.get()
            )

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(
                ModBlocks.LAPIS_LAMP.get(),
                ModBlocks.ENDER_BRIDGE.get(),
                ModBlocks.PRISMARINE_ENDER_BRIDGE.get(),
                ModBlocks.ENDER_ANCHOR.get(),
                ModBlocks.IMBUING_STATION.get(),
                ModBlocks.ANALOG_EMITTER.get(),
                ModBlocks.FLUID_DISPLAY.get(),
                ModBlocks.ENDER_MAILBOX.get(),
                ModBlocks.QUARTZ_LAMP.get(),
                ModBlocks.POTION_VAPORIZER.get(),
                ModBlocks.CONTACT_BUTTON.get(),
                ModBlocks.CONTACT_LEVER.get(),
                ModBlocks.RAIN_SHIELD.get(),
                ModBlocks.BLOCK_BREAKER.get(),
                ModBlocks.REDSTONE_OBSERVER.get(),
                ModBlocks.BIOME_RADAR.get(),
                ModBlocks.IRON_DROPPER.get(),
                ModBlocks.IGNITER.get(),
                ModBlocks.INVENTORY_REROUTER.get(),
                ModBlocks.PEACE_CANDLE.get(),
                ModBlocks.SOUND_BOX.get(),
                ModBlocks.SOUND_DAMPENER.get(),
                ModBlocks.SIDED_BLOCK_OF_REDSTONE.get(),
                ModBlocks.ITEM_COLLECTOR.get(),
                ModBlocks.ADVANCED_ITEM_COLLECTOR.get(),
                ModBlocks.RAINBOW_LAMP.get(),
                ModBlocks.SUPER_LUBRICANT_ICE.get(),
                ModBlocks.SUPER_LUBRICANT_STONE.get(),
                ModBlocks.SUPER_LUBRICANT_PLATFORM.get(),
                ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM.get(),
                ModBlocks.ONLINE_DETECTOR.get(),
                ModBlocks.CHAT_DETECTOR.get(),
                ModBlocks.GLOBAL_CHAT_DETECTOR.get(),
                ModBlocks.ENTITY_DETECTOR.get(),
                ModBlocks.PLAYER_INTERFACE.get(),
                ModBlocks.NOTIFICATION_INTERFACE.get(),
                ModBlocks.BASIC_REDSTONE_INTERFACE.get(),
                ModBlocks.ADVANCED_REDSTONE_INTERFACE.get(),
            )

        this.tag(BlockTags.MINEABLE_WITH_AXE)
            .add(
                ModBlocks.BLOCK_OF_STICKS.get(),
                ModBlocks.RETURNING_BLOCK_OF_STICKS.get(),
                ModBlocks.OAK_PLATFORM.get(),
                ModBlocks.SPRUCE_PLATFORM.get(),
                ModBlocks.BIRCH_PLATFORM.get(),
                ModBlocks.JUNGLE_PLATFORM.get(),
                ModBlocks.ACACIA_PLATFORM.get(),
                ModBlocks.DARK_OAK_PLATFORM.get(),
                ModBlocks.CRIMSON_PLATFORM.get(),
                ModBlocks.WARPED_PLATFORM.get(),
                ModBlocks.MANGROVE_PLATFORM.get(),
                ModBlocks.BAMBOO_PLATFORM.get(),
                ModBlocks.CHERRY_PLATFORM.get(),
            )

    }

}