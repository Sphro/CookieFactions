package com.jonah.cookiefactions.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.jonah.cookiefactions.BukkitPlugin;

import java.io.File;
import java.io.IOException;

public class Config {

    //private Main main;

    private String name;

    private File file;
    private FileConfiguration fileConfiguration;

    public Config(String name, BukkitPlugin main) {
    	
    	//this.main = main;
    	
        this.name = name + ".yml";

        file = new File(main.getDataFolder(), this.name);

        if (!(file.exists())) {
            main.saveResource(this.name, false);
        }

        fileConfiguration = new YamlConfiguration();

        try {
            fileConfiguration.load(file);
        } catch (IOException e) {
            ExceptionReport.report(e);
        } catch (InvalidConfigurationException e) {
            ExceptionReport.report(e);
        }
    }
    
    public Config(String name) {
    	this(name, JavaPlugin.getPlugin(BukkitPlugin.class));
    }
    
    public String getName() {
    	return name;
    }
    
    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void reloadConfig() {
    	try {
			fileConfiguration.load(file);
		} catch (IOException | InvalidConfigurationException e) {
			ExceptionReport.report(e);
		}
    }
    
}
