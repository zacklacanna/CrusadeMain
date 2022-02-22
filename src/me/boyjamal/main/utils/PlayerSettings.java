package me.boyjamal.main.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.boyjamal.main.Main;
import me.clip.ezblocks.BreakHandler;
import me.clip.ezblocks.EZBlocks;

public class PlayerSettings implements Listener {

	public static Map<String,PlayerSettings> activeSettings = new HashMap<String,PlayerSettings>();
	
	private boolean mineSounds;
	private boolean tokenSummery;
	private boolean nightVision;
	private boolean jumpBoost;
	private boolean exempt;
	private int prestige;
	private int blocks;
	private String uuid;
	private String name;
	private OfflinePlayer p;
	
	public PlayerSettings(String uuid, String name)
	{
		this.mineSounds = true;
		this.nightVision = true;
		this.tokenSummery = true;
		this.jumpBoost = false;
		this.exempt = false;
		this.prestige = 0;
		this.blocks = 0;
		this.uuid = uuid;
		this.name = name;
		this.p = Bukkit.getOfflinePlayer(uuid);
	}
	
	public PlayerSettings(String uuid, String name, int prestige, int blocks, boolean exempt, boolean jumpBoost, boolean mineSounds, boolean nightVision, boolean tokenSummery)
	{
		this.uuid = uuid;
		this.prestige = prestige;
		this.exempt = exempt;
		this.blocks = blocks;
		this.mineSounds = mineSounds;
		this.jumpBoost = jumpBoost;
		this.nightVision = nightVision;
		this.tokenSummery = tokenSummery;
		this.name = name;
		this.p = Bukkit.getOfflinePlayer(uuid);
	}
	
	public boolean isExempt()
	{
		return exempt;
	}
	
	public void setExempt(boolean val)
	{
		this.exempt = val;
	}
	
	public boolean hasMineSounds()
	{
		return mineSounds;
	}
	
	public void setPrestige(int num)
	{
		this.prestige = num;
	}
	
	public void setBlocks(int amount)
	{
		this.blocks = amount;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setNightVision(boolean val)
	{
		this.nightVision = val;
	}
	
	public void setMineSounds(boolean val)
	{
		this.mineSounds = val;
	}
	
	public void setTokenSummery(boolean val)
	{
		this.tokenSummery = val;
	}
	
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getPrestige()
	{
		return prestige;
	}
	
	public int getBlocks()
	{
		return blocks;
	}
	
	public String getUUID()
	{
		return uuid;
	}
	
	public OfflinePlayer getPlayer()
	{
		return p;
	}
	
	public void addPrestige(int amount)
	{
		this.prestige += amount;
	}
	
	public boolean hasTokenSummery()
	{
		return tokenSummery;
	}
	
	public boolean hasNightVision()
	{
		return nightVision;
	}
	
	public boolean hasJumpBoost()
	{
		return jumpBoost;
	}
	
	public void changeEnabled(String type)
	{
		if (type == null)
		{
			return;
		}
		
		
		if (type.equalsIgnoreCase("minesounds"))
		{
			if (mineSounds)
			{
				mineSounds = false;
			} else {
				mineSounds = true;
			}
		} else if (type.equalsIgnoreCase("jumpboost")) {
			if (jumpBoost)
			{
				jumpBoost = false;
				if (Bukkit.getPlayer(name).isOnline())
				{
					Player onlinePlayer = Bukkit.getPlayer(name);
					if (onlinePlayer.hasPotionEffect(PotionEffectType.JUMP))
					{
						onlinePlayer.removePotionEffect(PotionEffectType.JUMP);
					}
				}
			} else {
				jumpBoost = true;
				if (Bukkit.getPlayer(name).isOnline())
				{
					Player onlinePlayer = Bukkit.getPlayer(name);
					if (!(onlinePlayer.hasPotionEffect(PotionEffectType.JUMP)))
					{
						onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,2));
					}
				}
			}
		} else if (type.equalsIgnoreCase("tokensummery")) {
		
			if (tokenSummery)
			{
				tokenSummery = false;
			} else {
				tokenSummery = true;
			}
		} else if (type.equalsIgnoreCase("nightvision")) {
			if (nightVision)
			{
				nightVision = false;
				if (Bukkit.getPlayer(name).isOnline())
				{
					Player onlinePlayer = Bukkit.getPlayer(name);
					if (onlinePlayer.hasPotionEffect(PotionEffectType.NIGHT_VISION))
					{
						onlinePlayer.removePotionEffect(PotionEffectType.NIGHT_VISION);
					}
				}
			} else {
				nightVision = true;
				if (Bukkit.getPlayer(name).isOnline())
				{
					Player onlinePlayer = Bukkit.getPlayer(name);
					if (!(onlinePlayer.hasPotionEffect(PotionEffectType.NIGHT_VISION)))
					{
						onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,1));
					}
				}
			}
		} else {
			return;
		}
	}
	
	public boolean hasEnabled(String type)
	{
		if (type == null)
		{
			return false;
		}
		
		
		if (type.equalsIgnoreCase("minesounds"))
		{
			if (mineSounds)
			{
				return true;
			} else {
				return false;
			}
		} else if (type.equalsIgnoreCase("jumpboost")) {
			if (jumpBoost)
			{
				return true;
			} else {
				return false;
			}
		} else if (type.equalsIgnoreCase("tokensummery")) {
		
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
		System.out.println("CrusadeMain > Saving PlayerData!");
		if (!(activeSettings.isEmpty()))
		{
			for (PlayerSettings each : activeSettings.values())
			{
				File file = new File(Main.getInstance().getDataFolder() + File.separator  + "data" + File.separator + each.getUUID() + ".yml");
				if (!(file.exists()))
				{
					try {
						file.createNewFile();
					} catch (Exception exc ) {
						exc.printStackTrace();
						continue;
					}
				}
				
				FileConfiguration playerYML;
				try {
					playerYML = YamlConfiguration.loadConfiguration(file);
				} catch (Exception exc ) {
					exc.printStackTrace();
					continue;
				}
				
				playerYML.set("data.uuid", each.getUUID());
				playerYML.set("data.name", each.getName());
				if (BreakHandler.breaks.containsKey(each.getUUID()))
				{
					playerYML.set("data.blocks", BreakHandler.breaks.get(each.getUUID()));
				} else {
					playerYML.set("data.blocks", EZBlocks.getEZBlocks().playerconfig.getBlocksBroken(each.getUUID()));
				}
				
				playerYML.set("data.exempt", each.exempt);
				playerYML.set("data.prestige", each.getPrestige());
				playerYML.set("data.jumpboost", each.jumpBoost);
				playerYML.set("data.minesounds", each.mineSounds);
				playerYML.set("data.nightvision", each.nightVision);
				playerYML.set("data.tokenSummery", each.tokenSummery);
				try {
					playerYML.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("CrusadeMain > PlayerData has been correctly saved!");
		}
	}
	
	public static void loadSettings()
	{
		System.out.println("CrusadeMain > Loading PlayerData!");
		File file = new File(Main.getInstance().getDataFolder() + File.separator + "data");
		if (file != null)
		{
			File[] eachSetting = file.listFiles();
			if (eachSetting != null && eachSetting.length >= 1) 
			{
			    for (File child : eachSetting) {
			    	FileConfiguration playerYML;
					try {
						playerYML = YamlConfiguration.loadConfiguration(child);
					} catch (Exception exc ) {
						exc.printStackTrace();
						continue;
					}
					
					String uuid = playerYML.getString("data.uuid");
					String name = playerYML.getString("data.name");
					int prestige = playerYML.getInt("data.prestige");
					int blocks = playerYML.getInt("data.blocks");
					boolean exempt = playerYML.getBoolean("data.exempt");
					boolean jumpBoost = playerYML.getBoolean("data.jumpboost");
					boolean mineSounds = playerYML.getBoolean("data.mineSounds");
					boolean nightVision = playerYML.getBoolean("data.nightVision");
					boolean tokenSummery = playerYML.getBoolean("data.tokenSummery");
					
					PlayerSettings setting = new PlayerSettings(uuid,name,prestige,blocks,exempt,jumpBoost,mineSounds,nightVision,tokenSummery);
					activeSettings.put(uuid, setting);
			    }
			    System.out.println("CrusadeMain > PlayerData has been loaded correctly!");
			}
		}
	}
}
