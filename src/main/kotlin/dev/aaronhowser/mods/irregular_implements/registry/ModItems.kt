package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.*
import net.minecraft.core.Direction
import net.minecraft.core.Holder
import net.minecraft.util.Unit
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.Entity
import net.minecraft.world.food.Foods
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object ModItems {

	//TODO: Check rarity for all items

	val ITEM_REGISTRY: DeferredRegister<Item> =
		DeferredRegister.create(ForgeRegistries.ITEMS, IrregularImplements.ID)

	val STABLE_ENDER_PEARL: RegistryObject<StableEnderPearlItem> =
		register("stable_ender_pearl", ::StableEnderPearlItem, StableEnderPearlItem.DEFAULT_PROPERTIES)
	val EVIL_TEAR: RegistryObject<EvilTearItem> =
		register("evil_tear", ::EvilTearItem)
	val PORTKEY: RegistryObject<PortkeyItem> =
		register("portkey", ::PortkeyItem, PortkeyItem.DEFAULT_PROPERTIES)
	val BIOME_CRYSTAL: RegistryObject<BiomeCrystalItem> =
		register("biome_crystal", ::BiomeCrystalItem, BiomeCrystalItem.DEFAULT_PROPERTIES)
	val SUMMONING_PENDULUM: RegistryObject<SummoningPendulumItem> =
		register("summoning_pendulum", ::SummoningPendulumItem, SummoningPendulumItem.DEFAULT_PROPERTIES)
	val BOTTLE_OF_AIR: RegistryObject<AirBottleItem> =
		register("bottle_of_air", ::AirBottleItem, AirBottleItem.DEFAULT_PROPERTIES)
	val ENDER_LETTER: RegistryObject<EnderLetterItem> =
		register("ender_letter", ::EnderLetterItem, EnderLetterItem.DEFAULT_PROPERTIES)
	val GOLDEN_EGG: RegistryObject<GoldenEggItem> =
		register("golden_egg", ::GoldenEggItem)
	val EMERALD_COMPASS: RegistryObject<EmeraldCompassItem> =
		register("emerald_compass", ::EmeraldCompassItem, EmeraldCompassItem.DEFAULT_PROPERTIES)
	val GOLDEN_COMPASS: RegistryObject<GoldenCompassItem> =
		register("golden_compass", ::GoldenCompassItem, GoldenCompassItem.DEFAULT_PROPERTIES)
	val BLAZE_AND_STEEL: RegistryObject<BlazeAndSteelItem> =
		register("blaze_and_steel", ::BlazeAndSteelItem, BlazeAndSteelItem.DEFAULT_PROPERTIES)
	val ESCAPE_ROPE: RegistryObject<EscapeRopeItem> =
		register("escape_rope", ::EscapeRopeItem, EscapeRopeItem.DEFAULT_PROPERTIES)
	val CHUNK_ANALYZER: RegistryObject<ChunkAnalyzerItem> =
		register("chunk_analyzer", ::ChunkAnalyzerItem, ChunkAnalyzerItem.DEFAULT_PROPERTIES)
	val LAVA_CHARM: RegistryObject<LavaCharmItem> =
		register("lava_charm", ::LavaCharmItem, LavaCharmItem.DEFAULT_PROPERTIES) //TODO: Advancement joking about the ui bar
	val OBSIDIAN_SKULL: RegistryObject<Item> =
		basicWithProperties("obsidian_skull", Item.Properties().stacksTo(1).fireResistant())
	val OBSIDIAN_SKULL_RING: RegistryObject<Item> =
		basicWithProperties("obsidian_skull_ring", Item.Properties().stacksTo(1).fireResistant())
	val DIVINING_ROD: RegistryObject<DiviningRodItem> =
		register("divining_rod", ::DiviningRodItem, DiviningRodItem.DEFAULT_PROPERTIES)

	// Block items
	val DIAPHANOUS_BLOCK: RegistryObject<DiaphanousBlockItem> =
		register("diaphanous_block", ::DiaphanousBlockItem)
	val CUSTOM_CRAFTING_TABLE: RegistryObject<CustomCraftingTableBlockItem> =
		register("custom_crafting_table", ::CustomCraftingTableBlockItem)

	// Ingredients
	val TRANSFORMATION_CORE: RegistryObject<Item> = basic("transformation_core")
	val OBSIDIAN_ROD: RegistryObject<Item> = basic("obsidian_rod")
	val BIOME_SENSOR: RegistryObject<Item> = basic("biome_sensor")
	val PLATE_BASE: RegistryObject<Item> = basic("plate_base")
	val ECTOPLASM: RegistryObject<Item> = basic("ectoplasm")
	val SUPER_LUBRICANT_TINCTURE: RegistryObject<Item> = basic("super_lubricant_tincture")
	val SPECTRE_INGOT: RegistryObject<Item> = basic("spectre_ingot")
	val SPECTRE_STRING: RegistryObject<Item> = basic("spectre_string")
	val LUMINOUS_POWDER: RegistryObject<Item> = basicWithProperties("luminous_powder") { Item.Properties().component(ModDataComponents.HAS_LUMINOUS_POWDER, Unit.INSTANCE) }

	// Bucket
	val ENDER_BUCKET: RegistryObject<EnderBucketItem> =
		register("ender_bucket", ::EnderBucketItem, EnderBucketItem.DEFAULT_PROPERTIES)
	val REINFORCED_ENDER_BUCKET: RegistryObject<ReinforcedEnderBucketItem> =
		register("reinforced_ender_bucket", ::ReinforcedEnderBucketItem, ReinforcedEnderBucketItem.DEFAULT_PROPERTIES)

	// Plants
	val LOTUS_BLOSSOM: RegistryObject<LotusBlossomItem> =
		register("lotus_blossom", ::LotusBlossomItem, LotusBlossomItem.DEFAULT_PROPERTIES)
	val LOTUS_SEEDS: RegistryObject<ItemNameBlockItem> =
		registerItemNameBlockItem("lotus_seeds", ModBlocks.LOTUS)
	val BEAN: RegistryObject<ItemNameBlockItem> =
		registerItemNameBlockItem("bean", ModBlocks.BEAN_SPROUT)
	val BEAN_STEW: RegistryObject<Item> =
		basicWithProperties("bean_stew", Item.Properties().stacksTo(1).food(Foods.stew(8).build()))
	val LESSER_MAGIC_BEAN: RegistryObject<ItemNameBlockItem> =
		registerItemNameBlockItem("lesser_magic_bean", ModBlocks.LESSER_BEAN_STALK)
	val MAGIC_BEAN: RegistryObject<ItemNameBlockItem> =
		registerItemNameBlockItem("magic_bean", ModBlocks.BEAN_STALK, Item.Properties().rarity(Rarity.RARE))

	// Armors
	val MAGIC_HOOD: RegistryObject<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"magic_hood",
			ModArmorMaterials.MAGIC, ArmorItem.Type.HELMET,
			ModArmorItems.MAGIC_HOOD_PROPERTIES
		)
	val WATER_WALKING_BOOTS: RegistryObject<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"water_walking_boots",
			ModArmorMaterials.WATER_WALKING, ArmorItem.Type.BOOTS,
			ModArmorItems.WATER_WALKING_BOOTS_PROPERTIES
		)
	val OBSIDIAN_WATER_WALKING_BOOTS: RegistryObject<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"obsidian_water_walking_boots",
			ModArmorMaterials.OBSIDIAN_WATER_WALKING, ArmorItem.Type.BOOTS,
			ModArmorItems.OBSIDIAN_WATER_WALKING_BOOTS_PROPERTIES
		)
	val LAVA_WADERS: RegistryObject<ArmorItem> =
		ITEM_REGISTRY.registerItem("lava_waders") {
			object : ArmorItem(
				ModArmorMaterials.LAVA_WADERS,
				Type.BOOTS,
				ModArmorItems.LAVA_WADERS_PROPERTIES.get()
			) {
				override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
					LavaCharmItem.charge(stack)
				}
			}
		}
	val SPECTRE_HELMET: RegistryObject<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"spectre_helmet",
			ModArmorMaterials.SPECTRE, ArmorItem.Type.HELMET,
			ModArmorItems.SPECTRE_HELMET_PROPERTIES
		)
	val SPECTRE_CHESTPLATE: RegistryObject<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"spectre_chestplate",
			ModArmorMaterials.SPECTRE, ArmorItem.Type.CHESTPLATE,
			ModArmorItems.SPECTRE_CHESTPLATE_PROPERTIES
		)
	val SPECTRE_LEGGINGS: RegistryObject<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"spectre_leggings",
			ModArmorMaterials.SPECTRE, ArmorItem.Type.LEGGINGS,
			ModArmorItems.SPECTRE_LEGGINGS_PROPERTIES
		)
	val SPECTRE_BOOTS: RegistryObject<ArmorItem> =
		ModArmorItems.registerArmorItem(
			"spectre_boots",
			ModArmorMaterials.SPECTRE, ArmorItem.Type.BOOTS,
			ModArmorItems.SPECTRE_BOOTS_PROPERTIES
		)

	// Weather Eggs
	val WEATHER_EGG: RegistryObject<WeatherEggItem> =
		register("weather_egg", ::WeatherEggItem, WeatherEggItem.DEFAULT_PROPERTIES)

	// Filters
	val LOCATION_FILTER: RegistryObject<LocationFilterItem> =
		register("location_filter", ::LocationFilterItem, LocationFilterItem.DEFAULT_PROPERTIES)
	val ITEM_FILTER: RegistryObject<ItemFilterItem> =
		register("item_filter", ::ItemFilterItem, ItemFilterItem.DEFAULT_PROPERTIES)
	val ENTITY_FILTER: RegistryObject<EntityFilterItem> =
		register("entity_filter", ::EntityFilterItem, EntityFilterItem.DEFAULT_PROPERTIES)
	val PLAYER_FILTER: RegistryObject<PlayerFilterItem> =   //TODO: Rename to Player Filter?
		register("player_filter", ::PlayerFilterItem, PlayerFilterItem.DEFAULT_PROPERTIES)

	// Imbues
	val FIRE_IMBUE: RegistryObject<ImbueItem> =
		registerImbue("imbue_fire", ModEffects.FIRE_IMBUE)
	val POISON_IMBUE: RegistryObject<ImbueItem> =
		registerImbue("imbue_poison", ModEffects.POISON_IMBUE)
	val EXPERIENCE_IMBUE: RegistryObject<ImbueItem> =
		registerImbue("imbue_experience", ModEffects.EXPERIENCE_IMBUE)
	val WITHER_IMBUE: RegistryObject<ImbueItem> =
		registerImbue("imbue_wither", ModEffects.WITHER_IMBUE)
	val COLLAPSE_IMBUE: RegistryObject<ImbueItem> =
		registerImbue("imbue_collapse", ModEffects.COLLAPSE_IMBUE)
	val SPECTRE_IMBUE: RegistryObject<ImbueItem> =
		registerImbue("imbue_spectre", ModEffects.SPECTRE_IMBUE)

	// Spectre
	val SPECTRE_ILLUMINATOR: RegistryObject<SpectreIlluminatorItem> =
		register("spectre_illuminator", ::SpectreIlluminatorItem)
	val BLACKOUT_POWDER: RegistryObject<BlackoutPowderItem> =
		register("blackout_powder", ::BlackoutPowderItem)
	val SPECTRE_KEY: RegistryObject<SpectreKeyItem> =
		register("spectre_key", ::SpectreKeyItem)
	val SPECTRE_ANCHOR: RegistryObject<SpectreAnchorItem> =
		register("spectre_anchor", ::SpectreAnchorItem, SpectreAnchorItem.DEFAULT_PROPERTIES)
	val SPECTRE_CHARGER_BASIC: RegistryObject<SpectreChargerItem> =
		registerSpectreCharger("spectre_charger_basic", SpectreChargerItem.Type.BASIC)
	val SPECTRE_CHARGER_REDSTONE: RegistryObject<SpectreChargerItem> =
		registerSpectreCharger("spectre_charger_redstone", SpectreChargerItem.Type.REDSTONE)
	val SPECTRE_CHARGER_ENDER: RegistryObject<SpectreChargerItem> =
		registerSpectreCharger("spectre_charger_ender", SpectreChargerItem.Type.ENDER)
	val SPECTRE_CHARGER_GENESIS: RegistryObject<SpectreChargerItem> =
		registerSpectreCharger("spectre_charger_genesis", SpectreChargerItem.Type.GENESIS)
	val SPECTRE_SWORD: RegistryObject<SwordItem> =
		ModToolItems.registerSword("spectre_sword", ModToolItems.SPECTRE_TIER, ModToolItems.SPECTRE_SWORD_DEFAULT_PROPERTIES)
	val SPECTRE_PICKAXE: RegistryObject<PickaxeItem> =
		ModToolItems.registerPickaxe("spectre_pickaxe", ModToolItems.SPECTRE_TIER, ModToolItems.SPECTRE_PICKAXE_DEFAULT_PROPERTIES)
	val SPECTRE_AXE: RegistryObject<AxeItem> =
		ModToolItems.registerAxe("spectre_axe", ModToolItems.SPECTRE_TIER, ModToolItems.SPECTRE_AXE_DEFAULT_PROPERTIES)
	val SPECTRE_SHOVEL: RegistryObject<ShovelItem> =
		ModToolItems.registerShovel("spectre_shovel", ModToolItems.SPECTRE_TIER, ModToolItems.SPECTRE_SHOVEL_DEFAULT_PROPERTIES)

	// Redstone
	val REDSTONE_TOOL: RegistryObject<RedstoneToolItem> =
		register("redstone_tool", ::RedstoneToolItem, RedstoneToolItem.DEFAULT_PROPERTIES)
	val REDSTONE_ACTIVATOR: RegistryObject<RedstoneActivatorItem> =
		register("redstone_activator", ::RedstoneActivatorItem, RedstoneActivatorItem.DEFAULT_PROPERTIES)
	val REDSTONE_REMOTE: RegistryObject<RedstoneRemoteItem> =
		register("redstone_remote", ::RedstoneRemoteItem, RedstoneRemoteItem.DEFAULT_PROPERTIES)
	val ADVANCED_REDSTONE_TORCH =
		register(
			"advanced_redstone_torch",
			{
				StandingAndWallBlockItem(
					ModBlocks.ADVANCED_REDSTONE_TORCH.get(),
					ModBlocks.ADVANCED_REDSTONE_WALL_TORCH.get(),
					Item.Properties(),
					Direction.DOWN
				)
			}
		)

	// Floo
	val FLOO_POWDER: RegistryObject<Item> =
		basic("floo_powder")
	val FLOO_SIGN: RegistryObject<FlooSignItem> =
		register("floo_sign", ::FlooSignItem)
	val FLOO_TOKEN: RegistryObject<FlooTokenItem> =
		register("floo_token", ::FlooTokenItem)
	val FLOO_POUCH: RegistryObject<FlooPouchItem> =
		register("floo_pouch", ::FlooPouchItem, FlooPouchItem.DEFAULT_PROPERTIES)

	// Not above 1.7
	val BIOME_CAPSULE: RegistryObject<BiomeCapsuleItem> =
		register("biome_capsule", ::BiomeCapsuleItem, BiomeCapsuleItem.DEFAULT_PROPERTIES)
	val BIOME_PAINTER: RegistryObject<BiomePainterItem> =
		register("biome_painter", ::BiomePainterItem, BiomePainterItem.DEFAULT_PROPERTIES)
	val DROP_FILTER: RegistryObject<DropFilterItem> =
		register("drop_filter", ::DropFilterItem, DropFilterItem.DEFAULT_PROPERTIES)
	val VOIDING_DROP_FILTER: RegistryObject<DropFilterItem> =
		register("voiding_drop_filter", ::DropFilterItem, DropFilterItem.DEFAULT_PROPERTIES)
	val VOID_STONE: RegistryObject<VoidStoneItem> =
		register("void_stone", ::VoidStoneItem, VoidStoneItem.DEFAULT_PROPERTIES)
	val WHITE_STONE: RegistryObject<WhiteStoneItem> =
		register("white_stone", ::WhiteStoneItem, WhiteStoneItem.DEFAULT_PROPERTIES)

	// Not above 1.6.4
	val PORTABLE_ENDER_BRIDGE: RegistryObject<PortableEnderBridgeItem> =
		register("portable_ender_bridge", ::PortableEnderBridgeItem, PortableEnderBridgeItem.DEFAULT_PROPERTIES)
	val BLOCK_MOVER: RegistryObject<BlockMoverItem> =
		register("block_mover", ::BlockMoverItem, BlockMoverItem.DEFAULT_PROPERTIES)
	val DIAMOND_BREAKER: RegistryObject<Item> =
		basic("diamond_breaker")
	val BLOCK_REPLACER: RegistryObject<BlockReplacerItem> =
		register("block_replacer", ::BlockReplacerItem, BlockReplacerItem.DEFAULT_PROPERTIES)

	// Colors
	val GRASS_SEEDS: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds", color = null)
	val GRASS_SEEDS_WHITE: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_white", color = DyeColor.WHITE)
	val GRASS_SEEDS_ORANGE: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_orange", color = DyeColor.ORANGE)
	val GRASS_SEEDS_MAGENTA: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_magenta", color = DyeColor.MAGENTA)
	val GRASS_SEEDS_LIGHT_BLUE: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_light_blue", color = DyeColor.LIGHT_BLUE)
	val GRASS_SEEDS_YELLOW: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_yellow", color = DyeColor.YELLOW)
	val GRASS_SEEDS_LIME: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_lime", color = DyeColor.LIME)
	val GRASS_SEEDS_PINK: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_pink", color = DyeColor.PINK)
	val GRASS_SEEDS_GRAY: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_gray", color = DyeColor.GRAY)
	val GRASS_SEEDS_LIGHT_GRAY: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_light_gray", color = DyeColor.LIGHT_GRAY)
	val GRASS_SEEDS_CYAN: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_cyan", color = DyeColor.CYAN)
	val GRASS_SEEDS_PURPLE: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_purple", color = DyeColor.PURPLE)
	val GRASS_SEEDS_BLUE: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_blue", color = DyeColor.BLUE)
	val GRASS_SEEDS_BROWN: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_brown", color = DyeColor.BROWN)
	val GRASS_SEEDS_GREEN: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_green", color = DyeColor.GREEN)
	val GRASS_SEEDS_RED: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_red", color = DyeColor.RED)
	val GRASS_SEEDS_BLACK: RegistryObject<GrassSeedItem> =
		grassSeed("grass_seeds_black", color = DyeColor.BLACK)

	// Removed items:
	// - Time in a Bottle (Use the standalone one!)
	// - Eclipsed Clock (requires TIAB)
	// - Runic Dust (obscure and difficult)
	// - Blood Stone (requires Blood Moon)
	// - Precious Emerald (undocumented and weird)
	// - Sound Pattern, Recorder, Dampener (just use Super Sound Muffler)
	// - Loot Generator (don't care)
	// - Magnetic Force (don't care, obscure, arguably OP)

	private fun basic(id: String): RegistryObject<Item> {
		return ITEM_REGISTRY.register(id) { Item(Item.Properties()) }
	}

	private fun basicWithProperties(id: String, properties: Item.Properties): RegistryObject<Item> {
		return ITEM_REGISTRY.register(id) { Item(properties) }
	}

	private fun basicWithProperties(id: String, properties: Supplier<Item.Properties>): RegistryObject<Item> {
		return ITEM_REGISTRY.register(id) { Item(properties.get()) }
	}

	private fun <I : Item> register(
		id: String,
		builder: (Item.Properties) -> I,
		properties: Item.Properties = Item.Properties()
	): RegistryObject<I> {
		return ITEM_REGISTRY.register(id) { builder(properties) }
	}

	private fun <I : Item> register(
		id: String,
		builder: (Item.Properties) -> I,
		properties: Supplier<Item.Properties>
	): RegistryObject<I> {
		return ITEM_REGISTRY.register(id) { builder(properties.get()) }
	}

	private fun registerItemNameBlockItem(
		id: String,
		block: RegistryObject<out Block>,
		properties: Item.Properties = Item.Properties()
	): RegistryObject<ItemNameBlockItem> {
		return ITEM_REGISTRY.register(id) { ItemNameBlockItem(block.get(), properties) }
	}

	private fun registerImbue(
		id: String,
		mobEffect: Holder<MobEffect>,
		properties: Item.Properties = ImbueItem.DEFAULT_PROPERTIES
	): RegistryObject<ImbueItem> {
		return ITEM_REGISTRY.register(id) { ImbueItem(mobEffect, properties) }
	}

	private fun registerSpectreCharger(
		id: String,
		type: SpectreChargerItem.Type,
		properties: Item.Properties = SpectreChargerItem.DEFAULT_PROPERTIES
	): RegistryObject<SpectreChargerItem> {
		return ITEM_REGISTRY.register(id) { SpectreChargerItem(type, properties) }
	}

	private fun grassSeed(
		id: String,
		color: DyeColor?,
		properties: Item.Properties = Item.Properties()
	): RegistryObject<GrassSeedItem> {
		return ITEM_REGISTRY.register(id) { GrassSeedItem(color, properties) }
	}

}