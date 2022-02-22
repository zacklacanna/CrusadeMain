package me.boyjamal.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.bossbar.BossBarAPI;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.earth2me.essentials.Essentials;
import com.vk2gpz.tokenenchant.TokenEnchant;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import me.boyjamal.main.commands.BlocksTop;
import me.boyjamal.main.commands.Busy;
import me.boyjamal.main.commands.Discord;
import me.boyjamal.main.commands.Enchant;
import me.boyjamal.main.commands.FixHand;
import me.boyjamal.main.commands.GiveAll;
import me.boyjamal.main.commands.HideFlags;
import me.boyjamal.main.commands.Kits;
import me.boyjamal.main.commands.MaxRankup;
import me.boyjamal.main.commands.Mineparty;
import me.boyjamal.main.commands.Pets;
import me.boyjamal.main.commands.PrestigeCommand;
import me.boyjamal.main.commands.Rankup;
import me.boyjamal.main.commands.Settings;
import me.boyjamal.main.commands.TokenShop;
import me.boyjamal.main.commands.Trash;
import me.boyjamal.main.commands.TriggerAnimation;
import me.boyjamal.main.commands.Warps;
import me.boyjamal.main.commands.Wings;
import me.boyjamal.main.commands.WithdrawMoney;
import me.boyjamal.main.commands.WithdrawTokens;
import me.boyjamal.main.commands.XpRedeem;
import me.boyjamal.main.events.ArmorStandInteract;
import me.boyjamal.main.events.ConfirmClick;
import me.boyjamal.main.events.TokenShopClick;
import me.boyjamal.main.listeners.BalanceListener;
import me.boyjamal.main.listeners.BusyListener;
import me.boyjamal.main.listeners.EnchantListener;
import me.boyjamal.main.listeners.KitsListener;
import me.boyjamal.main.listeners.LeaderboardListener;
import me.boyjamal.main.listeners.NightVision;
import me.boyjamal.main.listeners.PickaxeDrop;
import me.boyjamal.main.listeners.PrestigeListener;
import me.boyjamal.main.listeners.RanksListener;
import me.boyjamal.main.listeners.RecipeBlocker;
import me.boyjamal.main.listeners.RegionListener;
import me.boyjamal.main.listeners.SettingsListener;
import me.boyjamal.main.listeners.TntBlocker;
import me.boyjamal.main.listeners.WarpsListener;
import me.boyjamal.main.utils.EnchantItem;
import me.boyjamal.main.utils.EnchantUtil;
import me.boyjamal.main.utils.FortuneUtil;
import me.boyjamal.main.utils.HeadAPI;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.StorageManager;
import me.boyjamal.main.utils.TokenMultiplierAPI;
import me.boyjamal.main.utils.TokenShopGui;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private Economy econ;
	private Essentials ess;
	private TokenEnchantAPI tokens;
	private boolean titlesEnabled = false;
	private boolean blocksEnabled = false;
	private EnchantUtil util = new EnchantUtil();
	private LuckPerms perms;
	private BossBarAPI api;
	
	public void onEnable()
	{
		instance = this;
		
		if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		PlayerSettings.loadSettings();
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
			
			util = new EnchantUtil();
			util.loadEnchantItems();
			
			for (EnchantItem each : util.getEnchantItems())
			{
				System.out.println("Enchant Items: " + "slot-" + each.getSlot() + " name-" + each.getName());
			}
		} else {
			Bukkit.getPluginManager().disablePlugin(this);
			System.out.println("CrusadeMain > Could not be loaded missing [TokenEnchant]");
		}
		
		if (Bukkit.getPluginManager().isPluginEnabled("EZBlocks"))
		{
			blocksEnabled = true;
		}
		
		if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms"))
		{
			perms = LuckPermsProvider.get();
		} else {
			Bukkit.getPluginManager().disablePlugin(this);
			System.out.println("CrusadeMain > Could not be loaded missing [LuckPerms]");
		}
		
		if (Bukkit.getPluginManager().isPluginEnabled("TitleAPI"))
		{
			titlesEnabled = true;
		}
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new TokenMultiplierAPI().register();
		} else {
			Bukkit.getPluginManager().disablePlugin(this);
			System.out.println("CrusadeMain > Could not be loaded missing [PlaceholderAPI]");
		}
	}
	
	public void onDisable()
	{
		PlayerSettings.saveSettings();
	}
	
	public void registerListeners()
	{
		Bukkit.getPluginManager().registerEvents(new TntBlocker(), this);
		Bukkit.getPluginManager().registerEvents(new NightVision(),this);
		Bukkit.getPluginManager().registerEvents(new SettingsListener(), this);
		Bukkit.getPluginManager().registerEvents(new RanksListener(), this);
		Bukkit.getPluginManager().registerEvents(new HeadAPI(), this);
		Bukkit.getPluginManager().registerEvents(new LeaderboardListener(), this);
		Bukkit.getPluginManager().registerEvents(new RecipeBlocker(), this);
		Bukkit.getPluginManager().registerEvents(new BusyListener(),this);
		Bukkit.getPluginManager().registerEvents(new BalanceListener(),this);
		Bukkit.getPluginManager().registerEvents(new ArmorStandInteract(), this);
		Bukkit.getPluginManager().registerEvents(new KitsListener(), this);
		Bukkit.getPluginManager().registerEvents(new WarpsListener(), this);
		Bukkit.getPluginManager().registerEvents(new PrestigeListener(), this);
		Bukkit.getPluginManager().registerEvents(new TokenShopGui(), this);
		Bukkit.getPluginManager().registerEvents(new PickaxeDrop(), this);
		Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);
		Bukkit.getPluginManager().registerEvents(new TokenShopClick(), this);
		Bukkit.getPluginManager().registerEvents(new ConfirmClick(), this);
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard") &&
				Bukkit.getPluginManager().isPluginEnabled("WGRegionEvents") &&
				Bukkit.getPluginManager().isPluginEnabled("BossBarAPI"))
		{
			api = new BossBarAPI();
			
			Bukkit.getPluginManager().registerEvents(new RegionListener(), this);
		}
	}
	
	public Economy getEco()
	{
		return econ;
	}
	
	public Essentials getEssentials()
	{
		return ess;
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
		getCommand("withdrawbalance").setExecutor(new WithdrawMoney());
		getCommand("tokenshop").setExecutor(new TokenShop());
		getCommand("settings").setExecutor(new Settings());
		getCommand("rankup").setExecutor(new Rankup());
		getCommand("triggeranimation").setExecutor(new TriggerAnimation());
		getCommand("wings").setExecutor(new Wings());
		getCommand("trash").setExecutor(new Trash());
		getCommand("hideflags").setExecutor(new HideFlags());
		getCommand("busy").setExecutor(new Busy());
		getCommand("discord").setExecutor(new Discord());
		getCommand("blockstop").setExecutor(new BlocksTop());
		getCommand("warps").setExecutor(new Warps());
		getCommand("kits").setExecutor(new Kits());
		getCommand("prestige").setExecutor(new PrestigeCommand());
		getCommand("fixhand").setExecutor(new FixHand());
		getCommand("withdrawtokens").setExecutor(new WithdrawTokens());
		getCommand("maxrankup").setExecutor(new MaxRankup());
		getCommand("giveall").setExecutor(new GiveAll());
		getCommand("redeemxp").setExecutor(new XpRedeem());
		getCommand("pets").setExecutor(new Pets());
		getCommand("enchant").setExecutor(new Enchant());
		getCommand("mineparty").setExecutor(new Mineparty());
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
	
	public LuckPerms getPerms()
	{
		return perms;
	}
	
	public boolean blocks()
	{
		return blocksEnabled;
	}
	
	public boolean titles()
	{
		return titlesEnabled;
	}
	
	public EnchantUtil getEnchantUtil()
	{
		return util;
	}
	
	public BossBarAPI getBossBar()
	{
		return api;
	}

}
