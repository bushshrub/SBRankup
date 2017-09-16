package net.derpz.sbrankup.commands;

import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.LevelCalcByChunk;
import com.wasteofplastic.askyblock.events.IslandPostLevelEvent;
import net.derpz.sbrankup.SBRankup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

/**
 * Created by xiurobert on 06-Sep-17.
 */
public class RankupCommand implements CommandExecutor, Listener {

    private final SBRankup plugin;

    public RankupCommand(SBRankup plugin) {
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (sender instanceof Player) {

                if (args.length != 0) {
                    sender.sendMessage(this.plugin.getPluginPrefix() +
                            ChatColor.RED.toString() + ChatColor.BOLD + "You shouldn't give any" +
                            "inputs to this command!");
                    return true;
                }

                UUID uuid = ((Player) sender).getUniqueId();
                ASkyBlockAPI asbapi = ASkyBlockAPI.getInstance();

                asbapi.calculateIslandLevel(uuid);
                sender.sendMessage("Island level:" + asbapi.getIslandLevel(uuid));

                return true;
            } else {
                sender.sendMessage(this.plugin.getPluginPrefix()
                        + ChatColor.RED.toString() + "You must be a player to use " +
                        "this command. If you are running this from the console, " +
                        "use sbsetrank <player> instead!");
                return true;
            }

    }



}
