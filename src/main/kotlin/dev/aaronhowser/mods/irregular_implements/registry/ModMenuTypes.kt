package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.menu.advanced_item_collector.AdvancedItemCollectorMenu
import dev.aaronhowser.mods.irregular_implements.menu.advanced_item_collector.AdvancedItemCollectorScreen
import dev.aaronhowser.mods.irregular_implements.menu.block_destabilizer.BlockDestabilizerMenu
import dev.aaronhowser.mods.irregular_implements.menu.block_destabilizer.BlockDestabilizerScreen
import dev.aaronhowser.mods.irregular_implements.menu.chat_detector.ChatDetectorMenu
import dev.aaronhowser.mods.irregular_implements.menu.chat_detector.ChatDetectorScreen
import dev.aaronhowser.mods.irregular_implements.menu.drop_filter.DropFilterMenu
import dev.aaronhowser.mods.irregular_implements.menu.drop_filter.DropFilterScreen
import dev.aaronhowser.mods.irregular_implements.menu.ender_energy_distributor.EnderEnergyDistributorMenu
import dev.aaronhowser.mods.irregular_implements.menu.ender_energy_distributor.EnderEnergyDistributorScreen
import dev.aaronhowser.mods.irregular_implements.menu.filtered_platform.FilteredPlatformMenu
import dev.aaronhowser.mods.irregular_implements.menu.filtered_platform.FilteredPlatformScreen
import dev.aaronhowser.mods.irregular_implements.menu.global_chat_detector.GlobalChatDetectorMenu
import dev.aaronhowser.mods.irregular_implements.menu.global_chat_detector.GlobalChatDetectorScreen
import dev.aaronhowser.mods.irregular_implements.menu.igniter.IgniterMenu
import dev.aaronhowser.mods.irregular_implements.menu.igniter.IgniterScreen
import dev.aaronhowser.mods.irregular_implements.menu.imbuing_station.ImbuingStationMenu
import dev.aaronhowser.mods.irregular_implements.menu.imbuing_station.ImbuingStationScreen
import dev.aaronhowser.mods.irregular_implements.menu.inventory_tester.InventoryTesterMenu
import dev.aaronhowser.mods.irregular_implements.menu.inventory_tester.InventoryTesterScreen
import dev.aaronhowser.mods.irregular_implements.menu.iron_dropper.IronDropperMenu
import dev.aaronhowser.mods.irregular_implements.menu.iron_dropper.IronDropperScreen
import dev.aaronhowser.mods.irregular_implements.menu.item_filter.ItemFilterMenu
import dev.aaronhowser.mods.irregular_implements.menu.item_filter.ItemFilterScreen
import dev.aaronhowser.mods.irregular_implements.menu.notification_interface.NotificationInterfaceMenu
import dev.aaronhowser.mods.irregular_implements.menu.notification_interface.NotificationInterfaceScreen
import dev.aaronhowser.mods.irregular_implements.menu.online_detector.OnlineDetectorMenu
import dev.aaronhowser.mods.irregular_implements.menu.online_detector.OnlineDetectorScreen
import dev.aaronhowser.mods.irregular_implements.menu.redstone_remote.RedstoneRemoteEditMenu
import dev.aaronhowser.mods.irregular_implements.menu.redstone_remote.RedstoneRemoteEditScreen
import dev.aaronhowser.mods.irregular_implements.menu.redstone_remote.RedstoneRemoteUseMenu
import dev.aaronhowser.mods.irregular_implements.menu.redstone_remote.RedstoneRemoteUseScreen
import dev.aaronhowser.mods.irregular_implements.menu.void_stone.VoidStoneMenu
import dev.aaronhowser.mods.irregular_implements.menu.void_stone.VoidStoneScreen
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModMenuTypes {

	val MENU_TYPE_REGISTRY: DeferredRegister<MenuType<*>> =
		DeferredRegister.create(BuiltInRegistries.MENU, IrregularImplements.ID)

	val IRON_DROPPER: DeferredHolder<MenuType<*>, MenuType<IronDropperMenu>> =
		register("iron_dropper", ::IronDropperMenu)
	val BLOCK_DESTABILIZER: DeferredHolder<MenuType<*>, MenuType<BlockDestabilizerMenu>> =
		register("block_destabilizer", ::BlockDestabilizerMenu)
	val CHAT_DETECTOR: DeferredHolder<MenuType<*>, MenuType<ChatDetectorMenu>> =
		register("chat_detector", ::ChatDetectorMenu)
	val GLOBAL_CHAT_DETECTOR: DeferredHolder<MenuType<*>, MenuType<GlobalChatDetectorMenu>> =
		register("global_chat_detector", ::GlobalChatDetectorMenu)
	val IGNITER: DeferredHolder<MenuType<*>, MenuType<IgniterMenu>> =
		register("igniter", ::IgniterMenu)
	val VOID_STONE: DeferredHolder<MenuType<*>, MenuType<VoidStoneMenu>> =
		register("void_stone", ::VoidStoneMenu)
	val ONLINE_DETECTOR: DeferredHolder<MenuType<*>, MenuType<OnlineDetectorMenu>> =
		register("online_detector", ::OnlineDetectorMenu)
	val NOTIFICATION_INTERFACE: DeferredHolder<MenuType<*>, MenuType<NotificationInterfaceMenu>> =
		register("notification_interface", ::NotificationInterfaceMenu)
	val IMBUING_STATION: DeferredHolder<MenuType<*>, MenuType<ImbuingStationMenu>> =
		register("imbuing_station", ::ImbuingStationMenu)
	val ITEM_FILTER: DeferredHolder<MenuType<*>, MenuType<ItemFilterMenu>> =
		register("item_filter", ::ItemFilterMenu)
	val FILTERED_PLATFORM: DeferredHolder<MenuType<*>, MenuType<FilteredPlatformMenu>> =
		register("filtered_platform", ::FilteredPlatformMenu)
	val DROP_FILTER: DeferredHolder<MenuType<*>, MenuType<DropFilterMenu>> =
		register("drop_filter", ::DropFilterMenu)
	val INVENTORY_TESTER: DeferredHolder<MenuType<*>, MenuType<InventoryTesterMenu>> =
		register("inventory_tester", ::InventoryTesterMenu)
	val ADVANCED_ITEM_COLLECTOR: DeferredHolder<MenuType<*>, MenuType<AdvancedItemCollectorMenu>> =
		register("advanced_item_collector", ::AdvancedItemCollectorMenu)
	val REDSTONE_REMOTE_EDIT: DeferredHolder<MenuType<*>, MenuType<RedstoneRemoteEditMenu>> =
		register("redstone_remote_edit", ::RedstoneRemoteEditMenu)
	val REDSTONE_REMOTE_USE: DeferredHolder<MenuType<*>, MenuType<RedstoneRemoteUseMenu>> =
		register("redstone_remote_use", ::RedstoneRemoteUseMenu)
	val ENDER_ENERGY_DISTRIBUTOR: DeferredHolder<MenuType<*>, MenuType<EnderEnergyDistributorMenu>> =
		register("ender_energy_distributor", ::EnderEnergyDistributorMenu)

	private fun <T : AbstractContainerMenu> register(name: String, constructor: MenuType.MenuSupplier<T>): DeferredHolder<MenuType<*>, MenuType<T>> {
		return MENU_TYPE_REGISTRY.register(name, Supplier { MenuType(constructor, FeatureFlags.DEFAULT_FLAGS) })
	}

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
		event.register(REDSTONE_REMOTE_EDIT.get(), ::RedstoneRemoteEditScreen)
		event.register(REDSTONE_REMOTE_USE.get(), ::RedstoneRemoteUseScreen)
		event.register(ENDER_ENERGY_DISTRIBUTOR.get(), ::EnderEnergyDistributorScreen)
	}

}