package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.FluidTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BucketPickup
import net.minecraft.world.level.block.LiquidBlockContainer
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.common.SoundActions
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.fluids.SimpleFluidContent
import net.neoforged.neoforge.fluids.capability.IFluidHandler

class EnderBucketItem : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {

        fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
            if (tintIndex != 1) return 0xFFFFFFFF.toInt()

            val fluid = stack.get(ModDataComponents.SIMPLE_FLUID_CONTENT)?.fluid ?: return 0xFFFFFFFF.toInt()

            return 0xFFFFFFFF.toInt()
        }

        private fun tryFill(
            level: Level,
            player: Player,
            usedStack: ItemStack,
            blockPos: BlockPos
        ): InteractionResultHolder<ItemStack> {

            val blockState = level.getBlockState(blockPos)
            val block = blockState.block

            if (block !is BucketPickup) return InteractionResultHolder.fail(usedStack)

            val sourcePos = getNearestSource(level, blockPos, blockState.fluidState.fluidType)
                ?: return InteractionResultHolder.fail(usedStack)

            val sourceState = level.getBlockState(sourcePos)
            val sourceBlock = sourceState.block as? BucketPickup
                ?: return InteractionResultHolder.fail(usedStack)

            val pickup = sourceBlock.pickupBlock(player, level, sourcePos, sourceState)
            val pickupFluidStack = pickup.getCapability(Capabilities.FluidHandler.ITEM)
            val fluidStack = pickupFluidStack?.drain(Int.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE)

            if (pickup.isEmpty || fluidStack == null) return InteractionResultHolder.fail(usedStack)

            sourceBlock.getPickupSound(sourceState).ifPresent {
                player.playSound(it)
            }

            level.gameEvent(player, GameEvent.FLUID_PICKUP, sourcePos)

            if (!player.hasInfiniteMaterials()) {
                usedStack.set(ModDataComponents.SIMPLE_FLUID_CONTENT, SimpleFluidContent.copyOf(fluidStack))
            }

            return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide)
        }

        private fun getNearestSource(
            level: Level,
            blockPos: BlockPos,
            fluidType: FluidType
        ): BlockPos? {

            val positionsToCheck: MutableList<BlockPos> = mutableListOf()
            val checkedPositions: MutableList<BlockPos> = mutableListOf()

            positionsToCheck.add(blockPos)

            while (positionsToCheck.isNotEmpty() && checkedPositions.size < 2000) {
                val currentPos = positionsToCheck.removeAt(0)
                checkedPositions.add(currentPos)

                if (!level.isLoaded(currentPos)) continue

                val checkedFluidState = level.getFluidState(currentPos)
                if (checkedFluidState.isSource && checkedFluidState.fluidType == fluidType) return currentPos

                for (direction in Direction.entries) {
                    val nextPos = currentPos.relative(direction)
                    if (!checkedPositions.contains(nextPos)) positionsToCheck.add(nextPos)
                }
            }

            return null
        }

        private fun tryEmpty(
            level: Level,
            player: Player,
            usedStack: ItemStack,
            clickedPos: BlockPos,
            relativePos: BlockPos,
            blockHitResult: BlockHitResult
        ): InteractionResultHolder<ItemStack> {

            val currentContents = usedStack.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY
            val contentFluid = currentContents.fluid

            val clickedState = level.getBlockState(clickedPos)

            val clickedStateCanContainFluid = (clickedState.block as? LiquidBlockContainer)
                ?.canPlaceLiquid(player, level, clickedPos, clickedState, contentFluid)
                .isTrue

            val posToPlace = if (clickedStateCanContainFluid) clickedPos else relativePos

            if (!attemptPlace(player, level, posToPlace, blockHitResult, usedStack)) return InteractionResultHolder.fail(usedStack)

            usedStack.remove(ModDataComponents.SIMPLE_FLUID_CONTENT)

            return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide)
        }

        //TL;DR BucketItem#emptyContents but kotlin
        private fun attemptPlace(
            player: Player,
            level: Level,
            blockPos: BlockPos,
            blockHitResult: BlockHitResult?,
            container: ItemStack
        ): Boolean {
            val fluidContent = container.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY
            val fluid = fluidContent.fluid

            if (fluid !is FlowingFluid) return false

            val blockState = level.getBlockState(blockPos)
            val block = blockState.block
            val canBeReplaced = blockState.canBeReplaced(fluid)

            val isPlaceable = if (!blockState.isAir && !canBeReplaced) {
                (block as? LiquidBlockContainer)?.canPlaceLiquid(player, level, blockPos, blockState, fluid).isTrue
            } else true

            if (!isPlaceable) {
                return if (blockHitResult != null) {
                    attemptPlace(player, level, blockPos.relative(blockHitResult.direction), null, container)
                } else {
                    false
                }
            }

            val fluidStack = fluidContent.copy()
            val fluidType = fluid.fluidType

            if (fluidType.isVaporizedOnPlacement(level, blockPos, fluidStack)) {
                fluidType.isVaporizedOnPlacement(level, blockPos, fluidStack)
                return true
            }

            if (block is LiquidBlockContainer && block.canPlaceLiquid(player, level, blockPos, blockState, fluid)) {
                block.placeLiquid(level, blockPos, blockState, fluid.getSource(false))
                playSound(fluid, player, level, blockPos)

                return true
            }

            if (!level.isClientSide && canBeReplaced && !blockState.liquid()) level.destroyBlock(blockPos, true)

            return if (!level.setBlock(blockPos, fluid.defaultFluidState().createLegacyBlock(), 11) && !blockState.fluidState.isSource) {
                false
            } else {
                playSound(fluid, player, level, blockPos)
                true
            }

        }

        private fun playSound(fluid: Fluid, player: Player, level: Level, blockPos: BlockPos) {
            val soundEvent = fluid.fluidType.getSound(player, level, blockPos, SoundActions.BUCKET_EMPTY)
                ?: if (fluid.`is`(FluidTags.LAVA)) SoundEvents.BUCKET_EMPTY_LAVA else SoundEvents.BUCKET_EMPTY

            level.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS)
            level.gameEvent(player, GameEvent.FLUID_PLACE, blockPos)
        }

    }

    override fun use(
        level: Level,
        player: Player,
        usedHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)

        val currentContents = usedStack.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY

        val blockHitResult = getPlayerPOVHitResult(
            level,
            player,
            if (currentContents.isEmpty) ClipContext.Fluid.ANY else ClipContext.Fluid.NONE
        )

        if (blockHitResult.type != HitResult.Type.BLOCK) return InteractionResultHolder.pass(usedStack)

        val clickedPos = blockHitResult.blockPos
        val clickedFace = blockHitResult.direction

        val blockPos = clickedPos.relative(clickedFace)

        if (!level.mayInteract(player, clickedPos) || !player.mayUseItemAt(blockPos, clickedFace, usedStack)) return InteractionResultHolder.fail(usedStack)

        return if (currentContents.isEmpty) {
            tryFill(level, player, usedStack, clickedPos)
        } else {
            tryEmpty(level, player, usedStack, clickedPos, blockPos, blockHitResult)
        }
    }
}