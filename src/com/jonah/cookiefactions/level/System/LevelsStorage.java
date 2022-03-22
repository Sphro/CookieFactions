package com.jonah.cookiefactions.level.System;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class LevelsStorage {

	private UUID uuid;
	private LevelsLib lib;
	private File fileDirectory;
	private File filePath;
	
	public LevelsStorage(UUID uuid) {
		this.uuid = uuid;
		this.fileDirectory = new File(BukkitPlugin.getInstance().getDataFolder().toString() + "/data/players/levelslib/" + this.uuid.toString());
		this.filePath = new File(this.fileDirectory, "data.dat");
		if (!this.fileDirectory.exists()) this.fileDirectory.mkdirs();
		if (!this.filePath.exists()) {
			try {
				this.filePath.createNewFile();
			} catch (IOException e) {
				if (!(e instanceof EOFException)) {
					e.printStackTrace();
				}
			}
		}
		this.lib = (LevelsLib) DataStreaming.load(this.filePath);
		if (this.lib == null) {
			this.lib = new LevelsLib();
			refresh();
		}
	}
	
	private void refresh() {
		DataStreaming.save(this.lib, this.filePath);
		this.lib = (LevelsLib) DataStreaming.load(this.filePath);
	}
	
	public LevelsLib getLibInstance() {
		return this.lib;
	}
	
	public void setLibInstance(LevelsLib instance) {
		this.lib = instance;
		refresh();
	}
	
	public static LevelsLib getLibInstance(UUID uuid) {
		return new LevelsStorage(uuid).getLibInstance();
	}
	
	public static void addPnts(UUID uuid, int points) {
		LevelsStorage lvls = new LevelsStorage(uuid);
		lvls.addPoints(points, true);
	}
	
	public void addPoints(int points, boolean msgs) {
		LevelsLib ins = this.getLibInstance();
		ins.addPoints(points);
		Player p = Bukkit.getPlayer(this.uuid);
		while (ins.getPoints() >= ins.getRequiredPoints()) {
			ins.addLevel();
			ins.setRequiredPoints(ins.getNeededPoints((ins.getLevel() + 1)));
			if (msgs) {
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
				p.sendMessage(Text.colorize("&0(&e+&0)&r &6You leveled up to Level " + ins.getLevel() + "! You are now required of " + ins.getRequiredPoints() + " Level Points to reach Level " + (ins.getLevel() + 1)));
			}
		}
		this.setLibInstance(ins);
	}
	
}
