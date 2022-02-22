package me.boyjamal.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.boyjamal.main.Main;
import me.boyjamal.main.utils.HeadAPI;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.PrestigeGUI;
import me.boyjamal.main.utils.PrestigeSettings;
import me.boyjamal.main.utils.StorageManager;
import me.clip.ezrankspro.EZRanksPro;
import me.clip.ezrankspro.rankdata.LastRank;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;

public class PrestigeCommand implements CommandExecutor {
	
	private PrestigeInfo[] calcPrestige = new PrestigeInfo[5];
	private long lastCalculated = -1;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		if (settings == null)
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getName()));
			settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		}
		
		if (args.length == 1)
		{
			if (args[0].equalsIgnoreCase("help"))
			{
				helpMessage(p);
				return true;
			} else if (args[0].equalsIgnoreCase("top")) {
				
				if (lastCalculated == -1 || System.currentTimeMillis() >= lastCalculated + TimeUnit.HOURS.toMillis(1))
				{
					lastCalculated = System.currentTimeMillis();
					calcTop(p);
				} else {
					if (calcPrestige == null || calcPrestige[0] == null)
					{
						lastCalculated = System.currentTimeMillis();
						calcTop(p);
					} else {
						p.openInventory(prestigeTop(calcPrestige));
					}
				}
				return true;
			} else {
				Inventory inv = PrestigeGUI.prestigeGui(settings, p);
				if (inv != null)
				{
					p.openInventory(inv);
					return true;
				}
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("set"))
			{
				if (!(p.hasPermission("crusademc.admin")))
				{
					p.sendMessage(MainUtils.chatColor("&c&LError &7No Perms!"));
					return true;
				}
				
				int level;
				String name = args[1];
				try {
					level = Integer.valueOf(args[2]);
				} catch (Exception exc) {
					p.sendMessage(MainUtils.chatColor("&c&LError &7Enter a valid number!"));
					return true;
				}
				
				for (PlayerSettings each : PlayerSettings.activeSettings.values())
				{
					if (each.getName().equalsIgnoreCase(name))
					{
						each.setPrestige(level);
						p.sendMessage(MainUtils.chatColor("&d&o&LPrestige &7Prestige has been set to " + level + "!"));
						if (Bukkit.getPlayer(name) != null)
						{
							Player set = Bukkit.getPlayer(name);
							set.sendMessage(MainUtils.chatColor("&d&o&lPrestige &7Your prestige has been set to " + level + "!"));
						}
						return true;
					}
				}
			} else {
				Inventory inv = PrestigeGUI.prestigeGui(settings, p);
				if (inv != null)
				{
					p.openInventory(inv);
					return true;
				}
			}
		} else {
			Inventory inv = PrestigeGUI.prestigeGui(settings, p);
			if (inv != null)
			{
				p.openInventory(inv);
				return true;
			}
		}
		return true;
	}
	
	
	public void calcTop(Player p)
	{
		List<PrestigeInfo> prestige = new ArrayList<>();
		for (PlayerSettings each : PlayerSettings.activeSettings.values())
		{
			if (each.isExempt())
			{
				
			} else {
				prestige.add(new PrestigeInfo(each.getPlayer(),each.getPrestige(),each.getName()));
			}
		}
		Collections.sort(prestige);
		
		PrestigeInfo[] top5prestige = new PrestigeInfo[5];
		for (int i = 0; i <=4; i++)
		{
			top5prestige[i] = prestige.get(i);
		}
		
		calcPrestige = top5prestige;
		
		if (prestigeTop(top5prestige) != null)
		{
			p.openInventory(prestigeTop(top5prestige));
		}
		
		Bukkit.broadcastMessage(MainUtils.chatColor("&d&o&lPrestige &7Leaderboards have been updated!"));
	}
	
	public void helpMessage(Player p)
	{
		p.sendMessage(MainUtils.chatColor("&7&m&l+--------------------------------------+"));
		p.sendMessage("");
		p.sendMessage(MainUtils.chatColor("&d&l+ &f&n&lPrestige Help&r"));
		p.sendMessage(MainUtils.chatColor("&7&l- &f/prestige cost &7&o(Display cost of prestige)"));
		p.sendMessage(MainUtils.chatColor("&7&l- &f/prestige check &7&o(Check current prestige)"));
		p.sendMessage(MainUtils.chatColor("&c&l- &f/prestige help &7&o(Display prestige help)"));
		p.sendMessage("");
		p.sendMessage(MainUtils.chatColor("&7&m&l+--------------------------------------+"));
	}
	
	public Inventory prestigeTop(PrestigeInfo[] settings)
	{
		ItemStack firstPlaceTrophy;
		ItemStack secondPlaceTrophy;
		ItemStack thirdPlaceTrophy;
		ItemStack fourthPlaceTrophy;
		ItemStack fifthPlaceTrophy;
		
		ItemStack firstPlacePlayer;
		ItemStack secondPlacePlayer;
		ItemStack thirdPlacePlayer;
		ItemStack fourthPlacePlayer;
		ItemStack fifthPlacePlayer;
		
		HeadDatabaseAPI api = new HeadDatabaseAPI();
		try {
			firstPlaceTrophy = api.getItemHead("846");
			secondPlaceTrophy = api.getItemHead("47722");
			thirdPlaceTrophy = api.getItemHead("47721");
			fourthPlaceTrophy = api.getItemHead("42035");
			fifthPlaceTrophy = api.getItemHead("42035");
			
			firstPlacePlayer = MainUtils.playerHead(settings[0].getPlayer(),settings[0].getName());
			secondPlacePlayer = MainUtils.playerHead(settings[1].getPlayer(),settings[1].getName());
			thirdPlacePlayer = MainUtils.playerHead(settings[2].getPlayer(),settings[2].getName());
			fourthPlacePlayer = MainUtils.playerHead(settings[3].getPlayer(),settings[3].getName());
			fifthPlacePlayer = MainUtils.playerHead(settings[4].getPlayer(),settings[4].getName());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		ItemMeta firstPlaceMeta = firstPlaceTrophy.getItemMeta();
		firstPlaceMeta.setDisplayName(MainUtils.chatColor("&f&m-&d&m---&f&m-&r &e&lFirst Place &f&m-&d&m---&f&m-"));
		firstPlaceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		firstPlaceTrophy.setItemMeta(firstPlaceMeta);
		firstPlaceTrophy.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		
		ItemMeta secondPlaceMeta = secondPlaceTrophy.getItemMeta();
		secondPlaceMeta.setDisplayName(MainUtils.chatColor("&f&m-&d&m---&f&m-&r &e&lSecond Place &f&m-&d&m---&f&m-"));
		secondPlaceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		secondPlaceTrophy.setItemMeta(secondPlaceMeta);
		secondPlaceTrophy.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		
		ItemMeta thirdPlaceMeta = thirdPlaceTrophy.getItemMeta();
		thirdPlaceMeta.setDisplayName(MainUtils.chatColor("&f&m-&d&m---&f&m-&r &e&lThird Place &f&m-&d&m---&f&m-"));
		thirdPlaceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		thirdPlaceTrophy.setItemMeta(thirdPlaceMeta);
		thirdPlaceTrophy.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		
		ItemMeta fourthPlaceMeta = fourthPlaceTrophy.getItemMeta();
		fourthPlaceMeta.setDisplayName(MainUtils.chatColor("&f&m-&d&m---&f&m-&r &e&lFourth Place &f&m-&d&m---&f&m-"));
		fourthPlaceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		fourthPlaceTrophy.setItemMeta(fourthPlaceMeta);
		fourthPlaceTrophy.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		
		ItemMeta fifthPlaceMeta = fifthPlaceTrophy.getItemMeta();
		fifthPlaceMeta.setDisplayName(MainUtils.chatColor("&f&m-&d&m---&f&m-&r &e&lFifth Place &f&m-&d&m---&f&m-"));
		fifthPlaceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		fifthPlaceTrophy.setItemMeta(fifthPlaceMeta);
		fifthPlaceTrophy.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		
		ItemMeta fppm = firstPlacePlayer.getItemMeta();
		DecimalFormat format = new DecimalFormat("#,###");
		List<String> firstLore = new ArrayList<>();
		firstLore.add(MainUtils.chatColor("&7Prestige " + format.format(settings[0].getPrestige())));
		fppm.setLore(firstLore);
		firstPlacePlayer.setItemMeta(fppm);
		
		ItemMeta sppm = secondPlacePlayer.getItemMeta();
		List<String> secondLore = new ArrayList<>();
		secondLore.add(MainUtils.chatColor("&7Prestige " + format.format(settings[1].getPrestige())));
		sppm.setLore(secondLore);
		secondPlacePlayer.setItemMeta(sppm);
		
		ItemMeta tppm = thirdPlacePlayer.getItemMeta();
		List<String> thirdLore = new ArrayList<>();
		thirdLore.add(MainUtils.chatColor("&7Prestige " + format.format(settings[2].getPrestige())));
		tppm.setLore(thirdLore);
		thirdPlacePlayer.setItemMeta(tppm);
		
		ItemMeta foppm = fourthPlacePlayer.getItemMeta();
		List<String> fourthLore = new ArrayList<>();
		fourthLore.add(MainUtils.chatColor("&7Prestige " + format.format(settings[3].getPrestige())));
		foppm.setLore(fourthLore);
		fourthPlacePlayer.setItemMeta(foppm);
		
		ItemMeta fippm = fifthPlacePlayer.getItemMeta();
		List<String> fifthLore = new ArrayList<>();
		fifthLore.add(MainUtils.chatColor("&7Prestige " + format.format(settings[4].getPrestige())));
		fippm.setLore(fifthLore);
		fifthPlacePlayer.setItemMeta(fippm);
		
		
		Inventory inv = Bukkit.createInventory(null, 45,MainUtils.chatColor("&8&nPrestige Top"));
		inv.setItem(3, firstPlaceTrophy);
		inv.setItem(4, firstPlacePlayer);
		inv.setItem(5, firstPlaceTrophy);
		
		inv.setItem(19, secondPlaceTrophy);
		inv.setItem(20, secondPlacePlayer);
		inv.setItem(21, secondPlaceTrophy);
		
		inv.setItem(23, thirdPlaceTrophy);
		inv.setItem(24, thirdPlacePlayer);
		inv.setItem(25, thirdPlaceTrophy);
		
		inv.setItem(37, fourthPlaceTrophy);
		inv.setItem(38, fourthPlacePlayer);
		inv.setItem(39, fourthPlaceTrophy);
		
		inv.setItem(41, fifthPlaceTrophy);
		inv.setItem(42, fifthPlacePlayer);
		inv.setItem(43, fifthPlaceTrophy);
		
		return inv;
	}

}

class PrestigeInfo implements Comparable<PrestigeInfo>
{
	private OfflinePlayer player;
	private String name;
	private Integer prestige;
	
	PrestigeInfo(OfflinePlayer player, Integer prestige, String name)
	{
		this.player = player;
		this.prestige = prestige;
		this.name = name;
	}
	
	OfflinePlayer getPlayer()
	{
		return player;
	}
	
	String getName()
	{
		return name;
	}
	
	Integer getPrestige()
	{
		return prestige;
	}

	@Override
	public int compareTo(PrestigeInfo p) {
		return p.getPrestige().compareTo(prestige);
	}
}
