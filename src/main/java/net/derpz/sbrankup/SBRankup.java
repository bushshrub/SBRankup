package net.derpz.sbrankup;

/*
 * Created by Robert on 06-Sep-17.
 */

import net.derpz.sbrankup.commands.RankupCommand;
import net.derpz.sbrankup.commands.SBAdminCommand;
import net.derpz.sbrankup.commands.SetRankCommand;

import net.derpz.sbrankup.config.Rankups;
import net.derpz.sbrankup.config.Messages;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;


public class SBRankup extends JavaPlugin {

    private Permission perms = null;

    private String PluginPrefix = null;

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
                setupPermissions();
                saveDefaultConfig();

                Messages msgs = new Messages(this);
                msgs.saveDefault();

                Rankups rankups = new Rankups(this);
                rankups.saveDefault();

                getCommand("sbrankup").setExecutor(new RankupCommand(this));
                getCommand("sbsetrank").setExecutor(new SetRankCommand(this));
                getCommand("sbadmin").setExecutor(new SBAdminCommand(this));
                getCommand("sbadmin").setTabCompleter(new SBAdminCommand(this));

                //getCommand("sblistranks").setExecutor(new RankListCommand(this)); //TODO GUI for ranklist


                // new IslandLevelListener(this);
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




    public Permission getPerms() {
        return perms;
    }

    public String getPluginPrefix() { return PluginPrefix; }

    public String replacePlaceholder(CharSequence placeholder, String textToReplaceIn, String inputText) {

        return inputText.replace(placeholder, textToReplaceIn);
    }
}
