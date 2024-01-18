package elocindev.item_obliterator.fabric_quilt;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elocindev.item_obliterator.fabric_quilt.config.ConfigEntries;
import elocindev.item_obliterator.fabric_quilt.plugin.FDCompatibility;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;

public class ItemObliterator implements ModInitializer {
	public static final String MODID = "item_obliterator";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	public static ConfigEntries Config = ConfigEntries.INSTANCE;

	public static Set<String> blacklisted_items;
	public static Set<String> blacklisted_potions;
	public static Set<String> only_disable_interactions;
	public static Set<String> only_disable_attacks;
    public static Set<String> only_disable_recipes;

	@Override
	public void onInitialize() {
		// renaming old pre 1.5.0 file (item-obliterator.json) to new file (item_obliterator.json)
		Path oldFile = FabricLoader.getInstance().getConfigDir().resolve("item-obliterator.json");
		
		if (oldFile.toFile().exists()) 
			oldFile.toFile().renameTo(FabricLoader.getInstance().getConfigDir().resolve("item_obliterator.json5").toFile());

		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
			NecConfigAPI.registerConfig(ConfigEntries.class);
			Config = ConfigEntries.INSTANCE;
			
			if (ItemObliterator.Config.use_hashmap_optimizations)
            	ItemObliterator.reloadConfigCache();
		});
	
		LOGGER.info("Item Obliterator Config Loaded");
		FDCompatibility.init();
	}

	public static void reloadConfigCache() {
		blacklisted_items = new HashSet<>(ItemObliterator.Config.blacklisted_items);
		blacklisted_potions = new HashSet<>(ItemObliterator.Config.blacklisted_potions);
		only_disable_interactions = new HashSet<>(ItemObliterator.Config.only_disable_interactions);
		only_disable_attacks = new HashSet<>(ItemObliterator.Config.only_disable_attacks);
		only_disable_recipes = new HashSet<>(ItemObliterator.Config.only_disable_recipes);
	}
}
