package com.jonah.cookiefactions.chat.common.event;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.chat.common.ChatLib;
import com.jonah.cookiefactions.level.LevelsManager;
import com.jonah.cookiefactions.level.System.LevelsLib;
import com.jonah.cookiefactions.level.System.LevelsStorage;
import com.jonah.cookiefactions.level.System.ProgressBar;
import com.jonah.cookiefactions.util.Text;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class MainChat implements Listener {
	
	public static BaseComponent[] getFormattedChat(Player p, String msg, boolean includeLevel) {
		try {
			BukkitPlugin main = BukkitPlugin.getInstance();
			ChatLib lib = new ChatLib(p.getUniqueId());
		    TextComponent rest = new TextComponent((Text.colorize(lib.getPrefix() + " " + lib.getNameColor() + p.getName() + "&r &8» &r" + lib.getChatColor()) + msg));
		    String lvl;
		    TextComponent lvlh = new TextComponent("");
		    if (includeLevel) {
		    	LevelsLib inst = LevelsStorage.getLibInstance(p.getUniqueId());
		     	lvl = Text.colorize("&8[&a" + inst.getLevel() + LevelsManager.getInstance().getHeader() + "&8]&r ");
		     	lvlh = new TextComponent(lvl);
		     	String[] ls =
		     		{"&7Level of " + p.getName() + "&7: &a" + inst.getLevel() + LevelsManager.getInstance().getHeader(),
		             "&7Currently has " + inst.getPoints() + " Points",
		             "&7Points Required for Level " + (inst.getLevel() + 1) + ": " + inst.getRequiredPoints(),
		             " ",
		             "&7Level Progress:",
		             new ProgressBar(p).get() + " &8(&a" + new ProgressBar(p).getPercentage() + "%&8)"};
		        StringBuilder builder2 = new StringBuilder(Text.colorize(ls[0]));
		        for (int i = 1 ; i < ls.length ; i++) {
		        	builder2.append("\n" + Text.colorize(ls[i]));
		        }
		        lvlh.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(builder2.toString()).create()));
		    } else {
		        lvlh = null;
		    }
		    return new BaseComponent[] {lvlh, rest};
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String getFormattedChat(Player p, String msg) {
		ChatLib lib = new ChatLib(p.getUniqueId());
		return getFormattedChat(p) + "&8» &r" + lib.getChatColor() + msg;
	}
	
	public static String getFormattedChat(Player p) {
		ChatLib lib = new ChatLib(p.getUniqueId());
		return Text.colorize(lib.getPrefix() + " " + lib.getNameColor() + p.getName());
	}

	public static String getDMChatFormat(Player p) {
		ChatLib lib = new ChatLib(p.getUniqueId());
		return Text.colorize(lib.hasPrefix() ? (lib.getPrefix() + " ") : "" + lib.getNameColor() + p.getName());
	}

}
