/* Decompiler 2ms, total 655ms, lines 21 */
package me.boyjamal.main.events;

import me.boyjamal.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfirmClick implements Listener {

   @EventHandler
   public void onclick(InventoryClickEvent e) {
      Player p = (Player)e.getWhoClicked();
   }
}
