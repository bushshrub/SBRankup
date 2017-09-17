package net.derpz.sbrankup.commands;

import net.derpz.sbrankup.SBRankup;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by xiurobert on 10-Sep-17.
 */
public class SBAdminCommand implements CommandExecutor {

    private SBRankup plugin;

    public SBAdminCommand(SBRankup plugin) {
        this.plugin = plugin;
    }


    @Override

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "[ SBRankup by DerpZ ]");

            sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "/sbadmin reload" +
                    ChatColor.GREEN +": reloads configuration from file");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(plugin.getPluginPrefix() + ChatColor.AQUA + "Reloading Config");


            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + ChatColor.GREEN +
                    "Reloading " +
                    "config.yml");
            plugin.reloadConfig();
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + ChatColor.GREEN +
                    "Reloading " +
                    "rankups.yml");
            plugin.reloadRankups();

            return true;
        }
        return false;
    }
}
