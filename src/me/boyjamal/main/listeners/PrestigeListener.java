package me.boyjamal.main.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.PrestigeGUI;

public class PrestigeListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		if (!(evt.getWhoClicked() instanceof Player))
		{
			return;
		}
		Player p = (Player)evt.getWhoClicked();
		Inventory inv =  evt.getInventory();
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		if (settings == null)
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getName()));
			settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		}
		
		if (inv.getName().equalsIgnoreCase(MainUtils.chatColor("&8&nPrestige")))
		{
			evt.setCancelled(true);
			ItemStack clicked = evt.getCurrentItem();
			if (clicked == null || clicked.getType() == Material.AIR)
			{
				return;
			}
			
			int neededBlocks = 4000;
			neededBlocks = neededBlocks + (settings.getPrestige()*25000);

			long prestigeCost = new Long("35000000000");
			if (settings.getPrestige() != 0)
			{
				long increment = new Long("2500000000");
				prestigeCost = prestigeCost + settings.getPrestige()*increment;
			}
			
			if (clicked.isSimilar(PrestigeGUI.canPrestige(p,settings, neededBlocks,prestigeCost)))
			{
				PrestigeGUI.prestigeUtil(p);
				p.closeInventory();
				return;
			}
			
			for (int i = 25; i<=125; i+=25)
			{
				if (clicked.isSimilar(PrestigeGUI.prestigeMine(p, i)))
				{
					if (p.hasPermission("essentials.warps.prestige" + i))
					{
						p.closeInventory();
						p.performCommand("ewarp prestige" + i);
						return;
					}
				}
			}
			return;
			
		}
	}

}
