package me.boyjamal.main.commands;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.MainUtils;

public class GiveAll implements CommandExecutor {
   public static String RewardMessageFormat(String type, String reward, String amount, Player giver) {
      if (type.equalsIgnoreCase("item")) {
         return MainUtils.chatColor("&a&lGIVEALL &e&l// &7You have been givin &e" + amount + " " + reward + "&7 by &e" + giver.getName());
      } else {
         return type.equalsIgnoreCase("money") ? MainUtils.chatColor("&a&lGIVEALL &e&l// &7You have been givin &e$" + reward + " &7by &e" + giver.getName()) : MainUtils.chatColor("&a&lGIVEALL &e&l// &7You have put &e" + type + " &7which is an invalid type");
      }
   }

   public static void gahelp(Player p) {
      p.sendMessage(MainUtils.chatColor("&8&m&L--------------------------------------"));
      p.sendMessage(" ");
      p.sendMessage(MainUtils.chatColor("&e/giveall &7&o(item [material] [amount])"));
      p.sendMessage(MainUtils.chatColor("&e/giveall &7&o(money [amount])"));
      p.sendMessage(MainUtils.chatColor("&e/giveall &7&o(hand [amount]"));
      p.sendMessage(" ");
      p.sendMessage(MainUtils.chatColor("&8&m&L--------------------------------------"));
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         return true;
      } else {
         Player p = (Player)sender;
         if (p.hasPermission("luckyprison.giveall")) {
            if (args.length <= 1) {
               gahelp(p);
               return true;
            }

            if (args.length == 2) {
               if (args[0].equalsIgnoreCase("hand")) {
                  if (!MainUtils.isNumber(args[1])) {
                     p.sendMessage(MainUtils.chatColor("&a&lGIVEALL &e// &e&n" + args[1] + " &7is not a vaild number!"));
                     return true;
                  }

                  Iterator var13 = Bukkit.getOnlinePlayers().iterator();

                  while(var13.hasNext()) {
                     Player online = (Player)var13.next();

                     for(int i = 0; i < Integer.parseInt(args[1]); ++i) {
                        int amount = Integer.parseInt(args[1]);
                        ItemStack is = p.getInventory().getItemInHand();
                        is.setAmount(amount);
                        online.getInventory().addItem(new ItemStack[]{is});
                     }

                     ItemStack is = p.getInventory().getItemInHand();
                     if (is.hasItemMeta()) {
                        if (is.getItemMeta().hasDisplayName()) {
                           online.sendMessage(MainUtils.chatColor("&a&lGIVEALL &e// &7You have been givin &e" + args[1] + " " + p.getInventory().getItemInHand().getItemMeta().getDisplayName() + " &7by &e" + p.getName()));
                        } else {
                           online.sendMessage(MainUtils.chatColor("&a&lGIVEALL &e// &7You have been givin &e" + args[1] + " " + p.getInventory().getItemInHand().getType() + " &7by &e" + p.getName()));
                        }
                     } else {
                        online.sendMessage(MainUtils.chatColor("&a&lGIVEALL &e// &7You have been givin &e" + args[1] + " " + p.getInventory().getItemInHand().getType() + " &7by &e" + p.getName()));
                     }
                  }

                  return true;
               }

               if (args[0].equalsIgnoreCase("item")) {
                  gahelp(p);
                  return true;
               }

               if (!args[0].equalsIgnoreCase("money")) {
                  gahelp(p);
                  return true;
               }

               if (!MainUtils.isNumber(args[1])) {
                  p.sendMessage(MainUtils.chatColor("&a&lGIVEALL &e// &e&n" + args[1] + " &7is not a vaild number!"));
                  return true;
               }

               double amount = (double)Integer.parseInt(args[1]);
               Iterator var9 = Bukkit.getOnlinePlayers().iterator();

               while(var9.hasNext()) {
                  Player online = (Player)var9.next();
                  Main.getInstance().getEco().bankDeposit(online.getName(), amount);
                  online.sendMessage(RewardMessageFormat("money", String.valueOf(amount), String.valueOf(0), p));
               }

               return true;
            }

            if (args.length == 3) {
               if (!args[0].equalsIgnoreCase("item")) {
                  gahelp(p);
                  return true;
               }

               if (Material.matchMaterial(args[1]) == null) {
                  p.sendMessage(MainUtils.chatColor("&a&lGIVEALL &e// &e&n" + args[1] + "&r &7is not a vaild material!"));
                  return true;
               }

               if (!MainUtils.isNumber(args[2])) {
                  p.sendMessage(MainUtils.chatColor("&a&lGIVEALL &e// &e&n" + args[2] + "&r &7is not a vaild number!"));
                  return true;
               }

               int amount = Integer.parseInt(args[2]);
               Iterator var8 = Bukkit.getOnlinePlayers().iterator();

               while(var8.hasNext()) {
                  Player online = (Player)var8.next();
                  online.getInventory().addItem(new ItemStack[]{new ItemStack(Material.matchMaterial(args[1]), amount)});
                  online.sendMessage(RewardMessageFormat("item", args[1], args[2], p));
               }

               return true;
            }

            if (args.length < 3) {
               gahelp(p);
               return true;
            }
         }

         p.sendMessage(MainUtils.chatColor("&c&lERROR: &7&oYou do not have permission to use this command!"));
         return true;
      }
   }
}

