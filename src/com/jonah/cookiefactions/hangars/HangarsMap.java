package com.jonah.cookiefactions.hangars;

import com.jonah.cookiefactions.util.Config;
import com.jonah.cookiefactions.util.ExceptionReport;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class HangarsMap {

    private static Config config = null;

    static {
        if (config == null) {
            config = new Config("hangars");
        }
    }

    public static Config getConfig() {
        return config;
    }

    private String worldName;

    public HangarsMap(String worldName) {
        this(worldName, true);
    }

    public HangarsMap(String worldName, boolean loadWorld) throws IllegalArgumentException {
        if (loadWorld) new WorldCreator(worldName).createWorld();
        this.worldName = worldName;

        if (!config.getConfig().isConfigurationSection(worldName)) {
            config.getConfig().set(String.format("%s.enabled", worldName), false);
            config.getConfig().set(String.format("%s.WarpLocation.x", worldName), 0);
            config.getConfig().set(String.format("%s.WarpLocation.y", worldName), 0);
            config.getConfig().set(String.format("%s.WarpLocation.z", worldName), 0);
            config.getConfig().set(String.format("%s.RTP.x1", worldName), 0);
            config.getConfig().set(String.format("%s.RTP.z1", worldName), 0);
            config.getConfig().set(String.format("%s.RTP.x2", worldName), 0);
            config.getConfig().set(String.format("%s.RTP.z2", worldName), 0);
            config.getConfig().set(String.format("%s.TeleportMethod", worldName), "WARP");
            config.getConfig().set(String.format("%s.ColoredName", worldName), worldName);
            config.getConfig().set(String.format("%s.description", worldName), worldName);

            config.save();
        }

        for (Player p : Bukkit.getOnlinePlayers().stream().filter(p2 -> p2.hasPermission("admin") || p2.isOp()).collect(Collectors.toList())) {
            p.sendMessage(Text.colorize("&aSuccessfully created " + worldName + ", the current settings are:"));

        }

    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public boolean loadMap() {
        try {
            new WorldCreator(worldName).createWorld();
            return true;
        } catch (Exception e) {
            ExceptionReport.report(e);
            return false;
        }
    }

    public String getName() {
        return worldName;
    }

    public List<String> getDescription() {
        return config.getConfig().getStringList(String.format("%s.description", worldName));
    }

    public String getColoredName() {
        return Text.colorize(config.getConfig().getString(String.format("%s.ColoredName", worldName)));
    }

    public boolean disableWorld() {
        try {
            config.getConfig().set(String.format("%s.enabled", worldName), false);
            config.save();
            return true;
        } catch (Exception e) {
            ExceptionReport.report(e);
            return false;
        }
    }

    public boolean enableWorld() {
        try {
            config.getConfig().set(String.format("%s.enabled", worldName), true);
            config.save();
            return true;
        } catch (Exception e) {
            ExceptionReport.report(e);
            return false;
        }
    }

    public boolean isEnabled() {
        return config.getConfig().getBoolean(String.format("%s.enabled", worldName));
    }

    public int getPlayers() {
        return Bukkit.getWorld(worldName).getPlayers().size();
    }

    public int[] getWarp() {
        return new int[] {
                config.getConfig().getInt(String.format("%s.WarpLocation.x", worldName)),
                config.getConfig().getInt(String.format("%s.WarpLocation.y", worldName)),
                config.getConfig().getInt(String.format("%s.WarpLocation.z", worldName)),
        };
    }

    public boolean setWarp(int[] newLocation) {
        try {
            config.getConfig().set(String.format("%s.WarpLocation.x", worldName), newLocation[0]);
            config.getConfig().set(String.format("%s.WarpLocation.y", worldName), newLocation[1]);
            config.getConfig().set(String.format("%s.WarpLocation.z", worldName), newLocation[2]);
            config.save();
            return true;
        } catch (Exception e) {
            ExceptionReport.report(e);
            return false;
        }
    }

    public boolean setFirstBounds(int x1, int z1) {
        try {
            config.getConfig().set(String.format("%s.RTP.x1", worldName), x1);
            config.getConfig().set(String.format("%s.RTP.z1", worldName), z1);
            config.save();
            return true;
        } catch (Exception e) {
            ExceptionReport.report(e);
            return false;
        }
    }

    public boolean setSecondBounds(int x2, int z2) {
        try {
            config.getConfig().set(String.format("%s.RTP.x2", worldName), x2);
            config.getConfig().set(String.format("%s.RTP.z2", worldName), z2);
            config.save();
            return true;
        } catch (Exception e) {
            ExceptionReport.report(e);
            return false;
        }
    }

    public int[] rtpBounds() {
        return new int [] {
                config.getConfig().getInt(String.format("%s.RTP.x1", worldName)),
                config.getConfig().getInt(String.format("%s.RTP.z1", worldName)),
                config.getConfig().getInt(String.format("%s.RTP.x2", worldName)),
                config.getConfig().getInt(String.format("%s.RTP.z2", worldName)),
        };
    }

    public Hangars.TeleportMethod getDefaultMethod() {
        return Hangars.TeleportMethod.valueOf(config.getConfig().getString(String.format("%s.TeleportMethod", worldName)));
    }

    public int hashCode() {
        return worldName.hashCode();
    }

}
