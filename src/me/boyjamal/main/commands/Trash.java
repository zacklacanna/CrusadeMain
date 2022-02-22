package me.boyjamal.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.boyjamal.main.utils.MainUtils;

public class Trash implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		} else {
			Player p = (Player)sender;
			Inventory inv = Bukkit.createInventory(null, 45,MainUtils.chatColor("&r                &8&nTrash"));
			p.openInventory(inv);
		}
		return true;
	}

}
