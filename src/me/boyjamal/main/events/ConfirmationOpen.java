/* Decompiler 221ms, total 265ms, lines 194 */
package me.boyjamal.main.events;

import java.util.ArrayList;
import java.util.List;
import me.boyjamal.main.utils.TokenShopGui;
import me.boyjamal.main.utils.TokenShopItem;
import me.boyjamal.main.utils.MainUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmationOpen extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private boolean cancelled = false;
   private Player player;
   private Inventory inventory;
   private TokenShopItem tokenItem;
   public int turns = 0;

   public ConfirmationOpen(Player player, TokenShopItem tokenItem, Inventory inventory) {
      this.player = player;
      this.inventory = inventory;
      this.tokenItem = tokenItem;
   }

   public Player getPlayer() {
      return this.player;
   }

   public Inventory getInventory() {
      return this.inventory;
   }
   
   public TokenShopItem getTokenItem()
   {
	   return tokenItem;
   }

   public void setInventory(Inventory inventory) {
      this.inventory = inventory;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean cancel) {
      this.cancelled = cancel;
   }

   public void displayEnchantedEblock() {
      ItemStack newmaterial = emeraldblock();
      ItemMeta meta = newmaterial.getItemMeta();
      meta.addEnchant(Enchantment.DURABILITY, 1, true);
      meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
      newmaterial.setItemMeta(meta);
      switch(this.turns) {
      case 0:
         this.inventory.setItem(27, newmaterial);
         this.inventory.setItem(28, newmaterial);
         this.inventory.setItem(29, newmaterial);
         this.inventory.setItem(36, newmaterial);
         this.inventory.setItem(37, newmaterial);
         this.inventory.setItem(38, newmaterial);
         this.inventory.setItem(45, newmaterial);
         this.inventory.setItem(46, newmaterial);
         this.inventory.setItem(47, newmaterial);
         break;
      case 1:
         this.inventory.setItem(27, emeraldblock());
         this.inventory.setItem(28, emeraldblock());
         this.inventory.setItem(29, emeraldblock());
         this.inventory.setItem(36, emeraldblock());
         this.inventory.setItem(37, emeraldblock());
         this.inventory.setItem(38, emeraldblock());
         this.inventory.setItem(45, emeraldblock());
         this.inventory.setItem(46, emeraldblock());
         this.inventory.setItem(47, emeraldblock());
         break;
      default:
         this.inventory.setItem(27, newmaterial);
         this.inventory.setItem(28, newmaterial);
         this.inventory.setItem(29, newmaterial);
         this.inventory.setItem(36, newmaterial);
         this.inventory.setItem(37, newmaterial);
         this.inventory.setItem(38, newmaterial);
         this.inventory.setItem(45, newmaterial);
         this.inventory.setItem(46, newmaterial);
         this.inventory.setItem(47, newmaterial);
      }

   }

   public void displayEnchantedRblock() {
      ItemStack newmaterial = redstoneblock();
      ItemMeta meta = newmaterial.getItemMeta();
      meta.addEnchant(Enchantment.DURABILITY, 1, true);
      meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
      newmaterial.setItemMeta(meta);
      switch(this.turns) {
      case 0:
         this.inventory.setItem(33, redstoneblock());
         this.inventory.setItem(34, redstoneblock());
         this.inventory.setItem(35, redstoneblock());
         this.inventory.setItem(42, redstoneblock());
         this.inventory.setItem(43, redstoneblock());
         this.inventory.setItem(44, redstoneblock());
         this.inventory.setItem(51, redstoneblock());
         this.inventory.setItem(52, redstoneblock());
         this.inventory.setItem(53, redstoneblock());
         break;
      case 1:
         this.inventory.setItem(33, newmaterial);
         this.inventory.setItem(34, newmaterial);
         this.inventory.setItem(35, newmaterial);
         this.inventory.setItem(42, newmaterial);
         this.inventory.setItem(43, newmaterial);
         this.inventory.setItem(44, newmaterial);
         this.inventory.setItem(51, newmaterial);
         this.inventory.setItem(52, newmaterial);
         this.inventory.setItem(53, newmaterial);
         break;
      default:
         this.inventory.setItem(33, redstoneblock());
         this.inventory.setItem(34, redstoneblock());
         this.inventory.setItem(35, redstoneblock());
         this.inventory.setItem(42, redstoneblock());
         this.inventory.setItem(43, redstoneblock());
         this.inventory.setItem(44, redstoneblock());
         this.inventory.setItem(51, redstoneblock());
         this.inventory.setItem(52, redstoneblock());
         this.inventory.setItem(53, redstoneblock());
      }

   }

   public static ItemStack glass() {
      ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, TokenShopGui.glasstype());
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(" ");
      item.setItemMeta(meta);
      return item;
   }

   public static ItemStack emeraldblock() {
      ItemStack item = new ItemStack(Material.STAINED_CLAY, 1, (short)5);
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(MainUtils.chatColor("&a&LCONFIRM"));
      List<String> lore = new ArrayList();
      lore.add(MainUtils.chatColor("&7Click to confirm"));
      meta.setLore(lore);
      item.setItemMeta(meta);
      return item;
   }

   public static ItemStack redstoneblock() {
      ItemStack item = new ItemStack(Material.STAINED_CLAY, 1, (short)14);
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(MainUtils.chatColor("&c&lCANCEL"));
      List<String> lore = new ArrayList();
      lore.add(MainUtils.chatColor("&7Click to cancel"));
      meta.setLore(lore);
      item.setItemMeta(meta);
      return item;
   }

   public static void setConfirmation(Inventory inv, Player p) {
      int i;
      for(i = 0; i <= 26; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 30; i <= 32; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 39; i <= 41; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 48; i <= 50; ++i) {
         inv.setItem(i, glass());
      }

   }
}
