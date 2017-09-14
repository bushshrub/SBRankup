package net.derpz.sbrankup.commands;

import net.derpz.sbrankup.SBRankup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xiurobert on 08-Sep-17.
 */
public class SetRankCommand implements CommandExecutor {
    private SBRankup plugin;
    public SetRankCommand(SBRankup plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(this.plugin.PluginPrefix + ChatColor.RED.toString() +
                    "You need to provide 2 inputs to this command!");
            sender.sendMessage(this.plugin.PluginPrefix + ChatColor.YELLOW +
            "Usage: " + cmd.getName() + " <player> <rank>");
            return false;
        } else {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                // player online
                sender.sendMessage("Nothing yet");
            } else {
                sender.sendMessage("Nothing yet");
            }
            sender.sendMessage(this.plugin.PluginPrefix + ChatColor.GREEN.toString() +
                    "Set the rank of " + args[0] + "to: " + args[1]);
            return true;
        }
    }
}
