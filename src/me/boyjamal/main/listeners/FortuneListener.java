package me.boyjamal.main.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.FortuneUtil;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.StorageManager;

public class FortuneListener implements Listener {
	
	@EventHandler
	public void onFortuneBreak(BlockBreakEvent e)
	{
		if (e.isCancelled())
		{
			return;
		}
		
		Player p = e.getPlayer();
		if (StorageManager.getFortune() != null)
		{
			boolean allowed = false;
			FortuneUtil util = StorageManager.getFortune();
			for (String worlds : util.getWorlds())
			{
				if (worlds.equalsIgnoreCase(p.getWorld().getName()))
				{
					allowed = true;
					break;
				}
			}
			if (!(allowed))
			{
				return;
			} else {
				allowed = false;
			}
			
			for (String blockVal : util.getBlocks())
			{
				if (blockVal.equalsIgnoreCase(e.getBlock().getType().toString()))
				{
					allowed = true;
					break;
				}
			}
			if (!(allowed))
			{
				return;
			}
			
			if (p.getInventory().getItemInHand() != null && MainUtils.isPickaxe(p.getInventory().getItemInHand()))
			{
				ItemStack itemHeld = p.getInventory().getItemInHand();
				if (!(itemHeld.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)))
				{
					return;
				}
				int level = itemHeld.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
				if (level > 100)
				{
					level = 100;
				} else if (level < 0) {
					level = 0;
				}
				
				int blocksFortuned = (int)(1 + level*.21);
				if (Main.getInstance().blocks())
				{
					//add fortune algorithm
				}
				
			}
		}
	}

}
