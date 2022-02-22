package me.boyjamal.main.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.boyjamal.main.Main;
import me.clip.ezblocks.BreakHandler;
import me.clip.ezrankspro.EZRanksPro;
import me.clip.ezrankspro.rankdata.LastRank;
import net.luckperms.api.model.user.User;

public class PrestigeGUI {

	public static Inventory prestigeGui(PlayerSettings settings,Player p)
	{
		Inventory inv = Bukkit.createInventory(null, 45,MainUtils.chatColor("&8&nPrestige"));
		if (settings == null)
		{
			return null;
		}
		
		int blocks = settings.getBlocks();
		int prestige = settings.getPrestige();
		
		int neededBlocks = 4000 + (settings.getPrestige()*25000);
		long prestigeCost = new Long("35000000000");
		if (settings.getPrestige() != 0)
		{
			long increment = new Long("2500000000");
			prestigeCost = prestigeCost + settings.getPrestige()*increment;
		}
		
		LastRank lastRank = EZRanksPro.getAPI().getLastRank();
		String currentRank = EZRanksPro.getAPI().getCurrentRank(p);
		
		if (blocks >= neededBlocks && Main.getInstance().getEco().getBalance(p) >= prestigeCost && lastRank.getRank().equalsIgnoreCase(currentRank))
		{
			inv.setItem(11, canPrestige(p,settings,neededBlocks,prestigeCost));
		} else {
			inv.setItem(11, cantPrestige(p,settings,neededBlocks,prestigeCost));
		}
		
		inv.setItem(15, currentPrestige(settings));
		int count = 25;
		for (int i = 29; i <= 33; i++)
		{
			inv.setItem(i, prestigeMine(p,count));
			count += 25;
		}
		
		for (int i = 0; i < 45; i++)
		{
			if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR)
			{
				inv.setItem(i, glass());
			}
		}
		return inv;
	}
	
	public static ItemStack glass()
	{
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)15);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&r"));
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack cantPrestige(Player p, PlayerSettings settings, int requiredBlocks,long cost)
	{
		ItemStack barrier = new ItemStack(Material.BARRIER);
		ItemMeta im = barrier.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&c&lPrestige"));
		
		List<String> lore = new ArrayList<>();
		DecimalFormat format = new DecimalFormat("#,###");
		int blocks = settings.getBlocks();
		
		lore.add(MainUtils.chatColor("&7You can not prestige yet!"));
		lore.add(MainUtils.chatColor("&7Current Blocks: &f" + format.format(blocks)));
		lore.add("");
		lore.add(MainUtils.chatColor("&7Blocks Required: &f" + format.format(requiredBlocks)));
		lore.add(MainUtils.chatColor("&7Cost: &f$" + format.format(cost)));
		lore.add("");
		lore.add(MainUtils.chatColor("&c&nLocked"));
		
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		barrier.setItemMeta(im);
		barrier.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return barrier;
	}
	
	public static ItemStack prestigeMine(Player p, int level)
	{
		ItemStack item;
		if (p.hasPermission("essentials.warps.prestige" + level))
		{
			item = new ItemStack(Material.STAINED_CLAY,1,(short)5);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(MainUtils.chatColor("&a&lPrestige Mine " + level));
			List<String> lore = new ArrayList<>();
			lore.add(MainUtils.chatColor("&7You have unlocked this mine!"));
			lore.add("");
			double baseSell = 1+((level/25)*0.05);
			double baseToken = 1+((level/25)*0.02);
			lore.add(MainUtils.chatColor("&7Sell Boost: &f" + baseSell + "x"));
			lore.add(MainUtils.chatColor("&7Token Boost: &f" + baseToken + "x"));
			lore.add("");
			lore.add(MainUtils.chatColor("&a&nUnlocked"));
			im.setLore(lore);
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(im);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			return item;
		} else {
			item = new ItemStack(Material.BARRIER);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(MainUtils.chatColor("&c&lPrestige Mine " + level));
			List<String> lore = new ArrayList<>();
			lore.add(MainUtils.chatColor("&cYou have not unlocked this mine!"));
			lore.add("");
			//1 + ((25/25)*0.05)
			double baseSell = 1+((level/25)*0.05);
			double baseToken = 1+((level/25)*0.02);
			lore.add("&7Sell Boost: &f" + baseSell + "x");
			lore.add("&7Token Boost: &f" + baseToken + "x");
			lore.add("");
			lore.add(MainUtils.chatColor("&c&nLocked"));
			im.setLore(lore);
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(im);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			return item;
		}
	}
	
	public static ItemStack canPrestige(Player p, PlayerSettings settings, int requiredBlocks, long cost)
	{
		ItemStack barrier = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
		ItemMeta im = barrier.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&a&lPrestige"));
		List<String> lore = new ArrayList<>();
		DecimalFormat format = new DecimalFormat("#,###");
		int blocks = settings.getBlocks();
		
		lore.add(MainUtils.chatColor("&7You can prestige!"));
		lore.add(MainUtils.chatColor("&7Current Blocks: &f" + format.format(blocks)));
		lore.add("");
		lore.add(MainUtils.chatColor("&7Blocks Required: &f" + format.format(requiredBlocks)));
		lore.add(MainUtils.chatColor("&7Cost: &f$" + format.format(cost)));
		lore.add("");
		lore.add(MainUtils.chatColor("&a&nRewards:"));
		lore.add(MainUtils.chatColor("&f- &71x Prestige Crate"));
		if (settings.getPrestige()+1 % 25 == 0)
		{
			lore.add(MainUtils.chatColor("&f- &7Access to new Prestige Mine"));
			lore.add(MainUtils.chatColor("&f- &7Unlock upgraded Sell/Token Multiplier"));
		}
		lore.add("");
		lore.add(MainUtils.chatColor("&a&nClick to Prestige!"));
		
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		barrier.setItemMeta(im);
		barrier.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return barrier;
	}
	
	public static ItemStack currentPrestige(PlayerSettings settings)
	{
		DecimalFormat format = new DecimalFormat("#,###");
	
		ItemStack barrier = new ItemStack(Material.BEACON);
		ItemMeta im = barrier.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&a&lPrestige " + format.format(settings.getPrestige())));
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(MainUtils.chatColor("&7Current Prestige: &f" + format.format(settings.getPrestige())));
		
		double percentIncrease = 1.0;
		percentIncrease = settings.getPrestige() + (settings.getPrestige()*0.1);
		
		lore.add(MainUtils.chatColor("&7Rankup Increase: &f" + "1.0x"));
		
		long prestigeCost = new Long("35000000000");
		if (settings.getPrestige() != 0)
		{
			long increment = new Long("2500000000");
			prestigeCost = prestigeCost + settings.getPrestige()*increment;
		}
		
		lore.add("");
		lore.add(MainUtils.chatColor("&7Prestige Cost: &f$" + format.format(prestigeCost)));
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		barrier.setItemMeta(im);
		barrier.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return barrier;
	}
	
	public static void prestigeUtil(Player p)
	{
		LastRank lastRank = EZRanksPro.getAPI().getLastRank();
		String currentRank = EZRanksPro.getAPI().getCurrentRank(p);
		
		if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getDisplayName()));
		}
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		
		if (lastRank.getRank().equalsIgnoreCase(currentRank))
		{
			int currentPrestige = StorageManager.getPrestigeSettings().getCurrentPrestige(p);
			User user = Main.getInstance().getPerms().getUserManager().getUser(p.getUniqueId());
			
			if (user == null)
			{
				return;
			}
			
			long prestigeCost = new Long("35000000000");
			if (settings.getPrestige() != 0)
			{
				long increment = new Long("2500000000");
				prestigeCost = prestigeCost + settings.getPrestige()*increment;
			}
			
			DecimalFormat format = new DecimalFormat("#,###");
			if (Main.getInstance().getEco().getBalance(p) < prestigeCost)
			{
			
				p.sendMessage(MainUtils.chatColor("&c&lError: &7You do not have enough money to prestige."));
				p.sendMessage(MainUtils.chatColor("&r           &c&nCost: $" + format.format(prestigeCost)));
				return;
			}
			
			int blocks = settings.getBlocks();
			int prestige = settings.getPrestige();
			
			int neededBlocks = 4000 + (settings.getPrestige()*25000);
			if (blocks < neededBlocks)
			{
				p.sendMessage(MainUtils.chatColor("&d&o&lPrestige &7&oYou need to mine &d" + (neededBlocks-blocks) + "&7 more blocks to prestige!"));
				return;
			}
			
			if (currentPrestige == 0)
			{
				for (String actions : StorageManager.getPrestigeSettings().getRewards())
				{
					if (actions.startsWith("console:"))
					{
						String[] each = actions.split(":");
						if (each.length == 2)
						{
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), each[1].replaceAll("%player%", p.getName())
																					 .replaceAll("%prestige%", String.valueOf(currentPrestige))
																					 );
							continue;
						}
					} else if (actions.startsWith("player:")) {
						String[] each = actions.split(":");
						if (each.length == 2)
						{
							p.performCommand(each[1]);
						}
						continue;
					} else if (actions.startsWith("message:")) {
						String[] each = actions.split(":");
						if (each.length == 2)
						{
							p.sendMessage(MainUtils.chatColor(each[1]).replaceAll("%player%", p.getName()));
						}
						continue;
					} else if (actions.startsWith("broadcast:")) {
						String[] each = actions.split(":");
						if (each.length == 2)
						{
							Bukkit.broadcastMessage(MainUtils.chatColor(each[1]).replaceAll("%player%", p.getName()).replaceAll("%prestige%", String.valueOf(currentPrestige+1)));
						}
					} else {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),actions);
						continue;
					}
				}
				settings.addPrestige(1);
			} else {
				int nextPrestige = Integer.valueOf(currentPrestige)+1;
				if (nextPrestige != 0 && nextPrestige <= 125 && nextPrestige % 25 == 0)
				{
					if (nextPrestige == 25)
					{
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " group add prestige25");
					} else {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " promote prestige");
					}
				}
				
				for (String actions : StorageManager.getPrestigeSettings().getRewards())
				{
					if (actions.startsWith("console:"))
					{
						String[] each = actions.split(":");
						if (each.length == 2)
						{
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), each[1].replaceAll("%player%", p.getName())
									 .replaceAll("%prestige%", String.valueOf(currentPrestige))
									 .replaceAll("%nextprestige%", String.valueOf(nextPrestige)));
							continue;
						}
					} else if (actions.startsWith("player:")) {
						String[] each = actions.split(":");
						if (each.length == 2)
						{
							p.performCommand(each[1]);
						}
						continue;
					} else if (actions.startsWith("message:")) {
						String[] each = actions.split(":");
						if (each.length == 2)
						{
							p.sendMessage(MainUtils.chatColor(each[1]).replaceAll("%player%", p.getName()));
						}
						continue;
					} else if (actions.startsWith("broadcast:")) {
						String[] each = actions.split(":");
						if (each.length == 2)
						{
							Bukkit.broadcastMessage(MainUtils.chatColor(each[1]).replaceAll("%player%", p.getName()).replaceAll("%prestige%", String.valueOf(currentPrestige+1)));
						}
					} else {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),actions);
						continue;
					}
				}
				settings.addPrestige(1);
				Main.getInstance().getPerms().getUserManager().saveUser(user);
			}
		} else {
			p.sendMessage(MainUtils.chatColor("&c&lError: &7You must be &c&nRank Z&7 to use this command!"));
			return;
		}
	}
	
}
