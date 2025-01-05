package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.savedata.SpectreCoilSavedData.Companion.spectreCoilSavedData
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
import java.awt.Color
import java.util.function.Supplier

class SpectreChargerItem private constructor(
    private val type: Type
) : Item(Properties().stacksTo(1)) {

    companion object {
        val BASIC = SpectreChargerItem(Type.BASIC)
        val REDSTONE = SpectreChargerItem(Type.REDSTONE)
        val ENDER = SpectreChargerItem(Type.ENDER)
        val GENESIS = SpectreChargerItem(Type.GENESIS)

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

    override fun inventoryTick(stack: ItemStack, level: Level, player: Entity, slotId: Int, isSelected: Boolean) {
        if (level !is ServerLevel
            || player !is Player
            || level.gameTime % CHARGE_DELAY != 0L
            || !stack.has(ModDataComponents.IS_ENABLED)
        ) return

        val amountToCharge = this.type.amountGetter.get() * CHARGE_DELAY

        val coil = level.spectreCoilSavedData.getCoil(player.uuid)

        for (inventoryStack in player.inventory.items) {
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

        if (!level.isClientSide) {
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

        val component = ModLanguageProvider.Tooltips.CHARGER_CHARGES
            .toGrayComponent(String.format("%,d", amount))

        tooltipComponents.add(component)
    }

    enum class Type(
        val color: Int,
        val amountGetter: Supplier<Int>
    ) {
        BASIC(
            color = Color.CYAN.rgb,
            amountGetter = { 1024 }
        ),
        REDSTONE(
            color = Color.RED.rgb,
            amountGetter = { 4096 }
        ),
        ENDER(
            color = Color(200, 0, 210).rgb,
            amountGetter = { 20480 }
        ),
        GENESIS(
            color = Color.ORANGE.rgb,
            amountGetter = { Int.MAX_VALUE }
        )
    }

}