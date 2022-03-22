package com.jonah.cookiefactions.scoreboard;

import com.gmail.dejayyy.killStats.API.ksAPI;
import com.gmail.dejayyy.killStats.ksMain;
import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CookieFactionsMainScoreboard implements CookieFactionsScoreboard {

    // &a&lCookie&b&lFactions
    // &6&lOnline: &7online
    // &a&lKills: &7kills
    // &c&lDeaths: &7deaths
    // &3&lKill Streak: &7ks
    // &9&lKDR: &7kdr
    //
    // &a&lTop Kills Ranking: &7(&erank&7)
    // &a&lTop Streak Ranking: &7(&erank&7)
    //
    // &d&lDiscord:
    // &7https://discord.gg/7KNesXF
    //
    // &bcookiefactions.networkgoalie.com

    private Map<Integer, String> scoreMap = new HashMap<>();

    public void onEnable(Player p, Scoreboard board) {
        board.resetScores(p.getName());

        Objective obj = board.getObjective(DisplaySlot.SIDEBAR);

        obj.setDisplayName(Text.colorize("&a&lCookie&b&lFactions"));

//        Score score = obj.getScore(Text.colorize("&6&lOnline: &7" + Bukkit.getOnlinePlayers().size()));
//        score.setScore(13);

        ksAPI api = ksMain.api;

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String text = date.format(formatter);
        LocalDate parsedDate = LocalDate.parse(text, formatter);

        int i = 14;
        for (String s : new String[] {
                String.format("&6&lOnline: &7%s", Bukkit.getOnlinePlayers().size()),
                String.format("&a&lKills: &7%s", api.getKills(p)),
                String.format("&c&lDeaths: &7%s", api.getDeaths(p)),
                String.format("&3&lKill Streak: &7%s", api.getStreak(p)),
                String.format("&9&lKDR: &7%s", api.getRatio(p)),
                " &r&r&r",
                String.format("&a&lTop Kills Ranking: &7(&e#%s&7)", api.getKillsRank(p)),
                String.format("&a&lTop KDR Ranking: &7(&e#%s&7)", api.getRatioRank(p)),
                " &r&r",
                "&d&lDiscord:",
                "&7https://discord.gg/7KNesXF",
                " &r",
                "&bcookiefactions.networkgoalie.com"
        }) {
            Score score = obj.getScore(Text.colorize(s));
            scoreMap.put(i, Text.colorize(s));
            score.setScore(i);
            i--;
        }

        p.setScoreboard(board);

    }

    public void update(Player p, Scoreboard board) {

        Objective obj = board.getObjective(DisplaySlot.SIDEBAR);

        for (int i : new int [] {
                14,13,12,11,10,8,7
        }) {
            board.resetScores(scoreMap.get(i));
        }

        onEnable(p, board);
    }

    public void onDisable(Player p, Scoreboard board) {
        for (int i : scoreMap.keySet()) {
            board.resetScores(scoreMap.get(i));
        }
    }

    public void clearLines(Player p) {
        p.getScoreboard().resetScores(p.getName());
    }

    public CookieFactionsScoreboard switchTo() {
        return new CookieFactionsKillBoard();
    }


}
