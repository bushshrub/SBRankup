package net.derpz.sbrankup.Executors;

import net.derpz.sbrankup.SBRankup;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Robert on 06-Sep-17.
 */
public class RankupCommandExecutor implements CommandExecutor {

    private final SBRankup plugin;

    public RankupCommandExecutor(SBRankup plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sbrankup")) {
            if (sender instanceof Player) {
                UUID uuid = ((Player) sender).getUniqueId();

                ASkyBlockAPI skyblockApi = new ASkyBlockAPI();
            }

            return true;
        }
    }

}
