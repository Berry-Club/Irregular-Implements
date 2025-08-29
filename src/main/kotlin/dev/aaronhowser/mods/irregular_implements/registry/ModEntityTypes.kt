package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.entity.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModEntityTypes {

	val ENTITY_TYPE_REGISTRY: DeferredRegister<EntityType<*>> =
		DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, IrregularImplements.ID)

	val SPECTRE_ILLUMINATOR: DeferredHolder<EntityType<*>, EntityType<SpectreIlluminatorEntity>> =
		registerEntityType(
			"spectre_illuminator",
			MobCategory.MISC,
			1f, 1f,
			::SpectreIlluminatorEntity
		) {
			clientTrackingRange(256)
		}

	val ARTIFICIAL_END_PORTAL: DeferredHolder<EntityType<*>, EntityType<ArtificialEndPortalEntity>> =
		registerEntityType(
			"artificial_end_portal",
			MobCategory.MISC,
			3f, 0.25f,
			::ArtificialEndPortalEntity
		)

	val GOLDEN_EGG: DeferredHolder<EntityType<*>, EntityType<ThrownGoldenEggEntity>> =
		registerEntityType(
			"thrown_golden_egg",
			MobCategory.MISC,
			0.25f, 0.25f,
			::ThrownGoldenEggEntity
		) {
			clientTrackingRange(4)
			updateInterval(10)
		}

	val GOLDEN_CHICKEN: DeferredHolder<EntityType<*>, EntityType<GoldenChickenEntity>> =
		registerEntityType(
			"golden_chicken",
			MobCategory.CREATURE,
			0.4f, 0.7f,
			::GoldenChickenEntity
		) {
			eyeHeight(0.644f)
			passengerAttachments(Vec3(0.0, 0.7, -0.1))
			clientTrackingRange(10)
		}

	val WEATHER_EGG: DeferredHolder<EntityType<*>, EntityType<ThrownWeatherEggEntity>> =
		registerEntityType(
			"thrown_weather_egg",
			MobCategory.MISC,
			0.25f, 0.25f,
			::ThrownWeatherEggEntity
		) {
			clientTrackingRange(4)
			updateInterval(10)
		}

	val WEATHER_CLOUD: DeferredHolder<EntityType<*>, EntityType<WeatherCloudEntity>> =
		registerEntityType(
			"weather_cloud",
			MobCategory.MISC,
			1f, 1f,
			::WeatherCloudEntity
		) {
			clientTrackingRange(256)
		}

	val PORTKEY_ITEM: DeferredHolder<EntityType<*>, EntityType<PortkeyItemEntity>> =
		registerEntityType(
			"portkey_item",
			MobCategory.MISC,
			0.25f, 0.25f,
			::PortkeyItemEntity
		) {
			eyeHeight(0.2125f)
			clientTrackingRange(6)
			updateInterval(20)
		}

	val TEMPORARY_FLOO_FIREPLACE: DeferredHolder<EntityType<*>, EntityType<TemporaryFlooFireplaceEntity>> =
		registerEntityType(
			"temporary_floo_fireplace",
			MobCategory.MISC,
			2f, 2f,
			::TemporaryFlooFireplaceEntity
		) {
			setTrackingRange(80)
			updateInterval(20)
			setShouldReceiveVelocityUpdates(false)
		}

	val SPIRIT =
		registerEntityType(
			"spirit",
			MobCategory.MISC,
			0.25f, 0.25f,
			::SpiritEntity
		)

	fun <T : Entity> registerEntityType(
		name: String,
		category: MobCategory,
		width: Float, height: Float,
		entityFactory: EntityType.EntityFactory<T>,
		builderModifier: EntityType.Builder<T>.() -> Unit = {}
	): DeferredHolder<EntityType<*>, EntityType<T>> {
		return ENTITY_TYPE_REGISTRY.register(name, Supplier {
			EntityType.Builder.of(entityFactory, category)
				.sized(width, height)
				.apply(builderModifier)
				.build(name)
		})
	}


	//TODO : Spirit entity

}