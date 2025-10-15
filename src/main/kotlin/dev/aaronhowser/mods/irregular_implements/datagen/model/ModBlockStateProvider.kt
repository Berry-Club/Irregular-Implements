package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.*
import dev.aaronhowser.mods.irregular_implements.block.plate.DirectionalAcceleratorPlateBlock
import dev.aaronhowser.mods.irregular_implements.block.plate.RedirectorPlateBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getDirectionName
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.DropperBlock
import net.minecraft.world.level.block.RedstoneTorchBlock
import net.minecraft.world.level.block.RedstoneWallTorchBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.neoforged.neoforge.client.model.generators.*
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.registries.DeferredBlock

class ModBlockStateProvider(
	output: PackOutput,
	private val existingFileHelper: ExistingFileHelper
) : BlockStateProvider(output, IrregularImplements.ID, existingFileHelper) {

	override fun registerStatesAndModels() {
		singleTextureBlocks()
		singleTextureTranslucentBlocks()
		crossBlocks()
		buildSingleFaces()
		rainbowLamp()
		triggerGlass()
		platforms()
		luminousBlocks()
		stainedBricks()
		coloredGrass()
		contactLever()
		contactButton()
		biomeBlocks()
		enderBridges()
		fertilizedDirt()
		compressedSlime()
		blockDestabilizer()
		igniter()
		lotus()
		beanSprout()
		beanStalk()
		basePlates()
		directionalAcceleratorPlate()
		redirectorPlate()
		moonPhaseDetector()
		shockAbsorber()
		chatDetector()
		globalChatDetector()
		onlineDetector()
		spectreLens()
		diaphanousBlock()
		spectreEnergyInjector()
		spectreCoils()
		spectreLogs()
		blockBreaker()
		ironDropper()
		customCraftingTable()
		notificationInterface()
		imbuingStation()
		inventoryTester()
		itemCollectors()
		peaceCandle()
		sakanade()
		playerInterface()
		flooBrick()
		enderEnergyDistributor()
		slimeCube()
		enderMailbox()
		autoPlacer()
		glowingMushroom()
		blockDetector()
		biomeRadar()
		rainShield()
		specialChests()
		advancedRedstoneTorch()
	}

	private fun advancedRedstoneTorch() {
		val standing = ModBlocks.ADVANCED_REDSTONE_TORCH.get()
		val wall = ModBlocks.ADVANCED_REDSTONE_WALL_TORCH.get()

		val red = modLoc("block/advanced_redstone_torch/red")
		val green = modLoc("block/advanced_redstone_torch/green")

		getVariantBuilder(standing)
			.forAllStates {
				val isLit = it.getValue(RedstoneTorchBlock.LIT)

				val name = name(standing) + if (isLit) "_on" else "_off"
				val model = models()
					.torch(name, if (isLit) red else green)
					.renderType(RenderType.cutout().name)

				ConfiguredModel.builder()
					.modelFile(model)
					.build()
			}

		getVariantBuilder(wall)
			.forAllStates {
				val isLit = it.getValue(RedstoneWallTorchBlock.LIT)
				val facing = it.getValue(RedstoneWallTorchBlock.FACING)

				val name = name(wall) + if (isLit) "_on" else "_off"
				val model = models()
					.torchWall(name, if (isLit) red else green)
					.renderType(RenderType.cutout().name)

				val yRotation = when (facing) {
					Direction.NORTH -> 270
					Direction.EAST -> 0
					Direction.SOUTH -> 90
					Direction.WEST -> 180
					else -> 0
				}

				ConfiguredModel.builder()
					.modelFile(model)
					.rotationY(yRotation)
					.build()
			}
	}

	private fun rainShield() {
		val block = ModBlocks.RAIN_SHIELD.get()

		val texture = modLoc("block/rain_shield")

		val model = models()
			.withExistingParent(name(block), "block/end_rod")
			.texture("end_rod", texture)
			.texture("particle", texture)

		simpleBlockWithItem(block, model)
	}

	private fun blockDetector() {
		val block = ModBlocks.BLOCK_DETECTOR.get()

		val frontOff = modLoc("block/block_detector/front")
		val frontOn = modLoc("block/block_detector/front_on")
		val top = mcLoc("block/furnace_top")
		val side = mcLoc("block/furnace_side")

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(BlockDetectorBlock.FACING)
				val triggered = it.getValue(BlockDetectorBlock.TRIGGERED)

				val model = models()
					.orientable(name(block), side, if (triggered) frontOn else frontOff, top)

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				val xRotation = when (facing) {
					Direction.UP -> 270
					Direction.DOWN -> 90
					else -> 0
				}

				ConfiguredModel
					.builder()
					.modelFile(model)
					.rotationY(yRotation)
					.rotationX(xRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/block_detector"),
				existingFileHelper
			)
		)
	}

	private fun autoPlacer() {
		val block = ModBlocks.AUTO_PLACER.get()

		val front = modLoc("block/auto_placer_front")
		val top = mcLoc("block/furnace_top")
		val side = mcLoc("block/furnace_side")

		val model = models()
			.orientable(name(block), side, front, top)

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(AutoPlacerBlock.FACING)

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				val xRotation = when (facing) {
					Direction.UP -> 270
					Direction.DOWN -> 90
					else -> 0
				}

				ConfiguredModel
					.builder()
					.modelFile(model)
					.rotationY(yRotation)
					.rotationX(xRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/auto_placer"),
				existingFileHelper
			)
		)
	}

	private fun enderEnergyDistributor() {
		val block = ModBlocks.ENDER_ENERGY_DISTRIBUTOR.get()

		val textureTop = modLoc("block/ender_energy_distributor/top")
		val textureSides = modLoc("block/ender_energy_distributor/sides")

		val model = models()
			.cube(
				name(block),
				textureSides,
				textureTop,
				textureSides,
				textureSides,
				textureSides,
				textureSides
			)

		simpleBlockWithItem(block, model)
	}

	private fun playerInterface() {
		val block = ModBlocks.PLAYER_INTERFACE.get()

		val textureTop = modLoc("block/player_interface/armor")
		val textureBottom = modLoc("block/player_interface/hotbar")
		val textureNorth = modLoc("block/player_interface/shield")
		val textureOther = modLoc("block/player_interface/main")

		val model = models()
			.cube(
				name(block),
				textureBottom,
				textureTop,
				textureNorth,
				textureOther,
				textureOther,
				textureOther
			)
			.texture("particle", textureTop)

		simpleBlockWithItem(block, model)
	}

	private fun sakanade() {
		val block = ModBlocks.SAKANADE_SPORES.get()

		val texture = modLoc("block/sakanade")

		itemModels()
			.withExistingParent(name(block), "item/generated")
			.texture("layer0", texture)

		var multiPartBuilder = getMultipartBuilder(block)

		for (direction in Direction.entries) {

			val shape = when (direction) {
				Direction.UP -> SakanadeBlock.SHAPE_UP
				Direction.DOWN -> SakanadeBlock.SHAPE_DOWN
				Direction.NORTH -> SakanadeBlock.SHAPE_NORTH
				Direction.SOUTH -> SakanadeBlock.SHAPE_SOUTH
				Direction.WEST -> SakanadeBlock.SHAPE_WEST
				Direction.EAST -> SakanadeBlock.SHAPE_EAST
			}

			val x1 = shape.min(Direction.Axis.X).toFloat() * 16f
			val x2 = shape.max(Direction.Axis.X).toFloat() * 16f
			val y1 = shape.min(Direction.Axis.Y).toFloat() * 16f
			val y2 = shape.max(Direction.Axis.Y).toFloat() * 16f
			val z1 = shape.min(Direction.Axis.Z).toFloat() * 16f
			val z2 = shape.max(Direction.Axis.Z).toFloat() * 16f

			val blockModel = models()
				.withExistingParent(name(block) + "_" + direction.getDirectionName(), "block/block")
				.renderType(RenderType.cutout().name)
				.texture("texture", texture)
				.texture("particle", texture)

				.element()
				.from(x1, y1, z1).to(x2, y2, z2)
				.textureAll("#texture")
				.end()

			val property = SakanadeBlock.PROPERTY_BY_DIRECTION[direction] ?: continue

			multiPartBuilder = multiPartBuilder
				.part()
				.modelFile(blockModel)
				.addModel()
				.condition(property, true)
				.end()
		}

	}

	private fun enderMailbox() {
		val block = ModBlocks.ENDER_MAILBOX.get()

		val woodTexture = mcLoc("block/oak_planks")
		val redstoneTexture = mcLoc("block/redstone_block")
		val mailboxBodyTexture = modLoc("block/ender_mailbox_body")

		fun getBaseModel(name: String): BlockModelBuilder {
			return models()
				.withExistingParent(name, "block/block")
				.texture("post", woodTexture)
				.texture("body", mailboxBodyTexture)
				.texture("particle", mailboxBodyTexture)

				.element()
				.from(6f, 0f, 6f).to(10f, 16f, 10f)
				.allFaces { dir, fb ->
					when (dir) {
						Direction.DOWN -> fb.uvs(9f, 9f, 7f, 7f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
						Direction.UP -> fb.uvs(7f, 7f, 9f, 9f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
						else -> fb.uvs(7f, 8f, 9f, 16f)
					}

					fb.texture("#post")
				}
				.end()

				.element()
				.from(5f, 15f, 1f).to(11f, 22f, 15f)
				.allFaces { dir, fb ->
					when (dir) {
						Direction.DOWN -> fb.uvs(9.5f, 11.5f, 6.5f, 4.5f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
						Direction.UP -> fb.uvs(6.5f, 4.5f, 9.5f, 11.5f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
						Direction.NORTH -> fb.uvs(6.5f, 5f, 9.5f, 8.5f)
						Direction.SOUTH -> fb.uvs(6.5f, 5f, 9.5f, 8.5f)
						Direction.WEST -> fb.uvs(4.5f, 5f, 11.5f, 8.5f)
						Direction.EAST -> fb.uvs(4.5f, 5f, 11.5f, 8.5f)
					}

					fb.texture("#body")
				}
				.end()
		}

		getVariantBuilder(block)
			.forAllStates {

				val facing = it.getValue(EnderMailboxBlock.FACING)
				val isFlagRaised = it.getValue(EnderMailboxBlock.IS_FLAG_RAISED)

				val yRotation = when (facing) {
					Direction.SOUTH -> 0
					Direction.WEST -> 90
					Direction.NORTH -> 180
					Direction.EAST -> 270
					else -> 0
				}

				val name = if (isFlagRaised) {
					"ender_mailbox_flag_raised"
				} else {
					"ender_mailbox"
				}

				val model = getBaseModel(name)

				if (isFlagRaised) {
					model
						.element()
						.from(4f, 23f, 4f).to(5f, 25f, 5f)
						.allFaces { dir, fb ->
							when (dir) {
								Direction.DOWN -> fb.uvs(6.5f, 10f, 6f, 9.5f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
								Direction.UP -> fb.uvs(9.5f, 9.5f, 10f, 10f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
								Direction.NORTH -> fb.uvs(9.5f, 3.5f, 10f, 4.5f)
								Direction.SOUTH -> fb.uvs(6f, 3.5f, 6.5f, 4.5f)
								Direction.WEST -> fb.uvs(6f, 3.5f, 6.5f, 4.5f)
								Direction.EAST -> fb.uvs(9.5f, 3.5f, 10f, 4.5f)
							}

							fb.texture("#flag")
						}
						.end()

						.element()
						.from(4f, 19f, 3f).to(5f, 25f, 4f)
						.allFaces { dir, fb ->
							when (dir) {
								Direction.DOWN -> fb.uvs(6.5f, 10.5f, 6f, 10f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
								Direction.UP -> fb.uvs(9.5f, 10f, 10f, 10.5f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
								Direction.NORTH -> fb.uvs(9.5f, 3.5f, 10f, 6.5f)
								Direction.SOUTH -> fb.uvs(6f, 3.5f, 6.5f, 6.5f)
								Direction.WEST -> fb.uvs(5.5f, 3.5f, 6f, 6.5f)
								Direction.EAST -> fb.uvs(10f, 3.5f, 10.5f, 6.5f)
							}

							fb.texture("#flag")
						}
						.end()
				} else {
					model
						.element()
						.from(4f, 18f, 7f).to(5f, 19f, 9f)
						.allFaces { dir, fb ->
							when (dir) {
								Direction.DOWN -> fb.uvs(6f, 3.5f, 6.5f, 4.5f)
								Direction.UP -> fb.uvs(9.5f, 3.5f, 10f, 4.5f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
								Direction.NORTH -> fb.uvs(6.5f, 10f, 6f, 9.5f)
								Direction.SOUTH -> fb.uvs(9.5f, 9.5f, 10f, 10f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
								Direction.WEST -> fb.uvs(6f, 3.5f, 6.5f, 4.5f)
								Direction.EAST -> fb.uvs(9.5f, 3.5f, 10f, 4.5f).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90)
							}

							fb.texture("#flag")
						}
						.end()

						.element()
						.from(4f, 19f, 3f).to(5f, 20f, 9f)
						.allFaces { dir, fb ->
							when (dir) {
								Direction.DOWN -> fb.uvs(6f, 3.5f, 6.5f, 6.5f)
								Direction.UP -> fb.uvs(9.5f, 3.5f, 10f, 6.5f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
								Direction.NORTH -> fb.uvs(6.5f, 10.5f, 6f, 10f)
								Direction.SOUTH -> fb.uvs(9.5f, 10f, 10f, 10.5f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN)
								Direction.WEST -> fb.uvs(5.5f, 3.5f, 6f, 6.5f)
								Direction.EAST -> fb.uvs(10f, 3.5f, 10.5f, 6.5f).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90)
							}

							fb.texture("#flag")
						}
						.end()
				}
					.texture("flag", redstoneTexture)

				ConfiguredModel
					.builder()
					.modelFile(model)
					.rotationY(yRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/ender_mailbox_flag_raised"),
				existingFileHelper
			)
		)
	}

	//TODO
	private fun peaceCandle() {
		val block = ModBlocks.PEACE_CANDLE.get()

		val model = models()
			.cross(name(block), modLoc("block/peace_candle/body"))
			.renderType(RenderType.cutout().name)

		simpleBlockWithItem(block, model)
	}

	private fun itemCollectors() {
		val blocks = listOf(
			ModBlocks.ITEM_COLLECTOR.get(),
			ModBlocks.ADVANCED_ITEM_COLLECTOR.get()
		)

		for (block in blocks) {

			val texture = if (block == ModBlocks.ITEM_COLLECTOR.get())
				modLoc("block/item_collector")
			else
				modLoc("block/advanced_item_collector")

			val model = models()
				.withExistingParent(name(block), "block/block")
				.texture("texture", texture)
				.texture("particle", texture)

				.element()
				.from(6f, 0f, 6f).to(10f, 1f, 10f)
				.allFaces { direction, modelBuilder ->
					if (direction.axis.isVertical) {
						modelBuilder.uvs(6f, 12f, 10f, 16f)
					} else {
						modelBuilder.uvs(6f, 15f, 10f, 16f)
					}

					modelBuilder.texture("#texture")
				}
				.end()

				.element()
				.from(6.5f, 1f, 6.5f).to(9.5f, 2f, 9.5f)
				.allFaces { direction, modelBuilder ->
					if (direction == Direction.DOWN) {
						modelBuilder.uvs(9f, 15f, 7f, 14f)
					} else {
						modelBuilder.uvs(7f, 14f, 9f, 15f)
					}

					modelBuilder.texture("#texture")
				}
				.end()

				.element()
				.from(7.5f, 2f, 7.5f).to(8.5f, 4f, 8.5f)
				.allFaces { direction, modelBuilder ->
					when (direction) {
						Direction.DOWN -> modelBuilder.uvs(7f, 13f, 6f, 12f)
						Direction.UP -> modelBuilder.uvs(6f, 12f, 7f, 13f)
						else -> modelBuilder.uvs(6f, 10f, 7f, 12f)
					}

					modelBuilder.texture("#texture")
				}
				.end()

				.element()
				.from(7.5f, 4f, 7.5f).to(8.5f, 5f, 8.5f)
				.allFaces { direction, modelBuilder ->
					if (direction == Direction.DOWN) {
						modelBuilder.uvs(7f, 10f, 6f, 9f)
					} else {
						modelBuilder.uvs(6f, 9f, 7f, 10f)
					}

					modelBuilder.texture("#texture")
				}
				.end()

			getVariantBuilder(block)
				.forAllStates {
					val facing = it.getValue(ItemCollectorBlock.FACING)

					val yRotation = when (facing) {
						Direction.SOUTH -> 0
						Direction.WEST -> 90
						Direction.NORTH -> 180
						Direction.EAST -> 270
						else -> 0
					}

					val xRotation = when (facing) {
						Direction.UP -> 180
						Direction.DOWN -> 0
						else -> 90
					}

					ConfiguredModel
						.builder()
						.modelFile(model)
						.rotationY(yRotation)
						.rotationX(xRotation)
						.build()
				}

			itemModels()
				.simpleBlockItem(block)
				.transforms()

				.transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
				.rotation(75f, 45f, 0f)
				.scale(0.7f)
				.end()

				.transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
				.rotation(0f, 45f, 0f)
				.translation(0f, 8f, 0f)
				.end()

				.transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
				.rotation(0f, 225f, 0f)
				.translation(0f, 8f, 0f)
				.end()

				.transform(ItemDisplayContext.GUI)
				.rotation(30f, 225f, 0f)
				.translation(0f, 10.5f, 0f)
				.scale(2.1875f)
				.end()

				.transform(ItemDisplayContext.GROUND)
				.translation(0f, 4f, 0f)
		}
	}

	private fun inventoryTester() {
		val block = ModBlocks.INVENTORY_TESTER.get()

		val texture = modLoc("block/inventory_tester")
		val slabTexture = mcLoc("block/smooth_stone")

		// Model defaults to being on the bottom
		val blockModel = models()
			.withExistingParent(name(block), "block/thin_block")
			.texture("texture", texture)
			.texture("side", slabTexture)
			.texture("particle", texture)
			.element()
			.from(6f, 0f, 6f).to(10f, 1f, 10f)
			.allFaces { face, modelBuilder ->
				if (face.axis.isVertical) {
					modelBuilder.texture("#texture")
				} else {
					modelBuilder.texture("#side")
				}
			}
			.end()

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(DirectionalBlock.FACING)

				val yRotation = when (facing) {
					Direction.SOUTH -> 0
					Direction.WEST -> 90
					Direction.NORTH -> 180
					Direction.EAST -> 270
					else -> 0
				}

				val xRotation = when (facing) {
					Direction.UP -> 180
					Direction.DOWN -> 0
					else -> 90
				}

				ConfiguredModel
					.builder()
					.modelFile(blockModel)
					.rotationY(yRotation)
					.rotationX(xRotation)
					.build()
			}


		itemModels()
			.simpleBlockItem(block)
			.transforms()

			.transform(ItemDisplayContext.GUI)
			.rotation(30f, 225f, 0f)
			.translation(0f, 10.5f, 0f)
			.scale(2.18f)
			.end()
	}

	private fun imbuingStation() {
		val block = ModBlocks.IMBUING_STATION.get()

		val top = modLoc("block/imbuing_station/top")
		val bottom = modLoc("block/imbuing_station/bottom")
		val side = modLoc("block/imbuing_station/side")

		val model = models()
			.cubeBottomTop(
				name(block),
				side,
				bottom,
				top
			)
			.texture("particle", top)

		simpleBlockWithItem(block, model)
	}

	private fun notificationInterface() {
		val block = ModBlocks.NOTIFICATION_INTERFACE.get()

		val top = modLoc("block/notification_interface/top")
		val bottom = modLoc("block/notification_interface/bottom")
		val side = modLoc("block/notification_interface/side")

		val model = models()
			.cubeBottomTop(
				name(block),
				side,
				bottom,
				top
			)
			.texture("particle", top)

		simpleBlockWithItem(block, model)
	}

	private fun customCraftingTable() {
		val block = ModBlocks.CUSTOM_CRAFTING_TABLE.get()
		val top = modLoc("block/custom_crafting_table/top")

		//Never actually gets loaded, just need it for the particles and block state
		val model = models()
			.cubeAll(name(block), top)

		simpleBlock(block, model)
	}

	private fun ironDropper() {
		val block = ModBlocks.IRON_DROPPER.get()

		val frontHorizontal = modLoc("block/iron_dropper/front_horizontal")
		val frontVertical = modLoc("block/iron_dropper/front_vertical")
		val side = modLoc("block/iron_dropper/side")
		val top = modLoc("block/iron_dropper/top")

		val horizontalModel = models()
			.orientable(
				name(block) + "_horizontal",
				side,
				frontHorizontal,
				top
			)

		val verticalModel = models()
			.cubeBottomTop(
				name(block) + "_vertical",
				side,
				top,
				frontVertical
			)

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(DropperBlock.FACING)

				if (Direction.Axis.Y.test(facing)) {
					val xRotation = when (facing) {
						Direction.UP -> 0
						Direction.DOWN -> 180
						else -> 0
					}

					ConfiguredModel
						.builder()
						.modelFile(verticalModel)
						.rotationX(xRotation)
						.build()
				} else {

					val yRotation = when (facing) {
						Direction.NORTH -> 0
						Direction.EAST -> 90
						Direction.SOUTH -> 180
						Direction.WEST -> 270
						else -> 0
					}

					ConfiguredModel
						.builder()
						.modelFile(horizontalModel)
						.rotationY(yRotation)
						.build()
				}
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/iron_dropper_horizontal"),
				existingFileHelper
			)
		)
	}

	private fun blockBreaker() {
		val block = ModBlocks.BLOCK_BREAKER.get()
		val name = name(block)

		val front = modLoc("block/block_breaker/front")
		val frontUpgraded = modLoc("block/block_breaker/front_upgraded")    //TODO: Improve this texture
		val side = modLoc("block/block_breaker/side")

		val model = models()
			.orientable(name, side, front, side)

		val modelUpgraded = models()
			.orientable(name + "_upgraded", side, frontUpgraded, side)

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(DirectionalBlock.FACING)
				val isUpgraded = it.getValue(BlockBreakerBlock.IS_UPGRADED)

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				val xRotation = when (facing) {
					Direction.UP -> 270
					Direction.DOWN -> 90
					else -> 0
				}

				ConfiguredModel
					.builder()
					.modelFile(
						if (isUpgraded) modelUpgraded else model
					)
					.rotationY(yRotation)
					.rotationX(xRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/$name"),
				existingFileHelper
			)
		)

	}

	private fun spectreLogs() {
		logBlock(ModBlocks.SPECTRE_LOG.get())
		blockItem(ModBlocks.SPECTRE_LOG.get())

		//FIXME: Need a stripped texture

		simpleBlockWithItem(
			ModBlocks.SPECTRE_LEAVES.get(),
			models()
				.cubeAll(name(ModBlocks.SPECTRE_LEAVES.get()), modLoc("block/spectre_leaves"))
				.renderType(RenderType.cutout().name)
		)

		simpleBlockWithItem(
			ModBlocks.SPECTRE_WOOD.get(),
			models()
				.cubeAll(name(ModBlocks.SPECTRE_WOOD.get()), modLoc("block/spectre_log"))
		)

	}

	private fun blockItem(block: Block) {
		simpleBlockItem(
			block,
			ModelFile.UncheckedModelFile(modLoc("block/" + name(block)))
		)
	}

	private fun spectreCoils() {
		val blocks = listOf(
			ModBlocks.SPECTRE_COIL_BASIC.get(),
			ModBlocks.SPECTRE_COIL_REDSTONE.get(),
			ModBlocks.SPECTRE_COIL_ENDER.get(),
			ModBlocks.SPECTRE_COIL_NUMBER.get(),
			ModBlocks.SPECTRE_COIL_GENESIS.get()
		)

		val baseModelName = "block/spectre_coil"
		val baseItemModelName = "item/spectre_coil"

		itemModels()
			.withExistingParent(baseItemModelName, modLoc(baseModelName))
			.transforms()

			.transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
			.rotation(75f, 45f, 0f)
			.scale(0.7f)
			.end()

			.transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
			.rotation(0f, 45f, 0f)
			.translation(0f, 8f, 0f)
			.end()

			.transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
			.rotation(0f, 225f, 0f)
			.translation(0f, 8f, 0f)
			.end()

			.transform(ItemDisplayContext.GUI)
			.rotation(40f, 225f, 0f)
			.translation(0f, 10.5f, 0f)
			.scale(2.1875f)
			.end()

			.transform(ItemDisplayContext.GROUND)
			.translation(0f, 4f, 0f)
			.end()

			.end()

		for (block in blocks) {

			itemModels()
				.withExistingParent(
					name(block),
					modLoc(baseItemModelName)
				)

			getVariantBuilder(block)
				.forAllStates {
					val facing = it.getValue(SpectreCoilBlock.FACING)

					// Faces down by default

					val yRotation = when (facing) {
						Direction.NORTH -> 180
						Direction.EAST -> 270
						Direction.SOUTH -> 0
						Direction.WEST -> 90
						else -> 0
					}

					val xRotation = when (facing) {
						Direction.UP -> 180
						Direction.DOWN -> 0
						else -> 90
					}

					val model = models()
						.getExistingFile(modLoc(baseModelName))

					ConfiguredModel
						.builder()
						.modelFile(model)
						.rotationY(yRotation)
						.rotationX(xRotation)
						.build()
				}
		}
	}

	private fun flooBrick() {
		val block = ModBlocks.FLOO_BRICK.get()

		val baseTexture = modLoc("block/floo_brick/base")
		val tintTexture = modLoc("block/floo_brick/tint")

		val luminousModel = models()
			.withExistingParent(name(block), mcLoc("block/block"))
			.texture("base", baseTexture)
			.texture("tint", tintTexture)
			.texture("particle", baseTexture)
			.renderType(RenderType.cutoutMipped().name)

			.element()
			.cube("#base")
			.end()

			.element()
			.cube("#tint")
			.ao(false)
			.emissivity(15, 15)
			.end()

		simpleBlock(block, luminousModel)
	}

	private fun slimeCube() {
		val block = ModBlocks.SLIME_CUBE.get()
		val green = modLoc("block/slime_cube/green")
		val red = modLoc("block/slime_cube/red")

		getVariantBuilder(block)
			.forAllStates {
				val powered = it.getValue(SlimeCubeBlock.POWERED)

				val modelName = name(block) + if (powered) "_powered" else ""
				val texture = if (powered) red else green

				val model = models()
					.withExistingParent(modelName, mcLoc("block/block"))
					.renderType(RenderType.translucent().name)
					.texture("texture", texture)
					.texture("inner", green)
					.texture("particle", texture)

					.element()
					.from(6f, 6f, 6f).to(10f, 10f, 10f)
					.textureAll("#texture")
					.end()

					.element()
					.from(7f, 7f, 7f).to(9f, 9f, 9f)
					.textureAll("#inner")
					.emissivity(15, 15)
					.end()

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/slime_cube"),
				existingFileHelper
			)
		)
	}

	private fun biomeRadar() {
		val block = ModBlocks.BIOME_RADAR.get()

		val model = models()
			.withExistingParent(name(block), mcLoc("block/block"))
			.renderType(RenderType.cutout().name)
			.texture("particle", mcLoc("block/iron_block"))
			.texture("texture", mcLoc("block/iron_block"))
			.texture("glass", mcLoc("block/glass"))
			.texture("top", modLoc("block/biome_radar_top"))

			.element()
			.from(0f, 14f, 0f).to(16f, 16f, 16f)
			.face(Direction.DOWN).uvs(16f, 16f, 0f, 0f).texture("#texture").end()
			.face(Direction.UP).uvs(0f, 0f, 16f, 16f).texture("#top").end()
			.face(Direction.NORTH).uvs(0f, 0f, 16f, 2f).texture("#texture").end()
			.face(Direction.SOUTH).uvs(0f, 0f, 16f, 2f).texture("#texture").end()
			.face(Direction.WEST).uvs(0f, 0f, 16f, 2f).texture("#texture").end()
			.face(Direction.EAST).uvs(0f, 0f, 16f, 2f).texture("#texture").end()
			.end()

			.element()
			.from(0f, 0f, 0f).to(16f, 2f, 16f)
			.face(Direction.DOWN).uvs(16f, 16f, 0f, 0f).texture("#texture").end()
			.face(Direction.UP).uvs(0f, 0f, 16f, 16f).texture("#texture").end()
			.face(Direction.NORTH).uvs(0f, 14f, 16f, 16f).texture("#texture").end()
			.face(Direction.SOUTH).uvs(0f, 14f, 16f, 16f).texture("#texture").end()
			.face(Direction.WEST).uvs(0f, 14f, 16f, 16f).texture("#texture").end()
			.face(Direction.EAST).uvs(0f, 14f, 16f, 16f).texture("#texture").end()
			.end()

			.element()
			.from(0f, 2f, 0f).to(2f, 14f, 2f)
			.face(Direction.DOWN).uvs(16f, 2f, 14f, 0f).texture("#texture").end()
			.face(Direction.UP).uvs(0f, 0f, 2f, 2f).texture("#texture").end()
			.face(Direction.NORTH).uvs(14f, 2f, 16f, 14f).texture("#texture").end()
			.face(Direction.SOUTH).uvs(0f, 2f, 2f, 14f).texture("#texture").end()
			.face(Direction.WEST).uvs(0f, 2f, 2f, 14f).texture("#texture").end()
			.face(Direction.EAST).uvs(14f, 2f, 16f, 14f).texture("#texture").end()
			.end()

			.element()
			.from(14f, 2f, 0f).to(16f, 14f, 2f)
			.face(Direction.DOWN).uvs(2f, 2f, 0f, 0f).texture("#texture").end()
			.face(Direction.UP).uvs(14f, 0f, 16f, 2f).texture("#texture").end()
			.face(Direction.NORTH).uvs(0f, 2f, 2f, 14f).texture("#texture").end()
			.face(Direction.SOUTH).uvs(14f, 2f, 16f, 14f).texture("#texture").end()
			.face(Direction.WEST).uvs(0f, 2f, 2f, 14f).texture("#texture").end()
			.face(Direction.EAST).uvs(14f, 2f, 16f, 14f).texture("#texture").end()
			.end()

			.element()
			.from(0f, 2f, 14f).to(2f, 14f, 16f)
			.face(Direction.DOWN).uvs(16f, 16f, 14f, 14f).texture("#texture").end()
			.face(Direction.UP).uvs(0f, 14f, 2f, 16f).texture("#texture").end()
			.face(Direction.NORTH).uvs(14f, 2f, 16f, 14f).texture("#texture").end()
			.face(Direction.SOUTH).uvs(0f, 2f, 2f, 14f).texture("#texture").end()
			.face(Direction.WEST).uvs(14f, 2f, 16f, 14f).texture("#texture").end()
			.face(Direction.EAST).uvs(0f, 2f, 2f, 14f).texture("#texture").end()
			.end()

			.element()
			.from(14f, 2f, 14f).to(16f, 14f, 16f)
			.face(Direction.DOWN).uvs(2f, 16f, 0f, 14f).texture("#texture").end()
			.face(Direction.UP).uvs(14f, 14f, 16f, 16f).texture("#texture").end()
			.face(Direction.NORTH).uvs(0f, 2f, 2f, 14f).texture("#texture").end()
			.face(Direction.SOUTH).uvs(14f, 2f, 16f, 14f).texture("#texture").end()
			.face(Direction.WEST).uvs(14f, 2f, 16f, 14f).texture("#texture").end()
			.face(Direction.EAST).uvs(0f, 2f, 2f, 14f).texture("#texture").end()
			.end()

			.element()
			.from(0.00625f, 2f, 2f).to(0.00625f, 14f, 14f)
			.face(Direction.WEST).uvs(0f, 0f, 16f, 16f).texture("#glass").tintindex(1).end()
			.face(Direction.EAST).uvs(0f, 0f, 16f, 16f).texture("#glass").tintindex(1).end()
			.end()

			.element()
			.from(15.99375f, 2f, 2f).to(15.99375f, 14f, 14f)
			.face(Direction.WEST).uvs(0f, 0f, 16f, 16f).texture("#glass").tintindex(1).end()
			.face(Direction.EAST).uvs(0f, 0f, 16f, 16f).texture("#glass").tintindex(1).end()
			.end()

			.element()
			.from(2f, 2f, 0.00625f).to(14f, 14f, 0.00625f)
			.face(Direction.NORTH).uvs(0f, 0f, 16f, 16f).texture("#glass").tintindex(1).end()
			.face(Direction.SOUTH).uvs(0f, 0f, 16f, 16f).texture("#glass").tintindex(1).end()
			.end()

			.element()
			.from(2f, 2f, 15.99375f).to(14f, 14f, 15.99375f)
			.face(Direction.NORTH).uvs(0f, 0f, 16f, 16f).texture("#glass").tintindex(1).end()
			.face(Direction.SOUTH).uvs(0f, 0f, 16f, 16f).texture("#glass").tintindex(1).end()
			.end()

		simpleBlockWithItem(block, model)
	}

	private fun spectreEnergyInjector() {
		val block = ModBlocks.SPECTRE_ENERGY_INJECTOR.get()

		val caseModel = models()
			.cubeAll(name(block) + "_case", modLoc("block/spectre_energy_injector"))
			.renderType(RenderType.translucent().name)

		// Needs to be a separate model because if this was also translucent, the BER's rays would stop the pedestal from rendering
		val pedestalModel = models()
			.withExistingParent(name(block) + "_pedestal", "block/block")
			.texture("all", mcLoc("block/obsidian"))
			.element()
			.from(1f, 0.1f, 1f).to(15f, 2f, 15f)
			.textureAll("#all")
			.end()

		// Makes the blockstate, still need to make the item model
		getMultipartBuilder(block)
			.part()
			.modelFile(caseModel)
			.addModel()
			.end()
			.part()
			.modelFile(pedestalModel)
			.addModel()
			.end()

		val itemModel = models()
			.withExistingParent(name(block), mcLoc("block/block"))
			.texture("case", mcLoc("block/tinted_glass"))
			.texture("pedestal", mcLoc("block/obsidian"))
			.renderType(RenderType.translucent().name)

			.element()
			.from(0f, 0f, 0f).to(16f, 16f, 16f)
			.textureAll("#case")
			.end()

			.element()
			.from(1f, 0.1f, 1f).to(15f, 2f, 15f)
			.textureAll("#pedestal")
			.end()

		simpleBlockItem(block, itemModel)
	}

	private fun specialChests() {
		val blocks = listOf(
			ModBlocks.WATER_CHEST.get(),
			ModBlocks.NATURE_CHEST.get()
		)

		for (block in blocks) {
			//Never actually gets loaded, just need it for the particles and block state
			val model = models()
				.withExistingParent(name(block), mcLoc("block/chest"))

			simpleBlock(block, model)
		}
	}

	private fun diaphanousBlock() {
		val block = ModBlocks.DIAPHANOUS_BLOCK.get()

		//Never actually gets loaded, just need it for the particles and block state
		val model = models()
			.cubeAll(name(block), mcLoc("block/stone"))

		simpleBlock(block, model)
	}

	private fun spectreLens() {
		val block = ModBlocks.SPECTRE_LENS.get()

		val texture = modLoc("block/spectre_lens")

		val model = models()
			.withExistingParent(name(block), "block/thin_block")
			.texture("texture", texture)
			.texture("particle", texture)
			.element()
			.from(0f, 0f, 0f).to(16f, 1f, 16f)
			.textureAll("#texture")
			.end()
			.renderType(RenderType.translucent().name)

		simpleBlockWithItem(block, model)
	}

	private fun onlineDetector() {
		val block = ModBlocks.ONLINE_DETECTOR.get()

		val topTexture = modLoc("block/online_detector/top")
		val bottomTexture = modLoc("block/online_detector/bottom")
		val sideOffTexture = modLoc("block/online_detector/side_off")
		val sideOnTexture = modLoc("block/online_detector/side_on")

		getVariantBuilder(block)
			.forAllStates {
				val isEnabled = it.getValue(OnlineDetectorBlock.ENABLED)
				val facing = it.getValue(OnlineDetectorBlock.HORIZONTAL_FACING)

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				val modelName = name(block) + if (isEnabled) "_on" else "_off"

				val model = models()
					.cubeBottomTop(
						modelName,
						if (isEnabled) sideOnTexture else sideOffTexture,
						bottomTexture,
						topTexture
					)

				ConfiguredModel
					.builder()
					.modelFile(model)
					.rotationY(yRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/online_detector_off"),
				existingFileHelper
			)
		)
	}

	private fun globalChatDetector() {
		val block = ModBlocks.GLOBAL_CHAT_DETECTOR.get()

		val topTexture = modLoc("block/global_chat_detector/top")
		val bottomTexture = modLoc("block/global_chat_detector/bottom")
		val sideOffTexture = modLoc("block/global_chat_detector/side_off")
		val sideOnTexture = modLoc("block/global_chat_detector/side_on")

		getVariantBuilder(block)
			.forAllStates {
				val isEnabled = it.getValue(ChatDetectorBlock.ENABLED)

				val modelName = name(block) + if (isEnabled) "_on" else "_off"

				val model = models()
					.cubeBottomTop(
						modelName,
						if (isEnabled) sideOnTexture else sideOffTexture,
						bottomTexture,
						topTexture
					)

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/global_chat_detector_off"),
				existingFileHelper
			)
		)
	}

	private fun chatDetector() {
		val block = ModBlocks.CHAT_DETECTOR.get()

		val topTexture = modLoc("block/chat_detector/top")
		val bottomTexture = modLoc("block/chat_detector/bottom")
		val sideOffTexture = modLoc("block/chat_detector/side_off")
		val sideOnTexture = modLoc("block/chat_detector/side_on")

		getVariantBuilder(block)
			.forAllStates {
				val isEnabled = it.getValue(ChatDetectorBlock.ENABLED)

				val modelName = name(block) + if (isEnabled) "_on" else "_off"

				val model = models()
					.cubeBottomTop(
						modelName,
						if (isEnabled) sideOnTexture else sideOffTexture,
						bottomTexture,
						topTexture
					)

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/chat_detector_off"),
				existingFileHelper
			)
		)
	}

	private fun shockAbsorber() {
		val block = ModBlocks.SHOCK_ABSORBER.get()

		val sideTexture = modLoc("block/shock_absorber/side")
		val bottomTexture = modLoc("block/shock_absorber/bottom")

		getVariantBuilder(block)
			.forAllStates {
				val isActive = it.getValue(ShockAbsorberBlock.POWER) > 0

				val modelName = name(block) + if (isActive) "_active" else ""
				val topTexture = modLoc("block/shock_absorber/top" + if (isActive) "_active" else "")

				val model = models()
					.cubeBottomTop(
						modelName,
						sideTexture,
						bottomTexture,
						topTexture
					)

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/shock_absorber"),
				existingFileHelper
			)
		)
	}

	private fun moonPhaseDetector() {
		val block = ModBlocks.MOON_PHASE_DETECTOR.get()

		val sideTexture = modLoc("block/moon_phase_detector_side")

		getVariantBuilder(block)
			.forAllStates {
				val isInverted = it.getValue(MoonPhaseDetectorBlock.INVERTED)

				val name = name(block) + if (isInverted) "_inverted" else ""

				val topTexture = modLoc("block/moon_phase_detector_top" + if (isInverted) "_inverted" else "")

				val model = models()
					.withExistingParent(name, mcLoc("block/daylight_detector"))
					.texture("side", sideTexture)
					.texture("top", topTexture)

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/moon_phase_detector"),
				existingFileHelper
			)
		)
	}

	private fun redirectorPlate() {
		val block = ModBlocks.REDIRECTOR_PLATE.get()

		val baseTexture = modLoc("block/plate/redirector/base")
		val activeTexture = modLoc("block/plate/redirector/active")
		val inactiveTexture = modLoc("block/plate/redirector/inactive")

		val baseModel = models()
			.pressurePlate(name(block), baseTexture)
			.renderType(RenderType.cutout().name)

		val activeModel = models()
			.pressurePlate(name(block) + "_active", activeTexture)
			.renderType(RenderType.cutout().name)

		//TODO: Figure out how to get this on the inactive sides
		models()
			.pressurePlate(name(block) + "_inactive", inactiveTexture)
			.renderType(RenderType.cutout().name)

		val builder = getMultipartBuilder(block)
			.part().modelFile(baseModel).addModel().end()

		for (direction in Direction.Plane.HORIZONTAL) {
			builder
				.part()
				.modelFile(activeModel)
				.rotationY(
					when (direction) {
						Direction.NORTH -> 0
						Direction.EAST -> 90
						Direction.SOUTH -> 180
						Direction.WEST -> 270
						else -> 0
					}
				)
				.addModel()
				.condition(RedirectorPlateBlock.ACTIVE_ONE, direction)
				.condition(RedirectorPlateBlock.ACTIVE_TWO, direction)
		}

		simpleBlockItem(
			block,
			baseModel
		)
	}

	private fun directionalAcceleratorPlate() {
		val block = ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE.get()

		val texture = modLoc("block/plate/directional_accelerator")
		val model = models()
			.pressurePlate(name(block), texture)
			.renderType(RenderType.cutout().name)

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(DirectionalAcceleratorPlateBlock.FACING)

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				ConfiguredModel
					.builder()
					.modelFile(model)
					.rotationY(yRotation)
					.build()
			}

		simpleBlockItem(block, model)
	}

	private fun basePlates() {

		//TODO: Extraction, Filtered Director, Processing, Redirector, Redstone

		val plateBlocks = listOf(
			ModBlocks.ACCELERATOR_PLATE,
			ModBlocks.BOUNCY_PLATE,
			ModBlocks.COLLECTION_PLATE,
			ModBlocks.CORRECTOR_PLATE,
			ModBlocks.ITEM_REJUVENATOR_PLATE,
			ModBlocks.ITEM_SEALER_PLATE
		).map { it.get() }

		for (plate in plateBlocks) {

			val textureName = name(plate).removeSuffix("_plate")
			val texture = modLoc("block/plate/$textureName")

			val model = models()
				.pressurePlate(name(plate), texture)
				.renderType(RenderType.cutout().name)

			simpleBlockWithItem(plate, model)
		}
	}

	private fun beanStalk() {
		val leafModel = models()
			.cross("bean_stalk", modLoc("block/bean_stalk"))
			.renderType(RenderType.cutout().name)

		for (block in listOf(ModBlocks.BEAN_STALK.get(), ModBlocks.LESSER_BEAN_STALK.get())) {
			simpleBlock(block, leafModel)
		}
	}

	private fun beanSprout() {
		val block = ModBlocks.BEAN_SPROUT.get()

		getVariantBuilder(block)
			.forAllStates {
				val fullyGrown = it.getValue(BeanSproutBlock.AGE) == BeanSproutBlock.MAXIMUM_AGE
				val modelName = name(block) + if (fullyGrown) "_big" else "_small"

				val model = models()
					.cross(modelName, modLoc("block/$modelName"))
					.renderType(RenderType.cutout().name)

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}
	}

	private fun lotus() {
		val block = ModBlocks.LOTUS.get()

		getVariantBuilder(block)
			.forAllStates {
				val age = it.getValue(LotusBlock.AGE)
				val nameWithAge = name(block) + "_$age"

				val model = models()
					.cross(nameWithAge, modLoc("block/$nameWithAge"))
					.renderType(RenderType.cutout().name)

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}
	}

	private fun igniter() {
		val block = ModBlocks.IGNITER.get()

		val front = modLoc("block/igniter/front")
		val side = modLoc("block/igniter/side")
		val back = modLoc("block/igniter/back")

		val model = models()
			.withExistingParent(name(block), "minecraft:block/template_piston")
			.texture("bottom", back)
			.texture("platform", front)
			.texture("side", side)

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(DirectionalBlock.FACING)

				val xRotation = when (facing) {
					Direction.UP -> 270
					Direction.DOWN -> 90
					else -> 0
				}

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				ConfiguredModel
					.builder()
					.modelFile(model)
					.rotationX(xRotation)
					.rotationY(yRotation)
					.build()
			}

		itemModels()
			.withExistingParent(name(block), mcLoc("piston"))
			.texture("bottom", back)
			.texture("top", front)
			.texture("side", side)
	}

	private fun blockDestabilizer() {
		val block = ModBlocks.BLOCK_DESTABILIZER.get()

		val faceTexture = modLoc("block/block_destabilizer/face")
		val frontOverlay = modLoc("block/block_destabilizer/front")
		val sideOverlay = modLoc("block/block_destabilizer/side")

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(DirectionalBlock.FACING)
				val modelName = name(block) + "_" + facing.getDirectionName().lowercase()

				val xRotation = when (facing) {
					Direction.UP -> 270
					Direction.DOWN -> 90
					else -> 0
				}

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				val model = models()
					.withExistingParent(modelName, mcLoc("block/block"))
					.texture("all", faceTexture)
					.texture("particle", faceTexture)
					.texture("front", frontOverlay)
					.texture("side", sideOverlay)
					.renderType(RenderType.cutout().name)

					.element()
					.from(0f, 0f, 0f).to(16f, 16f, 16f)
					.textureAll("#all")
					.end()

					.element()
					.from(-0.01f, -0.01f, -0.01f).to(16.01f, 16.01f, 16.01f)
					.emissivity(15, 15)

					.face(Direction.NORTH)
					.texture("#front")
					.end()

					.face(Direction.EAST)
					.texture("#side")
					.end()

					.face(Direction.SOUTH)
					.texture("#side")
					.end()

					.face(Direction.WEST)
					.texture("#side")
					.end()

					.face(Direction.UP)
					.texture("#side")
					.end()

					.face(Direction.DOWN)
					.texture("#side")
					.end()

					.end()

				ConfiguredModel
					.builder()
					.modelFile(model)
					.rotationX(xRotation)
					.rotationY(yRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/block_destabilizer_north"),
				existingFileHelper
			)
		)
	}

	private fun compressedSlime() {
		val block = ModBlocks.COMPRESSED_SLIME_BLOCK.get()

		val texture = mcLoc("block/slime_block")

		getVariantBuilder(block)
			.forAllStates {
				val compressionLevel = it.getValue(CompressedSlimeBlock.COMPRESSION_LEVEL)
				val modelName = name(block) + "_$compressionLevel"

				val height = when (compressionLevel) {
					0 -> 8f
					1 -> 4f
					2 -> 2f
					else -> 0f
				}

				val innerHeight = height * 0.5f
				val innerBottom = height * 0.5f - innerHeight * 0.5f
				val innerTop = height * 0.5f + innerHeight * 0.5f

				val model = models()
					.withExistingParent(modelName, mcLoc("block/block"))
					.texture("all", texture)
					.texture("particle", texture)
					.renderType(RenderType.translucent().name)

					.element()
					.from(0f, 0f, 0f).to(16f, height, 16f)
					.textureAll("#all")
					.end()

					.element()
					.from(2f, innerBottom, 2f).to(14f, innerTop, 14f)
					.textureAll("#all")
					.end()

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/compressed_slime_block_0"),
				existingFileHelper
			)
		)
	}

	private fun fertilizedDirt() {
		val block = ModBlocks.FERTILIZED_DIRT.get()

		getVariantBuilder(block)
			.forAllStates {
				val tilled = it.getValue(FertilizedDirtBlock.TILLED)

				val texture = blockTexture(block)

				val modelName = name(block) + if (tilled) "_tilled" else ""
				val model = if (tilled) {
					models()
						.withExistingParent(modelName, mcLoc("block/block"))
						.texture("texture", texture)
						.texture("particle", texture)

						.element()
						.from(0f, 0f, 0f).to(16f, 15f, 16f)
						.textureAll("#texture")
						.end()
				} else {
					models()
						.cubeAll(
							modelName,
							texture
						)
				}

				ConfiguredModel
					.builder()
					.modelFile(model)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/fertilized_dirt"),
				existingFileHelper
			)
		)
	}

	private fun enderBridges() {
		val enderBridges = listOf(
			ModBlocks.ENDER_BRIDGE,
			ModBlocks.PRISMARINE_ENDER_BRIDGE
		).map { it.get() }

		for (enderBridge in enderBridges) {
			val isPrismarine = enderBridge == ModBlocks.PRISMARINE_ENDER_BRIDGE.get()

			getVariantBuilder(enderBridge)
				.forAllStates {
					val facing = it.getValue(EnderBridgeBlock.FACING)
					val enabled = it.getValue(EnderBridgeBlock.ENABLED)

					val yRotation = when (facing) {
						Direction.NORTH -> 0
						Direction.EAST -> 90
						Direction.SOUTH -> 180
						Direction.WEST -> 270
						else -> 0
					}

					val xRotation = when (facing) {
						Direction.UP -> 270
						Direction.DOWN -> 90
						else -> 0
					}

					val modelName = name(enderBridge) + if (enabled) "_on" else "_off"

					val sideTexture = StringBuilder()
						.append("block/ender_bridge/")
						.append(if (isPrismarine) "prismarine/" else "")
						.append("side")
						.toString()

					val frontTexture = StringBuilder()
						.append("block/ender_bridge/")
						.append(if (isPrismarine) "prismarine/" else "")
						.append("front")
						.append(if (enabled) "_on" else "_off")
						.toString()

					ConfiguredModel
						.builder()
						.modelFile(
							models()
								.orientable(
									modelName,
									modLoc(sideTexture),
									modLoc(frontTexture),
									modLoc(sideTexture)
								)
						)
						.rotationY(yRotation)
						.rotationX(xRotation)
						.build()
				}

			simpleBlockItem(
				enderBridge,
				ItemModelBuilder(
					modLoc(
						StringBuilder()
							.append("block/")
							.append(if (isPrismarine) "prismarine_" else "")
							.append("ender_bridge_off")
							.toString()
					),
					existingFileHelper
				)
			)
		}
	}

	private fun biomeBlocks() {
		val biomeBlocks = mapOf(
			ModBlocks.BIOME_STONE.get() to mcLoc("block/stone"),
			ModBlocks.BIOME_COBBLESTONE.get() to mcLoc("block/cobblestone"),
			ModBlocks.BIOME_STONE_BRICKS.get() to mcLoc("block/stone_bricks"),
			ModBlocks.BIOME_STONE_BRICKS_CHISELED.get() to mcLoc("block/chiseled_stone_bricks"),
			ModBlocks.BIOME_STONE_BRICKS_CRACKED.get() to mcLoc("block/cracked_stone_bricks"),
			ModBlocks.BIOME_GLASS.get() to mcLoc("block/glass"),
		)

		for ((block, texture) in biomeBlocks) {

			var model = models()
				.withExistingParent(name(block), "block/block")
				.texture("all", texture)
				.texture("particle", texture)

				.element()
				.from(0f, 0f, 0f).to(16f, 16f, 16f)
				.allFaces { _, faceBuilder ->
					faceBuilder.tintindex(0)
				}
				.textureAll("#all")
				.end()

			if (block == ModBlocks.BIOME_GLASS.get()) {
				model = model.renderType(RenderType.translucent().name)
			}

			simpleBlockWithItem(block, model)
		}
	}

	private fun crossBlocks() {
		val crossBlocks = listOf(
			ModBlocks.PITCHER_PLANT,
			ModBlocks.SPECTRE_SAPLING
		).map { it.get() }

		for (block in crossBlocks) {
			val texture = blockTexture(block)

			val model = models()
				.cross(name(block), texture)
				.renderType(RenderType.cutout().name)

			simpleBlock(block, model)

			this.itemModels()
				.withExistingParent(name(block), "item/generated")
				.texture("layer0", texture)
		}
	}

	private fun contactButton() {
		val block = ModBlocks.CONTACT_BUTTON.get()

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(DirectionalBlock.FACING)
				val enabled = it.getValue(ContactButtonBlock.ENABLED)

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				val xRotation = when (facing) {
					Direction.UP -> 270
					Direction.DOWN -> 90
					else -> 0
				}

				val modelName = name(block) + if (enabled) "_on" else "_off"
				val sideTexture = "block/contact_button/" + if (enabled) "side_on" else "side_off"

				ConfiguredModel
					.builder()
					.modelFile(
						models()
							.orientable(
								modelName,
								modLoc(sideTexture),
								modLoc("block/contact_button/front"),
								modLoc(sideTexture)
							)
					)
					.rotationY(yRotation)
					.rotationX(xRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/contact_button_off"),
				existingFileHelper
			)
		)
	}

	private fun contactLever() {
		val block = ModBlocks.CONTACT_LEVER.get()

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(DirectionalBlock.FACING)
				val enabled = it.getValue(ContactLeverBlock.ENABLED)

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				val xRotation = when (facing) {
					Direction.UP -> 270
					Direction.DOWN -> 90
					else -> 0
				}

				val modelName = name(block) + if (enabled) "_on" else "_off"
				val sideTexture = "block/contact_lever/" + if (enabled) "side_on" else "side_off"

				ConfiguredModel
					.builder()
					.modelFile(
						models()
							.orientable(
								modelName,
								modLoc(sideTexture),
								modLoc("block/contact_lever/front"),
								modLoc(sideTexture)
							)
					)
					.rotationY(yRotation)
					.rotationX(xRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/contact_lever_off"),
				existingFileHelper
			)
		)
	}

	private fun coloredGrass() {
		for (color in DyeColor.entries) {
			val block = ModBlocks.getColoredGrass(color)?.get() ?: continue

			val model = models()
				.withExistingParent(name(block), mcLoc("block/grass_block"))
				.renderType(RenderType.cutout().name)

			simpleBlockWithItem(block, model)
		}
	}

	private fun glowingMushroom() {
		val block = ModBlocks.GLOWING_MUSHROOM.get()

		val base = modLoc("block/glowing_mushroom/base")
		val glow = modLoc("block/glowing_mushroom/glow")

		val blockModel = models().withExistingParent(name(block), "block/block")
			.texture("base", base)
			.texture("glow", glow)
			.renderType(RenderType.cutoutMipped().name)

			// Base north/south
			.element()
			.from(0.8f, 0f, 8f).to(15.2f, 16f, 8f)
			.rotation().origin(8f, 8f, 8f).axis(Direction.Axis.Y).angle(45f).end()
			.shade(false)
			.face(Direction.NORTH).texture("#base").uvs(0f, 0f, 16f, 16f).end()
			.face(Direction.SOUTH).texture("#base").uvs(0f, 0f, 16f, 16f).end()
			.end()

			// Base east/west
			.element()
			.from(8f, 0f, 0.8f).to(8f, 16f, 15.2f)
			.rotation().origin(8f, 8f, 8f).axis(Direction.Axis.Y).angle(45f).end()
			.shade(false)
			.face(Direction.EAST).texture("#base").uvs(0f, 0f, 16f, 16f).end()
			.face(Direction.WEST).texture("#base").uvs(0f, 0f, 16f, 16f).end()
			.end()

			// Glow north/south
			.element()
			.from(0.8f, 0f, 8f).to(15.2f, 16f, 8f)
			.rotation().origin(8f, 8f, 8f).axis(Direction.Axis.Y).angle(45f).end()
			.shade(false)
			.emissivity(15, 15)
			.face(Direction.NORTH).texture("#glow").uvs(0f, 0f, 16f, 16f).end()
			.face(Direction.SOUTH).texture("#glow").uvs(0f, 0f, 16f, 16f).end()
			.end()

			// Glow east/west
			.element()
			.from(8f, 0f, 0.8f).to(8f, 16f, 15.2f)
			.rotation().origin(8f, 8f, 8f).axis(Direction.Axis.Y).angle(45f).end()
			.shade(false)
			.emissivity(15, 15)
			.face(Direction.EAST).texture("#glow").uvs(0f, 0f, 16f, 16f).end()
			.face(Direction.WEST).texture("#glow").uvs(0f, 0f, 16f, 16f).end()
			.end()

		simpleBlock(block, blockModel)

		val combined = modLoc("block/glowing_mushroom/combined")

		this.itemModels()
			.withExistingParent(name(block), "item/generated")
			.texture("layer0", combined)
	}

	private fun luminousBlocks() {
		for (color in DyeColor.entries) {
			val opaqueTexture = modLoc("block/luminous_block/$color")
			val translucentTexture = modLoc("block/luminous_block/translucent/$color")

			val opaqueBlock = ModBlocks.getLuminousBlock(color)?.get()
			if (opaqueBlock != null) {
				val opaqueModel = models()
					.withExistingParent(name(opaqueBlock), mcLoc("block/block"))
					.texture("all", opaqueTexture)
					.texture("particle", translucentTexture)

					.element()
					.cube("#all")
					.ao(false)
					.emissivity(15, 15)
					.end()

				simpleBlockWithItem(opaqueBlock, opaqueModel)
			}

			val translucentBlock = ModBlocks.getLuminousBlockTranslucent(color)?.get()
			if (translucentBlock != null) {
				val translucentModel = models()
					.withExistingParent(name(translucentBlock), "block/block")
					.renderType(RenderType.translucent().name)
					.texture("all", translucentTexture)
					.texture("particle", translucentTexture)

					.element()
					.cube("#all")
					.ao(false)
					.emissivity(15, 15)
					.end()

				simpleBlockWithItem(translucentBlock, translucentModel)
			}
		}
	}

	private fun stainedBricks() {
		for (color in DyeColor.entries) {
			val regular = ModBlocks.getStainedBrick(color)?.get()

			if (regular != null) {
				val regularTexture = modLoc("block/stained_bricks/$color")

				val regularModel = models()
					.cubeAll(name(regular), regularTexture)

				simpleBlockWithItem(regular, regularModel)
			}

			val luminous = ModBlocks.getStainedBrickLuminous(color)?.get()

			if (luminous != null) {
				val luminousBaseTexture = modLoc("block/luminous_stained_brick/base/$color")
				val luminousTintTexture = modLoc("block/luminous_stained_brick/tint/$color")

				val luminousModel = models()
					.withExistingParent(name(luminous), mcLoc("block/block"))
					.texture("base", luminousBaseTexture)
					.texture("tint", luminousTintTexture)
					.texture("particle", luminousTintTexture)
					.renderType(RenderType.cutoutMipped().name)

					.element()
					.cube("#base")
					.end()

					.element()
					.cube("#tint")
					.ao(false)
					.emissivity(15, 15)
					.end()

				simpleBlockWithItem(luminous, luminousModel)
			}
		}
	}

	private fun platforms() {
		val platformTextureMap = mapOf(
			ModBlocks.OAK_PLATFORM to mcLoc("block/oak_planks"),
			ModBlocks.SPRUCE_PLATFORM to mcLoc("block/spruce_planks"),
			ModBlocks.BIRCH_PLATFORM to mcLoc("block/birch_planks"),
			ModBlocks.JUNGLE_PLATFORM to mcLoc("block/jungle_planks"),
			ModBlocks.ACACIA_PLATFORM to mcLoc("block/acacia_planks"),
			ModBlocks.DARK_OAK_PLATFORM to mcLoc("block/dark_oak_planks"),
			ModBlocks.CRIMSON_PLATFORM to mcLoc("block/crimson_planks"),
			ModBlocks.WARPED_PLATFORM to mcLoc("block/warped_planks"),
			ModBlocks.MANGROVE_PLATFORM to mcLoc("block/mangrove_planks"),
			ModBlocks.BAMBOO_PLATFORM to mcLoc("block/bamboo_planks"),
			ModBlocks.CHERRY_PLATFORM to mcLoc("block/cherry_planks"),
			ModBlocks.SUPER_LUBRICANT_PLATFORM to modLoc("block/super_lubricant_ice"),
			ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM to modLoc("block/filtered_super_lubricant_platform")
		)

		for ((deferred, texture) in platformTextureMap) {
			val modelName = "block/" + name(deferred.get())

			val model = models()
				.withExistingParent(modelName, mcLoc("block/block"))
				.texture("texture", texture)
				.texture("particle", texture)

				.element()
				.from(0f, 15f, 0f).to(16f, 16f, 16f)
				.textureAll("#texture")
				.end()

			simpleBlockWithItem(deferred.get(), model)
		}
	}

	private fun triggerGlass() {
		val block = ModBlocks.TRIGGER_GLASS.get()

		getVariantBuilder(block)
			.forAllStates {
				val notSolid = it.getValue(TriggerGlassBlock.NOT_SOLID)
				val modelName = name(block) + if (notSolid) "_triggered" else ""
				val textureLocation = if (notSolid) "block/trigger_glass_triggered" else "block/trigger_glass"

				ConfiguredModel
					.builder()
					.modelFile(
						models()
							.cubeAll(
								modelName,
								modLoc(textureLocation)
							)
							.renderType(RenderType.translucent().name)
					)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/trigger_glass"),
				existingFileHelper
			)
		)
	}

	private fun buildSingleFaces() {
		oneUniqueFace(
			ModBlocks.ANALOG_EMITTER.get(),
			uniqueTexture = modLoc("block/analog_emitter_front"),
			otherTexture = modLoc("block/analog_emitter_side")
		)
		oneUniqueFace(
			ModBlocks.SIDED_BLOCK_OF_REDSTONE.get(),
			uniqueTexture = modLoc("block/sided_redstone_front"),
			otherTexture = modLoc("block/sided_redstone_side")
		)
		oneUniqueFace(
			ModBlocks.ENERGY_DISTRIBUTOR.get(),
			uniqueTexture = modLoc("block/energy_distributor/front"),
			otherTexture = modLoc("block/energy_distributor/side")
		)
		oneUniqueFace(
			ModBlocks.BLOCK_TELEPORTER.get(),
			uniqueTexture = modLoc("block/block_teleporter/front"),
			otherTexture = modLoc("block/block_teleporter/side")
		)
	}

	private fun oneUniqueFace(
		block: Block,
		uniqueTexture: ResourceLocation,
		otherTexture: ResourceLocation
	) {
		val name = name(block)

		getVariantBuilder(block)
			.forAllStates {
				val facing = it.getValue(BlockStateProperties.FACING)

				val yRotation = when (facing) {
					Direction.NORTH -> 0
					Direction.EAST -> 90
					Direction.SOUTH -> 180
					Direction.WEST -> 270
					else -> 0
				}

				val xRotation = when (facing) {
					Direction.UP -> 270
					Direction.DOWN -> 90
					else -> 0
				}

				ConfiguredModel
					.builder()
					.modelFile(
						models()
							.orientable(
								name,
								otherTexture,
								uniqueTexture,
								otherTexture
							)
					)
					.rotationY(yRotation)
					.rotationX(xRotation)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/$name"),
				existingFileHelper
			)
		)
	}

	private fun rainbowLamp() {
		val block = ModBlocks.RAINBOW_LAMP.get()

		getVariantBuilder(block)
			.forAllStates {
				val colorInt = it.getValue(RainbowLampBlock.COLOR)
				val colorString = when (colorInt) {
					0 -> "white"
					1 -> "light_gray"
					2 -> "gray"
					3 -> "black"
					4 -> "red"
					5 -> "orange"
					6 -> "yellow"
					7 -> "lime"
					8 -> "green"
					9 -> "light_blue"
					10 -> "cyan"
					11 -> "blue"
					12 -> "purple"
					13 -> "magenta"
					14 -> "pink"
					15 -> "brown"
					else -> error("Invalid color")
				}

				ConfiguredModel
					.builder()
					.modelFile(
						models().cubeAll(
							"${name(block)}_$colorString",
							modLoc("block/rainbow_lamp/$colorString")
						)
					)
					.build()
			}

		simpleBlockItem(
			block,
			ItemModelBuilder(
				modLoc("block/rainbow_lamp_white"),
				existingFileHelper
			)
		)
	}

	private fun singleTextureTranslucentBlocks() {
		val singleTextureTranslucentBlocks = listOf(
			ModBlocks.BLOCK_OF_STICKS,
			ModBlocks.RETURNING_BLOCK_OF_STICKS,
			ModBlocks.LAPIS_GLASS,
			ModBlocks.QUARTZ_GLASS,
			ModBlocks.SUPER_LUBRICANT_ICE,
			ModBlocks.SPECTRE_BLOCK,
			ModBlocks.SPECTRE_CORE
		).map(DeferredBlock<*>::get)

		for (block in singleTextureTranslucentBlocks) {
			val model = models()
				.cubeAll(name(block), blockTexture(block))
				.renderType(RenderType.translucent().name)

			simpleBlockWithItem(block, model)
		}
	}

	private fun singleTextureBlocks() {
		val singleTextureBlocks = listOf(
			ModBlocks.SUPER_LUBRICANT_STONE,
			ModBlocks.SPECTRE_PLANKS,
//			ModBlocks.SOUND_DAMPENER,
			ModBlocks.REDSTONE_OBSERVER,
			ModBlocks.ENTITY_DETECTOR,
			ModBlocks.BEAN_POD,
			ModBlocks.ENDER_ANCHOR,
			ModBlocks.NATURE_CORE,
			ModBlocks.BASIC_REDSTONE_INTERFACE,
			ModBlocks.ADVANCED_REDSTONE_INTERFACE
		).map { it.get() }

		for (block in singleTextureBlocks) {
			simpleBlockWithItem(block, cubeAll(block))
		}
	}

	private fun name(block: Block): String {
		return BuiltInRegistries.BLOCK.getKey(block).path
	}

}