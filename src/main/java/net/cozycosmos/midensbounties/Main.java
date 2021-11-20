package net.cozycosmos.midensbounties;
import net.cozycosmos.midensbounties.commands.CheckBounty;
import net.cozycosmos.midensbounties.commands.SetBounty;
import net.cozycosmos.midensbounties.events.PlayerClickOnHead;
import net.cozycosmos.midensbounties.events.PlayerKilledHead;
import net.cozycosmos.midensbounties.events.PlayerKilledStandard;
import net.cozycosmos.midensbounties.extras.Metrics;
import net.cozycosmos.midensbounties.extras.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public JavaPlugin instance;
    public PluginManager pm;
    public ConsoleCommandSender cs;

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    File bountiesYml = new File(getDataFolder()+"/bounties.yml");
    File messagesYml = new File(getDataFolder()+"/messages.yml");

    public void onEnable(){
        instance = this;

        pm = Bukkit.getServer().getPluginManager();
        cs = Bukkit.getServer().getConsoleSender();

        cs.sendMessage(ChatColor.GRAY+"[MidensBounties] "+ChatColor.GREEN + "Enabling Miden's Bounties...");
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
            cs.sendMessage(ChatColor.GRAY+"[MidensBounties] "+ChatColor.GREEN + "Making the data folder");
        }
        new UpdateChecker(this, 96070).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                cs.sendMessage(ChatColor.GRAY +"[MidensBounties] "+ChatColor.GREEN + "You're running the latest version!");
            } else {
                cs.sendMessage(ChatColor.GRAY +"[MidensBounties] "+ChatColor.GREEN + "There's a new update available!");
                cs.sendMessage(ChatColor.GRAY +"[MidensBounties] "+ChatColor.GREEN + "You're running version "+this.getDescription().getVersion()+ " While the latest version is "+version+"!");
            }
        });

        registerCommands();
        registerEvents();
        registerConfig();
        if(!bountiesYml.exists()){
            this.saveResource("bounties.yml", false);
        }else{
            // do nothing
        }
        if(!messagesYml.exists()){
            this.saveResource("messages.yml", false);
        }else{
            // do nothing
        }
        cs.sendMessage(ChatColor.GRAY+"[MidensBounties] "+ChatColor.GREEN + "Events, Commands, and configs registered");

        int pluginId = 12732;
        Metrics metrics = new Metrics(this, pluginId);

        cs.sendMessage(ChatColor.GRAY+"[MidensBounties] "+ChatColor.GREEN + "Miden's Bounties Enabled");

    }
    public void onDisable(){
        cs.sendMessage(ChatColor.GRAY+"[MidensBounties] "+ChatColor.RED + "Miden's Bounties Disabled");
    }

    public void registerEvents() {
        if(getConfig().getBoolean("Head-Drops")){
            pm.registerEvents(new PlayerKilledHead(), this);
            pm.registerEvents(new PlayerClickOnHead(), this);

        } else {
            pm.registerEvents(new PlayerKilledStandard(), this);
        }


    }

    public void registerCommands(){
        getCommand("setbounty").setExecutor(new SetBounty());
        getCommand("bounty").setExecutor(new CheckBounty());
    }

    public void registerConfig() {
        getConfig().options().copyDefaults();
        this.saveDefaultConfig();
    }



    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }


}
