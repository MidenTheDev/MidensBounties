package net.cozycosmos.midensbounties.extras;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.cozycosmos.midensbounties.MidensBounties;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class PlaceHolderAPI extends PlaceholderExpansion {

    private final MidensBounties plugin;

    public PlaceHolderAPI(MidensBounties plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "midensbounties";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("bounty")){
            boolean bountyfound = false;
            AtomicInteger bounty = new AtomicInteger();

            File bountiesYml = new File(plugin.getDataFolder()+"/bounties.yml");
            FileConfiguration bountiesdata = YamlConfiguration.loadConfiguration(bountiesYml);

            bountiesdata.getConfigurationSection("bounties").getKeys(false).forEach(uuid -> {
                if (player.getUniqueId().toString().equals(uuid)) {
                    bounty.set(bountiesdata.getInt("bounties." + uuid + ".bounty"));
                }
            });

            return "$" + bounty;
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
