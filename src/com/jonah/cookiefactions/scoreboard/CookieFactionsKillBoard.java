package com.jonah.cookiefactions.scoreboard;

import com.gmail.dejayyy.killStats.API.ksAPI;
import com.gmail.dejayyy.killStats.MySQL.SQLite;
import com.gmail.dejayyy.killStats.PlayerStats;
import com.gmail.dejayyy.killStats.ksMain;
import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.ExceptionReport;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class CookieFactionsKillBoard implements CookieFactionsScoreboard{

    public static Map<String, Integer> killMap = new HashMap<>();

    public static void updateKillMap() {
        ksMain ksmain = JavaPlugin.getPlugin(ksMain.class);
        ksAPI api = ksMain.api;

        killMap.clear();

        try {
            Connection conn = new SQLite(ksmain, "player_data.db").openConnection();
            for (int i = 1 ; i <= 14 ; i++) {
                String playerName = api.getRank_Name("spawn", "kills", String.valueOf(i));
                String query = "select playerName, kills from killstats_data where playerName = '" + playerName + "'";

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    killMap.put(playerName, rs.getInt("kills"));
                }
            }
        } catch (SQLException e) {
            ExceptionReport.report(e);
        }

    }

    private Map<Integer, String> scoreMap = new HashMap<>();

    public void onEnable(Player p, Scoreboard board) {

        board.resetScores(p.getName());

        Objective obj = board.getObjective(DisplaySlot.SIDEBAR);

        obj.setDisplayName(Text.colorize("&9Top Kills"));

        ksAPI api = ksMain.api;

        for (int i = 1 ; i <= 15 ; i++) {

            for (Map.Entry<String, Integer> entry : killMap.entrySet()) {
                Score score = obj.getScore(Text.colorize("&c" + entry.getKey()));
                score.setScore(entry.getValue());
            }
        }

        p.setScoreboard(board);

    }

    public void onDisable(Player p, Scoreboard board) {
        for (String name : killMap.keySet()) {
            board.resetScores(Text.colorize("&c" + name));
        }
    }

    public void update(Player p, Scoreboard board) {
        onDisable(p, board);
        onEnable(p, board);
    } 

    public void clearLines(Player p) {
        p.getScoreboard().resetScores(p.getName());
    }

    public CookieFactionsScoreboard switchTo() {
        return new CookieFactionsMainScoreboard();
    }
}
