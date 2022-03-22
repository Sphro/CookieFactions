package com.jonah.cookiefactions.chat.common.cmd.dm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jonah.cookiefactions.chat.ChatManager;
import me.jonah.chat.lang.Messages;

public class ReplyCommand implements CommandExecutor {

	private ChatManager main;

	public ReplyCommand(ChatManager ChatManager) {
		this.main = ChatManager;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		
		if (args.length < 1) {
			sender.sendMessage(Messages.REPLY_COMMAND_USAGE.getMessage());
			return true;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0 ; i < args.length ; i++) {
			builder.append(args[i] + (i == (args.length-1) ? "" : " "));
		}
		
		main.getSenderHandler().reply(sender, builder.toString());
		
		return true;
	}

}
