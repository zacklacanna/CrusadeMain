/* Decompiler 11ms, total 178ms, lines 76 */
package me.boyjamal.main.events;

import java.util.HashMap;
import java.util.Iterator;
import me.boyjamal.main.Main;
import me.boyjamal.main.commands.TokenShop;
import me.boyjamal.main.events.ConfirmationOpen;
import me.boyjamal.main.utils.TokenShopGui;
import me.boyjamal.main.utils.TokenShopItem;
import me.boyjamal.main.utils.MainUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

public class TokenShopClick implements Listener {
	
   public static HashMap<String,TokenShopItem> carriedItems = new HashMap<>();

   public static void notenoughtokens(final int slot, final Inventory inv, final ItemStack item) {
      ItemStack newmaterial = new ItemStack(MainUtils.stringToDisplayItem("redstone_block 1 &cYou_Cannot_Afford_This"));
      inv.setItem(slot, newmaterial);
      (new BukkitRunnable() {
         public void run() {
            inv.setItem(slot, item);
         }
      }).runTaskLater(Main.getInstance(), 25L);
   }

   @EventHandler
   public void onClick(InventoryClickEvent e) {
      Player p = (Player)e.getWhoClicked();
      Inventory inv = e.getInventory();
      if (!(e.getClickedInventory() instanceof PlayerInventory)) {
         if (inv.getName().equalsIgnoreCase(MainUtils.chatColor(TokenShopGui.tokenguiname))) {
            ItemStack item = e.getCurrentItem();
            e.setCancelled(true);
            
            if (item == null || item.getType() == Material.AIR)
            {
            	return;
            }
            
            for (TokenShopItem each : TokenShopGui.getTokenItems())
            {
            	if (each.getSlot() == e.getSlot() && each.getDisplay().isSimilar(item))
            	{
            		double tokens = Main.getInstance().getTokens().getTokens(p);
            		if (tokens < each.getCost())
            		{
            			notenoughtokens(each.getSlot(),inv,item);
            			return;
            		} else {
            			p.closeInventory();
            			new BukkitRunnable()
            			{
            				public void run()
            				{
            					Inventory confirmInv = TokenShopGui.confirmationgui(p);
                   	         	p.openInventory(confirmInv);
                   	         	
                   	         	ConfirmationOpen open = new ConfirmationOpen(p,each,confirmInv);
                   	         	Bukkit.getPluginManager().callEvent(open);
                   	         	
                   	         	carriedItems.put(p.getUniqueId().toString(), each);
            				}

            			}.runTaskLater(Main.getInstance(), 2L);
            			return;
            		}
            	}
            }
         }

      }
   }
   
   @EventHandler
   public void onConfirmClick(InventoryClickEvent e) {
      Player p = (Player)e.getWhoClicked();
      Inventory inv = e.getInventory();
      if (!(e.getClickedInventory() instanceof PlayerInventory)) {
         if (inv.getName().equalsIgnoreCase(MainUtils.chatColor(TokenShopGui.confirmationguiname))) {
            ItemStack item = e.getCurrentItem();
            TokenShopItem carriedItem;
            if (carriedItems.containsKey(p.getUniqueId().toString()))
            {
            	carriedItem = carriedItems.get(p.getUniqueId().toString());
            } else {
            	p.closeInventory();
            	return;
            }

            e.setCancelled(true);
            
            if (item == null || item.getType() == Material.AIR)
            {
            	return;
            }
            
            if (item.isSimilar(ConfirmationOpen.emeraldblock()))
            {
            	double tokens = Main.getInstance().getTokens().getTokens(p);
            	if (tokens < carriedItem.getCost())
            	{
            		p.closeInventory();
            		p.sendMessage(MainUtils.chatColor("&d&lTokenShop &f&L// &7You don't have enough tokens for this!"));
            		if (carriedItems.containsKey(p.getUniqueId().toString()))
            		{
            			carriedItems.remove(p.getUniqueId().toString());
            		}
            		return;
            	}
            	
            	Main.getInstance().getTokens().removeTokens(p, carriedItem.getCost());
            	for (String actions : carriedItem.getActions())
            	{
            		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actions.replace("%player%",p.getName()));
            	}
            	p.closeInventory();
            	p.sendMessage(MainUtils.chatColor("&d&lTokenShop &f&L// &7Purchase Successful!"));
            	carriedItems.remove(p.getUniqueId().toString());
            	return;
            
            } else if (item.isSimilar(ConfirmationOpen.redstoneblock())) {
            	carriedItems.remove(p.getUniqueId().toString());
            	p.closeInventory();
            	return;
            } else {
            	return;
            }
         }

      }
   }
}
