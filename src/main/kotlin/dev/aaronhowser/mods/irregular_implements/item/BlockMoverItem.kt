package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.component.BlockDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.ExperienceOrb
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.level.BlockEvent

class BlockMoverItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
        val player = context.player
        if (stack.isEmpty
            || player == null
            || player.isSecondaryUseActive
        ) return InteractionResult.PASS

        val blockDataComponent = stack.get(ModDataComponents.BLOCK_DATA)

        return if (blockDataComponent == null) tryPickUpBlock(player, stack, context) else tryPlaceBlock(player, stack, context)
    }

    //TODO: Plop sounds
    companion object {
        private var blockMoverPreventingContainerDrops = false

        fun handleEntityJoinLevel(event: EntityJoinLevelEvent) {
            val entity = event.entity

            if (blockMoverPreventingContainerDrops && entity is ItemEntity || entity is ExperienceOrb) {
                entity.discard()
                event.isCanceled = true
            }
        }

        fun tryPickUpBlock(player: Player, stack: ItemStack, context: UseOnContext): InteractionResult {
            val level = context.level
            val clickedPos = context.clickedPos
            val blockState = level.getBlockState(clickedPos)

            if (blockState.isAir
                || blockState.getDestroySpeed(level, clickedPos) == -1f
                || blockState.`is`(ModBlockTagsProvider.BLOCK_MOVER_BLACKLIST)
                || !level.mayInteract(player, clickedPos)
                || !player.mayUseItemAt(clickedPos, context.clickedFace, stack)
                || NeoForge.EVENT_BUS.post(BlockEvent.BreakEvent(level, clickedPos, blockState, player)).isCanceled
            ) return InteractionResult.FAIL

            val blockEntity = level.getBlockEntity(clickedPos)

            if (level.isClientSide) return InteractionResult.PASS

            val blockDataComponent = BlockDataComponent(level.registryAccess(), blockState, blockEntity)
            stack.set(ModDataComponents.BLOCK_DATA, blockDataComponent)

            this.blockMoverPreventingContainerDrops = true
            level.setBlockAndUpdate(clickedPos, Blocks.AIR.defaultBlockState())
            this.blockMoverPreventingContainerDrops = false

            return InteractionResult.SUCCESS
        }

        fun tryPlaceBlock(player: Player, stack: ItemStack, context: UseOnContext): InteractionResult {
            val level = context.level as? ServerLevel ?: return InteractionResult.FAIL
            val clickedPos = context.clickedPos
            val clickedState = level.getBlockState(clickedPos)

            val blockDataComponent = stack.get(ModDataComponents.BLOCK_DATA) ?: return InteractionResult.FAIL

            val posToPlaceBlock = if (clickedState.canBeReplaced()) clickedPos else clickedPos.relative(context.clickedFace)
            val stateAlreadyThere = level.getBlockState(posToPlaceBlock)
            val stateToPlace = blockDataComponent.blockState

            if (!stateAlreadyThere.canBeReplaced()
                || !level.mayInteract(player, clickedPos)
                || !player.mayUseItemAt(clickedPos, context.clickedFace, stack)
                || !stateToPlace.canSurvive(level, posToPlaceBlock)
                || level.getEntities(null, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(posToPlaceBlock))).isNotEmpty()
            ) return InteractionResult.FAIL

            level.captureBlockSnapshots = true
            level.setBlockAndUpdate(posToPlaceBlock, stateToPlace)
            level.captureBlockSnapshots = false

            val snapshots = level.capturedBlockSnapshots.toList()
            level.capturedBlockSnapshots.clear()

            val snapshot = snapshots.firstOrNull() ?: return InteractionResult.FAIL

            if (BlockEvent.EntityPlaceEvent(snapshot, clickedState, player).isCanceled) {
                level.restoringBlockSnapshots = true
                snapshot.restore(snapshot.flags or Block.UPDATE_CLIENTS)
                level.restoringBlockSnapshots = false
                return InteractionResult.FAIL
            }

            //TODO: Nbt

            stack.remove(ModDataComponents.BLOCK_DATA)

            return InteractionResult.SUCCESS
        }
    }

}