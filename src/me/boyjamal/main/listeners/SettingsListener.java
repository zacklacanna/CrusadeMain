package me.boyjamal.main.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.StorageManager;

public class SettingsListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		if (StorageManager.getSettings().getName().equalsIgnoreCase(ChatColor.stripColor(e.getInventory().getName())))
		{
			e.setCancelled(true);
			for (GuiItem items : StorageManager.getSettings().getItems())
			{
				if (items.getSlot() == e.getSlot())
				{
					for (String actions : items.getActions())
					{
						//check specfic type
					}
					return;
				}
			}
			return;
		}
	}
	
}
