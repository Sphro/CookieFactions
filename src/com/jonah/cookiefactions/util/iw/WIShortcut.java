package com.jonah.cookiefactions.util.iw;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class WIShortcut {

	private UUID uuid;
	
	public WIShortcut(UUID uuid) {
		this.uuid = uuid;
	}

	public WIShortcut(Player p) {
		this.uuid = p.getUniqueId();
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setPassThrough(boolean b) {
		WriteInventory.setPassThrough(b);
	}
	
	public boolean getPassThrough(boolean b) {
		return WriteInventory.passThrough();
	}
	
	public void set(int i, ClickType type, Runnable rnb) {
		WriteInventory.setInventoryWrite(this.uuid, i, type, rnb);
	}

	public void set(int i, List<ClickType> type, Runnable rnb) {
		WriteInventory.setInventoryWrite(this.uuid, i, type, rnb);
	}
	
	public void set(int i, ClickType[] type, Runnable rnb) {
		WriteInventory.setInventoryWrite(this.uuid, i, type, rnb);
	}
	
	public Runnable get(int slot, ClickType type) {
		return WriteInventory.getInventoryWrite(this.uuid, slot, type);
	}
	
	public Map<ClickType, Runnable> getTaskMap(int slot) {
		return WriteInventory.getTaskMap(this.uuid, slot);
	}
	
	public void remove(int slot) {
		WriteInventory.removeSlot(this.uuid, slot);
	}
	
	public void clear(int slot) {
		WriteInventory.removeSlot(this.uuid, slot);
	}
	
	public void clear() {
		WriteInventory.clearInventoryWrite(this.uuid);
	}
	
}
