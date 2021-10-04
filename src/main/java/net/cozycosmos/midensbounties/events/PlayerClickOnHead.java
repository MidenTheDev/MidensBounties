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
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;

public class PlayerClickOnHead implements Listener {

    @EventHandler
    public void OnClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        final Main plugin = Main.getPlugin(Main.class);
        File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
        FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);


        if (e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasEnchants() && e.getItem().getType().equals(Material.SKULL_ITEM)) {
            if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                SkullMeta meta = (SkullMeta) e.getItem().getItemMeta();
                bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
                    if(meta.getOwningPlayer().getUniqueId().equals(uuid)){
                        try {
                            Main.getEconomy().depositPlayer(p, bountiesdata.getInt("bounties." + uuid + ".bounty"));
                            bountiesdata.set("bounties." + uuid + ".bounty", 0);
                            bountiesdata.set("bounties." + uuid + ".headdropped", false);

                            bountiesdata.save(bountiesYml);
                            p.sendMessage(ChatColor.GREEN + "You claimed the bounty!");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
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
