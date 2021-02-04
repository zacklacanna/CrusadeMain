package me.boyjamal.main.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.boyjamal.main.utils.MainUtils;

public class HideFlags implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player)sender;
		if (p.hasPermission("crusademain.admin"))
		{
			if (p.getInventory().getItemInHand() != null && (!(p.getInventory().getItemInHand().getType() == Material.AIR)))
			{
				ItemStack fix = p.getInventory().getItemInHand();
				ItemMeta itemStackMeta = fix.getItemMeta(); 
			      itemStackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			      itemStackMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			      itemStackMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			      fix.setItemMeta(itemStackMeta);
			      p.setItemInHand(fix);
			     p.sendMessage(MainUtils.chatColor("&a&lSUCCESS &7&oYour item has been fixed!"));
			     return true;
			} else {
				p.sendMessage(MainUtils.chatColor("&c&lERROR &7&oYou must be holding an item!"));
				return true;
			}
		} else {
			p.sendMessage(MainUtils.chatColor("&c&lERROR &7&oYou can not access this command!"));
			return true;
		}
	}

}
