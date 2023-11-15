package org.locationbookmark;

import org.bukkit.plugin.java.JavaPlugin;
import org.locationbookmark.Commands.GOTO;
import org.locationbookmark.Commands.LOCLIST;
import org.locationbookmark.Commands.RMLOC;
import org.locationbookmark.Commands.SV;
import org.locationbookmark.Config.Config;

public final class LocationBookmark extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        DB.initialize();
        setupConfig();
        registerCommands();
    }

    private void setupConfig() {
        config = new Config(this);
    }

    private void registerCommands() {
        getCommand("sv").setExecutor(new SV());
        getCommand("goto").setExecutor(new GOTO());
        getCommand("rmloc").setExecutor(new RMLOC());
        getCommand("locList").setExecutor(new LOCLIST());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
