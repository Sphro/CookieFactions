package com.jonah.cookiefactions.level.Events;

import com.jonah.cookiefactions.level.System.LevelsStorage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class Death implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (e.getEntity() == null) return;
		if (e.getEntity().getKiller() == null) return;
		Player vic = e.getEntity();
		Player att = e.getEntity().getKiller();
		int pnts = 2;
		try {
			Material helmtype = vic.getInventory().getHelmet().getType();
			Material chesttype = vic.getInventory().getChestplate().getType();
			Material legstype = vic.getInventory().getLeggings().getType();
			Material bootstype = vic.getInventory().getBoots().getType();
			if (helmtype != Material.AIR && chesttype != Material.AIR && legstype != Material.AIR && bootstype != Material.AIR) {
				pnts = 5;
				if (helmtype == Material.IRON_HELMET && chesttype == Material.IRON_CHESTPLATE && legstype == Material.IRON_LEGGINGS && bootstype == Material.IRON_BOOTS) {
					pnts = 15;
				}
				if (helmtype == Material.DIAMOND_HELMET && chesttype == Material.DIAMOND_CHESTPLATE && legstype == Material.IRON_LEGGINGS && bootstype == Material.IRON_BOOTS) {
					pnts = 20;
				}
			}
		} catch (NullPointerException e2) {}
		LevelsStorage.addPnts(att.getUniqueId(), pnts);
	}
	
}
