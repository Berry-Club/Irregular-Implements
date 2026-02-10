package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.aaron.entity.BetterFakePlayerFactory
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isItem
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.*
import dev.aaronhowser.mods.irregular_implements.block.block_entity.*
import dev.aaronhowser.mods.irregular_implements.command.ModCommands
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModDimensions
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import dev.aaronhowser.mods.irregular_implements.entity.GoldenChickenEntity
import dev.aaronhowser.mods.irregular_implements.entity.SpiritEntity
import dev.aaronhowser.mods.irregular_implements.entity.TemporaryFlooFireplaceEntity
import dev.aaronhowser.mods.irregular_implements.handler.EscapeRopeHandler
import dev.aaronhowser.mods.irregular_implements.handler.SpectreIlluminationHandler
import dev.aaronhowser.mods.irregular_implements.handler.redstone_signal.RedstoneHandlerSavedData
import dev.aaronhowser.mods.irregular_implements.handler.spectre_cube.SpectreCubeSavedData
import dev.aaronhowser.mods.irregular_implements.item.*
import dev.aaronhowser.mods.irregular_implements.item.component.EnderLetterContentsDataComponent
import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.world.village.VillageAdditions
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.event.AnvilUpdateEvent
import net.neoforged.neoforge.event.RegisterCommandsEvent
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.entity.living.*
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import net.neoforged.neoforge.event.level.BlockEvent
import net.neoforged.neoforge.event.level.ChunkWatchEvent
import net.neoforged.neoforge.event.level.LevelEvent
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent
import net.neoforged.neoforge.event.tick.LevelTickEvent
import net.neoforged.neoforge.event.tick.PlayerTickEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent

@EventBusSubscriber(
	modid = IrregularImplements.MOD_ID
)
object CommonEvents {

	@SubscribeEvent
	fun afterServerTick(event: ServerTickEvent.Post) {
		EscapeRopeHandler.tick()
	}

	@SubscribeEvent
	fun afterEntityDamaged(event: LivingDamageEvent.Post) {
		ImbueEffect.handleAttackImbues(event)
	}

	@SubscribeEvent
	fun onLivingIncomingDamage(event: LivingIncomingDamageEvent) {
		ImbueEffect.handleDamageImbue(event)
		ModArmorItems.tryBlockFireDamage(event)
		LavaCharmItem.tryBlockLavaDamage(event)
	}

	@SubscribeEvent
	fun entityXpDrop(event: LivingExperienceDropEvent) {
		ImbueEffect.handleXpImbue(event)
	}

	@SubscribeEvent
	fun onClickBlock(event: PlayerInteractEvent.RightClickBlock) {
		if (event.isCanceled) return

		val level = event.level
		if (level.isClientSide) return

		val pos = event.pos

		if (event.hand == InteractionHand.MAIN_HAND) {
			ContactLeverBlock.handleClickBlock(level, pos)
			ContactButtonBlock.handleClickBlock(level, pos)

			SpectreTreeBlocks.convertSaplings(event)
		}
	}

	@SubscribeEvent
	fun onBlockToolModification(event: BlockEvent.BlockToolModificationEvent) {
		CompressedSlimeBlock.modifySlimeBlock(event)
	}

	@SubscribeEvent
	fun onLevelTick(event: LevelTickEvent.Post) {
		RedstoneHandlerSavedData.tick(event.level)
	}

	@SubscribeEvent
	fun entityJoinLevel(event: EntityJoinLevelEvent) {
		BlockMoverItem.handleEntityJoinLevel(event)
	}

	@SubscribeEvent
	fun onLivingDeath(event: LivingDeathEvent) {
		WhiteStoneItem.tryPreventDeath(event)
	}

	@SubscribeEvent(
		priority = EventPriority.LOWEST
	)
	fun onLivingDeathLowPriority(event: LivingDeathEvent) {
		SpiritEntity.trySpawn(event)
	}

	@SubscribeEvent(
		priority = EventPriority.LOWEST     //In case something else cancels the event after it was saved
	)
	fun keepInventoryFailsafe(event: LivingDeathEvent) {
		val player = event.entity as? Player ?: return
		if (!event.isCanceled) SpectreAnchorItem.saveAnchoredItems(player)
	}

	@SubscribeEvent
	fun onPlayerRespawn(event: PlayerEvent.PlayerRespawnEvent) {
		SpectreAnchorItem.returnItems(event.entity)
	}

	@SubscribeEvent
	fun beforePickupItem(event: ItemEntityPickupEvent.Pre) {
		DropFilterItem.beforePickupItem(event)
	}

	@SubscribeEvent
	fun onAnvilUpdate(event: AnvilUpdateEvent) {
		val left = event.left
		val right = event.right

		if (left.isItem(ModItems.OBSIDIAN_SKULL) && right.isItem(Items.FIRE_CHARGE)) {
			event.cost = 10
			event.materialCost = 1
			event.output = ModItems.OBSIDIAN_SKULL_RING.toStack()
		}

		if (left.isItem(ModItems.WATER_WALKING_BOOTS) && (right.isItem(ModItems.OBSIDIAN_SKULL) || right.isItem(ModItems.OBSIDIAN_SKULL_RING))) {
			event.cost = 10
			event.materialCost = 1
			event.output = ModItems.OBSIDIAN_WATER_WALKING_BOOTS.toStack()
		}

		if (left.isItem(ModItems.OBSIDIAN_WATER_WALKING_BOOTS) && right.isItem(ModItems.LAVA_CHARM)) {
			event.cost = 15
			event.materialCost = 1
			event.output = ModItems.LAVA_WADERS.toStack()
		}
	}

	@SubscribeEvent
	fun onServerChat(event: ServerChatEvent) {
		FlooBrickBlockEntity.processMessage(event)
		TemporaryFlooFireplaceEntity.processMessage(event)
		ChatDetectorBlockEntity.processMessage(event)
		GlobalChatDetectorBlockEntity.processMessage(event)
	}

	@SubscribeEvent
	fun onServerAboutToStart(event: ServerAboutToStartEvent) {
		VillageAdditions.addNewVillageBuildings(event)
	}

	@SubscribeEvent
	fun onSpawnPlacementCheck(event: MobSpawnEvent.SpawnPlacementCheck) {
		PeaceCandleBlockEntity.onSpawnPlacementCheck(event)
	}


	@SubscribeEvent
	fun registerPayloads(event: RegisterPayloadHandlersEvent) {
		ModPacketHandler.registerPayloads(event)
	}

	@SubscribeEvent
	fun onRegisterCapabilities(event: RegisterCapabilitiesEvent) {
		event.registerBlockEntity(
			Capabilities.EnergyStorage.BLOCK,
			ModBlockEntityTypes.SPECTRE_ENERGY_INJECTOR.get(),
			SpectreEnergyInjectorBlockEntity::getEnergyCapability
		)

		event.registerBlockEntity(
			Capabilities.EnergyStorage.BLOCK,
			ModBlockEntityTypes.SPECTRE_COIL.get(),
			SpectreCoilBlockEntity::getEnergyCapability
		)

		event.registerBlockEntity(
			Capabilities.ItemHandler.BLOCK,
			ModBlockEntityTypes.PLAYER_INTERFACE.get(),
			PlayerInterfaceBlockEntity::getItemCapability
		)

		event.registerBlockEntity(
			Capabilities.ItemHandler.BLOCK,
			ModBlockEntityTypes.IMBUING_STATION.get(),
			ImbuingStationBlockEntity::getItemCapability
		)

		event.registerItem(
			Capabilities.ItemHandler.ITEM,
			RedstoneRemoteDataComponent::getItmeCapability,
			ModItems.REDSTONE_REMOTE.get()
		)

		event.registerBlockEntity(
			Capabilities.EnergyStorage.BLOCK,
			ModBlockEntityTypes.ENERGY_DISTRIBUTOR.get(),
			EnergyDistributorBlockEntity::getEnergyCapability
		)

		event.registerBlockEntity(
			Capabilities.EnergyStorage.BLOCK,
			ModBlockEntityTypes.ENDER_ENERGY_DISTRIBUTOR.get(),
			EnderEnergyDistributorBlockEntity::getEnergyCapability
		)

		event.registerItem(
			Capabilities.ItemHandler.ITEM,
			EnderLetterContentsDataComponent::getItemCapability,
			ModItems.ENDER_LETTER.get(),
		)

		event.registerBlock(
			Capabilities.FluidHandler.BLOCK,
			{ _, _, _, _, _ -> PitcherPlantBlock.INFINITE_WATER_HANDLER },
			ModBlocks.PITCHER_PLANT.get()
		)

		event.registerBlockEntity(
			Capabilities.ItemHandler.BLOCK,
			ModBlockEntityTypes.AUTO_PLACER.get(),
			AutoPlacerBlockEntity::getItemCapability,
		)

		event.registerBlockEntity(
			Capabilities.ItemHandler.BLOCK,
			ModBlockEntityTypes.ADVANCED_REDSTONE_INTERFACE.get(),
			RedstoneInterfaceAdvancedBlockEntity::getItemCapability,
		)

	}

	@SubscribeEvent
	fun entityAttributeEvent(event: EntityAttributeCreationEvent) {
		event.put(ModEntityTypes.GOLDEN_CHICKEN.get(), GoldenChickenEntity.createAttributes())
		event.put(ModEntityTypes.SPIRIT.get(), SpiritEntity.createAttributes())
	}

	@SubscribeEvent
	fun onRegisterCommandsEvent(event: RegisterCommandsEvent) {
		ModCommands.register(event.dispatcher, event.buildContext)
	}

	@SubscribeEvent
	fun beforePlayerTick(event: PlayerTickEvent.Pre) {
		val player = event.entity

		if (player is ServerPlayer && player.level().dimension() == ModDimensions.SPECTRE_LEVEL_KEY) {
			val handler = SpectreCubeSavedData.get(player.serverLevel())
			handler.verifyPosition(player)
		}
	}

	@SubscribeEvent
	fun onLevelUnload(event: LevelEvent.Unload) {
		val level = event.level
		if (level is ServerLevel) {
			BetterFakePlayerFactory.unloadLevel(level)
		}
	}

	@SubscribeEvent
	fun onChunkWatch(event: ChunkWatchEvent.Watch) {
		val player = event.player
		val chunkPos = event.pos

		SpectreIlluminationHandler.watchChunk(player, chunkPos)
	}

	@SubscribeEvent
	fun onChunkUnwatch(event: ChunkWatchEvent.UnWatch) {
		val player = event.player
		val chunkPos = event.pos

		SpectreIlluminationHandler.stopWatchingChunk(player, chunkPos)
	}

}