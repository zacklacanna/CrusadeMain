package me.boyjamal.main.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vk2gpz.tokenenchant.TokenEnchant;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.EnchantItem;
import me.boyjamal.main.utils.EnchantUtil;
import me.boyjamal.main.utils.MainUtils;

public class Enchant implements CommandExecutor {
	
	private List<EnchantItem> enchantItems = new ArrayList<>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		ItemStack inHand = p.getItemInHand();
		EnchantUtil util = Main.getInstance().getEnchantUtil();
		
		if (util.isPickaxe(inHand))
		{
			p.openInventory(util.getEnchantGUI(p,inHand));
			
			//check player sounds
			p.playSound(p.getLocation(), Sound.CHEST_OPEN, 15L, 15L);
		} else {
			p.sendMessage(MainUtils.chatColor("&c&lError: &7&oYou must hold a pickaxe to do this!"));
			return true;
		}
		return true;
	}

}
