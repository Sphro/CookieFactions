package com.jonah.cookiefactions.chat.common.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.chat.ChatManager;
import com.jonah.cookiefactions.chat.common.ChatLib;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.jonah.chat.lang.Messages;

public class MessageSenderHandler {
	
	private ChatManager main;
	
	private Map<CommandSender, UUID> lastReplies;
	
	public MessageSenderHandler(ChatManager main) {
		this.main = main;
		lastReplies = new HashMap<CommandSender, UUID>();
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			public void onQuit(PlayerQuitEvent e) {
				main.getSenderHandler().clearLastReply(e.getPlayer());
			}
			
		}, BukkitPlugin.getInstance());
		
	}
	
	public void send(CommandSender sender, OfflinePlayer recipient, String msg) {
		
		if (!recipient.isOnline()) {
			sender.sendMessage(Messages.RECIPIENT_OFFLINE.getMessage());
			return;
		}
		
		if (!(sender instanceof ConsoleCommandSender)) {
			Player psender = (Player) sender;
			ChatLib senderLib = new ChatLib(psender.getUniqueId());
			ChatLib recipientLib = new ChatLib(recipient.getUniqueId());
			
			if (senderLib.isIgnored(recipient.getUniqueId())) {
				sender.sendMessage(Messages.IS_IGNORED.getMessage());
				return;
			}
			
			else if (recipientLib.isIgnored(psender.getUniqueId())) {
				sender.sendMessage(Messages.IS_IGNORED.getMessage());
				return;
			}
			
			main.getRecieverHandler().handle(new MsgPacket(sender, recipient.getPlayer(), msg));
			
		}
	}
	
	public void reply(CommandSender sender, String msg) {
		
		if (!lastReplies.containsKey(sender)) {
			sender.sendMessage(Messages.NO_LAST_REPLY.getMessage());
		}
		
		if (!Bukkit.getOfflinePlayer(lastReplies.get(sender)).isOnline()) {
			sender.sendMessage(Messages.LAST_REPLY_OFFLINE.getMessage());
		}
		
		send(sender, Bukkit.getPlayer(lastReplies.get(sender)), msg);
		
	}
	
	public void clearLastReply(CommandSender sender) {
		lastReplies.remove(sender);
	}
	
	public static class MsgPacket {
		
		private CommandSender sender;
		private Player requestedReciever;
		private String message;
		
		private ChatLib senderLib;
		private ChatLib requestedRecieverLib;
		
		public MsgPacket(CommandSender sender, Player requestedReciever, String message) {
			this.sender = sender;
			this.requestedReciever = requestedReciever;
			this.message = message;
			
			this.senderLib = sender instanceof Player ? new ChatLib(((Player) sender).getUniqueId()) : null;
			this.requestedRecieverLib = new ChatLib(requestedReciever.getUniqueId());
			
		}
		
		public String getMessage() {
			return message;
		}
		
		public String getColorizedMessage() {
			return Text.colorize(message);
		}
		
		public CommandSender getSender() {
			return sender;
		}
		
		public Player getRequestedReciever() {
			return requestedReciever;
		}
		
		public ChatLib getSenderChatLibrary() {
			return senderLib;
		}
		
		public ChatLib getRequestedRecieverChatLibrary() {
			return requestedRecieverLib;
		}
		
	}
	
}
