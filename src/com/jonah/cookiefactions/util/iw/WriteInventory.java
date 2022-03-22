package com.jonah.cookiefactions.util.iw;

import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.event.inventory.ClickType;

public class WriteInventory {

	private static boolean passThrough = false;

	static Map<UUID, Map<Integer, Map<ClickType, Runnable>>> inventoryWrite;

	public static Map<UUID, Map<Integer, Map<ClickType, Runnable>>> getHash() {
		return inventoryWrite;
	}

	public static boolean passThrough() {
		return passThrough;
	}

	public static void setPassThrough(boolean b) {
		passThrough = b;
	}

	static {
		if (inventoryWrite == null) {
			inventoryWrite = (Map<UUID, Map<Integer, Map<ClickType, Runnable>>>) new HashMap<UUID, Map<Integer, Map<ClickType, Runnable>>>();
		}
	}

	public static void setInventoryWrite(UUID uuid, int i, ClickType type, Runnable rnb) {
		inventoryWrite.putIfAbsent(uuid, new HashMap<Integer, Map<ClickType, Runnable>>());
		inventoryWrite.get(uuid).putIfAbsent(i, new HashMap<ClickType, Runnable>());
		Map<ClickType, Runnable> inv = (Map<ClickType, Runnable>) inventoryWrite.get(uuid).get(i);
		inv.put(type, rnb);
		inventoryWrite.get(uuid).put(i, inv);
	}

	public static List<ClickType> getAllClicks() {
		return Arrays.asList(ClickType.values());
	}

	public static void setInventoryWrite(UUID uuid, int i, List<ClickType> type, Runnable rnb) {
		inventoryWrite.putIfAbsent(uuid, new HashMap<Integer, Map<ClickType, Runnable>>());
		inventoryWrite.get(uuid).putIfAbsent(i, new HashMap<ClickType, Runnable>());
		Map<ClickType, Runnable> inv = (Map<ClickType, Runnable>) inventoryWrite.get(uuid).get(i);
		for (ClickType port : type) {
			inv.put(port, rnb);
		}
		inventoryWrite.get(uuid).put(i, inv);
	}

	public static void setInventoryWrite(UUID uuid, int i, ClickType[] type, Runnable rnb) {
		inventoryWrite.putIfAbsent(uuid, new HashMap<Integer, Map<ClickType, Runnable>>());
		inventoryWrite.get(uuid).putIfAbsent(i, new HashMap<ClickType, Runnable>());
		Map<ClickType, Runnable> inv = (Map<ClickType, Runnable>) inventoryWrite.get(uuid).get(i);
		for (ClickType port : type) {
			inv.put(port, rnb);
		}
		inventoryWrite.get(uuid).put(i, inv);
	}
	
	public static Runnable getInventoryWrite(UUID uuid, int slot, ClickType type) {
		try {
			return inventoryWrite.get(uuid).get(slot).get(type);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static Map<ClickType, Runnable> getTaskMap(UUID uuid, int slot) {
		return (inventoryWrite.get(uuid).containsKey(slot)) ? inventoryWrite.get(uuid).get(slot) : null;
	}

	public static void removeSlot(UUID uuid, int slot) {
		inventoryWrite.get(uuid).put(slot, new HashMap<ClickType, Runnable>());
	}

	public static void clearInventoryWrite(UUID uuid) {
		inventoryWrite.putIfAbsent(uuid, new HashMap<Integer, Map<ClickType, Runnable>>());
		inventoryWrite.get(uuid).clear();
	}

}
