package net.cozycosmos.midensbounties.commands;

import net.cozycosmos.midensbounties.MidensBounties;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CheckBounty implements CommandExecutor {
    private int bounty;
    private boolean bountyfound = false;
    private final MidensBounties plugin = MidensBounties.getPlugin(MidensBounties.class);
    File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
    FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);

    File messagesYml = new File(plugin.getDataFolder()+"/messages.yml");
    FileConfiguration messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);
        messagesconfig = YamlConfiguration.loadConfiguration(messagesYml);
        if (cmd.getName().equals("bounty")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length >= 1) {

                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    bountyfound = false;
                    bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
                        if (target.getUniqueId().toString().equals(uuid)) {
                            bountyfound = true;
                            bounty = bountiesdata.getInt("bounties." + uuid + ".bounty");
                            if (bounty >= 1) {
                                p.sendMessage(messagesconfig.getString("BountyOther").replace("&", "§") + bounty);
                            } else {
                                p.sendMessage(messagesconfig.getString("NoBountyOther").replace("&", "§"));
                            }

                        } else {

                        }
                    });
                    if (!bountyfound){
                        p.sendMessage(messagesconfig.getString("NoBountyOther").replace("&", "§"));
                    }
                } else {
                    bountyfound = false;
                    bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
                        if (p.getUniqueId().toString().equals(uuid)) {
                            bounty = bountiesdata.getInt("bounties." + uuid + ".bounty");
                            bountyfound = true;
                            if (bounty >= 1) {
                                p.sendMessage(messagesconfig.getString("BountySelf").replace("&", "§") + bounty);
                            } else {
                                p.sendMessage(messagesconfig.getString("NoBountySelf").replace("&", "§"));
                            }

                        } else {

                        }
                    });
                    if (bountyfound == false){
                        p.sendMessage(messagesconfig.getString("NoBountySelf").replace("&", "§"));
                    }
                }

            } else {
                sender.sendMessage(messagesconfig.getString("MustBeAPlayer").replace("&", "§"));
            }
        }
        return true;
    }



}
