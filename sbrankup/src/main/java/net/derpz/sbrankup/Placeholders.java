package net.derpz.sbrankup;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import net.derpz.sbrankup.config.Rankups;
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
        
        Rankups rus = new Rankups(plugin);

        switch (identifier) {
            case "current_rank":
                return rus.getRankOfPlayer(p);
            case "next_rank":
                return rus.getNextRank(p);
            case "next_rank_cost":
                return rus.getRankups().getString(
                        "rankups." + rus.getNextRank(p) +".cost");
            case "next_rank_difference":
                int islandLevel = ASkyBlockAPI.getInstance().getIslandLevel(p.getUniqueId());
                int needed = rus.getRankups().getInt("rankups." + rus.getNextRank(p) + ".cost");
                return (needed - islandLevel) + "";
            case "last_rank_name":
                return rus.getRankups().getString("rankups.lastrank.name");

        }

        return null;
    }

}


