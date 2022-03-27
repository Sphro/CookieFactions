package com.jonah.cookiefactions.killprofile.cmd;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.killprofile.runnable.MultiKillManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillProfileCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("fakedeath")) {
            if (args.length > 1) {
                MultiKillManager manager = BukkitPlugin.getInstance().getKillProfile().getManager();

                Player killer = (Player) sender;
                String victim = args[1];

                if (manager.doesPlayerExist(killer)) {
                    manager.killedPlayer(killer, victim);
                } else {
                    manager.registerPlayer(killer, victim);
                }
            }
        }

        return true;
    }
}
