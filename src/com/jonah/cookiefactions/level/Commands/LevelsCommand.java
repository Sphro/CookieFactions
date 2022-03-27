package com.jonah.cookiefactions.level.Commands;

import com.jonah.cookiefactions.level.System.LevelsLib;
import com.jonah.cookiefactions.level.System.LevelsStorage;
import com.jonah.cookiefactions.level.System.ProgressBar;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelsCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String main, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				/*
				 *             message "&8&m--------------------------------"
            message "&6Your Level: %{db_currentlevel.%player's UUID%}%"
            message "&6You have %{db_levelpoints.%player's UUID%}% Points"
            message "&6Points Required for Next Level: %{db_pointsrequired.%player's UUID%}%"
            message " "
            set {_1} to getPoints("%{db_currentlevel.%player's UUID%}%") parsed as integer
            set {_2} to getPoints("%{db_currentlevel.%player's UUID%}+1%") parsed as integer
            set {_denominator} to ({_2} - {_1})
            set {_numerator} to ({db_levelpoints.%player's UUID%} - {_1})
            set {_perc} to (({_numerator} * 100)/{_denominator})
            set {_perc} to round({_perc})
            set {_block} to percentBlocks("%{_perc}%")
            message "&6Level Progress:"
            message "%{_block}% &8(&a%{_perc}%%%&8)"
            message "&8&m--------------------------------"
				 */
				LevelsStorage storage = new LevelsStorage(p.getUniqueId());
				ProgressBar prog = new ProgressBar(p);
				LevelsLib inst = storage.getLibInstance();
				p.sendMessage(Text.colorize(String.format("&8&m--------------------------------")));
				p.sendMessage(Text.colorize(String.format("&aYour Level: &e%s", inst.getLevel())));
				p.sendMessage(Text.colorize(String.format("&aYou have &e%s&a Points", inst.getPoints())));
				p.sendMessage(Text.colorize(String.format("&aPoints Required for Next Level: &e%s", inst.getRequiredPoints())));
				p.sendMessage(Text.colorize(" "));
				p.sendMessage(Text.colorize("&aLevel Progress:"));
				p.sendMessage(Text.colorize(String.format("%s &8(&a%s%&8)", prog.get(), prog.getPercentage())));
				p.sendMessage(Text.colorize(String.format("&8&m--------------------------------")));
			} else if (args[0].equalsIgnoreCase("addpoints") && p.hasPermission("admin.commands")) {
				if (args.length == 1) {
					p.sendMessage(Text.colorize("&cUsage: /level addpoints [player] [amount]"));
				} else if (Bukkit.getOfflinePlayer(args[1]) == null) {
					p.sendMessage(Text.colorize("&cPlayer not found!"));
				} else {
					try {
						LevelsStorage.addPnts(Bukkit.getOfflinePlayer(args[1]).getUniqueId(), Integer.valueOf(args[2]));
						p.sendMessage(Text.colorize("&aAdded " + args[2] + " points to " + args[1] + ". Now has " + LevelsStorage.getLibInstance(p.getUniqueId()).getPoints()));
					} catch (NumberFormatException e) {
						p.sendMessage(Text.colorize("&cThe argument entered where number expected '" + args[2] + "'"));
					}
				}
			} else if (args[0].equalsIgnoreCase("delpoints") && p.hasPermission("admin.commands")) {
				p.sendMessage(Text.colorize("&cUnimplemented Administrative Feature"));
			} else if (args[0].equalsIgnoreCase("setlevel") && p.hasPermission("admin.commands")) {
				p.sendMessage(Text.colorize("&cUnimplemented Administrative Feature"));
			} else if (args[0].equalsIgnoreCase("reset") && p.hasPermission("admin.commands")) {
				if (args.length == 1) {
					p.sendMessage(Text.colorize("&cUsage: /level reset [player]"));
				} else if (Bukkit.getPlayer(args[1]) == null) {
					p.sendMessage(Text.colorize("&cPlayer not found!"));
				} else {
					try {
						LevelsStorage storage = new LevelsStorage(p.getUniqueId());
						LevelsLib inst = storage.getLibInstance();
						inst.reset();
						storage.setLibInstance(inst);
						p.sendMessage(Text.colorize("&cReset " + args[1] + " level stats"));
					} catch (NumberFormatException e) {
						p.sendMessage(Text.colorize("&cThe argument entered where number expected '" + args[2] + "'"));
					}
				}
			}
		}
		return false;
	}

}
