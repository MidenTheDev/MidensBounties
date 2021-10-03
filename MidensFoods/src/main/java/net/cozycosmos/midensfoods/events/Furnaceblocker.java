package net.cozycosmos.midensfoods.events;

import net.cozycosmos.midensfoods.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;



public class Furnaceblocker implements Listener {
    private final Main plugin = Main.getPlugin(Main.class);
    FileConfiguration config = plugin.getConfig();  //Accessing the config file

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            ItemStack item = e.getCurrentItem();
            InventoryType type = e.getInventory().getType();
            if (item.hasItemMeta()) {
                ItemMeta itemMeta = item.getItemMeta();
                String displayName = ChatColor.translateAlternateColorCodes('&', itemMeta.getDisplayName());
                if (displayName.contains(config.getString("CraftblockKey")))
                    switch (type) {
                        case FURNACE:
                                e.setCancelled(true);
                                break;

                        case SMOKER:
                           e.setCancelled(true);
                            break;
            }
        }
    }
}}

