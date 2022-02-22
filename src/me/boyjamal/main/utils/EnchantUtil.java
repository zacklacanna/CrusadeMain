package me.boyjamal.main.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.earth2me.essentials.craftbukkit.SetExpFix;
import com.vk2gpz.tokenenchant.TokenEnchant;
import com.vk2gpz.tokenenchant.api.CEHandler;
import com.vk2gpz.tokenenchant.api.EnchantHandler;
import com.vk2gpz.tokenenchant.api.PotionHandler;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

import me.PM2.customcrates.SpecializedCrates;
import me.boyjamal.main.Main;

public class EnchantUtil {

    private List<EnchantItem> enchantItems = new ArrayList<>();
	
	public void loadEnchantItems()
	{
		enchantItems.add(new EnchantItem(11,"Wipeout"));
		enchantItems.add(new EnchantItem(12,"Explosive"));
		enchantItems.add(new EnchantItem(13,"Drill"));
		enchantItems.add(new EnchantItem(14,"Haste"));
		enchantItems.add(new EnchantItem(19,"Laser"));
		enchantItems.add(new EnchantItem(20,"Keyfinder"));
		enchantItems.add(new EnchantItem(21,"Efficiency"));
		enchantItems.add(new EnchantItem(22,"Unbreaking"));
		enchantItems.add(new EnchantItem(23,"Fortune"));
		enchantItems.add(new EnchantItem(24,"Speed"));
		enchantItems.add(new EnchantItem(25,"Greedy"));
		enchantItems.add(new EnchantItem(30,"Excavator"));
		enchantItems.add(new EnchantItem(31,"Blessing"));
		enchantItems.add(new EnchantItem(32,"Detonate"));
	}
	
	public boolean isPickaxe(ItemStack hand)
	{
		if (hand == null)
		{
			return false;
		}
		
		if (hand.getType() == Material.DIAMOND_PICKAXE)
		{
			return true;
		} else if (hand.getType() == Material.IRON_PICKAXE) {
			return true;
		} else if (hand.getType() == Material.GOLD_PICKAXE) {
			return true;
		} else if (hand.getType() == Material.WOOD_PICKAXE) {
			return true;
		} else if (hand.getType() == Material.STONE_PICKAXE) {
			return true;
		} else {
			return false;
		}
	}
	
	public Inventory getEnchantGUI(Player p, ItemStack pickaxe)
	{
		Inventory inv = Bukkit.createInventory(null, 54,MainUtils.chatColor("&8&nEnchant GUI"));
		
		setEnchantItems(inv,pickaxe);
		
		for (int i = 0; i <= 10; i++)
		{
			inv.setItem(i, filler());
		}
		
		for (int i = 15; i <=18; i++)
		{
			inv.setItem(i, filler());
		}

		for (int i = 26; i <= 29; i++)
		{
			inv.setItem(i, filler());
		}
		
		//enchant item from 
		
		//enchant item on 40
		//inv.setItem(40, pickaxe);
		
		for (int i = 33; i <= 44; i++)
		{
			if (i == 37)
			{
				inv.setItem(i, sellItems());
			} else if (i == 43) {
				inv.setItem(i, multi());
			} else {
			    inv.setItem(i, filler());
			}
		}
		
		for (int i = 46; i <= 52; i++)
		{
			if (i == 49)
			{
				inv.setItem(i, pickaxe);
			} else if (i == 47) {
				inv.setItem(i, redeemXp(p));
			} else {
				inv.setItem(i, filler());
			}
		}
		
		
		inv.setItem(45, tokenItem(p));
		inv.setItem(53, tokenShop());
		return inv;
	}
	
	//essential
	//rare
	//epic
	//legendary
	
	public void setEnchantItems(Inventory inv,ItemStack pickaxe)
	{
		createEnchantItems(inv,11,pickaxe,"Wipeout","&cLegendary",new ItemStack(Material.FIREBALL),"&c&lWipeout","&7Chance to wipeout the entire mine!");
		createEnchantItems(inv,12,pickaxe,"Explosive","&bRare",new ItemStack(Material.TNT),"&b&lExplosive","&7Chance to explosde nearby blocks!");
		createEnchantItems(inv,13,pickaxe,"Drill","&cLegendary",new ItemStack(Material.HOPPER),"&c&lDrill","&7Chance to drill a vertical hole in mine!");
		createEnchantItems(inv,14,pickaxe,"Haste","&bRare",new ItemStack(Material.BLAZE_POWDER),"&b&lHaste","&7Increase pickaxe speed!");
		createEnchantItems(inv,19,pickaxe,"Laser","&cLegendary",new ItemStack(Material.BLAZE_ROD),"&c&lLaser","&7Chance to shoot a 3x3x3 laser into the mine!");
		createEnchantItems(inv,20,pickaxe,"Keyfinder","&dEpic",new ItemStack(Material.CHEST),"&d&lKeyfinder","&7Chance to find keys while you mine!");
		createEnchantItems(inv,21,pickaxe,"Efficiency","&fEssential",new ItemStack(Material.DIAMOND_PICKAXE),"&f&lEfficiency","&7Ability to break blocks faster!");
		createEnchantItems(inv,22,pickaxe,"Unbreaking","&fEssential",new ItemStack(Material.ANVIL),"&f&lUnbreaking","&7Increase durability of pickaxe!");
		createEnchantItems(inv,23,pickaxe,"Fortune","&fEssential",new ItemStack(Material.DIAMOND),"&f&lFortune","&7Increase amount of ores dropped!");
		createEnchantItems(inv,24,pickaxe,"Speed","&bRare",new ItemStack(Material.SUGAR),"&b&lSpeed","&7Increase movement speed!");
		createEnchantItems(inv,25,pickaxe,"Greedy","&cLegendary",new ItemStack(Material.RED_ROSE),"&c&lGreedy","&7Chance to find extra tokens while mining!");
		createEnchantItems(inv,30,pickaxe,"Excavator","&dEpic",new ItemStack(Material.BONE),"&d&lExcavator","&7Chance to excavate entire layer of mine!");
		createEnchantItems(inv,31,pickaxe,"Blessing","&dEpic",new ItemStack(Material.NETHER_STAR), "&d&lBlessing", "&7Chance to give all online players tokens!");
		createEnchantItems(inv,32,pickaxe,"Detonate","&dEpic",new ItemStack(Material.EYE_OF_ENDER), "&d&lDetonate", "&7Chance to fire mineboms when mining!");
	}
	
	public void createEnchantItems(Inventory inv, int slot, ItemStack pickaxe, String name, String tier, ItemStack guiItem, String displayName, String description)
	{
		ItemMeta im = guiItem.getItemMeta();
		CEHandler enchant = TokenEnchantAPI.getInstance().getEnchantment(name);
		if (enchant == null)
		{
			System.out.println(MainUtils.chatColor("&c&lERROR: &7&oCould not load " + name+ " Enchant!"));
			return;
		}
		double price = enchant.getPrice();
		int level = enchant.getCELevel(pickaxe);
		
		im.setDisplayName(MainUtils.chatColor(displayName));
		List<String> lore = new ArrayList<>();
			lore.add(MainUtils.chatColor(description));
			lore.add(MainUtils.chatColor("&r"));
			lore.add(MainUtils.chatColor("&7Level: &f" + level));
			lore.add(MainUtils.chatColor("&7Max Level: &f" + enchant.getMaxLevel()));
			
			DecimalFormat format = new DecimalFormat("#,###");
			lore.add(MainUtils.chatColor("&7Price: &f" + format.format(price) + " tokens per level!"));
			lore.add(MainUtils.chatColor("&7Tier: &f" + tier));
			lore.add("");
			if (level >= enchant.getMaxLevel())
			{
				lore.add(MainUtils.chatColor("&c&nMax Level!"));
			} else {
				lore.add(MainUtils.chatColor("&7Left click to buy one level"));
				lore.add(MainUtils.chatColor("&7Right click to buy <x> levels"));
			}
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		guiItem.setItemMeta(im);
		guiItem.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		inv.setItem(slot, guiItem);
	}
	
	public ItemStack redeemXp(Player p)
	{
		ItemStack item = new ItemStack(Material.EXP_BOTTLE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&a&lRedeem Expierence"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7Click to exchange your"));
		lore.add(MainUtils.chatColor("&7expierence for tokens!"));
		lore.add(MainUtils.chatColor("&r"));
		lore.add(MainUtils.chatColor("&aRedeem Rate:"));
		lore.add(MainUtils.chatColor("&7- 50 EXP per Token"));
		lore.add(MainUtils.chatColor("&r"));
		lore.add(MainUtils.chatColor("&a&nExpierence:&7 " + MainUtils.getPlayerExperience(p) + " EXP"));
		
		im.setLore(lore);;
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack multi()
	{
		ItemStack item = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&c&lActive Multipliers"));
		List<String> lore = new ArrayList<String>();
		lore.add(MainUtils.chatColor("&7Click to open your"));
		lore.add(MainUtils.chatColor("&7current multipliers!"));
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return item;
	}
	
	
	public ItemStack sellItems()
	{
		ItemStack item = new ItemStack(Material.EMPTY_MAP);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&a&lSell Items"));
		
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7Click to sell all your items"));
		lore.add(MainUtils.chatColor("&7currently in your inventory!"));
		lore.add("");
		lore.add(MainUtils.chatColor("&7Only valid when standing &a&ninside&7 of a mine!"));
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return item;	
	}
	
	public ItemStack tokenItem(Player p)
	{
		String tokens = TokenEnchant.getInstance().getTokensInString(p);
		ItemStack item = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&e&lTokens"));
		
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7You have &e" + tokens + "&7 tokens!"));
		lore.add(" ");
		lore.add(MainUtils.chatColor("&7Right click to withdraw tokens!"));
		im.setLore(lore);
		
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack tokenShop()
	{
		ItemStack item = new ItemStack(Material.ENDER_CHEST);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&d&lToken Shop"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7Tokens that are mined at each"));
		lore.add(MainUtils.chatColor("&7mine can be used to buy OP"));
		lore.add(MainUtils.chatColor("&7items and perks to use on server!"));
		lore.add(MainUtils.chatColor(""));
		lore.add(MainUtils.chatColor("&7Click to open tokenshop!"));
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack filler()
	{
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(" ");
		item.setItemMeta(im);
		return item;
	}
	
	public List<EnchantItem> getEnchantItems()
	{
		return enchantItems;
	}
	
}
