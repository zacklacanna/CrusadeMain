package me.boyjamal.main.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemCreator {
	
	private boolean hasNoPerm;
	private boolean hasCooldown;
	private ItemStack accessItem;
	private ItemStack noPermItem;
	private ItemStack cooldownItem;
	private String permission;
	private Long cost;
	
	public ItemCreator(Material material)
	{
		this.accessItem = new ItemStack(material,1);
	}
	
	public ItemCreator(boolean hasNoPerm, ItemStack noPermItem, ItemStack accessItem, String permission)
	{
		this.hasNoPerm = hasNoPerm;
		this.noPermItem = noPermItem;
		this.accessItem = accessItem;
		this.permission = permission;
	}
	
	public ItemCreator(boolean hasNoPerm, boolean hasCooldown, ItemStack noPermItem, ItemStack cooldownItem, ItemStack accessItem, Long cost, String permission)
	{
		this.hasNoPerm = hasNoPerm;
		this.hasCooldown = hasCooldown;
		this.noPermItem = noPermItem;
		this.cooldownItem = cooldownItem;
		this.accessItem = accessItem;
		this.permission = permission;
		this.cost = cost;
	}
	
	public boolean hasCooldown()
	{
		return hasCooldown;
	}
	
	public boolean hasPermission()
	{
		return hasNoPerm;
	}
	
	public ItemStack getAccessItem()
	{
		return accessItem;
	}
	
	public ItemStack getNoPermItem()
	{
		return noPermItem;
	}
	
	public long getCost()
	{
		return cost;
	}
	
	public ItemStack getCooldownItem()
	{
		return cooldownItem;
	}
	
	public String getPermission()
	{
		return permission;
	}
}
