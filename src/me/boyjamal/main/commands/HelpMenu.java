package me.boyjamal.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.GuiManager;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.StorageManager;

public class HelpMenu implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player)sender;
		if (StorageManager.getHelpMenu() != null)
		{
			GuiManager mang = StorageManager.getHelpMenu();
			Inventory inv = Bukkit.createInventory(null, mang.getSlots(), MainUtils.chatColor(mang.getName()));
			for (GuiItem items : mang.getItems())
			{
				if (items != null && items.getItemCreator().getAccessItem() != null)
				{
					inv.setItem(items.getSlot(), items.getItemCreator().getAccessItem());
				}
			}
			p.openInventory(inv);
			p.playSound(p.getLocation(), Sound.CHEST_OPEN, 500, 500);
		} else {
			p.sendMessage(MainUtils.chatColor("&c&lERROR &7Help Menu is currently inactive!"));
		}
		return true;
	}

}
