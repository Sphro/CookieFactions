package com.jonah.cookiefactions.chat.common.cmd;

import com.jonah.cookiefactions.chat.common.ChatLib;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			ChatLib lib = new ChatLib(p.getUniqueId());
			if (args.length == 0) {
				p.sendMessage(Text.colorize("&cUsage: /ignore [player]"));
			} else if (!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
				p.sendMessage(Text.colorize("&cThis player has never played before."));
			} else if (lib.isIgnored(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
				lib.removeIgnored(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
				p.sendMessage(Text.colorize("&aRemoved " + Bukkit.getOfflinePlayer(args[0]).getName() + " from your Ignored Players list"));
			} else {
				lib.addIgnored(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
				p.sendMessage(Text.colorize("&aAdded " + Bukkit.getOfflinePlayer(args[0]).getName() + " to your Ignored Players list. If this player is a Staff Member, you will still be able to see his messages in public chat and through Private Messages."));
			}
		} else {
			sender.sendMessage(Text.colorize("&cYou can only run this command as a player."));
		}
		return false;
	}

}
