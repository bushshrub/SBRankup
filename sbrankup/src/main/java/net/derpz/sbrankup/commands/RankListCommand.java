package net.derpz.sbrankup.commands;

import net.derpz.sbrankup.Placeholders;
import net.derpz.sbrankup.SBRankup;


import net.derpz.sbrankup.config.Rankups;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xiurobert on 08-Sep-17.
 */
public class RankListCommand implements CommandExecutor {

    private SBRankup plugin;

    public RankListCommand(SBRankup plugin) {
        this.plugin = plugin;
    }

    public Inventory rankListInv = Bukkit.createInventory(null,
            (int) Math.ceil(Rankups.getRanks().size() / 9) * 9,
            ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("ranklist.name")));
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            sender = (Player) sender;

            String[] ranks = Rankups.getRanks().toArray(new String[Rankups.getRanks().size()]);
            for (int i = 0; i <= Rankups.getRanks().size(); i++) {
                if (sender.hasPermission("sbrankup.rank." + ranks[i])) {
                    ItemStack hasPermsItemStack = new ItemStack
                            (Material.getMaterial(plugin.getConfig().getString("ranklist.unlockedItem")));

                } else {
                    ItemStack noPermsStack = new ItemStack(Material.getMaterial(
                            plugin.getConfig().getString("ranklist.locked-item.name")
                    ));
                }

            }
        }

        return false;
    }
}
