package org.locationbookmark;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.locationbookmark.Commands.GOTO;
import org.locationbookmark.Commands.LOCLIST;
import org.locationbookmark.Commands.RMLOC;
import org.locationbookmark.Commands.SV;

public final class LocationBookmark extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        DB.connect();
        registerCommands();
    }

    private void registerCommands() {
        getCommand("sv").setExecutor(new SV());
        getCommand("goto").setExecutor(new GOTO());
        getCommand("rmloc").setExecutor(new RMLOC());
        getCommand("locList").setExecutor(new LOCLIST());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
