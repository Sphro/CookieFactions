package com.jonah.cookiefactions.killprofile.runnable;

import com.jonah.cookiefactions.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MultiKillManager {

    protected Map<UUID, MultiKillRunnable> killMap = new HashMap<>();
    private BukkitPlugin plugin;

    public MultiKillManager(BukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onLeave(PlayerQuitEvent e) {
                killMap.remove(e.getPlayer().getUniqueId());
            }

            }, plugin);
    }


    public void registerPlayer(Player p, String playerKilled) {
        killMap.put(p.getUniqueId(), new MultiKillRunnable(this, p, playerKilled));
        MultiKillRunnable run = killMap.get(p.getUniqueId());
        run.runTaskTimer(plugin, 0, 20);
    }

    public void unregisterPlayer(Player p) {
        killMap.remove(p.getUniqueId());
    }

    public boolean doesPlayerExist(Player p) {
        return killMap.containsKey(p.getUniqueId());
    }

    public void killedPlayer(Player p, String victim) {
        MultiKillRunnable run = killMap.get(p.getUniqueId());
        run.addKill(victim);
    }

}
