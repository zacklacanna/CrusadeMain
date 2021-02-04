package me.boyjamal.main.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.boyjamal.main.utils.MainUtils;

public class Busy implements CommandExecutor {
	
	private static List<String> isBusy = new ArrayList<>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		if (p.hasPermission("crusademc.command.busy"))
		{
			String key = p.getUniqueId().toString();
			if (isBusy.contains(key))
			{
				Bukkit.broadcastMessage(MainUtils.chatColor("&d&l(&f&l!&d&l) &f&o" + p.getName() + " &7&ois no longer Busy!"));
				isBusy.remove(key);
				return true;
			} else {
				Bukkit.broadcastMessage(MainUtils.chatColor("&d&l(&f&l!&d&l) &f&o" + p.getName() + " &7&ois now Busy!"));
				isBusy.add(key);
				return true;
			}
		} else {
			p.sendMessage(MainUtils.chatColor("&c&n&lError&7 You do not have access to use &c&n/busy"));
			return true;
		}
	}
	
	public static List<String> getBusyPlayers()
	{
		return isBusy;
	}

}
