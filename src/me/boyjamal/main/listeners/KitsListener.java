package me.boyjamal.main.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import me.boyjamal.main.Main;
import me.boyjamal.main.commands.Kits;
import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.GuiManager;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.FundsEvent;
import me.boyjamal.main.utils.StorageManager;

public class KitsListener implements Listener {

	private HashMap<String,Integer> activeTasks = new HashMap<>();
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		Inventory inv = evt.getInventory();
		if (!(evt.getWhoClicked() instanceof Player))
		{
			return;
		}
		
		Player p = (Player)evt.getWhoClicked();
		Inventory kitsGUI = Kits.mainKits();
		
		if (inv.getName().equalsIgnoreCase(MainUtils.chatColor("&8&nKits")))
		{
			evt.setCancelled(true);
			if (evt.getSlot() == 3 && evt.getCurrentItem().isSimilar(Kits.rankKit()))
			{
				p.closeInventory();
				
				//check sounds settings
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 15L, 15L);
				
				new BukkitRunnable()
				{
					@Override
					public void run() {
						Inventory kits = rankKitGui(p);
						p.openInventory(kits);
					}
					
				}.runTaskLater(Main.getInstance(), 3);
				return;
			}
			
			if (evt.getSlot() == 5 && evt.getCurrentItem().isSimilar(Kits.gKit()))
			{
				p.closeInventory();
				
				//check sound settings
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 15L, 15L);
				
				new BukkitRunnable()
				{
					@Override
					public void run() {
						p.performCommand("gkits");
					}
					
				}.runTaskLater(Main.getInstance(), 3);
			}
		}
	}
	
	@EventHandler
	public void onKitClick(InventoryClickEvent evt)
	{
		Inventory inv = evt.getInventory();
		if (!(evt.getWhoClicked() instanceof Player))
		{
			return;
		}
		
		Player p = (Player)evt.getWhoClicked();
		if (StorageManager.getKits() != null)
		{
			GuiManager kits = StorageManager.getKits();
			if (kits.getName().equalsIgnoreCase(inv.getName()))
			{
				evt.setCancelled(true);
				for (GuiItem each : kits.getItems())
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
									if (evt.getClick().equals(ClickType.LEFT))
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
									} else if (evt.getClick().equals(ClickType.RIGHT)) {
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
												p.performCommand(run[1]);
											} else {
												Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actions);
											}
										}
									} else {
										return;
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
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent evt)
	{
		Inventory inv = evt.getInventory();
		if (!(evt.getPlayer() instanceof Player))
		{
			return;
		}
		
		Player p = (Player)evt.getPlayer();
		if (activeTasks.containsKey(p.getUniqueId().toString()))
		{
			if (StorageManager.getKits() != null)
			{
				GuiManager mang = StorageManager.getKits();
				if (inv.getName().equalsIgnoreCase(mang.getName()))
				{
					new BukkitRunnable()
					{

						@Override
						public void run() {
							p.openInventory(rankKitGui(p));			
						}
						
					}.runTaskLater(Main.getInstance(), 3L);
				}
			}
		}
	}
	
	public static Inventory rankKitGui(Player p)
	{
		if (StorageManager.getKits() != null)
		{
			GuiManager mang = StorageManager.getKits();
			Inventory inv = Bukkit.createInventory(null, mang.getSlots(),mang.getName());
			
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
