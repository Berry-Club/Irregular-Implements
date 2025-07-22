package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.*
import net.minecraft.core.Holder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.food.Foods
import net.minecraft.world.item.*
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModItems {

	//TODO: Check rarity for all items

	val ITEM_REGISTRY: DeferredRegister.Items =
		DeferredRegister.createItems(IrregularImplements.ID)

	val STABLE_ENDER_PEARL: DeferredItem<StableEnderPearlItem> =
		register("stable_ender_pearl", ::StableEnderPearlItem, StableEnderPearlItem.DEFAULT_PROPERTIES)
	val EVIL_TEAR: DeferredItem<EvilTearItem> =
		register("evil_tear", ::EvilTearItem)
	val PORTKEY: DeferredItem<PortkeyItem> =
		register("portkey", ::PortkeyItem, PortkeyItem.DEFAULT_PROPERTIES)
	val BIOME_CRYSTAL: DeferredItem<BiomeCrystalItem> =
		register("biome_crystal", ::BiomeCrystalItem, BiomeCrystalItem.DEFAULT_PROPERTIES)
	val SUMMONING_PENDULUM: DeferredItem<SummoningPendulumItem> =
		register("summoning_pendulum", ::SummoningPendulumItem, SummoningPendulumItem.DEFAULT_PROPERTIES)
	val LOOT_GENERATOR = basic("loot_generator")
	val BOTTLE_OF_AIR: DeferredItem<AirBottleItem> =
		register("bottle_of_air", ::AirBottleItem, AirBottleItem.DEFAULT_PROPERTIES)
	val ENDER_LETTER: DeferredItem<EnderLetterItem> =
		register("ender_letter", ::EnderLetterItem, EnderLetterItem.DEFAULT_PROPERTIES)
	val GOLDEN_EGG: DeferredItem<GoldenEggItem> =
		register("golden_egg", ::GoldenEggItem)
	val EMERALD_COMPASS: DeferredItem<EmeraldCompassItem> =
		register("emerald_compass", ::EmeraldCompassItem, EmeraldCompassItem.DEFAULT_PROPERTIES)
	val BLAZE_AND_STEEL: DeferredItem<BlazeAndSteelItem> =
		register("blaze_and_steel", ::BlazeAndSteelItem, BlazeAndSteelItem.DEFAULT_PROPERTIES)
	val ESCAPE_ROPE: DeferredItem<EscapeRopeItem> =
		register("escape_rope", ::EscapeRopeItem, EscapeRopeItem.DEFAULT_PROPERTIES)
	val CHUNK_ANALYZER: DeferredItem<ChunkAnalyzerItem> =
		register("chunk_analyzer", ::ChunkAnalyzerItem, ChunkAnalyzerItem.DEFAULT_PROPERTIES)
	val LAVA_CHARM: DeferredItem<LavaCharmItem> =
		register("lava_charm", ::LavaCharmItem, LavaCharmItem.DEFAULT_PROPERTIES) //TODO: Advancement joking about the ui bar
	val OBSIDIAN_SKULL: DeferredItem<Item> =
		basic("obsidian_skull", Item.Properties().stacksTo(1).fireResistant())
	val OBSIDIAN_SKULL_RING: DeferredItem<Item> =
		basic("obsidian_skull_ring", Item.Properties().stacksTo(1).fireResistant())
	val DIVINING_ROD: DeferredItem<DiviningRodItem> =
		register("divining_rod", ::DiviningRodItem, DiviningRodItem.DEFAULT_PROPERTIES)

	// Block items
	val DIAPHANOUS_BLOCK: DeferredItem<DiaphanousBlockItem> =
		register("diaphanous_block", ::DiaphanousBlockItem)
	val CUSTOM_CRAFTING_TABLE: DeferredItem<CustomCraftingTableBlockItem> =
		register("custom_crafting_table", ::CustomCraftingTableBlockItem)

	// Ingredients
	val TRANSFORMATION_CORE: DeferredItem<Item> = basic("transformation_core")
	val OBSIDIAN_ROD: DeferredItem<Item> = basic("obsidian_rod")
	val BIOME_SENSOR: DeferredItem<Item> = basic("biome_sensor")
	val PLATE_BASE: DeferredItem<Item> = basic("plate_base")
	val ECTOPLASM: DeferredItem<Item> = basic("ectoplasm")
	val SUPER_LUBRICANT_TINCTURE: DeferredItem<Item> = basic("super_lubricant_tincture")
	val SPECTRE_INGOT: DeferredItem<Item> = basic("spectre_ingot")
	val SPECTRE_STRING: DeferredItem<Item> = basic("spectre_string")
	val LUMINOUS_POWDER: DeferredItem<Item> = basic("luminous_powder")  //TODO: Craft with item to make enchant glint emissive

	// Bucket
	val ENDER_BUCKET: DeferredItem<EnderBucketItem> =
		register("ender_bucket", ::EnderBucketItem, EnderBucketItem.DEFAULT_PROPERTIES)
	val REINFORCED_ENDER_BUCKET = basic("reinforced_ender_bucket")

	// Plants
	val LOTUS_BLOSSOM: DeferredItem<LotusBlossomItem> =
		register("lotus_blossom", ::LotusBlossomItem, LotusBlossomItem.DEFAULT_PROPERTIES)
	val LOTUS_SEEDS: DeferredItem<ItemNameBlockItem> =
		registerItemNameBlockItem("lotus_seeds", ModBlocks.LOTUS)
	val BEAN: DeferredItem<ItemNameBlockItem> =
		registerItemNameBlockItem("bean", ModBlocks.BEAN_SPROUT)
	val BEAN_STEW: DeferredItem<Item> =
		basic("bean_stew", Item.Properties().stacksTo(1).food(Foods.stew(8).build()))
	val LESSER_MAGIC_BEAN: DeferredItem<ItemNameBlockItem> =
		registerItemNameBlockItem("lesser_magic_bean", ModBlocks.LESSER_BEAN_STALK)
	val MAGIC_BEAN: DeferredItem<ItemNameBlockItem> =
		registerItemNameBlockItem("magic_bean", ModBlocks.BEAN_STALK, Item.Properties().rarity(Rarity.RARE))

	// Armors
	val MAGIC_HOOD: DeferredItem<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"magic_hood",
			ModArmorMaterials.MAGIC, ArmorItem.Type.HELMET,
			ModArmorItems.MAGIC_HOOD_PROPERTIES
		)
	val WATER_WALKING_BOOTS: DeferredItem<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"water_walking_boots",
			ModArmorMaterials.WATER_WALKING, ArmorItem.Type.BOOTS,
			ModArmorItems.WATER_WALKING_BOOTS_PROPERTIES
		)
	val OBSIDIAN_WATER_WALKING_BOOTS: DeferredItem<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"obsidian_water_walking_boots",
			ModArmorMaterials.OBSIDIAN_WATER_WALKING, ArmorItem.Type.BOOTS,
			ModArmorItems.OBSIDIAN_WATER_WALKING_BOOTS_PROPERTIES
		)
	val LAVA_WADERS: DeferredItem<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"lava_waders",
			ModArmorMaterials.LAVA_WADERS, ArmorItem.Type.BOOTS,
			ModArmorItems.LAVA_WADERS_PROPERTIES
		)
	val SPECTRE_HELMET: DeferredItem<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"spectre_helmet",
			ModArmorMaterials.SPECTRE, ArmorItem.Type.HELMET,
			ModArmorItems.SPECTRE_HELMET_PROPERTIES
		)
	val SPECTRE_CHESTPLATE: DeferredItem<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"spectre_chestplate",
			ModArmorMaterials.SPECTRE, ArmorItem.Type.CHESTPLATE,
			ModArmorItems.SPECTRE_CHESTPLATE_PROPERTIES
		)
	val SPECTRE_LEGGINGS: DeferredItem<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"spectre_leggings",
			ModArmorMaterials.SPECTRE, ArmorItem.Type.LEGGINGS,
			ModArmorItems.SPECTRE_LEGGINGS_PROPERTIES
		)
	val SPECTRE_BOOTS: DeferredItem<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"spectre_boots",
			ModArmorMaterials.SPECTRE, ArmorItem.Type.BOOTS,
			ModArmorItems.SPECTRE_BOOTS_PROPERTIES
		)

	// Weather Eggs
	val WEATHER_EGG: DeferredItem<WeatherEggItem> =
		register("weather_egg", ::WeatherEggItem, WeatherEggItem.DEFAULT_PROPERTIES)

	// Filters
	val LOCATION_FILTER: DeferredItem<LocationFilterItem> =
		register("location_filter", ::LocationFilterItem, LocationFilterItem.DEFAULT_PROPERTIES)
	val ITEM_FILTER: DeferredItem<ItemFilterItem> =
		register("item_filter", ::ItemFilterItem, ItemFilterItem.DEFAULT_PROPERTIES)
	val ENTITY_FILTER: DeferredItem<EntityFilterItem> =
		register("entity_filter", ::EntityFilterItem, EntityFilterItem.DEFAULT_PROPERTIES)
	val ID_CARD: DeferredItem<PlayerFilterItem> =   //TODO: Rename to Player Filter?
		register("id_card", ::PlayerFilterItem, PlayerFilterItem.DEFAULT_PROPERTIES)

	// Imbues
	val FIRE_IMBUE: DeferredItem<ImbueItem> =
		registerImbue("imbue_fire", ModEffects.FIRE_IMBUE)
	val POISON_IMBUE: DeferredItem<ImbueItem> =
		registerImbue("imbue_poison", ModEffects.POISON_IMBUE)
	val EXPERIENCE_IMBUE: DeferredItem<ImbueItem> =
		registerImbue("imbue_experience", ModEffects.EXPERIENCE_IMBUE)
	val WITHER_IMBUE: DeferredItem<ImbueItem> =
		registerImbue("imbue_wither", ModEffects.WITHER_IMBUE)
	val COLLAPSE_IMBUE: DeferredItem<ImbueItem> =
		registerImbue("imbue_collapse", ModEffects.COLLAPSE_IMBUE)
	val SPECTRE_IMBUE: DeferredItem<ImbueItem> =
		registerImbue("imbue_spectre", ModEffects.SPECTRE_IMBUE)

	// Spectre
	val SPECTRE_ILLUMINATOR: DeferredItem<SpectreIlluminatorItem> =
		register("spectre_illuminator", ::SpectreIlluminatorItem)
	val BLACKOUT_POWDER: DeferredItem<BlackoutPowderItem> =
		register("blackout_powder", ::BlackoutPowderItem)
	val SPECTRE_KEY: DeferredItem<SpectreKeyItem> =
		register("spectre_key", ::SpectreKeyItem)
	val SPECTRE_ANCHOR: DeferredItem<SpectreAnchorItem> =
		register("spectre_anchor", ::SpectreAnchorItem, SpectreAnchorItem.DEFAULT_PROPERTIES)
	val SPECTRE_CHARGER_BASIC: DeferredItem<SpectreChargerItem> =
		registerSpectreCharger("spectre_charger_basic", SpectreChargerItem.Type.BASIC)
	val SPECTRE_CHARGER_REDSTONE: DeferredItem<SpectreChargerItem> =
		registerSpectreCharger("spectre_charger_redstone", SpectreChargerItem.Type.REDSTONE)
	val SPECTRE_CHARGER_ENDER: DeferredItem<SpectreChargerItem> =
		registerSpectreCharger("spectre_charger_ender", SpectreChargerItem.Type.ENDER)
	val SPECTRE_CHARGER_GENESIS: DeferredItem<SpectreChargerItem> =
		registerSpectreCharger("spectre_charger_genesis", SpectreChargerItem.Type.GENESIS)
	val SPECTRE_SWORD: DeferredItem<SwordItem> =
		ModToolItems.registerSword("spectre_sword", ModToolItems.SPECTRE_TIER, ModToolItems.SPECTRE_SWORD_DEFAULT_PROPERTIES)
	val SPECTRE_PICKAXE: DeferredItem<PickaxeItem> =
		ModToolItems.registerPickaxe("spectre_pickaxe", ModToolItems.SPECTRE_TIER, ModToolItems.SPECTRE_PICKAXE_DEFAULT_PROPERTIES)
	val SPECTRE_AXE: DeferredItem<AxeItem> =
		ModToolItems.registerAxe("spectre_axe", ModToolItems.SPECTRE_TIER, ModToolItems.SPECTRE_AXE_DEFAULT_PROPERTIES)
	val SPECTRE_SHOVEL: DeferredItem<ShovelItem> =
		ModToolItems.registerShovel("spectre_shovel", ModToolItems.SPECTRE_TIER, ModToolItems.SPECTRE_SHOVEL_DEFAULT_PROPERTIES)

	// Redstone
	val REDSTONE_TOOL: DeferredItem<RedstoneToolItem> =
		register("redstone_tool", ::RedstoneToolItem, RedstoneToolItem.DEFAULT_PROPERTIES)
	val REDSTONE_ACTIVATOR: DeferredItem<RedstoneActivatorItem> =
		register("redstone_activator", ::RedstoneActivatorItem, RedstoneActivatorItem.DEFAULT_PROPERTIES)
	val REDSTONE_REMOTE: DeferredItem<RedstoneRemoteItem> =
		register("redstone_remote", ::RedstoneRemoteItem, RedstoneRemoteItem.DEFAULT_PROPERTIES)

	// Floo
	val FLOO_POWDER: DeferredItem<Item> =
		basic("floo_powder")
	val FLOO_SIGN: DeferredItem<FlooSignItem> =
		register("floo_sign", ::FlooSignItem)
	val FLOO_TOKEN: DeferredItem<FlooTokenItem> =
		register("floo_token", ::FlooTokenItem)
	val FLOO_POUCH: DeferredItem<FlooPouchItem> =
		register("floo_pouch", ::FlooPouchItem, FlooPouchItem.DEFAULT_PROPERTIES)

	// Not above 1.7
	val BIOME_CAPSULE: DeferredItem<BiomeCapsuleItem> =
		register("biome_capsule", ::BiomeCapsuleItem, BiomeCapsuleItem.DEFAULT_PROPERTIES)
	val BIOME_PAINTER: DeferredItem<BiomePainterItem> =
		register("biome_painter", ::BiomePainterItem, BiomePainterItem.DEFAULT_PROPERTIES)
	val DROP_FILTER: DeferredItem<DropFilterItem> =
		register("drop_filter", ::DropFilterItem, DropFilterItem.DEFAULT_PROPERTIES)
	val VOIDING_DROP_FILTER: DeferredItem<DropFilterItem> =
		register("voiding_drop_filter", ::DropFilterItem, DropFilterItem.DEFAULT_PROPERTIES)
	val VOID_STONE: DeferredItem<VoidStoneItem> =
		register("void_stone", ::VoidStoneItem, VoidStoneItem.DEFAULT_PROPERTIES)
	val WHITE_STONE: DeferredItem<WhiteStoneItem> =
		register("white_stone", ::WhiteStoneItem, WhiteStoneItem.DEFAULT_PROPERTIES)
	val MAGNETIC_FORCE = basic("magnetic_force")

	// Not above 1.6.4
	val PORTABLE_ENDER_BRIDGE: DeferredItem<PortableEnderBridgeItem> =
		register("portable_ender_bridge", ::PortableEnderBridgeItem, PortableEnderBridgeItem.DEFAULT_PROPERTIES)
	val BLOCK_MOVER: DeferredItem<BlockMoverItem> =
		register("block_mover", ::BlockMoverItem, BlockMoverItem.DEFAULT_PROPERTIES)
	val DIAMOND_BREAKER: DeferredItem<Item> =
		basic("diamond_breaker")
	val BLOCK_REPLACER: DeferredItem<BlockReplacerItem> =
		register("block_replacer", ::BlockReplacerItem, BlockReplacerItem.DEFAULT_PROPERTIES)

	// Colors
	val GRASS_SEEDS: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds", color = null)
	val GRASS_SEEDS_WHITE: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_white", color = DyeColor.WHITE)
	val GRASS_SEEDS_ORANGE: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_orange", color = DyeColor.ORANGE)
	val GRASS_SEEDS_MAGENTA: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_magenta", color = DyeColor.MAGENTA)
	val GRASS_SEEDS_LIGHT_BLUE: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_light_blue", color = DyeColor.LIGHT_BLUE)
	val GRASS_SEEDS_YELLOW: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_yellow", color = DyeColor.YELLOW)
	val GRASS_SEEDS_LIME: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_lime", color = DyeColor.LIME)
	val GRASS_SEEDS_PINK: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_pink", color = DyeColor.PINK)
	val GRASS_SEEDS_GRAY: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_gray", color = DyeColor.GRAY)
	val GRASS_SEEDS_LIGHT_GRAY: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_light_gray", color = DyeColor.LIGHT_GRAY)
	val GRASS_SEEDS_CYAN: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_cyan", color = DyeColor.CYAN)
	val GRASS_SEEDS_PURPLE: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_purple", color = DyeColor.PURPLE)
	val GRASS_SEEDS_BLUE: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_blue", color = DyeColor.BLUE)
	val GRASS_SEEDS_BROWN: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_brown", color = DyeColor.BROWN)
	val GRASS_SEEDS_GREEN: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_green", color = DyeColor.GREEN)
	val GRASS_SEEDS_RED: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_red", color = DyeColor.RED)
	val GRASS_SEEDS_BLACK: DeferredItem<GrassSeedItem> =
		grassSeed("grass_seeds_black", color = DyeColor.BLACK)

	// Removed items:
	// - Time in a Bottle (Use the standalone one!)
	// - Eclipsed Clock (requires TIAB)
	// - Golden Compass (redundant, use Lodestones)
	// - Runic Dust (obscure and difficult)
	// - Blood Stone (requires Blood Moon)
	// - Precious Emerald (undocumented and weird)
	// - Sound Pattern, Recorder, Dampener (just use Super Sound Muffler)

	private fun basic(id: String): DeferredItem<Item> {
		return ITEM_REGISTRY.registerSimpleItem(id)
	}

	private fun basic(id: String, properties: Item.Properties): DeferredItem<Item> {
		return ITEM_REGISTRY.registerSimpleItem(id, properties)
	}

	private fun basic(id: String, maxStackSize: Int): DeferredItem<Item> {
		return ITEM_REGISTRY.registerSimpleItem(id, Item.Properties().stacksTo(maxStackSize))
	}

	private fun <I : Item> register(
		id: String,
		builder: (Item.Properties) -> I,
		properties: Item.Properties = Item.Properties()
	): DeferredItem<I> {
		return ITEM_REGISTRY.registerItem(id) { builder(properties) }
	}

	private fun <I : Item> register(
		id: String,
		builder: (Item.Properties) -> I,
		properties: Supplier<Item.Properties>
	): DeferredItem<I> {
		return ITEM_REGISTRY.registerItem(id) { builder(properties.get()) }
	}

	private fun registerItemNameBlockItem(
		id: String,
		block: DeferredBlock<out Block>,
		properties: Item.Properties = Item.Properties()
	): DeferredItem<ItemNameBlockItem> {
		return ITEM_REGISTRY.registerItem(id) { ItemNameBlockItem(block.get(), properties) }
	}

	private fun registerImbue(
		id: String,
		mobEffect: Holder<MobEffect>,
		properties: Item.Properties = ImbueItem.DEFAULT_PROPERTIES
	): DeferredItem<ImbueItem> {
		return ITEM_REGISTRY.registerItem(id) { ImbueItem(mobEffect, properties) }
	}

	private fun registerSpectreCharger(
		id: String,
		type: SpectreChargerItem.Type,
		properties: Item.Properties = SpectreChargerItem.DEFAULT_PROPERTIES
	): DeferredItem<SpectreChargerItem> {
		return ITEM_REGISTRY.registerItem(id) { SpectreChargerItem(type, properties) }
	}

	private fun grassSeed(
		id: String,
		color: DyeColor?,
		properties: Item.Properties = Item.Properties()
	): DeferredItem<GrassSeedItem> {
		return ITEM_REGISTRY.registerItem(id) { GrassSeedItem(color, properties) }
	}

}