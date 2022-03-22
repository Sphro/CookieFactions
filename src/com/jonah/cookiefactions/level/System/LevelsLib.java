package com.jonah.cookiefactions.level.System;

import com.jonah.cookiefactions.level.LevelsManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.Serializable;

public class LevelsLib implements Serializable {

	private static final long serialVersionUID = 601323521025120986L;

	private int level;
	private int points;
	
	private int requiredPoints;
	
	public LevelsLib(int level, int points) {
		this.level = level;
		this.points = points;
		this.requiredPoints = this.getNeededPoints(level + 1);
	}
	
	public LevelsLib() {
		this.level = 1;
		this.points = 0;
		this.requiredPoints = this.getNeededPoints(level + 1);
	}

	public int getLevel() {
		return this.level;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public void subPoints(int points) {
		this.points = ((this.points -= points) < 0) ? 0 : (this.points -= points);
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void addLevel() {
		this.level++;
	}
	
	public void resetPoints() {
		this.points = 0;
	}
	
	public void resetLevel() {
		this.level = 1;
	}
	
	public void reset() {
		this.level = 1;
		this.points = 0;
		this.requiredPoints = this.getNeededPoints(level + 1);
	}
	
	public int getRequiredPoints() {
		return this.requiredPoints;
	}
	
	public void setRequiredPoints(int points) {
		this.requiredPoints = points;
	}
	
	public int getNeededPoints(int lvl) {
		FileConfiguration config = LevelsManager.getInstance().getConfig();
		if (lvl == 1) return 0;
		int base = config.getInt("Foreach");
		while (lvl > 1) {
			int rndup = this.roundUp((double) lvl / (double) 5);
			base = base + ((rndup * config.getInt("Foreach")) + config.getInt("Foreach"));
			lvl--;
		}
		return base;
	}
	
	public int roundUp(double i) {
		String[] s = String.valueOf(i).split("\\.", 2);
		if (s[1].equals("00") || s[1].equals("0") || s.length == 1) {
			return Integer.valueOf(s[0]);
		} else {
			return Integer.valueOf(s[0]) + 1;
		}
	}
	
}
