package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.CompressedSlimeBlock
import dev.aaronhowser.mods.irregular_implements.block.ContactButtonBlock
import dev.aaronhowser.mods.irregular_implements.block.ContactLeverBlock
import dev.aaronhowser.mods.irregular_implements.block.SpectreTreeBlocks
import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.GlobalChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import dev.aaronhowser.mods.irregular_implements.item.*
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.savedata.RedstoneHandlerSavedData
import dev.aaronhowser.mods.irregular_implements.savedata.WorldInformationSavedData.Companion.worldInformationSavedData
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
import net.neoforged.neoforge.event.AnvilUpdateEvent
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import net.neoforged.neoforge.event.level.BlockEvent
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent
import net.neoforged.neoforge.event.tick.LevelTickEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID
)
object OtherEvents {

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
        val player = event.player
        val heldItem = player.getItemInHand(InteractionHand.MAIN_HAND)

        if (heldItem.`is`(ModItems.ITEM_FILTER)) {
            ItemFilterItem.setTestingFilter(heldItem)
            ItemFilterItem.testFilter(heldItem)
        }

        if (heldItem.`is`(ModItems.DROP_FILTER) || heldItem.`is`(ModItems.VOIDING_DROP_FILTER)) {
            DropFilterItem.setFilter(heldItem)
        }

        ChatDetectorBlockEntity.processMessage(event)
        GlobalChatDetectorBlockEntity.processMessage(event)
    }

    @SubscribeEvent
    fun onServerAboutToStart(event: ServerAboutToStartEvent) {
        VillageAdditions.addNewVillageBuildings(event)
    }

}