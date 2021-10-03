package net.cozycosmos.midensfoods.events;


import net.cozycosmos.midensfoods.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Craftblocker implements Listener{
    private final Main plugin = Main.getPlugin(Main.class);
    FileConfiguration config = plugin.getConfig();  //Accessing the config file
    @EventHandler
    public void Craft(PrepareItemCraftEvent c) {
        if (c.getInventory().getSize() == 10) {
            ItemStack air = new ItemStack(Material.AIR);
            for (int i = 1; c.getInventory().getContents().length > i; i++) {
                ItemStack is = c.getInventory().getContents()[i];
                if (is.hasItemMeta()) {
                    ItemMeta meta = is.getItemMeta();
                    if (meta.getDisplayName().contains(config.getString("CraftblockKey"))) {
                        c.getInventory().setResult(air);
                    }
                } else {
                    //do nothing
                }
            }
        }
    }
}