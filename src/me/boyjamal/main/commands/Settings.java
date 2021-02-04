package me.boyjamal.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.StorageManager;

public class Settings implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player)sender;
		p.openInventory(openInv(p));
		return true;
	}
	
	public Inventory openInv(Player p)
	{
		String name = p.getName();
		if (name.endsWith("s"))
		{
			name = p.getName() + "' Settings";
		} else {
			name = p.getName() + "'s Settings";
		}
		
		double spacesAmount = ((30-name.length())/2)-1;
		if (!(spacesAmount % 1 == 0))
		{
			spacesAmount++;
		}
		
		String spaces = "";
		for (int i = 1; i<=spacesAmount;i++)
		{
			spaces += " ";
		}
		
		Inventory inv = Bukkit.createInventory(null, InventoryType.DISPENSER,MainUtils.chatColor("&r" + spaces + "&8" + name));
		if (StorageManager.getSettings() != null)
		{
			for (GuiItem items : StorageManager.getSettings().getItems())
			{
				if (PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString()))
				{
					PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
					if (items.getItemCreator().getAccessItem() != null && items.getType() != null)
					{
						if (settings.hasEnabled(items.getType()))
						{
							inv.setItem(items.getSlot(), items.getItemCreator().getNoPermItem());
						} else {
							inv.setItem(items.getSlot(), items.getItemCreator().getAccessItem());
						}
					}
				} else {
					if (items.getItemCreator().getAccessItem() != null)
					{
						inv.setItem(items.getSlot(), items.getItemCreator().getAccessItem());
					}
				}
			}
		}
		return inv;
	}

}
