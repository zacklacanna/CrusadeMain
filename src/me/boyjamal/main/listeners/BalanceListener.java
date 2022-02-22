package me.boyjamal.main.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.boyjamal.main.Main;
import me.boyjamal.main.commands.WithdrawMoney;
import me.boyjamal.main.utils.MainUtils;

public class BalanceListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent evt)
	{
		Player p = evt.getPlayer();
		if (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			ItemStack inHand = p.getItemInHand();
			if (inHand.getType() == Material.CHEST && inHand.hasItemMeta() && inHand.getItemMeta().hasDisplayName() && inHand.getItemMeta().hasLore())
			{
				if (inHand.getItemMeta().getDisplayName().equalsIgnoreCase(MainUtils.chatColor("&e&lMoney Pouch &7&o(Right Click)")))
				{
					evt.setCancelled(true);
					
					if (inHand.getItemMeta().getLore().size() >= 1)
					{
						String stripped = ChatColor.stripColor(inHand.getItemMeta().getLore().get(0));
						String unformatted = stripped.split("Value:")[1];
						unformatted = unformatted.replace(",", "").replace(" ", "").replace("$", "");
						
						long amount;
						try {
							amount = Long.parseLong(unformatted);
						} catch (Exception exc) {
							return;
						}
						
						if (inHand.getAmount() > 1)
						{
							inHand.setAmount(inHand.getAmount()-1);
						} else {
							p.getInventory().remove(inHand);
						}
						Main.getInstance().getEco().depositPlayer(p,amount);
						p.sendMessage(MainUtils.chatColor("&d&o&lPouch &7You have successfully redeemed your pouch!"));
					}
				}
			}
		} else {
			return;
		}
	}
	
}
