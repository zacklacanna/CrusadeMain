package me.boyjamal.main.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.boyjamal.main.utils.MainUtils;

public class LeaderboardListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		if (!(evt.getWhoClicked() instanceof Player))
		{
			return;
		}
		
		Player p = (Player)evt.getWhoClicked();
		if (evt.getInventory().getName().equalsIgnoreCase(MainUtils.chatColor("&8&nBlocks Top")) 
						|| evt.getInventory().getName().equalsIgnoreCase(MainUtils.chatColor("&8&nPrestige Top")))
		{
			evt.setCancelled(true);
		}
	}

}
