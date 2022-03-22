package com.jonah.cookiefactions;

import com.jonah.cookiefactions.chat.ChatManager;
import com.jonah.cookiefactions.core.CoreManager;
import com.jonah.cookiefactions.death.StandardDeathFX;
import com.jonah.cookiefactions.hangars.Hangars;
import com.jonah.cookiefactions.hangars.HangarsCommand;
import com.jonah.cookiefactions.level.LevelsManager;
import com.jonah.cookiefactions.policy.ArmorPolicy;
import com.jonah.cookiefactions.scoreboard.CookieFactionsScoreboardManager;
import com.jonah.cookiefactions.util.NameChanger;
import com.jonah.cookiefactions.util.Text;
import com.jonah.cookiefactions.util.iw.WIEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin  {

    private static BukkitPlugin instance;
    private Hangars hangarsManager;
    private CoreManager core;
    private ChatManager chat;
    private LevelsManager levels;
    //private NameChanger nameChanger;
    private CookieFactionsScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        instance = this;
        core = new CoreManager();
        levels = new LevelsManager();
        chat = new ChatManager();

        scoreboardManager = new CookieFactionsScoreboardManager(this);

        //nameChanger = new NameChanger(this);

        for (CookieFactionsManagedComponent comp : new CookieFactionsManagedComponent[] {core, levels, chat}) {
            comp.onEnable(this);
        }

        hangarsManager = new Hangars();
        if (Bukkit.getWorld("spawn") == null) new WorldCreator("spawn").createWorld();
        getCommand("tpworld").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
                StringBuilder b = new StringBuilder();
                if (args.length >= 1 && sender.hasPermission("admin") && sender instanceof Player) {
                    for (String s : args)
                        b.append(s);
                    if (Bukkit.getWorld(b.toString()) == null) {
                        sender.sendMessage("&cWorld does not exist. Check your spelling");
                        return true;
                    }
                    Player p = (Player) sender;
                    p.teleport(Bukkit.getWorld(b.toString()).getSpawnLocation());
                } else {
                    sender.sendMessage(Text.colorize("&cUsage: /tpworld [world name]"));
                    return true;
                }
                return true;
            }
        });

        getCommand("hangars").setExecutor(new HangarsCommand());

        ArmorPolicy armor = new ArmorPolicy();

        Bukkit.getServer().getPluginManager().registerEvents(new WIEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(armor, this);

        getCommand("armor").setExecutor(armor.getArmorCommand());

        Bukkit.getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onFall(EntityDamageEvent e) {
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true);
            }

        }, this);

        Bukkit.getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onDamage(EntityDamageEvent event) {
                if ((event.getEntity() instanceof Player)) {
                    Player player = (Player) event.getEntity();
                    player.getInventory().getHelmet().setDurability((short) 0);
                    player.getInventory().getChestplate().setDurability((short) 0);
                    player.getInventory().getLeggings().setDurability((short) 0);
                    player.getInventory().getBoots().setDurability((short) 0);
                }
            }

            }, this);

        Bukkit.getServer().getPluginManager().registerEvents(new StandardDeathFX(), this);

    }

    public void onDisable() {
        scoreboardManager.clearAllScoreboards();
    }

    public CookieFactionsScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    //public NameChanger getNameChanger() {
    //    return nameChanger;
    //}

    public static BukkitPlugin getInstance() {
        return instance;
    }

    public static CoreManager getCoreManager() {
        return instance.core;
    }

    public CoreManager getCore() {
        return core;
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public Hangars getHangars() {
        return hangarsManager;
    }

}
