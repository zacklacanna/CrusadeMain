package me.boyjamal.main.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

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
	
	public static void summeryMessage(Player p)
	{
		if (TokenManager.getTokenSummery().containsKey(p.getUniqueId().toString()))
		{
			int tokens = TokenManager.getTokenSummery().get(p.getUniqueId().toString()).getAmount();
			int blocks = TokenManager.getTokenSummery().get(p.getUniqueId().toString()).blocksMined();
			p.sendMessage(MainUtils.chatColor(""));
			p.sendMessage(MainUtils.chatColor("&r       &d&lTOKEN SUMMERY"));
			p.sendMessage(MainUtils.chatColor(""));
			p.sendMessage(MainUtils.chatColor("&r  &7&oBlocks Mined: &d&o" + blocks));
			p.sendMessage(MainUtils.chatColor("&r  &7&oTokens Earned: &d&o" + tokens));
			p.sendMessage(" ");
			TokenManager.getTokenSummery().remove(p.getUniqueId().toString());
			return;
		} else {
			return;
		}
	}
	
}
