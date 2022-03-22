package com.jonah.cookiefactions.core.event;

import com.jonah.cookiefactions.core.CoreManager;
import com.jonah.cookiefactions.core.lang.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreventEvents implements Listener {

    @EventHandler
    public void onAccess(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
            if (!CoreManager.getInstance().getRequiredWorldEnchTable().equalsIgnoreCase(e.getPlayer().getWorld().getName())) {
                e.setUseInteractedBlock(Event.Result.DENY);
                e.getPlayer().sendMessage(Messages.NO_INTERACT_BLOCK.toString());
                return;
            }
        }

        if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
            if (!CoreManager.getInstance().getRequiredWorldEnderChest().equalsIgnoreCase(e.getPlayer().getWorld().getName())) {
                e.setUseInteractedBlock(Event.Result.DENY);
                e.getPlayer().sendMessage(Messages.NO_INTERACT_BLOCK.toString());
                return;
            }
        }

        switch (e.getClickedBlock().getType()) {
            case BURNING_FURNACE:
            case STORAGE_MINECART:
            case BEACON:
            case BREWING_STAND:
            case DROPPER:
            case DISPENSER:
            case FURNACE:
            case ANVIL:
            case HOPPER:
            case HOPPER_MINECART:
            case WORKBENCH:
                e.setUseInteractedBlock(Event.Result.DENY);
                e.getPlayer().sendMessage(Messages.NO_INTERACT_BLOCK.toString());
                return;

            default: return;
        }

    }

}
