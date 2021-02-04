package me.boyjamal.main.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.bossbar.BossBar;

import com.connorlinfoot.titleapi.TitleAPI;
import com.google.common.collect.PeekingIterator;
import com.vk2gpz.tokenenchant.a.a.a.e;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.TokenManager;
import me.boyjamal.main.utils.TokenSummery;
import me.kvq.supertrailspro.SuperTrails;
import me.kvq.supertrailspro.API.SuperTrailsAPI;

public class TntBlocker implements Listener {
	
	private static HashMap<String,Long> invFullCooldown = new HashMap<String,Long>();
	
	@EventHandler
	public void onTokenPlace(BlockPlaceEvent e)
	{
		if (!(e.getPlayer().hasPermission("crusademc.admin")))
		{
			if (e.getBlock().getType().equals(Material.QUARTZ_ORE))
			{
				e.getPlayer().sendMessage(MainUtils.chatColor("&c&nError!&7&o You cannot place a TokenBlock!"));
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void invFull(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		String key = p.getUniqueId().toString();
		if (p.getInventory().firstEmpty() == -1)
		{
			if (!(invFullCooldown.containsKey(key)))
			{
				if (Main.getInstance().titles())
				{
					TitleAPI.sendFullTitle(p, 20, 60, 20, MainUtils.chatColor("&c&lAttention"), MainUtils.chatColor("&7&oYour inventory is full!"));
				}
				p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 500, 500);
				//particles
				invFullCooldown.put(key, System.currentTimeMillis());
			} else {
				Long start = invFullCooldown.get(key);
				if (System.currentTimeMillis() >= (start + 300))
				{
					if (Main.getInstance().titles())
					{
						TitleAPI.sendFullTitle(p, 20, 60, 20, MainUtils.chatColor("&c&lAttention"), MainUtils.chatColor("&7&oYour inventory is full!"));
					}
					p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 500, 500);
					//particles
					invFullCooldown.replace(key, System.currentTimeMillis());
				} else {
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onTokenRedeem(PlayerInteractEvent e)
	{
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			ItemStack item = e.getPlayer().getItemInHand();
			if (item != null)
			{
				if (item.getType() == Material.QUARTZ_ORE)
				{
					e.setCancelled(true);
					
					/*
					 * check for custom tokenfinder enchant
					 */
					
					if (item.getAmount() == 1)
					{
						TokenManager.addToken(e.getPlayer(), 1);
					} else {
						TokenManager.addToken(e.getPlayer(), item.getAmount());
					}
					e.getPlayer().getInventory().setItemInHand(null);
					e.getPlayer().sendMessage(MainUtils.chatColor("&a&nSuccess&7 You redeemed &a&n" + item.getAmount() + "&7 Tokens!"));
				}
			}
		}
	}
	
	@EventHandler
    public void onBlockPlace(BlockBreakEvent event)
	{
		Player p = event.getPlayer();
		
        if(event.getBlock().getType() == Material.TNT)
        {
            if (p.getWorld().getName().equalsIgnoreCase("PlotWorld"))
            {
            	if (!(p.hasPermission("valancemc.admin")))
            	{
            		event.setCancelled(true);
                	p.sendMessage(MainUtils.chatColor("&c&nError!&7&o You can not break a Tnt Block!"));
                	return;
            	}
            }
        }
        
        if (TokenManager.getTokenSummery().containsKey(p.getUniqueId().toString()))
		{
			if (p.getWorld().getName().equalsIgnoreCase("NewMines"))
			{
				TokenManager.getTokenSummery().get(p.getUniqueId().toString()).addBlocks(1);
			}
		}
        
        if (event.getBlock().getType() == Material.QUARTZ_ORE)
        {
        	if (p != null)
        	{
        		event.setCancelled(true);
        		if (TokenManager.getTokenSummery().containsKey(p.getUniqueId().toString()))
        		{
        			long start = TokenManager.getTokenSummery().get(p.getUniqueId().toString()).getStart();
        			if (System.currentTimeMillis() >= start + TimeUnit.SECONDS.toMillis(60))
        			{
        				if (PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString()))
        				{
        					if (PlayerSettings.activeSettings.get(p.getUniqueId().toString()).hasTokenSummery())
        					{
        						MainUtils.summeryMessage(p);
        						return;
        					}
        				} else {
        					TokenManager.getTokenSummery().remove(p.getUniqueId().toString());
        					TokenManager.getTokenSummery().put(p.getUniqueId().toString(), new TokenSummery(p,1,System.currentTimeMillis()));
                			TokenManager.addToken(p, 1);
        				}
        			} else {
        				TokenManager.getTokenSummery().get(p.getUniqueId().toString()).addTokens(1);
        			}
        		} else {
        			TokenManager.getTokenSummery().put(p.getUniqueId().toString(), new TokenSummery(p,1,System.currentTimeMillis()));
        			TokenManager.addToken(p, 1);
        		}
        		event.getBlock().setType(Material.AIR);
        	}
        }
        
        if (event.getBlock().getType() == Material.SNOW_BLOCK)
        {
        	if (p != null)
        	{
        		if (p.getWorld().getName().equalsIgnoreCase("NewMines"))
        		{
        			event.setCancelled(true);
        			p.getInventory().addItem(new ItemStack(Material.SNOW_BLOCK));
        			event.getBlock().setType(Material.AIR);
        		}
        	}      
        }
        
        if (event.getBlock().getType() == Material.PACKED_ICE)
        {
        	if (p != null)
        	{
        		if (p.getWorld().getName().equalsIgnoreCase("NewMines"))
        		{
        			event.getBlock().getDrops().add(new ItemStack(Material.PACKED_ICE));
        			return;
        		}
        	}
        }
    }
}
