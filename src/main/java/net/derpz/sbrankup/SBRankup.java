package net.derpz.sbrankup;

/**
 * Created by Robert on 06-Sep-17.
 */

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import net.derpz.sbrankup.commands.RankListCommand;
import net.derpz.sbrankup.commands.RankupCommand;

import net.derpz.sbrankup.commands.SetRankCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class SBRankup extends JavaPlugin {

    public Permission perms = null;
    private FileConfiguration rankups = null;
    private File rankupsYml= null;
    public String PluginPrefix = ChatColor.GRAY + "["+ ChatColor.GREEN + "SBR" + ChatColor.GRAY + "] ";

    @Override
    public void onEnable() {

        PluginManager plmgr = getServer().getPluginManager();

        Plugin asb = plmgr.getPlugin("ASkyBlock");
        ConsoleCommandSender console = getServer().getConsoleSender();

        if (asb == null) {

            console.sendMessage(PluginPrefix + ChatColor.RED.toString() + "ASkyBlock was NOT detected. " +
                    "Disabling plugin");
            plmgr.disablePlugin(this);
        } else {
            console.sendMessage(PluginPrefix + ChatColor.GREEN.toString() + "Linking to ASkyBlock!");

            if (!setupPermissions()) {
                console.sendMessage(PluginPrefix + ChatColor.RED.toString() + "Can't link Vault for permissions! Disabling" +
                        "plugin");
                plmgr.disablePlugin(this);
            } else {
                this.saveDefaultConfig();
                getRankups();

                this.getCommand("sbrankup").setExecutor(new RankupCommand(this));
                this.getCommand("sbsetrank").setExecutor(new SetRankCommand(this));
                this.getCommand("sblistranks").setExecutor(new RankListCommand(this)); //TODO GUI for ranklist
                plmgr.registerEvents(new RankupCommand(this), this);
            }
        }


    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(PluginPrefix + ChatColor.GREEN.toString() + "" +
                "SBRankup is now shutting down");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public void reloadRankups() {
        if (rankupsYml == null) {
            rankupsYml = new File(getDataFolder(), "rankups.yml");
        }

        rankups = YamlConfiguration.loadConfiguration(rankupsYml);
        try {
            Reader defRankups = new InputStreamReader(getResource("rankups.yml"), "UTF8");
            rankups.setDefaults(YamlConfiguration.loadConfiguration(defRankups));

        } catch (UnsupportedEncodingException Uee) {
            getServer().getConsoleSender().sendMessage(
                    PluginPrefix + ChatColor.RED.toString() + "Plugin error: rankups.yml not encoded in UTF8. Please" +
                    "report this to the developer");

        }


    }

    public FileConfiguration getRankups() {
        if (rankups == null) {
            reloadRankups();
        }
        return rankups;
    }

    public void saveDefaultRankups() {
        if (rankupsYml == null) {
            rankupsYml = new File(getDataFolder(), "rankups.yml");
        }

        if (!rankupsYml.exists()) {
            this.saveResource("rankups.yml", false);
        }
    }
}
