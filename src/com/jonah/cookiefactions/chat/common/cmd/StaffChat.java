package com.jonah.cookiefactions.chat.common.cmd;

import com.jonah.cookiefactions.chat.common.event.MainChat;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class StaffChat implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender , Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("")) {
				p.sendMessage(Text.colorize("&cYou do not have permission to send messages in the Staff Chat."));
			} else if (args.length == 0) {
				sender.sendMessage(Text.colorize("&cUsage: /staffchat [message]"));
			} else {
				StringBuilder builder = new StringBuilder("");
				for (int i = 0 ; i < args.length ; i++) {
					builder.append(args[i] + " ");
				}
				
				String toSend = Text.colorize("&8[&2Staff Chat&8]&r " + MainChat.getFormattedChat(p, builder.toString()));
				for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
					if (p2.hasPermission("staff.chat")) {
						p2.sendMessage(toSend);
					}
				}
				Bukkit.getConsoleSender().sendMessage(Text.colorize("&8[&2Staff Chat&8] &a" + p.getName() + "&7: &6" + builder.toString()));
			}
		} else if (sender instanceof ConsoleCommandSender) {
			if (args.length == 0) {
				sender.sendMessage(Text.colorize("&cUsage: /staffchat [message]"));
			} else {
				StringBuilder builder = new StringBuilder("");
				for (int i = 0 ; i < args.length ; i++) {
					builder.append(args[i] + " ");
				}
				
				TextComponent staffComponent = new TextComponent(Text.colorize("&8[&2Staff Chat&8] "));
				staffComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Text.colorize("&cThis is a message sent\nfrom the Console")).create()));
	
				TextComponent consoleComponent = new TextComponent(Text.colorize("&aCONSOLE&7: &6" + builder.toString()));
				
				for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
					if (p2.hasPermission("staff.chat")) {
						p2.spigot().sendMessage(staffComponent, consoleComponent);
					}
				}
				Bukkit.getConsoleSender().sendMessage(Text.colorize("&8[&2Staff Chat&8] &aCONSOLE &7: &6" + builder.toString()));
			}
		}
		return false;
	}

	

}
