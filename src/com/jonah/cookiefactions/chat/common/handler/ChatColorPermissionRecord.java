package com.jonah.cookiefactions.chat.common.handler;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public class ChatColorPermissionRecord {

    //4c6e2ab39578df
    public static ChatColorPermissionRecord[] RECORDS = new ChatColorPermissionRecord[] {
            new ChatColorPermissionRecord('4', "&4&l&nDark Red", DyeColor.RED),
            new ChatColorPermissionRecord('c', "&c&l&nRed", DyeColor.RED),
            new ChatColorPermissionRecord('6', "&6&l&nGold", DyeColor.ORANGE),
            new ChatColorPermissionRecord('e', "&e&l&nYellow", DyeColor.YELLOW),
            new ChatColorPermissionRecord('2', "&2&l&nGreen", DyeColor.GREEN),
            new ChatColorPermissionRecord('a', "&a&l&nLime", DyeColor.LIME),
            new ChatColorPermissionRecord('b', "&b&l&nSky Blue", DyeColor.LIGHT_BLUE),
            new ChatColorPermissionRecord('3', "&3&l&nAqua", DyeColor.CYAN),
            new ChatColorPermissionRecord('9', "&9&l&nBlue", DyeColor.BLUE),
            new ChatColorPermissionRecord('5', "&5&l&nPurple", DyeColor.PURPLE),
            new ChatColorPermissionRecord('7', "&7&l&nSilver", DyeColor.SILVER),
            new ChatColorPermissionRecord('8', "&8&l&nDark Gray", DyeColor.GRAY),
            new ChatColorPermissionRecord('d', "&d&l&nPink", DyeColor.PINK),
            new ChatColorPermissionRecord('f', "&f&l&nWhite", DyeColor.WHITE)
    };

    private char code;
    private String guiName;
    private DyeColor dyeColor;

    private ChatColorPermissionRecord(char code, String guiName, DyeColor color) {
        this.code = code;
        this.guiName = guiName;
        this.dyeColor = color;
    }

    public char getCode() {
        return code;
    }

    public String getGUIName() {
        return guiName;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public String raw() {
        return ChatColor.getByChar(getCode()).toString();
    }

}
