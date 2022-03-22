package com.jonah.cookiefactions.core.cmd;

import com.jonah.cookiefactions.core.CoreManager;
import com.jonah.cookiefactions.util.ExceptionReport;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CookieFactionsCoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("admin")) return true;

        else if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                try {
                    CoreManager.getInstance().reloadConfig();
                    sender.sendMessage(Text.colorize("&aCookieFactionsCore reloaded successfully"));
                } catch (Exception e) {
                    ExceptionReport.report(e);
                    sender.sendMessage(Text.colorize("&cCookieFactionsCore did not reload successfully. Please check console"));
                }
            }
        }

        return true;
    }

}
