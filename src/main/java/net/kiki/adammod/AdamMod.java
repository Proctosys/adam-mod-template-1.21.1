package net.kiki.adammod;

import net.fabricmc.api.ModInitializer;

import net.kiki.adammod.block.ModBlocks;
import net.kiki.adammod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdamMod implements ModInitializer {
	public static final String MOD_ID = "adammod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
	}
}