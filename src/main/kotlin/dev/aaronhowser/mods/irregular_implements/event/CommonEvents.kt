package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.CompressedSlimeBlock
import dev.aaronhowser.mods.irregular_implements.block.ContactButtonBlock
import dev.aaronhowser.mods.irregular_implements.block.ContactLeverBlock
import dev.aaronhowser.mods.irregular_implements.block.SpectreTreeBlocks
import dev.aaronhowser.mods.irregular_implements.block.block_entity.*
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import dev.aaronhowser.mods.irregular_implements.entity.GoldenChickenEntity
import dev.aaronhowser.mods.irregular_implements.handler.WorldInformationSavedData.Companion.worldInformationSavedData
import dev.aaronhowser.mods.irregular_implements.handler.redstone_signal.RedstoneHandlerSavedData
import dev.aaronhowser.mods.irregular_implements.item.*
import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.EscapeRopeHandler
import dev.aaronhowser.mods.irregular_implements.util.ServerScheduler
import dev.aaronhowser.mods.irregular_implements.world.village.VillageAdditions
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.event.AnvilUpdateEvent
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.entity.living.*
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import net.neoforged.neoforge.event.level.BlockEvent
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent
import net.neoforged.neoforge.event.tick.LevelTickEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent

@EventBusSubscriber(
	modid = IrregularImplements.ID
)
object CommonEvents {

	@SubscribeEvent
	fun afterServerTick(event: ServerTickEvent.Post) {
		ServerScheduler.tick()
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
		if (!event.isCanceled && event.entity.type == EntityType.ENDER_DRAGON) {
			val level = event.entity.level() as? ServerLevel ?: return
			level.worldInformationSavedData.enderDragonKilled = true
		}
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
		PortkeyItem.pickUpPortkey(event)
	}

	@SubscribeEvent
	fun onAnvilUpdate(event: AnvilUpdateEvent) {
		val left = event.left
		val right = event.right

		if (left.`is`(ModItems.OBSIDIAN_SKULL) && right.`is`(Items.FIRE_CHARGE)) {
			event.cost = 10
			event.materialCost = 1
			event.output = ModItems.OBSIDIAN_SKULL_RING.toStack()
		}

		if (left.`is`(ModItems.WATER_WALKING_BOOTS) && (right.`is`(ModItems.OBSIDIAN_SKULL) || right.`is`(ModItems.OBSIDIAN_SKULL_RING))) {
			event.cost = 10
			event.materialCost = 1
			event.output = ModItems.OBSIDIAN_WATER_WALKING_BOOTS.toStack()
		}

		if (left.`is`(ModItems.OBSIDIAN_WATER_WALKING_BOOTS) && right.`is`(ModItems.LAVA_CHARM)) {
			event.cost = 15
			event.materialCost = 1
			event.output = ModItems.LAVA_WADERS.toStack()
		}
	}

	@SubscribeEvent
	fun onServerChat(event: ServerChatEvent) {
		FlooBrickBlockEntity.processMessage(event)
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
			ModBlockEntities.SPECTRE_ENERGY_INJECTOR.get(),
			SpectreEnergyInjectorBlockEntity::getCapability
		)

		event.registerBlockEntity(
			Capabilities.EnergyStorage.BLOCK,
			ModBlockEntities.SPECTRE_COIL.get(),
			SpectreCoilBlockEntity::getCapability
		)

		event.registerBlockEntity(
			Capabilities.ItemHandler.BLOCK,
			ModBlockEntities.PLAYER_INTERFACE.get(),
			PlayerInterfaceBlockEntity::getCapability
		)

		event.registerItem(
			Capabilities.ItemHandler.ITEM,
			RedstoneRemoteDataComponent::getCapability,
			ModItems.REDSTONE_REMOTE.get()
		)
	}

	@SubscribeEvent
	fun entityAttributeEvent(event: EntityAttributeCreationEvent) {
		event.put(ModEntityTypes.GOLDEN_CHICKEN.get(), GoldenChickenEntity.createAttributes())
	}


}