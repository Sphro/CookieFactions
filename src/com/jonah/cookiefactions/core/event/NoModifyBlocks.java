package com.jonah.cookiefactions.core.event;

import com.jonah.cookiefactions.core.lang.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class NoModifyBlocks implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.getPlayer().hasPermission("cookiefactions.break") && !e.getPlayer().isOp()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Messages.NO_BREAK_BLOCK.toString());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().hasPermission("cookiefactions.place") && !e.getPlayer().isOp()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Messages.NO_PLACE_BLOCK.toString());
        }
    }

}
