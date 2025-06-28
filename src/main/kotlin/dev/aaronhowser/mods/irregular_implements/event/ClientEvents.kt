package dev.aaronhowser.mods.irregular_implements.event

import com.mojang.blaze3d.systems.RenderSystem
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.ModArmorItems
import dev.aaronhowser.mods.irregular_implements.item.SpectreAnchorItem
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.client.model.HumanoidModel
import net.minecraft.util.Mth
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RenderLivingEvent
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent

@EventBusSubscriber(
	modid = IrregularImplements.ID,
	value = [Dist.CLIENT]
)
object ClientEvents {

	@SubscribeEvent
	fun tooltipEvent(event: ItemTooltipEvent) {
		ModArmorItems.tooltip(event)
		SpectreAnchorItem.tooltip(event)
	}

	private var changedAlpha = false

	@SubscribeEvent
	fun beforeRenderLiving(event: RenderLivingEvent.Pre<LivingEntity, HumanoidModel<LivingEntity>>) {
		val entity = event.entity

		val wearingFullSpectreArmor = entity.getItemBySlot(EquipmentSlot.HEAD).`is`(ModItems.SPECTRE_HELMET.get())
				&& entity.getItemBySlot(EquipmentSlot.CHEST).`is`(ModItems.SPECTRE_CHESTPLATE.get())
				&& entity.getItemBySlot(EquipmentSlot.LEGS).`is`(ModItems.SPECTRE_LEGGINGS.get())
				&& entity.getItemBySlot(EquipmentSlot.FEET).`is`(ModItems.SPECTRE_BOOTS.get())

		if (wearingFullSpectreArmor) {
			val alpha = Mth.sin(entity.tickCount.toFloat() * 0.025f) * 0.1f + 0.5f

			RenderSystem.enableBlend()
			RenderSystem.defaultBlendFunc()
			RenderSystem.setShaderColor(1f, 1f, 1f, alpha)

			changedAlpha = true
		}
	}

	@SubscribeEvent
	fun afterRenderLiving(event: RenderLivingEvent.Post<LivingEntity, HumanoidModel<LivingEntity>>) {
		if (changedAlpha) {
			RenderSystem.disableBlend()
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f)

			changedAlpha = false
		}
	}

}