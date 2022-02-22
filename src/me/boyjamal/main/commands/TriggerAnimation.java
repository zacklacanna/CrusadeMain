package me.boyjamal.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.boyjamal.main.utils.LevelUp;
import me.boyjamal.main.utils.MainUtils;

public class TriggerAnimation implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender.hasPermission("crusademc.admin")))
		{
			sender.sendMessage(MainUtils.chatColor("&c&LERROR &7&oYou can not access this!"));
			return true;
		}
		
		if (args.length == 2)
		{
			Player reciever;
			try {
				reciever = Bukkit.getPlayer(args[0]);
			} catch (Exception e) {
				sender.sendMessage(MainUtils.chatColor("&c&LERROR &7&o" + args[0] + " is not online!"));
				return true;
			}
			
			LevelUp.createAnimation(reciever,args[1],"Rankup");
		} else {
			sender.sendMessage(MainUtils.chatColor("&c&LERROR &7&o/triggeranimation <player> <level>"));
			return true;
		}
		return true;
	}

}
