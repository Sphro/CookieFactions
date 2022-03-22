package com.jonah.cookiefactions.chat.common.cmd.dm;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jonah.cookiefactions.chat.ChatManager;
import me.jonah.chat.lang.Messages;

public class DMCommand implements CommandExecutor {

	private ChatManager ChatManager;
	
	public DMCommand(ChatManager ChatManager) {
		this.ChatManager = ChatManager;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		
		if (args.length < 2) {
			sender.sendMessage(Messages.MESSAGE_COMMAND_USAGE.getMessage());
			return true;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 1 ; i < args.length ; i++) {
			builder.append(args[i] + ((i == args.length - 1) ? "" : " "));
		}
		
		ChatManager.getSenderHandler().send(sender, Bukkit.getOfflinePlayer(args[0]), builder.toString());
		
		return true;
	}

}
