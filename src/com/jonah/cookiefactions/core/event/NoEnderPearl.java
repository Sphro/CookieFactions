package com.jonah.cookiefactions.core.event;

import com.jonah.cookiefactions.core.lang.Messages;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class NoEnderPearl implements Listener {

    @EventHandler
    public void onEnderPearl(PlayerInteractEvent e) {
        if (e.getItem() == null) return;

        if (e.getItem().getType() == Material.ENDER_PEARL) {
            e.getPlayer().sendMessage(Messages.NO_ENDER_PEARL.toString());
            e.setUseItemInHand(Event.Result.DENY);
            return;
        }

    }

}
