package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.*
import net.minecraft.world.food.Foods
import net.minecraft.world.item.*
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object ModItems {

    //TODO: Check rarity for all items

    val ITEM_REGISTRY: DeferredRegister.Items =
        DeferredRegister.createItems(IrregularImplements.ID)

    val STABLE_ENDER_PEARL: DeferredItem<StableEnderPearlItem> =
        register("stable_ender_pearl") { StableEnderPearlItem() }
    val PRECIOUS_EMERALD = basic("precious_emerald") //Click on a villager to start a "festival" in the village
    val EVIL_TEAR = basic("evil_tear")  // Used for artificial end portal
    val PORTKEY: DeferredItem<PortkeyItem> =
        register("portkey") { PortkeyItem() }
    val BIOME_CRYSTAL: DeferredItem<BiomeCrystalItem> =
        register("biome_crystal") { BiomeCrystalItem() }
    val SUMMONING_PENDULUM: DeferredItem<SummoningPendulumItem> =
        register("summoning_pendulum") { SummoningPendulumItem() }
    val LOOT_GENERATOR = basic("loot_generator")
    val BOTTLE_OF_AIR: DeferredItem<AirBottleItem> =
        register("bottle_of_air") { AirBottleItem() }
    val ENDER_LETTER = basic("ender_letter")
    val LUMINOUS_POWDER = basic("luminous_powder")  //TODO: Craft with item to make enchant glint emissive
    val GOLDEN_EGG = basic("golden_egg")
    val BLACKOUT_POWDER: DeferredItem<BlackoutPowderItem> =
        register("blackout_powder") { BlackoutPowderItem() }
    val EMERALD_COMPASS: DeferredItem<EmeraldCompassItem> =
        register("emerald_compass") { EmeraldCompassItem() }
    val BLAZE_AND_STEEL: DeferredItem<BlazeAndSteelItem> =
        register("blaze_and_steel") { BlazeAndSteelItem() }
    val ESCAPE_ROPE = basic("escape_rope")
    val ENDER_BUCKET = basic("ender_bucket")
    val REINFORCED_ENDER_BUCKET = basic("reinforced_ender_bucket")
    val CHUNK_ANALYZER: DeferredItem<ChunkAnalyzerItem> =
        register("chunk_analyzer") { ChunkAnalyzerItem() }
    val LAVA_CHARM: DeferredItem<LavaCharmItem> =
        register("lava_charm") { LavaCharmItem() } //TODO: Advancement joking about the ui bar
    val OBSIDIAN_SKULL: DeferredItem<Item> =
        basic("obsidian_skull", Item.Properties().stacksTo(1).fireResistant())
    val OBSIDIAN_SKULL_RING: DeferredItem<Item> =
        basic("obsidian_skull_ring", Item.Properties().stacksTo(1).fireResistant())
    val DIVINING_ROD: DeferredItem<DiviningRodItem> =
        register("divining_rod") { DiviningRodItem() }

    // Ingredients
    val TRANSFORMATION_CORE = basic("transformation_core")
    val OBSIDIAN_ROD = basic("obsidian_rod")
    val BIOME_SENSOR = basic("biome_sensor")
    val PLATE_BASE = basic("plate_base")
    val ECTOPLASM = basic("ectoplasm")  // use on sapling to make spectre sapling
    val SUPER_LUBRICANT_TINCTURE = basic("super_lubricant_tincture")
    val SPECTRE_INGOT = basic("spectre_ingot")
    val SPECTRE_STRING = basic("spectre_string")

    // Plants
    val SAKANADE_SPORES = basic("sakanade_spores")
    val LOTUS_BLOSSOM: DeferredItem<LotusBlossomItem> =
        register("lotus_blossom") { LotusBlossomItem() }
    val LOTUS_SEEDS: DeferredItem<ItemNameBlockItem> =
        register("lotus_seeds") { ItemNameBlockItem(ModBlocks.LOTUS.get(), Item.Properties()) }
    val BEAN: DeferredItem<ItemNameBlockItem> =
        register("bean") { ItemNameBlockItem(ModBlocks.BEAN_SPROUT.get(), Item.Properties()) }
    val BEAN_STEW: DeferredItem<Item> =
        register("bean_stew") { Item(Item.Properties().stacksTo(1).food(Foods.stew(8).build())) }
    val LESSER_MAGIC_BEAN = basic("lesser_magic_bean")
    val MAGIC_BEAN = basic("magic_bean")    //rarity blue

    // Armors
    val MAGIC_HOOD: DeferredItem<ArmorItem> =
        register("magic_hood") { ModArmorItems.MAGIC_HOOD }
    val WATER_WALKING_BOOTS: DeferredItem<ArmorItem> =
        register("water_walking_boots") { ModArmorItems.WATER_WALKING_BOOTS }
    val OBSIDIAN_WATER_WALKING_BOOTS: DeferredItem<ArmorItem> =
        register("obsidian_water_walking_boots") { ModArmorItems.OBSIDIAN_WATER_WALKING_BOOTS }
    val LAVA_WADERS: DeferredItem<ArmorItem> =
        register("lava_waders") { ModArmorItems.LAVA_WADERS }
    val SPECTRE_HELMET: DeferredItem<ArmorItem> =
        register("spectre_helmet") { ModArmorItems.SPECTRE_HELMET }
    val SPECTRE_CHESTPLATE: DeferredItem<ArmorItem> =
        register("spectre_chestplate") { ModArmorItems.SPECTRE_CHESTPLATE }
    val SPECTRE_LEGGINGS: DeferredItem<ArmorItem> =
        register("spectre_leggings") { ModArmorItems.SPECTRE_LEGGINGS }
    val SPECTRE_BOOTS: DeferredItem<ArmorItem> =
        register("spectre_boots") { ModArmorItems.SPECTRE_BOOTS }

    // Weather Eggs
    val WEATHER_EGG_SUNNY: DeferredItem<WeatherEggItem> =
        register("weather_egg_sunny") { WeatherEggItem.SUNNY }
    val WEATHER_EGG_RAINY: DeferredItem<WeatherEggItem> =
        register("weather_egg_rainy") { WeatherEggItem.RAINY }
    val WEATHER_EGG_STORMY: DeferredItem<WeatherEggItem> =
        register("weather_egg_stormy") { WeatherEggItem.STORMY }

    // Filters
    val LOCATION_FILTER: DeferredItem<FilterLocationItem> =
        register("location_filter") { FilterLocationItem() }
    val ITEM_FILTER = basic("item_filter")
    val ENTITY_FILTER: DeferredItem<FilterEntityItem> =
        register("entity_filter") { FilterEntityItem() }
    val ID_CARD: DeferredItem<FilterPlayerItem> =
        register("id_card") { FilterPlayerItem() }

    // Imbues
    val FIRE_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_fire") { ImbueItem(ModEffects.FIRE_IMBUE) }
    val POISON_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_poison") { ImbueItem(ModEffects.POISON_IMBUE) }
    val EXPERIENCE_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_experience") { ImbueItem(ModEffects.EXPERIENCE_IMBUE) }
    val WITHER_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_wither") { ImbueItem(ModEffects.WITHER_IMBUE) }
    val COLLAPSE_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_collapse") { ImbueItem(ModEffects.COLLAPSE_IMBUE) }
    val SPECTRE_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_spectre") { ImbueItem(ModEffects.SPECTRE_IMBUE) }

    // Spectre
    val SPECTRE_ILLUMINATOR = register("spectre_illuminator") { SpectreIlluminatorItem() }
    val SPECTRE_KEY = basic("spectre_key")
    val SPECTRE_ANCHOR: DeferredItem<SpectreAnchorItem> =
        register("spectre_anchor") { SpectreAnchorItem() }
    val SPECTRE_CHARGER = basic("spectre_charger") //Same item for all tiers
    val SPECTRE_SWORD: DeferredItem<SwordItem> =
        register("spectre_sword") { ModToolItems.SPECTRE_SWORD }
    val SPECTRE_PICKAXE: DeferredItem<PickaxeItem> =
        register("spectre_pickaxe") { ModToolItems.SPECTRE_PICKAXE }
    val SPECTRE_AXE: DeferredItem<AxeItem> =
        register("spectre_axe") { ModToolItems.SPECTRE_AXE }
    val SPECTRE_SHOVEL: DeferredItem<ShovelItem> =
        register("spectre_shovel") { ModToolItems.SPECTRE_SHOVEL }

    // Redstone
    val ADVANCED_REDSTONE_REPEATER = basic("advanced_redstone_repeater")
    val ADVANCED_REDSTONE_TORCH = basic("advanced_redstone_torch")
    val REDSTONE_TOOL: DeferredItem<RedstoneToolItem> =
        register("redstone_tool") { RedstoneToolItem() }
    val REDSTONE_ACTIVATOR: DeferredItem<RedstoneActivatorItem> =
        register("redstone_activator") { RedstoneActivatorItem() }
    val REDSTONE_REMOTE = basic("redstone_remote")

    // Floo
    val FLOO_POWDER = basic("floo_powder")
    val FLOO_SIGN = basic("floo_sign")
    val FLOO_TOKEN = basic("floo_token")
    val FLOO_POUCH = basic("floo_pouch")

    // Sound
    val SOUND_PATTERN = basic("sound_pattern")
    val SOUND_RECORDER = basic("sound_recorder")
    val PORTABLE_SOUND_DAMPENER = basic("portable_sound_dampener")

    // Not above 1.7
    val BIOME_CAPSULE: DeferredItem<BiomeCapsuleItem> =
        register("biome_capsule") { BiomeCapsuleItem() }
    val BIOME_PAINTER: DeferredItem<BiomePainterItem> =
        register("biome_painter") { BiomePainterItem() }
    val DROP_FILTER = basic("drop_filter")
    val VOIDING_DROP_FILTER = basic("voiding_drop_filter")
    val VOID_STONE: DeferredItem<VoidStoneItem> =
        register("void_stone") { VoidStoneItem() }
    val WHITE_STONE: DeferredItem<WhiteStoneItem> =
        register("white_stone") { WhiteStoneItem() }
    val MAGNETIC_FORCE = basic("magnetic_force")

    // Not above 1.6.4
    val PORTABLE_ENDER_BRIDGE: DeferredItem<PortableEnderBridgeItem> =
        register("portable_ender_bridge") { PortableEnderBridgeItem() }
    val BLOCK_MOVER: DeferredItem<BlockMoverItem> =
        register("block_mover") { BlockMoverItem() }
    val DIAMOND_BREAKER = basic("diamond_breaker")
    val BLOCK_REPLACER: DeferredItem<BlockReplacerItem> =
        register("block_replacer") { BlockReplacerItem() }

    // Colors
    val GRASS_SEEDS: DeferredItem<GrassSeedItem> =
        register("grass_seeds") { GrassSeedItem(dyeColor = null) }
    val GRASS_SEEDS_WHITE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_white") { GrassSeedItem(dyeColor = DyeColor.WHITE) }
    val GRASS_SEEDS_ORANGE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_orange") { GrassSeedItem(dyeColor = DyeColor.ORANGE) }
    val GRASS_SEEDS_MAGENTA: DeferredItem<GrassSeedItem> =
        register("grass_seeds_magenta") { GrassSeedItem(dyeColor = DyeColor.MAGENTA) }
    val GRASS_SEEDS_LIGHT_BLUE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_light_blue") { GrassSeedItem(dyeColor = DyeColor.LIGHT_BLUE) }
    val GRASS_SEEDS_YELLOW: DeferredItem<GrassSeedItem> =
        register("grass_seeds_yellow") { GrassSeedItem(dyeColor = DyeColor.YELLOW) }
    val GRASS_SEEDS_LIME: DeferredItem<GrassSeedItem> =
        register("grass_seeds_lime") { GrassSeedItem(dyeColor = DyeColor.LIME) }
    val GRASS_SEEDS_PINK: DeferredItem<GrassSeedItem> =
        register("grass_seeds_pink") { GrassSeedItem(dyeColor = DyeColor.PINK) }
    val GRASS_SEEDS_GRAY: DeferredItem<GrassSeedItem> =
        register("grass_seeds_gray") { GrassSeedItem(dyeColor = DyeColor.GRAY) }
    val GRASS_SEEDS_LIGHT_GRAY: DeferredItem<GrassSeedItem> =
        register("grass_seeds_light_gray") { GrassSeedItem(dyeColor = DyeColor.LIGHT_GRAY) }
    val GRASS_SEEDS_CYAN: DeferredItem<GrassSeedItem> =
        register("grass_seeds_cyan") { GrassSeedItem(dyeColor = DyeColor.CYAN) }
    val GRASS_SEEDS_PURPLE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_purple") { GrassSeedItem(dyeColor = DyeColor.PURPLE) }
    val GRASS_SEEDS_BLUE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_blue") { GrassSeedItem(dyeColor = DyeColor.BLUE) }
    val GRASS_SEEDS_BROWN: DeferredItem<GrassSeedItem> =
        register("grass_seeds_brown") { GrassSeedItem(dyeColor = DyeColor.BROWN) }
    val GRASS_SEEDS_GREEN: DeferredItem<GrassSeedItem> =
        register("grass_seeds_green") { GrassSeedItem(dyeColor = DyeColor.GREEN) }
    val GRASS_SEEDS_RED: DeferredItem<GrassSeedItem> =
        register("grass_seeds_red") { GrassSeedItem(dyeColor = DyeColor.RED) }
    val GRASS_SEEDS_BLACK: DeferredItem<GrassSeedItem> =
        register("grass_seeds_black") { GrassSeedItem(dyeColor = DyeColor.BLACK) }

    // Removed items:
    // - Time in a Bottle (Use the standalone one!)
    // - Eclipsed Clock (requires TIAB)
    // - Golden Compass (redundant, use Lodestones)
    // - Runic Dust (obscure and difficult)
    // - Blood Stone (requires Blood Moon)


    private fun basic(id: String): DeferredItem<Item> {
        return ITEM_REGISTRY.registerSimpleItem(id)
    }

    private fun basic(id: String, properties: Item.Properties): DeferredItem<Item> {
        return ITEM_REGISTRY.registerSimpleItem(id, properties)
    }

    private fun basic(id: String, maxStackSize: Int): DeferredItem<Item> {
        return ITEM_REGISTRY.registerSimpleItem(id, Item.Properties().stacksTo(maxStackSize))
    }

    private fun <T : Item> register(id: String, itemBuilder: (Item.Properties) -> T): DeferredItem<T> {
        return ITEM_REGISTRY.registerItem(id, itemBuilder)
    }

}