package me.boyjamal.main.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.boyjamal.main.Main;
import me.boyjamal.main.commands.Rankup;
import me.boyjamal.main.commands.Warps;
import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.GuiManager;
import me.boyjamal.main.utils.StorageManager;

public class WarpsListener implements Listener {
	
	@EventHandler
	public void onDonorClick(InventoryClickEvent evt)
	{
		if (!(evt.getWhoClicked() instanceof Player))
		{
			return;
		}
		Player p = (Player)evt.getWhoClicked();
		Inventory inv = evt.getInventory();
		String name = ChatColor.stripColor(inv.getName());
		
		GuiManager mang;
		
		if (inv.getName().equalsIgnoreCase(getWarpsGui(p,StorageManager.getDonorWarps()).getName())) {
			evt.setCancelled(true);
			mang = StorageManager.getDonorWarps();
		} else if(inv.getName().equalsIgnoreCase(getWarpsGui(p,StorageManager.getPublicWarps()).getName())) {
			evt.setCancelled(true);
			mang = StorageManager.getPublicWarps();
		} else if(inv.getName().equalsIgnoreCase(getWarpsGui(p,StorageManager.getPrestigeWarps()).getName())) {
			evt.setCancelled(true);
			mang = StorageManager.getPrestigeWarps();
		} else {
			return;
		}
		
		for (GuiItem each : mang.getItems())
		{
			if (each.getSlot() == evt.getSlot())
			{
				if (evt.getCurrentItem().isSimilar(each.getItemCreator().getAccessItem()))
				{
					if (each.getItemCreator().getPermission().equals("") || each.getItemCreator().getPermission().equals(" "))
					{
						return;
					}
					
					
					p.closeInventory();
					new BukkitRunnable()
					{
						@Override
						public void run()
						{
							for (String actions : each.getActions())
							{
								if (actions.equals(" " ) || actions.equals(""))
								{
									continue;
								}
									
									
								if (actions.startsWith("console-"))
								{
									String[] run = actions.split("-");
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), run[1].replaceAll("%name%", p.getName()));
								} else if (actions.startsWith("player-")) {
									String[] run = actions.split("-");
									p.performCommand(run[1]);
								} else {
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actions);
								}
							}
						}
					}.runTaskLater(Main.getInstance(),3L);
					
					for (String message : each.getMessages())
					{
						if (message.equals(" " ) || message.equals(""))
						{
							continue;
						}
						p.sendMessage(message);
					}
							
					//check settings for sounds
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 15L, 15L);
					return;
				} else if (evt.getCurrentItem().isSimilar(each.getItemCreator().getNoPermItem())) {
					if (each.getSecondAction() != null && !(each.getSecondAction().get(0).equals("") || each.getSecondAction().get(0).equals(" ")))
					{
						if (evt.getClick().equals(ClickType.RIGHT))
						{
							p.closeInventory();
							new BukkitRunnable()
							{
								@Override
								public void run()
								{
									for (String actions : each.getSecondAction())
									{
										if (actions.equals(" " ) || actions.equals(""))
										{
											continue;
										}
										
										
										if (actions.startsWith("console-"))
										{
											String[] run = actions.split("-");
											Bukkit.dispatchCommand(Bukkit.getConsoleSender(), run[1].replaceAll("%name%", p.getName()));
										} else if (actions.startsWith("player-")) {
											String[] run = actions.split("-");
											if (run.length >= 2)
											{
												p.performCommand(run[1]);
											}
										} else {
											Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actions);
										}
									}
								}
							}.runTaskLater(Main.getInstance(),3L);
						} 
					}
					return;
				}
			} else {
				continue;
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		if (!(evt.getWhoClicked() instanceof Player))
		{
			return;
		}
		Player p = (Player)evt.getWhoClicked();
		Inventory inv = evt.getInventory();
		
		if (inv.getName().equalsIgnoreCase(Warps.mainGUI().getName()))
		{
			evt.setCancelled(true);
			ItemStack clickedItem = evt.getCurrentItem();
			if (clickedItem == null)
			{
				return;
			}
			
			if (clickedItem.isSimilar(Warps.donorWarps()))
			{
				p.closeInventory();
				//check sounds settings
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 15L, 15L);
				
				new BukkitRunnable()
				{
					@Override
					public void run() {
						Inventory kits = getWarpsGui(p,StorageManager.getDonorWarps());
						p.openInventory(kits);
					}
					
				}.runTaskLater(Main.getInstance(), 3);
				return;
			} else if (clickedItem.isSimilar(Warps.mineWarps())) {
				p.closeInventory();
				//check sounds settings
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 15L, 15L);
				
				new BukkitRunnable()
				{
					@Override
					public void run() {
						p.openInventory(Rankup.getInv(p));
					}
					
				}.runTaskLater(Main.getInstance(), 3);
				return;
			} else if (clickedItem.isSimilar(Warps.prestigeWarps())) {
				p.closeInventory();
				//check sounds settings
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 15L, 15L);
				
				new BukkitRunnable()
				{
					@Override
					public void run() {
						Inventory kits = getWarpsGui(p,StorageManager.getPrestigeWarps());
						p.openInventory(kits);
					}
					
				}.runTaskLater(Main.getInstance(), 3);
			} else if (clickedItem.isSimilar(Warps.publicWarps())) {
				p.closeInventory();
				//check sounds settings
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 15L, 15L);
				
				new BukkitRunnable()
				{
					@Override
					public void run() {
						Inventory kits = getWarpsGui(p,StorageManager.getPublicWarps());
						p.openInventory(kits);
					}
					
				}.runTaskLater(Main.getInstance(), 3);
			} else {
				return;
			}
		}
	}
	
	
	public static Inventory getWarpsGui(Player p,GuiManager mang)
	{
		if (mang != null)
		{
			Inventory inv;
			
			if (mang.getItems().size() <= 5)
			{
				 inv = Bukkit.createInventory(null, InventoryType.HOPPER,mang.getName());
			} else {
				 inv = Bukkit.createInventory(null, mang.getSlots(),mang.getName());
			}
			
			
			for (GuiItem each : mang.getItems())
			{
				if (each.getItemCreator().getPermission().equals(" "))
				{
					inv.setItem(each.getSlot(), each.getItemCreator().getAccessItem());
					continue;
				}	
				
				if (p.hasPermission(each.getItemCreator().getPermission()))
				{
					inv.setItem(each.getSlot(), each.getItemCreator().getAccessItem());
				} else {
					inv.setItem(each.getSlot(), each.getItemCreator().getNoPermItem());
				}
			}
			return inv;
		}
		return Bukkit.createInventory(null, 9,"invalid");
	}

}
