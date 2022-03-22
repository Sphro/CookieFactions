package com.jonah.cookiefactions.core.event;

import com.jonah.cookiefactions.core.CoreManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public class NoAnimalsEvent implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        List<String> noEn = CoreManager.getInstance().getConfig().getStringList("NoSpawn");
        if (noEn.contains(e.getEntityType().toString())) e.setCancelled(true);
    }

}
