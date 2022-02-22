package me.boyjamal.main.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.boyjamal.main.Main;
import me.clip.autosell.AutoSell;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.external.EZPlaceholderHook;

public class TokenMultiplierAPI extends PlaceholderExpansion {
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier)
	{
		if (identifier.equals("tokenmultiplier"))
		{
			if (p != null)
			{
				String type = "tokenenchant.multiplier.";
				if (p.hasPermission(type + "2.4"))
				{
					return "2.4x";
				} else if (p.hasPermission(type + "2.2")) {
					return "2.2x";
				} else if (p.hasPermission(type + "2.0")) {
					return "2.0x";
				} else if (p.hasPermission(type + "1.8")) {
					return "1.8x";
				} else if (p.hasPermission(type + "1.6")) {
					return "1.6x";
				} else if (p.hasPermission(type + "1.4")) {
					return "1.4x";
				} else if (p.hasPermission(type + "1.2")) {
					return "1.2x";
				} else {
					return "1.0x";
				}
			} else {
				return "Default";
			}
		}
		
		if (identifier.equals("correctmulti"))
		{
			if (p != null)
			{
				if (AutoSell.getInstance().getMultipliers().getPermissionMultiplier(p) != null)
				{
					double multi = AutoSell.getInstance().getMultipliers().getPermissionMultiplier(p).getMultiplier();
					multi+=1;
					return String.valueOf(multi);
				} else {
					return "1.0";
				}
			} else {
				return "1.0";
			}
		}
		
		if (identifier.equals("prestige"))
		{
			if (p != null)
			{
				if (StorageManager.getPrestigeSettings() != null)
				{
					return String.valueOf(StorageManager.getPrestigeSettings().getCurrentPrestige(p));
				} else {
					return "0";
				}
			} else {
				return "0";
			}
		}
		
		if (identifier.equals("prestige_chat"))
		{
			if (p != null)
			{
				if (StorageManager.getPrestigeSettings() != null)
				{
					int prestige = StorageManager.getPrestigeSettings().getCurrentPrestige(p);
					if (prestige != 0)
					{
						return MainUtils.chatColor("&d&l⚡" + String.valueOf(prestige));
					} else {
						return "";
					}
				} else {
					return "";
				}
			} else {
				return "";
			}
		}
		
		if (identifier.equals("rankname"))
		{
			if (p != null)
			{
				String type = "crusademc.rank.";
				if (p.hasPermission(type + "owner"))
				{
					return "Owner";
				} else if (p.hasPermission(type + "developer")) {
					return "Developer";
				} else if (p.hasPermission(type + "manager")) {
					return "Manager";
				} else if (p.hasPermission(type + "sradmin")) {
					return "SrAdmin";
				} else if (p.hasPermission(type + "admin")) {
					return "Admin";
				} else if (p.hasPermission(type + "srmod")) {
					return "SrMod";
				} else if (p.hasPermission(type + "mod")) {
					return "Mod";
				} else if (p.hasPermission(type + "trialmod")) {
					return "TrialMod";
				} else if (p.hasPermission(type + "builder")) {
					return "Builder";
				} else if (p.hasPermission(type + "youtuber")) {
					return "YouTube";
				} else if (p.hasPermission(type + "hercules")) {
					return "Hercules";
				} else if (p.hasPermission(type + "phoenix")) {
					return "Phoenix";
				} else if (p.hasPermission(type + "pegasus")) {
					return "Pegasus";
				} else if (p.hasPermission(type + "cyclops")) {
					return "Cyclops";
				} else if (p.hasPermission(type + "leviathan")) {
					return "Leviathan";
				} else if (p.hasPermission(type + "cerberus")) {
					return "Cerberus";
				} else {
					return "Default";
				}
			} else {
				return "Default";
			}
		}
		return null;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "BoyJamal";
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return "crusademain";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0.0";
	}

}
