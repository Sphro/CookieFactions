package com.jonah.cookiefactions.weaponlist;

import com.jonah.cookiefactions.chat.utils.Repageable;
import com.jonah.cookiefactions.util.AbstractUI;
import com.jonah.cookiefactions.util.ItemBuilder;
import com.jonah.cookiefactions.util.Text;
import com.jonah.cookiefactions.util.iw.WIShortcut;
import com.shampaggon.crackshot.CSDirector;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class WeaponsGUI extends AbstractUI implements Repageable<WeaponsGUI> {

    int page;

    public WeaponsGUI(Player p, int page) {
        super(p);
        this.page = page;
    }

    private static void giveWeapon(CSDirector main, String gunLabel, Player p, int amount) {
        main.csminion.getWeaponCommand(p, gunLabel, false, String.valueOf(amount), false, true);
        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
        p.sendMessage(Text.colorize("&a&lC&b&lF&8&l » &b" + gunLabel + "&a acquired &7[&ex" + amount + "&7]"));
    }

    @Override
    protected Inventory getInv(WIShortcut iw) {

        Inventory inv = Bukkit.createInventory(null, 9 * 6, "Weapon List (" + page + "/2)");

        CSDirector main = JavaPlugin.getPlugin(CSDirector.class);
        int slot = 0;
        for (int parentIndex : main.wlist.keySet().stream().filter(i -> ((page-1)*46 < i && i < (page)*46)).collect(Collectors.toSet())) {
            inv.setItem(slot, new ItemBuilder(main.csminion.vendingMachine(main.wlist.get(parentIndex))).appendLore("&9&m-----------------", "&aRight-Click to acquire", "&eShift-Click to acquire stack").build());
            iw.set(slot, new ClickType[] {ClickType.RIGHT, ClickType.LEFT}, () -> {
                giveWeapon(main, main.wlist.get(parentIndex), getPlayer(), 1);
            });
            iw.set(slot, new ClickType[] {ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT}, () -> {
                giveWeapon(main, main.wlist.get(parentIndex), getPlayer(), 64);
            });
            slot++;
        }

        int[] barrierSlots = {45, 46, 47, 51, 52, 53};
        for (int bSlot : barrierSlots) {
            inv.setItem(bSlot, ItemBuilder.of(Material.STAINED_GLASS_PANE, DyeColor.GRAY).setName(" ").build());
        }

        inv.setItem(48, ItemBuilder.of(Material.ARROW).setName(page == 1 ? "&c<- Previous" : "&a<- Previous").build());
        iw.set(48, ClickType.values(), () -> {
            if (page != 1) AbstractUI.open(repage(page - 1));
        });

        inv.setItem(49, ItemBuilder.of(Material.BOOK).setName("&ePage &7(&e" + page + "/2&7)").build());

        inv.setItem(50, ItemBuilder.of(Material.ARROW).setName(page == 2 ? "&cNext ->" : "&aNext ->").build());
        iw.set(50, ClickType.values(), () -> {
            if (page != 2) AbstractUI.open(repage(page + 1));
        });

        return inv;
    }

    public static void listWeapons(CommandSender sender) {
        CSDirector main = JavaPlugin.getPlugin(CSDirector.class);

        for (Map.Entry<Integer, String> entry : main.wlist.entrySet()) {
            sender.sendMessage(Text.colorize("&7" + entry.getKey() + "&8: &a" + entry.getValue()));
        }

    }

    @Override
    public WeaponsGUI repage(int page) {
        return new WeaponsGUI(getPlayer(), page);
    }
}
