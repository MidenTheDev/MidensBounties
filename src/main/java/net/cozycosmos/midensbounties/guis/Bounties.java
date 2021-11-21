package net.cozycosmos.midensbounties.guis;

import net.cozycosmos.midensbounties.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Bounties implements Listener {
    private int number = 0;
    private ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
    private final Main plugin = Main.getPlugin(Main.class);

    private List<ItemStack> heads;
    private List<Integer> bounties;
    private List<ItemStack> sortedHeads;

    private Location loc;

    FileConfiguration config = plugin.getConfig();

    File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
    FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);

    File messagesYml = new File(plugin.getDataFolder()+"/messages.yml");
    FileConfiguration messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);
    public static Inventory bountiesGUI = Bukkit.createInventory(null, 54, "Open Bounties:");

    public void fillInventory() {
        bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
            if (number == bountiesGUI.getSize()) {

            } else {

                if(bountiesdata.getInt("bounties." + uuid + ".bounty") == 0) {

                } else {
                    SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                    skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(bountiesdata.getString("bounties."+uuid+".username")));
                    skullMeta.setDisplayName(ChatColor.BLUE + bountiesdata.getString("bounties." + uuid + ".username"));
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add(messagesconfig.getString("SkullLoreLine2").replace("&", "§")+ChatColor.GOLD+" $"+bountiesdata.getInt("bounties." + uuid + ".bounty"));
                    lore.add(messagesconfig.getString("CompassTrackerLore").replace("&", "§"));
                    lore.add(messagesconfig.getString("CompassTrackerLore2").replace("&", "§"));
                    skullMeta.setLore(lore);
                    head.setItemMeta(skullMeta);
                    //heads.add(head);
                    //bounties.add(bountiesdata.getInt("bounties." + uuid + ".bounty"));

                    bountiesGUI.setItem(number, head);
                    number++;
                }

            }

        });
        /*Collections.sort(bounties, Collections.reverseOrder());
        for (int i = 0; i < heads.size(); i++) {
            ItemStack curHead = heads.get(i);
            for (int a = 0; a < bounties.size(); a++) {
                int curBounty = bounties.get(a);
                if (curBounty == Integer.parseInt(curHead.getItemMeta().getLore().get(0).substring(messagesconfig.getString("SkullLoreLine2").length()+4))) {
                    sortedHeads.add(curHead);
                    break;
                }
            }
        }
        for (int i = 0; i <= 54; i++) {
            bountiesGUI.setItem(i, sortedHeads.get(i));
        }*/
    }



    @EventHandler
    public void guiClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        if (event.getView().getTitle().equals("Open Bounties:")) {
            if(clicked == null) {

            } else {
                if (clicked.getType() == Material.SKULL_ITEM) {
                    loc = player.getLocation();
                    loc.setX(bountiesdata.getInt("bounties."+Bukkit.getOfflinePlayer(clicked.getItemMeta().getDisplayName().substring(2)).getUniqueId().toString()+".lastKnownLocationX"));
                    loc.setY(bountiesdata.getInt("bounties."+Bukkit.getOfflinePlayer(clicked.getItemMeta().getDisplayName().substring(2)).getUniqueId().toString()+".lastKnownLocationY"));
                    loc.setZ(bountiesdata.getInt("bounties."+Bukkit.getOfflinePlayer(clicked.getItemMeta().getDisplayName().substring(2)).getUniqueId().toString()+".lastKnownLocationZ"));
                    if(config.getBoolean("TrackCompass")) {
                        player.setCompassTarget(loc);
                    }
                    event.setCancelled(true);
                    player.closeInventory();
                    if(config.getBoolean("GiveCompass")) {
                        player.getInventory().addItem(new ItemStack(Material.COMPASS));
                    } else {

                    }
                    player.sendMessage(messagesconfig.getString("HeadClickedGUI").replace("&", "§")+clicked.getItemMeta().getDisplayName());
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        bountiesdata.set("bounties."+e.getPlayer().getUniqueId().toString()+".lastKnownLocationX", e.getPlayer().getLocation().getBlockX());
        bountiesdata.set("bounties."+e.getPlayer().getUniqueId().toString()+".lastKnownLocationY", e.getPlayer().getLocation().getBlockY());
        bountiesdata.set("bounties."+e.getPlayer().getUniqueId().toString()+".lastKnownLocationZ", e.getPlayer().getLocation().getBlockZ());
        try {
            bountiesdata.save(bountiesYml);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

}

