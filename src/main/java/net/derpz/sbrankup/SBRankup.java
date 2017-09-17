package net.derpz.sbrankup;

/*
 * Created by Robert on 06-Sep-17.
 */

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import net.derpz.sbrankup.commands.RankListCommand;
import net.derpz.sbrankup.commands.RankupCommand;

import net.derpz.sbrankup.commands.SBAdminCommand;
import net.derpz.sbrankup.commands.SetRankCommand;
import net.derpz.sbrankup.listeners.IslandLevelListener;
import net.milkbowl.vault.chat.Chat;
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
import java.util.HashSet;
import java.util.Set;

public class SBRankup extends JavaPlugin {

    private Permission perms = null;
    private FileConfiguration rankups = null;
    private File rankupsYml= null;
    private String PluginPrefix = null;

    public Set<String> ranks = new HashSet<>();

    @Override
    public void onEnable() {



        PluginManager plmgr = getServer().getPluginManager();

        Plugin asb = plmgr.getPlugin("ASkyBlock");
        ConsoleCommandSender console = getServer().getConsoleSender();

        PluginPrefix = ChatColor.translateAlternateColorCodes('&',
                getConfig().getString("plugin-prefix"));

        if (asb == null) {

            console.sendMessage(PluginPrefix + ChatColor.RED.toString() + " ASkyBlock was NOT detected. " +
                    "Disabling plugin");
            plmgr.disablePlugin(this);
        } else {
            console.sendMessage(PluginPrefix + ChatColor.GREEN.toString() + " Linking to ASkyBlock!");

            if (!setupPermissions()) {
                console.sendMessage(PluginPrefix + ChatColor.RED.toString() + " Can't link Vault for permissions!" +
                        " Disabling plugin");
                plmgr.disablePlugin(this);
            } else {
                saveDefaultConfig();
                getRankups();



                getCommand("sbrankup").setExecutor(new RankupCommand(this));
                getCommand("sbsetrank").setExecutor(new SetRankCommand(this));
                getCommand("sbadmin").setExecutor(new SBAdminCommand(this));
                //getCommand("sblistranks").setExecutor(new RankListCommand(this)); //TODO GUI for ranklist


                new IslandLevelListener(this);
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
        saveDefaultRankups();

        rankups = YamlConfiguration.loadConfiguration(rankupsYml);
        try {
            Reader defRankups = new InputStreamReader(getResource("rankups.yml"), "UTF8");
            rankups.setDefaults(YamlConfiguration.loadConfiguration(defRankups));

            for (Object e : getRankups().getKeys(true)) {
                if (e.toString().contains(".")) {
                    String segs[] = e.toString().split("\\.");

                    // plugin.getLogger().info(segs[1]);
                    ranks.add(segs[1]);

                }

            }

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
            saveResource("rankups.yml", false);
        }
    }


    public Permission getPerms() {
        return perms;
    }

    public String getPluginPrefix() { return PluginPrefix; }
}
