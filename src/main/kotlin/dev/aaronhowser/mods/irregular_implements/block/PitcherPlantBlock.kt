package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.BucketPickup
import net.minecraft.world.level.block.FlowerBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.BlockHitResult
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.capability.IFluidHandler
import java.util.*

//TODO: Advancement "Pitcherer Plant"
class PitcherPlantBlock : FlowerBlock(
	MobEffects.WATER_BREATHING,
	5f,
	Properties.ofFullCopy(Blocks.RED_TULIP)
), BucketPickup, BonemealableBlock {

	override fun isRandomlyTicking(state: BlockState): Boolean = true

	override fun randomTick(
		state: BlockState,
		level: ServerLevel,
		pos: BlockPos,
		random: RandomSource
	) {
		fillAdjacentTanks(level, pos, ServerConfig.CONFIG.pitcherPlantTickFillAmount.get())
	}

	override fun useItemOn(
		stack: ItemStack,
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hand: InteractionHand,
		hitResult: BlockHitResult
	): ItemInteractionResult {
		if (level.isClientSide) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

		val usedStack = player.getItemInHand(hand)
		val fluidCap = usedStack.getCapability(Capabilities.FluidHandler.ITEM) ?: return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

		val amountThatFits = fluidCap.fill(FluidStack(Fluids.WATER, ServerConfig.CONFIG.pitcherPlantUseFillAmount.get()), IFluidHandler.FluidAction.SIMULATE)
		fluidCap.fill(FluidStack(Fluids.WATER, amountThatFits), IFluidHandler.FluidAction.EXECUTE)

		return ItemInteractionResult.SUCCESS
	}

	@Suppress("OVERRIDE_DEPRECATION")
	override fun getPickupSound(): Optional<SoundEvent> {
		return Fluids.WATER.pickupSound
	}

	override fun pickupBlock(player: Player?, level: LevelAccessor, pos: BlockPos, state: BlockState): ItemStack {
		return Fluids.WATER.bucket.defaultInstance
	}

	override fun isValidBonemealTarget(level: LevelReader, pos: BlockPos, state: BlockState): Boolean {
		return level is Level && Direction.entries.any {
			val fluidCap = level.getCapability(
				Capabilities.FluidHandler.BLOCK,
				pos.relative(it),
				it.opposite
			) ?: return@any false

			val amountThatFits = fluidCap.fill(FluidStack(Fluids.WATER, 1), IFluidHandler.FluidAction.SIMULATE)

			return@any amountThatFits > 0
		}
	}

	override fun isBonemealSuccess(level: Level, random: RandomSource, pos: BlockPos, state: BlockState): Boolean {
		return true
	}

	override fun performBonemeal(level: ServerLevel, random: RandomSource, pos: BlockPos, state: BlockState) {
		fillAdjacentTanks(level, pos, ServerConfig.CONFIG.pitcherPlantBoneMealFillAmount.get())
	}

	companion object {
		fun fillAdjacentTanks(level: Level, pos: BlockPos, amount: Int) {
			for (direction in Direction.entries) {
				val offsetPos = pos.relative(direction)

				val fluidCap = level.getCapability(
					Capabilities.FluidHandler.BLOCK,
					offsetPos,
					direction.opposite
				) ?: continue

				val amountThatFits = fluidCap.fill(FluidStack(Fluids.WATER, amount), IFluidHandler.FluidAction.SIMULATE)
				fluidCap.fill(FluidStack(Fluids.WATER, amountThatFits), IFluidHandler.FluidAction.EXECUTE)
			}
		}

		val INFINITE_WATER_HANDLER = object : IFluidHandler {
			override fun getTanks(): Int = 1
			override fun getFluidInTank(tank: Int): FluidStack = FluidStack(Fluids.WATER, Int.MAX_VALUE)
			override fun getTankCapacity(tank: Int): Int = Int.MAX_VALUE
			override fun isFluidValid(tank: Int, stack: FluidStack): Boolean = stack.`is`(Fluids.WATER)
			override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction): Int = 0

			override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
				return if (resource.`is`(Fluids.WATER)) {
					val configMax = ServerConfig.CONFIG.pitcherPlantPipeDrainRate.get()
					FluidStack(Fluids.WATER, minOf(resource.amount, configMax))
				} else {
					FluidStack.EMPTY
				}
			}

			override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
				val configMax = ServerConfig.CONFIG.pitcherPlantPipeDrainRate.get()
				return FluidStack(Fluids.WATER, minOf(maxDrain, configMax))
			}
		}
	}

}