package com.jonah.cookiefactions.hangars;

import com.jonah.cookiefactions.util.ExceptionReport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Hangars {

    private ArrayList<HangarsMap> maps;

    public Hangars() {
        maps = new ArrayList<>();

        for (String key : HangarsMap.getConfig().getConfig().getKeys(false)) {
            initializeMap(key);
        }

    }

    public ArrayList<HangarsMap> getAllMaps() {
        return maps;
    }

    public HangarsMap getMap(String name) throws IllegalArgumentException {
        try {
            return maps.stream().filter((h) -> h.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("World name not found");
        }
    }

    public boolean isMap(String name) {
        return maps.stream().filter((h) -> h.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).size() > 0;
    }

    public HangarsMap initializeMap(String name) {
        HangarsMap map = new HangarsMap(name, true);
        maps.add(map);
        return map;
    }

    public boolean sendPlayerTo(Player p, String name, TeleportMethod method) {
        return sendPlayerTo(p, getMap(name), method);
    }

    public boolean sendPlayerTo(Player p, HangarsMap map, TeleportMethod method) {
        if (!map.isEnabled()) return false;
        try {
            p.teleport(method.teleport(map));
            return true;
        } catch (Exception e) {
            ExceptionReport.report(e);
            return false;
        }
    }

    public boolean loadMap(String name) {
        try {
            getMap(name).loadMap();
            return true;
        } catch (Exception e) {
            ExceptionReport.report(e);
            return false;
        }
    }

    public enum TeleportMethod {

        RTP,
        WARP;

        public Location teleport(HangarsMap map) {
            if (this == RTP) {
                Random random = new Random();

                int[] finalCoords = new int[2];
                int xmin, xmax, zmin, zmax;

                if (map.rtpBounds()[0] > map.rtpBounds()[2]) {
                    xmax = map.rtpBounds()[0];
                    xmin = map.rtpBounds()[2];
                } else {
                    xmax = map.rtpBounds()[2];
                    xmin = map.rtpBounds()[0];
                }

                if (map.rtpBounds()[1] > map.rtpBounds()[3]) {
                    zmax = map.rtpBounds()[1];
                    zmin = map.rtpBounds()[3];
                } else {
                    zmax = map.rtpBounds()[3];
                    zmin = map.rtpBounds()[1];
                }

                finalCoords[0] = random.nextInt(xmax-xmin) + xmin;
                finalCoords[1] = random.nextInt(zmax-zmin) + zmin;

                //Location l = new Location(map.getWorld(), finalCoords[0], 256, finalCoords[1]);

                int i = 256;
                while (i > 0) {

                    if (map.getWorld().getBlockAt(new Location(map.getWorld(), finalCoords[0], i, finalCoords[1])).getType() == Material.AIR) {
                        i -= 1;
                    } else {
                        break;
                    }
                }

                return new Location(map.getWorld(), finalCoords[0], i, finalCoords[1]);

            } else {
                return new Location(map.getWorld(), map.getWarp()[0], map.getWarp()[1], map.getWarp()[2]);
            }
        }

    }

}
