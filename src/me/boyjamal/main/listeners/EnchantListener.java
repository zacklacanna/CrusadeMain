package me.boyjamal.main.listeners;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.inventory.ClickType;

import com.vk2gpz.tokenenchant.api.CEHandler;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

import me.boyjamal.main.Main;
import me.boyjamal.main.commands.Enchant;
import me.boyjamal.main.events.TokenShopOpen;
import me.boyjamal.main.utils.AnvilGUI;
import me.boyjamal.main.utils.EnchantItem;
import me.boyjamal.main.utils.EnchantUtil;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.SignUtil;
import me.boyjamal.main.utils.TokenShopGui;
import net.lightshard.prisonbombs.PrisonBombs;
import net.lightshard.prisonbombs.grenade.GrenadeManager;

public class EnchantListener implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack inHand = p.getItemInHand();
		EnchantUtil util = Main.getInstance().getEnchantUtil();
		
		if (!(evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() == Action.RIGHT_CLICK_AIR))
		{
			return;
		}
		
		if (evt.getClickedBlock() != null && evt.getClickedBlock().getType() == Material.CHEST)
		{
			return;
		}
		
		if (inHand == null || !(util.isPickaxe(inHand)))
		{
			return;
		}
		
		p.openInventory(util.getEnchantGUI(p,inHand));
		
		//check player sounds
		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 15L, 15L);
	}
	
	@EventHandler
	public void guiClick(InventoryClickEvent evt)
	{
		if (!(evt.getWhoClicked() instanceof Player))
		{
			return;
		}
		
		Player p = (Player)evt.getWhoClicked();
		Inventory inv = evt.getInventory();
		EnchantUtil util = Main.getInstance().getEnchantUtil();
		
		if (inv.getName().equalsIgnoreCase(MainUtils.chatColor("&8&nEnchant GUI")))
		{
			evt.setCancelled(true);
			ItemStack clickedItem = evt.getCurrentItem();
			if (clickedItem == null)
			{
				return;
			}
			
			else if (clickedItem.isSimilar(util.multi()))
			{
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dm open multipliers " + p.getName());
					}
				}.runTaskLater(Main.getInstance(), 5);
				return;
			}
			
			else if (clickedItem.isSimilar(util.sellItems()))
			{
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						p.performCommand("sellall");
					}
				}.runTaskLater(Main.getInstance(), 5);
				return;
			}
			
			else if (clickedItem.isSimilar(util.redeemXp(p)))
			{
				p.closeInventory();
				MainUtils.redeemXP(p);
				return;
			}
			
			else if (clickedItem.isSimilar(util.tokenItem(p)))
			{
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						SignUtil.withdrawTokens(p);
					}
				}.runTaskLater(Main.getInstance(), 3);
				return;
			}
			
			else if (clickedItem.isSimilar(util.tokenShop()))
			{
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						Inventory inv = TokenShopGui.tokenshopgui(p);
				         p.openInventory(inv);
				         TokenShopOpen e = new TokenShopOpen(p, inv);
				         Bukkit.getServer().getPluginManager().callEvent(e);
					}
				}.runTaskLater(Main.getInstance(), 5);
				return;
			}
			
			else {
				if (util.getEnchantItems() != null && util.getEnchantItems().size() > 0)
				{
					for (EnchantItem each : util.getEnchantItems())
					{
						if (!(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()))
						{
							continue;
						}
						
						String stripName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
						if (each.isSimilar(stripName, each.getSlot()))
						{
							CEHandler enchant = TokenEnchantAPI.getInstance().getEnchantment(stripName);
							if (enchant == null)
							{
								System.out.println(MainUtils.chatColor("&c&lERROR: &7&oCould not load " + stripName+ " Enchant!"));
								return;
							}
							
							p.closeInventory();
							
							ItemStack pickaxe = evt.getInventory().getItem(49);
							if (pickaxe == null || !(MainUtils.isPickaxe(pickaxe)))
							{
								return;
							}
							
							double price = enchant.getPrice();
							int level = enchant.getCELevel(pickaxe);
							
							if (evt.getClick().equals(ClickType.RIGHT))
							{
								if (level+1 > enchant.getMaxLevel())
								{
									p.sendMessage(MainUtils.chatColor("&c&LError: &7Your pickaxe already is maxed in &c&n" + stripName + "&r"));
									return;
								}
								
								p.closeInventory();
								new BukkitRunnable()
								{
									public void run()
									{
										new AnvilGUI.Builder()
									    .onComplete((player, text) -> {     
									    	int amount;
									    	try {
									    		amount = Integer.parseInt(text);
									    	} catch (Exception exc) {
									    		return AnvilGUI.Response.text(MainUtils.chatColor("&7Enter &c&nvalid&7 number!"));
									    	}
									    	
									    	if (enchant.getMaxLevel() < (level+amount))
									    	{
									    		p.sendMessage(MainUtils.chatColor("&c&lError: &7&oThat would put &c&n" + enchant.getAlias() + "&7 over the max!"));
									    		return AnvilGUI.Response.close();
									    	}
									    	
									    	if (TokenEnchantAPI.getInstance().getTokens(p) < amount*price)
									    	{
									    		DecimalFormat formater = new DecimalFormat("#,###");
									    		p.sendMessage(MainUtils.chatColor("&c&lError: &7&oYou do not have &c&n" + (formater.format(amount*price)) + "&7 tokens!"));
									    	} else {
									    		TokenEnchantAPI.getInstance().enchant(p, p.getItemInHand(), stripName, amount, true, price*amount, true);
									    	}
									    	return AnvilGUI.Response.close();
									    })                                             //prevents the inventory from being closed
									    .text(MainUtils.chatColor("&dEnter level!"))                              //sets the text the GUI should start with//called when the right input slot is clicked
									    .title(MainUtils.chatColor("&8&nToken Amount"))                                       //set the title of the GUI (only works in 1.14+)
									    .plugin(Main.getInstance())                                          //set the plugin instance
									    .open(p);    
									}
								}.runTaskLater(Main.getInstance(), 5);	
								
							} else {
								if (level+1 > enchant.getMaxLevel())
								{
									p.sendMessage(MainUtils.chatColor("&c&LError: &7Your pickaxe already is maxed in &c&n" + stripName + "&r"));
									return;
								}
								
								double tokens = TokenEnchantAPI.getInstance().getTokens(p);
								if (price > tokens)
								{
									p.sendMessage(MainUtils.chatColor("&c&LError: &7This enchant costs &c&n" + price + "&7 tokens. You have &c&n" + TokenEnchantAPI.getInstance().getTokensInString(p) + "&7 tokens!"));
									return;
								}
								
								TokenEnchantAPI.getInstance().enchant(p, p.getItemInHand(), stripName, 1, true, price, true);
								return;
							}
						}
					}
				}
			}
		}
		
	}
	
}
