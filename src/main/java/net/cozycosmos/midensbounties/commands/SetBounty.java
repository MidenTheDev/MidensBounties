package net.cozycosmos.midensbounties.commands;

import net.cozycosmos.midensbounties.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetBounty implements CommandExecutor {
    private final Main plugin = Main.getPlugin(Main.class);
    File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
    FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
    FileConfiguration config = plugin.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
        if (cmd.getName().equals("setbounty")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "You must specify a player!");
                } else {
                    if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                        if (isInt(args[1])) {
                            if (Main.getEconomy().getBalance(p) >= Integer.parseInt(args[1])) {
                                try {
                                    double bountystart = bountiesdata.getInt("bounties." + target.getUniqueId().toString() + ".bounty");
                                    Main.getEconomy().withdrawPlayer(p, Integer.parseInt(args[1]));
                                    bountiesdata.set("bounties." + p.getUniqueId().toString() + ".headdropped", false);
                                    bountiesdata.set("bounties." + target.getUniqueId().toString() + ".bounty", (bountystart + Math.round((config.getDouble("TaxPercent")*Integer.parseInt(args[1])))));
                                    bountiesdata.save(bountiesYml);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if(config.getInt("TaxPercent")!=1) {
                                    sender.sendMessage(ChatColor.GREEN + "Your bounty has been placed. "+Math.round(((1-config.getDouble("TaxPercent"))*100))+"% has been taken as a tax.");
                                } else {
                                    sender.sendMessage(ChatColor.GREEN + "Your bounty has been placed.");
                                }

                            } else {
                                sender.sendMessage(ChatColor.RED + "You don't have enough money!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "You must specify an amount!");
                        }

                    } else {
                        sender.sendMessage(ChatColor.RED + "Make sure the player's name is spelled properly!");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            }
        }



        return true;
    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
