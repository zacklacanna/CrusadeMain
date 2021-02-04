package me.boyjamal.main.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.GuiManager;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.StorageManager;

public class HelpListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if (StorageManager.getHelpMenu() != null)
		{
			GuiManager mang = StorageManager.getHelpMenu();
			if (e.getInventory().getName().equalsIgnoreCase(MainUtils.chatColor(mang.getName())))
			{
				Player p = (Player)e.getWhoClicked();
				e.setCancelled(true);
				for (GuiItem items : mang.getItems())
				{
					if (items != null)
					{
						if (e.getSlot() == items.getSlot())
						{
							for (String actions : items.getActions())
							{
								if (actions.startsWith("console:"))
								{
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actions.substring(8));
								} else if (actions.startsWith("player:")) {
									Bukkit.dispatchCommand(p, actions.substring(8));
								} else if (actions.startsWith("message:")) {
									p.sendMessage(MainUtils.chatColor(actions.substring(8)).replaceAll("%player%", p.getName()));
								} else {
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actions.substring(8));
								}
							}
							return;
						}
					}
				}
			}
		}
	}

}
