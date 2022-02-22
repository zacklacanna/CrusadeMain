package me.boyjamal.main.utils;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class TokenShopItem {

	private ItemStack display;
	private int slot;
	private List<String> actions;
	private int cost;
	
	public TokenShopItem(ItemStack display, int slot, List<String> actions, int cost)
	{
		this.display = display;
		this.slot = slot;
		this.actions = actions;
		this.cost = cost;
	}
	
	public ItemStack getDisplay()
	{
		return display;
	}
	
	public int getSlot()
	{
		return slot;
	}
	
	public List<String> getActions()
	{
		return actions;
	}
	
	public int getCost()
	{
		return cost;
	}
	
}
