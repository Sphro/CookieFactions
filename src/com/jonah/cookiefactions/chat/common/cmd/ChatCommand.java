package com.jonah.cookiefactions.chat.common.cmd;

import java.util.List;

import com.jonah.cookiefactions.chat.ChatManager;
import com.jonah.cookiefactions.chat.common.ChatLib;
import com.jonah.cookiefactions.util.ConsoleColors;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class ChatCommand implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("chat")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				ChatLib lib = new ChatLib(p.getUniqueId());
				if (args.length == 0) {
					helpMessage(sender);
//				} else if (args[0].equalsIgnoreCase("color")) {
//					if (args.length == 1) {
//						p.sendMessage(Text.colorize(String.valueOf("&cUsage: /chat color [color code]")));
//					} else {
//						String colorcodes = "1,2,3,4,5,6,7,8,9,0,e,a,d,f,c,b";
//						if (colorcodes.contains(args[1]) && !args[1].equals(",") && args[1].toCharArray().length == 1) {
//							if (p.hasPermission("chatcolor." + args[1])) {
//								lib.setChatColor(args[1].toCharArray()[0]);
//								p.sendMessage(Text.colorize(String.valueOf("&eCrazy&6&l&nGTA&r &8»&e Successfully set Chat Color to " + lib.getChatColor() + "this color!")));
//							} else {
//								p.sendMessage(Text.colorize(String.valueOf("&eCrazy&6&l&nGTA&r &8»&c You don't have permission to use this Chat Color.")));
//							}
//						} else {
//							p.sendMessage(Text.colorize(String.valueOf("&eCrazy&6&l&nGTA&r &8»&c This is not a chat color we recognize!")));
//						}
//					}
				} else if (args[0].equalsIgnoreCase("namecolor")) {
					if (args.length == 1) {
						p.sendMessage(Text.colorize(String.valueOf("&cUsage: /chat namecolor [color code]")));
					} else {
						String namecolorcodes = "1,2,3,4,5,6,7,8,9,0,e,a,d,f,c,b";
						if (namecolorcodes.contains(args[1]) && !args[1].equals(",") && args[1].toCharArray().length == 1) {
							if (p.hasPermission("namecolor." + args[1])) {
								lib.setNameColor(args[1].toCharArray()[0]);
								p.sendMessage(Text.colorize(String.valueOf("&eCrazy&6&l&nGTA&r &8»&e Successfully set Name Color to " + lib.getNameColor() + "this color!")));
							} else {
								p.sendMessage(Text.colorize(String.valueOf("&eCrazy&6&l&nGTA&r &8»&c You don't have permission to use this name color.")));
							}
						} else {
							p.sendMessage(Text.colorize(String.valueOf("&eCrazy&6&l&nGTA&r &8»&c This is not a name color we recognize!")));
						}
					}
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (p.hasPermission("admin")) {
						try {
							p.sendMessage(Text.colorize("&aReloading Main Configuration..."));
							ChatManager.getInstance().reloadConfig();
							p.sendMessage(Text.colorize("&aReloading Prefixes Configuration..."));
							p.sendMessage(Text.colorize(String.valueOf("&aChat plugin reloaded successfully.")));
						} catch (Exception e) {
							p.sendMessage(Text.colorize(String.valueOf("&cChat plugin did not reload successfully. Please check the Console for errors.")));
							e.printStackTrace();
							System.out.println(ConsoleColors.ANSI_RED + "Chat plugin did not reload successfully. Please check above for errors.");
						}
					}
				} else {
					p.sendMessage(Text.colorize("&cI don't know what you mean by '" + args[args.length - 1] + "'"));
				}
			} else if (sender instanceof ConsoleCommandSender) {
				ConsoleCommandSender console = (ConsoleCommandSender) sender;
				if (args.length == 0) {
					this.helpMessage(sender);
				} else if (args[0].equalsIgnoreCase("reload")) {
					try {
						console.sendMessage(Text.colorize(String.valueOf("&aChat plugin reloaded successfully.")));
					} catch (Exception e) {
						console.sendMessage(Text.colorize(String.valueOf("&cChat plugin did not reload successfully. Please check the Console for errors.")));
						e.printStackTrace();
						System.out.println(ConsoleColors.ANSI_RED + "Chat plugin did not reload successfully. Please check above for errors.");
					}
				}
			}
		}
		return false;
	}
	private void helpMessage(CommandSender sender) {
		List<String> help = ChatManager.getInstance().getConfig().getStringList("HelpMessage");
		if (help != null) {
			for (int i = 0 ; i < help.size() ; i++) {
				sender.sendMessage(Text.colorize(String.valueOf(help.get(i))));
			}
		} else {
			sender.sendMessage(Text.colorize(String.valueOf("&cCould not find Help Configuration.")));
			sender.sendMessage(Text.colorize(String.valueOf("&cRegistered Commands: /chat, /tag[s]")));
		}
	}
}
