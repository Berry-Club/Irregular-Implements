package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.BlockTagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModBlockTagsProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper
) : BlockTagsProvider(output, lookupProvider, IrregularImplements.ID, existingFileHelper) {

    companion object {
        private fun create(id: String): TagKey<Block> {
            return BlockTags.create(OtherUtil.modResource(id))
        }

        private fun common(id: String): TagKey<Block> {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", id))
        }

        @JvmStatic
        val SUPER_LUBRICATED = create("super_lubricated")
        val C_CROPS_BEAN = common("crops/bean")
        val BLOCK_MOVER_BLACKLIST = create("block_mover_blacklist")
        val BLOCK_REPLACER_BLACKLIST = create("block_replacer_blacklist")
        val BLOCK_DESTABILIZER_BLACKLIST = create("block_destabilizer_blacklist")
        val DIAPHANOUS_BLOCK_BLACKLIST = create("diaphanous_block_blacklist")
        val C_STRIPPED_WOODS = common("stripped_woods")
    }

    override fun addTags(provider: HolderLookup.Provider) {

        this.tag(BlockTags.REPLACEABLE_BY_TREES)
            .add(
                ModBlocks.SPECTRE_LEAVES.get()
            )

        this.tag(BlockTags.LEAVES)
            .add(
                ModBlocks.SPECTRE_LEAVES.get()
            )

        this.tag(C_STRIPPED_WOODS)
            .add(
                ModBlocks.STRIPPED_SPECTRE_LOG.get()
            )

        this.tag(BlockTags.LOGS_THAT_BURN)
            .add(
                ModBlocks.SPECTRE_LOG.get(),
                ModBlocks.STRIPPED_SPECTRE_LOG.get(),
                ModBlocks.SPECTRE_WOOD.get()
            )

        this.tag(BlockTags.PLANKS)
            .add(
                ModBlocks.SPECTRE_PLANKS.get()
            )

        this.tag(DIAPHANOUS_BLOCK_BLACKLIST)
            .add(
                Blocks.CHAIN,
                Blocks.CACTUS
            )

        this.tag(BLOCK_DESTABILIZER_BLACKLIST)
            .add(
                Blocks.BEDROCK,
                Blocks.BARRIER,
                Blocks.COMMAND_BLOCK,
                Blocks.CHAIN_COMMAND_BLOCK,
                Blocks.REPEATING_COMMAND_BLOCK,
                Blocks.STRUCTURE_BLOCK,
                Blocks.JIGSAW,
                Blocks.STRUCTURE_VOID,
                Blocks.END_PORTAL_FRAME,
                Blocks.AIR,
            )

        this.tag(BLOCK_REPLACER_BLACKLIST)
            .add(
                Blocks.BEDROCK,
                Blocks.BARRIER,
                Blocks.COMMAND_BLOCK,
                Blocks.CHAIN_COMMAND_BLOCK,
                Blocks.REPEATING_COMMAND_BLOCK,
                Blocks.STRUCTURE_BLOCK,
                Blocks.JIGSAW,
                Blocks.STRUCTURE_VOID,
                Blocks.END_PORTAL_FRAME,
                Blocks.AIR,
            )

        this.tag(BLOCK_MOVER_BLACKLIST)
            .add(
                Blocks.BEDROCK,
                Blocks.BARRIER,
                Blocks.COMMAND_BLOCK,
                Blocks.CHAIN_COMMAND_BLOCK,
                Blocks.REPEATING_COMMAND_BLOCK,
                Blocks.STRUCTURE_BLOCK,
                Blocks.JIGSAW,
                Blocks.STRUCTURE_VOID,
                Blocks.END_PORTAL_FRAME,
            )
            .addTag(
                BlockTags.PORTALS,
            )

        colorTags()

        this.tag(Tags.Blocks.GLASS_BLOCKS)
            .add(
                ModBlocks.BIOME_GLASS.get(),
                ModBlocks.LAPIS_GLASS.get(),
                ModBlocks.QUARTZ_GLASS.get(),
                ModBlocks.TRIGGER_GLASS.get(),
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
            )

        this.tag(BlockTags.STONE_BRICKS)
            .add(
                ModBlocks.BIOME_STONE_BRICKS.get(),
                ModBlocks.BIOME_STONE_BRICKS_CHISELED.get(),
                ModBlocks.BIOME_STONE_BRICKS_CRACKED.get(),
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

        this.tag(Tags.Blocks.COBBLESTONES)
            .add(
                ModBlocks.BIOME_COBBLESTONE.get()
            )

        this.tag(Tags.Blocks.STONES)
            .add(
                ModBlocks.BIOME_STONE.get()
            )

        this.tag(BlockTags.SAPLINGS)
            .add(
                ModBlocks.SPECTRE_SAPLING.get()
            )

        this.tag(BlockTags.WITHER_IMMUNE)
            .add(
                ModBlocks.SPECTRE_BLOCK.get()
            )

        this.tag(BlockTags.CLIMBABLE)
            .add(
                ModBlocks.BEAN_STALK.get(),
                ModBlocks.LESSER_BEAN_STALK.get()
            )

        this.tag(C_CROPS_BEAN)
            .add(
                ModBlocks.BEAN_SPROUT.get()
            )

        this.tag(BlockTags.CROPS)
            .add(
                ModBlocks.LOTUS.get()
            )
            .addTag(C_CROPS_BEAN)

        this.tag(BlockTags.SMALL_FLOWERS)
            .add(
                ModBlocks.LOTUS.get(),
                ModBlocks.PITCHER_PLANT.get()
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
                ModBlocks.ENDER_BRIDGE.get(),
                ModBlocks.PRISMARINE_ENDER_BRIDGE.get(),
                ModBlocks.ENDER_ANCHOR.get(),
                ModBlocks.IMBUING_STATION.get(),
                ModBlocks.ANALOG_EMITTER.get(),
                ModBlocks.FLUID_DISPLAY.get(),
                ModBlocks.ENDER_MAILBOX.get(),
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
                ModBlocks.SPECTRE_COIL_BASIC.get(),
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
                ModBlocks.ITEM_REJUVENATOR_PLATE.get(),
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
                ModBlocks.SLIME_CUBE.get(),
                ModBlocks.INVENTORY_TESTER.get(),
                ModBlocks.BLOCK_DESTABILIZER.get()
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
                ModBlocks.SPECTRE_LOG.get(),
                ModBlocks.STRIPPED_SPECTRE_LOG.get(),
                ModBlocks.BEAN_STALK.get(),
                ModBlocks.LESSER_BEAN_STALK.get(),
            )

        this.tag(BlockTags.MINEABLE_WITH_HOE)
            .add(
                ModBlocks.SPECTRE_LEAVES.get()
            )

    }

    private fun colorTags() {

        for (color in DyeColor.entries) {
            val coloredGrass = ModBlocks.getColoredGrass(color).get()
            val stainedBrick = ModBlocks.getStainedBrick(color).get()
            val luminousStainedBrick = ModBlocks.getStainedBrickLuminous(color).get()
            val luminousBlock = ModBlocks.getLuminousBlock(color).get()
            val translucentLuminousBlock = ModBlocks.getLuminousBlockTranslucent(color).get()

            this.tag(Tags.Blocks.DYED)
                .add(
                    coloredGrass,
                    stainedBrick,
                    luminousStainedBrick,
                    luminousBlock,
                    translucentLuminousBlock
                )

            val coloredTag = BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "dyed/" + color.getName()))

            this.tag(coloredTag)
                .add(
                    coloredGrass,
                    stainedBrick,
                    luminousStainedBrick,
                    luminousBlock,
                    translucentLuminousBlock
                )

        }
    }

}