package org.locationbookmark.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.locationbookmark.DB;

public class RMLOC implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("rmloc") && args.length == 1) {
            Player player = (Player) sender;
            DB.deleteLocation(player.getDisplayName(), args[0]);
            sender.sendMessage(ChatColor.GREEN + "Deleted location " + args[0]);
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid command");
        }
        return true;
    }
}
