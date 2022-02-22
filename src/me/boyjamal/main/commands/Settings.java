package me.boyjamal.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.StorageManager;

public class Settings implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player)sender;
		p.openInventory(openInv(p));
		return true;
	}
	
	public static Inventory openInv(Player p)
	{		
		Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER,MainUtils.chatColor("&8&nPersonal Settings"));
		
		if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getName()));
		}
		
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		
		if (StorageManager.getSettings() != null)
		{
			for (GuiItem items : StorageManager.getSettings().getItems())
			{
				if (items.getItemCreator().getPermission() != null)
				{
					if (p.hasPermission(items.getItemCreator().getPermission()))
					{
						if (settings.hasEnabled(items.getType()))
						{
							inv.setItem(items.getSlot(), items.getItemCreator().getAccessItem());
						} else {
							inv.setItem(items.getSlot(), items.getItemCreator().getCooldownItem());
						}
					} else {
						if (items.getItemCreator().getNoPermItem() != null)
						{
							inv.setItem(items.getSlot(), items.getItemCreator().getNoPermItem());
						}
					}
				} else {
					if (settings.hasEnabled(items.getType()))
					{
						inv.setItem(items.getSlot(), items.getItemCreator().getAccessItem());
					} else {
						inv.setItem(items.getSlot(), items.getItemCreator().getNoPermItem());
					}
				}
			}
		}
		return inv;
	}
	
	public static Inventory essenceInv(Player p)
	{
		Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, MainUtils.chatColor("&8&nEssence Settings"));
		if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
		{
			PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getName()));
		}
		PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
		
		
		return inv;
	}

}
