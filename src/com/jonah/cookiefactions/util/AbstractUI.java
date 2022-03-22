package com.jonah.cookiefactions.util;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.iw.WIShortcut;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class AbstractUI {

	private Player p;
	
	public AbstractUI(Player p) {
		this.p = p;
	}
	
	protected abstract Inventory getInv(WIShortcut iw);
	
	public void open() {
		WIShortcut iw = new WIShortcut(p);
		iw.setPassThrough(true);
		try {
			p.openInventory(getInv(iw));
		} catch (Exception e) {
			ExceptionReport.report(e);
			p.sendMessage(BukkitPlugin.color("&cAn exception occured. " + e));
		} finally {
			iw.setPassThrough(false);
		}
	}
	
	protected Player getPlayer() {
		return p;
	}
	
	public static <T extends AbstractUI> void open(T ui) {
		ui.open();
	}
	
}
