package com.jonah.cookiefactions.core.event;

import com.jonah.cookiefactions.core.CoreManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AirPoisonEvent implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getTo().getY() > CoreManager.getInstance().getConfig().getDouble("AirPoisonHeight", 256d)) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 999999, 2));
        } else {
            e.getPlayer().removePotionEffect(PotionEffectType.POISON);
        }
    }

}
