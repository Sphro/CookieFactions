package com.jonah.cookiefactions.core.event;

import com.jonah.cookiefactions.chat.common.event.MainChat;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NoJoinMessageEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(Text.colorize("&8&l[&a&l+&8&l] " + MainChat.getFormattedChat(e.getPlayer())));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(Text.colorize("&8&l[&c&l-&8&l] " + MainChat.getFormattedChat(e.getPlayer())));
    }

}
