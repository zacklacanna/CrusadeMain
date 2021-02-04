package me.boyjamal.main.utils;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class PetUtil {
	
	private ItemStack item;
	private String name;
	private List<String> actions;
	
	public PetUtil(ItemStack item, String name, List<String> actions)
	{
		this.item = item;
		this.name = name;
		this.actions = actions;
	}
	
	public ItemStack getPetItem()
	{
		return item;
	}
	
	public String getPetName()
	{
		return name;
	}
	
	public List<String> getPetActions()
	{
		return actions;
	}

}
