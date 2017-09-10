package net.derpz.sbrankup.commands;

import net.derpz.sbrankup.SBRankup;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by xiurobert on 10-Sep-17.
 */
public class ReloadConfig implements CommandExecutor {

    private SBRankup plugin;

    public ReloadConfig(SBRankup plugin) {
        this.plugin = plugin;
    }


    @Override

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        sender.sendMessage(ChatColor.YELLOW.toString() + "[SBRankup] " + ChatColor.AQUA + "Reloading Config");

        this.plugin.reloadConfig();

        return true;
    }
}
