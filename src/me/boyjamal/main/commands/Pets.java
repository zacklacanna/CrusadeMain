package me.boyjamal.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PetUtil;
import me.boyjamal.main.utils.StorageManager;

public class Pets implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage(MainUtils.chatColor("&c&lERROR &7&oPlayers Only"));
			return true;
		}
		Player p = (Player)sender;
		Inventory inv;
		if (!(StorageManager.getPets().isEmpty()))
		{
			inv = Bukkit.createInventory(null, MainUtils.getInvSize(StorageManager.getPets().size()), MainUtils.chatColor("&8&nPets Menu"));
			int counter = 1;
			for (PetUtil each : StorageManager.getPets())
			{
				if (counter > 54)
				{
					continue;
				} else {
					inv.setItem(counter, each.getPetItem());
				}
			}
			p.openInventory(inv);
			p.sendMessage(MainUtils.chatColor("&a&lSUCCESS &7&oPets menu successfully opened!"));
			p.playSound(p.getLocation(), Sound.CHEST_OPEN, 500, 500);
			return true;
		} else {
			p.sendMessage(MainUtils.chatColor("&c&lERROR &7&oPets are currently disabled!"));
			p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 500, 500);
			return true;
		}
	}

}
