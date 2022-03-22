package com.jonah.cookiefactions.chat.common;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.chat.ChatManager;
import com.jonah.cookiefactions.level.System.LevelsStorage;
import com.jonah.cookiefactions.util.ExceptionReport;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatLib {

	public static Map<Character, ChatColor> CHARMAP = new HashMap<>();

	static {
		for (char c : "4c6e2ab39578df".toCharArray()) {
			CHARMAP.put(c, ChatColor.getByChar(c));
		}
	}

	private FileConfiguration config;
	
	private File filePath;
	
	private UUID uuid;
	
	public ChatLib(UUID uuid) {
		
		this.uuid = uuid;
		
		BukkitPlugin main = BukkitPlugin.getInstance();
		File fileDirectory = new File(BukkitPlugin.getInstance().getDataFolder() + "/userdata");
		filePath = new File(fileDirectory, uuid.toString() + ".yml");
		
		if (!fileDirectory.exists()) fileDirectory.mkdirs();
		
		boolean needsPresets = false;
		if (!filePath.exists()) {
			try {
				needsPresets = true;
				filePath.createNewFile();
			} catch (IOException e) {
				ExceptionReport.report(e);
			}
		}
		
		config = new YamlConfiguration();
		
		try {
			config.load(filePath);
			if (needsPresets) {
				config.set("prefix", "blank");
				config.set("chatcolor", "blank");
				config.set("namecolor", "blank");
				config.set("ignoredplayers", new ArrayList<String>());
			}
			config.save(filePath);
		} catch (IOException | InvalidConfigurationException e) {
			ExceptionReport.report(e);
		}
		
	}
	
	public ChatLib(Player p) {
		this(p.getUniqueId());
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}
	
	public ChatLib setReferencedConfig(FileConfiguration config) {
		this.config = config;
		return this;
	}

	public String getPrefixReference() {
		return config.getString("prefix");
	}

	public String getChatColorReference() {
		return config.getString("chatcolor");
	}
	
	public String getNameColorReference() {
		return config.getString("namecolor");
	}
	
	public String getPrefix() {
		if (getPrefixReference().equalsIgnoreCase("blank")) return "";
		return Text.colorize(getPrefixReference());
	}

	public String getChatColor() {
		if (getChatColorReference().equalsIgnoreCase("blank")) return "§r";
		return getChatColorReference();
	}
	
	public String getNameColor() {
		if (getNameColorReference().equalsIgnoreCase("blank")) return "§r";
		return getNameColorReference();
	}
	
	public List<UUID> getIgnoredPlayers() {
		return config.getStringList("ignoredplayers").stream().map(s -> UUID.fromString(s)).collect(Collectors.toList());
	}

	public boolean hasPrefix() {
		return !config.getString("prefix").equalsIgnoreCase("blank");
	}
	
	public boolean hasChatColor() {
		return !config.getString("chatcolor").equalsIgnoreCase("blank");
	}
	
	public boolean hasNameColor() {
		return !config.getString("namecolor").equalsIgnoreCase("blank");
	}
	
	public void removePrefix() {
		config.set("prefix", "blank");
		save();
	}
	
	public void removeNameColor() {
		config.set("namecolor", "blank");
		save();
	}
	
	public void removeChatColor() {
		config.set("chatcolor", "blank");
		save();
	}

	
	public void setPrefix(String prefRef) {
		config.set("prefix", prefRef);
		save();
	}
	
	public void setChatColor(char c) {
		config.set("chatcolor", "§" + c);
		save();
	}
	
	public void setNameColor(char c) {
		config.set("namecolor", "§" + c);
		save();
	}
	
	public void setOverride(String ref) {
		config.set("override", ref);
		save();
	}

	public void addIgnored(UUID uuid) {
		
		List<String> ignoredPlayers = config.getStringList("ignoredplayers");
		
		ignoredPlayers.add(uuid.toString());
		
		config.set("ignoredplayers", ignoredPlayers);
		
		save();
		
	}
	
	public boolean removeIgnored(UUID uuid) {
		
		List<String> ignoredPlayers = config.getStringList("ignoredplayers");
		
		if (!ignoredPlayers.contains(uuid.toString())) return false;
		
		else {
			
			ignoredPlayers.remove(uuid.toString());
			
			config.set("ignoredplayers", ignoredPlayers);
			
			save();
			
			return true;
			
		}
	}
	
	public boolean isIgnored(UUID uuid) {
		return config.getStringList("ignoredplayers").contains(uuid.toString());
	}
	
	public int getLevel() {
		return LevelsStorage.getLibInstance(uuid).getLevel();
	}
	
	private void save() {
		try {
			config.save(filePath);
		} catch (IOException e) {
			ExceptionReport.report(e);
		}
	}
	
}
