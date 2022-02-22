package me.boyjamal.main.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import me.boyjamal.main.utils.LevelUp;

public class ArmorStandInteract implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEntityEvent evt)
	{
		if (evt.getRightClicked().getType() == EntityType.ARMOR_STAND)
		{
			if (LevelUp.activeArmorStands.containsValue(evt.getRightClicked().getUniqueId()))
			{
				evt.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractAtEntityEvent evt)
	{
		if (evt.getRightClicked().getType() == EntityType.ARMOR_STAND)
		{
			if (LevelUp.activeArmorStands.containsValue(evt.getRightClicked().getUniqueId()))
			{
				evt.setCancelled(true);
			}
		}
	}

}
