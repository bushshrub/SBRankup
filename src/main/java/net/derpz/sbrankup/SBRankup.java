package net.derpz.sbrankup;

/**
 * Created by Robert on 06-Sep-17.
 */

import net.derpz.sbrankup.Executors.RankupCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class SBRankup extends JavaPlugin {

    @Override
    public void onEnable() {

        this.getCommand("sbrankup").setExecutor(new RankupCommandExecutor(this));
    }

    @Override
    public void onDisable() {

    }
}
