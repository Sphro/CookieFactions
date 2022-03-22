package com.jonah.cookiefactions.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public interface CookieFactionsScoreboard {

    void onEnable(Player p, Scoreboard board);

    void update(Player p, Scoreboard board);

    void onDisable(Player p, Scoreboard board);

    CookieFactionsScoreboard switchTo();

    void clearLines(Player p);

}
