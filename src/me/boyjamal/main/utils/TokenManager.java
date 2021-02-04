package me.boyjamal.main.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.boyjamal.main.Main;

public class TokenManager {
	
	private static HashMap<String,TokenSummery> tokenSummery = new HashMap<>();
	
	public static void addToken(Player p, int amount)
	{
		Main.getInstance().getTokens().addTokens(p, amount);
	}
	
	public static void removeToken(Player p, int amount)
	{
		if (Main.getInstance().getTokens().removeTokens(p, amount) >= 0)
		{
			Main.getInstance().getTokens().removeTokens(p, amount);
		} else {
			Main.getInstance().getTokens().setTokens(p, 0);
		}
	}
	
	public static HashMap<String,TokenSummery> getTokenSummery()
	{
		return tokenSummery;
	}
}
