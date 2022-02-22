package me.boyjamal.main.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.MainUtils;
import net.md_5.bungee.api.chat.TextComponent;

public class RegionListener implements Listener {

	@EventHandler
	public void onFlyEnable(RegionEnterEvent e)
	{
		new BukkitRunnable()
		{
			public void run()
			{
				Player p = e.getPlayer();
				if (p != null)
				{
					if (p.hasPermission("crusademc.admin"))
					{
						return;
					}
					
					if (p.getWorld().getName().equalsIgnoreCase("NewMines"))
					{
						String regionName = e.getRegion().getId();
						boolean validRegion = false;
						try {
							String[] regionSplit = regionName.split("-");
							if (regionSplit.length == 2)
							{
								if (regionSplit[1].equalsIgnoreCase("area"))
								{
									validRegion = true;
								}
							}
						} catch (Exception exc) {}
						
						if (validRegion)
						{
							if (!(p.isFlying()))
							{
								p.sendMessage(MainUtils.chatColor("&d&o&lFly &7&oYour flight has been enabled!"));
								p.setAllowFlight(true);
								p.setFlying(true);
							}
						}
					}
				}
			}
		}.runTaskLater(Main.getInstance(), 8L);
	}
	
	@EventHandler
	public void onFlyDisable(RegionLeaveEvent e)
	{
		Player p = e.getPlayer();
		if (p != null)
		{
			if (p.hasPermission("crusademc.admin"))
			{
				return;
			}
			
			if (p.getWorld().getName().equalsIgnoreCase("NewMines"))
			{
				String regionName = e.getRegion().getId();
				boolean validRegion = false;
				try {
					String[] regionSplit = regionName.split("-");
					if (regionSplit.length == 2)
					{
						if (regionSplit[1].equalsIgnoreCase("area"))
						{
							validRegion = true;
						}
					}
				} catch (Exception exc) {}
				
				if (validRegion)
				{
					if (p.isFlying() || p.getAllowFlight())
					{
						p.sendMessage(MainUtils.chatColor("&d&o&lFly &7&oYour flight has been disabled!"));
						p.setAllowFlight(false);
						p.setFlying(false);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEnter(RegionEnterEvent e)
	{
		Player p = e.getPlayer();
		if (BossBarAPI.hasBar(p))
		{
			BossBarAPI.removeAllBars(p);
		}
	}
	
	@EventHandler
	public void onLeave(RegionLeaveEvent e)
	{
		Player p = e.getPlayer();
		if (BossBarAPI.hasBar(p))
		{
			BossBarAPI.removeAllBars(p);
		}
	}
	
	
}
