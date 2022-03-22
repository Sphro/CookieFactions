package com.jonah.cookiefactions;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class CookieFactionsManagedComponent {

    public abstract void onEnable(BukkitPlugin plugin);

    public abstract FileConfiguration getConfig();

    public abstract void saveConfig();

    public abstract void reloadConfig();

}
