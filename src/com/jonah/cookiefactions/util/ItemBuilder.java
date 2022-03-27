package com.jonah.cookiefactions.util;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
	
	private ItemStack stack;
	
	public ItemBuilder(ItemStack stack) {
		this.stack = stack;
	}
	
	public static ItemBuilder of(Material material) {
		return new ItemBuilder(new ItemStack(material));
	}
	public static ItemBuilder of(Material material, DyeColor color) {
		return new ItemBuilder(new ItemStack(material, 1, color.getData()));
	}
	public static ItemBuilder of(DyeColor dye) {
		Dye d = new Dye(dye);
		return new ItemBuilder(d.toItemStack());
	}



	public ItemBuilder setName(String name) {
		ItemMeta stackMeta = this.stack.getItemMeta();
		stackMeta.setDisplayName(Text.colorize(name));
		this.stack.setItemMeta(stackMeta);
		return this;
	}
	
	public ItemBuilder setAmount(int amount) {
		this.stack.setAmount(amount);
		return this;
	}
	
	public ItemBuilder setLore(List<String> lore) {
		ItemMeta stackMeta = this.stack.getItemMeta();
		stackMeta.setLore(Text.colorizeList(lore));
		this.stack.setItemMeta(stackMeta);
		return this;
	}
	
	public ItemBuilder setLore(String... lore) {
		List<String> arrayedLore = Arrays.asList(lore);
		ItemMeta stackMeta = this.stack.getItemMeta();
		stackMeta.setLore(Text.colorizeList(arrayedLore));
		this.stack.setItemMeta(stackMeta);
		return this;
	}
	
	public ItemBuilder appendLore(String... lore) {
		List<String> arrayedLore = Arrays.asList(lore);
		ItemMeta stackMeta = this.stack.getItemMeta();
		List<String> finalAppended = stackMeta.getLore() == null ? new ArrayList<>() : stackMeta.getLore();
		finalAppended.addAll(arrayedLore);
		stackMeta.setLore(Text.colorizeList(finalAppended));
		this.stack.setItemMeta(stackMeta);
		return this;
	}

	public ItemBuilder appendLore(List<String> lore) {
		ItemMeta stackMeta = this.stack.getItemMeta();
		List<String> finalAppended = stackMeta.getLore() == null ? new ArrayList<>() : stackMeta.getLore();
		finalAppended.addAll(lore);
		stackMeta.setLore(Text.colorizeList(finalAppended));
		this.stack.setItemMeta(stackMeta);
		return this;
	}

	public ItemBuilder setData(short data) {
		this.stack.setDurability(data);
		return this;
	}
	
	public ItemBuilder durability(short durability) {
		this.stack.setDurability(durability);
		return this;
	}

	public ItemBuilder addEnchant(Enchantment ench, int level) {
		ItemMeta meta = stack.getItemMeta();
		meta.addEnchant(ench, level, true);
		stack.setItemMeta(meta);
		return this;
	}

	public static void relore(ItemStack item, String... lore) {
		ItemMeta data = item.getItemMeta();

		data.setLore(Text.colorizeList(Arrays.asList(lore)));

		item.setItemMeta(data);

	}

//	public ItemBuilder toCITS() {
//		this.stack = NbtFactory.getCraftItemStack(stack);
//		return this;
//	}
//
//	public ItemBuilder setCustomValue(String label, Object value) {
//
//		ItemStack is = this.toCITS().stack;
//
//		NbtFactory.NbtCompound compound = NbtFactory.fromItemTag(is);
//
//		compound.put(label, value);
//
//		NbtFactory.setItemTag(is, compound);
//
//		return this;
//	}
//
	public ItemStack build() {
		return this.stack;
	}
//
//	public static String getData(ItemStack item) {
//		return NbtFactory.fromItemTag(NbtFactory.getCraftItemStack(item)).toString();
//	}
}
