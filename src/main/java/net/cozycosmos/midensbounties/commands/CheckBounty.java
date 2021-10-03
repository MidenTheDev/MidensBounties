package net.cozycosmos.midensbounties.commands;

import net.cozycosmos.midensbounties.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CheckBounty implements CommandExecutor {
    private int bounty;
    private int check;
    private final Main plugin = Main.getPlugin(Main.class);
    File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
    FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
        if (cmd.getName().equals("bounty")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
                    if (p.getUniqueId().toString().equals(uuid)) {
                        bounty = bountiesdata.getInt("bounties." + uuid + ".bounty");
                        if (bounty >= 1) {
                            p.sendMessage(ChatColor.GOLD + "Your current bounty is: $" + bounty);
                        } else {
                            p.sendMessage(ChatColor.GOLD + "You don't currently have a bounty!");
                        }

                    } else {
                        check++;
                    }
                });
                if (bountiesdata.getConfigurationSection("bounties").getKeys(false).size() == check){
                    p.sendMessage(ChatColor.GOLD + "You don't currently have a bounty!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            }
        }
        return true;
    }
}
