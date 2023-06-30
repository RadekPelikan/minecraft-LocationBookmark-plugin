package org.locationbookmark.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.locationbookmark.Location;

public class LOCLIST implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("locList") && args.length == 0) {
            Player player = (Player) sender;
            sender.sendMessage(ChatColor.YELLOW + "Your locations:");
            for(Location location : org.locationbookmark.DB.getAllLocations(player.getDisplayName())) {
                sender.sendMessage(ChatColor.YELLOW + "  " + location.getLocationName() + " at " + location.getX() + ", " + location.getY() + ", " + location.getZ());
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid command");
        }
        return true;
    }
}
