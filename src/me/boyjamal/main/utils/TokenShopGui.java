package me.boyjamal.main.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import me.boyjamal.main.Main;
import me.boyjamal.main.events.ConfirmationClose;
import me.boyjamal.main.events.ConfirmationOpen;
import me.boyjamal.main.events.TokenShopClick;
import me.boyjamal.main.events.TokenShopClose;
import me.boyjamal.main.events.TokenShopOpen;
import me.boyjamal.main.utils.MainUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TokenShopGui implements Listener {

	   private static HashMap<Player, Integer> runnableId = new HashMap();
	   private static HashMap<Player, Integer> confirmationrunnableId = new HashMap();
	   private static List<TokenShopItem> tokenItems = new ArrayList<>();
	   public static String tokenguiname = "&r              &8&nTokenShop";
	   public static String confirmationguiname = "&r            &8&nConfirmation";

	   public static ItemStack comingsoon() {
	      ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte)15);
	      ItemMeta meta = item.getItemMeta();
	      meta.setDisplayName(MainUtils.chatColor("&a&lCOMING SOON"));
	      item.setItemMeta(meta);
	      return item;
	   }

	   public static ItemStack glass() {
	      ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, glasstype());
	      ItemMeta meta = item.getItemMeta();
	      meta.setDisplayName(" ");
	      item.setItemMeta(meta);
	      return item;
	   }
	   
	   public static void createTokenShopItems(ItemStack item, String displayName, int slot, List<String> actions, int cost)
	   {
		  ItemMeta im = item.getItemMeta();
		  im.setDisplayName(MainUtils.chatColor(displayName));
		  
		  List<String> lore = new ArrayList<>();
		  lore.add(MainUtils.chatColor("&7Click to purchase!"));
		  DecimalFormat format = new DecimalFormat("#,###");
		  lore.add(MainUtils.chatColor("&7Price: &f" + format.format(cost) + " Tokens"));
		  im.setLore(lore);
		  im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		  im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		  im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		  item.setItemMeta(im);
		  item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		  
		  tokenItems.add(new TokenShopItem(item,slot,actions,cost));
	   }
	   
	   public static void setTokenItems()
	   {
		   createTokenShopItems(new ItemStack(Material.TRIPWIRE_HOOK,1),"&c&l1x Rankup Key",12,Arrays.asList("crates give %player% Rankup 1"),5000);
		   createTokenShopItems(new ItemStack(Material.GHAST_TEAR,1),"&c&l1x Visionary Key",13,Arrays.asList("crates give %player% Visionary 1"),15000);
		   createTokenShopItems(new ItemStack(Material.SLIME_BALL,1),"&c&l1x Mystical Key",14,Arrays.asList("crates give %player% Mystical 1"),25000);
		   createTokenShopItems(new ItemStack(Material.REDSTONE_TORCH_ON,1),"&c&l1x Flare Key &7&o(Start an Envoy)",20,Arrays.asList("envoy flare 1 %player%"),25000);
		   createTokenShopItems(new ItemStack(Material.EYE_OF_ENDER,1),"&c&l1x Level5 Bomb",21,Arrays.asList("bomb give %player% Level5"),600);
		   createTokenShopItems(new ItemStack(Material.EYE_OF_ENDER,1),"&c&l1x Level6 Bomb",22,Arrays.asList("bomb give %player% Level6"),1200);
		   createTokenShopItems(new ItemStack(Material.EYE_OF_ENDER,1),"&c&l1x Level7 Bomb",23,Arrays.asList("bomb give %player% Level7"),2000);
		   createTokenShopItems(new ItemStack(Material.PRISMARINE_SHARD,1),"&c&l1x Aremis Essence",24,Arrays.asList("fragments give %player% ArtemisEssence 1"),75000);
	   }

	   public static Inventory tokenshopgui(Player p) {
	      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, MainUtils.chatColor(tokenguiname));
	      TokenShopOpen.setnewglass(inv, p);
	      
	      setTokenItems();
	      
	      for (TokenShopItem each : tokenItems)
	      {
	    	  inv.setItem(each.getSlot(), each.getDisplay());
	      }

	      int i;

	      for(i = 29; i <= 33; ++i) {
	         inv.setItem(i, comingsoon());
	      }

	      for(i = 39; i <= 41; ++i) {
	         inv.setItem(i, comingsoon());
	      }

	      return inv;
	   }

	   public static Inventory confirmationgui(Player p) {
	      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, MainUtils.chatColor(confirmationguiname));
	      ConfirmationOpen.setConfirmation(inv, p);
	      return inv;
	   }

	   public static short glasstype() {
	      Random gen = new Random();
	      short r = (short)(gen.nextInt(3) + 1);
	      if (r == 1) {
	         return 0;
	      } else if (r == 2) {
	         return 10;
	      } else {
	         return (short)(r == 3 ? 6 : 5);
	      }
	   }

	   @EventHandler
	   public void closeTokenShopinv(InventoryCloseEvent event) {
	      if (event.getInventory().getName().equalsIgnoreCase(MainUtils.chatColor(tokenguiname))) {
	         TokenShopClose e = new TokenShopClose((Player)event.getPlayer(), event.getInventory());
	         Bukkit.getServer().getPluginManager().callEvent(e);
	      }

	   }

	   @EventHandler
	   public void closeConfirmationinv(InventoryCloseEvent event) {
	      if (event.getInventory().getName().equalsIgnoreCase(MainUtils.chatColor(confirmationguiname))) {
	         if (!(event.getPlayer() instanceof Player)) {
	            return;
	         }

	         ConfirmationClose e = new ConfirmationClose((Player)event.getPlayer(), event.getInventory());
	         Bukkit.getServer().getPluginManager().callEvent(e);
	      }

	   }

	   @EventHandler
	   public void playerOpenConfirmatoionGui(final ConfirmationOpen event) {
	      int IdNumber = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
	         public void run() {
	            Player p = event.getPlayer();
	            event.displayEnchantedEblock();
	            event.displayEnchantedRblock();
	            if (event.turns == 0) {
	               event.turns = 1;
	            } else {
	               event.turns = 0;
	            }

	            ConfirmationOpen.setConfirmation(event.getInventory(), p);
	            p.updateInventory();
	         }
	      }, 0L, 8L);
	      confirmationrunnableId.put(event.getPlayer(), IdNumber);
	   }

	   @EventHandler
	   public void playerOpenDynamicGUI(final TokenShopOpen event) {
	      int IdNumber = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
	         public void run() {
	            Player p = event.getPlayer();
	            TokenShopOpen.setnewglass(event.getInventory(), p);
	            p.updateInventory();
	         }
	      }, 0L, 10L);
	      runnableId.put(event.getPlayer(), IdNumber);
	   }

	   @EventHandler
	   public void playerCloseDynamicGUI(TokenShopClose event) {
	      if (runnableId.containsKey(event.getPlayer())) {
	         Bukkit.getServer().getScheduler().cancelTask((Integer)runnableId.get(event.getPlayer()));
	      }

	   }

	   @EventHandler
	   public void playerCloseConfirmationGUI(ConfirmationClose event) {
	      if (confirmationrunnableId.containsKey(event.getPlayer())) {
	         Bukkit.getServer().getScheduler().cancelTask((Integer)confirmationrunnableId.get(event.getPlayer()));
	      }
	      
	      if (TokenShopClick.carriedItems.containsKey(event.getPlayer().getUniqueId().toString()))
	      {
	    	  TokenShopClick.carriedItems.remove(event.getPlayer().getUniqueId().toString());
	      }

	   }

	   public static HashMap<Player, Integer> getRunnableId() {
	      return runnableId;
	   }

	   public static HashMap<Player, Integer> getConfirmationRunnableId() {
	      return confirmationrunnableId;
	   }
	
	   public static List<TokenShopItem> getTokenItems()
	   {
		   return tokenItems;
	   }
}
