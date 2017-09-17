package net.derpz.sbrankup.rankupmanager;

import net.derpz.sbrankup.SBRankup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by xiurobert on 17-Sep-17.
 */
public class PermissionsEditor {

    private SBRankup plugin;
    private List<String> allowedOperations = Arrays.asList(
            "addgroup", "removegroup", "addpermission", "removepermission");
    
    
    public PermissionsEditor(SBRankup plugin) {
        this.plugin = plugin;
    }
    public void worldPerms(String world, Player targetPlayer, String action, String permission) {
        if (allowedOperations.contains(action)) {
            switch (action) {
                
                    case "addgroup":
                        plugin.getPerms().playerAddGroup(world,
                                Bukkit.getOfflinePlayer(targetPlayer.getUniqueId()), permission);
                        break;
                    case "removegroup":
                        plugin.getPerms().playerRemoveGroup(world,
                                Bukkit.getOfflinePlayer(targetPlayer.getUniqueId()), permission);
                        break;
                    case "addpermission":
                        plugin.getPerms().playerAdd(world,
                                Bukkit.getOfflinePlayer(targetPlayer.getUniqueId()), permission);
                        break;
                    case "removepermission":
                        plugin.getPerms().playerRemove(world,
                                Bukkit.getOfflinePlayer(targetPlayer.getUniqueId()), permission);
                        break;
                    default:
                        plugin.getServer().getConsoleSender().sendMessage(
                                plugin.getPluginPrefix() + ChatColor.RED + "Rankups are" +
                                        "configured incorrectly! Please check your rankups.yml! The error is: "+
                                        "Not a permission edit!");

                        targetPlayer.sendMessage(plugin.getPluginPrefix() + ChatColor.RED +
                                "There seems to be an error ranking up. If this problem persists, " +
                                "contact your server administrator");
                        break;
            }
        }

    }
    
    public void nonWorldPerms(String action, Player targetPlayer, String permission) {
        switch (action) {
            case "addgroup":
                plugin.getPerms().playerAddGroup(targetPlayer, permission);
                break;
            case "removegroup":
                plugin.getPerms().playerRemoveGroup(targetPlayer, permission);
                break;

            case "addpermission":
                plugin.getPerms().playerAdd(targetPlayer, permission);
                break;
            case "removepermission":
                plugin.getPerms().playerRemove(targetPlayer, permission);
                break;

            default:
                plugin.getServer().getConsoleSender().sendMessage(
                        plugin.getPluginPrefix() + ChatColor.RED + "Rankups are" +
                                "configured incorrectly! Please check your rankups.yml");

                targetPlayer.sendMessage(plugin.getPluginPrefix() + ChatColor.RED +
                                "There seems to be an error ranking up. If this problem persists, " +
                                "contact your server administrator");
                break;
        }
    }

    public List<String> getAllowedOperations() {
        return allowedOperations;
    }
}
