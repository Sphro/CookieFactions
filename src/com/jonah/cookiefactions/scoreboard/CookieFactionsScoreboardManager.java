package com.jonah.cookiefactions.scoreboard;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.ExceptionReport;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class CookieFactionsScoreboardManager {

    Map<UUID, CookieFactionsScoreboard> boardMap;

    public CookieFactionsScoreboardManager(BukkitPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(getListener(), plugin);
        plugin.getCommand("cfsb").setExecutor(new ScoreboardCommand());


        boardMap = new HashMap<>();

        try {
            new BukkitRunnable() {

                @Override
                public void run() {
                    CookieFactionsKillBoard.updateKillMap();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (boardMap.containsKey(p.getUniqueId())) {
                            boardMap.get(p.getUniqueId()).onDisable(p, p.getScoreboard());
                            boardMap.put(p.getUniqueId(), boardMap.get(p.getUniqueId()).switchTo());
                        } else {
                            boardMap.put(p.getUniqueId(), new CookieFactionsMainScoreboard());
                        }
                        boardMap.get(p.getUniqueId()).onEnable(p, p.getScoreboard());

                    }
                }

            }.runTaskTimer(plugin, 0, 20 * 60);
        } catch (Exception e) {
            ExceptionReport.report(e);
        }

    }

    public void clearAllScoreboards() {
        for (Map.Entry<UUID, CookieFactionsScoreboard> entry : boardMap.entrySet()) {
            Player p = Bukkit.getPlayer(entry.getKey());
            entry.getValue().onDisable(p, p.getScoreboard());
        }
    }

    public CookieFactionsScoreboard getScoreboard(Player p) {
        return boardMap.get(p.getUniqueId());
    }

    public void updateAll() {
        for (Map.Entry<UUID, CookieFactionsScoreboard> entrySet : boardMap.entrySet()) {
            Player p = Bukkit.getPlayer(entrySet.getKey());
            entrySet.getValue().update(p, p.getScoreboard());
        }
    }

    public Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent e) {
                boardMap.put(e.getPlayer().getUniqueId(), new CookieFactionsMainScoreboard());
                boardMap.get(e.getPlayer().getUniqueId()).onEnable(e.getPlayer(), freshBoard());
                BukkitPlugin.getInstance().getScoreboardManager().updateAll();
            }

            @EventHandler
            public void onDeath(PlayerDeathEvent e) {
                BukkitPlugin.getInstance().getScoreboardManager().updateAll();
            }

            @EventHandler
            public void onQuit(PlayerQuitEvent e) {
                BukkitPlugin.getInstance().getScoreboardManager().updateAll();
            }

        };
    }

    public Scoreboard freshBoard() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("cookiefactions", "dummy");

        obj.setDisplayName(Text.colorize("&a&lCookie&b&lFactions"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        BukkitPlugin.getInstance().getLogger().log(Level.INFO, board.getObjective("cookiefactions").getName());

        return board;
    }

}
