package me.boyjamal.main.utils;

import org.bukkit.inventory.ItemStack;

import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

import org.bukkit.entity.Player;
import org.bukkit.Material;

import me.boyjamal.main.Main;

public class SignUtil {
   
	public static void withdrawTokens(Player p)
	{
		new AnvilGUI.Builder()
		    .onComplete((player, text) -> {     
		    	int amount;
		    	try {
		    		amount = Integer.parseInt(text);
		    	} catch (Exception exc) {
		    		return AnvilGUI.Response.text(MainUtils.chatColor("&7Enter &c&nvalid&7 number!"));
		    	}
		    	
		    	if (TokenEnchantAPI.getInstance().getTokens(p) < amount)
		    	{
		    		p.sendMessage(MainUtils.chatColor("&c&lError: &7&oYou do not have &c&n" + amount + "&7 tokens!"));
		    	} else {
		    		p.performCommand("tok cheque " + amount);
		    	}
		    	return AnvilGUI.Response.close();
		    })                                             //prevents the inventory from being closed
		    .text(MainUtils.chatColor("&dEnter tokens!"))                              //sets the text the GUI should start with//called when the right input slot is clicked
		    .title(MainUtils.chatColor("&8&nToken Amount"))                                       //set the title of the GUI (only works in 1.14+)
		    .plugin(Main.getInstance())                                          //set the plugin instance
		    .open(p);     
	}
	
}