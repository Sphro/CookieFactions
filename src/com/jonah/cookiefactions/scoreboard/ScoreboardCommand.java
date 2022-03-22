package com.jonah.cookiefactions.scoreboard;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreboardCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("update")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                BukkitPlugin.getInstance().getScoreboardManager().getScoreboard(p).update(p, p.getScoreboard());
                p.sendMessage(Text.colorize("&aAttempting to update " + BukkitPlugin.getInstance().getScoreboardManager().getScoreboard(p)));

            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("reset")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                BukkitPlugin.getInstance().getScoreboardManager().getScoreboard(p).update(p, p.getScoreboard());
                p.sendMessage(Text.colorize("&aAttempting to update " + BukkitPlugin.getInstance().getScoreboardManager().getScoreboard(p)));

            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("testRemove")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                BukkitPlugin.getInstance().getScoreboardManager().getScoreboard(p).clearLines(p);
                p.sendMessage(Text.colorize("&aAttempting to clear lines of " + BukkitPlugin.getInstance().getScoreboardManager().getScoreboard(p)));

            }
        }

        return false;
    }
}
