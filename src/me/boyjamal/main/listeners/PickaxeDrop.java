package me.boyjamal.main.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import me.boyjamal.main.utils.MainUtils;

public class PickaxeDrop implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack item = evt.getItemDrop().getItemStack();
		if (item != null)
		{
			if (MainUtils.isPickaxe(item))
			{
				evt.setCancelled(true);
				p.sendMessage(MainUtils.chatColor("&c&LError: &7You can't drop your pickaxe!"));
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt)
	{
		Player p = evt.getPlayer();
		if (!(p.hasPlayedBefore()))
		{
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " group add A");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(PlayerDeathEvent evt) 
	{
		evt.getEntity().sendMessage(MainUtils.chatColor("&d&o&lXP &7You have kept your XP on death!"));
		evt.setDroppedExp(0);
	}
	
}
