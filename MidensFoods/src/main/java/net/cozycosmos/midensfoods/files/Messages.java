package net.cozycosmos.midensfoods.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {

    private static File file;
    private static FileConfiguration messages;

    //find or makes the foodvalues file
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MidensFoods").getDataFolder(), "messages.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();

            }catch (IOException e) {

            }

        }

        messages = YamlConfiguration.loadConfiguration(file);
        addDefaults();

    }

    public static FileConfiguration get() {
        return messages;
    }

    public static void save() {
        try {
            messages.save(file);
        }catch (IOException e) {
            System.out.println("messages.yml couldn't be saved");
        }

    }

    public static void reload() {
        messages = YamlConfiguration.loadConfiguration(file);
    }

    public static void addDefaults() {

        get().addDefault("noPerm", "&cYou do not have permission!");
        get().addDefault("notRegistered", "&c Is not a registered food item!");
        get().addDefault("pleaseEnterItem", "&cPlease Enter a Fooditem!");
        get().addDefault("unrecognizedCommand", "&cUnrecognized command, do /cf for a list of commands");
        get().addDefault("CoreCommandShowHelp", "&fShow help menu\n");
        get().addDefault("CoreCommandGive", "&fGives you a custom fooditem\n");
        get().addDefault("CoreCommandReload", "&fReloads the plugin\n");
        get().addDefault("GiveCommandGivingItem", "&aGiving you Fooditem ");
        get().addDefault("ReloadCommandReloading", "&aReloading Miden's Foods...");
        get().addDefault("ReloadCommandLag", "&aBe warned, this will cause a few seconds of lag!");
        get().addDefault("ReloadCommandFinished", "&aMiden's Foods has been reloaded!");
        get().options().copyDefaults(true);
    }
}
