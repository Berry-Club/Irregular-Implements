package dev.aaronhowser.mods.irregular_implements.registry

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister

object ModRegistries {

	private val registries: List<DeferredRegister<*>> = listOf(
		ModDataComponents.DATA_COMPONENT_REGISTRY,
		ModItems.ITEM_REGISTRY,
		ModBlocks.BLOCK_REGISTRY,
		ModBlockEntityTypes.BLOCK_ENTITY_REGISTRY,
		ModCreativeModeTabs.TABS_REGISTRY,
		ModMobEffects.EFFECT_REGISTRY,
		ModEntityTypes.ENTITY_TYPE_REGISTRY,
		ModSounds.SOUND_EVENT_REGISTRY,
		ModArmorMaterials.ARMOR_MATERIAL_REGISTRY,
		ModRecipeSerializers.RECIPE_SERIALIZERS_REGISTRY,
		ModRecipeTypes.RECIPE_TYPES_REGISTRY,
		ModMenuTypes.MENU_TYPE_REGISTRY,
		ModAttachmentTypes.ATTACHMENT_TYPES_REGISTRY,
		ModLootPoolEntryTypes.LOOT_POOL_ENTRY_TYPE_REGISTRY,
		ModFeatures.FEATURE_REGISTRY,
		ModPlacementModifierTypes.PLACEMENT_MODIFIER_TYPE_REGISTRY,
		ModParticleTypes.PARTICLE_TYPE_REGISTRY
	)

	fun register(modBus: IEventBus) {
		registries.forEach { it.register(modBus) }
	}
}