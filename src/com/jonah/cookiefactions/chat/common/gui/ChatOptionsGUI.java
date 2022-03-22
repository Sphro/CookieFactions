package com.jonah.cookiefactions.chat.common.gui;

import com.jonah.cookiefactions.util.AbstractUI;
import com.jonah.cookiefactions.util.ItemBuilder;
import com.jonah.cookiefactions.util.iw.WIShortcut;
import com.jonah.cookiefactions.util.iw.WriteInventory;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Dye;
import org.bukkit.material.Wool;

public class ChatOptionsGUI extends AbstractUI {

	public ChatOptionsGUI(Player p) {
		super(p);
	}

	@Override
	protected Inventory getInv(WIShortcut iw) {
		Inventory inv = Bukkit.createInventory(null, 27, "Chat Options");
		getPlayer().playSound(getPlayer().getLocation(), Sound.WOOD_CLICK, 1.0f, 1.0f);

		inv.setItem(10, ItemBuilder.of(Material.NAME_TAG).setName("&e&lMy Tags").setAmount(1).build());

		inv.setItem(13, new ItemBuilder(new Wool(DyeColor.RED).toItemStack()).setName("&c&lMy Name Colors").setAmount(1).build());//new Wool(DyeColor.RED).toItemStack(), 1, "�c�lMy Name Colors"));
		iw.set(13, ClickType.values(), () -> {
			AbstractUI.open(new NameColorsGUI(getPlayer()));
		});

		inv.setItem(16, new ItemBuilder(new Dye(DyeColor.LIGHT_BLUE).toItemStack()).setName("&b&lMy Chat Colors").setAmount(1).build());
		iw.set(16, WriteInventory.getAllClicks(), () -> AbstractUI.open(new ChatColorsGUI(getPlayer())));

		return inv;
	}
}
