package me.boyjamal.main.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.boyjamal.main.utils.MainUtils;

public class Kits implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Players only!");
			return true;
		}
		
		Player p = (Player)sender;
		
		if (args.length == 0 || args.length >= 2)
		{
			p.openInventory(mainKits());
		} else {
			p.performCommand("ekit " + args[0]);
		}
		
		//check minesounds
		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 15L, 15L);
		return true;
	}
	
	
	public static Inventory mainKits()
	{
		Inventory inv = Bukkit.createInventory(null, 9, MainUtils.chatColor("&8&nKits"));
		
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(MainUtils.chatColor(" "));
		filler.setItemMeta(fillerMeta);
		
		//set items
		inv.setItem(0, filler);
		inv.setItem(1, filler);
		inv.setItem(2, filler);
		inv.setItem(3, rankKit());
		inv.setItem(4, filler);
		inv.setItem(5, gKit());
		inv.setItem(6, filler);
		inv.setItem(7, filler);
		inv.setItem(8, filler);
		return inv;
	}
	
	public static ItemStack rankKit()
	{
		ItemStack rankKit = new ItemStack(Material.NETHER_STAR,1);
		ItemMeta rankKitMeta = rankKit.getItemMeta();
		rankKitMeta.setDisplayName(MainUtils.chatColor("&e&lRank Kits"));
		List<String> rankKitLore = new ArrayList<>();
		rankKitLore.add(MainUtils.chatColor("&7Access to kits"));
		rankKitLore.add(MainUtils.chatColor("&7unlocked through ranks!"));
		rankKitLore.add("");
		rankKitLore.add(MainUtils.chatColor("&7Unlock at &7&nshop.crusademc.com"));
		rankKitMeta.setLore(rankKitLore);
		rankKit.setItemMeta(rankKitMeta);
		
		rankKitMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		rankKit.setItemMeta(rankKitMeta);
		rankKit.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		
		return rankKit;
	}
	
	public static ItemStack gKit()
	{
		ItemStack gkit = new ItemStack(Material.DRAGON_EGG,1);
		ItemMeta gkitMeta = gkit.getItemMeta();
		gkitMeta.setDisplayName(MainUtils.chatColor("&c&lGlobal Kits"));
		List<String> gkitLore = new ArrayList<>();
		gkitLore.add(MainUtils.chatColor("&7Lifetime access to"));
		gkitLore.add(MainUtils.chatColor("&7unqiue powerful items!"));
		gkitLore.add("");
		gkitLore.add(MainUtils.chatColor("&7Unlock at &7&nshop.crusademc.com"));
		gkitMeta.setLore(gkitLore);
		gkit.setItemMeta(gkitMeta);
		
		gkitMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		gkit.setItemMeta(gkitMeta);
		gkit.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		
		return gkit;
	}
}
