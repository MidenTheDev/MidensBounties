package net.cozycosmos.midensbounties.events;

import net.cozycosmos.midensbounties.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;

public class PlayerClickOnHead implements Listener {

    private final Main plugin = Main.getPlugin(Main.class);
    File messagesYml = new File(plugin.getDataFolder()+"/messages.yml");
    FileConfiguration messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);

    @EventHandler
    public void OnClick(PlayerInteractEvent e) {
        messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);

        Player p = e.getPlayer();
        Action action = e.getAction();
        final Main plugin = Main.getPlugin(Main.class);
        File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
        FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);


        if (p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().hasEnchants() && p.getInventory().getItemInMainHand().getType() == Material.SKULL_ITEM) {
            if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                SkullMeta meta = (SkullMeta) e.getItem().getItemMeta();
                bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
                    if(meta.getOwningPlayer().getUniqueId().toString().equals(uuid) && p.getInventory().getItemInMainHand().hasItemMeta()){
                        Main.getEconomy().depositPlayer(p, Integer.parseInt(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(2).substring(2)));
                        p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        p.sendMessage(messagesconfig.getString("BountyClaimed").replace("&", "§"));

                    } else {
                        //do nothing
                    }
                });
            }
        } else {
            //do nothing
        }

    }
}