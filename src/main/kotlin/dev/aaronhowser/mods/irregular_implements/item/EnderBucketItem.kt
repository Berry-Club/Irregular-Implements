package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isServerSide
import dev.aaronhowser.mods.aaron.isTrue
import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.FluidTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemUtils
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BucketPickup
import net.minecraft.world.level.block.LiquidBlockContainer
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.common.SoundActions
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.fluids.SimpleFluidContent
import net.neoforged.neoforge.fluids.capability.IFluidHandler

class EnderBucketItem(properties: Properties) : Item(properties) {

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

	override fun getMaxStackSize(stack: ItemStack): Int {
		return if (stack.has(ModDataComponents.SIMPLE_FLUID_CONTENT)) 1 else 16
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val fluidContent = stack.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY
		if (fluidContent.isEmpty) return

		val fluidStack = fluidContent.copy()
		val fluidType = fluidStack.fluidType
		val fluidName = fluidType.getDescription(fluidStack)

		tooltipComponents.add(fluidName)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(16)

		val HAS_FLUID: ResourceLocation = OtherUtil.modResource("has_fluid")
		fun getHasFluidPredicate(
			stack: ItemStack,
			localLevel: ClientLevel?,
			holdingEntity: LivingEntity?,
			int: Int
		): Float {
			val fluidContent = stack.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY
			return if (fluidContent.isEmpty) 0f else 1f
		}

		fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
			if (tintIndex != 1) return 0xFFFFFFFF.toInt()

			val fluidContent = stack.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY
			if (fluidContent.isEmpty) return 0xFFFFFFFF.toInt()
			val fluidStack = fluidContent.copy()

			return RenderUtil.getColorFromFluid(fluidStack)
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

			val newStack = usedStack.copyWithCount(1)
			newStack.set(ModDataComponents.SIMPLE_FLUID_CONTENT, SimpleFluidContent.copyOf(fluidStack))

			val resultStack = ItemUtils.createFilledResult(usedStack, player, newStack)

			return InteractionResultHolder.sidedSuccess(resultStack, level.isClientSide)
		}

		fun getNearestSource(
			level: Level,
			blockPos: BlockPos,
			fluidType: FluidType
		): BlockPos? {
			val positionsToCheck: MutableList<BlockPos> = mutableListOf()
			val checkedPositions: MutableList<BlockPos> = mutableListOf()

			positionsToCheck.add(blockPos)

			while (positionsToCheck.isNotEmpty() && checkedPositions.size < 2_000) {
				val currentPos = positionsToCheck.removeAt(0)
				checkedPositions.add(currentPos)

				if (!level.isLoaded(currentPos)) continue

				val checkedFluidState = level.getFluidState(currentPos)
				if (checkedFluidState.isSource && checkedFluidState.fluidType == fluidType) return currentPos

				for (direction in Direction.entries) {
					val nextPos = currentPos.relative(direction)
					if (!checkedPositions.contains(nextPos) && !positionsToCheck.contains(nextPos)) {
						val fluidThere = level.getFluidState(nextPos)
						if (!fluidThere.isEmpty) {
							positionsToCheck.add(nextPos)
						}
					}
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

			val fluidStack = fluidContent.copy()
			val fluid = fluidStack.fluid

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

			val fluidType = fluid.fluidType

			if (fluidType.isVaporizedOnPlacement(level, blockPos, fluidStack)) {
				return true
			}

			if (block is LiquidBlockContainer && block.canPlaceLiquid(player, level, blockPos, blockState, fluid)) {
				block.placeLiquid(level, blockPos, blockState, fluid.getSource(false))
				playSound(fluidStack, player, level, blockPos)

				return true
			}

			if (level.isServerSide && canBeReplaced && !blockState.liquid()) level.destroyBlock(blockPos, true)

			return if (!level.setBlock(blockPos, fluid.defaultFluidState().createLegacyBlock(), 11) && !blockState.fluidState.isSource) {
				false
			} else {
				playSound(fluidStack, player, level, blockPos)
				true
			}
		}

		fun playSound(fluidStack: FluidStack, player: Player, level: Level, blockPos: BlockPos) {
			val soundEvent = fluidStack.fluidType.getSound(player, level, blockPos, SoundActions.BUCKET_EMPTY)
				?: if (fluidStack.`is`(FluidTags.LAVA)) SoundEvents.BUCKET_EMPTY_LAVA else SoundEvents.BUCKET_EMPTY

			level.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS)
			level.gameEvent(player, GameEvent.FLUID_PLACE, blockPos)
		}

	}

}