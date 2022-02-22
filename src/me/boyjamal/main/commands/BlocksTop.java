package me.boyjamal.main.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
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

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.TokenManager;
import me.clip.ezblocks.BreakHandler;
import me.clip.ezblocks.EZBlocks;

public class BlocksTop implements CommandExecutor {

	private BlockTop[] calcBlocks = new BlockTop[5];
	private long lastCalculated = -1;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player)sender;
		
		if (lastCalculated == -1 || System.currentTimeMillis() >= lastCalculated + TimeUnit.HOURS.toMillis(1))
		{
			lastCalculated = System.currentTimeMillis();
			calcTop(p);
		} else {
			if (calcBlocks == null || calcBlocks[0] == null)
			{
				lastCalculated = System.currentTimeMillis();
				calcTop(p);
			} else {
				p.openInventory(blocksTop(calcBlocks));
			}
		}
		return true;
	}
	
	public void calcTop(Player p)
	{
		List<BlockTop> blocks = new ArrayList<>();
		for (PlayerSettings each : PlayerSettings.activeSettings.values())
		{
			if (BreakHandler.breaks.containsKey(each.getUUID()))
			{
				each.setBlocks(BreakHandler.breaks.get(each.getUUID()));
			}
			
			if (each.isExempt())
			{
				
			} else {
				blocks.add(new BlockTop(each.getBlocks(),each.getName(),each.getPlayer(),each.getName()));
			}
		}
		Collections.sort(blocks);
		BlockTop[] top5block = new BlockTop[5];
		for (int i = 0; i <=4; i++)
		{
			top5block[i] = blocks.get(i);
		}
		
		calcBlocks = top5block;
		
		if (blocksTop(top5block) != null)
		{
			p.openInventory(blocksTop(top5block));
		}
		
		Bukkit.broadcastMessage(MainUtils.chatColor("&d&o&lBlocks &7Leaderboards have been updated!"));
	}
	
	public Inventory blocksTop(BlockTop[] settings)
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
		firstLore.add(MainUtils.chatColor("&7Blocks Mined: " + format.format(settings[0].getBlocks())));
		fppm.setLore(firstLore);
		firstPlacePlayer.setItemMeta(fppm);
		
		ItemMeta sppm = secondPlacePlayer.getItemMeta();
		List<String> secondLore = new ArrayList<>();
		secondLore.add(MainUtils.chatColor("&7Blocks Mined: " + format.format(settings[1].getBlocks())));
		sppm.setLore(secondLore);
		secondPlacePlayer.setItemMeta(sppm);
		
		ItemMeta tppm = thirdPlacePlayer.getItemMeta();
		List<String> thirdLore = new ArrayList<>();
		thirdLore.add(MainUtils.chatColor("&7Blocks Mined: " + format.format(settings[2].getBlocks())));
		tppm.setLore(thirdLore);
		thirdPlacePlayer.setItemMeta(tppm);
		
		ItemMeta foppm = fourthPlacePlayer.getItemMeta();
		List<String> fourthLore = new ArrayList<>();
		fourthLore.add(MainUtils.chatColor("&7Blocks Mined: " + format.format(settings[3].getBlocks())));
		foppm.setLore(fourthLore);
		fourthPlacePlayer.setItemMeta(foppm);
		
		ItemMeta fippm = fifthPlacePlayer.getItemMeta();
		List<String> fifthLore = new ArrayList<>();
		fifthLore.add(MainUtils.chatColor("&7Blocks Mined: " + format.format(settings[4].getBlocks())));
		fippm.setLore(fifthLore);
		fifthPlacePlayer.setItemMeta(fippm);
		
		
		Inventory inv = Bukkit.createInventory(null, 45,MainUtils.chatColor("&8&nBlocks Top"));
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

class BlockTop implements Comparable<BlockTop>
{
	private Integer blocks;
	private String name; 
	private OfflinePlayer player;
	private String uuid;
	
	BlockTop(Integer blocks, String name, OfflinePlayer player, String uuid)
	{
		this.blocks = blocks;
		this.name = name;
		this.uuid = uuid;
		this.player = player;
	}
	
	public Integer getBlocks()
	{
		return blocks;
	}
	
	public OfflinePlayer getPlayer()
	{
		return player;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUUID()
	{
		return uuid;
	}

	@Override
	public int compareTo(BlockTop o) {
		return o.blocks.compareTo(blocks);
	}
}
