package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object ModBlockEntityTypes {

	val BLOCK_ENTITY_REGISTRY: DeferredRegister<BlockEntityType<*>> =
		DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IrregularImplements.ID)

	val RAIN_SHIELD: DeferredHolder<BlockEntityType<*>, BlockEntityType<RainShieldBlockEntity>> =
		register("rain_shield", ::RainShieldBlockEntity, ModBlocks.RAIN_SHIELD)
	val BASIC_REDSTONE_INTERFACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<RedstoneInterfaceBasicBlockEntity>> =
		register("basic_redstone_interface", ::RedstoneInterfaceBasicBlockEntity, ModBlocks.BASIC_REDSTONE_INTERFACE)
	val ADVANCED_REDSTONE_INTERFACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<RedstoneInterfaceAdvancedBlockEntity>> =
		register("advanced_redstone_interface", ::RedstoneInterfaceAdvancedBlockEntity, ModBlocks.ADVANCED_REDSTONE_INTERFACE)
	val REDSTONE_OBSERVER: DeferredHolder<BlockEntityType<*>, BlockEntityType<RedstoneObserverBlockEntity>> =
		register("redstone_observer", ::RedstoneObserverBlockEntity, ModBlocks.REDSTONE_OBSERVER)
	val BLOCK_DESTABILIZER: DeferredHolder<BlockEntityType<*>, BlockEntityType<BlockDestabilizerBlockEntity>> =
		register("block_destabilizer", ::BlockDestabilizerBlockEntity, ModBlocks.BLOCK_DESTABILIZER)
	val BLOCK_BREAKER: DeferredHolder<BlockEntityType<*>, BlockEntityType<BlockBreakerBlockEntity>> =
		register("block_breaker", ::BlockBreakerBlockEntity, ModBlocks.BLOCK_BREAKER)
	val SPECTRE_LENS: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreLensBlockEntity>> =
		register("spectre_lens", ::SpectreLensBlockEntity, ModBlocks.SPECTRE_LENS)
	val SPECTRE_ENERGY_INJECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreEnergyInjectorBlockEntity>> =
		register("spectre_energy_injector", ::SpectreEnergyInjectorBlockEntity, ModBlocks.SPECTRE_ENERGY_INJECTOR)
	val SPECTRE_COIL: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreCoilBlockEntity>> =
		register(
			"spectre_coil",
			::SpectreCoilBlockEntity,
			ModBlocks.SPECTRE_COIL_BASIC,
			ModBlocks.SPECTRE_COIL_REDSTONE,
			ModBlocks.SPECTRE_COIL_ENDER,
			ModBlocks.SPECTRE_COIL_NUMBER,
			ModBlocks.SPECTRE_COIL_GENESIS
		)
	val MOON_PHASE_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<MoonPhaseDetectorBlockEntity>> =
		register("moon_phase_detector", ::MoonPhaseDetectorBlockEntity, ModBlocks.MOON_PHASE_DETECTOR)
	val CHAT_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<ChatDetectorBlockEntity>> =
		register("chat_detector", ::ChatDetectorBlockEntity, ModBlocks.CHAT_DETECTOR)
	val GLOBAL_CHAT_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<GlobalChatDetectorBlockEntity>> =
		register("global_chat_detector", ::GlobalChatDetectorBlockEntity, ModBlocks.GLOBAL_CHAT_DETECTOR)
	val ONLINE_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<OnlineDetectorBlockEntity>> =
		register("online_detector", ::OnlineDetectorBlockEntity, ModBlocks.ONLINE_DETECTOR)
	val DIAPHANOUS_BLOCK: DeferredHolder<BlockEntityType<*>, BlockEntityType<DiaphanousBlockEntity>> =
		register("diaphanous_block", ::DiaphanousBlockEntity, ModBlocks.DIAPHANOUS_BLOCK)
	val IRON_DROPPER: DeferredHolder<BlockEntityType<*>, BlockEntityType<IronDropperBlockEntity>> =
		register("iron_dropper", ::IronDropperBlockEntity, ModBlocks.IRON_DROPPER)
	val CUSTOM_CRAFTING_TABLE: DeferredHolder<BlockEntityType<*>, BlockEntityType<CustomCraftingTableBlockEntity>> =
		register("custom_crafting_table", ::CustomCraftingTableBlockEntity, ModBlocks.CUSTOM_CRAFTING_TABLE)
	val IGNITER: DeferredHolder<BlockEntityType<*>, BlockEntityType<IgniterBlockEntity>> =
		register("igniter", ::IgniterBlockEntity, ModBlocks.IGNITER)
	val NOTIFICATION_INTERFACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<NotificationInterfaceBlockEntity>> =
		register("notification_interface", ::NotificationInterfaceBlockEntity, ModBlocks.NOTIFICATION_INTERFACE)
	val IMBUING_STATION: DeferredHolder<BlockEntityType<*>, BlockEntityType<ImbuingStationBlockEntity>> =
		register("imbuing_station", ::ImbuingStationBlockEntity, ModBlocks.IMBUING_STATION)
	val FILTERED_PLATFORM: DeferredHolder<BlockEntityType<*>, BlockEntityType<FilteredPlatformBlockEntity>> =
		register("filtered_platform", ::FilteredPlatformBlockEntity, ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM)
	val INVENTORY_TESTER: DeferredHolder<BlockEntityType<*>, BlockEntityType<InventoryTesterBlockEntity>> =
		register("inventory_tester", ::InventoryTesterBlockEntity, ModBlocks.INVENTORY_TESTER)
	val ITEM_COLLECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<ItemCollectorBlockEntity>> =
		register("item_collector", ::ItemCollectorBlockEntity, ModBlocks.ITEM_COLLECTOR)
	val ADVANCED_ITEM_COLLECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<AdvancedItemCollectorBlockEntity>> =
		register("advanced_item_collector", ::AdvancedItemCollectorBlockEntity, ModBlocks.ADVANCED_ITEM_COLLECTOR)

	@JvmField
	val NATURE_CHEST: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpecialChestBlockEntity.NatureChestBlockEntity>> =
		register(
			"nature_chest",
			{ pos, state -> SpecialChestBlockEntity.NatureChestBlockEntity(pos, state) },
			ModBlocks.NATURE_CHEST
		)

	@JvmField
	val WATER_CHEST: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpecialChestBlockEntity.WaterChestBlockEntity>> =
		register(
			"water_chest",
			{ pos, state -> SpecialChestBlockEntity.WaterChestBlockEntity(pos, state) },
			ModBlocks.WATER_CHEST
		)

	val PEACE_CANDLE: DeferredHolder<BlockEntityType<*>, BlockEntityType<PeaceCandleBlockEntity>> =
		register("peace_candle", ::PeaceCandleBlockEntity, ModBlocks.PEACE_CANDLE)
	val PLAYER_INTERFACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<PlayerInterfaceBlockEntity>> =
		register("player_interface", ::PlayerInterfaceBlockEntity, ModBlocks.PLAYER_INTERFACE)
	val FLOO_BRICK: DeferredHolder<BlockEntityType<*>, BlockEntityType<FlooBrickBlockEntity>> =
		register("floo_brick", ::FlooBrickBlockEntity, ModBlocks.FLOO_BRICK)
	val ENERGY_DISTRIBUTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<EnergyDistributorBlockEntity>> =
		register("energy_distributor", ::EnergyDistributorBlockEntity, ModBlocks.ENERGY_DISTRIBUTOR)
	val ENDER_ENERGY_DISTRIBUTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<EnderEnergyDistributorBlockEntity>> =
		register("ender_energy_distributor", ::EnderEnergyDistributorBlockEntity, ModBlocks.ENDER_ENERGY_DISTRIBUTOR)
	val SLIME_CUBE: DeferredHolder<BlockEntityType<*>, BlockEntityType<SlimeCubeBlockEntity>> =
		register("slime_cube", ::SlimeCubeBlockEntity, ModBlocks.SLIME_CUBE)
	val ENDER_MAILBOX: DeferredHolder<BlockEntityType<*>, BlockEntityType<EnderMailboxBlockEntity>> =
		register("ender_mailbox", ::EnderMailboxBlockEntity, ModBlocks.ENDER_MAILBOX)
	val BLOCK_TELEPORTER: DeferredHolder<BlockEntityType<*>, BlockEntityType<BlockTeleporterBlockEntity>> =
		register("block_teleporter", ::BlockTeleporterBlockEntity, ModBlocks.BLOCK_TELEPORTER)
	val BLOCK_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<BlockDetectorBlockEntity>> =
		register("block_detector", ::BlockDetectorBlockEntity, ModBlocks.BLOCK_DETECTOR)
	val NATURE_CORE: DeferredHolder<BlockEntityType<*>, BlockEntityType<NatureCoreBlockEntity>> =
		register("nature_core", ::NatureCoreBlockEntity, ModBlocks.NATURE_CORE)
	val AUTO_PLACER: DeferredHolder<BlockEntityType<*>, BlockEntityType<AutoPlacerBlockEntity>> =
		register("auto_placer", ::AutoPlacerBlockEntity, ModBlocks.AUTO_PLACER)
	val BIOME_RADAR: DeferredHolder<BlockEntityType<*>, BlockEntityType<BiomeRadarBlockEntity>> =
		register("biome_radar", ::BiomeRadarBlockEntity, ModBlocks.BIOME_RADAR)
	val ENTITY_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<EntityDetectorBlockEntity>> =
		register("entity_detector", ::EntityDetectorBlockEntity, ModBlocks.ENTITY_DETECTOR)
	val ADVANCED_REDSTONE_TORCH: DeferredHolder<BlockEntityType<*>, BlockEntityType<AdvancedRedstoneTorchBlockEntity>> =
		register(
			"advanced_redstone_torch",
			::AdvancedRedstoneTorchBlockEntity,
			ModBlocks.ADVANCED_REDSTONE_TORCH, ModBlocks.ADVANCED_REDSTONE_WALL_TORCH
		)

	private fun <T : BlockEntity> register(
		name: String,
		builder: BlockEntityType.BlockEntitySupplier<out T>,
		vararg validBlocks: DeferredBlock<*>
	): DeferredHolder<BlockEntityType<*>, BlockEntityType<T>> {
		return BLOCK_ENTITY_REGISTRY.register(name, Supplier {
			BlockEntityType.Builder.of(
				builder,
				*validBlocks.map(DeferredBlock<*>::get).toTypedArray()
			).build(null)
		})
	}

}