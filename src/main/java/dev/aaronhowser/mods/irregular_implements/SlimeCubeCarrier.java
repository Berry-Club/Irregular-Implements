package dev.aaronhowser.mods.irregular_implements;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public interface SlimeCubeCarrier {

	default LongOpenHashSet irregular_implements$getSlimeCubePositions() {
		throw new IllegalStateException();
	}


}
