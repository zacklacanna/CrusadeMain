package me.boyjamal.main.utils;

public class EnchantItem {

	private int slot;
	private String name;
	
	public EnchantItem(int slot, String name)
	{
		this.slot = slot;
		this.name = name;
	}
	
	public int getSlot()
	{
		return slot;
	}
	
	public String getName()
	{
		return name;
	}
	

	public boolean isSimilar(String itemName, int clickedSlot) 
	{ 
		if (clickedSlot == slot) 
		{
			if (itemName.equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}
}
