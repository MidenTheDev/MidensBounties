package net.cozycosmos.midensbounties.events;

import net.cozycosmos.midensbounties.MidensBounties;
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
    private final MidensBounties plugin = MidensBounties.getPlugin(MidensBounties.class);
    File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
    public FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
    File messagesYml = new File(plugin.getDataFolder()+"/messages.yml");
    FileConfiguration messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
        bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
        messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);
        bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
            if(p.getUniqueId().toString().equals(uuid) && p.getKiller() != null) {
                Player killer = p.getKiller();
                bounty = bountiesdata.getInt("bounties."+uuid+".bounty");
                if (bounty >= 1) {
                    try {
                        bountiesdata.set("bounties." + p.getUniqueId().toString() + ".bounty", 0);
                        bountiesdata.save(bountiesYml);
                        MidensBounties.getEconomy().depositPlayer(killer, bounty);
                        e.setDeathMessage(p.getDisplayName() + ChatColor.RED + " died with a bounty of $" + bounty);
                        killer.sendMessage(messagesconfig.getString("BountyAmountRecieved").replace("&", "§") + bounty);
                        bounty = 0;
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
