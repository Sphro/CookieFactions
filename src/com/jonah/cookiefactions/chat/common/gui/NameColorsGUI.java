package com.jonah.cookiefactions.chat.common.gui;

import com.jonah.cookiefactions.chat.common.ChatLib;
import com.jonah.cookiefactions.chat.common.handler.ChatColorPermissionRecord;
import com.jonah.cookiefactions.chat.utils.EnchantGlow;
import com.jonah.cookiefactions.util.AbstractUI;

import com.jonah.cookiefactions.util.ItemBuilder;
import com.jonah.cookiefactions.util.Text;
import com.jonah.cookiefactions.util.iw.WIShortcut;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;



public class NameColorsGUI extends AbstractUI {

	private ChatLib lib;

	public NameColorsGUI(Player p) {
		super(p);
		this.lib = new ChatLib(p);
	}

	@Override
	protected Inventory getInv(WIShortcut iw) {
		Inventory inv = Bukkit.createInventory(null, 27, "Name Colors");
		int i = 0;
		for (ChatColorPermissionRecord record : ChatColorPermissionRecord.RECORDS) {
			if (getPlayer().hasPermission("namecolor." + record.getCode())) {
				inv.setItem(i, getItem(record));
				iw.set(i, ClickType.values(), () -> {
					if (lib.getNameColor().equals(record.raw())) {
						getPlayer().sendMessage(Text.colorize("&cYou already have this set as your Name Color!"));
						getPlayer().playSound(getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.0F);
					} else {
						lib.setNameColor(record.getCode());
						getPlayer().playSound(lib.getPlayer().getLocation(), Sound.WOOD_CLICK, 1.0f, 1.0f);
						AbstractUI.open(this);
					}
				});
				i++;
			}
		}
		return inv;
	}

	protected ItemStack getItem(ChatColorPermissionRecord record) {

		ItemBuilder builder = new ItemBuilder(new Wool(record.getDyeColor()).toItemStack());

		builder = builder.setAmount(1).setName("&8»&r " + record.getGUIName() + "&r &7&nName Color&r &8«").appendLore(
				"&7Your name-Color will display",
				"&7as " + record.getGUIName() + "&r &7when you talk",
				"&7to others in the main chat.",
				" ",
				"&7This will not have any effect on",
				"&7your server gameplay."
		);

		if (lib.getNameColor().equals(ChatColor.getByChar(record.getCode()).toString())) {
			builder = builder.addEnchant(new EnchantGlow(70), 1).appendLore(
					" ",
					"&8»&a Currently Selected"
			);
		}
	
		return builder.build();
	}
}

