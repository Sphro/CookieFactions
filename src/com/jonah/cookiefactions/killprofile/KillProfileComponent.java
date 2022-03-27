package com.jonah.cookiefactions.killprofile;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.CookieFactionsManagedComponent;
import com.jonah.cookiefactions.killprofile.cmd.KillProfileCommand;
import com.jonah.cookiefactions.killprofile.event.KillPlayerEvent;
import com.jonah.cookiefactions.killprofile.runnable.MultiKillManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class KillProfileComponent extends CookieFactionsManagedComponent {

    private MultiKillManager manager;

    @Override
    public void onEnable(BukkitPlugin plugin) {
        manager = new MultiKillManager(plugin);
        Bukkit.getPluginManager().registerEvents(new KillPlayerEvent(), plugin);
        plugin.getCommand("killsprofile").setExecutor(new KillProfileCommand());
    }

    public MultiKillManager getManager() {
        return manager;
    }

    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void reloadConfig() {

    }
}
