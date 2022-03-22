package com.jonah.cookiefactions.chat.common.handler;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.chat.common.event.MainChat;
import me.jonah.chat.lang.Messages;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class MessageRecieverHandler {
	
	public void handle(MessageSenderHandler.MsgPacket packet) {
		
		if (packet.getSender() instanceof Player) {
			packet.getRequestedReciever().sendMessage(Messages.DM.processAsMessage(
					MainChat.getDMChatFormat((Player) packet.getSender()),
					"me", packet.getMessage()));
			packet.getSender().sendMessage(Messages.DM.processAsMessage("me",
					MainChat.getDMChatFormat((Player) packet.getSender()),
					packet.getMessage()));
		}
		
		else {
			packet.getRequestedReciever().sendMessage(Messages.DM.processAsMessage(
					Messages.CONSOLE_MESSAGER_FORMAT.getMessage(),
					"me",
					packet.getMessage()));
			packet.getSender().sendMessage(Messages.DM.processAsMessage("me",
					Messages.CONSOLE_MESSAGER_FORMAT.getMessage(),
					packet.getMessage()));
		}
		
		BukkitPlugin.getInstance().getLogger().log(Level.INFO, packet.getSender().getName() + " -> " + packet.getRequestedReciever().getName() + " : " + packet.getMessage());
		
	}
	
}
