package me.boyjamal.main.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.boyjamal.main.utils.MainUtils;

public class Discord implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		for (String line : discordLink(p))
		{
			p.sendMessage(MainUtils.chatColor(line));
		}
		return true;
	}
	
	public List<String> discordLink(Player p)
	{
		List<String> message = new ArrayList<>();
		message.add(" ");
		message.add("            &d&o&nDiscord");
		message.add(" ");
		message.add("        &7&odiscord.crusademc.com");
		message.add(" ");
		return message;
	}

}
