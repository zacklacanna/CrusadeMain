package me.boyjamal.main.listeners;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import me.boyjamal.main.utils.MainUtils;

public class RecipeBlocker implements Listener {
	
	@EventHandler
	public void blockRecipes(PrepareItemCraftEvent e)
	{
		Material mat = e.getRecipe().getResult().getType();
		Byte itemData = e.getRecipe().getResult().getData().getData();
		
		if (mat == Material.QUARTZ_ORE || (mat == Material.GOLDEN_APPLE && itemData == 1))
		{
			e.getInventory().setResult(new ItemStack(Material.AIR));
            for(HumanEntity he : e.getViewers()) {
                if(he instanceof Player) {
                    ((Player)he).sendMessage(MainUtils.chatColor("&c&lERROR &7&oYou can not craft this item!"));
                }
            }
            return;
		}
	}

}
