package me.boyjamal.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;


public class NightVision implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = (Player)sender;
		if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getName()));
		}
		
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		if (settings.hasNightVision())
		{
			settings.setNightVision(false);
			if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION))
			{
				p.removePotionEffect(PotionEffectType.NIGHT_VISION);
			}
			p.sendMessage(MainUtils.chatColor("&d&o&lSettings &7You have &fdisabled &7Night Vision!"));
		} else {
			settings.setNightVision(true);
			if (!(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)))
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,1));
			}
			p.sendMessage(MainUtils.chatColor("&d&o&lSettings &7You have &fenabled &7Night Vision!"));
		}
		return true;
	}

}
