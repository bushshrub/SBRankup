package net.derpz.sbrankup.commands;


import net.derpz.sbrankup.SBRankup;
import net.derpz.sbrankup.config.Messages;
import net.derpz.sbrankup.config.Rankups;
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


    /**
     * RankupCommand initialiser
     * @param plugin SBRankup plugin
     */
    public RankupCommand(SBRankup plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Messages msgs = new Messages(plugin);
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPluginPrefix()
                    + msgs.getAltColourCodedMsg("notAPlayer"));
            return true;
        }


        if (args.length != 0) {
            sender.sendMessage(plugin.getPluginPrefix() +
                    ChatColor.RED.toString() + ChatColor.BOLD + "You shouldn't give any" +
                    "inputs to this command!");
            return true;
        }

        UUID uuid = ((Player) sender).getUniqueId();
        ASkyBlockAPI asbapi = ASkyBlockAPI.getInstance();

        Rankups rus = new Rankups(plugin);

        // Reverse the ranklist so that we check if they have the last rank first, and go down
        // This is better because some servers have inheritance
        // such that they don't remove the permission sbrankup.rank.[previous rank]
        // on rankup
        List<String> nr = new ArrayList<>(rus.getRanks());
        nr.sort(Collections.reverseOrder());
        LinkedHashSet<String> NrR = new LinkedHashSet<>(nr);

        for (String currRank : NrR) {

            if (!sender.hasPermission("sbrankup.rank." + currRank)) {
                sender.sendMessage(plugin.getPluginPrefix() + msgs.getAltColourCodedMsg("noRankDetected"));
            }

            // Override if sender is on last rank
            if (currRank.equals("lastrank")) {
                sender.sendMessage(plugin.getPluginPrefix() + msgs.getAltColourCodedMsg("lastRank"));
            }
            String nextRank = rus.getRankups().getString("rankups." + currRank + ".nextrank");
            // check if player has enough levels to rankup

            if (rus.getRankups().getInt("rankups." + nextRank + ".cost")
                    <= asbapi.getIslandLevel(uuid)) {

                // run the rankup actions
                List<String> actions = rus.getRankups().getStringList(
                        "rankups." + currRank + ".actions");

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

                        String actionArgs = plugin.replacePlaceholder("%player_name%",
                                ((Player) sender).getDisplayName(), cmdAndArgs.toString());

                        switch (action2P) {

                            case "consolecommand":
                                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                                        actionArgs);
                                break;
                            case "playercommand":
                                ((Player) sender).performCommand(actionArgs);
                                break;

                            case "playermessage":
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', actionArgs));
                                break;

                            case "broadcastmessage":
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', actionArgs));
                                break;

                            case "playeractionbar":
                                plugin.getActionbar().sendActionBar((Player) sender,
                                        ChatColor.translateAlternateColorCodes('&', actionArgs));
                                break;

                            case "broadcastactionbar":
                                for (Player player : plugin.getServer().getOnlinePlayers()) {
                                    plugin.getActionbar().sendActionBar(player,
                                            ChatColor.translateAlternateColorCodes('&', actionArgs));
                                }
                                break;

                            case "setprefix":
                                plugin.getChat().setPlayerPrefix((Player) sender,
                                        ChatColor.translateAlternateColorCodes('&', actionArgs));
                                break;

                            case "setsuffix":

                                plugin.getChat().setPlayerSuffix((Player) sender,
                                        ChatColor.translateAlternateColorCodes('&', actionArgs));
                                break;


                            default:
                                plugin.getServer().getConsoleSender().sendMessage(
                                        plugin.getPluginPrefix() + msgs.getAltColourCodedMsg(
                                                "rankupsConfiguredIncorrectlyConsole"));
                                sender.sendMessage(plugin.getPluginPrefix() +
                                        msgs.getAltColourCodedMsg("rankupsConfiguredIncorrectlyToPlayer"));
                                break;

                        }
                    }

                }
                break;

                // island level doesn't meet requirements
            } else {

                int missingLvls = asbapi.getIslandLevel(uuid) -
                        rus.getRankups().getInt("rankups." + nextRank + ".cost");

                String msgToSend = plugin.replacePlaceholder("%curr_level%",
                        asbapi.getIslandLevel(uuid) + "",
                        msgs.getAltColourCodedMsg("notEnoughLevels"));

                msgToSend = plugin.replacePlaceholder(
                        "%levels_needed%", rus.getRankups().getInt("rankups." +
                                nextRank + ".cost")
                                + "", msgToSend);
                msgToSend = plugin.replacePlaceholder("%more_levels%", missingLvls + "",
                        msgToSend);

                sender.sendMessage(plugin.getPluginPrefix() + msgToSend);
            }



        }
        return true;



    }
}
