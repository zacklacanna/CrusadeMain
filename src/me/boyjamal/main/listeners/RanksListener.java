package me.boyjamal.main.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.StorageManager;

public class RanksListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		if (StorageManager.getRankups().getName().equalsIgnoreCase(ChatColor.stripColor(e.getInventory().getName())))
		{
			Player p = (Player)e.getWhoClicked();
			e.setCancelled(true);
			for (GuiItem items : StorageManager.getRankups().getItems())
			{
				if (items == null)
				{
					continue;
				}
				
				if (items.getSlot() == e.getSlot())
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
			return;
		}
	}

}
