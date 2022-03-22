package com.jonah.cookiefactions.level.System;

import com.jonah.cookiefactions.util.Text;
import org.bukkit.entity.Player;

public class ProgressBar {
	
	private LevelsLib inst;
	
	public ProgressBar(Player p) {
		inst = LevelsStorage.getLibInstance(p.getUniqueId());
	}
	
    /*set {_1} to getPoints("%{db_currentlevel.%player's UUID%}%") parsed as integer ({_1} IS POINTS REQ. 
    set {_2} to getPoints("%{db_currentlevel.%player's UUID%}+1%") parsed as integer (
    set {_denominator} to ({_2} - {_1})
    set {_numerator} to ({db_levelpoints.%player's UUID%} - {_1})
    set {_perc} to (({_numerator} * 100)/{_denominator})
    set {_perc} to round({_perc})
    set {_block} to percentBlocks("%{_perc}%")*/
	
	public String get() {
		int num = this.getPercentage() / 10;
		switch (num) {
			case 0:
				return Text.colorize("&c██████████");
			case 1:
				return Text.colorize("&a█&c█████████");
			case 2:
				return Text.colorize("&a██&c████████");
			case 3:
				return Text.colorize("&a███&c███████");
			case 4:
				return Text.colorize("&a████&c██████");
			case 5:
				return Text.colorize("&a█████&c█████");
			case 6:
				return Text.colorize("&a██████&c████");
			case 7:
				return Text.colorize("&a███████&c███");
			case 8:
				return Text.colorize("&a████████&c██");
			case 9:
				return Text.colorize("&a█████████&c█");
			case 10:
				return Text.colorize("&a██████████");
			default:
				return Text.colorize("&c██████████");
		}
	}
	
	public int getPercentage() {
		/*
		If I had 100 points, and I need 150 points to level up, previous level costed 90 points
		150 (mylevel + 1) - 90 (prev lvl points) = denominator
		mypoints - (prev lvl points) = numerator
		*/
		int curpnts = inst.getNeededPoints(inst.getLevel());
		int reqpnts = inst.getNeededPoints(inst.getLevel() + 1);
		int denominator = reqpnts - curpnts;
		int numerator = inst.getPoints() - curpnts;
		//this.p.sendMessage(this.point + " - " + curpnts + " * 100 / " + denominator);
		return ((numerator * 100) / denominator);
	}
}
