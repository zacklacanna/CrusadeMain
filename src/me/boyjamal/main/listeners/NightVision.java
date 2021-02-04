package me.boyjamal.main.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.boyjamal.main.utils.PlayerSettings;

public class NightVision implements Listener {
	
	@EventHandler
	public void nightVisionAdd(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		if (PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString()))
		{
			if (PlayerSettings.activeSettings.get(p.getUniqueId().toString()).hasNightVision())
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,1,true));
			}
		}
	}
	
	@EventHandler
	public void onSettings(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		if (!(p.hasPlayedBefore()))
		{
			if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
			{
				PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p));
			}
		}
	}
	
	@EventHandler
	public void onMoveEvent(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		if (PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString()))
		{
			PlayerSettings playerSetting = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
			if (playerSetting.hasEnabled("nightVision"))
			{
				if (!(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)))
				{
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,1,true));
				}
			}
		}
	}

}
