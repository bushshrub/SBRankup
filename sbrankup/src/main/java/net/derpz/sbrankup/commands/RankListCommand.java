package net.derpz.sbrankup.commands;

import net.derpz.sbrankup.SBRankup;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by xiurobert on 08-Sep-17.
 */
public class RankListCommand implements CommandExecutor {

    private SBRankup plugin;

    public RankListCommand(SBRankup plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        return false;
    }
}
