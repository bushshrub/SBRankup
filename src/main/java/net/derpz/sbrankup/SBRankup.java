package net.derpz.sbrankup;

/**
 * Created by Robert on 06-Sep-17.
 */

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.util.VaultHelper;
import net.derpz.sbrankup.commands.RankupCommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SBRankup extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginManager plmgr = getServer().getPluginManager();

        Plugin asb = plmgr.getPlugin("ASkyBlock");

        if (asb == null) {
            getLogger().severe(ChatColor.RED.toString() + "ASkyBlock was NOT detected. " +
                    "Disabling plugin");
            plmgr.disablePlugin(this);
        } else {
            getLogger().info(ChatColor.GREEN.toString() + "Linking to ASkyBlock!");

            if (!VaultHelper.setupPermissions()) {
                getLogger().severe(ChatColor.RED.toString() + "Can't link Vault for permissions! Disabling" +
                        "plugin");
                plmgr.disablePlugin(this);
            }
        }

        ASkyBlockAPI a = ASkyBlockAPI.getInstance();

        this.getCommand("sbrankup").setExecutor(new RankupCommandExecutor(this));
        this.getCommand("sblistranks"); //TODO GUI for ranklist
    }

    @Override
    public void onDisable() {

    }
}
