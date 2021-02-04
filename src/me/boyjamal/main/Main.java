package me.boyjamal.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.vk2gpz.tokenenchant.TokenEnchant;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import me.boyjamal.main.commands.Busy;
import me.boyjamal.main.commands.Discord;
import me.boyjamal.main.commands.FixHand;
import me.boyjamal.main.commands.HideFlags;
import me.boyjamal.main.commands.Pets;
import me.boyjamal.main.commands.Rankup;
import me.boyjamal.main.commands.Settings;
import me.boyjamal.main.commands.Trash;
import me.boyjamal.main.commands.TriggerAnimation;
import me.boyjamal.main.commands.Wings;
import me.boyjamal.main.listeners.BusyListener;
import me.boyjamal.main.listeners.FortuneListener;
import me.boyjamal.main.listeners.NightVision;
import me.boyjamal.main.listeners.RanksListener;
import me.boyjamal.main.listeners.RecipeBlocker;
import me.boyjamal.main.listeners.RegionListener;
import me.boyjamal.main.listeners.SettingsListener;
import me.boyjamal.main.listeners.TntBlocker;
import me.boyjamal.main.utils.FortuneUtil;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.StorageManager;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private Economy econ;
	private TokenEnchantAPI tokens;
	private boolean titlesEnabled = false;
	private boolean blocksEnabled = false;
	
	public void onEnable()
	{
		instance = this;
		
		if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		StorageManager.loadFiles();
		registerCommands();
		registerListeners();

		ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(this,  ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
                    @Override
                    public void onPacketSending(PacketEvent e) {
                        String soundName = e.getPacket().getStrings().read(0);
                        Player p = e.getPlayer();
                        if(e.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT) 
                        {
                           if((soundName.equals("mob.wither") || (soundName.equals("mob.wither.idle")) ||(soundName.equals("entity.wither.shoot")))) 
                           {
                               e.setCancelled(true);
                           }
                           
                           if (PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString()))
                           {
                        	   if (PlayerSettings.activeSettings.get(p.getUniqueId().toString()).hasMineSounds())
                        	   {
                        		   if((soundName.equals("random.explode") || (soundName.equals("random.dig")))) 
                        		   {
                                       e.setCancelled(true);
                                   }
                        	   }
                           }
                        }
                    }
                }
        );
		
		if (Bukkit.getPluginManager().isPluginEnabled("TokenEnchant"))
		{
			tokens = TokenEnchant.getInstance();
		}
		
		if (Bukkit.getPluginManager().isPluginEnabled("EZBlocks"))
		{
			blocksEnabled = true;
		}
		
		if (Bukkit.getPluginManager().isPluginEnabled("TitleAPI"))
		{
			titlesEnabled = true;
		}
		
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI"))
		{
			PlaceholderAPI.registerPlaceholder(this, "rankname",
					new PlaceholderReplacer() {
					
					public String onPlaceholderReplace(PlaceholderReplaceEvent evt)
					{
						Player p = evt.getPlayer();
						if (p != null)
						{
							String type = "crusademc.rank.";
							if (p.hasPermission(type + "owner"))
							{
								return "Owner";
							} else if (p.hasPermission(type + "admin")) {
								return "Admin";
							} else if (p.hasPermission(type + "mod")) {
								return "Mod";
							} else if (p.hasPermission(type + "builder")) {
								return "Builder";
							} else if (p.hasPermission(type + "youtuber")) {
								return "YouTube";
							} else if (p.hasPermission(type + "hercules")) {
								return "Hercules";
							} else if (p.hasPermission(type + "phoenix")) {
								return "Phoenix";
							} else if (p.hasPermission(type + "pegasus")) {
								return "Pegasus";
							} else if (p.hasPermission(type + "cyclops")) {
								return "Cyclops";
							} else if (p.hasPermission(type + "leviathan")) {
								return "Leviathan";
							} else if (p.hasPermission(type + "cerberus")) {
								return "Cerberus";
							} else {
								return "Default";
							}
						} else {
							return "Default";
						}
					}
			});
		}
	}
	
	public void onDisable()
	{
		//save player settings
	}
	
	public void registerListeners()
	{
		Bukkit.getPluginManager().registerEvents(new TntBlocker(), this);
		Bukkit.getPluginManager().registerEvents(new NightVision(),this);
		Bukkit.getPluginManager().registerEvents(new SettingsListener(), this);
		Bukkit.getPluginManager().registerEvents(new FortuneListener(), this);
		Bukkit.getPluginManager().registerEvents(new RanksListener(), this);
		Bukkit.getPluginManager().registerEvents(new RecipeBlocker(), this);
		Bukkit.getPluginManager().registerEvents(new BusyListener(),this);
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard") &&
				Bukkit.getPluginManager().isPluginEnabled("WGRegionEvents") &&
				Bukkit.getPluginManager().isPluginEnabled("BossBarAPI"))
		{
			Bukkit.getPluginManager().registerEvents(new RegionListener(), this);
		}
	}
	
	public Economy getEco()
	{
		return econ;
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public void registerCommands()
	{
		getCommand("settings").setExecutor(new Settings());
		getCommand("rankup").setExecutor(new Rankup());
		getCommand("triggeranimation").setExecutor(new TriggerAnimation());
		getCommand("wings").setExecutor(new Wings());
		getCommand("trash").setExecutor(new Trash());
		getCommand("hideflags").setExecutor(new HideFlags());
		getCommand("busy").setExecutor(new Busy());
		getCommand("discord").setExecutor(new Discord());
		getCommand("fixhand").setExecutor(new FixHand());
		getCommand("pets").setExecutor(new Pets());
		getCommand("nv").setExecutor(new me.boyjamal.main.commands.NightVision());
	}
	
	public static Main getInstance()
	{
		return instance;
	}
	
	public TokenEnchantAPI getTokens()
	{
		return tokens;
	}
	
	public boolean blocks()
	{
		return blocksEnabled;
	}
	
	public boolean titles()
	{
		return titlesEnabled;
	}

}
