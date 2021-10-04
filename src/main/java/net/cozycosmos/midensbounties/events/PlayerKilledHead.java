package net.cozycosmos.midensbounties.events;

import net.cozycosmos.midensbounties.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerKilledHead implements Listener {
    private int bounty;
    private final Main plugin = Main.getPlugin(Main.class);
    File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
    public FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
    FileConfiguration config = plugin.getConfig();
    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
        bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
        bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
            if(p.getUniqueId().toString().equals(uuid) && p.getKiller() != null) {
                Player killer = p.getKiller();
                bounty = bountiesdata.getInt("bounties."+uuid+".bounty");
                if (bounty >= 1) {
                    if(!bountiesdata.getBoolean("bounties." + p.getUniqueId().toString() + ".headdropped")) {
                        ItemStack playerskull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                        SkullMeta meta = (SkullMeta) playerskull.getItemMeta();
                        ArrayList<String> lore = new ArrayList<String>();

                        meta.setOwningPlayer(p);
                        meta.setDisplayName(p.getName() + "'s Head");
                        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                        lore.add(ChatColor.GOLD + "Right-click in your hand to claim the bounty!");
                        lore.add(ChatColor.GOLD + "Bounty: $" + bounty);
                        meta.setLore(lore);
                        playerskull.setItemMeta(meta);
                        bountiesdata.set("bounties." + p.getUniqueId().toString() + ".headdropped", true);
                        e.getDrops().add(playerskull);
                        e.setDeathMessage(p.getDisplayName() + ChatColor.RED + " died with a bounty of $" + bounty);
                    } else {
                        killer.sendMessage(ChatColor.RED + "This bounty was taken by someone else, but they haven't claimed it!");
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
