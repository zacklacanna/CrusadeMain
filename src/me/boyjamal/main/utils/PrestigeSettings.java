package me.boyjamal.main.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import me.boyjamal.main.Main;

public class PrestigeSettings {

	private Long cost;
	private List<String> rewards;
	
	public PrestigeSettings(Long cost, List<String> rewards)
	{
		this.cost = cost;
		this.rewards = rewards;
	}
	
	public Long getCost()
	{
		return cost;
	}
	
	public List<String> getRewards()
	{
		return rewards;
	}

	public int getCurrentPrestige(Player p)
	{
		if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getDisplayName()));
		}
		
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		return settings.getPrestige();
	}
}
