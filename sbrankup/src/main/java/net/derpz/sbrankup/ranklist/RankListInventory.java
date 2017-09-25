package net.derpz.sbrankup.ranklist;

import net.derpz.sbrankup.SBRankup;
import net.derpz.sbrankup.config.Rankups;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xiurobert on 25-Sep-17.
 */
public class RankListInventory {
    private static SBRankup plugin;
    public RankListInventory(SBRankup plugin) {
        RankListInventory.plugin = plugin;
    }
    public static Inventory rankListInv = Bukkit.createInventory(null,
            (int) Math.ceil(Rankups.getRanks().size() / 9) * 9,
            ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("ranklist.name")));

    static {
        for (int i = 0; i <= Rankups.getRanks().size(); i++) {
            ItemStack obj = new ItemStack(Material.getMaterial(plugin.getConfig().getString("ranklist.unlockedItem")));
        }
    }
}
