package me.boyjamal.main.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.boyjamal.main.Main;

public class StorageManager {

	private static File ranksFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "ranks.yml");
	private static FileConfiguration ranksYML;
	private static GuiManager rankups = null;
	
	private static File warpsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "warps.yml");
	private static FileConfiguration warpsYML;
	private static GuiManager warps = null;
	
	private static File prestigeWarpsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "prestigeWarps.yml");
	private static FileConfiguration prestigeWarpsYML;
	private static GuiManager prestigeWarps = null;
	
	private static File publicWarpsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "publicWarps.yml");
	private static FileConfiguration publicWarpsYML;
	private static GuiManager publicWarps = null;
	
	private static File donorWarpsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "donorWarps.yml");
	private static FileConfiguration donorWarpsYML;
	private static GuiManager donorWarps = null;
	
	private static File kitsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "kits.yml");
	private static FileConfiguration kitsYML;
	private static GuiManager kits = null;
	
	private static File settingsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "settings.yml");
	private static FileConfiguration settingsYML;
	private static GuiManager settings = null;
	
	private static File petsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "pets.yml");
	private static FileConfiguration petsYML;
	private static List<PetUtil> activePets = new ArrayList<>();
	
	private static File helpFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "help.yml");
	private static FileConfiguration helpYML;
	private static GuiManager helpMenu = null;
	private static FortuneUtil fortuneUtil = null;
	private static PrestigeSettings prestigeSettings = null;
	
	public static void loadFiles()
	{
		if (!(Main.getInstance().getConfig() == null))
		{
			Main.getInstance().saveDefaultConfig();
		}
		
		try {
			fortuneUtil = new FortuneUtil(Main.getInstance().getConfig().getStringList("fortune.allowed_worlds"),
					Main.getInstance().getConfig().getStringList("fortune.fortune_blocks"));
		} catch (Exception e) {
			for (int i = 1; i <=3;i++)
			System.out.println(MainUtils.chatColor("&c&lERROR &7&lCould not load Fortune Blocks"));
		}
		
		Long prestigeCost;
		List<String> prestigeRewards;
		
		try {
			prestigeCost = Main.getInstance().getConfig().getLong("prestige.cost");
			prestigeRewards = Main.getInstance().getConfig().getStringList("prestige.rewards");
			prestigeSettings = new PrestigeSettings(prestigeCost,prestigeRewards);
		} catch (Exception exc) {System.out.println("CrusadeMain >> Could not load prestiges!");}
		
		if (!(helpFile.exists()))
		{
			if(!(helpFile.getParentFile().exists()))
			{
				helpFile.getParentFile().mkdirs();
			}
			
			try {
				helpFile.createNewFile();
				copy(Main.getInstance().getResource("guis/help.yml"),helpFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} 
		
		try {
			helpYML = YamlConfiguration.loadConfiguration(helpFile);
			loadHelpGui();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (!(kitsFile.exists()))
		{
			if(!(kitsFile.getParentFile().exists()))
			{
				kitsFile.getParentFile().mkdirs();
			}
			
			try {
				kitsFile.createNewFile();
				copy(Main.getInstance().getResource("guis/kits.yml"),kitsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		try {
			kitsYML = YamlConfiguration.loadConfiguration(kitsFile);
			loadKitsGui();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (!(warpsFile.exists()))
		{
			if(!(warpsFile.getParentFile().exists()))
			{
				warpsFile.getParentFile().mkdirs();
			}
			
			try {
				warpsFile.createNewFile();
				copy(Main.getInstance().getResource("guis/warps.yml"),warpsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} 
		
		try {
			warpsYML = YamlConfiguration.loadConfiguration(warpsFile);
			loadWarpsGUI();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (!(prestigeWarpsFile.exists()))
		{
			if(!(prestigeWarpsFile.getParentFile().exists()))
			{
				prestigeWarpsFile.getParentFile().mkdirs();
			}
			
			try {
				prestigeWarpsFile.createNewFile();
				copy(Main.getInstance().getResource("guis/prestigeWarps.yml"),prestigeWarpsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} 
		
		try {
			prestigeWarpsYML = YamlConfiguration.loadConfiguration(prestigeWarpsFile);
			loadPrestigeWarps();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (!(donorWarpsFile.exists()))
		{
			if(!(donorWarpsFile.getParentFile().exists()))
			{
				donorWarpsFile.getParentFile().mkdirs();
			}
			
			try {
				donorWarpsFile.createNewFile();
				copy(Main.getInstance().getResource("guis/donorWarps.yml"),donorWarpsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} 
		
		try {
			donorWarpsYML = YamlConfiguration.loadConfiguration(donorWarpsFile);
			loadDonorWarps();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (!(publicWarpsFile.exists()))
		{
			if(!(publicWarpsFile.getParentFile().exists()))
			{
				publicWarpsFile.getParentFile().mkdirs();
			}
			
			try {
				publicWarpsFile.createNewFile();
				copy(Main.getInstance().getResource("guis/publicWarps.yml"),publicWarpsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} 
		
		try {
			publicWarpsYML = YamlConfiguration.loadConfiguration(publicWarpsFile);
			loadPublicWarps();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (!(petsFile.exists()))
		{
			if(!(petsFile.getParentFile().exists()))
			{
				petsFile.getParentFile().mkdirs();
			}
			
			try {
				petsFile.createNewFile();
				copy(Main.getInstance().getResource("guis/pets.yml"),petsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} 
		
		try {
			petsYML = YamlConfiguration.loadConfiguration(petsFile);
			//loadPets
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (!(ranksFile.exists()))
		{
			if (!(ranksFile.getParentFile().exists()))
			{
				ranksFile.getParentFile().mkdirs();
			}
			
			try {
				ranksFile.createNewFile();
				copy(Main.getInstance().getResource("guis/ranks.yml"),ranksFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		try {
			ranksYML = YamlConfiguration.loadConfiguration(ranksFile);
			loadRankupGui();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (!(settingsFile.exists()))
		{
			if (!(settingsFile.getParentFile().exists()))
			{
				settingsFile.getParentFile().mkdirs();
			}
			try {
				settingsFile.createNewFile();
				copy(Main.getInstance().getResource("guis/settings.yml"),settingsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		try {
			settingsYML = YamlConfiguration.loadConfiguration(settingsFile);
			loadSettingsGui();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static void loadHelpGui()
	{
		ConfigurationSection helpGui = helpYML.getConfigurationSection("gui");
		if (helpGui == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/help.yml"), helpFile);
				helpYML.save(helpFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		ConfigurationSection helpItems = helpGui.getConfigurationSection("items");
		if (helpItems == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/help.yml"), helpFile);
				helpYML.save(helpFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		List<GuiItem> activeItems = new ArrayList<>();
		for (String keys : helpItems.getKeys(false))
		{
			ItemStack enabled;
			if (helpItems.getConfigurationSection(keys + ".enabled") != null)
			{
				String item;
				short data;
				String name;
				List<String> lore;
				
				try {
					item = helpItems.getString(keys + ".enabled.id");
					data = (short)helpItems.getInt(keys + ".enabled.data");
					name = helpItems.getString(keys + ".enabled.name");
					lore = helpItems.getStringList(keys + ".enabled.lore");
				} catch (Exception e) {
					Bukkit.getServer().getLogger().severe("Invalid item in Help Gui (" + keys + ", ENABLED)");
					continue;
				}
				
				ItemStack temp;
				if (item != null && item.startsWith("customhead:"))
				{
					try {
						temp = MainUtils.fromBase64(item.substring(item.indexOf("customhead:")));
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Help Gui (" + keys + ", ENABLED)");
						continue;
					}
				} else {
					int typeID;
					try {
						typeID = Integer.valueOf(item);
						temp = new ItemStack(typeID,1,data);
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Help Gui (" + keys + ", ENABLED)");
						continue;
					}
				}
				
				ItemMeta tempMeta = temp.getItemMeta();
				tempMeta.setDisplayName(MainUtils.chatColor(name));
				if (helpItems.getBoolean(keys + ".enabled.hasLore"))
				{
					tempMeta.setLore(MainUtils.listColor(lore));
				}
				temp.setItemMeta(tempMeta);
				if (helpItems.getBoolean(keys + ".enabled.glow"))
				{
					tempMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					temp.setItemMeta(tempMeta);
					temp.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				enabled = temp.clone();
			} else {
				continue;
			}
			
			if (enabled != null)
			{
				List<String> actions = helpItems.getStringList(keys + ".actions");
				List<String> messages = helpItems.getStringList(keys + ".messages");
				activeItems.add(new GuiItem(new ItemCreator(false,enabled,enabled,""),Integer.valueOf(keys),actions,messages));
			}
		}
		helpMenu = new GuiManager(helpGui.getString("name"),helpGui.getInt("slot"),activeItems);
	}
	
	public static void loadKitsGui()
	{
		ConfigurationSection kitsGui = kitsYML.getConfigurationSection("kits");
		if (kitsGui == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/kits.yml"), kitsFile);
				kitsYML.save(kitsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		String name = ChatColor.translateAlternateColorCodes('&', kitsGui.getString("name"));
		int slots = kitsGui.getInt("slots");
		if (slots < 9 || slots > 54)
		{
			System.out.println(MainUtils.chatColor("&c&LERROR: &7Invalid number of slots. Set to 54"));
			slots = 54;
		}
		
		ConfigurationSection items = kitsGui.getConfigurationSection("items");
		
		List<GuiItem> activeItems = new ArrayList<>();
		for (String itemSlots : items.getKeys(false))
		{
			ItemStack hasPerm = null;
			ItemStack noPerm = null;
			String permission = items.getConfigurationSection(itemSlots).getString("permission");
			List<String> messages = MainUtils.listColor(items.getConfigurationSection(itemSlots).getStringList("messages"));
			List<String> leftClick = items.getConfigurationSection(itemSlots).getStringList("leftClick");
			List<String> rightClick = items.getConfigurationSection(itemSlots).getStringList("rightClick");
			
			if (items.getConfigurationSection(itemSlots + ".hasPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".hasPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".hasPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".hasPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 KitsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				hasPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = hasPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					hasPerm.setItemMeta(permMeta);
					hasPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				hasPerm.setItemMeta(permMeta);
			}
			
			if (items.getConfigurationSection(itemSlots + ".noPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".noPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".noPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".noPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".noPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".noPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 KitsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				noPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = noPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					noPerm.setItemMeta(permMeta);
					noPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				noPerm.setItemMeta(permMeta);
			}
			
			if (hasPerm != null && noPerm != null)
			{
				activeItems.add(new GuiItem(new ItemCreator(true,noPerm,hasPerm,permission),Integer.valueOf(itemSlots),leftClick,messages,rightClick));
			} else {
				System.out.println(MainUtils.chatColor("&c&LERROR &7Item in Kits not loaded correctly (" + itemSlots + ")"));
			}
		}
		kits = new GuiManager(name,slots,activeItems);
	}
	
	public static void loadWarpsGUI()
	{
		ConfigurationSection warpsGUI = warpsYML.getConfigurationSection("warps");
		if (warpsGUI == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/warps.yml"), warpsFile);
				warpsYML.save(warpsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		String name = MainUtils.chatColor(warpsGUI.getString("name"));
		int slots = warpsGUI.getInt("slots");
		if (slots < 9 || slots > 54)
		{
			System.out.println(MainUtils.chatColor("&c&LERROR: &7Invalid number of slots. Set to 54"));
			slots = 54;
		}
		
		ConfigurationSection items = warpsGUI.getConfigurationSection("items");
		
		List<GuiItem> activeItems = new ArrayList<>();
		for (String itemSlots : items.getKeys(false))
		{
			ItemStack hasPerm = null;
			ItemStack noPerm = null;
			String permission = items.getConfigurationSection(itemSlots).getString("permission");
			List<String> messages = MainUtils.listColor(items.getConfigurationSection(itemSlots).getStringList("messages"));
			List<String> commands = items.getConfigurationSection(itemSlots).getStringList("commands");
			
			if (items.getConfigurationSection(itemSlots + ".hasPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".hasPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".hasPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".hasPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 WarpsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				hasPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = hasPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					hasPerm.setItemMeta(permMeta);
					hasPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				hasPerm.setItemMeta(permMeta);
			}
			
			if (items.getConfigurationSection(itemSlots + ".noPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".noPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".noPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".noPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".noPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".noPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 WarpsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				noPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = noPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					noPerm.setItemMeta(permMeta);
					noPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				noPerm.setItemMeta(permMeta);
			}
			
			if (hasPerm != null && noPerm != null)
			{
				activeItems.add(new GuiItem(new ItemCreator(true,noPerm,hasPerm,permission),Integer.valueOf(itemSlots),commands,messages));
			} else {
				System.out.println(MainUtils.chatColor("&c&LERROR &7Item in Warps not loaded correctly (" + itemSlots + ")"));
			}
		}
		warps = new GuiManager(name,slots,activeItems);
	}

	public static void loadDonorWarps()
	{
		ConfigurationSection warpsGUI = donorWarpsYML.getConfigurationSection("warps");
		if (warpsGUI == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/donorWarps.yml"), donorWarpsFile);
				donorWarpsYML.save(donorWarpsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		String name = MainUtils.chatColor(warpsGUI.getString("name"));
		int slots = warpsGUI.getInt("slots");
		if (slots < 9 || slots > 54)
		{
			System.out.println(MainUtils.chatColor("&c&LERROR: &7Invalid number of slots. Set to 54"));
			slots = 54;
		}
		
		ConfigurationSection items = warpsGUI.getConfigurationSection("items");
		
		List<GuiItem> activeItems = new ArrayList<>();
		for (String itemSlots : items.getKeys(false))
		{
			ItemStack hasPerm = null;
			ItemStack noPerm = null;
			String permission = items.getConfigurationSection(itemSlots).getString("permission");
			List<String> messages = MainUtils.listColor(items.getConfigurationSection(itemSlots).getStringList("messages"));
			List<String> commands = items.getConfigurationSection(itemSlots).getStringList("commands");
			
			if (items.getConfigurationSection(itemSlots + ".hasPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".hasPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".hasPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".hasPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 DonorWarpsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				hasPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = hasPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					hasPerm.setItemMeta(permMeta);
					hasPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				hasPerm.setItemMeta(permMeta);
			}
			
			if (items.getConfigurationSection(itemSlots + ".noPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".noPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".noPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".noPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".noPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".noPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 WarpsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				noPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = noPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					noPerm.setItemMeta(permMeta);
					noPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				noPerm.setItemMeta(permMeta);
			}
			
			if (hasPerm != null && noPerm != null)
			{
				activeItems.add(new GuiItem(new ItemCreator(true,noPerm,hasPerm,permission),Integer.valueOf(itemSlots),commands,messages));
			} else {
				System.out.println(MainUtils.chatColor("&c&LERROR &7Item in Warps not loaded correctly (" + itemSlots + ")"));
			}
		}
		donorWarps = new GuiManager(name,slots,activeItems);
	}
	
	public static void loadPrestigeWarps()
	{
		ConfigurationSection warpsGUI = prestigeWarpsYML.getConfigurationSection("warps");
		if (warpsGUI == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/prestigeWarps.yml"), prestigeWarpsFile);
				prestigeWarpsYML.save(prestigeWarpsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		String name = MainUtils.chatColor(warpsGUI.getString("name"));
		int slots = warpsGUI.getInt("slots");
		if (slots < 9 || slots > 54)
		{
			System.out.println(MainUtils.chatColor("&c&LERROR: &7Invalid number of slots. Set to 54"));
			slots = 54;
		}
		
		ConfigurationSection items = warpsGUI.getConfigurationSection("items");
		
		List<GuiItem> activeItems = new ArrayList<>();
		for (String itemSlots : items.getKeys(false))
		{
			ItemStack hasPerm = null;
			ItemStack noPerm = null;
			String permission = items.getConfigurationSection(itemSlots).getString("permission");
			List<String> messages = MainUtils.listColor(items.getConfigurationSection(itemSlots).getStringList("messages"));
			List<String> commands = items.getConfigurationSection(itemSlots).getStringList("commands");
			
			if (items.getConfigurationSection(itemSlots + ".hasPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".hasPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".hasPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".hasPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 WarpsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				hasPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = hasPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					hasPerm.setItemMeta(permMeta);
					hasPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				hasPerm.setItemMeta(permMeta);
			}
			
			if (items.getConfigurationSection(itemSlots + ".noPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".noPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".noPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".noPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".noPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".noPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 PrestigeWarpsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				noPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = noPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					noPerm.setItemMeta(permMeta);
					noPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				noPerm.setItemMeta(permMeta);
			}
			
			if (hasPerm != null && noPerm != null)
			{
				activeItems.add(new GuiItem(new ItemCreator(true,noPerm,hasPerm,permission),Integer.valueOf(itemSlots),commands,messages));
			} else {
				System.out.println(MainUtils.chatColor("&c&LERROR &7Item in Warps not loaded correctly (" + itemSlots + ")"));
			}
		}
		prestigeWarps = new GuiManager(name,slots,activeItems);
	}
	
	public static void loadPublicWarps()
	{
		ConfigurationSection warpsGUI = publicWarpsYML.getConfigurationSection("warps");
		if (warpsGUI == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/publicWarps.yml"), publicWarpsFile);
				publicWarpsYML.save(publicWarpsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		String name = MainUtils.chatColor(warpsGUI.getString("name"));
		int slots = warpsGUI.getInt("slots");
		if (slots < 9 || slots > 54)
		{
			System.out.println(MainUtils.chatColor("&c&LERROR: &7Invalid number of slots. Set to 54"));
			slots = 54;
		}
		
		ConfigurationSection items = warpsGUI.getConfigurationSection("items");
		
		List<GuiItem> activeItems = new ArrayList<>();
		for (String itemSlots : items.getKeys(false))
		{
			ItemStack hasPerm = null;
			ItemStack noPerm = null;
			String permission = items.getConfigurationSection(itemSlots).getString("permission");
			List<String> messages = MainUtils.listColor(items.getConfigurationSection(itemSlots).getStringList("messages"));
			List<String> commands = items.getConfigurationSection(itemSlots).getStringList("commands");
			
			if (items.getConfigurationSection(itemSlots + ".hasPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".hasPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".hasPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".hasPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".hasPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 WarpsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				hasPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = hasPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					hasPerm.setItemMeta(permMeta);
					hasPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				hasPerm.setItemMeta(permMeta);
			}
			
			if (items.getConfigurationSection(itemSlots + ".noPerm") != null)
			{
				int id,data;
				String itemName;
				List<String> lore;
				boolean glow;
				
				try {
					id = items.getConfigurationSection(itemSlots + ".noPerm").getInt("id");
					data = items.getConfigurationSection(itemSlots + ".noPerm").getInt("data");
					itemName = MainUtils.chatColor(items.getConfigurationSection(itemSlots + ".noPerm").getString("name"));
					lore = MainUtils.listColor(items.getConfigurationSection(itemSlots + ".noPerm").getStringList("lore"));
					glow = items.getConfigurationSection(itemSlots + ".noPerm").getBoolean("glow");
				} catch (Exception exc) {
					System.out.println("&c&lERROR&7 PublicWarpsGui Item:" + itemSlots);
					exc.printStackTrace();
					continue;
				}
				
				noPerm = new ItemStack(Material.getMaterial(id), 1, (short)data);
				ItemMeta permMeta = noPerm.getItemMeta();
				permMeta.setDisplayName(itemName);
				permMeta.setLore(lore);
				if (glow)
				{
					permMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					noPerm.setItemMeta(permMeta);
					noPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				noPerm.setItemMeta(permMeta);
			}
			
			if (hasPerm != null && noPerm != null)
			{
				activeItems.add(new GuiItem(new ItemCreator(true,noPerm,hasPerm,permission),Integer.valueOf(itemSlots),commands,messages));
			} else {
				System.out.println(MainUtils.chatColor("&c&LERROR &7Item in Warps not loaded correctly (" + itemSlots + ")"));
			}
		}
		publicWarps = new GuiManager(name,slots,activeItems);
	}
	
	public static void loadSettingsGui()
	{
		ConfigurationSection settingsGui = settingsYML.getConfigurationSection("gui");
		if (settingsGui == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/settings.yml"), settingsFile);
				settingsYML.save(settingsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		ConfigurationSection settingsItems = settingsGui.getConfigurationSection("items");
		if (settingsItems == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/settings.yml"), settingsFile);
				settingsYML.save(settingsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		List<GuiItem> activeItems = new ArrayList<>();
		for (String keys : settingsItems.getKeys(false))
		{
			ItemStack enabled = null;
			ItemStack disabled = null;
			ItemStack noPerm = null;
			
			if (settingsItems.getConfigurationSection(keys + ".enabled") != null)
			{
				String item;
				short data;
				String name;
				List<String> lore;
				
				try {
					item = settingsItems.getString(keys + ".enabled.item");
					data = (short) settingsItems.getInt(keys + ".enabled.data");
					name = settingsItems.getString(keys + ".enabled.name");
					lore = settingsItems.getStringList(keys + ".enabled.lore");
				} catch (Exception e) {
					Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", ENABLED)");
					continue;
				}
				
				ItemStack temp;
				if (item.startsWith("customhead:"))
				{
					try {
						temp = MainUtils.fromBase64(item.substring(item.indexOf("customhead:")));
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", ENABLED)");
						continue;
					}
				} else {
					int typeID;
					try {
						typeID = Integer.valueOf(item);
						temp = new ItemStack(typeID,1,data);
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", ENABLED)");
						continue;
					}
				}
				ItemMeta tempMeta = temp.getItemMeta();
				tempMeta.setDisplayName(MainUtils.chatColor(name));
				if (settingsItems.getBoolean(keys + ".enabled.hasLore"))
				{
					tempMeta.setLore(MainUtils.listColor(lore));
				}
				tempMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				tempMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				temp.setItemMeta(tempMeta);
				if (settingsItems.getBoolean(keys + ".enabled.glow"))
				{
					tempMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					temp.setItemMeta(tempMeta);
					temp.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				enabled = temp.clone();
				
			}
			
			if (settingsItems.getConfigurationSection(keys + ".disabled") != null)
			{
				String item;
				short data;
				String name;
				List<String> lore;
				
				try {
					item = settingsItems.getString(keys + ".disabled.item");
					data = (short) settingsItems.getInt(keys + ".disabled.data");
					name = settingsItems.getString(keys + ".disabled.name");
					lore = settingsItems.getStringList(keys + ".disabled.lore");
				} catch (Exception e) {
					Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", DISABLED)");
					continue;
				}
				
				ItemStack temp;
				if (item.startsWith("customhead:"))
				{
					try {
						temp = MainUtils.fromBase64(item.substring(item.indexOf("customhead:")));
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", DISABLED)");
						continue;
					}
				} else {
					int typeID;
					try {
						typeID = Integer.valueOf(item);
						temp = new ItemStack(typeID,1,data);
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", DISABLED)");
						continue;
					}
				}
				ItemMeta tempMeta = temp.getItemMeta();
				tempMeta.setDisplayName(MainUtils.chatColor(name));
				if (settingsItems.getBoolean(keys + ".disabled.hasLore"))
				{
					tempMeta.setLore(MainUtils.listColor(lore));
				}
				tempMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				tempMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				
				temp.setItemMeta(tempMeta);
				if (settingsItems.getBoolean(keys + ".disabled.glow"))
				{
					tempMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					temp.setItemMeta(tempMeta);
					temp.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				disabled = temp.clone();
			}
			
			if (settingsItems.getConfigurationSection(keys + ".noPerm") != null)
			{
				String item;
				short data;
				String name;
				List<String> lore;
				
				try {
					item = settingsItems.getString(keys + ".noPerm.item");
					data = (short) settingsItems.getInt(keys + ".noPerm.data");
					name = settingsItems.getString(keys + ".noPerm.name");
					lore = settingsItems.getStringList(keys + ".noPerm.lore");
				} catch (Exception e) {
					Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", NOPERM)");
					continue;
				}
				
				ItemStack temp;
				if (item.startsWith("customhead:"))
				{
					try {
						temp = MainUtils.fromBase64(item.substring(item.indexOf("customhead:")));
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", NOPERM)");
						continue;
					}
				} else {
					int typeID;
					try {
						typeID = Integer.valueOf(item);
						temp = new ItemStack(typeID,1,data);
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Settings Gui (" + keys + ", NOPERM)");
						continue;
					}
				}
				ItemMeta tempMeta = temp.getItemMeta();
				tempMeta.setDisplayName(MainUtils.chatColor(name));
				if (settingsItems.getBoolean(keys + ".noPerm.hasLore"))
				{
					tempMeta.setLore(MainUtils.listColor(lore));
				}
				tempMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				tempMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				
				temp.setItemMeta(tempMeta);
				if (settingsItems.getBoolean(keys + ".noPerm.glow"))
				{
					tempMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					temp.setItemMeta(tempMeta);
					temp.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				noPerm = temp.clone();
			}
			
			List<String> enabledList = new ArrayList<>();
			List<String> disabledList = new ArrayList<>();
			String type = settingsItems.getString(keys + ".type");
			if (enabled != null && disabled != null && noPerm != null)
			{
				activeItems.add(new GuiItem(new ItemCreator(true,true, noPerm, disabled,enabled,Long.valueOf(0),settingsItems.getString(keys + ".perm")),Integer.valueOf(keys),enabledList,disabledList,type));
			} else if (enabled != null && disabled != null) {
				activeItems.add(new GuiItem(new ItemCreator(true,disabled,enabled,null),Integer.valueOf(keys),enabledList,disabledList,type));
			} else {
				continue;
			}
		}
		settings = new GuiManager("jamal the goat",9,activeItems);
	}
	
	public static void loadPets()
	{
		ConfigurationSection petsSection = petsYML.getConfigurationSection("pets");
		if (petsSection == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/pets.yml"), petsFile);
				petsYML.save(petsFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		for (String keys : petsSection.getKeys(false))
		{
			ConfigurationSection items = petsSection.getConfigurationSection(keys + ".item");
			if (items == null)
			{
				continue;
			}
			
			List<String> petActions = petsSection.getStringList(keys + ".actions");
			ItemStack petItem;
			for (String itemKeys : items.getKeys(false))
			{
				String id = items.getString(itemKeys + ".id");
				short data = (short)items.getInt(itemKeys + ".data");
				String name = items.getString(itemKeys + ".name");
				List<String> lore = items.getStringList(itemKeys + ".lore");
				boolean glow = items.getBoolean(itemKeys + ".glow");
				if (id.startsWith("head:"))
				{
					id = id.substring(id.indexOf("head:"));
					try {
						petItem = MainUtils.fromBase64(id);
					} catch (Exception e) {
						System.out.println(MainUtils.chatColor("&c&lERROR &7&nInvalid item in PetsFile (" + keys + ")"));
						continue;
					}
				} else {
					petItem = new ItemStack(Material.getMaterial(id),1,data);
				}
				
				ItemMeta petMeta = petItem.getItemMeta();
				petMeta.setDisplayName(MainUtils.chatColor(name));
				petMeta.setLore(MainUtils.listColor(lore));
				petItem.setItemMeta(petMeta);
				if (glow)
				{
					petMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					petItem.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				activePets.add(new PetUtil(petItem,keys,petActions));
			}
		}
	}
	
	
	public static void loadRankupGui()
	{
		ConfigurationSection ranksGui = ranksYML.getConfigurationSection("gui");
		if (ranksGui == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/ranks.yml"), ranksFile);
				ranksYML.save(ranksFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		ConfigurationSection ranksItems = ranksGui.getConfigurationSection("items");
		if (ranksItems == null)
		{
			try {
				copy(Main.getInstance().getResource("guis/ranks.yml"), ranksFile);
				ranksYML.save(ranksFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		List<GuiItem> activeItems = new ArrayList<>();
		for (String keys : ranksItems.getKeys(false))
		{
			ItemStack access = null;
			ItemStack locked = null;
			ItemStack canRankup = null;
			long cost;
			try {
				cost = ranksItems.getLong(keys + ".cost");
			} catch (Exception e) {
				cost = 0;
			}
			
			if (ranksItems.getConfigurationSection(keys + ".access") != null)
			{
				String item;
				short data;
				String name;
				List<String> lore;
				try {
					item = ranksItems.getString(keys + ".access.item");
					data = (short) ranksItems.getInt(keys + ".access.data");
					name = ranksItems.getString(keys + ".access.name");
					lore = ranksItems.getStringList(keys + ".access.lore");
				} catch (Exception e) {
					Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", ACCESS)");
					continue;
				}
				
				ItemStack temp;
				if (item.startsWith("customhead:"))
				{
					try {
						temp = MainUtils.fromBase64(item.substring(item.indexOf("customhead:")));
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", ACCESS)");
						continue;
					}
				} else {
					int typeID;
					try {
						typeID = Integer.valueOf(item);
						temp = new ItemStack(typeID,1,data);
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", ACCESS)");
						continue;
					}
				}
				ItemMeta tempMeta = temp.getItemMeta();
				tempMeta.setDisplayName(MainUtils.chatColor(name));
				if (ranksItems.getBoolean(keys + ".access.hasLore"))
				{
					tempMeta.setLore(MainUtils.listColor(lore));
				}
				temp.setItemMeta(tempMeta);
				if (ranksItems.getBoolean(keys + ".access.glow"))
				{
					tempMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					temp.setItemMeta(tempMeta);
					temp.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				access = temp.clone();
			}
			
			if (ranksItems.getConfigurationSection(keys + ".locked") != null)
			{
				String item;
				short data;
				String name;
				List<String> lore;
				try {
					item = ranksItems.getString(keys + ".locked.item");
					data = (short) ranksItems.getInt(keys + ".locked.data");
					name = ranksItems.getString(keys + ".locked.name");
					lore = ranksItems.getStringList(keys + ".locked.lore");
				} catch (Exception e) {
					Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", LOCKED)");
					continue;
				}
				
				ItemStack temp;
				if (item.startsWith("customhead:"))
				{
					try {
						temp = MainUtils.fromBase64(item.substring(item.indexOf("customhead:")));
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", ACCESS)");
						continue;
					}
				} else {
					int typeID;
					try {
						typeID = Integer.valueOf(item);
						temp = new ItemStack(typeID,1,data);
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", ACCESS)");
						continue;
					}
				}
				ItemMeta tempMeta = temp.getItemMeta();
				tempMeta.setDisplayName(MainUtils.chatColor(name));
				if (ranksItems.getBoolean(keys + ".locked.hasLore"))
				{
					tempMeta.setLore(MainUtils.listColor(lore));
				}
				temp.setItemMeta(tempMeta);
				if (ranksItems.getBoolean(keys + ".locked.glow"))
				{
					tempMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					temp.setItemMeta(tempMeta);
					temp.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				locked = temp.clone();
			}
			
			if (ranksItems.getConfigurationSection(keys + ".canRankup") != null)
			{
				String item;
				short data;
				String name;
				List<String> lore;
				try {
					item = ranksItems.getString(keys + ".canRankup.item");
					data = (short) ranksItems.getInt(keys + ".canRankup.data");
					name = ranksItems.getString(keys + ".canRankup.name");
					lore = ranksItems.getStringList(keys + ".canRankup.lore");
				} catch (Exception e) {
					Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", RANKUP)");
					continue;
				}
				
				ItemStack temp;
				if (item.startsWith("customhead:"))
				{
					try {
						temp = MainUtils.fromBase64(item.substring(item.indexOf("customhead:")));
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", RANKUP)");
						continue;
					}
				} else {
					int typeID;
					try {
						typeID = Integer.valueOf(item);
						temp = new ItemStack(typeID,1,data);
					} catch (Exception e) {
						Bukkit.getServer().getLogger().severe("Invalid item in Ranks Gui (" + keys + ", RANKUP)");
						continue;
					}
				}
				ItemMeta tempMeta = temp.getItemMeta();
				tempMeta.setDisplayName(MainUtils.chatColor(name));
				if (ranksItems.getBoolean(keys + ".canRankup.hasLore"))
				{
					tempMeta.setLore(MainUtils.listColor(lore));
				}
				temp.setItemMeta(tempMeta);
				if (ranksItems.getBoolean(keys + ".canRankup.glow"))
				{
					tempMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					temp.setItemMeta(tempMeta);
					temp.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				canRankup = temp.clone();
			}
			
			if (access != null && locked != null && canRankup != null)
			{
				List<String> actions = ranksItems.getStringList(keys + ".actions");
				List<String> secondActions = ranksItems.getStringList(keys + ".rankedUp");
				List<String> noPerm = ranksItems.getStringList(keys + ".noPerm");
				String permission = ranksItems.getString(keys + ".permission");
				activeItems.add(new GuiItem(new ItemCreator(true,true,locked,canRankup,access,cost,permission),
						Integer.valueOf(keys),actions,noPerm,secondActions));
			} else if (access != null && locked != null) {
				List<String> actions = ranksItems.getStringList(keys + ".actions");
				List<String> secondActions = ranksItems.getStringList(keys + ".rankedUp");
				List<String> noPerm = ranksItems.getStringList(keys + ".noPerm");
				String permission = ranksItems.getString(keys + ".permission");
				activeItems.add(new GuiItem(new ItemCreator(true,locked,access,permission),
						Integer.valueOf(keys),actions,noPerm,secondActions));
			} else {
				continue;
			}
		}
		
		String name = ranksGui.getString("name");
		int slots = ranksGui.getInt("slots");
		rankups = new GuiManager(MainUtils.chatColor(name),slots,activeItems);
	}
	
	private static void copy(InputStream in, File file)
    {
    	try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static GuiManager getRankups()
	{
		return rankups;
	}
	
	public static List<PetUtil> getPets()
	{
		return activePets;
	}
	
	public static GuiManager getHelpMenu()
	{
		return helpMenu;
	}
	
	public static GuiManager getSettings()
	{
		return settings;
	}
	
	public static GuiManager getWarps()
	{
		return warps;
	}
	
	public static PrestigeSettings getPrestigeSettings()
	{
		return prestigeSettings;
	}
	
	public static GuiManager getKits()
	{
		return kits;
	}
	public static GuiManager getDonorWarps()
	{
		return donorWarps;
	}
	public static GuiManager getPrestigeWarps()
	{
		return prestigeWarps;
	}
	public static GuiManager getPublicWarps()
	{
		return publicWarps;
	}
	
	public static FortuneUtil getFortune()
	{
		return fortuneUtil;
	}
	
}
