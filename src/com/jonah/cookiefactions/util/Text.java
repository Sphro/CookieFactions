package com.jonah.cookiefactions.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Text {

	public static String colorize(String s) {
		return String.valueOf(ChatColor.translateAlternateColorCodes('&', s));
	}
	
	public static List<String> colorizeList(List<String> toColorize) {
		List<String> feedBack = new ArrayList<String>();
		for (String s : toColorize) {
			feedBack.add(Text.colorize(s));
		}
		return feedBack;
	}
}
