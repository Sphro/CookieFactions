package com.jonah.cookiefactions.hangars;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.AbstractUI;
import com.jonah.cookiefactions.util.ItemBuilder;
import com.jonah.cookiefactions.util.iw.WIShortcut;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HangarsGUI extends AbstractUI {


    public HangarsGUI(Player p) {
        super(p);
    }

    @Override
    protected Inventory getInv(WIShortcut iw) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, "Hangar Maps");

        int[] slots = {10, 12, 14, 16};
        List<HangarsMap> maps = BukkitPlugin.getInstance().getHangars().getAllMaps();

        for (int i = 0 ; i < maps.size() ; i ++) {
            int finalI = i;
            HangarsMap map = maps.get(finalI);
            iw.set(slots[i], new ClickType[] {ClickType.RIGHT, ClickType.LEFT}, new Runnable() {
                @Override
                public void run() {
                    BukkitPlugin.getInstance().getHangars().sendPlayerTo(getPlayer(), map, Hangars.TeleportMethod.WARP);
                }
            });
            iw.set(slots[i], new ClickType[] {ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT}, new Runnable() {
                @Override
                public void run() {
                    BukkitPlugin.getInstance().getHangars().sendPlayerTo(getPlayer(), map, Hangars.TeleportMethod.RTP);
                }
            });

            //inv.setItem(0, new ItemStack(Material.ANVIL));

            inv.setItem(slots[i], ItemBuilder.of(Material.CAULDRON_ITEM)
                .appendLore(maps.get(finalI).getDescription()).setName(map.getColoredName()).setLore(map.getDescription()).build()
            );
        }

        return inv;

    }



}
