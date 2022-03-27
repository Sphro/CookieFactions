package com.jonah.cookiefactions.death;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.ItemBuilder;
import net.minecraft.server.v1_8_R3.FileIOThread;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StandardDeathFX implements Listener {

    HashMap<UUID, ItemStack[]> itemMap;

    public StandardDeathFX() {
        itemMap = new HashMap<>();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        itemMap.put(p.getUniqueId(), p.getInventory().getArmorContents());
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));

        ItemStack[] dropItems = new ItemStack[] {
                ItemBuilder.of(Material.DIAMOND_HELMET).setName("No Pickup").build(),
                ItemBuilder.of(Material.DIAMOND_CHESTPLATE).setName("No Pickup").build(),
                ItemBuilder.of(Material.DIAMOND_LEGGINGS).setName("No Pickup").build(),
                ItemBuilder.of(Material.DIAMOND_BOOTS).setName("No Pickup").build(),
                ItemBuilder.of(DyeColor.BLUE).setName("No Pickup").setAmount(64).build(),
                ItemBuilder.of(DyeColor.BLUE).setName("No Pickup").setAmount(64).build(),
                ItemBuilder.of(DyeColor.BLUE).setName("No Pickup").setAmount(64).build(),
                ItemBuilder.of(Material.PAPER).setName("No Pickup").setAmount(64).build(),
        };

        for (ItemStack item : dropItems) {

            Location l = p.getLocation();
            l.add(0, 1, 0);

            Item m = p.getWorld().dropItem(l, item);
            new BukkitRunnable() {

                @Override
                public void run() {
                    m.remove();
                }

            }.runTaskLater(BukkitPlugin.getInstance(), 20 * 5);
        }

    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();
        if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("No Pickup")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        p.getInventory().setArmorContents(itemMap.get(p.getUniqueId()));

    }

}
