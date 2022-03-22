package com.jonah.cookiefactions.util.iw;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class WIEvent implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (WriteInventory.passThrough()) return;
		WriteInventory.clearInventoryWrite(e.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClick(InventoryClickEvent e) {
		Runnable runs = WriteInventory.getInventoryWrite(e.getWhoClicked().getUniqueId(), e.getRawSlot(), e.getClick());
		try {
			if ((runs == null) && !WriteInventory.getTaskMap(e.getWhoClicked().getUniqueId(), e.getRawSlot()).isEmpty()) {
				e.setCancelled(true);
				return;
			}
		} catch (NullPointerException e2) {

		}
		try {
			if (e.getCurrentItem().getType() != Material.AIR && !WriteInventory.getHash().get(e.getWhoClicked().getUniqueId()).isEmpty()) {
				e.setCancelled(true);
				runs.run();
			} else return;
		} catch (NullPointerException e2) {
			return;
		}
	}
}
