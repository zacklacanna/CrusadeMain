package me.boyjamal.main.utils;

import java.util.List;

public class FortuneUtil {
	
	private List<String> enabledWorlds;
	private List<String> enabledBlocks;
	
	public FortuneUtil(List<String> enabledWorlds, List<String> enabledBlocks)
	{
		this.enabledBlocks = enabledBlocks;
		this.enabledWorlds = enabledWorlds;
	}
	
	public List<String> getWorlds()
	{
		return enabledWorlds;
	}
	
	public List<String> getBlocks()
	{
		return enabledBlocks;
	}

}
