package me.boyjamal.main.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.boyjamal.main.Main;

public class PlayerSettings implements Listener {

	public static Map<String,PlayerSettings> activeSettings = new HashMap<String,PlayerSettings>();
	
	private boolean mineSounds;
	private boolean tokenSummery;
	private boolean nightVision;
	private Player p;
	
	public PlayerSettings(Player p)
	{
		this.p = p;
		this.mineSounds = true;
		this.nightVision = true;
		this.tokenSummery = true;
	}
	
	public boolean hasMineSounds()
	{
		return mineSounds;
	}
	
	public boolean hasTokenSummery()
	{
		return tokenSummery;
	}
	
	public boolean hasNightVision()
	{
		return nightVision;
	}
	
	public Player getPlayer()
	{
		return p;
	}
	
	public boolean hasEnabled(String type)
	{
		if (type.equalsIgnoreCase("minesounds"))
		{
			if (mineSounds)
			{
				return true;
			} else {
				return false;
			}
		} else if (type.equalsIgnoreCase("tokenSummery")) {
		
			if (tokenSummery)
			{
				return true;
			} else {
				return false;
			}
		} else if (type.equalsIgnoreCase("nightvision")) {
			if (nightVision)
			{
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static void saveSettings()
	{
		if (!(activeSettings.isEmpty()))
		{
			for (PlayerSettings each : activeSettings.values())
			{
				
			}
		}
	}
	
	public static void loadSettings()
	{
		File file = new File(Main.getInstance().getDataFolder() + File.separator + "data" + File.separator + "settings.data");
		if (file != null)
		{
			//load file into active settings
		}
	}
}
