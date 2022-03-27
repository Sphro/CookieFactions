package com.jonah.cookiefactions.weaponlist;

import com.jonah.cookiefactions.util.AbstractUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeaponGUICommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("listweapons")) {
            WeaponsGUI.listWeapons(sender);
        } else {
            if (sender instanceof Player) {
                AbstractUI.open(new WeaponsGUI((Player) sender, 1));
            }
        }

        return false;
    }
}
