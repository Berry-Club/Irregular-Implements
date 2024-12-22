package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.*
import dev.aaronhowser.mods.irregular_implements.block.plate.DirectionalAcceleratorPlateBlock
import dev.aaronhowser.mods.irregular_implements.block.plate.RedirectorPlateBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.client.model.generators.ConfiguredModel
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder
import net.neoforged.neoforge.common.data.ExistingFileHelper

class ModBlockStateProvider(
    output: PackOutput,
    private val existingFileHelper: ExistingFileHelper
) : BlockStateProvider(output, IrregularImplements.ID, existingFileHelper) {

    override fun registerStatesAndModels() {
        singleTextureBlocks()
        singleTextureTransparentBlocks()
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
        val inactiveModel = models()
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
                val modelName = name(block) + "_" + facing.name.lowercase()

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
                    .from(0f, 0f, 0f)
                    .to(16f, 16f, 16f)
                    .textureAll("#all")
                    .end()

                    .element()
                    .from(-0.01f, -0.01f, -0.01f)
                    .to(16.01f, 16.01f, 16.01f)
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
                    .from(0f, 0f, 0f)
                    .to(16f, height, 16f)
                    .textureAll("#all")
                    .end()

                    .element()
                    .from(2f, innerBottom, 2f)
                    .to(14f, innerTop, 14f)
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
                        .from(0f, 0f, 0f)
                        .to(16f, 15f, 16f)
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
                    val facing = it.getValue(DirectionalBlock.FACING)
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
                .from(0f, 0f, 0f)
                .to(16f, 16f, 16f)
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
            val model = models()
                .cross(name(block), blockTexture(block))
                .renderType(RenderType.cutout().name)
            simpleBlockWithItem(block, model)
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
            val block = ModBlocks.getColoredGrass(color).get()

            val model = models()
                .withExistingParent(name(block), mcLoc("block/grass_block"))
                .renderType(RenderType.cutout().name)

            simpleBlockWithItem(block, model)
        }

    }

    private fun luminousBlocks() {

        for (color in DyeColor.entries) {

            val opaqueBlock = ModBlocks.getLuminousBlock(color).get()
            val translucentBlock = ModBlocks.getLuminousBlockTranslucent(color).get()

            val opaqueTexture = modLoc("block/luminous_block/$color")
            val translucentTexture = modLoc("block/luminous_block/translucent/$color")

            val opaqueModel = models()
                .withExistingParent(name(opaqueBlock), mcLoc("block/block"))
                .texture("all", opaqueTexture)
                .texture("particle", translucentTexture)

                .element()
                .cube("#all")
                .ao(false)
                .emissivity(15, 15)
                .end()

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

            simpleBlockWithItem(opaqueBlock, opaqueModel)
            simpleBlockWithItem(translucentBlock, translucentModel)

        }
    }

    private fun stainedBricks() {

        for (color in DyeColor.entries) {

            val regular = ModBlocks.getStainedBrick(color).get()
            val luminous = ModBlocks.getStainedBrickLuminous(color).get()

            val regularTexture = modLoc("block/stained_bricks/$color")
            val luminousBaseTexture = modLoc("block/luminous_stained_brick/base/$color")
            val luminousTintTexture = modLoc("block/luminous_stained_brick/tint/$color")

            val regularModel = models()
                .cubeAll(name(regular), regularTexture)

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

            simpleBlockWithItem(regular, regularModel)
            simpleBlockWithItem(luminous, luminousModel)
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
                .from(0f, 15f, 0f)
                .to(16f, 16f, 16f)
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
    }

    private fun oneUniqueFace(
        block: Block,
        uniqueTexture: ResourceLocation,
        otherTexture: ResourceLocation
    ) {
        val name = name(block)

        getVariantBuilder(block)
            .forAllStates {
                val facing = it.getValue(DirectionalBlock.FACING)

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

    private fun singleTextureTransparentBlocks() {
        val singleTextureTransparentBlocks = listOf(
            ModBlocks.BLOCK_OF_STICKS,
            ModBlocks.RETURNING_BLOCK_OF_STICKS,
            ModBlocks.LAPIS_GLASS,
            ModBlocks.QUARTZ_GLASS,
            ModBlocks.SUPER_LUBRICANT_ICE
        ).map { it.get() }

        for (block in singleTextureTransparentBlocks) {
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
            ModBlocks.SOUND_DAMPENER,
            ModBlocks.REDSTONE_OBSERVER,
            ModBlocks.ENTITY_DETECTOR,
            ModBlocks.BEAN_POD,
            ModBlocks.ENDER_ANCHOR,
            ModBlocks.FLUID_DISPLAY,
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