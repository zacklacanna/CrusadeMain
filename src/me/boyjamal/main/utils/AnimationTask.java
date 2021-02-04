package me.boyjamal.main.utils;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnimationTask extends BukkitRunnable {
	
	private Player p;
	private ArmorStand as;
	
	public AnimationTask(Player p, ArmorStand as)
	{
		this.p = p;
		this.as = as;
	}
	
	int count = 0;
	float Oldyaw = 0;
	
	@Override
	public void run() {
    	if (count == 20*5)
    	{
    		if (LevelUp.activeAnimations.containsKey(p.getUniqueId().toString()))
    		{
    			LevelUp.activeAnimations.remove(p.getUniqueId().toString());
    		}
    		
    		if (LevelUp.activeArmorStands.containsKey(p.getUniqueId().toString()))
    		{
    			as.remove();
    			LevelUp.activeArmorStands.remove(p.getUniqueId().toString());
    		}
    		this.cancel();
    	}
    	count++;
    	
    	Block b = p.getTargetBlock((HashSet<Byte>) null, 3);
		Location loc = b.getLocation().add(0,-.75,0);
    	
        Location rotatingLoc = loc;
        float yaw = rotatingLoc.getYaw() + Oldyaw;
        Oldyaw += 6;
        if (Oldyaw >= 180)
            Oldyaw *= -1;
        rotatingLoc.setYaw(yaw);
        as.teleport(rotatingLoc);
    }
	

}
