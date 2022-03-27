package com.jonah.cookiefactions.level;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.CookieFactionsManagedComponent;
import com.jonah.cookiefactions.level.Commands.LevelsCommand;
import com.jonah.cookiefactions.level.Events.Death;
import com.jonah.cookiefactions.papi.LevelsPapiIntegration;
import com.jonah.cookiefactions.util.Config;
import com.jonah.cookiefactions.util.ExceptionReport;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class LevelsManager extends CookieFactionsManagedComponent {

	private static LevelsManager levelsManager;
	private Config config;

	public static String HEADER = "✰";

	@Override
	public void onEnable(BukkitPlugin plugin) {
		levelsManager = this;
		config = new Config("LevelsManager");

		try {
			this.checkForPAPI(plugin);
		} catch (RuntimeException e) {
			ExceptionReport.report(e);
			System.out.println("Placeholder API Not found for LevelSystem!");
		}
		plugin.getCommand("level").setExecutor(new LevelsCommand());
//		Bukkit.getServer().getPluginManager().registerEvents(new Death(), plugin);
	}

	@Override
	public FileConfiguration getConfig() {
		return config.getConfig();
	}

	@Override
	public void saveConfig() {
		config.save();
	}

	@Override
	public void reloadConfig() {
		config.reloadConfig();
	}

	public String getHeader() {
		return config.getConfig().isSet("Header") ? getConfig().getString("Header") : HEADER;
	}

	public static LevelsManager getInstance() {
		return levelsManager;
	}

	public void onDisable() {
		System.out.println("LevelSystem Shutting Down!");
	}
	
	private void checkForPAPI(BukkitPlugin plugin) {
		if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			throw new RuntimeException("PlaceholderAPI Not Found! Required for LevelSystem to work!");	
		} else {
			new LevelsPapiIntegration(plugin).register();
		}
	}
	
}
