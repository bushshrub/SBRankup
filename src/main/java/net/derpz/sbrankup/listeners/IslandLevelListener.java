package net.derpz.sbrankup.listeners;

import com.wasteofplastic.askyblock.events.IslandPostLevelEvent;
import net.derpz.sbrankup.SBRankup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class IslandLevelListener implements Listener{



    public IslandLevelListener(SBRankup plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onIslandLevelCalc(IslandPostLevelEvent e) {

        if (Bukkit.getOfflinePlayer(e.getPlayer()) != null && Bukkit.getOfflinePlayer(e.getPlayer()).isOnline()) {
            Bukkit.getPlayer(e.getPlayer()).sendMessage(ChatColor.GREEN.toString() + "Ok! Your island level" +
                    "was calculated, please run the command you had just ran again!");
        }


    }
}
