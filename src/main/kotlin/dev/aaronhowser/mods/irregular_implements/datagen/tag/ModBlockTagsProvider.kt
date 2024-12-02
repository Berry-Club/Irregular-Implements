package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
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

        @JvmStatic
        val SUPER_LUBRICATED = create("super_lubricated")
    }

    override fun addTags(provider: HolderLookup.Provider) {

        this.tag(BlockTags.SMALL_FLOWERS)
            .add(
                ModBlocks.LOTUS.get()
            )

        this.tag(SUPER_LUBRICATED)
            .add(
                ModBlocks.SUPER_LUBRICANT_ICE.get(),
                ModBlocks.SUPER_LUBRICANT_STONE.get(),
                ModBlocks.SUPER_LUBRICANT_PLATFORM.get(),
                ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM.get()
            )

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
                ModBlocks.SPECTRE_LENS.get(),
                ModBlocks.SPECTRE_ENERGY_INJECTOR.get(),
                ModBlocks.SPECTRE_COIL.get(),
                ModBlocks.SPECTRE_COIL_REDSTONE.get(),
                ModBlocks.SPECTRE_COIL_ENDER.get(),
                ModBlocks.SPECTRE_COIL_NUMBER.get(),
                ModBlocks.SPECTRE_COIL_GENESIS.get(),
                ModBlocks.BIOME_COBBLESTONE.get(),
                ModBlocks.BIOME_STONE.get(),
                ModBlocks.BIOME_STONE_BRICKS.get(),
                ModBlocks.BIOME_STONE_BRICKS_CRACKED.get(),
                ModBlocks.BIOME_STONE_BRICKS_CHISELED.get(),
                ModBlocks.PROCESSING_PLATE.get(),
                ModBlocks.REDIRECTOR_PLATE.get(),
                ModBlocks.FILTERED_REDIRECTOR_PLATE.get(),
                ModBlocks.REDSTONE_PLATE.get(),
                ModBlocks.CORRECTOR_PLATE.get(),
                ModBlocks.ITEM_SEALER_PLATE.get(),
                ModBlocks.ITEM_REJUVINATOR_PLATE.get(),
                ModBlocks.ACCELERATOR_PLATE.get(),
                ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE.get(),
                ModBlocks.BOUNCY_PLATE.get(),
                ModBlocks.COLLECTION_PLATE.get(),
                ModBlocks.EXTRACTION_PLATE.get(),
                ModBlocks.LUMINOUS_BLOCK_WHITE.get(),
                ModBlocks.LUMINOUS_BLOCK_ORANGE.get(),
                ModBlocks.LUMINOUS_BLOCK_MAGENTA.get(),
                ModBlocks.LUMINOUS_BLOCK_LIGHT_BLUE.get(),
                ModBlocks.LUMINOUS_BLOCK_YELLOW.get(),
                ModBlocks.LUMINOUS_BLOCK_LIME.get(),
                ModBlocks.LUMINOUS_BLOCK_PINK.get(),
                ModBlocks.LUMINOUS_BLOCK_GRAY.get(),
                ModBlocks.LUMINOUS_BLOCK_LIGHT_GRAY.get(),
                ModBlocks.LUMINOUS_BLOCK_CYAN.get(),
                ModBlocks.LUMINOUS_BLOCK_PURPLE.get(),
                ModBlocks.LUMINOUS_BLOCK_BLUE.get(),
                ModBlocks.LUMINOUS_BLOCK_BROWN.get(),
                ModBlocks.LUMINOUS_BLOCK_GREEN.get(),
                ModBlocks.LUMINOUS_BLOCK_RED.get(),
                ModBlocks.LUMINOUS_BLOCK_BLACK.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_WHITE.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_ORANGE.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_MAGENTA.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_BLUE.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_YELLOW.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_LIME.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_PINK.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_GRAY.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_GRAY.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_CYAN.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_PURPLE.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_BLUE.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_BROWN.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_GREEN.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_RED.get(),
                ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_BLACK.get(),
                ModBlocks.STAINED_BRICKS_WHITE.get(),
                ModBlocks.STAINED_BRICKS_ORANGE.get(),
                ModBlocks.STAINED_BRICKS_MAGENTA.get(),
                ModBlocks.STAINED_BRICKS_LIGHT_BLUE.get(),
                ModBlocks.STAINED_BRICKS_YELLOW.get(),
                ModBlocks.STAINED_BRICKS_LIME.get(),
                ModBlocks.STAINED_BRICKS_PINK.get(),
                ModBlocks.STAINED_BRICKS_GRAY.get(),
                ModBlocks.STAINED_BRICKS_LIGHT_GRAY.get(),
                ModBlocks.STAINED_BRICKS_CYAN.get(),
                ModBlocks.STAINED_BRICKS_PURPLE.get(),
                ModBlocks.STAINED_BRICKS_BLUE.get(),
                ModBlocks.STAINED_BRICKS_BROWN.get(),
                ModBlocks.STAINED_BRICKS_GREEN.get(),
                ModBlocks.STAINED_BRICKS_RED.get(),
                ModBlocks.STAINED_BRICKS_BLACK.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_WHITE.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_ORANGE.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_MAGENTA.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_LIGHT_BLUE.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_YELLOW.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_LIME.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_PINK.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_GRAY.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_LIGHT_GRAY.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_CYAN.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_PURPLE.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_BLUE.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_BROWN.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_GREEN.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_RED.get(),
                ModBlocks.LUMINOUS_STAINED_BRICKS_BLACK.get(),
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
                ModBlocks.SPECTRE_PLANKS.get(),
                ModBlocks.SPECTRE_WOOD.get()
            )

        this.tag(BlockTags.MINEABLE_WITH_HOE)
            .add(
                ModBlocks.SPECTRE_LEAVES.get()
            )

    }

}