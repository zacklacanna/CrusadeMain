package me.boyjamal.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.boyjamal.main.utils.MainUtils;
import me.kvq.supertrailspro.API.SuperTrailsAPI;
import me.kvq.supertrailspro.wings.WingsManager;
import me.kvq.supertrailspro.wings.WingsMenu;

public class Wings implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		} else {
			if (args.length == 0 || args.length >= 2)
			{
				WingsMenu.WingsSelector((Player)sender);
				return true;
			} else {
				if (args[0].equalsIgnoreCase("off"))
				{
					//turn wings off
					if (SuperTrailsAPI.getPlayerData((Player)sender).getWings().length >= 1)
					{
						SuperTrailsAPI.getPlayerData((Player)sender).resetWings();
					}
				} else {
					sender.sendMessage(MainUtils.chatColor("&c&lERROR &7Usage: /wings off"));
				}
			}
			return true;
		}
	}

}
