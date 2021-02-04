package me.boyjamal.main.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.boyjamal.main.commands.Busy;
import me.boyjamal.main.utils.MainUtils;

public class BusyListener implements Listener {
	
	@EventHandler
	public void onCmdRun(PlayerCommandPreprocessEvent e)
	{
		if (!(e.getPlayer().hasPermission("crusademc.admin")))
		{
			String[] parts = e.getMessage().replaceAll("/", "").split(" ");
			if (parts.length >= 2)
			{
				switch(parts[0].toLowerCase())
				{
					case "message":
					{
						boolean contains;
						try {
							contains = Busy.getBusyPlayers().contains(Bukkit.getPlayer(parts[1]).getUniqueId().toString());
						} catch (Exception exc) {
							return;
						}
						
						if (contains)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(MainUtils.chatColor("&c&n&lError&7 You can not message this player right now!"));
							break;
						}
					}
					
					case "msg":
					{
						boolean contains;
						try {
							contains = Busy.getBusyPlayers().contains(Bukkit.getPlayer(parts[1]).getUniqueId().toString());
						} catch (Exception exc) {
							return;
						}
						
						if (contains)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(MainUtils.chatColor("&c&n&lError&7 You can not message this player right now!"));
							break;
						}
					}
					
					case "pm":
					{
						boolean contains;
						try {
							contains = Busy.getBusyPlayers().contains(Bukkit.getPlayer(parts[1]).getUniqueId().toString());
						} catch (Exception exc) {
							break;
						}
						
						if (contains)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(MainUtils.chatColor("&c&n&lError&7 You can not message this player right now!"));
							break;
						}
					}
					
					case "dm":
					{
						boolean contains;
						try {
							contains = Busy.getBusyPlayers().contains(Bukkit.getPlayer(parts[1]).getUniqueId().toString());
						} catch (Exception exc) {
							return;
						}
						
						if (contains)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(MainUtils.chatColor("&c&n&lError&7 You can not message this player right now!"));
							break;
						}
					}
					
					case "whisper":
					{
						boolean contains;
						try {
							contains = Busy.getBusyPlayers().contains(Bukkit.getPlayer(parts[1]).getUniqueId().toString());
						} catch (Exception exc) {
							return;
						}
						
						if (contains)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(MainUtils.chatColor("&c&n&lError&7 You can not message this player right now!"));
							break;
						}
					}
					
					case "epm":
					{
						boolean contains;
						try {
							contains = Busy.getBusyPlayers().contains(Bukkit.getPlayer(parts[1]).getUniqueId().toString());
						} catch (Exception exc) {
							return;
						}
						
						if (contains)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(MainUtils.chatColor("&c&n&lError&7 You can not message this player right now!"));
							break;
						}
					}
					
					case "emessage":
					{
						boolean contains;
						try {
							contains = Busy.getBusyPlayers().contains(Bukkit.getPlayer(parts[1]).getUniqueId().toString());
						} catch (Exception exc) {
							return;
						}
						
						if (contains)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(MainUtils.chatColor("&c&n&lError&7 You can not message this player right now!"));
							break;
						}
					}
					
					case "ewhisper":
					{
						boolean contains;
						try {
							contains = Busy.getBusyPlayers().contains(Bukkit.getPlayer(parts[1]).getUniqueId().toString());
						} catch (Exception exc) {
							return;
						}
						
						if (contains)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(MainUtils.chatColor("&c&n&lError&7 You can not message this player right now!"));
							break;
						}
					}
				}
			}
		} else {
			return;
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		String key = e.getPlayer().getUniqueId().toString();
		if (Busy.getBusyPlayers().contains(key))
		{
			Busy.getBusyPlayers().remove(key);
		}
	}

}
