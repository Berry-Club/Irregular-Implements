package dev.aaronhowser.mods.irregular_implements;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public interface RainShieldCarrier {

	default LongOpenHashSet irregular_implements$getRainShieldChunks() {
		throw new IllegalStateException();
	}

}
