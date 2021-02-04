package me.boyjamal.main.utils;

import java.util.List;

import org.bukkit.event.inventory.InventoryType;

public class GuiManager {
	
	private String name;
	private int slots;
	private List<GuiItem> items;
	private InventoryType type;
	
	public GuiManager(String name, int slots, List<GuiItem> items, InventoryType type)
	{
		this.name = name;
		this.slots = slots;
		this.items = items;
		this.type = type;
	}
	
	public GuiManager(String name, int slots, List<GuiItem> items)
	{
		this.name = name;
		this.slots = slots;
		this.items = items;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getSlots()
	{
		return slots;
	}
	
	public InventoryType getType()
	{
		return type;
	}
	
	public List<GuiItem> getItems()
	{
		return items;
	}

}
