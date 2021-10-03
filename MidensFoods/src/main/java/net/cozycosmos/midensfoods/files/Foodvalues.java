package net.cozycosmos.midensfoods.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Foodvalues {

	private static File file;
	private static FileConfiguration foodvalues;
	
	//find or makes the foodvalues file
	public static void setup() {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("MidensFoods").getDataFolder(), "foodvalues.yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();

			}catch (IOException e) {
				
			}
		}
		
		foodvalues = YamlConfiguration.loadConfiguration(file);
		addDefaults();

	}
	
	public static FileConfiguration get() {
		return foodvalues;
	}
	
	public static void save() {
		try {
			foodvalues.save(file);
		}catch (IOException e) {
			System.out.println("foodvalues.yml couldn't be saved");
		}
		
	}
	
	public static void reload() {
		foodvalues = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void addDefaults() {

		get().addDefault("APPLE", 4);
		get().addDefault("BAKED_POTATO", 5);
		get().addDefault("BEETROOT", 1);
		get().addDefault("BEETROOT_SOUP", 6);
		get().addDefault("BREAD", 5);
		get().addDefault("CARROT", 3);
		get().addDefault("CHORUS_FRUIT", 4);
		get().addDefault("COOKED_CHICKEN", 6);
		get().addDefault("COOKED_COD", 5);
		get().addDefault("COOKED_MUTTON", 6);
		get().addDefault("COOKED_PORKCHOP", 8);
		get().addDefault("COOKED_RABBIT", 5);
		get().addDefault("COOKED_SALMON", 6);
		get().addDefault("COOKED_BEEF", 8);
		get().addDefault("COOKIE", 2);
		get().addDefault("DRIED_KELP", 1);
		get().addDefault("ENCHANTED_GOLDEN_APPLE", 4);
		get().addDefault("GOLDEN_APPLE", 4);
		get().addDefault("GOLDEN_CARROT", 4);
		get().addDefault("MUSHROOM_STEW", 4);
		get().addDefault("MELON_SLICE", 2);
		get().addDefault("POISONOUS_POTATO", 2);
		get().addDefault("POTATO", 1);
		get().addDefault("PUFFERFISH", 1);
		get().addDefault("PUMPKIN_PIE", 8);
		get().addDefault("RABBIT_STEW", 10);
		get().addDefault("BEEF", 3);
		get().addDefault("CHICKEN", 2);
		get().addDefault("COD", 2);
		get().addDefault("MUTTON", 2);
		get().addDefault("PORKCHOP", 3);
		get().addDefault("RABBIT", 3);
		get().addDefault("SALMON", 2);
		get().addDefault("ROTTEN_FLESH", 4);
		get().addDefault("SPIDER_EYE", 2);
		get().addDefault("SWEET_BERRIES", 2);
		get().addDefault("TROPICAL_FISH", 1);
		get().addDefault("GLOW_BERRIES", 2);
		get().options().copyDefaults(true);
	}
}
