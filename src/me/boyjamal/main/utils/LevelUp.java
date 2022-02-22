package me.boyjamal.main.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

import me.boyjamal.main.Main;

public class LevelUp {

	public static HashMap<String,Integer> activeAnimations = new HashMap<>();
	public static HashMap<String,UUID> activeArmorStands = new HashMap<>();
	
	public static void createAnimation(Player p, String rank, String type)
	{
		Block b = p.getTargetBlock((HashSet<Byte>) null, 2);
		Location loc = b.getLocation().add(0,-.75,0);
		
		final ArmorStand as = p.getWorld().spawn(loc, ArmorStand.class);
		as.setVisible(false);
		as.setGravity(false);
	     as.setBasePlate(false);
	     as.setCustomNameVisible(true);
	     as.setArms(true);
	     as.setItemInHand(itemStack(p,rank));
	     as.setRightArmPose(new EulerAngle(Math.toRadians(-90), Math.toRadians(90), Math.toRadians(0)));
	     as.setCustomName(MainUtils.chatColor(itemStack(p, rank).getItemMeta().getDisplayName()));
	     
	     BukkitTask task = new AnimationTask(p,as).runTaskTimer(Main.getInstance(), 0,1);
	     activeAnimations.put(p.getUniqueId().toString(), task.getTaskId());
	     activeArmorStands.put(p.getUniqueId().toString(), as.getUniqueId());
	}
	
	public static ItemStack itemStack(Player p, String rank)
	{
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
		
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&d&lRANK UP"));
		item.setItemMeta(im);
		
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return item;
	}
	
}
