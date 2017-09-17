package net.derpz.sbrankup.commands;


import net.derpz.sbrankup.SBRankup;

import net.derpz.sbrankup.rankupmanager.PermissionsEditor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by xiurobert on 06-Sep-17.
 */
public class RankupCommand implements CommandExecutor{

    private final SBRankup plugin;
    // More coming soon

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


            List<String> nr = new ArrayList<>(plugin.ranks);
            Collections.sort(nr, Collections.reverseOrder());
            LinkedHashSet<String> NrR = new LinkedHashSet<>(nr);

            if (sender.hasPermission("sbrankup.lastrank")) {
                sender.sendMessage(plugin.getPluginPrefix() + ChatColor.GOLD + "You are already " +
                        "on the last rank");
            }
            for (String currRank : NrR) {

                if (sender.hasPermission("sbrankup.rank." + currRank)) {
                    String nextRank = plugin.getRankups().getString("rankups." + currRank + ".nextrank");
                    // check if player has enough levels to rankup

                    if (plugin.getRankups().getInt("rankups." + nextRank + ".cost") <= asbapi.getIslandLevel(uuid)) {

                        // run the rankup actions
                        List<String> actions = plugin.getRankups().getStringList("rankups." + currRank + ".actions");

                        for (String action : actions) {
                            String[] actionToPerform = action.split("\\s+");
                            String action2P = actionToPerform[0].replace("<", "").
                                    replace(">", "");
                            PermissionsEditor p = new PermissionsEditor(plugin);
                            // Check if it is trying to change permissions
                            if (p.getAllowedOperations().contains(action2P)) {
                                if (actionToPerform.length == 3) {
                                    p.worldPerms(actionToPerform[2], (Player) sender, action2P, actionToPerform[1]);
                                } else if (actionToPerform.length == 2) {
                                    p.nonWorldPerms(action2P, (Player) sender, actionToPerform[1]);
                                }
                                // It is not, try other things
                            } else {

                                StringBuilder cmdAndArgs = new StringBuilder();
                                cmdAndArgs.append(actionToPerform[1]);
                                for (int i = 2; i < actionToPerform.length; i++) {
                                    cmdAndArgs.append(" ").append(actionToPerform[i]);
                                }

                                String execCmd = cmdAndArgs.toString().replace("%player_name%",
                                        ((Player) sender).getDisplayName());

                                switch (action2P) {

                                    case "consolecommand":
                                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                                                execCmd);
                                        break;
                                    case "playercommand":
                                        ((Player) sender).performCommand(execCmd);
                                        break;
                                    default:
                                        plugin.getServer().getConsoleSender().sendMessage(
                                                plugin.getPluginPrefix() + ChatColor.RED + "Rankups are " +
                                                        "configured incorrectly! Please check your rankups.yml. " +
                                                        "The error is: The action does not exist");
                                        sender.sendMessage(plugin.getPluginPrefix() + ChatColor.RED + "There " +
                                                "seems to be an error ranking up. If this problem persists, " +
                                                "contact your server administrator");
                                        break;

                                }
                            }

                        }
                        break;

                        // island level doesn't meet requirements
                    } else {
                        int missingLvls = asbapi.getIslandLevel(uuid) -
                                plugin.getRankups().getInt("rankups." + nextRank + ".cost");
                        sender.sendMessage(plugin.getPluginPrefix() + ChatColor.RED + "You're island level is" +
                                " too low to rankup. You need " + missingLvls + "more to rankup. [" +
                                asbapi.getIslandLevel(uuid) + "of " +
                                plugin.getRankups().getInt("rankups." + nextRank + ".cost") + "levels needed. " +
                                "If you believe that you should be able to rankup, please run /is level and run this " +
                                "command again");
                    }
                    break;
                }

            }
            return true;

        } else {

            sender.sendMessage(plugin.getPluginPrefix()
                    + ChatColor.RED.toString() + "You must be a player to use " +
                    "this command. If you are running this from the console, " +
                    "use sbsetrank instead!");
            return true;
        }

    }
}
