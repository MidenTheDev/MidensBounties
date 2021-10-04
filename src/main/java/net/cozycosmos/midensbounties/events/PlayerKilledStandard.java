package net.cozycosmos.midensbounties.events;

import net.cozycosmos.midensbounties.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.IOException;

public class PlayerKilledStandard implements Listener {
    private int bounty;
    private final Main plugin = Main.getPlugin(Main.class);
    File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
    public FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
        bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
        bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
            if(p.getUniqueId().toString().equals(uuid) && p.getKiller() != null) {
                Player killer = p.getKiller();
                bounty = bountiesdata.getInt("bounties."+uuid+".bounty");
                if (bounty >= 1) {
                    try {
                        bountiesdata.set("bounties." + p.getUniqueId().toString() + ".bounty", 0);
                        bountiesdata.save(bountiesYml);
                        Main.getEconomy().depositPlayer(killer, bounty);
                        e.setDeathMessage(p.getDisplayName() + ChatColor.RED + " died with a bounty of $" + bounty);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    } else {
                    //do nothing
                }
            } else {
                //do nothing
            }

        });

    }
}
