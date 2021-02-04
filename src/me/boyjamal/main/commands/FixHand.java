package me.boyjamal.main.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.MainUtils;

public class FixHand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		if (!(p.hasPermission("valancemc.command.fixhand")))
		{
			p.sendMessage(MainUtils.chatColor("&c&n&lError&7 You must be at least &c&nLeviathan&7 to use this!"));
			return true;
		}
		
		if (p.getInventory().getItemInHand() != null && MainUtils.isPickaxe(p.getInventory().getItemInHand()))
		{
			ItemStack repair = p.getInventory().getItemInHand().clone();
			if (!(repair.getDurability() == Short.MAX_VALUE))
			{
				if (MainUtils.getPrice(repair) != -1)
				{
					double price = MainUtils.getPrice(repair);
					if (Main.getInstance().getEco().withdrawPlayer(p, price).transactionSuccess())
					{
						repair.setDurability(Short.MAX_VALUE);
						p.sendMessage(MainUtils.chatColor("&a&n&lSuccess&7 You have repaired your &a&n" + repair.getType().toString().replaceAll("_", " ")));
						p.setItemInHand(repair);
						return true;
					} else {
						p.sendMessage(MainUtils.chatColor("&c&n&lError&7 You need at least &c&n$" + price + "&7 to repair this item!"));
						return true;
					}
				} else {
					p.sendMessage(MainUtils.chatColor("&c&n&lError&7 You must hold a valid item to use this!"));
					return true;
				}
			} else {
				p.sendMessage(MainUtils.chatColor("&c&n&lError&7 Your item is already repaired!"));
				return true;
			}
		} else {
			p.sendMessage(MainUtils.chatColor("&c&n&lError&7 You must hold a valid item to use this!"));
			return true;
		}
	}

}
