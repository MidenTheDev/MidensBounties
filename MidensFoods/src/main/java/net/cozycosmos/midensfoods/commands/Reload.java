package net.cozycosmos.midensfoods.commands;

import net.cozycosmos.midensfoods.files.Foodvalues;
import net.cozycosmos.midensfoods.files.Messages;
import net.cozycosmos.midensfoods.files.SaturationValues;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;

import org.bukkit.command.CommandSender;

import net.cozycosmos.midensfoods.Main;


public class Reload{
	private Main plugin = Main.getPlugin(Main.class);
	public Server server;
	
	public void runCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
			sender.sendMessage(Messages.get().getString("ReloadCommandReloading").replace("&", "§"));
			plugin.getServer().getConsoleSender().sendMessage(Messages.get().getString("ReloadCommandReloading").replace("&", "§"));
			sender.sendMessage(Messages.get().getString("ReloadCommandLag").replace("&", "§"));
			reload();
			plugin.getServer().getConsoleSender().sendMessage(Messages.get().getString("ReloadCommandFinished").replace("&", "§"));
			sender.sendMessage(Messages.get().getString("ReloadCommandFinished").replace("&", "§"));


	}
	public void reload() {
		plugin.unloadRecipes();
		plugin.unloadEvents();
		plugin.reloadConfig();
		Foodvalues.reload();
		Messages.reload();
		SaturationValues.reload();
		plugin.registerRecipes();
		plugin.registerEvents();
	}
}