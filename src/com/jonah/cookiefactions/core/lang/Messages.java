package com.jonah.cookiefactions.core.lang;


import com.jonah.cookiefactions.core.CoreManager;
import com.jonah.cookiefactions.util.Text;

public enum Messages {

    NO_ENDER_PEARL("&cYou cannot use Ender Pearls", "NoEnderPearls"),
    NO_PLACE_BLOCK("&cYou cannot place blocks.", "NoPlaceBlocks"),
    NO_BREAK_BLOCK("&cYou cannot break blocks.", "NoBreakBlocks"),
    NO_INTERACT_BLOCK("&cYou cannot interact with this.", "NoInteractMessage");

    private String msg;
    private String configLabel;

    Messages(String msg, String configLabel) {
        this.msg = msg;
        this.configLabel = configLabel;
    }

    @Override
    public String toString() {
        String fromConfig = CoreManager.getInstance().getMessages().getString("Messages." + configLabel, "NULL");
        return Text.colorize(fromConfig.equalsIgnoreCase("NULL") ? msg : fromConfig);
    }
}
