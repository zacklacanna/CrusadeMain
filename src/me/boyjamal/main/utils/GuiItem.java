package me.boyjamal.main.utils;

import java.util.List;

public class GuiItem {

	private ItemCreator item;
	private int slot;
	private List<String> actions;
	private List<String> secondActions;
	private List<String> messages;
	private String type;
	
	public GuiItem(ItemCreator item, int slot, List<String> actions, List<String> messages)
	{
		this.item = item;
		this.slot = slot;
		this.actions = actions;
		this.messages = messages;
	}
	
	public GuiItem(ItemCreator item, int slot, List<String> actions, List<String> messages,String type)
	{
		this.item = item;
		this.slot = slot;
		this.actions = actions;
		this.messages = messages;
		this.type = type;
	}
	
	public GuiItem(ItemCreator item, int slot, List<String> actions, List<String> messages, List<String> secondActions)
	{
		this.item = item;
		this.slot = slot;
		this.actions = actions;
		this.messages = messages;
		this.secondActions = secondActions;
	}
	
	public List<String> getSecondAction()
	{
		return secondActions;
	}
	
	public ItemCreator getItemCreator()
	{
		return item;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getSlot()
	{
		return slot;
	}
	
	public List<String> getActions()
	{
		return actions;
	}
	
	public List<String> getMessages()
	{
		return messages;
	}

}
