package com.jonah.cookiefactions.killprofile.event;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.killprofile.runnable.MultiKillManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillPlayerEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player victim = e.getEntity();

        MultiKillManager manager = BukkitPlugin.getInstance().getKillProfile().getManager();

        if (manager.doesPlayerExist(killer)) {
            manager.killedPlayer(killer, victim.getName());
        } else {
            manager.registerPlayer(killer, victim.getName());
        }

    }

}
