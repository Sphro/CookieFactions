package com.jonah.cookiefactions.chat.common.event;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.chat.common.ChatLib;
import com.jonah.cookiefactions.util.Text;
import com.mojang.authlib.GameProfile;
import com.nametagedit.plugin.NametagEdit;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TagCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length > 0) {
            StringBuilder builder = new StringBuilder();

            Player p = (Player) sender;

            for (int i = 0 ; i < args.length ; i++) {
                builder.append(args[i] + ((i == args.length - 1) ? "" : " "));
            }

            if (ChatColor.stripColor(builder.toString()).length() > 16) {
                sender.sendMessage(Text.colorize("&cThis tag is too long! Try something shorter."));
                return true;
            }

            if (builder.toString().matches("([^A-Z][^0-9][^!@#$^&])")) {
                sender.sendMessage(Text.colorize("&cYour tag must not contain illegal characters."));
                sender.sendMessage(Text.colorize("&cLegal characters: ") + "A-Z, 0-9, !@#$^&");


                return true;
            }

            ChatLib lib = new ChatLib((Player) sender);
            lib.setPrefix(builder.toString());
            //p.setPlayerListName(Text.colorize(lib.getPrefix() + " " + "&f" + p.getName()));

            NametagEdit.getApi().setPrefix(p, Text.colorize(lib.getPrefix() + " &f"));

            //BukkitPlugin.getInstance().getNameChanger().changeName(p, Text.colorize(lib.getPrefix() + " " + "&f" + p.getName()));

            sender.sendMessage(Text.colorize("&aSuccessfully set your tag to " + lib.getPrefix()));

            for (Player lp : Bukkit.getOnlinePlayers()) {
                lp.hidePlayer(p);
                lp.showPlayer(p);
            }

        }

        return true;
    }
}
