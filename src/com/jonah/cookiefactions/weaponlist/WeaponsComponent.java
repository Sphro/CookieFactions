package com.jonah.cookiefactions.weaponlist;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.CookieFactionsManagedComponent;
import org.bukkit.configuration.file.FileConfiguration;

public class WeaponsComponent extends CookieFactionsManagedComponent {

    @Override
    public void onEnable(BukkitPlugin plugin) {
        plugin.getCommand("weapons").setExecutor(new WeaponGUICommand());
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
