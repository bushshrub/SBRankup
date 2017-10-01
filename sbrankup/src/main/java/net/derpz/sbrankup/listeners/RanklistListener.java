package net.derpz.sbrankup.listeners;

import net.derpz.sbrankup.SBRankup;
import net.derpz.sbrankup.commands.RankListCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xiurobert on 01-Oct-17.
 */
public class RanklistListener implements Listener{

    private SBRankup plugin;

    public RanklistListener(SBRankup plugin) {
        this.plugin = plugin;

    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ItemStack clicked = event.getCurrentItem();
        Inventory inv = event.getInventory();


        if (inv.getName().equals(new RankListCommand(plugin).getRankListInv().getName())) {
            event.setCancelled(true);
            player.closeInventory();

            // TODO: Implement commands
        }
    }
}
