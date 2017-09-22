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
                return Rankups.getRankups().getString("rankups." + Rankups.getRankOfPlayer(p) +
                ".nextrank");
            case "next_rank_cost":
                return Rankups.getRankups().getString(
                        "rankups." +
                                Rankups.getRankups().getString(
                                        "rankups." + Rankups.getRankOfPlayer(p) + ".nextrank") +".cost");
            case "next_rank_difference":
                int Needed = ASkyBlockAPI.getInstance().getIslandLevel(p.getUniqueId());
        }

        return null;
    }

}


