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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 * Created by xiurobert on 08-Sep-17.
 */
public class RankListCommand implements CommandExecutor {

    private SBRankup plugin;
    private Rankups rus;
    public Inventory rankListInv;

    public RankListCommand(SBRankup plugin) {
        this.plugin = plugin;
        this.rus = new Rankups(plugin);
        this.rankListInv = Bukkit.createInventory(null,
                (int) Math.ceil(this.rus.getRanks().size() / 9) * 9,
                ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("ranklist.name")));
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            sender = (Player) sender;

            String[] ranks = this.rus.getRanks().toArray(new String[this.rus.getRanks().size()]);
            for (int i = 0; i <= this.rus.getRanks().size(); i++) {
                if (sender.hasPermission("sbrankup.rank." + ranks[i])) {
                    Material hasPermsMaterial = Material.getMaterial(plugin.getConfig().getString("ranklist.unlocked-item.item"));
                    ItemStack hasPermsItemStack = new ItemStack
                            (hasPermsMaterial, 1, (short)
                    plugin.getConfig().getInt("ranklist.unlocked-item.item-data"));

                    ItemMeta itemMeta = hasPermsItemStack.getItemMeta();
                    if (plugin.getConfig().getString("ranklist.unlocked-item.item-display-name") != null) {
                        itemMeta.setDisplayName(plugin.getConfig().getString("ranklist.unlocked-item.item-display-name"));
                    }
                    if (plugin.getConfig().getStringList("ranklist.unlocked-item.item-lore") != null) {
                        itemMeta.setLore(plugin.getConfig().getStringList("ranklist.unlocked-item.item-lore"));
                    }

                    if (plugin.getConfig().getStringList("ranklist.unlocked-item.enchantments") != null) {
                        for (String ench : plugin.getConfig().getStringList("ranklist.unlocked-item.item-enchantments")) {
                            String[] enchMt = ench.split(";");
                            itemMeta.addEnchant(Enchantment.getByName(enchMt[0]), Integer.valueOf(enchMt[1]), true);
                        }
                    }
                    hasPermsItemStack.setItemMeta(itemMeta);
                    rankListInv.setItem(i, hasPermsItemStack);


                } else {
                    Material noPermsMaterial = Material.getMaterial(plugin.getConfig().getString("ranklist.locked-item.item"));
                    ItemStack noPermsStack = new ItemStack(noPermsMaterial, 1, (short)
                            plugin.getConfig().getInt("ranklist.locked-item.item-data"));

                    ItemMeta itemMeta = noPermsStack.getItemMeta();

                    if (plugin.getConfig().getString("ranklist.locked-item.item-display-name") != null) {
                        itemMeta.setDisplayName(plugin.getConfig().getString("ranklist.unlocked-item.item-display-name"));
                    }

                    if (plugin.getConfig().getStringList("ranklist.locked-item.item-lore") != null) {
                        itemMeta.setLore(plugin.getConfig().getStringList("ranklist.locked-item.item-lore"));
                    }


                    if (plugin.getConfig().getStringList("ranklist.locked-item.enchantments") != null) {
                        for (String ench : plugin.getConfig().getStringList("ranklist.locked-item.item-enchantments")) {
                            String[] enchMt = ench.split(";");
                            itemMeta.addEnchant(Enchantment.getByName(enchMt[0]), Integer.valueOf(enchMt[1]), true);
                        }
                    }

                    noPermsStack.setItemMeta(itemMeta);
                    rankListInv.setItem(i, noPermsStack);
                }

            }

            ((Player) sender).openInventory(rankListInv);
        }

        return false;
    }
}
