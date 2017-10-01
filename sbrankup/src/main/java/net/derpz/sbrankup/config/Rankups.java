package net.derpz.sbrankup.config;

import net.derpz.sbrankup.SBRankup;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by xiurobert on 18-Sep-17.
 */
public class Rankups {

    private SBRankup plugin;
    private Set<String> ranks = new HashSet<>();
    private File rankupsYml = null;
    private FileConfiguration rankups = null;


    public Rankups(SBRankup plugin) {
        this.plugin = plugin;
    }


    public void reloadRankups() {
        saveDefault();

        rankups = YamlConfiguration.loadConfiguration(rankupsYml);
        try {
            Reader defRankups = new InputStreamReader(plugin.getResource("rankups.yml"), "UTF8");
            rankups.setDefaults(YamlConfiguration.loadConfiguration(defRankups));

            for (Object e : getRankups().getKeys(true)) {
                if (e.toString().contains(".")) {
                    String segs[] = e.toString().split("\\.");
                    ranks.add(segs[1]);
                }

            }

        } catch (UnsupportedEncodingException Uee) {
            plugin.getServer().getConsoleSender().sendMessage(
                    plugin.getPluginPrefix() + ChatColor.RED.toString() + "Plugin error: rankups.yml not encoded in UTF8. Please" +
                            "report this to the developer");

        }


    }

    public FileConfiguration getRankups() {
        if (rankups == null) {
            reloadRankups();
        }
        return rankups;
    }

    public void saveDefault() {
        if (rankupsYml == null) {
            rankupsYml = new File(plugin.getDataFolder(), "rankups.yml");
        }

        if (!rankupsYml.exists()) {
            plugin.saveResource("rankups.yml", false);
        }
    }


    public Set<String> getRanks() {
        if (rankups == null) {
            reloadRankups();
        }
        return ranks;
    }

    public String getRankOfPlayer(Player p) {

        // Reverse the ranklist so that we check if they have the last rank first, and go down
        // This is better because some servers have inheritance
        // such that they don't remove the permission sbrankup.rank.[previous rank]
        // on rankup
        List<String> nr = new ArrayList<>(ranks);
        nr.sort(Collections.reverseOrder());
        LinkedHashSet<String> NrR = new LinkedHashSet<>(nr);
        for (String rank : NrR) {
            if (p.hasPermission("sbrankup.rank." + rank)) {
                return rank;
            }
        }
        return "";
    }

    public String getNextRank(Player p) {
        return getRankups().getString("rankups." + getRankOfPlayer(p) + ".nextrank");
    }

}
