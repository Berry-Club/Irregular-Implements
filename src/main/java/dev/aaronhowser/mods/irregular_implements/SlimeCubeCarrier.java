package dev.aaronhowser.mods.irregular_implements;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public interface SlimeCubeCarrier {

	@SuppressWarnings("unused")    // it lies, it's called in SlimeCubeBlockEntity
	default LongOpenHashSet irregular_implements$getSlimeCubeBlockPositions() {
		throw new IllegalStateException();
	}


}
