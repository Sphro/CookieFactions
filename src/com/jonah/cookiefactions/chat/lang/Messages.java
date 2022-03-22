package me.jonah.chat.lang;

import com.jonah.cookiefactions.chat.ChatManager;
import com.jonah.cookiefactions.util.Text;

public enum Messages {

	DM("%s -> %r > %m", "DM_Format"),
	CONSOLE_MESSAGER_FORMAT("&aConsole", "Console_Messager_Format"),
	NO_LAST_REPLY("&cYou don't have any last replies", "No_Last_Reply"),
	LAST_REPLY_OFFLINE("&cThe person you last responded to went offline", "Last_Reply_Offline"),
	RECIPIENT_OFFLINE("&cThe recipient is offline", "Recipient_Offline"),
	MESSAGE_COMMAND_USAGE("&cUsage: /message [player] [message]", "Message_Command_Usage"),
	REPLY_COMMAND_USAGE("&cUsage: /r [message]", "Reply_Command_Usage"),
	IS_IGNORED("&cThis player is ignored", "Is_Ignored");
	
	private String message;
	private String configPath;
	
	Messages(String message, String configPath) {
		this.message = message;
		this.configPath = configPath;
	}
	
	public String getMessage() {
		String retMsg = ChatManager.getInstance().getMessages().getConfig().getString(configPath, "null");
		if (retMsg.equals("null")) {
			return Text.colorize(message);
		} else return Text.colorize(retMsg);
	}
	
	public String processAsMessage(String senderTitle, final String recipientTitle, final String sentMessage) {		
		return Text.colorize(getMessage().replace("%s", senderTitle).replace("%r", recipientTitle)).replace("%m", sentMessage);
	}
	
}
