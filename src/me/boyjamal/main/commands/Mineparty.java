package me.boyjamal.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.MainUtils;

public class Mineparty implements CommandExecutor {

	private static boolean isActive = false;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender.hasPermission("crusademc.admin"))
		{
			if (isActive)
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"rg flag mineparty -w build block-break deny");
				Bukkit.broadcastMessage(MainUtils.chatColor("&d&o&lMineparty &7Mineparty has been disabled!"));
				setBarriers();
				isActive = false;
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"rg flag mineparty -w build block-break allow");
				Bukkit.broadcastMessage(MainUtils.chatColor("&d&o&lMineparty &7Mineparty has been enabled!"));
				setBlocks();
				isActive = true;
			}
		} else {
			sender.sendMessage(MainUtils.chatColor("&d&o&lMineparty &7You do not have permission to do this!"));
		}
		return true;
	}
	
	public static boolean isMinePartyActive()
	{
		return isActive;
	}
	
	public void setBarriers()
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine removeblock mineparty 133:0");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine removeblock mineparty 57:0");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine removeblock mineparty 169:0");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine removeblock mineparty 153:0");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine addblock mineparty 166:0 100");
		new BukkitRunnable()
		{
			public void run()
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine reset mineparty");
			}
		}.runTaskLater(Main.getInstance(),5L);
	}
	
	public void setBlocks()
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine removeblock mineparty 166:0");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine addblock mineparty 133:0 33");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine addblock mineparty 57:0 33");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine addblock mineparty 169:0 33");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine addblock mineparty 153:0 1");
		new BukkitRunnable()
		{
			public void run()
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"prisonmine reset mineparty");
			}
		}.runTaskLater(Main.getInstance(),5L);
	}
}
