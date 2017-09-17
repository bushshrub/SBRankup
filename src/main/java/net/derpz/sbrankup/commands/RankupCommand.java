package net.derpz.sbrankup.commands;


import net.derpz.sbrankup.SBRankup;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.Iterator;
import java.util.UUID;

/**
 * Created by xiurobert on 06-Sep-17.
 */
public class RankupCommand implements CommandExecutor{

    private final SBRankup plugin;

    /**
     * RankupCommand initialiser
     * @param plugin SBRankup plugin
     */
    public RankupCommand(SBRankup plugin) {
        this.plugin = plugin;
    }

    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (sender instanceof Player) {

                if (args.length != 0) {
                    sender.sendMessage(plugin.getPluginPrefix() +
                            ChatColor.RED.toString() + ChatColor.BOLD + "You shouldn't give any" +
                            "inputs to this command!");
                    return true;
                }

                UUID uuid = ((Player) sender).getUniqueId();
                ASkyBlockAPI asbapi = ASkyBlockAPI.getInstance();

                asbapi.calculateIslandLevel(uuid);
                sender.sendMessage("Island level:" + asbapi.getIslandLevel(uuid));
                //plugin.getLogger().info("hashset now");
                for (String s : plugin.ranks) {
                    //plugin.getLogger().info("Hashset:" + s);
                    if (sender.hasPermission("sbrankup." + s)) {
                        // Assume that sender is at that rank AND STOP THERE
                        String nextRank = plugin.getRankups().getString("sbrankup." + s + ".nextrank");
                        if (plugin.getRankups().getInt("sbrankup." + nextRank + ".cost")
                                == asbapi.getIslandLevel(uuid)) {


                            //plugin.getPerms().playerAddGroup()
                        }
                        break;
                    }
                }

                return true;
            } else {

                sender.sendMessage(plugin.getPluginPrefix()
                        + ChatColor.RED.toString() + "You must be a player to use " +
                        "this command. If you are running this from the console, " +
                        "use sbsetrank <player> instead!");
                return true;
            }

    }



}
