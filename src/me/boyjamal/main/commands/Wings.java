package me.boyjamal.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.kvq.supertrailspro.wings.WingsMenu;

public class Wings implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		} else {
			WingsMenu.WingsSelector((Player)sender);
			return true;
		}
	}

}
