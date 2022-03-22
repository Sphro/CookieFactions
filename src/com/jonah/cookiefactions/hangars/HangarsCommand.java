package com.jonah.cookiefactions.hangars;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.policy.ArmorPolicy;
import com.jonah.cookiefactions.util.AbstractUI;
import com.jonah.cookiefactions.util.ExceptionReport;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HangarsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("initmap") && (sender.hasPermission("admin") || sender.isOp())) {
                if (args.length > 1) {
                    try {
                        BukkitPlugin.getInstance().getHangars().initializeMap(args[1]);
                    } catch (Exception e) {
                        ExceptionReport.report(e);
                        sender.sendMessage(Text.colorize("&cCould not initialize world. "));
                    }
                } else {
                    sender.sendMessage(Text.colorize("&cUsage: /hangars initmap [worldname]"));
                }
            }

            else if (args[0].equalsIgnoreCase("loadmap") && (sender.hasPermission("admin") || sender.isOp())) {
                if (args.length > 1) {
                    if (!verifyMapExists(args[1])) {
                        sender.sendMessage(Text.colorize("&cMap does not exist! List maps with /hangars list"));
                    } else {
                        HangarsMap map = BukkitPlugin.getInstance().getHangars().getMap(args[1]);
                        if (!map.loadMap()) {
                            sender.sendMessage(Text.colorize("&cMap failed to load! Check console for errors."));
                        } else {
                            sender.sendMessage(Text.colorize("&aMap loaded! Do /hangars " + map.getName() + " to teleport."));
                        }
                    }
                } else {
                    sender.sendMessage(Text.colorize("&cUsage: /hangars loadmap [worldname]"));
                }
            }

            else if (args[0].equalsIgnoreCase("disablemap") && (sender.hasPermission("admin") || sender.isOp())) {
                if (args.length > 1) {
                    if (!verifyMapExists(args[1])) {
                        sender.sendMessage(Text.colorize("&cMap does not exist! List maps with /hangars list"));
                    } else {
                        HangarsMap map = BukkitPlugin.getInstance().getHangars().getMap(args[1]);

                        if (!map.isEnabled()) {
                            sender.sendMessage(Text.colorize("&aWorld already disabled!"));
                            return true;
                        }

                        if (!map.disableWorld()) {
                            sender.sendMessage(Text.colorize("&cMap failed to load! Check console for errors."));
                        } else {
                            sender.sendMessage(Text.colorize("&aMap loaded! Do /hangars " + map.getName() + " to teleport."));
                        }
                    }
                } else {
                    sender.sendMessage(Text.colorize("&cUsage: /hangars disablemap [worldname]"));

                }
            }

            else if (args[0].equalsIgnoreCase("enablemap") && (sender.hasPermission("admin") || sender.isOp())) {
                if (args.length > 1) {
                    if (!verifyMapExists(args[1])) {
                        sender.sendMessage(Text.colorize("&cMap does not exist! List maps with /hangars list"));
                    } else {
                        HangarsMap map = BukkitPlugin.getInstance().getHangars().getMap(args[1]);

                        if (map.isEnabled()) {
                            sender.sendMessage(Text.colorize("&aWorld already enabled!"));
                            return true;
                        }

                        if (!map.enableWorld()) {
                            sender.sendMessage(Text.colorize("&cMap failed to load! Check console for errors."));
                        } else {
                            sender.sendMessage(Text.colorize("&aMap loaded! Do /hangars " + map.getName() + " to teleport."));
                        }
                    }
                } else {
                    sender.sendMessage(Text.colorize("&cUsage: /hangars enablemap [worldname]"));

                }
            }

            else if (args[0].equalsIgnoreCase("info") && (sender.hasPermission("admin") || sender.isOp())) {
                if (args.length > 1) {
                    if (!verifyMapExists(args[1])) {
                        sender.sendMessage(Text.colorize("&cMap does not exist! List maps with /hangars list"));
                    } else {
                        HangarsMap map = BukkitPlugin.getInstance().getHangars().getMap(args[1]);

                        FileConfiguration config = HangarsMap.getConfig().getConfig();

                        sender.sendMessage(String.format("&bInfo about '%s'", args[1]));

                        for (String line : config.getConfigurationSection(args[1]).getKeys(true)) {
                            String value = String.valueOf(config.getConfigurationSection(args[1]).get(line));
                            sender.sendMessage(Text.colorize(String.format("&7- &a%s: &e%s", line, value)));
                        }

                    }
                } else {
                    sender.sendMessage(Text.colorize("&cUsage: /hangars info [worldname]"));

                }
            }

            else if (args[0].equalsIgnoreCase("list")) {
                StringBuilder b = new StringBuilder("&aHangar maps&7: &e");
                ArrayList<HangarsMap> maps = BukkitPlugin.getInstance().getHangars().getAllMaps();
                for (HangarsMap map : maps) {
                    b.append(map.getName());
                    if (maps.indexOf(map) != (maps.size()-1) && maps.indexOf(map) != -1) b.append(", ");
                }

                sender.sendMessage(Text.colorize(b.toString()));

            }

            else if (args[0].equalsIgnoreCase("setrtp") && (sender.hasPermission("admin") || sender.isOp())) {
                if (args.length > 1) {
                    if (!verifyMapExists(args[1])) {
                        sender.sendMessage(Text.colorize("&cMap does not exist! List maps with /hangars list"));
                    } else {
                        HangarsMap map = BukkitPlugin.getInstance().getHangars().getMap(args[1]);

                        if (args.length > 2) {
                            Player p = (Player) sender;

                            int posX = (int) p.getLocation().getX();
                            int posZ = (int) p.getLocation().getZ();

                            boolean b;
                            if (args[2].equalsIgnoreCase("pos1")) {

                                if (!map.setFirstBounds(posX, posZ)) {
                                    sender.sendMessage(Text.colorize("&cFailed to set coords! Check console for errors."));
                                } else {
                                    sender.sendMessage(Text.colorize("&aFirst position set at X:" + posX + " Z:" + posZ));
                                }
                            }
                            if (args[2].equalsIgnoreCase("pos2")) {
                                if (!map.setSecondBounds(posX, posZ)) {
                                    sender.sendMessage(Text.colorize("&cFailed to set coords! Check console for errors."));
                                } else {
                                    sender.sendMessage(Text.colorize("&aSecond position set at X:" + posX + " Z:" + posZ));
                                }
                            }
                        }
                    }
                } else {
                    sender.sendMessage(Text.colorize("&cUsage: /hangars setrtp [worldname] pos[1/2]"));

                }
            }

            else if (args[0].equalsIgnoreCase("setwarp") && (sender.hasPermission("admin") || sender.isOp())) {
                if (args.length > 1) {
                    if (!verifyMapExists(args[1])) {
                        sender.sendMessage(Text.colorize("&cMap does not exist! List maps with /hangars list"));
                    } else {
                        HangarsMap map = BukkitPlugin.getInstance().getHangars().getMap(args[1]);

                        Player p = (Player) sender;

                        int posX = (int) p.getLocation().getX();
                        int posZ = (int) p.getLocation().getZ();
                        int posY = (int) p.getLocation().getY();

                        if (!map.setWarp(new int[]{posX, posY, posZ})) {
                            sender.sendMessage(Text.colorize("&cFailed to set coords! Check console for errors."));
                        } else {
                            sender.sendMessage(Text.colorize("&aSecond position set at X:" + posX + " Z:" + posZ));
                        }
                    }
                } else {
                    sender.sendMessage(Text.colorize("&cUsage: /hangars setwarp [worldname]"));

                }
            }

            else if (args[0].equalsIgnoreCase("reload")) {
                HangarsMap.getConfig().reloadConfig();
                sender.sendMessage(Text.colorize("&aReloaded hangars config"));
            }

            else if (args[0].equalsIgnoreCase("toggletoken")) {
                ArmorPolicy.token.set(!ArmorPolicy.token.get());
                sender.sendMessage(Text.colorize("&aToggled token to " + ArmorPolicy.token.get()));
            }

            else {
                if (!verifyMapExists(args[0]) || !(sender instanceof Player)) {
                    sender.sendMessage(Text.colorize("&cHangars map does not exist. /hangars list"));
                } else {
                    HangarsMap map = BukkitPlugin.getInstance().getHangars().getMap(args[0]);

                    Hangars.TeleportMethod tp = map.getDefaultMethod();
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("-warp")) tp = Hangars.TeleportMethod.WARP;
                        if (args[1].equalsIgnoreCase("-rtp")) tp = Hangars.TeleportMethod.RTP;
                    }

                    BukkitPlugin.getInstance().getHangars().sendPlayerTo((Player) sender, map, tp);

                }
            }

        } else if (sender instanceof Player){
            AbstractUI.open(new HangarsGUI((Player) sender));
        }

        return true;
    }

    private boolean verifyMapExists(String map) {
        return BukkitPlugin.getInstance().getHangars().isMap(map);
    }

}
