package dev.aaronhowser.mods.irregular_implements;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public interface PeaceCandleCarrier {

	default LongOpenHashSet irregular_implements$getPeaceCandleChunks() {
		throw new IllegalStateException();
	}

}
