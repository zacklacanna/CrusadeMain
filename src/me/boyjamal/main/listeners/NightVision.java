package me.boyjamal.main.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.PlayerSettings;

public class NightVision implements Listener {
	
	@EventHandler
	public void onSettings(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		if (!(p.hasPlayedBefore()))
		{
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " group add A");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ac give " + p.getName() + " StarterCrate 1");
		}
		
		if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getName()));
		}
		
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		if (settings.getName() == null || !(settings.getName().equalsIgnoreCase(p.getName())))
		{
			settings.setName(p.getName());
		}
	}
	
	@EventHandler
	public void nightVisionAdd(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getName()));
		}
		
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		if (settings.hasNightVision())
		{
			if (!(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)))
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,1));
			}
		} else {
			if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				p.removePotionEffect(PotionEffectType.NIGHT_VISION);
			}
		}
		
		if (settings.hasJumpBoost())
		{
			if (!(p.hasPotionEffect(PotionEffectType.JUMP)))
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,3));
			}
		} else {
			if (p.hasPotionEffect(PotionEffectType.JUMP)) {
				p.removePotionEffect(PotionEffectType.JUMP);
			}
		}
		
		if (p.hasPermission("crusademc.top.exempt"))
		{
			settings.setExempt(true);
		} else {
			settings.setExempt(false);
		}
	}
}
