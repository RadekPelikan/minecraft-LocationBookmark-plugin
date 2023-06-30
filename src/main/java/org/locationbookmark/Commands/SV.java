package org.locationbookmark.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.locationbookmark.DB;

public class SV implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("sv") && args.length == 1) {
            Player player = (Player) sender;
            String locationName = args[0];
            double z = player.getLocation().getZ();
            double x = player.getLocation().getX();
            double y = player.getLocation().getY();
            if (DB.getLocation(player.getDisplayName(), locationName) == null) {
                sender.sendMessage(ChatColor.GREEN + "Saved location " + locationName + " at " + x + ", " + y);
                DB.addLocation(player.getDisplayName(), x, y, z, locationName);
            }else {
                sender.sendMessage(ChatColor.RED + "Location " + locationName + " already exists");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid command");
        }
        return true;
    }
}
