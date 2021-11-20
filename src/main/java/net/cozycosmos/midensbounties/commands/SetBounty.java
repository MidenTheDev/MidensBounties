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
    File messagesYml = new File(plugin.getDataFolder()+"/messages.yml");
    FileConfiguration messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
        messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);
        if (cmd.getName().equals("setbounty")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (args.length == 0) {
                    sender.sendMessage(messagesconfig.getString("MustSpecifyAPlayer").replace("&", "§"));
                } else {
                    if(Bukkit.getOfflinePlayer(args[0]).equals((OfflinePlayer) sender)){}else{if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                        if (isInt(args[1]) && args.length >= 2) {
                            if (Main.getEconomy().getBalance(p) >= Integer.parseInt(args[1])) {
                                try {
                                    double bountystart = bountiesdata.getInt("bounties." + target.getUniqueId().toString() + ".bounty");
                                    Main.getEconomy().withdrawPlayer(p, Integer.parseInt(args[1]));
                                    bountiesdata.set("bounties." + target.getUniqueId().toString() + ".bounty", (bountystart + Math.round((config.getDouble("TaxPercent")*Integer.parseInt(args[1])))));
                                    bountiesdata.set("bounties." + target.getUniqueId().toString() + ".headdropped", false);
                                    bountiesdata.save(bountiesYml);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if(config.getInt("TaxPercent")!=1) {
                                    sender.sendMessage(messagesconfig.getString("BountyPlaced").replace("&", "§")+Math.round(((1-config.getDouble("TaxPercent"))*100))+messagesconfig.getString("TaxPercentTaken").replace("&", "§"));
                                } else {
                                    sender.sendMessage(messagesconfig.getString("BountyPlaced").replace("&", "§"));
                                }

                            } else {
                                sender.sendMessage(messagesconfig.getString("NotEnoughMoney").replace("&", "§"));
                            }
                        } else {
                            sender.sendMessage(messagesconfig.getString("MustSpecifyAmount").replace("&", "§"));
                        }

                    } else {
                        sender.sendMessage(messagesconfig.getString("PlayerNotFound").replace("&", "§"));
                    }}
                }
            } else {
                sender.sendMessage(messagesconfig.getString("MustBeAPlayer").replace("&", "§"));
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
