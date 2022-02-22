package me.boyjamal.main.commands;

import java.awt.ImageCapabilities;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.GuiManager;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.StorageManager;

public class Warps implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Players only!");
			return true;
		}
		Player p = (Player)sender;
		
		if (StorageManager.getWarps() != null)
		{
			if (args.length == 0 || args.length >= 2)
			{
				
				p.openInventory(mainGUI());
				
				//check player settings for sounds
				p.playSound(p.getLocation(), Sound.CHEST_OPEN, 15L, 15L);
			} else {
				p.performCommand("ewarp " + args[0]);
			}
		}
		return true;
	}
	
	public static Inventory mainGUI()
	{
		Inventory inv = Bukkit.createInventory(null,InventoryType.DISPENSER,MainUtils.chatColor("&r &8&nWarps"));
		inv.setItem(1, publicWarps());
		inv.setItem(3, mineWarps());
		inv.setItem(5, donorWarps());
		inv.setItem(7, prestigeWarps());
		
		return inv;
	}
	
	public static ItemStack publicWarps()
	{
		ItemStack item = new ItemStack(Material.FIREWORK_CHARGE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&e&lPublic Warps"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7Click to open the"));
		lore.add(MainUtils.chatColor("&7public warps menu!"));
		im.setLore(lore);
		item.setItemMeta(im);
		
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return item;
	}
	
	public static ItemStack mineWarps()
	{
		ItemStack item = new ItemStack(Material.GOLDEN_CARROT);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&d&lRank Mines"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7Click to open the"));
		lore.add(MainUtils.chatColor("&7mine warps menu!"));
		im.setLore(lore);
		item.setItemMeta(im);
		
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return item;
	}
	
	public static ItemStack donorWarps()
	{
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&f&lDonor Mines"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7Click to open the"));
		lore.add(MainUtils.chatColor("&7donor warps menu!"));
		im.setLore(lore);
		item.setItemMeta(im);
		
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return item;
	}
	
	public static ItemStack prestigeWarps()
	{
		ItemStack item = new ItemStack(Material.PRISMARINE_CRYSTALS);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&b&lPrestige Warps"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7Click to open the"));
		lore.add(MainUtils.chatColor("&7prestige warps menu!"));
		im.setLore(lore);
		item.setItemMeta(im);
		
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return item;
	}
}
