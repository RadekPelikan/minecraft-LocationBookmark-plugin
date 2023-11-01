package org.locationbookmark.Config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.locationbookmark.LocationBookmark;

import java.io.File;

public class Config {

    private LocationBookmark plugin;
    private YamlConfiguration configYaml;
    private File configFile;


    public Config(LocationBookmark plugin) {
        this.plugin = plugin;
        this.configYaml = loadConfig("config.yml");
    }

    private YamlConfiguration loadConfig(String file_name) {

        if(!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }

        this.configFile = new File(this.plugin.getDataFolder(), file_name);

        if (!this.configFile.exists()) {
            this.plugin.saveResource(file_name, false);
        }

        return YamlConfiguration.loadConfiguration(this.configFile);
    }

    public String getRootCommand() {
        return configYaml.getString("root_command");
    }

    public boolean getEnabledTeleportation() {
        return configYaml.getBoolean("enable_teleportation");
    }

}
