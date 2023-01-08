package net.cozycosmos.midensbounties.events;

import net.cozycosmos.midensbounties.MidensBounties;
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
    private final MidensBounties plugin = MidensBounties.getPlugin(MidensBounties.class);
    File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
    public FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
    FileConfiguration config = plugin.getConfig();
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
                    if(!bountiesdata.getBoolean("bounties." + p.getUniqueId().toString() + ".headdropped")) {
                        ItemStack playerskull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                        SkullMeta meta = (SkullMeta) playerskull.getItemMeta();
                        ArrayList<String> lore = new ArrayList<String>();

                        meta.setOwningPlayer(p);
                        meta.setDisplayName(p.getName() + "'s Head");
                        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                        lore.add(messagesconfig.getString("SkullLoreLine1").replace("&", "§"));
                        lore.add(messagesconfig.getString("SkullLoreLine2").replace("&", "§"));
                        lore.add(ChatColor.GOLD + ""+ bounty);
                        meta.setLore(lore);
                        playerskull.setItemMeta(meta);
                        e.getDrops().add(playerskull);
                        bountiesdata.set("bounties." + p.getUniqueId().toString() + ".headdropped", true);
                        e.setDeathMessage(p.getDisplayName() + messagesconfig.getString("DeathMessage").replace("&", "§") + bounty);
                        bountiesdata.set("bounties." + p.getUniqueId().toString() + ".bounty", 0);
                        bounty = 0;
                        try {
                            bountiesdata.save(bountiesYml);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        killer.sendMessage(messagesconfig.getString("HeadTakenButUnclaimed").replace("&", "§"));
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
