package com.jonah.cookiefactions.core.nbt;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Attribute {

     public static boolean hasAttribute(ItemStack it, String key) {
         if (!(it instanceof CraftItemStack)) it = NbtFactory.getCraftItemStack(it);

         NbtFactory.NbtCompound compound = NbtFactory.fromItemTag(it);

         return compound.containsKey(key);
     }

     public static Object getAttribute(ItemStack it, String key) {
         if (!hasAttribute(it,  key)) return null;
         NbtFactory.NbtCompound compound = NbtFactory.fromItemTag(NbtFactory.getCraftItemStack(it));

         return compound.get(key);

     }

     public static NbtFactory.NbtCompound getCompound(ItemStack it) {
         if (!(it instanceof CraftItemStack)) it = NbtFactory.getCraftItemStack(it);
         return NbtFactory.fromItemTag(it);
     }

     public static void setAttribute(ItemStack it, String key, Object value) {
         //if (!hasAttribute(it,  key)) return;

         NbtFactory.NbtCompound compound = getCompound(it);

         compound.put(key, value);

         NbtFactory.setItemTag(NbtFactory.getCraftItemStack(it), compound);

     }

     public static void removeAttribute(ItemStack it, String key) {
         if (!hasAttribute(it,  key)) return;
         NbtFactory.NbtCompound compound = getCompound(it);

         compound.remove(key);

         NbtFactory.setItemTag(it, compound);
     }

     public static String getAttributeDump(ItemStack it) {
         return getCompound(it).toString();
     }

     public void setMeta(ItemStack it, ItemMeta itemMeta) {

     }

     public static class AttributeBuilder {
         private ItemStack item;

         private AttributeBuilder(ItemStack item) {
             this.item = item;
         }

         public AttributeBuilder setAttribute(String key, Object value) {
             Attribute.setAttribute(item, key, value);
             return this;
         }
         public void removeAttribute(String key) {
             Attribute.removeAttribute(item, key);
         }


     }


}
