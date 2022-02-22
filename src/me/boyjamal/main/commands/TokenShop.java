package me.boyjamal.main.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.boyjamal.main.events.TokenShopOpen;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.TokenShopGui;

public class TokenShop implements CommandExecutor {
	
	   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	      if (!(sender instanceof Player)) {
	         return true;
	      } else {
	         Player p = (Player)sender;
	         Inventory inv = TokenShopGui.tokenshopgui(p);
	         p.openInventory(inv);
	         TokenShopOpen e = new TokenShopOpen(p, inv);
	         Bukkit.getServer().getPluginManager().callEvent(e);

	         p.sendMessage(MainUtils.chatColor("&d&lTokenShop &f&L// &7You have opened the server &dToken Shop!"));
	         return true;
	      }
	   }
	
}
