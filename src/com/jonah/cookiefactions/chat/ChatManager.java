package com.jonah.cookiefactions.chat;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.CookieFactionsManagedComponent;
import com.jonah.cookiefactions.chat.common.cmd.ChatCommand;
import com.jonah.cookiefactions.chat.common.cmd.IgnoreCommand;
import com.jonah.cookiefactions.chat.common.cmd.StaffChat;
import com.jonah.cookiefactions.chat.common.cmd.dm.DMCommand;
import com.jonah.cookiefactions.chat.common.cmd.dm.ReplyCommand;
import com.jonah.cookiefactions.chat.common.event.ChatListener;
import com.jonah.cookiefactions.chat.common.event.TagCommand;
import com.jonah.cookiefactions.chat.common.handler.MessageRecieverHandler;
import com.jonah.cookiefactions.chat.common.handler.MessageSenderHandler;
import com.jonah.cookiefactions.chat.utils.EnchantGlow;
import com.jonah.cookiefactions.util.Config;
import org.bukkit.Bukkit;

import org.bukkit.configuration.file.FileConfiguration;

public class ChatManager extends CookieFactionsManagedComponent {

	private Config messages;
	private Config chat;

	private MessageSenderHandler senderHandler;
	private MessageRecieverHandler recieverHandler;
	
	private static ChatManager instance;

	@Override
	public void onEnable(BukkitPlugin plugin) {

		chat = new Config("chat");
		instance = this;
		messages = new Config("messages", plugin);
		senderHandler = new MessageSenderHandler(this);
		recieverHandler = new MessageRecieverHandler();

//		activateDataBase();
		new EnchantGlow(70).registerGlow();
		registerEvents();
		registerCommands(plugin);
		System.out.println("Enabled Chat Plugin by Sphro");
	}

	@Override
	public FileConfiguration getConfig() {
		return chat.getConfig();
	}

	@Override
	public void reloadConfig() {
		chat.reloadConfig();
		messages.reloadConfig();
	}

	@Override
	public void saveConfig() {
		chat.save();
	}

	public static ChatManager getInstance() {
		return instance;
	}
	
	public MessageSenderHandler getSenderHandler() {
		return senderHandler;
	}
	
	public MessageRecieverHandler getRecieverHandler() {
		return recieverHandler;
	}
	
	public void onDisable() {
		System.out.println("Disabled Chat Plugin by Sphro");
	}

	public Config getMessages() {
		return messages;
	}
	
	private void registerEvents() {
		Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(), BukkitPlugin.getInstance());
	}
	
	private void registerCommands(BukkitPlugin instance) {
		instance.getCommand("chat").setExecutor(new ChatCommand());
		instance.getCommand("ignore").setExecutor(new IgnoreCommand());
		instance.getCommand("staffchat").setExecutor(new StaffChat());
		instance.getCommand("message").setExecutor(new DMCommand(this));
		instance.getCommand("reply").setExecutor(new ReplyCommand(this));
		instance.getCommand("tag").setExecutor(new TagCommand());
	}
	
//	private void activateDataBase() {
//		if (config.getString("Database.SQLType") == "disabled") return;
//		try (Connection conn = DriverManager.getConnection(url, usrname, password)) {
//			ArrayList<String> tables = new ArrayList<String>();
//			// INTENTIONAL 25 MAX CHARS
//			tables.add("maindata(UUID varchar(255), ChatColor varchar(25), NameColor varchar(25), PlayerOverride varchar(255)");
//			tables.add("ignoredplayers(UUID varchar(255), IgnoredUUID varchar(255))");
//			for (int i2 = 0; i2 < tables.size(); i2++) {
//				PreparedStatement stat = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + tables.get(i2));
//				stat.executeUpdate();
//			}
//		} catch (SQLException e) {
//			System.out.println(ConsoleColors.ANSI_RED + "Database Failed to Load." + ConsoleColors.ANSI_RESET);
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			System.out.println(ConsoleColors.ANSI_RED + "Database Failed to Register." + ConsoleColors.ANSI_RESET);
//			e.printStackTrace();
//		}
//	}
//
//	@EventHandler(priority = EventPriority.HIGHEST)
//	public void onPlayerJoin(PlayerJoinEvent e) {
//		String uuid = e.getPlayer().getUniqueId().toString();
//		if (config.getString("Database.SQLType") == "disabled") return;
//		try (Connection conn = DriverManager.getConnection(url, usrname, password)) {
//			PreparedStatement stat = conn.prepareStatement("INSERT INTO maindata (UUID, ChatColor, NameColor, PlayerOverride) SELECT * FROM (SELECT '" + uuid + "', NULL, NULL, NULL) AS TEMP WHERE NOT EXISTS (SELECT UUID FROM maindata WHERE UUID='" + uuid + "') LIMIT 1;");
//			stat.executeUpdate();
//		} catch (SQLException e2) {
//			//
//		}
//	}
	
}
