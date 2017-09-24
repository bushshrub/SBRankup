package net.derpz.sbrankup;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import net.derpz.sbrankup.config.Rankups;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by xiurobert on 22-Sep-17.
 */
public class Placeholders extends EZPlaceholderHook {
    private SBRankup plugin;

    public Placeholders(SBRankup plugin) {
        super(plugin, "sbrankup");
        this.plugin = plugin;

    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) {
            return "";
        }

        switch (identifier) {
            case "rank":
                return Rankups.getRankOfPlayer(p);
            case "next_rank":
                return Rankups.getNextRank(p);
            case "next_rank_cost":
                return Rankups.getRankups().getString(
                        "rankups." + Rankups.getNextRank(p) +".cost");
            case "next_rank_difference":
                int islandLevel = ASkyBlockAPI.getInstance().getIslandLevel(p.getUniqueId());
                int needed = Rankups.getRankups().getInt("rankups." + Rankups.getNextRank(p) + ".cost");
                return (needed - islandLevel) + "";

        }

        return null;
    }

}


