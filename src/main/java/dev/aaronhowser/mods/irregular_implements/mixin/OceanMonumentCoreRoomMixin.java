package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.block.SpecialChestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OceanMonumentPieces.OceanMonumentCoreRoom.class)
abstract class OceanMonumentCoreRoomMixin {

	//FIXME: Why does this run multiple times for the same room??
	@Inject(method = "postProcess",
			at = @At("TAIL")
	)
	private void irregular_implements$addOceanChest(
			WorldGenLevel level,
			StructureManager structureManager,
			ChunkGenerator generator,
			RandomSource random,
			BoundingBox box,
			ChunkPos chunkPos,
			BlockPos pos,
			CallbackInfo ci
	) {
		SpecialChestBlock.addToOceanMonument(
				level,
				random,
				box,
				(OceanMonumentPieces.OceanMonumentCoreRoom) (Object) this
		);
	}

}
