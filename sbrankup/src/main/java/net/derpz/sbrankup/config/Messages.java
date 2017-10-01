package net.derpz.sbrankup.config;

import net.derpz.sbrankup.SBRankup;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by xiurobert on 18-Sep-17.
 */
public class Messages {

    private SBRankup plugin;
    private File messages = null;
    private FileConfiguration msgs = null;

    public Messages(SBRankup plugin) {
        this.plugin = plugin;
    }

    public void saveDefault() {
        if (messages == null) {
            messages = new File(plugin.getDataFolder(), "messages_en.yml");
        }
        if (!messages.exists()) {
            plugin.saveResource("messages_en.yml", false);
        }
    }

    public FileConfiguration getMessages() {
        if (msgs == null) {
            reloadmsgs();
        }
        return msgs;
    }

    public String getAltColourCodedMsg(String path) {
        if (msgs == null) {
            reloadmsgs();
        }

        return ChatColor.translateAlternateColorCodes('&', msgs.getString(path));
    }

    public void reloadmsgs() {
        saveDefault();

        msgs = YamlConfiguration.loadConfiguration(messages);
        try {
            Reader defmsgs = new InputStreamReader(plugin.getResource("messages_en.yml"), "UTF8");
            msgs.setDefaults(YamlConfiguration.loadConfiguration(defmsgs));


        } catch (UnsupportedEncodingException Uee) {
            plugin.getServer().getConsoleSender().sendMessage(
                    plugin.getPluginPrefix() + ChatColor.RED.toString() +
                            "Plugin error: msgs.yml not encoded in UTF8. Please report this to the developer");

        }


    }


}
