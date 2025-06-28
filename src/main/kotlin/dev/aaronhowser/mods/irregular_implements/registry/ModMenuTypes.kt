package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.menu.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModMenuTypes {

	val MENU_TYPE_REGISTRY: DeferredRegister<MenuType<*>> =
		DeferredRegister.create(BuiltInRegistries.MENU, IrregularImplements.ID)

	val IRON_DROPPER: DeferredHolder<MenuType<*>, MenuType<IronDropperMenu>> =
		MENU_TYPE_REGISTRY.register("iron_dropper", Supplier {
			MenuType(::IronDropperMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val BLOCK_DESTABILIZER: DeferredHolder<MenuType<*>, MenuType<BlockDestabilizerMenu>> =
		MENU_TYPE_REGISTRY.register("block_destabilizer", Supplier {
			MenuType(::BlockDestabilizerMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val CHAT_DETECTOR: DeferredHolder<MenuType<*>, MenuType<ChatDetectorMenu>> =
		MENU_TYPE_REGISTRY.register("chat_detector", Supplier {
			MenuType(::ChatDetectorMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val GLOBAL_CHAT_DETECTOR: DeferredHolder<MenuType<*>, MenuType<GlobalChatDetectorMenu>> =
		MENU_TYPE_REGISTRY.register("global_chat_detector", Supplier {
			MenuType(::GlobalChatDetectorMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val IGNITER: DeferredHolder<MenuType<*>, MenuType<IgniterMenu>> =
		MENU_TYPE_REGISTRY.register("igniter", Supplier {
			MenuType(::IgniterMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val VOID_STONE: DeferredHolder<MenuType<*>, MenuType<VoidStoneMenu>> =
		MENU_TYPE_REGISTRY.register("void_stone", Supplier {
			MenuType(::VoidStoneMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val ONLINE_DETECTOR: DeferredHolder<MenuType<*>, MenuType<OnlineDetectorMenu>> =
		MENU_TYPE_REGISTRY.register("online_detector", Supplier {
			MenuType(::OnlineDetectorMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val NOTIFICATION_INTERFACE: DeferredHolder<MenuType<*>, MenuType<NotificationInterfaceMenu>> =
		MENU_TYPE_REGISTRY.register("notification_interface", Supplier {
			MenuType(::NotificationInterfaceMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val IMBUING_STATION: DeferredHolder<MenuType<*>, MenuType<ImbuingStationMenu>> =
		MENU_TYPE_REGISTRY.register("imbuing_station", Supplier {
			MenuType(::ImbuingStationMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val ITEM_FILTER: DeferredHolder<MenuType<*>, MenuType<ItemFilterMenu>> =
		MENU_TYPE_REGISTRY.register("item_filter", Supplier {
			MenuType(::ItemFilterMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val FILTERED_PLATFORM: DeferredHolder<MenuType<*>, MenuType<FilteredPlatformMenu>> =
		MENU_TYPE_REGISTRY.register("filtered_platform", Supplier {
			MenuType(::FilteredPlatformMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val DROP_FILTER: DeferredHolder<MenuType<*>, MenuType<DropFilterMenu>> =
		MENU_TYPE_REGISTRY.register("drop_filter", Supplier {
			MenuType(::DropFilterMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val INVENTORY_TESTER: DeferredHolder<MenuType<*>, MenuType<InventoryTesterMenu>> =
		MENU_TYPE_REGISTRY.register("inventory_tester", Supplier {
			MenuType(::InventoryTesterMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	val ADVANCED_ITEM_COLLECTOR: DeferredHolder<MenuType<*>, MenuType<AdvancedItemCollectorMenu>> =
		MENU_TYPE_REGISTRY.register("advanced_item_collector", Supplier {
			MenuType(::AdvancedItemCollectorMenu, FeatureFlags.DEFAULT_FLAGS)
		})

	fun registerScreens(event: RegisterMenuScreensEvent) {
		event.register(IRON_DROPPER.get(), ::IronDropperScreen)
		event.register(BLOCK_DESTABILIZER.get(), ::BlockDestabilizerScreen)
		event.register(CHAT_DETECTOR.get(), ::ChatDetectorScreen)
		event.register(GLOBAL_CHAT_DETECTOR.get(), ::GlobalChatDetectorScreen)
		event.register(IGNITER.get(), ::IgniterScreen)
		event.register(VOID_STONE.get(), ::VoidStoneScreen)
		event.register(ONLINE_DETECTOR.get(), ::OnlineDetectorScreen)
		event.register(NOTIFICATION_INTERFACE.get(), ::NotificationInterfaceScreen)
		event.register(IMBUING_STATION.get(), ::ImbuingStationScreen)
		event.register(ITEM_FILTER.get(), ::ItemFilterScreen)
		event.register(FILTERED_PLATFORM.get(), ::FilteredPlatformScreen)
		event.register(DROP_FILTER.get(), ::DropFilterScreen)
		event.register(INVENTORY_TESTER.get(), ::InventoryTesterScreen)
		event.register(ADVANCED_ITEM_COLLECTOR.get(), ::AdvancedItemCollectorScreen)
	}

}