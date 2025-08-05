package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
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
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.SimpleFluidContent
import net.neoforged.neoforge.fluids.capability.IFluidHandler

class ReinforcedEnderBucketItem(properties: Properties) : Item(properties) {

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

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val fluidContent = stack.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY
		if (fluidContent.isEmpty) return

		val fluidStack = fluidContent.copy()
		val fluidType = fluidStack.fluidType
		val fluidName = fluidType.getDescription(fluidStack)

		val mb = fluidStack.amount

		tooltipComponents.add(fluidName.copy().append(" $mb mB"))
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
		const val MAX_FLUID_AMOUNT: Int = 16 * 1000

		private fun tryFill(
			level: Level,
			player: Player,
			usedStack: ItemStack,
			blockPos: BlockPos
		): InteractionResultHolder<ItemStack> {
			val currentContents = usedStack.get(ModDataComponents.SIMPLE_FLUID_CONTENT) ?: SimpleFluidContent.EMPTY

			if (currentContents.amount + 1000 > MAX_FLUID_AMOUNT) {
				return InteractionResultHolder.fail(usedStack)
			}

			val blockState = level.getBlockState(blockPos)
			val block = blockState.block

			if (block !is BucketPickup) return InteractionResultHolder.fail(usedStack)

			val sourcePos = EnderBucketItem.getNearestSource(level, blockPos, blockState.fluidState.fluidType)
				?: return InteractionResultHolder.fail(usedStack)

			val sourceState = level.getBlockState(sourcePos)
			val sourceBlock = sourceState.block as? BucketPickup
				?: return InteractionResultHolder.fail(usedStack)

			val pickup = sourceBlock.pickupBlock(player, level, sourcePos, sourceState)
			val pickupFluidStack = pickup.getCapability(Capabilities.FluidHandler.ITEM)
			val fluidStack = pickupFluidStack?.drain(Int.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE)

			if (pickup.isEmpty || fluidStack == null) return InteractionResultHolder.fail(usedStack)

			var newContents: FluidStack

			if (currentContents.isEmpty) {
				newContents = fluidStack.copy()
			} else {
				newContents = currentContents.copy()
				newContents.amount += fluidStack.amount
			}

			sourceBlock.getPickupSound(sourceState).ifPresent(player::playSound)

			level.gameEvent(player, GameEvent.FLUID_PICKUP, sourcePos)

			newContents.amount += fluidStack.amount
			usedStack.set(ModDataComponents.SIMPLE_FLUID_CONTENT, SimpleFluidContent.copyOf(newContents))

			return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide)
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

			val newContents = currentContents.copy()
			newContents.amount -= 1000

			usedStack.set(ModDataComponents.SIMPLE_FLUID_CONTENT, SimpleFluidContent.copyOf(newContents))

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
			if (fluidStack.amount < 1000) return false
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
				EnderBucketItem.playSound(fluidStack, player, level, blockPos)

				return true
			}

			if (!level.isClientSide && canBeReplaced && !blockState.liquid()) level.destroyBlock(blockPos, true)

			return if (!level.setBlock(blockPos, fluid.defaultFluidState().createLegacyBlock(), 11) && !blockState.fluidState.isSource) {
				false
			} else {
				EnderBucketItem.playSound(fluidStack, player, level, blockPos)
				true
			}
		}

	}

}