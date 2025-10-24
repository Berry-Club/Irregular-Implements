package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.*
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object ModBlockEntityTypes {

	val BLOCK_ENTITY_REGISTRY: DeferredRegister<BlockEntityType<*>> =
		DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IrregularImplements.ID)

	val RAIN_SHIELD: RegistryObject<BlockEntityType<RainShieldBlockEntity>> =
		register("rain_shield", ::RainShieldBlockEntity, ModBlocks.RAIN_SHIELD)
	val BASIC_REDSTONE_INTERFACE: RegistryObject<BlockEntityType<RedstoneInterfaceBasicBlockEntity>> =
		register("basic_redstone_interface", ::RedstoneInterfaceBasicBlockEntity, ModBlocks.BASIC_REDSTONE_INTERFACE)
	val ADVANCED_REDSTONE_INTERFACE: RegistryObject<BlockEntityType<RedstoneInterfaceAdvancedBlockEntity>> =
		register("advanced_redstone_interface", ::RedstoneInterfaceAdvancedBlockEntity, ModBlocks.ADVANCED_REDSTONE_INTERFACE)
	val REDSTONE_OBSERVER: RegistryObject<BlockEntityType<RedstoneObserverBlockEntity>> =
		register("redstone_observer", ::RedstoneObserverBlockEntity, ModBlocks.REDSTONE_OBSERVER)
	val BLOCK_DESTABILIZER: RegistryObject<BlockEntityType<BlockDestabilizerBlockEntity>> =
		register("block_destabilizer", ::BlockDestabilizerBlockEntity, ModBlocks.BLOCK_DESTABILIZER)
	val BLOCK_BREAKER: RegistryObject<BlockEntityType<BlockBreakerBlockEntity>> =
		register("block_breaker", ::BlockBreakerBlockEntity, ModBlocks.BLOCK_BREAKER)
	val SPECTRE_LENS: RegistryObject<BlockEntityType<SpectreLensBlockEntity>> =
		register("spectre_lens", ::SpectreLensBlockEntity, ModBlocks.SPECTRE_LENS)
	val SPECTRE_ENERGY_INJECTOR: RegistryObject<BlockEntityType<SpectreEnergyInjectorBlockEntity>> =
		register("spectre_energy_injector", ::SpectreEnergyInjectorBlockEntity, ModBlocks.SPECTRE_ENERGY_INJECTOR)
	val SPECTRE_COIL: RegistryObject<BlockEntityType<SpectreCoilBlockEntity>> =
		register(
			"spectre_coil",
			::SpectreCoilBlockEntity,
			ModBlocks.SPECTRE_COIL_BASIC,
			ModBlocks.SPECTRE_COIL_REDSTONE,
			ModBlocks.SPECTRE_COIL_ENDER,
			ModBlocks.SPECTRE_COIL_NUMBER,
			ModBlocks.SPECTRE_COIL_GENESIS
		)
	val MOON_PHASE_DETECTOR: RegistryObject<BlockEntityType<MoonPhaseDetectorBlockEntity>> =
		register("moon_phase_detector", ::MoonPhaseDetectorBlockEntity, ModBlocks.MOON_PHASE_DETECTOR)
	val CHAT_DETECTOR: RegistryObject<BlockEntityType<ChatDetectorBlockEntity>> =
		register("chat_detector", ::ChatDetectorBlockEntity, ModBlocks.CHAT_DETECTOR)
	val GLOBAL_CHAT_DETECTOR: RegistryObject<BlockEntityType<GlobalChatDetectorBlockEntity>> =
		register("global_chat_detector", ::GlobalChatDetectorBlockEntity, ModBlocks.GLOBAL_CHAT_DETECTOR)
	val ONLINE_DETECTOR: RegistryObject<BlockEntityType<OnlineDetectorBlockEntity>> =
		register("online_detector", ::OnlineDetectorBlockEntity, ModBlocks.ONLINE_DETECTOR)
	val DIAPHANOUS_BLOCK: RegistryObject<BlockEntityType<DiaphanousBlockEntity>> =
		register("diaphanous_block", ::DiaphanousBlockEntity, ModBlocks.DIAPHANOUS_BLOCK)
	val IRON_DROPPER: RegistryObject<BlockEntityType<IronDropperBlockEntity>> =
		register("iron_dropper", ::IronDropperBlockEntity, ModBlocks.IRON_DROPPER)
	val CUSTOM_CRAFTING_TABLE: RegistryObject<BlockEntityType<CustomCraftingTableBlockEntity>> =
		register("custom_crafting_table", ::CustomCraftingTableBlockEntity, ModBlocks.CUSTOM_CRAFTING_TABLE)
	val IGNITER: RegistryObject<BlockEntityType<IgniterBlockEntity>> =
		register("igniter", ::IgniterBlockEntity, ModBlocks.IGNITER)
	val NOTIFICATION_INTERFACE: RegistryObject<BlockEntityType<NotificationInterfaceBlockEntity>> =
		register("notification_interface", ::NotificationInterfaceBlockEntity, ModBlocks.NOTIFICATION_INTERFACE)
	val IMBUING_STATION: RegistryObject<BlockEntityType<ImbuingStationBlockEntity>> =
		register("imbuing_station", ::ImbuingStationBlockEntity, ModBlocks.IMBUING_STATION)
	val FILTERED_PLATFORM: RegistryObject<BlockEntityType<FilteredPlatformBlockEntity>> =
		register("filtered_platform", ::FilteredPlatformBlockEntity, ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM)
	val INVENTORY_TESTER: RegistryObject<BlockEntityType<InventoryTesterBlockEntity>> =
		register("inventory_tester", ::InventoryTesterBlockEntity, ModBlocks.INVENTORY_TESTER)
	val ITEM_COLLECTOR: RegistryObject<BlockEntityType<ItemCollectorBlockEntity>> =
		register("item_collector", ::ItemCollectorBlockEntity, ModBlocks.ITEM_COLLECTOR)
	val ADVANCED_ITEM_COLLECTOR: RegistryObject<BlockEntityType<AdvancedItemCollectorBlockEntity>> =
		register("advanced_item_collector", ::AdvancedItemCollectorBlockEntity, ModBlocks.ADVANCED_ITEM_COLLECTOR)

	@JvmField
	val NATURE_CHEST: RegistryObject<BlockEntityType<SpecialChestBlockEntity.NatureChestBlockEntity>> =
		register(
			"nature_chest",
			{ pos, state -> SpecialChestBlockEntity.NatureChestBlockEntity(pos, state) },
			ModBlocks.NATURE_CHEST
		)

	@JvmField
	val WATER_CHEST: RegistryObject<BlockEntityType<SpecialChestBlockEntity.WaterChestBlockEntity>> =
		register(
			"water_chest",
			{ pos, state -> SpecialChestBlockEntity.WaterChestBlockEntity(pos, state) },
			ModBlocks.WATER_CHEST
		)

	val PEACE_CANDLE: RegistryObject<BlockEntityType<PeaceCandleBlockEntity>> =
		register("peace_candle", ::PeaceCandleBlockEntity, ModBlocks.PEACE_CANDLE)
	val PLAYER_INTERFACE: RegistryObject<BlockEntityType<PlayerInterfaceBlockEntity>> =
		register("player_interface", ::PlayerInterfaceBlockEntity, ModBlocks.PLAYER_INTERFACE)
	val FLOO_BRICK: RegistryObject<BlockEntityType<FlooBrickBlockEntity>> =
		register("floo_brick", ::FlooBrickBlockEntity, ModBlocks.FLOO_BRICK)
	val ENERGY_DISTRIBUTOR: RegistryObject<BlockEntityType<EnergyDistributorBlockEntity>> =
		register("energy_distributor", ::EnergyDistributorBlockEntity, ModBlocks.ENERGY_DISTRIBUTOR)
	val ENDER_ENERGY_DISTRIBUTOR: RegistryObject<BlockEntityType<EnderEnergyDistributorBlockEntity>> =
		register("ender_energy_distributor", ::EnderEnergyDistributorBlockEntity, ModBlocks.ENDER_ENERGY_DISTRIBUTOR)
	val SLIME_CUBE: RegistryObject<BlockEntityType<SlimeCubeBlockEntity>> =
		register("slime_cube", ::SlimeCubeBlockEntity, ModBlocks.SLIME_CUBE)
	val ENDER_MAILBOX: RegistryObject<BlockEntityType<EnderMailboxBlockEntity>> =
		register("ender_mailbox", ::EnderMailboxBlockEntity, ModBlocks.ENDER_MAILBOX)
	val BLOCK_TELEPORTER: RegistryObject<BlockEntityType<BlockTeleporterBlockEntity>> =
		register("block_teleporter", ::BlockTeleporterBlockEntity, ModBlocks.BLOCK_TELEPORTER)
	val BLOCK_DETECTOR: RegistryObject<BlockEntityType<BlockDetectorBlockEntity>> =
		register("block_detector", ::BlockDetectorBlockEntity, ModBlocks.BLOCK_DETECTOR)
	val NATURE_CORE: RegistryObject<BlockEntityType<NatureCoreBlockEntity>> =
		register("nature_core", ::NatureCoreBlockEntity, ModBlocks.NATURE_CORE)
	val AUTO_PLACER: RegistryObject<BlockEntityType<AutoPlacerBlockEntity>> =
		register("auto_placer", ::AutoPlacerBlockEntity, ModBlocks.AUTO_PLACER)
	val BIOME_RADAR: RegistryObject<BlockEntityType<BiomeRadarBlockEntity>> =
		register("biome_radar", ::BiomeRadarBlockEntity, ModBlocks.BIOME_RADAR)
	val ENTITY_DETECTOR: RegistryObject<BlockEntityType<EntityDetectorBlockEntity>> =
		register("entity_detector", ::EntityDetectorBlockEntity, ModBlocks.ENTITY_DETECTOR)
	val ADVANCED_REDSTONE_TORCH: RegistryObject<BlockEntityType<AdvancedRedstoneTorchBlockEntity>> =
		register(
			"advanced_redstone_torch",
			::AdvancedRedstoneTorchBlockEntity,
			ModBlocks.ADVANCED_REDSTONE_TORCH, ModBlocks.ADVANCED_REDSTONE_WALL_TORCH
		)

	private fun <T : BlockEntity> register(
		name: String,
		builder: BlockEntityType.BlockEntitySupplier<out T>,
		vararg validBlocks: RegistryObject<out Block>
	): RegistryObject<BlockEntityType<T>> {
		return BLOCK_ENTITY_REGISTRY.register(name, Supplier {
			BlockEntityType.Builder.of(
				builder,
				*validBlocks.map(RegistryObject<out Block>::get).toTypedArray()
			).build(null)
		})
	}

}