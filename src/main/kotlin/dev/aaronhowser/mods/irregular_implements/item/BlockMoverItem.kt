package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModEntityTypeTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.component.BlockDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.ExperienceOrb
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.level.BlockEvent

class BlockMoverItem(properties: Properties) : Item(properties) {

	// TODO: Cooldown based on how the mining time of the block?
	override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
		val player = context.player
		if (stack.isEmpty
			|| player == null
			|| player.isSecondaryUseActive
		) return InteractionResult.PASS

		val blockDataComponent = stack.get(ModDataComponents.BLOCK_DATA)

		val result = if (blockDataComponent == null) tryPickUpBlock(player, stack, context) else tryPlaceBlock(player, stack, context)

		// Damage the item when placing a block
		if (blockDataComponent != null
			&& result.indicateItemUse()
			&& !player.hasInfiniteMaterials()
		) {
			stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack))
		}

		return result
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val blockDataComponent = stack.get(ModDataComponents.BLOCK_DATA) ?: return

		var blockName = blockDataComponent.blockState.block.name
		if (blockName.style.isEmpty) blockName = blockName.withStyle(ChatFormatting.GRAY)

		val component = if (blockDataComponent.blockEntityNbt == null) {
			blockName
		} else {
			ModTooltipLang.WITH_BLOCK_ENTITY
				.toGrayComponent(blockName)
		}

		tooltipComponents.add(component)
	}

	companion object {

		val DEFAULT_PROPERTIES: Properties = Properties().durability(99)

		private var blockMoverPreventingContainerDrops = false

		fun handleEntityJoinLevel(event: EntityJoinLevelEvent) {
			val entity = event.entity

			if (blockMoverPreventingContainerDrops && entity.type.`is`(ModEntityTypeTagsProvider.NOT_DROPPED_WHEN_MOVING_BLOCKS)) {
				entity.discard()
				event.isCanceled = true
			}
		}

		fun tryPickUpBlock(player: Player, stack: ItemStack, context: UseOnContext): InteractionResult {
			val level = context.level
			val clickedPos = context.clickedPos
			val clickedState = level.getBlockState(clickedPos)

			if (clickedState.isAir
				|| clickedState.getDestroySpeed(level, clickedPos) == -1f
				|| clickedState.`is`(ModBlockTagsProvider.BLOCK_MOVER_BLACKLIST)
				|| !level.mayInteract(player, clickedPos)
				|| !player.mayUseItemAt(clickedPos, context.clickedFace, stack)
				|| NeoForge.EVENT_BUS.post(BlockEvent.BreakEvent(level, clickedPos, clickedState, player)).isCanceled
			) return InteractionResult.FAIL

			val blockEntity = level.getBlockEntity(clickedPos)

			if (level.isClientSide) return InteractionResult.PASS

			val blockDataComponent = BlockDataComponent(level.registryAccess(), clickedState, blockEntity)
			stack.set(ModDataComponents.BLOCK_DATA, blockDataComponent)

			this.blockMoverPreventingContainerDrops = true
			level.setBlockAndUpdate(clickedPos, Blocks.AIR.defaultBlockState())
			this.blockMoverPreventingContainerDrops = false

			val soundType = clickedState.getSoundType(level, clickedPos, player)
			level.playSound(
				null,
				clickedPos,
				soundType.breakSound,
				SoundSource.BLOCKS,
				(soundType.volume + 1.0f) / 2.0f,
				soundType.pitch * 0.8f
			)

			return InteractionResult.SUCCESS
		}

		fun tryPlaceBlock(player: Player, stack: ItemStack, context: UseOnContext): InteractionResult {
			val level = context.level as? ServerLevel ?: return InteractionResult.FAIL
			val clickedPos = context.clickedPos
			val clickedState = level.getBlockState(clickedPos)

			val blockDataComponent = stack.get(ModDataComponents.BLOCK_DATA) ?: return InteractionResult.FAIL

			val posToPlaceBlock = if (clickedState.canBeReplaced()) clickedPos else clickedPos.relative(context.clickedFace)

			if (!level.mayInteract(player, clickedPos)
				|| !player.mayUseItemAt(clickedPos, context.clickedFace, stack)
				|| !blockDataComponent.tryPlace(level, posToPlaceBlock, player)
			) return InteractionResult.FAIL

			stack.remove(ModDataComponents.BLOCK_DATA)

			return InteractionResult.SUCCESS
		}
	}

}