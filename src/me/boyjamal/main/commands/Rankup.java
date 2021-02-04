package me.boyjamal.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.GuiManager;
import me.boyjamal.main.utils.ItemCreator;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.StorageManager;

public class Rankup implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage(MainUtils.chatColor("&d&l(&f&l!&d&l) &7&oYou must be a player to use this! &d&l(&f!&d&l)"));
			return true;
		}
		Player p = (Player)sender;
		if (getInv(p) != null)
		{
			p.openInventory(getInv(p));
		} else {
			return true;
		}
		p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 500, 500);
		return true;
	}
	
	public Inventory getInv(Player p)
	{
		if (StorageManager.getRankups() != null)
		{
			GuiManager mang = StorageManager.getRankups();
			Inventory inv;
			if (mang.getType() != null)
			{
				inv = Bukkit.createInventory(null, mang.getType(), MainUtils.chatColor(mang.getName()));
			} else {
				inv = Bukkit.createInventory(null, mang.getSlots(),MainUtils.chatColor(mang.getName()));
			}
			
			long sum = 0;
			for (GuiItem items : mang.getItems())
			{
				ItemCreator item = items.getItemCreator();
				if (p.hasPermission(item.getPermission()))
				{
					inv.setItem(items.getSlot()-1, item.getAccessItem());
				} else {
					if (Main.getInstance().getEco().getBalance(p) >= sum)
					{
						if (item.getCost() != 0)
						{
							sum += item.getCost();
						}
						inv.setItem(items.getSlot()-1, item.getCooldownItem());
						continue;
					} else {
						inv.setItem(items.getSlot()-1, item.getNoPermItem());
						continue;
					}
				}
			}
			return inv;
		} else {
			p.sendMessage(MainUtils.chatColor("&d&l(&f&l!&d&l) &7&oRankups are currently down. Please Contact a Staff Member!"));
			return null;
		}
	}

}
