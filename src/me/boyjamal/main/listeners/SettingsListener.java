package me.boyjamal.main.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.boyjamal.main.Main;
import me.boyjamal.main.commands.Settings;
import me.boyjamal.main.utils.GuiItem;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.StorageManager;

public class SettingsListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		if (!(e.getWhoClicked() instanceof Player))
		{
			return;
		}
		Player p = (Player)e.getWhoClicked();
		
		if (e.getInventory().getName().equalsIgnoreCase(MainUtils.chatColor("&8&nPersonal Settings")))
		{
			e.setCancelled(true);
			if (!(PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString())))
			{
				PlayerSettings.activeSettings.put(p.getUniqueId().toString(), new PlayerSettings(p.getUniqueId().toString(),p.getName()));
			}
			
			PlayerSettings settings = PlayerSettings.activeSettings.get(p.getUniqueId().toString());
			
			for (GuiItem items : StorageManager.getSettings().getItems())
			{
				if (items.getSlot() == e.getSlot())
				{
					if (items.getItemCreator().getPermission() == null)
					{
						settings.changeEnabled(items.getType());
						if (settings.hasEnabled(items.getType()))
						{
							e.getInventory().setItem(items.getSlot(), items.getItemCreator().getAccessItem());
						} else {
							e.getInventory().setItem(items.getSlot(), items.getItemCreator().getNoPermItem());
						}
						return;
					} else {
						if (p.hasPermission(items.getItemCreator().getPermission())) {
							settings.changeEnabled(items.getType());
							if (settings.hasEnabled(items.getType()))
							{
								e.getInventory().setItem(items.getSlot(), items.getItemCreator().getAccessItem());
							} else {
								e.getInventory().setItem(items.getSlot(), items.getItemCreator().getCooldownItem());
							}
							return;
						} else {
							return;
						}
					}
				}
			}
			return;
		}
	}
	
}
