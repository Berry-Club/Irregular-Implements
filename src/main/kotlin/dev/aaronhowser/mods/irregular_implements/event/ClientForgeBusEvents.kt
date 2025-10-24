package dev.aaronhowser.mods.irregular_implements.event

import com.mojang.blaze3d.systems.RenderSystem
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.client.render.*
import dev.aaronhowser.mods.irregular_implements.client.render.bewlr.CustomCraftingTableBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.bewlr.DiaphanousBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.bewlr.SpecialChestBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.bewlr.SpectreIlluminatorBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.block_entity.*
import dev.aaronhowser.mods.irregular_implements.client.render.entity.*
import dev.aaronhowser.mods.irregular_implements.item.*
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.ApplyLuminousPowderRecipe
import dev.aaronhowser.mods.irregular_implements.registry.*
import net.minecraft.client.model.HumanoidModel
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RenderLevelStageEvent
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus

@Mod.EventBusSubscriber(
	modid = IrregularImplements.ID,
	bus = Bus.FORGE,
	value = [Dist.CLIENT]
)
object ClientForgeBusEvents {

	@SubscribeEvent
	fun registerClientExtensions(event: RegisterClientExtensionsEvent) {
		event.registerItem(
			DiaphanousBEWLR.ClientItemExtensions,
			ModItems.DIAPHANOUS_BLOCK.get()
		)

		event.registerItem(
			CustomCraftingTableBEWLR.ClientItemExtensions,
			ModItems.CUSTOM_CRAFTING_TABLE.get()
		)

		event.registerItem(
			SpectreIlluminatorBEWLR.ClientItemExtensions,
			ModItems.SPECTRE_ILLUMINATOR.get()
		)

		event.registerItem(
			SpecialChestBEWLR.ClientItemExtensions,
			ModBlocks.NATURE_CHEST.get().asItem(),
			ModBlocks.WATER_CHEST.get().asItem()
		)
	}

	@SubscribeEvent
	fun onRegisterMenuScreens(event: RegisterMenuScreensEvent) {
		ModMenuTypes.registerScreens(event)
	}

	@SubscribeEvent
	fun tooltipEvent(event: ItemTooltipEvent) {
		ModArmorItems.lubricatedTooltip(event)
		SpectreAnchorItem.tooltip(event)
		ApplyLuminousPowderRecipe.tooltip(event)
	}

	private var isAlphaChanged = false

	@SubscribeEvent
	fun beforeRenderLiving(event: RenderLivingEvent.Pre<LivingEntity, HumanoidModel<LivingEntity>>) {
		val entity = event.entity

		if (ModArmorItems.isWearingFullSpectreArmor(entity)) {
			val alpha = Mth.sin(entity.tickCount.toFloat() * 0.025f) * 0.1f + 0.5f

			RenderSystem.enableBlend()
			RenderSystem.defaultBlendFunc()
			RenderSystem.setShaderColor(1f, 1f, 1f, alpha)

			isAlphaChanged = true
		}
	}

	@SubscribeEvent
	fun afterRenderLiving(event: RenderLivingEvent.Post<LivingEntity, HumanoidModel<LivingEntity>>) {
		if (isAlphaChanged) {
			RenderSystem.disableBlend()
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f)

			isAlphaChanged = false
		}
	}

	@SubscribeEvent
	fun afterClientTick(event: TickEvent.ClientTickEvent) {
		if (event.phase != TickEvent.Phase.END) return

		CubeIndicatorRenderer.afterClientTick(event)
		LineIndicatorRenderer.afterClientTick(event)
	}

	@SubscribeEvent
	fun onRenderLevel(event: RenderLevelStageEvent) {
		CubeIndicatorRenderer.onRenderLevel(event)
		LineIndicatorRenderer.onRenderLevel(event)
	}

}