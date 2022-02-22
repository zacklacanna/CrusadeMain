/* Decompiler 21ms, total 93ms, lines 104 */
package me.boyjamal.main.events;

import me.boyjamal.main.utils.TokenShopGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TokenShopOpen extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private boolean cancelled = false;
   private Player player;
   private Inventory inventory;

   public TokenShopOpen(Player player, Inventory inventory) {
      this.player = player;
      this.inventory = inventory;
   }

   public Player getPlayer() {
      return this.player;
   }

   public Inventory getInventory() {
      return this.inventory;
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

   public static ItemStack glass() {
      ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, TokenShopGui.glasstype());
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(" ");
      item.setItemMeta(meta);
      return item;
   }

   public static void setnewglass(Inventory inv, Player p) {
      int i;
      for(i = 0; i <= 8; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 9; i <= 11; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 15; i <= 17; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 18; i <= 19; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 25; i <= 26; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 27; i <= 28; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 34; i <= 35; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 36; i <= 38; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 42; i <= 44; ++i) {
         inv.setItem(i, glass());
      }

      for(i = 45; i <= 53; ++i) {
         inv.setItem(i, glass());
      }

   }
}
