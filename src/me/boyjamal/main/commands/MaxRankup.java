package me.boyjamal.main.commands;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.boyjamal.main.Main;
import me.clip.ezrankspro.EZRanksPro;
import me.clip.ezrankspro.events.RankupEvent;
import me.clip.ezrankspro.rankdata.RankupType;
import me.clip.ezrankspro.util.ChatUtil;

public class MaxRankup implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		
		boolean canRankup = true;
		
		while(canRankup)
		{
			me.clip.ezrankspro.rankdata.Rankup rankInfo = me.clip.ezrankspro.rankdata.Rankup.getRankup(p);
			if (rankInfo == null)
			{
				if (me.clip.ezrankspro.rankdata.Rankup.isLastRank(p))
				{
					canRankup = false;
				} else {
					canRankup = false;
				}
				return true;
			}
			
			double cost = rankInfo.getCost();
			double balance = Main.getInstance().getEco().getBalance(p);
			
			if (balance < cost) 
			{
                Iterator var13 = me.clip.ezrankspro.rankdata.Rankup.getRequirementMessage().iterator();

                while(var13.hasNext()) {
                   String str3 = (String)var13.next();
                   str3 = EZRanksPro.getInstance().getPlaceholderReplacer().setPlaceholders(p, rankInfo, str3);
                   ChatUtil.msg(p, str3);
                }

                return true;
            }
			
			RankupEvent localRankupEvent = new RankupEvent(p, rankInfo.getRank(), rankInfo.getRankup(), cost, RankupType.RANKUP);
            Bukkit.getPluginManager().callEvent(localRankupEvent);
            if (localRankupEvent.isCancelled()) {
               return true;
            }

            EZRanksPro.getInstance().getActionHandler().executeRankupActions(p, rankInfo);
            EZRanksPro.getInstance().getEconomy().withdrawMoney(cost, p);
		}
		
		return true;
	}

}
