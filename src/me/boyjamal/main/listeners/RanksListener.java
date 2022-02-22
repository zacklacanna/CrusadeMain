package me.boyjamal.main.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.GuiManager;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.StorageManager;

public class RanksListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		if (StorageManager.getRankups() != null)
		{
			GuiManager rankup = StorageManager.getRankups();
			if (rankup.getName().equalsIgnoreCase(e.getInventory().getName()))
			{
				Player p = (Player)e.getWhoClicked();
				e.setCancelled(true);
				for (GuiItem items : StorageManager.getRankups().getItems())
				{
					if (items == null)
					{
						continue;
					}
					if (items.getSlot()-1 == e.getSlot())
					{
						p.closeInventory();
						if (p.hasPermission(items.getItemCreator().getPermission()))
						{
							for (String actions : items.getActions())
							{
								if (actions.startsWith("console:"))
								{
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), each[1].replaceAll("%player%", p.getName()));
										continue;
									}
								} else if (actions.startsWith("player:")) {
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										p.performCommand(each[1]);
									}
									continue;
								} else if (actions.startsWith("message:")) {
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										p.sendMessage(MainUtils.chatColor(each[1]).replaceAll("%player%", p.getName()));
									}
									continue;
								} else {
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(),actions);
									continue;
								}
							}
							p.closeInventory();
							return;
						} else if (Main.getInstance().getEco().getBalance(p) >= items.getItemCreator().getCost()) {
							if (items.getSecondAction() == null)
							{
								return;
							}
							
							for (String actions : items.getSecondAction())
							{
								if (actions.startsWith("console:"))
								{
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), each[1].replaceAll("%player%", p.getName()));
										continue;
									}
								} else if (actions.startsWith("player:")) {
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										p.performCommand(each[1]);
									}
									continue;
								} else if (actions.startsWith("message:")) {
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										p.sendMessage(MainUtils.chatColor(each[1]).replaceAll("%player%", p.getName()));
									}
									continue;
								} else {
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(),actions);
									continue;
								}
							}
							p.closeInventory();
							return;
						} else {
							for (String actions : items.getSecondAction())
							{
								if (actions.startsWith("console:"))
								{
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), each[1].replaceAll("%player%", p.getName()));
										continue;
									}
								} else if (actions.startsWith("player:")) {
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										p.performCommand(each[1]);
									}
									continue;
								} else if (actions.startsWith("message:")) {
									String[] each = actions.split(":");
									if (each.length == 2)
									{
										p.sendMessage(MainUtils.chatColor(each[1]).replaceAll("%player%", p.getName()));
									}
									continue;
								} else {
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(),actions);
									continue;
								}
							}
							return;
						}
					} else {
						continue;
					}
				}
				return;
			}
		}
	}

}
