package net.cozycosmos.midensbounties.commands;

import net.cozycosmos.midensbounties.guis.Bounties;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyTracker implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equals("bounties")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (sender.hasPermission("midensbounties.bounties")) {
                    Bounties bounties = new Bounties();
                    bounties.fillInventory();
                    p.openInventory(Bounties.bountiesGUI);
                }
            }
        }
        return true;
    }
}
