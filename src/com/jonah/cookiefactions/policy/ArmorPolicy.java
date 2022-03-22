package com.jonah.cookiefactions.policy;

import com.google.common.util.concurrent.CycleDetectingLockFactory;
import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.util.Config;
import com.jonah.cookiefactions.util.DataConfig;
import com.jonah.cookiefactions.util.ItemBuilder;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ArmorPolicy implements Listener {

    public static AtomicBoolean token = new AtomicBoolean(true);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {



        if (e.getCurrentItem().containsEnchantment(Enchantment.PROTECTION_PROJECTILE) && token.get() && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            int level = e.getCurrentItem().getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE);
            e.getCurrentItem().removeEnchantment(Enchantment.PROTECTION_PROJECTILE);
                e.getCurrentItem().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level);

                e.getWhoClicked().sendMessage(BukkitPlugin.color("&aSwitched to Protection Armor!"));

                ItemBuilder.relore(e.getCurrentItem(), "&8[&eClick&8] &ato switch to", "&bProjectile Protection 4&a Armor");

                e.setCancelled(true);
            }
            else if (e.getCurrentItem().containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL) && token.get() && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
                int level = e.getCurrentItem().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);

                e.getCurrentItem().removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
                e.getCurrentItem().addEnchantment(Enchantment.PROTECTION_PROJECTILE, level);

                e.getWhoClicked().sendMessage(BukkitPlugin.color("&aSwitched to Projectile Protection Armor!"));
            ItemBuilder.relore(e.getCurrentItem(), "&8[&eClick&8] &ato switch to", "&bProtection 4&a Armor");

                e.getCurrentItem().getItemMeta().getLore();
                e.setCancelled(true);
            }

    }

    private DataConfig getConfig(Player p) {
        return new DataConfig(String.format("armordata/%s.yml", p.getUniqueId().toString()));
    }

    public void equipArmor(Player p) {
        p.getInventory().setArmorContents(
                new ItemStack[]{
                        ItemBuilder.of(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 4).setLore("&8[&eClick&8] &ato switch to", "&bProtection 4&a Armor").build(),
                        ItemBuilder.of(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 4).setLore("&8[&eClick&8] &ato switch to", "&bProtection 4&a Armor").build(),
                        ItemBuilder.of(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_PROJECTILE, 4).setLore("&8[&eClick&8] &ato switch to", "&bProtection 4&a Armor").build(),
                        ItemBuilder.of(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_PROJECTILE, 4).setLore("&8[&eClick&8] &ato switch to", "&bProtection 4&a Armor").build()
                });
    }

    @EventHandler
    public void onGameMode(PlayerGameModeChangeEvent e) {
        if (e.getNewGameMode() == GameMode.SURVIVAL || e.getNewGameMode() == GameMode.ADVENTURE) {
            if (e.getPlayer().getInventory().getHelmet() == null) {
                equipArmor(e.getPlayer());
            }
        }
    }

    public CommandExecutor getArmorCommand() {
        return new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    equipArmor(p);
                    p.sendMessage(Text.colorize("&aArmor equipped!"));
                }

                return true;
            }
        };
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (e.getPlayer().getInventory().getHelmet() == null) {
            equipArmor(e.getPlayer());
        }
//        } else {
//            ProtPacketInfo[] data = getArmorData(e.getPlayer());
//
//            e.getPlayer().getInventory().setArmorContents(new ItemStack[] {
//                    ItemBuilder.of(Material.DIAMOND_HELMET).addEnchant(data[0].enchantment, data[0].level).setLore(" ", "&aClick to switch to Protection 4 Armor").build(),
//                    ItemBuilder.of(Material.DIAMOND_CHESTPLATE).addEnchant(data[1].enchantment, data[1].level).setLore(" ", "&aClick to switch to Protection 4 Armor").build(),
//                    ItemBuilder.of(Material.DIAMOND_LEGGINGS).addEnchant(data[2].enchantment, data[2].level).setLore(" ", "&aClick to switch to Protection 4 Armor").build(),
//                    ItemBuilder.of(Material.DIAMOND_BOOTS).addEnchant(data[3].enchantment, data[3].level).setLore(" ", "&aClick to switch to Protection 4 Armor").build()
//            });
//
//        }
    }

//    @EventHandler
//    public void onLeave(PlayerQuitEvent e) {
//        Player p = e.getPlayer();
//        setArmorData(p,
//                new ProtPacketInfo[] {
//                    extractArmor(p, p.getInventory().getHelmet()),
//                        extractArmor(p, p.getInventory().getChestplate()),
//                        extractArmor(p, p.getInventory().getLeggings()),
//                        extractArmor(p, p.getInventory().getBoots()),
//                }
//                );
//    }

    private ProtPacketInfo extractArmor(Player p, ItemStack item) {
        if (item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            return new ProtPacketInfo(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        } else return new ProtPacketInfo(Enchantment.PROTECTION_PROJECTILE, 4);
    }

    private ProtPacketInfo[] getArmorData(Player p) {

        FileConfiguration data = getConfig(p).getConfig();

        return new ProtPacketInfo[] {
                new ProtPacketInfo(
                        Enchantment.getByName(data.getString("Boots.EnchantedWith")),
                        data.getInt("Boots.Level")
                ),
                new ProtPacketInfo(
                        Enchantment.getByName(data.getString("Leggings.EnchantedWith")),
                        data.getInt("Leggings.Level")
                ),
                new ProtPacketInfo(
                        Enchantment.getByName(data.getString("Chestplate.EnchantedWith")),
                        data.getInt("Chestplate.Level")
                ),
                new ProtPacketInfo(
                        Enchantment.getByName(data.getString("Helmet.EnchantedWith")),
                        data.getInt("Helmet.Level")
                ),

        };
    }

    private void saveArmorData(Player p, String toSave, ProtPacketInfo dataSaved) {
        DataConfig config = getConfig(p);

        config.getConfig().set(String.format("%s.EnchantedWith", toSave), dataSaved.getEnchantment().getName());
        config.getConfig().set(String.format("%s.Level", toSave), dataSaved.getLevel());

        config.save();

    }

    private void setArmorData(Player p, ProtPacketInfo[] info) {

        DataConfig config = getConfig(p);

        config.getConfig().set("Helmet.EnchantedWith", info[0].enchantment.getName());
        config.getConfig().set("Chestplate.EnchantedWith", info[1].enchantment.getName());
        config.getConfig().set("Leggings.EnchantedWith", info[2].enchantment.getName());
        config.getConfig().set("Boots.EnchantedWith", info[3].enchantment.getName());


        config.getConfig().set("Helmet.Level", info[0].level);
        config.getConfig().set("Chestplate.Level", info[1].level);
        config.getConfig().set("Leggings.Level", info[2].level);
        config.getConfig().set("Boots.Level", info[3].level);

        config.save();

    }

    public static class ProtPacketInfo {

        private int level;
        private Enchantment enchantment;



        public ProtPacketInfo(Enchantment enchantment, int level) {
            this.level = level;
            this.enchantment = enchantment;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public void setEnchantment(Enchantment enchantment) {
            this.enchantment = enchantment;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

    }

}
