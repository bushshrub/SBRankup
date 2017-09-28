package net.derpz.sbrankup;

/*
 * Created by Robert on 06-Sep-17.
 */

import net.derpz.sbrankup.commands.RankupCommand;
import net.derpz.sbrankup.commands.SBAdminCommand;
import net.derpz.sbrankup.commands.SetRankCommand;

import net.derpz.sbrankup.config.Rankups;
import net.derpz.sbrankup.config.Messages;

import net.derpz.sbrankup.nms.*;

import net.milkbowl.vault.chat.Chat;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;


public class SBRankup extends JavaPlugin {

    private Permission perms = null;
    private Chat chat = null;
    private String PluginPrefix = null;


    private ActionBar actionbar;
    private JsonMessage jsonMessage;
    @Override
    public void onEnable() {



        PluginManager plmgr = getServer().getPluginManager();

        Plugin asb = plmgr.getPlugin("ASkyBlock");
        ConsoleCommandSender console = getServer().getConsoleSender();

        PluginPrefix = ChatColor.translateAlternateColorCodes('&',
                getConfig().getString("plugin-prefix"));

        if (asb == null) {

            console.sendMessage(PluginPrefix + ChatColor.RED.toString() +
                    "ASkyBlock was NOT detected! Disabling plugin");
            plmgr.disablePlugin(this);
        }

        if (plmgr.getPlugin("PlaceholderAPI") == null) {
            console.sendMessage(PluginPrefix + ChatColor.RED.toString() +
                    "PlaceholderAPI was NOT detected! PlaceholderAPI hooks will " +
                    "be unavailable");
        }
        console.sendMessage(PluginPrefix + ChatColor.GREEN.toString() + "Linking to ASkyBlock!");

        if (!setupPermissions()) {
            console.sendMessage(PluginPrefix + ChatColor.RED.toString() +
                    "Can't link Vault for permissions! Disabling plugin.");
            plmgr.disablePlugin(this);
        }

        if (!setupChat()) {
            console.sendMessage(PluginPrefix + ChatColor.RED.toString() +
            "Can't link Vault for chat! Disabling plugin.");
            plmgr.disablePlugin(this);
        }

        if (!setupNMS()) {
            console.sendMessage(PluginPrefix + ChatColor.RED.toString() +
                    "Server version is incompatible with the NMS version in this plugin. Disabling. (Future releases " +
                    "will have more NMS support)");
            plmgr.disablePlugin(this);
        }

        saveDefaultConfig();

        Messages msgs = new Messages(this);
        msgs.saveDefault();

        Rankups.saveDefault();


        if (getConfig().getBoolean("enableMetrics")) {
            console.sendMessage(PluginPrefix + ChatColor.GREEN.toString() +
                    "You have chosen to enable metrics. Thank you for letting me know that you use this plugin!");
            Metrics metrics = new Metrics(this);
        } else {
            console.sendMessage(PluginPrefix + ChatColor.RED.toString() +
                    "You have chosen not to enable metrics. Metrics let me know that you support my plugin. However, " +
                    "this is your own choice. Please consider enabling it as it does not collect any sensitive " +
                    "information about your server.");
        }


        getCommand("sbrankup").setExecutor(new RankupCommand(this));
        getCommand("sbsetrank").setExecutor(new SetRankCommand(this));
        getCommand("sbadmin").setExecutor(new SBAdminCommand(this));
        getCommand("sbadmin").setTabCompleter(new SBAdminCommand(this));

        //getCommand("sblistranks").setExecutor(new RankListCommand(this)); //TODO GUI for ranklist


    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(PluginPrefix + ChatColor.GREEN.toString() +
                "SBRankup is now shutting down");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }


    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupNMS() {
        String version;

        try {
            version = getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException WeirdVersionE) {
            return false;
        }

        getServer().getConsoleSender().sendMessage(PluginPrefix + ChatColor.AQUA.toString() +
        "Now attempting NMS hook for version " + version);


        switch (version) {
            case "v1_12_R1":
                actionbar = new ActionBar_1_12_R1();
                jsonMessage = new JsonMessage_1_12_R1();
                break;
            case "v1_11_R1":
                actionbar = new ActionBar_1_11_R1();
                jsonMessage = new JsonMessage_1_11_R1();
                break;
            case "v1_10_R1":
                actionbar = new ActionBar_1_10_R1();
                jsonMessage = new JsonMessage_1_10_R1();
                break;
            case "v1_9_R2":
                actionbar = new ActionBar_1_9_R2();
                jsonMessage = new JsonMessage_1_9_R2();
                break;
            case "v1_9_R1":
                actionbar = new ActionBar_1_9_R1();
                jsonMessage = new JsonMessage_1_9_R1();
                break;
        }
        return jsonMessage != null;
    }


    public String replacePlaceholder(CharSequence placeholder, String textToReplaceIn, String inputText) {

        return inputText.replace(placeholder, textToReplaceIn);
    }

    public Permission getPerms() { return perms; }

    public Chat getChat() { return chat; }

    public String getPluginPrefix() { return PluginPrefix; }

    public ActionBar getActionbar() { return actionbar; }
}
