package me.boyjamal.main.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.inventivetalent.bossbar.BossBarAPI;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class RegionListener implements Listener {

	@EventHandler
	public void onEnter(RegionEnterEvent e)
	{
		Player p = e.getPlayer();
		if (p.getWorld().getName().equalsIgnoreCase("NewMines"))
		{
			String name = e.getRegion().getId();
			String toAdd = "";
			boolean remove = false;
			if (name.contains("-"))
			{
				String[] each = name.split("-");
				if (each.length == 2)
				{
					if (each[1].equalsIgnoreCase("area"))
					{
						if (!(each[0].length() == 1))
						{
							toAdd = each[0].toUpperCase();
							remove = true;
						} else {
							toAdd = each[0].toUpperCase() + "-MINE";
							remove = true;
						}
					} else {
						return;
					}
				}
			}
			
			
			if (remove && BossBarAPI.hasBar(p))
			{
				if (!(ChatColor.stripColor(BossBarAPI.getBossBar(p).getMessage()).equalsIgnoreCase(toAdd)))
				{
					BossBarAPI.removeAllBars(p);
				}
			}
			
			if (e.getRegion().getId().equalsIgnoreCase("__global__"))
			{
				return;
			}
			
			TextComponent text = new TextComponent(toAdd);
			text.setBold(true);
			text.setColor(ChatColor.LIGHT_PURPLE);
			
			BossBarAPI.addBar(p, // The receiver of the BossBar
				      text, // Displayed message
				      BossBarAPI.Color.PINK, // Color of the bar
				      BossBarAPI.Style.NOTCHED_20, // Bar style
				      1.0f, // Progress (0.0 - 1.0)
				      0, // Timeout
				      0); // Timeout-interval
		} else {
			if (BossBarAPI.hasBar(p))
			{
				BossBarAPI.removeAllBars(p);
			}
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
