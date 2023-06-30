package org.locationbookmark.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.locationbookmark.DB;
import org.locationbookmark.Location;

public class GOTO implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("goto") && args.length == 1) {
            Player player = (Player) sender;
            String locationName = args[0];
            Location location = DB.getLocation(player.getDisplayName(), locationName);
            org.bukkit.Location currentLocation = player.getLocation();
            currentLocation.setY(location.getY());
            currentLocation.setX(location.getX());
            currentLocation.setZ(location.getZ());
            sender.sendMessage(ChatColor.YELLOW + "Teleporting to " + locationName + " at " + location.getX() + ", " + location.getY());
            player.teleport(currentLocation);
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid command");
        }
        return true;
    }
}
