package com.jonah.cookiefactions.papi;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.level.System.LevelsStorage;
import com.jonah.cookiefactions.level.System.ProgressBar;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class LevelsPapiIntegration extends PlaceholderExpansion {

	private BukkitPlugin myPlugin;
	
	public LevelsPapiIntegration(BukkitPlugin myPlugin) {
		this.myPlugin = myPlugin;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (identifier.equals("points")) {
			return String.valueOf(new LevelsStorage(p.getUniqueId()).getLibInstance().getPoints());
		} else if (identifier.equals("level")) {
			return String.valueOf(new LevelsStorage(p.getUniqueId()).getLibInstance().getLevel());
		} else if (identifier.equals("progressbar")) {
			return String.valueOf(new ProgressBar(p).get());
		} else if (identifier.equals("percentage")) {
			return String.valueOf(new ProgressBar(p).getPercentage());
		} else {
			return null;
		}
	}
	
	@Override
	public String getAuthor() {
		return myPlugin.getDescription().getAuthors().toString();
	}

	@Override
	public String getIdentifier() {
		return "levelsystem";
	}

	@Override
	public String getVersion() {
		return myPlugin.getDescription().getVersion();
	}

}
