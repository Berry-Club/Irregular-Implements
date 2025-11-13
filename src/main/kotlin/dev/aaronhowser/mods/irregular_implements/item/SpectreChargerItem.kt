package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.aaron.AaronExtensions.isServerSide
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.handler.SpectreCoilHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Unit
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.neoforged.neoforge.capabilities.Capabilities
import top.theillusivec4.curios.api.CuriosApi
import java.awt.Color
import java.util.function.Supplier

class SpectreChargerItem(
	private val type: Type,
	properties: Properties
) : Item(properties) {

	override fun inventoryTick(stack: ItemStack, level: Level, player: Entity, slotId: Int, isSelected: Boolean) {
		if (level !is ServerLevel
			|| player !is Player
			|| level.gameTime % CHARGE_DELAY != 0L
			|| !stack.has(ModDataComponents.IS_ENABLED)
		) return

		val amountToCharge = this.type.amountGetter.get() * CHARGE_DELAY

		val coil = SpectreCoilHandler.get(level).getCoil(player.uuid)

		val stacks = player.inventory.compartments.flatten().toMutableList()

		CuriosApi.getCuriosInventory(player).ifPresent { curioHandler ->
			for (slot in 0 until curioHandler.equippedCurios.slots) {
				val stack = curioHandler.equippedCurios.getStackInSlot(slot)
				stacks.add(stack)
			}
		}

		for (inventoryStack in stacks) {
			val energyCapability = inventoryStack.getCapability(Capabilities.EnergyStorage.ITEM)
			if (energyCapability == null || !energyCapability.canReceive()) continue

			if (this.type == Type.GENESIS) {
				energyCapability.receiveEnergy(amountToCharge, false)
				continue
			}

			val available = coil.extractEnergy(amountToCharge, true)
			if (available <= 0) return

			val sent = energyCapability.receiveEnergy(available, false)
			coil.extractEnergy(sent, false)
		}
	}

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)

		if (level.isServerSide) {
			if (usedStack.has(ModDataComponents.IS_ENABLED)) {
				usedStack.remove(ModDataComponents.IS_ENABLED)
			} else {
				usedStack.set(ModDataComponents.IS_ENABLED, Unit.INSTANCE)
			}
		}

		return InteractionResultHolder.success(usedStack)
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val amount = this.type.amountGetter.get()

		val component = ModTooltipLang.CHARGER_CHARGES
			.toGrayComponent(String.format("%,d", amount))

		tooltipComponents.add(component)
	}

	enum class Type(
		val color: Int,
		val amountGetter: Supplier<Int>
	) {
		BASIC(
			color = Color.CYAN.rgb,
			amountGetter = { ServerConfig.CONFIG.spectreChargerBasic.get() }
		),
		REDSTONE(
			color = Color.RED.rgb,
			amountGetter = { ServerConfig.CONFIG.specterChargerRedstone.get() }
		),
		ENDER(
			color = Color(200, 0, 210).rgb,
			amountGetter = { ServerConfig.CONFIG.spectreChargerEnder.get() }
		),
		GENESIS(
			color = Color.ORANGE.rgb,
			amountGetter = { ServerConfig.CONFIG.spectreChargerGenesis.get() }
		)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		const val CHARGE_DELAY = 5

		val IS_ENABLED = OtherUtil.modResource("is_enabled")

		fun getEnabledForPredicate(
			stack: ItemStack,
			localLevel: ClientLevel?,
			holdingEntity: LivingEntity?,
			int: Int
		): Float {
			return if (stack.has(ModDataComponents.IS_ENABLED)) 1.0f else 0.0f
		}
	}

}