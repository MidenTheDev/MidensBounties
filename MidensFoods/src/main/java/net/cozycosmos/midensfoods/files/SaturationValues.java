package net.cozycosmos.midensfoods.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SaturationValues {

    private static File file;
    private static FileConfiguration satvalues;

    //find or makes the satvalues file
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MidensFoods").getDataFolder(), "satvalues.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();

            }catch (IOException e) {

            }
        }

        satvalues = YamlConfiguration.loadConfiguration(file);
        addDefaults();

    }

    public static FileConfiguration get() {
        return satvalues;
    }

    public static void save() {
        try {
            satvalues.save(file);
        }catch (IOException e) {
            System.out.println("satvalues.yml couldn't be saved");
        }

    }

    public static void reload() {
        satvalues = YamlConfiguration.loadConfiguration(file);
    }

    public static void addDefaults() {

        get().addDefault("APPLE", 2);
        get().addDefault("BAKED_POTATO", 6);
        get().addDefault("BEETROOT", 1);
        get().addDefault("BEETROOT_SOUP", 7);
        get().addDefault("BREAD", 6);
        get().addDefault("CARROT", 3);
        get().addDefault("CHORUS_FRUIT", 2);
        get().addDefault("COOKED_CHICKEN", 7);
        get().addDefault("COOKED_COD", 6);
        get().addDefault("COOKED_MUTTON", 9);
        get().addDefault("COOKED_PORKCHOP", 13);
        get().addDefault("COOKED_RABBIT", 6);
        get().addDefault("COOKED_SALMON", 9);
        get().addDefault("COOKED_BEEF", 13);
        get().addDefault("COOKIE", 1);
        get().addDefault("DRIED_KELP", 1);
        get().addDefault("ENCHANTED_GOLDEN_APPLE", 9);
        get().addDefault("GOLDEN_APPLE", 9);
        get().addDefault("GOLDEN_CARROT", 14);
        get().addDefault("MUSHROOM_STEW", 7);
        get().addDefault("MELON_SLICE", 1);
        get().addDefault("POISONOUS_POTATO", 1);
        get().addDefault("POTATO", 0);
        get().addDefault("PUFFERFISH", 0);
        get().addDefault("PUMPKIN_PIE", 5);
        get().addDefault("RABBIT_STEW", 12);
        get().addDefault("BEEF", 2);
        get().addDefault("CHICKEN", 1);
        get().addDefault("COD", 0);
        get().addDefault("MUTTON", 1);
        get().addDefault("PORKCHOP", 2);
        get().addDefault("RABBIT", 2);
        get().addDefault("SALMON", 0);
        get().addDefault("ROTTEN_FLESH", 1);
        get().addDefault("SPIDER_EYE", 3);
        get().addDefault("SWEET_BERRIES", 0);
        get().addDefault("TROPICAL_FISH", 0);
        get().addDefault("GLOW_BERRIES", 1);
        get().options().copyDefaults(true);
    }
}
