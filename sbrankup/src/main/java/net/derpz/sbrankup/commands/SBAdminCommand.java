package net.derpz.sbrankup.commands;

import net.derpz.sbrankup.SBRankup;
import net.derpz.sbrankup.config.Messages;
import net.derpz.sbrankup.config.Rankups;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiurobert on 10-Sep-17.
 */
public class SBAdminCommand implements CommandExecutor, TabCompleter{

    private SBRankup plugin;
    private List<String> functionalities = Arrays.asList("reload");
    private Messages msgs2 = new Messages(plugin);

    public SBAdminCommand(SBRankup plugin) {
        this.plugin = plugin;
    }


    @Override

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("sbrankup.admin.reload")) {
            sender.sendMessage(plugin.getPluginPrefix() + ChatColor.translateAlternateColorCodes('&',
                    msgs2.getMessages().getString("noPermission")));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "[ SBRankup by DerpZ ]");

            sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "/sbadmin reload" +
                    ChatColor.GREEN +": reloads configuration from file");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(plugin.getPluginPrefix() + ChatColor.AQUA + "Reloading Config");
            long startTime = System.currentTimeMillis();

            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + ChatColor.GREEN +
                    "Reloading config.yml");
            plugin.reloadConfig();
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + ChatColor.GREEN +
                    "Reloading rankups.yml");
            Rankups rankups = new Rankups(plugin);
            rankups.reloadRankups();
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + ChatColor.GREEN +
                    "Reloading messages_en.yml");
            Messages msgs = new Messages(plugin);
            msgs.reloadmsgs();
            long elapsedTime = System.currentTimeMillis() - startTime;
            sender.sendMessage(plugin.getPluginPrefix() + ChatColor.AQUA +
                    "Completed reload! (" + elapsedTime + "ms)");

            return true;
        }
        return false;
    }

    @Override

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return functionalities;
    }
}
