package com.jonah.cookiefactions.killprofile.runnable;

import com.jonah.cookiefactions.level.System.LevelsStorage;
import com.jonah.cookiefactions.util.ActionBarMessage;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MultiKillRunnable extends BukkitRunnable {

    public static final int SECONDS = 10;

    private MultiKillManager manager;

    List<String> allKills = new ArrayList<>();
    long timeLeft = 20 * SECONDS;
    private Player murderer;
    private String killMessage;
    int earnedPoints;

    boolean playerAdded = true;

    public MultiKillRunnable(MultiKillManager manager, Player murderer, String initPlayer) {
        allKills.add(initPlayer);
        this.manager = manager;
        this.murderer = murderer;
    }

    @Override
    public void run() {

        if (!murderer.isOnline()) cancel();

        if (timeLeft <= 0) {
            ActionBarMessage.send(murderer, "&c");
            cancel();
            manager.unregisterPlayer(murderer);
        }

        if (playerAdded) {
            playerAdded = false;
            killMessage = evaluate();
            LevelsStorage.addPnts(murderer.getUniqueId(), earnedPoints);
            timeLeft = 20 * SECONDS;
        }

        timeLeft -= 20;

        ActionBarMessage.send(murderer, killMessage);

    }

    private String evaluate() {
        StringBuilder b = new StringBuilder();
        // &eYou killed &a&lSphro&e, &a&lnxtl&e and &a&lDrGehirn. &2&l[+20 Level Points]
        b.append("&eYou have killed ");
        int size = allKills.size();
        int points = 0;
        if (size == 1) {
            b.append("&a&l" + allKills.get(0));
            points = 20;
        } else if (size == 2) {
            b.append(String.format("&a&l%s&e and &a&l%s", allKills.get(0), allKills.get(1)));
            points = 60;
            broadcastKills();
        } else if (size == 3) {
            b.append(String.format("&a&l%s&e, &a&l%s&e and &a&l%s", allKills.get(size-3), allKills.get(size-2), allKills.get(size-1)));
            points = 150;
            broadcastKills();
        } else if (size >= 4) {
            b.append(String.format("&a&l%s&e, &a&l%s&e, &a&l%s&e and &a&l%s", allKills.get(size-4), allKills.get(size-3), allKills.get(size-2), allKills.get(size-1)));
            points = 250;
            broadcastKills();
        }
        b.append(".");

        b.append(" &2&l[+" + points + " Level Points]");

        earnedPoints = points;

        return Text.colorize(b.toString());

    }

    private void broadcastKills() {
        int size = allKills.size();
        if (size == 2) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(Text.colorize(String.format("&b%s&3 just got a double kill on &b%s&3 and &b%s&3!", murderer.getName(), allKills.get(0), allKills.get(1))));
            }
        } else if (size == 3) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(Text.colorize(String.format("&b%s&3 just got a &5&lTRIPLE KILL&3 on &b%s&3, &b%s&3, and &b%s&3!", murderer.getName(), allKills.get(size-3), allKills.get(size-2), allKills.get(size-1))));
            }
        } else if (size == 4) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(Text.colorize(String.format("&b%s&3 just got a &4&lQUADROUPLE KILL&3 on &b%s&3, &b%s&3, &b%s&3 and &b%s&3!", murderer.getName(), allKills.get(size-4), allKills.get(size-3), allKills.get(size-2), allKills.get(size-1))));
            }
        }
    }

    public void addKill(String name) {
        allKills.add(name);
        playerAdded = true;
    }

    public List<String> getKills() {
        return allKills;
    }

}
