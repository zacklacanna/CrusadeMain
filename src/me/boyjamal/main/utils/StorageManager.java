package me.boyjamal.main.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
	
	private static File settingsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "settings.yml");
	private static FileConfiguration settingsYML;
	private static GuiManager settings = null;
	
	private static File petsFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "pets.yml");
	private static FileConfiguration petsYML;
	private static List<PetUtil> activePets = new ArrayList<>();
	
	private static File settingsData = new File(Main.getInstance().getDataFolder() + File.separator + "data" + File.separator + "settings.data");
	
	private static File helpFile = new File(Main.getInstance().getDataFolder() + File.separator + "guis" + File.separator + "help.yml");
	private static FileConfiguration helpYML;
	private static GuiManager helpMenu = null;
	private static FortuneUtil fortuneUtil = null;
	
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
			if (!(settingsData.exists()))
			{
				if (!(settingsData.getParentFile().exists()))
				{
					settingsData.getParentFile().mkdirs();
				}
				settingsData.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		try {
			settingsYML = YamlConfiguration.loadConfiguration(settingsFile);
			loadSettingsGui();
			PlayerSettings.loadSettings();
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
				if (item.startsWith("customhead:"))
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
				temp.setItemMeta(tempMeta);
				if (settingsItems.getBoolean(keys + ".disabled.glow"))
				{
					tempMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					temp.setItemMeta(tempMeta);
					temp.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				
				disabled = temp.clone();
			}
			
			List<String> enabledList = new ArrayList<>();
			List<String> disabledList = new ArrayList<>();
			String type = settingsGui.getString(keys + ".type");
			if (enabled != null && disabled != null)
			{
				activeItems.add(new GuiItem(new ItemCreator(true,disabled,enabled,""),Integer.valueOf(keys)-1,enabledList,disabledList,type));
			} else if (enabled != null) {
				activeItems.add(new GuiItem(new ItemCreator(true,null,enabled,""),Integer.valueOf(keys)-1,enabledList,disabledList,type));
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
				List<String> noPerm = ranksItems.getStringList(keys + ".noPerm");
				String permission = ranksItems.getString(keys + ".permission");
				activeItems.add(new GuiItem(new ItemCreator(true,true,locked,canRankup,access,cost,permission),
						Integer.valueOf(keys),actions,noPerm));
			} else if (access != null && locked != null) {
				List<String> actions = ranksItems.getStringList(keys + ".actions");
				List<String> noPerm = ranksItems.getStringList(keys + ".noPerm");
				String permission = ranksItems.getString(keys + ".permission");
				activeItems.add(new GuiItem(new ItemCreator(true,locked,access,permission),
						Integer.valueOf(keys),actions,noPerm));
			} else {
				continue;
			}
		}
		
		String name = ranksGui.getString("name");
		int slots = ranksGui.getInt("slots");
		rankups = new GuiManager(name,slots,activeItems);
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
	
	public static FortuneUtil getFortune()
	{
		return fortuneUtil;
	}
	
	public static File getSettingsData()
	{
		return settingsData;
	}
	
}
