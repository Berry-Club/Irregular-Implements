package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.SpecialChestBlock
import dev.aaronhowser.mods.irregular_implements.block.block_entity.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object ModBlockEntities {

	val BLOCK_ENTITY_REGISTRY: DeferredRegister<BlockEntityType<*>> =
		DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IrregularImplements.ID)

	val RAIN_SHIELD: DeferredHolder<BlockEntityType<*>, BlockEntityType<RainShieldBlockEntity>> =
		register("rain_shield", ::RainShieldBlockEntity, ModBlocks.RAIN_SHIELD.get())
	val REDSTONE_INTERFACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<RedstoneInterfaceBasicBlockEntity>> =
		register("redstone_interface", ::RedstoneInterfaceBasicBlockEntity, ModBlocks.BASIC_REDSTONE_INTERFACE.get())
	val REDSTONE_OBSERVER: DeferredHolder<BlockEntityType<*>, BlockEntityType<RedstoneObserverBlockEntity>> =
		register("redstone_observer", ::RedstoneObserverBlockEntity, ModBlocks.REDSTONE_OBSERVER.get())
	val BLOCK_DESTABILIZER: DeferredHolder<BlockEntityType<*>, BlockEntityType<BlockDestabilizerBlockEntity>> =
		register("block_destabilizer", ::BlockDestabilizerBlockEntity, ModBlocks.BLOCK_DESTABILIZER.get())
	val BLOCK_BREAKER: DeferredHolder<BlockEntityType<*>, BlockEntityType<BlockBreakerBlockEntity>> =
		register("block_breaker", ::BlockBreakerBlockEntity, ModBlocks.BLOCK_BREAKER.get())
	val SPECTRE_LENS: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreLensBlockEntity>> =
		register("spectre_lens", ::SpectreLensBlockEntity, ModBlocks.SPECTRE_LENS.get())
	val SPECTRE_ENERGY_INJECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreEnergyInjectorBlockEntity>> =
		register("spectre_energy_injector", ::SpectreEnergyInjectorBlockEntity, ModBlocks.SPECTRE_ENERGY_INJECTOR.get())
	val SPECTRE_COIL: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreCoilBlockEntity>> =
		register(
			"spectre_coil",
			::SpectreCoilBlockEntity,
			ModBlocks.SPECTRE_COIL_BASIC.get(),
			ModBlocks.SPECTRE_COIL_REDSTONE.get(),
			ModBlocks.SPECTRE_COIL_ENDER.get(),
			ModBlocks.SPECTRE_COIL_NUMBER.get(),
			ModBlocks.SPECTRE_COIL_GENESIS.get()
		)
	val MOON_PHASE_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<MoonPhaseDetectorBlockEntity>> =
		register("moon_phase_detector", ::MoonPhaseDetectorBlockEntity, ModBlocks.MOON_PHASE_DETECTOR.get())
	val CHAT_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<ChatDetectorBlockEntity>> =
		register("chat_detector", ::ChatDetectorBlockEntity, ModBlocks.CHAT_DETECTOR.get())
	val GLOBAL_CHAT_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<GlobalChatDetectorBlockEntity>> =
		register("global_chat_detector", ::GlobalChatDetectorBlockEntity, ModBlocks.GLOBAL_CHAT_DETECTOR.get())
	val ONLINE_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<OnlineDetectorBlockEntity>> =
		register("online_detector", ::OnlineDetectorBlockEntity, ModBlocks.ONLINE_DETECTOR.get())
	val DIAPHANOUS_BLOCK: DeferredHolder<BlockEntityType<*>, BlockEntityType<DiaphanousBlockEntity>> =
		register("diaphanous_block", ::DiaphanousBlockEntity, ModBlocks.DIAPHANOUS_BLOCK.get())
	val IRON_DROPPER: DeferredHolder<BlockEntityType<*>, BlockEntityType<IronDropperBlockEntity>> =
		register("iron_dropper", ::IronDropperBlockEntity, ModBlocks.IRON_DROPPER.get())
	val CUSTOM_CRAFTING_TABLE: DeferredHolder<BlockEntityType<*>, BlockEntityType<CustomCraftingTableBlockEntity>> =
		register("custom_crafting_table", ::CustomCraftingTableBlockEntity, ModBlocks.CUSTOM_CRAFTING_TABLE.get())
	val IGNITER: DeferredHolder<BlockEntityType<*>, BlockEntityType<IgniterBlockEntity>> =
		register("igniter", ::IgniterBlockEntity, ModBlocks.IGNITER.get())
	val NOTIFICATION_INTERFACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<NotificationInterfaceBlockEntity>> =
		register("notification_interface", ::NotificationInterfaceBlockEntity, ModBlocks.NOTIFICATION_INTERFACE.get())
	val IMBUING_STATION: DeferredHolder<BlockEntityType<*>, BlockEntityType<ImbuingStationBlockEntity>> =
		register("imbuing_station", ::ImbuingStationBlockEntity, ModBlocks.IMBUING_STATION.get())
	val FILTERED_PLATFORM: DeferredHolder<BlockEntityType<*>, BlockEntityType<FilteredPlatformBlockEntity>> =
		register("filtered_platform", ::FilteredPlatformBlockEntity, ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM.get())
	val INVENTORY_TESTER: DeferredHolder<BlockEntityType<*>, BlockEntityType<InventoryTesterBlockEntity>> =
		register("inventory_tester", ::InventoryTesterBlockEntity, ModBlocks.INVENTORY_TESTER.get())
	val ITEM_COLLECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<ItemCollectorBlockEntity>> =
		register("item_collector", ::ItemCollectorBlockEntity, ModBlocks.ITEM_COLLECTOR.get())
	val ADVANCED_ITEM_COLLECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<AdvancedItemCollectorBlockEntity>> =
		register("advanced_item_collector", ::AdvancedItemCollectorBlockEntity, ModBlocks.ADVANCED_ITEM_COLLECTOR.get())

	@JvmField
	val NATURE_CHEST: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpecialChestBlock.NatureChestBlockEntity>> =
		register(
			"nature_chest",
			{ pos, state -> SpecialChestBlock.NatureChestBlockEntity(pos, state) },
			ModBlocks.NATURE_CHEST.get()
		)

	@JvmField
	val WATER_CHEST: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpecialChestBlock.WaterChestBlockEntity>> =
		register(
			"water_chest",
			{ pos, state -> SpecialChestBlock.WaterChestBlockEntity(pos, state) },
			ModBlocks.WATER_CHEST.get()
		)

	val PEACE_CANDLE: DeferredHolder<BlockEntityType<*>, BlockEntityType<PeaceCandleBlockEntity>> =
		register("peace_candle", ::PeaceCandleBlockEntity, ModBlocks.PEACE_CANDLE.get())
	val PLAYER_INTERFACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<PlayerInterfaceBlockEntity>> =
		register("player_interface", ::PlayerInterfaceBlockEntity, ModBlocks.PLAYER_INTERFACE.get())
	val FLOO_BRICK: DeferredHolder<BlockEntityType<*>, BlockEntityType<FlooBrickBlockEntity>> =
		register("floo_brick", ::FlooBrickBlockEntity, ModBlocks.FLOO_BRICK.get())
	val ENERGY_DISTRIBUTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<EnergyDistributorBlockEntity>> =
		register("energy_distributor", ::EnergyDistributorBlockEntity, ModBlocks.ENERGY_DISTRIBUTOR.get())
	val ENDER_ENERGY_DISTRIBUTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<EnderEnergyDistributorBlockEntity>> =
		register("ender_energy_distributor", ::EnderEnergyDistributorBlockEntity, ModBlocks.ENDER_ENERGY_DISTRIBUTOR.get())

	private fun <T : BlockEntity> register(
		name: String,
		builder: BlockEntityType.BlockEntitySupplier<out T>,
		vararg validBlocks: Block
	): DeferredHolder<BlockEntityType<*>, BlockEntityType<T>> {
		return BLOCK_ENTITY_REGISTRY.register(name, Supplier {
			BlockEntityType.Builder.of(
				builder,
				*validBlocks
			).build(null)
		})
	}

}