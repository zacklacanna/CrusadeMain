package me.boyjamal.main.utils;

import org.bukkit.entity.Player;

import me.boyjamal.main.Main;

public class TokenSummery 
{
	
	private Player p;
	private int amount;
	private Long start;
	private int blocks = 0;
	
	public TokenSummery(Player p, int amount, long start)
	{
		this.p = p;
		this.amount = amount;
		this.start = start;
	}
	
	public Player getPlayer()
	{
		return p;
	}
	
	public int blocksMined()
	{
		return blocks;
	}
	
	public Long getStart()
	{
		return start;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public void addTokens(int add)
	{
		this.amount += add;
		TokenManager.addToken(p, add);
	}
	
	public void addBlocks(int add)
	{
		this.blocks += add;
	}
	
	public boolean removeTokens(int remove)
	{
		if (this.amount-remove >= 0)
		{
			this.amount = this.amount-remove;
			TokenManager.removeToken(p, remove);
			return true;
		} else {
			return false;
		}
	}
}
