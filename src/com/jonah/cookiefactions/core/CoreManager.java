package com.jonah.cookiefactions.core;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.CookieFactionsManagedComponent;
import com.jonah.cookiefactions.core.cmd.CookieFactionsCoreCommand;
import com.jonah.cookiefactions.core.event.*;
import com.jonah.cookiefactions.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class CoreManager extends CookieFactionsManagedComponent {

    private Config messages = new Config("messages");
    private Config config = new Config("CoreManager");

    @Override
    public void onEnable(BukkitPlugin main) {

        for (Listener l : new Listener[] {
                new PreventEvents(),
                new NoAnimalsEvent(),
                new NoEnderPearl(),
                new NoRainEvent(),
                new NoFallDamageEvent(),
                new NoModifyBlocks(),
                new NoJoinMessageEvent()
        }) Bukkit.getPluginManager().registerEvents(l, BukkitPlugin.getInstance());

        main.getCommand("cfc").setExecutor(new CookieFactionsCoreCommand());
    }

    @Override
    public void saveConfig() {
        config.save();
    }

    @Override
    public void reloadConfig() {
        for (Config c : new Config[] {messages, config}) {
            c.reloadConfig();
        }
    }

    public FileConfiguration getMessages() {
        return messages.getConfig();
    }

    public static CoreManager getInstance() {
        return BukkitPlugin.getCoreManager();
    }

    public FileConfiguration getConfig() {
        return messages.getConfig();
    }

    public String getRequiredWorldEnchTable() {
        return getConfig().getString("EnchTableWorld", "spawn");
    }

    public String getRequiredWorldEnderChest()  {

        return getConfig().getString("EnderChestWorld", "spawn");

    }

}
