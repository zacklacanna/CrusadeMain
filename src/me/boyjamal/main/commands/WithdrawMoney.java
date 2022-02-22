package me.boyjamal.main.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.AnvilGUI;
import me.boyjamal.main.utils.MainUtils;

public class WithdrawMoney implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		withdrawBalance(p);
		return true;
	}
	
	public static ItemStack createVoucher(long amount, Player p)
	{
		ItemStack item = new ItemStack(Material.CHEST,1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&e&lMoney Pouch &7&o(Right Click)"));
		List<String> lore = new ArrayList<>();
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		lore.add(MainUtils.chatColor("&eValue: &f$" + formatter.format(amount)));
		lore.add(MainUtils.chatColor("&r"));
		lore.add(MainUtils.chatColor("&7Withdrawn by &f&n" + p.getName()));
		im.setLore(lore);
		
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		
		return item;
	}
	
	public static void withdrawBalance(Player p)
	{
		new AnvilGUI.Builder()
		    .onComplete((player, text) -> {     
		    	long amount;
		    	try {
		    		amount = Long.parseLong(text);
		    	} catch (Exception exc) {
		    		return AnvilGUI.Response.text(MainUtils.chatColor("&7Enter &c&nvalid&7 number!"));
		    	}
		    	
		    	if (Main.getInstance().getEco().getBalance(p) < amount)
		    	{
		    		p.sendMessage(MainUtils.chatColor("&c&lError: &7&oYou do not have enough money!"));
		    	} else {
		    		p.getInventory().addItem(createVoucher(amount,p));
		    		Main.getInstance().getEco().withdrawPlayer(p, amount);
		    		p.sendMessage(MainUtils.chatColor("&d&o&lVouchers &7Your money pouch has been placed in your inventory!"));
		    	}
		    	return AnvilGUI.Response.close();
		    })                                             //prevents the inventory from being closed
		    .text(MainUtils.chatColor("&dEnter balance!"))                              //sets the text the GUI should start with//called when the right input slot is clicked
		    .title(MainUtils.chatColor("&8&nbalance"))                                       //set the title of the GUI (only works in 1.14+)
		    .plugin(Main.getInstance())                                          //set the plugin instance
		    .open(p);     
	}
}
