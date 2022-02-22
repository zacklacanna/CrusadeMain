package me.boyjamal.main.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.inventivetalent.bossbar.BossBar;

import com.connorlinfoot.titleapi.TitleAPI;
import com.google.common.collect.PeekingIterator;
import com.vk2gpz.sack.Sack;
import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent;
import com.vk2gpz.vkbackpack.VKBackPack;

import me.boyjamal.main.Main;
import me.boyjamal.main.utils.MainUtils;
import me.boyjamal.main.utils.PlayerSettings;
import me.boyjamal.main.utils.TokenManager;
import me.boyjamal.main.utils.TokenSummery;
import me.clip.autosell.AutoSell;
import me.clip.autosell.AutoSellAPI;
import me.clip.autosell.events.DropsToInventoryEvent;
import me.clip.ezblocks.EZBlocks;
import me.kvq.supertrailspro.SuperTrails;
import me.kvq.supertrailspro.API.SuperTrailsAPI;
import net.lightshard.prisonbombs.event.BombExplodeEvent;

public class TntBlocker implements Listener {
	
	private static HashMap<String,Long> invFullCooldown = new HashMap<String,Long>();
	
	//block placing token blocks
	@EventHandler
	public void onTokenPlace(BlockPlaceEvent e)
	{
		if (e.isCancelled())
		{
			return;
		}
		
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
	public void onBreak(TEBlockExplodeEvent evt)
	{
		if (evt.isCancelled())
		{
			return;
		}
		Player p = evt.getPlayer();
		
		if (TokenManager.getTokenSummery().containsKey(p.getUniqueId().toString()))
		{
			TokenSummery summery = TokenManager.getTokenSummery().get(p.getUniqueId().toString());
			summery.addBlocks(evt.blockList().size());
		}
		
		if (!(p.getWorld().getName().equalsIgnoreCase("NewMines")))
		{
			return;
		}
		
		Iterator<ItemStack> iter = evt.getDrops().iterator();
		while (iter.hasNext())
		{
			ItemStack block = iter.next();
			if (block == null || block.getType() == Material.AIR)
			{
				continue;
			} else {
				
				if (block.getType() == Material.QUARTZ_ORE)
				{
					TokenManager.addToken(p, 2);
					iter.remove();
					continue;
				} else {
					continue;
				}
				
			}
		}
		/*while (iter.hasNext())
		{
			ItemStack each = iter.next();
			if (each == null)
			{
				break;
			}
				
			else if (each.getType() == Material.QUARTZ || each.getType() == Material.QUARTZ_ORE)
			{
				TokenManager.addToken(p, 2);
				toRemove.add(each);
				continue;
			}
			
			else if (each.getType() == Material.SNOW_BLOCK || each.getType() == Material.SNOW_BALL)
			{
				toAdd.add(new ItemStack(Material.SNOW_BLOCK,each.getAmount()));
				toRemove.add(each);
				continue;
			}
			
			else if (each.getType() == Material.PRISMARINE_CRYSTALS)
			{
				toAdd.add(new ItemStack(Material.SEA_LANTERN,each.getAmount()));
				toRemove.add(each);
				continue;
			} else {
				continue;
			}
		}*/
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreakToken(BombExplodeEvent evt)
	{	
		Player p = evt.getPlayer();
		if (!(p.getWorld().getName().equalsIgnoreCase("NewMines")))
		{
			return;
		}
		
		Iterator<Block> iter = evt.getExploded().iterator();
		while (iter.hasNext())
		{
			Block each = iter.next();
			System.out.println(each.getType().toString());
		}
	}
	
	
	//prevent placing tnt
	@EventHandler
	public void itemHandler(BlockBreakEvent event)
	{
		Player p = event.getPlayer();
		
		if (event.isCancelled())
		{
			return;
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
        			return;
        		}
        	}      
        }
        
        if (event.getBlock().getType() == Material.PACKED_ICE)
        {
        	if (p != null)
        	{
        		if (p.getWorld().getName().equalsIgnoreCase("NewMines"))
        		{
        			if (event.getBlock().getDrops().isEmpty())
        			{
	        			event.getBlock().getDrops().add(new ItemStack(Material.PACKED_ICE));
	        			return;
        			}
        		}
        	}
        }
        
        if (event.getBlock().getType() == Material.SEA_LANTERN)
        {
        	if (p != null)
        	{
        		if (p.getWorld().getName().equalsIgnoreCase("NewMines"))
        		{
        			event.setCancelled(true);
            		event.getBlock().setType(Material.AIR);
            		p.getInventory().addItem(new ItemStack(Material.SEA_LANTERN));
        		}
        	}
        }
        
        if (event.getBlock().getType() == Material.GLOWSTONE)
        {
        	if (p != null)
        	{
        		event.setCancelled(true);
        		event.getBlock().setType(Material.AIR);
        		p.getInventory().addItem(new ItemStack(Material.GLOWSTONE,1));
        	}
        }
        
        if(event.getBlock().getType() == Material.TNT)
        {
            if (p.getWorld().getName().equalsIgnoreCase("PlotWorld"))
            {
            	if (!(p.hasPermission("crusademc.admin")))
            	{
            		event.setCancelled(true);
                	p.sendMessage(MainUtils.chatColor("&c&nError!&7&o You can not break a Tnt Block!"));
                	return;
            	}
            }
        }
	}
	
	
	@EventHandler
    public void tokenEvents(BlockBreakEvent event)
	{
		Player p = event.getPlayer();
		
		if (event.isCancelled())
		{
			return;
		}
        
        if (TokenManager.getTokenSummery().containsKey(p.getUniqueId().toString()))
        {
        	
		} else {
			if (PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString()) && PlayerSettings.activeSettings.get(p.getUniqueId().toString()).hasTokenSummery())
			{
				TokenManager.getTokenSummery().put(p.getUniqueId().toString(),new TokenSummery(p,0,System.currentTimeMillis()));
			}
		}
        
        PlayerSettings.activeSettings.get(p.getUniqueId().toString()).setBlocks(EZBlocks.getEZBlocks().getBlocksBroken(p));
        
        if (event.getBlock().getType() == Material.QUARTZ_ORE)
        {
        	if (p != null)
        	{
        		event.setCancelled(true);
        		event.getBlock().setType(Material.AIR);
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
        						TokenManager.getTokenSummery().remove(p.getUniqueId().toString());
            					TokenManager.getTokenSummery().put(p.getUniqueId().toString(), new TokenSummery(p,0,System.currentTimeMillis()));
        					}
        				}
        			} else {
        				TokenManager.getTokenSummery().get(p.getUniqueId().toString()).addTokens(2);
        				TokenManager.getTokenSummery().get(p.getUniqueId().toString()).addBlocks(1);
        			}
        		}
        	}
        } else {
        	if (PlayerSettings.activeSettings.containsKey(p.getUniqueId().toString()) && PlayerSettings.activeSettings.get(p.getUniqueId().toString()).hasTokenSummery())
			{
        		if (TokenManager.getTokenSummery().containsKey(p.getUniqueId().toString()))
        		{
        			long start = TokenManager.getTokenSummery().get(p.getUniqueId().toString()).getStart();
        			if (System.currentTimeMillis() >= start + TimeUnit.SECONDS.toMillis(60))
        			{
        				MainUtils.summeryMessage(p);
						TokenManager.getTokenSummery().remove(p.getUniqueId().toString());
    					TokenManager.getTokenSummery().put(p.getUniqueId().toString(), new TokenSummery(p,0,System.currentTimeMillis()));
        			} else {
        				TokenManager.getTokenSummery().get(p.getUniqueId().toString()).addBlocks(1);
        			}
        		}
			}
        }
    }
}
