package com.jonah.cookiefactions.chat.common.event;

import com.jonah.cookiefactions.chat.common.ChatLib;
import com.jonah.cookiefactions.util.ExceptionReport;
import com.jonah.cookiefactions.util.Text;
import com.nametagedit.plugin.NametagEdit;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        event.setCancelled(true);
        try {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                ChatLib pLib = new ChatLib(p);
                if (pLib.isIgnored(event.getPlayer().getUniqueId()) && !event.getPlayer().hasPermission("ignore.exempt")) continue;
                else p.spigot().sendMessage(MainChat.getFormattedChat(event.getPlayer(), event.getMessage(), true));
            }
            BaseComponent[] comps = MainChat.getFormattedChat(event.getPlayer(), event.getMessage(), true);
            Bukkit.getConsoleSender().sendMessage(comps[0].toPlainText() + comps[1].toPlainText());
            Bukkit.getConsoleSender().sendMessage(Text.colorize("&8[&6Main Chat&8] &e" + event.getPlayer().getName() + "&8: &7" + event.getMessage()));
        } catch (Exception e) {
            ExceptionReport.report(e);
            event.getPlayer().sendMessage(Text.colorize("&cAn error occured. " + e));
            throw e;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        ChatLib lib = new ChatLib(e.getPlayer());

        if (lib.hasPrefix()) {

            //p.setPlayerListName(Text.colorize(lib.getPrefix() + " " + "&f" + p.getName()));

            NametagEdit.getApi().setPrefix(p, Text.colorize(lib.getPrefix() + " &f"));
        }

        for (Player lp : Bukkit.getOnlinePlayers()) {
            lp.hidePlayer(p);
            lp.showPlayer(p);
        }

    }

}
