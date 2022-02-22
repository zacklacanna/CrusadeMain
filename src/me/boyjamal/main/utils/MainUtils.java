package me.boyjamal.main.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.earth2me.essentials.craftbukkit.SetExpFix;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.HeadAPI;

public class MainUtils {

	public static String chatColor(String message)
	{
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static List<String> listColor(List<String> message)
	{
		List<String> newList = new ArrayList<>();
		for (String line : message)
		{
			newList.add(chatColor(line));
		}
		return newList;
	}
	
	public static void redeemXP(Player p)
	{
		int tokens = getPlayerExperience(p)/50;
		int newEXP = getPlayerExperience(p)-(50*tokens);
		
		if (tokens == 0)
		{
			p.sendMessage(MainUtils.chatColor("&d&o&lXP &7You do not have enough XP for that!"));
			return;
		}
		
		p.setLevel(0);
		p.setExp(0.0F);
        p.setTotalExperience(0);
        
        
		p.giveExp(newEXP);
		
		p.sendMessage(MainUtils.chatColor("&d&o&lXP &7You have claimed &d" + tokens + " Tokens &7for &d" + tokens*50 + "EXP"));
		TokenManager.addToken(p, tokens);
	}
	
	public static int deltaLevelToExp(int level) {
	      if (Main.getInstance().getServer().getVersion().contains("1_7")) {
	         if (level <= 15) {
	            return 17;
	         } else {
	            return level <= 30 ? 3 * level - 31 : 7 * level - 155;
	         }
	      } else if (level <= 15) {
	         return 2 * level + 7;
	      } else {
	         return level <= 30 ? 5 * level - 38 : 9 * level - 158;
	      }
	   }
	
	public static int levelToExp(int level) {
	      if (Main.getInstance().getServer().getBukkitVersion().contains("1_7")) {
	         if (level <= 15) {
	            return 17 * level;
	         } else {
	            return level <= 30 ? 3 * level * level / 2 - 59 * level / 2 + 360 : 7 * level * level / 2 - 303 * level / 2 + 2220;
	         }
	      } else if (level <= 15) {
	         return level * level + 6 * level;
	      } else {
	         return level <= 30 ? (int)(2.5D * (double)level * (double)level - 40.5D * (double)level + 360.0D) : (int)(4.5D * (double)level * (double)level - 162.5D * (double)level + 2220.0D);
	      }
	   }
	
	 public static int getPlayerExperience(Player player) {
	      int bukkitExp = levelToExp(player.getLevel()) + Math.round((float)deltaLevelToExp(player.getLevel()) * player.getExp());
	      return bukkitExp;
	   }
	
	public static Integer getInvSize(int val)
	{
		if (val >=0 && val<=9)
		{
			return 9;
		} else if (val > 9 && val <=18) {
			return 18;
		} else if (val > 18 && val <=27) {
			return 27;
		} else if (val > 27 && val <=36) {
			return 36;
		} else if (val > 36 && val <=45) {
			return 45;
		} else if (val > 45 && val <=54) {
			return 54;
		} else {
			return 54;
		}
	}
	
	public static double getPrice(ItemStack item)
	{
		if (item.getType() == Material.DIAMOND_PICKAXE || item.getType() == Material.IRON_PICKAXE ||
				item.getType() == Material.GOLD_PICKAXE || item.getType() == Material.STONE_PICKAXE ||
				item.getType() == Material.WOOD_PICKAXE) {
			return 0;
		} else if (item.getType() == Material.DIAMOND_HELMET) {
			return 1000000;
		} else if (item.getType() == Material.DIAMOND_CHESTPLATE) {
			return 500000;
		} else if (item.getType() == Material.DIAMOND_LEGGINGS) {
			return 500000;
		} else if (item.getType() == Material.DIAMOND_BOOTS) {
			return 750000;
		} else if (item.getType() == Material.DIAMOND_SWORD) {
			return 750000;
		} else if (item.getType() == Material.DIAMOND_SPADE) {
			return 100000;
		} else {
			return -1;
		}
	}
	
	public static boolean isNumber(String string) {
	      try {
	         Long.parseLong(string);
	         return true;
	      } catch (Exception var2) {
	         return false;
	      }
	   }
	
	public static boolean isPickaxe(ItemStack item)
	{
		if (item.getType() == Material.DIAMOND_PICKAXE)
		{
			return true;
		} else if (item.getType() == Material.GOLD_PICKAXE) {
			return true;
		} else if (item.getType() == Material.IRON_PICKAXE) {
			return true;
		} else if (item.getType() == Material.STONE_PICKAXE) {
			return true;
		} else if (item.getType() == Material.WOOD_PICKAXE) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ItemStack fromBase64(String base64)
	  {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);            
		Property textures = new Property("textures", base64); 
	    SkullMeta meta = (SkullMeta) item.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), "default.name");
		    profile.getProperties().put("textures", textures);
		    setPrivateField(meta.getClass(), meta, "profile", profile);
		    item.setItemMeta(meta);
		return item;
	  }
	
	public static void setPrivateField(Class type, Object object, String name, Object value) {
      try {
          Field field = type.getDeclaredField(name);
          field.setAccessible(true);
          field.set(object, value);
          field.setAccessible(false);
      } catch (ReflectiveOperationException e) {
          e.printStackTrace();
      }
    }
	
	public static double tokenMultiplier(Player p)
	{
		String type = "tokenenchant.multiplier.";
		if (p.hasPermission(type + "2.4"))
		{
			return 2.4;
		} else if (p.hasPermission(type + "2.2")) {
			return 2.2;
		} else if (p.hasPermission(type + "2.0")) {
			return 2.0;
		} else if (p.hasPermission(type + "1.8")) {
			return 1.8;
		} else if (p.hasPermission(type + "1.6")) {
			return 1.6;
		} else if (p.hasPermission(type + "1.4")) {
			return 1.4;
		} else if (p.hasPermission(type + "1.2")) {
			return 1.2;
		} else {
			return 1.0;
		}
	}
	
	public static void summeryMessage(Player p)
	{
		if (TokenManager.getTokenSummery().containsKey(p.getUniqueId().toString()))
		{
			int tokens = TokenManager.getTokenSummery().get(p.getUniqueId().toString()).getAmount();
			tokens = (int) Math.round(tokenMultiplier(p) * tokens);
			int blocks = TokenManager.getTokenSummery().get(p.getUniqueId().toString()).blocksMined();
			p.sendMessage(MainUtils.chatColor(""));
			p.sendMessage(MainUtils.chatColor("&r       &d&lTOKEN SUMMERY"));
			p.sendMessage(MainUtils.chatColor(""));
			p.sendMessage(MainUtils.chatColor("&r  &7&oBlocks Mined: &d&o" + blocks));
			p.sendMessage(MainUtils.chatColor("&r  &7&oTokens Found: &d&o" + tokens));
			p.sendMessage(" ");
			TokenManager.getTokenSummery().remove(p.getUniqueId().toString());
			return;
		} else {
			return;
		}
	}
	
	public static ItemStack stringToDisplayItem(String string) {
	      Material material;
	      short durability;
	      if (string.split(" ")[0].contains(":")) {
	         if (isNumber(string.split(" ")[0].split(":")[0])) {
	            material = Material.getMaterial(Integer.parseInt(string.split(" ")[0].split(":")[0]));
	            durability = Short.parseShort(string.split(" ")[0].split(":")[1]);
	         } else {
	            material = Material.getMaterial(string.split(" ")[0].split(":")[0].toUpperCase());
	            durability = Short.parseShort(string.split(" ")[0].split(":")[1]);
	         }
	      } else if (isNumber(string.split(" ")[0])) {
	         material = Material.getMaterial(Integer.parseInt(string.split(" ")[0]));
	         durability = 0;
	      } else {
	         material = Material.getMaterial(string.split(" ")[0].toUpperCase());
	         durability = 0;
	      }

	      int amount = Integer.parseInt(string.split(" ")[1]);
	      String displayName = "";
	      List<String> lore = new ArrayList();
	      Map<Enchantment, Integer> enchantments = Maps.newHashMap();
	      boolean glow = false;
	      if (string.split(" ").length > 1) {
	         List lines;
	         String s;
	         Iterator var11;
	         int var12;
	         String[] var13;
	         String[] list;
	         int var16;
	         label94:
	         switch(string.split(" ").length) {
	         case 3:
	            displayName = chatColor(string.split(" ")[2]).replace("_", " ");
	            break;
	         case 4:
	            displayName = chatColor(string.split(" ")[2]).replace("_", " ");
	            if (string.split(" ")[3].contains(";")) {
	               lines = Arrays.asList(string.split(" ")[3].split(";"));
	               var11 = lines.iterator();

	               while(true) {
	                  if (!var11.hasNext()) {
	                     break label94;
	                  }

	                  s = (String)var11.next();
	                  ((List)lore).add(chatColor(s).replace("_", " "));
	               }
	            } else {
	               lore = new ExtendedList(new String[]{chatColor(string.split(" ")[3]).replace("_", " ")});
	               break;
	            }
	         case 5:
	            displayName = chatColor(string.split(" ")[2]).replace("_", " ");
	            if (string.split(" ")[3].contains(";")) {
	               lines = Arrays.asList(string.split(" ")[3].split(";"));
	               var11 = lines.iterator();

	               while(var11.hasNext()) {
	                  s = (String)var11.next();
	                  ((List)lore).add(chatColor(s).replace("_", " "));
	               }
	            } else {
	               lore = new ExtendedList(new String[]{chatColor(string.split(" ")[3]).replace("_", " ")});
	            }

	            if (string.split(" ")[4].contains(";")) {
	               list = string.split(" ")[4].split(";");
	               var13 = list;
	               var12 = list.length;
	               var16 = 0;

	               while(true) {
	                  if (var16 >= var12) {
	                     break label94;
	                  }

	                  s = var13[var16];
	                  enchantments.put(Enchantment.getById(Integer.parseInt(s.split(":")[0])), Integer.valueOf(s.split(":")[1]));
	                  ++var16;
	               }
	            } else {
	               enchantments.put(Enchantment.getById(Integer.parseInt(string.split(" ")[4].split(":")[0])), Integer.valueOf(string.split(" ")[4].split(":")[1]));
	               break;
	            }
	         case 6:
	            displayName = chatColor(string.split(" ")[2]).replace("_", " ");
	            if (string.split(" ")[3].contains(";")) {
	               lines = Arrays.asList(string.split(" ")[3].split(";"));
	               var11 = lines.iterator();

	               while(var11.hasNext()) {
	                  s = (String)var11.next();
	                  ((List)lore).add(chatColor(s).replace("_", " "));
	               }
	            } else {
	               lore = new ExtendedList(new String[]{chatColor(string.split(" ")[3]).replace("_", " ")});
	            }

	            if (string.split(" ")[4].contains(";")) {
	               list = string.split(" ")[4].split(";");
	               var13 = list;
	               var12 = list.length;

	               for(var16 = 0; var16 < var12; ++var16) {
	                  s = var13[var16];
	                  enchantments.put(Enchantment.getById(Integer.parseInt(s.split(":")[0])), Integer.valueOf(s.split(":")[1]));
	               }
	            } else {
	               enchantments.put(Enchantment.getById(Integer.parseInt(string.split(" ")[4].split(":")[0])), Integer.valueOf(string.split(" ")[4].split(":")[1]));
	            }

	            glow = Boolean.parseBoolean(string.split(" ")[5]);
	         }
	      }

	      ItemStack is = new ItemStack(material);
	      is.setAmount(amount);
	      is.setDurability(durability);
	      is.addUnsafeEnchantments(enchantments);
	      ItemMeta im = is.getItemMeta();
	      if (glow) {
	         im.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
	      }

	      if (displayName.equalsIgnoreCase("")) {
	         im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r"));
	      } else {
	         im.setDisplayName(displayName);
	      }

	      im.setLore((List)lore);
	      is.setItemMeta(im);
	      return is;
	   }
	
	public static ItemStack playerHead(OfflinePlayer player, String name)
	{
		String id = HeadAPI.addPlayerHead(player.getName(),player.getUniqueId());
		ItemStack item = HeadAPI.getHead(id);
		SkullMeta meta = (SkullMeta)item.getItemMeta();
		meta.setDisplayName(MainUtils.chatColor("&d&n" + name));
		meta.setOwner(name);
		item.setItemMeta(meta);
		return item;
	}
	
}
